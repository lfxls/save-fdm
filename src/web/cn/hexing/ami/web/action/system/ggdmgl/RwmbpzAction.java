package cn.hexing.ami.web.action.system.ggdmgl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import cn.hexing.ami.service.system.ggdmgl.RwmbpzManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;
import cn.hexing.ami.web.listener.StartupListener;

/** 
 * @Description 任务模板配置
 * @author  jun
 * @Copyright 2013 hexing Inc. All rights reserved
 * @time：2013-1-12
 * @version AMI3.0 
 */
public class RwmbpzAction extends BaseAction implements DbWorksInf,QueryInf{
	private static Logger logger = Logger.getLogger(RwmbpzAction.class.getName());
	private static final long serialVersionUID = -2834195548246036831L;
	//终端规约类型
	private List<Object> zdgyLs;
	private RwmbpzManagerInf rwmbpzManager;
	private String zdgylx;
	private String sjxbm;
	private String sjxmc;
	private String rwsx;
	private List<Object> tableLs;
	private List<Object> fieldLs;
	private Map<String,String[]> tableMap;
	private String sjb;
	
	public String getSjb() {
		return sjb;
	}

	public void setSjb(String sjb) {
		this.sjb = sjb;
	}

	public Map<String, String[]> getTableMap() {
		return tableMap;
	}

	public void setTableMap(Map<String, String[]> tableMap) {
		this.tableMap = tableMap;
	}

	public List<Object> getTableLs() {
		return tableLs;
	}

	public void setTableLs(List<Object> tableLs) {
		this.tableLs = tableLs;
	}

	public List<Object> getFieldLs() {
		return fieldLs;
	}

	public void setFieldLs(List<Object> fieldLs) {
		this.fieldLs = fieldLs;
	}

	public String getRwsx() {
		return rwsx;
	}

	public void setRwsx(String rwsx) {
		this.rwsx = rwsx;
	}

	public String getSjxbm() {
		return sjxbm;
	}

	public void setSjxbm(String sjxbm) {
		this.sjxbm = sjxbm;
	}

	public String getSjxmc() {
		return sjxmc;
	}

	public void setSjxmc(String sjxmc) {
		this.sjxmc = sjxmc;
	}

	public String getZdgylx() {
		return zdgylx;
	}

	public void setZdgylx(String zdgylx) {
		this.zdgylx = zdgylx;
	}

	public RwmbpzManagerInf getRwmbpzManager() {
		return rwmbpzManager;
	}

	public void setRwmbpzManager(RwmbpzManagerInf rwmbpzManager) {
		this.rwmbpzManager = rwmbpzManager;
	}

	public List<Object> getZdgyLs() {
		return zdgyLs;
	}

	public void setZdgyLs(List<Object> zdgyLs) {
		this.zdgyLs = zdgyLs;
	}
	
	/**
	 * 页面初始化
	 */
	public String init(){
		zdgyLs = CommonUtil.getCode("zdgylx", this.getLang(), true);
		zdgylx=Constants.GYLX_DLMS;
		return SUCCESS;
	}
	
	@Override
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("zdgylx", zdgylx);
		param.put(Constants.APP_LANG, this.getLang());
		Map<String, Object> m = rwmbpzManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(m);
		return null;
	}
	
	/**
	 * 页面初始化
	 */
	public String showConfigWin(){
		return "showConfigWin";
	}
	
	/**
	 * 待选数据项列表
	 */
	public String queryDxsjx() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sjxbm", sjxbm);
		param.put("sjxmc", sjxmc);
		param.put(Constants.APP_LANG, this.getLang());
		Map<String, Object> m = rwmbpzManager.queryDxsjx(param, start, limit, dir, sort, isExcel);
		responseGrid(m);
		return null;
	}
	
	/**
	 * 任务数据项列表
	 */
	public String queryRwsjx() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("rwsx", rwsx);
		param.put("zdgylx", zdgylx);
		param.put(Constants.APP_LANG, this.getLang());
		Map<String, Object> m = rwmbpzManager.queryRwsjx(param, start, limit, dir, sort, isExcel);
		responseGrid(m);
		return null;
	}
	
	/**
	 * 显示数据项和数据表字段对应配置界面
	 * @return
	 */
	public String showSjxzdWin(){
		//获取配置文件中数据和字段配置信息
		getTableInfo();
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("rwsx", rwsx);
		param.put("zdgylx", zdgylx);
		param.put(Constants.APP_LANG, this.getLang());
		//获取任务对应的全部数据项信息
		Map<String, Object> m = rwmbpzManager.queryRwsjx(param, "0", "1000", dir, sort, isExcel);
		List<Object> sjxLs = (List<Object>)((Map<String, Object>)m).get("result");
		
		if (StringUtil.isEmptyString(sjb)) {
			//默认任务模板对应的数据表，搜索p_sjxdy，p_dbsjxmx2张表，只要其中有一个张表有配置即可
			for (int i = 0; i < sjxLs.size(); i++) {
				Map<String, Object> rowMap = (Map<String, Object>)sjxLs.get(i);
				sjb = rowMap.get("DYSJB")==null?"":(String)rowMap.get("DYSJB");
				if (!StringUtil.isEmptyString(sjb)) {
					break;
				}
				
				//获取结构体内容
				String struct= String.valueOf(rowMap.get("array_struct_item".toUpperCase())==null?"":rowMap.get("array_struct_item".toUpperCase()));
				
				if (!"".equals(struct)) {
					//结构体格式：8.0.0.1.0.0.255.2#3.1.0.3.8.0.255.2#3.1.0.3.8.1.255.2#3.1.0.3.8.2.255.2#3.1.0.3.8.3.255.2#3.1.0.3.8.4.255.2
					String[] structArray = struct.split("#");
					for (int j = 0; j < structArray.length; j++) {
						String obis = structArray[j];
						int pos1 = obis.indexOf(".");
						int pos2 = obis.lastIndexOf(".");
						obis = obis.substring(0,pos1)+"#"+obis.substring(pos1+1,pos2)+"#"+obis.substring(pos2+1,obis.length());
						structArray[j] = obis;
					}
					param.put("sjxbms", structArray);
					//获取结构体数据项列表
					List<Object> structSjxLs = rwmbpzManager.getStructSjxRwsxList(param);
					for (int j = 0; j < structSjxLs.size(); j++) {
						rowMap = (Map<String, Object>)structSjxLs.get(i);
						sjb = rowMap.get("DYSJB")==null?"":(String)rowMap.get("DYSJB");
						if (!StringUtil.isEmptyString(sjb)) {
							break;
						}
					}
				}
				if (!StringUtil.isEmptyString(sjb)) {
					break;
				}
			}
		}
		
		List<Object> newSjxLs = new ArrayList<Object>();
		for (int i = 0; i < sjxLs.size(); i++) {
			Map<String, Object> rowMap = (Map<String, Object>)sjxLs.get(i);
			String sjxbm = (String)rowMap.get("SJXBM");
			newSjxLs.add(rowMap);
			//获取结构体内容
			String struct= String.valueOf(rowMap.get("array_struct_item".toUpperCase())==null?"":rowMap.get("array_struct_item".toUpperCase()));
			//如果结构体为空，表示小型数据，如果结构体不为空，表示打包数据
			if (!"".equals(struct)) {
				//结构体格式：8.0.0.1.0.0.255.2#3.1.0.3.8.0.255.2#3.1.0.3.8.1.255.2#3.1.0.3.8.2.255.2#3.1.0.3.8.3.255.2#3.1.0.3.8.4.255.2
				String[] structArray = struct.split("#");
				for (int j = 0; j < structArray.length; j++) {
					String obis = structArray[j];
					int pos1 = obis.indexOf(".");
					int pos2 = obis.lastIndexOf(".");
					obis = obis.substring(0,pos1)+"#"+obis.substring(pos1+1,pos2)+"#"+obis.substring(pos2+1,obis.length());
					structArray[j] = obis;
				}
				param.put("sjxbms", structArray);
				//获取结构体数据项列表
				List<Object> structSjxLs = rwmbpzManager.getStructSjxList(param);
				
				List<Object> structSjxNewLs = new ArrayList<Object>();
				//按照结构体内部顺序进行重新排序
				for (int j = 0; j < structArray.length; j++) {
					for (int j2 = 0; j2 < structSjxLs.size(); j2++) {
						Map<String,Object> tmpMap = (Map<String,Object>)structSjxLs.get(j2);
						String sjxbmTmp = String.valueOf(tmpMap.get("SJXBM"));
						tmpMap.put("DBSJXBM", sjxbm);
						if (structArray[j].equals(sjxbmTmp)) {
							structSjxNewLs.add(tmpMap);
							break;
						}
					}
				}
				structSjxLs = null;
				newSjxLs.addAll(structSjxNewLs);
			}
		}
		request.setAttribute("sjxLs", newSjxLs);
		
		//获取默认数据表对应的字段信息
		if (tableLs!=null && StringUtil.isEmptyString(sjb)) {
			sjb = String.valueOf(((Map<String,Object>)tableLs.get(0)).get("BM"));
		}
		
		//字段信息
		String[] fieldArray = tableMap.get(sjb.toUpperCase());
		fieldLs = new ArrayList<Object>();
		Map<String,String> o = new HashMap<String, String>();
		o.put("BM", "");
		o.put("MC", "");
		fieldLs.add(o);
		
		for (int i = 0; fieldArray!=null && i<fieldArray.length; i++) {
			o = new HashMap<String, String>();
			o.put("BM", fieldArray[i]);
			o.put("MC", fieldArray[i]);
			fieldLs.add(o);
		}
		return "showSjxzdWin";
	}
	
	/**
	 * 从配置文件获取任务对应数据表信息
	 */
	private void getTableInfo(){
		Properties props = new Properties();
		InputStream fis = null;
		try{
			fis = StartupListener.class.getResourceAsStream("/res/DLMS_RWSJX.properties");
			props.load(fis);
			
			tableLs = new ArrayList<Object>();
			tableMap = new HashMap<String,String[]>();
			
			for (Iterator iter = props.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				String svalue = (String) props.get(key);
				String[] valueArray = svalue.split(":");
				//表名
				Map<String,String> o = new HashMap<String, String>();
				o.put("BM", valueArray[0]);
				o.put("MC", valueArray[0]);
				tableLs.add(o);
				
				String[] fieldArray = valueArray[1].split(",");
				tableMap.put(valueArray[0], fieldArray);
			}
		}catch(Exception ex){
			logger.error(StringUtil.getExceptionDetailInfo(ex));
		}finally{
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error(CommonUtil.getExceptionDetailInfo(e));
				}
			}
		}
	}
	@Override
	public String queryDetail() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 更新操作
	 */
	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = rwmbpzManager.doDbWorks(czid, param);
		return re;
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		rwmbpzManager.dbLogger(czid, cznr, czyId, unitCode);
	}
}