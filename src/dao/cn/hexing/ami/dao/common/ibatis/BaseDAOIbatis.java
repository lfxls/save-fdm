package cn.hexing.ami.dao.common.ibatis;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import cn.hexing.ami.dao.common.pojo.Page;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.listener.AppEnv;

import com.ibatis.sqlmap.client.SqlMapExecutor;

@SuppressWarnings("unchecked")
public class BaseDAOIbatis {
	private static Logger log = Logger.getLogger(BaseDAOIbatis.class.getName());
	private static final int batchSize =500;//500条提交一次
	
	private SqlMapClientTemplate sqlMapClientTemplate = null;

	public void setSqlMapClientTemplate(
			SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	private TransactionTemplate transactionTemplate = null;

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	/**
	 * 根据起始行数获取结果集
	 * @param param
	 * @param sqlName
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Object> getLimitResult(Map<String, String> param,String sqlName, String start, String end) {
		start = start==null?"0":start;
		end = end==null?"30":end;
		param.put("start", start);
		param.put("end", end);
		return queryForList(sqlName, param);
	}
	public Page queryForPage(Map<String, Object> param,String sqlName, int pageNo, int pageSize){
		//总记录数
		Integer count = (Integer) queryForObject(sqlName + "Count", param,
				Integer.class);
		if(count < 1)return new Page();
		int start = Page.getStartOfPage(pageNo, pageSize);
		int end = start + pageSize;
		param.put("start", start);
		param.put("end", end);
		param.put("limit", pageSize);
		List data = queryForList(sqlName,param);
		return new Page(start,count,pageSize,data);
	}
	public Page queryForPage(Map<String, Object> param,String sqlName, int pageNo){
		return queryForPage(param,sqlName,pageNo,Page.DEFAULT_PAGE_SIZE);
	}
	/**
	 * 不转换日期（公历和伊朗日历）
	 * @param sqlId
	 * @param obj
	 * @return
	 */
	public List<Object> queryForListNotChangeDate(String sqlId, Object obj) {
		List<Object> ls = sqlMapClientTemplate.queryForList(sqlId, obj);
		
		/*//返回map的key值转换为大写，兼容mysql
		List<Object> lsNew = new ArrayList<Object>();
		for (Object rowObj : ls) {
			if (rowObj instanceof Map) {
				Map tmpMap = (Map)rowObj;
				Map tmpNewMap = new HashMap();
				for (Iterator iterator = tmpMap.keySet().iterator(); iterator
						.hasNext();) {
					String key = (String) iterator.next();
					Object value = tmpMap.get(key);
					tmpNewMap.put(key.toUpperCase(), value);
				}
				tmpMap = null;
				lsNew.add(tmpNewMap);
			}else{
				lsNew.add(rowObj);
			}
		}
		ls = null;*/
		return ls;
	}
			
	/**
	 * 查询返回List
	 * 
	 * @param sqlId
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List<Object> queryForList(String sqlId, Object obj) {
		List<Object> resultList = sqlMapClientTemplate.queryForList(sqlId, obj);
		
		/*//返回map的key值转换为大写，兼容mysql
		List<Object> lsNew = new ArrayList<Object>();
		for (Object rowObj : resultList) {
			if (rowObj instanceof Map) {
				Map tmpMap = (Map)rowObj;
				
				Map tmpNewMap = new HashMap();
				for (Iterator iterator = tmpMap.keySet().iterator(); iterator
						.hasNext();) {
					String key = (String) iterator.next();
					Object value = tmpMap.get(key);
					tmpNewMap.put(key.toUpperCase(), value);
				}
				tmpMap = null;
				lsNew.add(tmpNewMap);
			}else{
				lsNew.add(rowObj);
			}
		}*/
		
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		if (sysMap!=null) {
			String calendarType = sysMap.get("calendarType");
			//伊朗模式
			if (calendarType!=null && calendarType.equals("iran")) {
				//转换伊朗历
				resultList = DateUtil.showListByIranDate(resultList);
			}
		}
		
//		resultList = null;
		return resultList;
	}
	
	/**
	 * 查询返回List
	 * 
	 * @param sqlId
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List<?> queryForListGenerics(String sqlId, Object obj) {
		return (List<?>)sqlMapClientTemplate.queryForList(sqlId, obj);
		
	}

	/**
	 * 查询返回(clazz)Object
	 * 
	 * @param sqlId
	 * @param obj
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public Object queryForObject(String sqlId, Object obj, Class clazz) {
		Object rtnObj = clazz.cast(sqlMapClientTemplate.queryForObject(sqlId, obj));
		/*if (rtnObj instanceof Map) {
			Map tmpMap = (Map)rtnObj;
			
			Map tmpNewMap = new HashMap();
			
			for (Iterator iterator = tmpMap.keySet().iterator(); iterator
					.hasNext();) {
				String key = (String) iterator.next();
				Object value = tmpMap.get(key);
				tmpNewMap.put(key.toUpperCase(), value);
			}
			tmpMap = null;
			return tmpNewMap;
		}else{
			return rtnObj;
		}*/
		return rtnObj;
		
	}

	public Object insert(String sqlId, Object obj) {
		return sqlMapClientTemplate.insert(sqlId, obj);
	}

	public int update(String sqlId, Object obj) {
		return sqlMapClientTemplate.update(sqlId, obj);
	}

	public int delete(String sqlId, Object obj) {
		return sqlMapClientTemplate.delete(sqlId, obj);
	}

	// 批量事务增、删、改
	public int executeBatchTransaction(final List<String> statementID,
			final Object paramObjs) {
		Integer result = null;
		result = ((Integer) transactionTemplate
				.execute(new TransactionCallback() {

					public Object doInTransaction(TransactionStatus status) {
						Integer rstObj = new Integer(-1);
						try {
							rstObj = executeBatch(statementID, paramObjs);
						} catch (Exception e) {
							status.setRollbackOnly();
							log.error(StringUtil.getExceptionDetailInfo(e));
							return null;
						}
						return rstObj;
					}
				}));
		return result;
	}

	public int executeBatch(final List<String> ls, final Object paramObjs) {
		Integer result = null;
		SqlMapClientCallback callback = new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				Integer rows = new Integer(-1);
				executor.startBatch();
				for (String insertStatementID : ls) {
					if (insertStatementID.indexOf("ins") > -1) {
						executor.insert(insertStatementID, paramObjs);
					} else if (insertStatementID.indexOf("upd") > -1) {
						executor.update(insertStatementID, paramObjs);
					} else if (insertStatementID.indexOf("del") > -1) {
						executor.delete(insertStatementID, paramObjs);
					}
				}
				rows = new Integer(executor.executeBatch());
				return rows;
			}
		};
		if (ls != null && ls.size() > 0 && paramObjs != null) {
			result = (Integer) sqlMapClientTemplate.execute(callback);
		}
		return result;
	}

	// 批量事务增、删、改
	public int executeBatchTransaction(final List<String> statementID,
			final List<?> paramObjs) {
		Integer result = null;
		result = ((Integer) transactionTemplate
				.execute(new TransactionCallback() {

					public Object doInTransaction(TransactionStatus status) {
						Integer rstObj = new Integer(-1);
						try {
							rstObj = executeBatch(statementID, paramObjs);
						} catch (Exception e) {
							status.setRollbackOnly();
							log.error(StringUtil.getExceptionDetailInfo(e));
							log.error("error!" , e);
							return null;
						}
						return rstObj;
					}
				}));
		return result;
	}

	public int executeBatch(final List<String> ls, final List<?> paramObjs) {
		Integer result = null;
		SqlMapClientCallback callback = new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				Integer rows = new Integer(-1);
				executor.startBatch();
				for (String insertStatementID : ls) {
					for (int i = 0; i < paramObjs.size(); i++) {
						Object obj = paramObjs.get(i);
						if (insertStatementID.indexOf("ins") > -1) {
							executor.insert(insertStatementID, obj);
						} else if (insertStatementID.indexOf("upd") > -1) {
							executor.update(insertStatementID, obj);
						} else if (insertStatementID.indexOf("del") > -1) {
							executor.delete(insertStatementID, obj);
						}
					}
				}
				rows = new Integer(executor.executeBatch());
				return rows;
			}
		};
		if (ls != null && ls.size() > 0 && paramObjs != null) {
			result = (Integer) sqlMapClientTemplate.execute(callback);
		}
		return result;
	}
	

	/*
	 * 批量事务增、删、改
	 * 一个sql，一个对象的做法
	 * 参数ls和paramObjs长度相当
	 */
	public int executeBatchTransactionMultiSql(final List<String> statementID,
			final List<?> paramObjs) {
		Integer result = null;
		result = ((Integer) transactionTemplate
				.execute(new TransactionCallback() {

					public Object doInTransaction(TransactionStatus status) {
						Integer rstObj = new Integer(-1);
						try {
							rstObj = executeBatch(statementID, paramObjs);
						} catch (Exception e) {
							status.setRollbackOnly();
							log.error(StringUtil.getExceptionDetailInfo(e));
							return null;
						}
						return rstObj;
					}
				}));
		return result;
	}
	
	/**
	 * 一个sql，一个对象的做法
	 * 参数ls和paramObjs长度相当
	 * @param ls
	 * @param paramObjs
	 * @return
	 */
	public int executeBatchMultiSql(final List<String> ls, final List<?> paramObjs) {
		Integer result = null;
		SqlMapClientCallback callback = new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				Integer rows = new Integer(-1);
				executor.startBatch();
				for (int i=0; i<ls.size(); i++) {
					String insertStatementID = ls.get(i);
					Object obj = paramObjs.get(i);
					if (insertStatementID.indexOf("ins") > -1) {
						executor.insert(insertStatementID, obj);
					} else if (insertStatementID.indexOf("upd") > -1) {
						executor.update(insertStatementID, obj);
					} else if (insertStatementID.indexOf("del") > -1) {
						executor.delete(insertStatementID, obj);
					}
				}
				rows = new Integer(executor.executeBatch());
				return rows;
			}
		};
		if (ls != null && ls.size() > 0 && paramObjs != null) {
			result = (Integer) sqlMapClientTemplate.execute(callback);
		}
		return result;
	}
	
	/**
	 * 单sql批量操作
	 * @param sqlId
	 * @param paramObjs
	 * @return
	 */
	public void executeBatch(final String sqlId, final List<?> paramObjs) {
		SqlMapClientCallback callback = new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				Integer rows = new Integer(-1);
				executor.startBatch();
				
				int cnt = 0;
				for (int i = 0; i < paramObjs.size(); i++) {
					Object obj = paramObjs.get(i);
					if (sqlId.indexOf("ins") > -1) {
						executor.insert(sqlId, obj);
					} else if (sqlId.indexOf("upd") > -1) {
						executor.update(sqlId, obj);
					} else if (sqlId.indexOf("del") > -1) {
						executor.delete(sqlId, obj);
					}
					//批量提交
					if (++cnt % batchSize == 0) {
						executor.executeBatch();
					}
				}
				rows = new Integer(executor.executeBatch());
				return rows;
			}
		};
		if (paramObjs != null) {
			sqlMapClientTemplate.execute(callback);
		}
	}
	
	/**
	 * extGrid list生成
	 * 
	 * @param param
	 * @param sqlName
	 * @param start
	 * @param end
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> getExtGrid(Map<String, Object> param,
			String sqlName, String start, String limit, String dir,
			String sort, String isExcel) {
		Map<String, Object> mapAll = new HashMap<String, Object>();
		if (StringUtil.isEmptyString(isExcel)) {
			long startTime = System.currentTimeMillis();
			Integer count = (Integer) queryForObject(sqlName + "Count", param,
					Integer.class); // Count
			List<Object> resultList = new ArrayList<Object>();
			if (count > 0) {
				int startInt = Integer.valueOf(start==null?"0":start);
				int endInt = Integer.valueOf(limit==null?"30":limit);
				param.put("limit", endInt);
				endInt = startInt + endInt > count ? count : startInt + endInt;
				//用于mysql
				param.put("start", startInt);
				param.put("end", endInt);
				if (!StringUtil.isEmptyString(sort)) {
					param.put("sort", sort);
				}
				if (!StringUtil.isEmptyString(dir)) {
					param.put("dir", dir);
				}
				resultList = queryForList(sqlName + "Query", param); // query
			}
			long endTime = System.currentTimeMillis();
			mapAll.put("result", resultList);
			mapAll.put("rows", count);
			//查询时间 四舍五入，保留3位小数，单位：秒
			mapAll.put("time", new BigDecimal(endTime).subtract(new BigDecimal(startTime)).divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_UP));
		} else {
			// 导出excel时查询全部数据并
			param.put("isExcel", "true");
			List<Object> countList = queryForList(sqlName + "Query", param);
			mapAll.put("result", countList);
		}
		return mapAll;
	}

	/**
	 * 写操作数据库日志
	 * 
	 * @param czid
	 * @param cdid
	 * @param czyId
	 * @param unitCode
	 * @param cznr
	 */
	public void insRzFwxx(String czid, String cdid, String czyId,
			String unitCode, String cznr) {
		Map<String, String> param = new HashMap<String, String>();
		String systemId = CommonUtil.getMultiSystemID();
		param.put("czid", czid);
		param.put("cdid", cdid);
		param.put("czyId", czyId);
		param.put("unitCode", unitCode);
		param.put("cznr", cznr);
		param.put("xtid", systemId);
		sqlMapClientTemplate.insert("common.insRzFwxx", param);
	}
	
	/**
	 * 写操作终端日志
	 */
	public void insRzCzzd(List<Object> paramList) {
		List<String> sqlIdls = new ArrayList<String>();
		
		if(paramList != null && paramList.size() > 0){
			sqlIdls.add("common.insRzCzzd");
			String systemId = CommonUtil.getMultiSystemID();
			List<Object> paramNewList =  new ArrayList<Object>();
			for (int i = 0; i < paramList.size(); i++) {
				Map<String, String> paramMap = (HashMap<String, String>)paramList.get(i);
				paramMap.put("xtid", systemId);
				paramNewList.add(paramMap);
			}
			paramList = null;
			this.executeBatchTransaction(sqlIdls, paramNewList);
		}
	}

	/**
	 * 功能权限过滤
	 * 
	 * @param czid
	 * @param czyId
	 */
	public void checkCz(String czid, String czyId) {

	}
}
