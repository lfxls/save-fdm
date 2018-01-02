package cn.hexing.ami.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.ExtTreeNode;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.StringUtil;

public class LeftTreeManager {

	BaseDAOIbatis baseDAOIbatis = null;

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	String sqlId = "lefttree.";
	
	
	/**
	 * 取得电网结构
	 * @return
	 */
	public Map<String, Object> getDw(String nodeType, String nodeId,
			String czyid, String fwbj, String bmid, String viewType, String yhlx,
			String count, String pages, String start, String end, String lang) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("nodeId", nodeId);
		param.put("viewType", viewType);
		param.put("yhlx", yhlx);
		param.put(Constants.APP_LANG, lang);
		List<ExtTreeNode> nodes = new ArrayList<ExtTreeNode>();
		List<Object> ls = null;
		long recCount = 0;
		if("dw".equals(nodeType)) { //单位
			DatabaseUtil.fwdwFilter(param, czyid, fwbj, bmid, "DW");
			// 电网
			ls = baseDAOIbatis.queryForList(sqlId + "getDwDw", param);
			nodes.addAll(createNode(ls, viewType, yhlx, false, true, lang)); 
			//解决第一次加载没有分页的情况
			if (StringUtil.isEmptyString(start)) {
				start = String.valueOf(Constants.PAGE_START);
				end = String.valueOf(Constants.PAGE_END);
				pages = "true";
			}
					
			param.put("start",Integer.valueOf(start));
			param.put("end",Integer.valueOf(end));
			param.put("limit",Integer.valueOf(end)-Integer.valueOf(start));
			param.put("pages",pages);
			
			DatabaseUtil.fwdwFilter(param, czyid, fwbj, bmid, "m");
			ls = baseDAOIbatis.queryForList(sqlId + "getSb", param);
			nodes.addAll(createSbNode(ls, viewType, false, false, lang)); 	
			if ("true".equals(pages)) {
				recCount = (Long) baseDAOIbatis.queryForObject(sqlId + "countSb", param, Long.class);
			}
		}
		Map<String, Object> re = new HashMap<String, Object>();
		if (recCount==0) {
			recCount = new Long(nodes.size());
		}
		re.put("count", recCount);
		re.put("nodes", nodes);
		return re;
	}
	
	/**
	 * 单位节点
	 * 
	 * @param ls
	 * @param viewType
	 * @param expanded
	 * @return
	 */
	private List<ExtTreeNode> createNode(List<?> ls, String viewType,String yhlx,
			boolean expanded, boolean paging, String lang) {
		List<ExtTreeNode> nodes = new ArrayList<ExtTreeNode>();
		if (ls != null && ls.size() > 0) {
			for (int i = 0; i < ls.size(); i++) {
				Map<?, ?> m = (Map<?, ?>) ls.get(i);
				String type = StringUtil.getValue(m.get("TYPE"));
				ExtTreeNode node = new ExtTreeNode(StringUtil.getValue(m
						.get("DWBM")), StringUtil.getValue(m.get("DWMC")),
						type, viewType, false,
//						StringUtil.getValue(m.get("DWLX")).compareTo("20") > 0
//								&& paging);
						paging);
				if("dw".equals(type)) {
					node.setDwlx(StringUtil.getValue(m.get("DWLX")));
				} else if ("tq".equals(type)){
					node.setQtip(I18nUtil.getText("leftTree.tqrl", lang) + ":" + StringUtil.getValue(m.get("RL")) + "kVA");
				} else if ("xl".equals(type)){
					node.setQtip(I18nUtil.getText("leftTree.xl", lang) + ":" + StringUtil.getValue(m.get("DWBM")));
				} else if ("byq".equals(type)){
					node.setQtip(I18nUtil.getText("leftTree.byqrl", lang) + ":" + StringUtil.getValue(m.get("RL")) + "kVA");
				} else if (type.indexOf("qz") > -1){
					String yxts = StringUtil.getValue(m.get("YXTS"));
					String tip = (StringUtil.isEmptyString(yxts) ? I18nUtil.getText("leftTree.qz.yjyx", lang)
									: I18nUtil.getText("leftTree.qz.yxts", lang) + yxts);
					node.setQtip(tip);
				}
				node.setDwdm(StringUtil.getValue(m.get("DWDM")));
				node.setYhlx(yhlx);
				node.setIconCls(type);
				node.setExpanded(expanded);
				
				nodes.add(node);
			}
		}
		return nodes;
	}
	
	/**
	 * 取群组结构
	 * @return
	 */
	public Map<String, Object> getQz(String nodeType, String nodeId, String yhlx,String viewType,
			String count, String pages, String start, String end,String lang) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("nodeId", nodeId);
		yhlx = "03";
		param.put("yhlx", yhlx);
		param.put("viewType", viewType);
		param.put(Constants.APP_LANG, lang);
		List<ExtTreeNode> nodes = new ArrayList<ExtTreeNode>();
		List<Object> ls = null;
		if("qzRoot".equals(nodeType)) { //群组根
			//获取所有群组（传入的nodeId是操作员ID）
			ls = baseDAOIbatis.queryForList(sqlId + "getQzQz", param);
			//获取群组对应明细
			List<Object> mxLs = baseDAOIbatis.queryForList(sqlId + "getQzMx", param);
			List<Object> newLs = new ArrayList<Object>();
			
			//过滤掉没有明细对应的群组
			String qzidList = "";
			for (int i = 0; i < mxLs.size(); i++) {
				String qzidTmp = String.valueOf(((Map<String,Object>)mxLs.get(i)).get("QZID"));
				qzidList += qzidTmp+",";
			}
			
			for (int i = 0; i < ls.size(); i++) {
				Map<String,Object> rowMap = (Map<String,Object>)ls.get(i);
				String qzid = String.valueOf(rowMap.get("DWBM"));
				if (qzidList.indexOf(qzid+",")!=-1) {
					newLs.add(rowMap);
				}
			}
			ls = null;
			mxLs = null;
			nodes.addAll(createNode(newLs, viewType, yhlx,false, true, lang)); 
		} else if("sbqz".equals(nodeType) || "bjqz".equals(nodeType)) {//群组用户
			//表计或者终端
			String type = viewType.substring(0, 2);
			param.put("viewType", type);
			ls = baseDAOIbatis.queryForList(sqlId + "getQzSb", param);
			nodes.addAll(createSbNode(ls,type, false, true, lang)); 
		}
		Map<String, Object> re = new HashMap<String, Object>();
		re.put("count", new Long(nodes.size()));
		if ("true".equals(pages)) {
			re.put("nodes", nodes.subList(Integer.valueOf(start),
					Integer.valueOf(end)));
		} else {
			re.put("nodes", nodes);
		}
		return re;
	}
	
	/**
	 * 取查询结构
	 * @return
	 */
	public Map<String, Object> getCx(String sbid, Map<String, Object> queryInfo, 
			String czyid, String fwbj, String bmid, 
			String count, String pages, String start, String end, String lang) {
		Map<String, Object> re = new HashMap<String, Object>();
		List<Object> ls = null;
		List<ExtTreeNode> nodes = null;
		queryInfo.put(Constants.APP_LANG, lang);
		
		if(StringUtil.isEmptyString(sbid)) { //查询
			DatabaseUtil.fwdwFilter(queryInfo, czyid, fwbj, bmid, "m");
			if("true".equals(count)) {
				Long c = (Long) baseDAOIbatis.queryForObject(
						sqlId + "countCxSb", queryInfo, Long.class);
				re.put("count", c);
			} else {
				if ("true".equals(pages)) {
					queryInfo.put("start",Integer.valueOf(start));
					queryInfo.put("end",Integer.valueOf(end));
					queryInfo.put("pages",pages);
					queryInfo.put("limit",Integer.valueOf(end)-Integer.valueOf(start));
				} 
				ls = baseDAOIbatis.queryForList(sqlId + "getCxSb", queryInfo);
				nodes = createSbNode(ls, String.valueOf(queryInfo.get("viewType")), true, false, lang);
				re.put("nodes", nodes);
			}
		} 
		return re;
	}
	
	/**
	 * 取查询结构-小型预付费
	 * @return
	 */
	public Map<String, Object> getCxS(String sbid, Map<String, Object> queryInfo, 
			String czyid, String fwbj, String bmid, 
			String count, String pages, String start, String end, String lang) {
		Map<String, Object> re = new HashMap<String, Object>();
		List<Object> ls = null;
		List<ExtTreeNode> nodes = null;
		queryInfo.put(Constants.APP_LANG, lang);
		
		if(StringUtil.isEmptyString(sbid)) { //查询
			DatabaseUtil.fwdwFilter(queryInfo, czyid, fwbj, bmid, "zd");
			if("true".equals(count)) {
				Long c = (Long) baseDAOIbatis.queryForObject(
						sqlId + "countCxSb", queryInfo, Long.class);
				re.put("count", c);
			} else {
				if ("true".equals(pages)) {
					queryInfo.put("start",Integer.valueOf(start));
					queryInfo.put("end",Integer.valueOf(end));
					queryInfo.put("pages",pages);
					queryInfo.put("limit",Integer.valueOf(end)-Integer.valueOf(start));
				} 
				ls = baseDAOIbatis.queryForList(sqlId + "getCxSb", queryInfo);
				nodes = createSbNode(ls, String.valueOf(queryInfo.get("viewType")), true, false, lang);
				re.put("nodes", nodes);
			}
		} 
		return re;
	}
	
	/**
	 * 取设备节点
	 * 
	 * @param ls
	 * @param sfzd 是否终端 （区别表计）
	 * @return
	 */
	private List<ExtTreeNode> createSbNode(List<?> ls, String viewType,
			boolean sfzd, boolean expanded, String lang) {
		List<ExtTreeNode> nodes = new ArrayList<ExtTreeNode>();
		if (ls != null && ls.size() > 0) {
			for (int i = 0; i < ls.size(); i++) {
				Map<?, ?> m = (Map<?, ?>) ls.get(i);
				String tip = "";
				String custName = StringUtil.getValue(m.get("CNAME"));
				String custNo = StringUtil.getValue(m.get("CNO"));
				String meterNo = StringUtil.getValue(m.get("MSN"));
				String nodeText = StringUtil.isEmptyString(custNo)?meterNo:meterNo+"("+custNo+")";
				ExtTreeNode node = new ExtTreeNode(meterNo, nodeText, viewType, viewType,true, expanded);
				node.setHh(custNo);
				node.setHm(custName);
				tip = StringUtil.getValue(m.get("MSTSN"));
				if (!StringUtil.isEmptyString(custName)) {
					tip += "("+custName+")";
				}
				node.setIconCls("bj");
				node.setQtip(tip);
				nodes.add(node);
			}
		}
		return nodes;
	}
	
	/**
	 * 取设备节点(小型预付费)
	 * 
	 * @param ls
	 * @param sfzd 是否终端 （区别表计）
	 * @return
	 */
	private List<ExtTreeNode> createSbNodeS(List<?> ls, String viewType, boolean expanded, String lang) {
		List<ExtTreeNode> nodes = new ArrayList<ExtTreeNode>();
		if (ls != null && ls.size() > 0) {
			for (int i = 0; i < ls.size(); i++) {
				Map<?, ?> m = (Map<?, ?>) ls.get(i);
				String tip = "";
				String type = (String) m.get("TYPE");
				if("bj".equals(type) || "allBj".equals(type)){
					viewType = "bj";
					//表计显示时加入表计号显示
					String hm = m.get("HM")==null?"":"("+(String) m.get("HM")+")";
					ExtTreeNode node = new ExtTreeNode((String) m.get("BJJH"),
							(String) m.get("BJJH")+hm, viewType, viewType,
							true, expanded);
					// 表计树的时候，把表计信息虚拟化成终端信息
					String yhlx = StringUtil.getValue(m.get("YHLX"));
					
					node.setZdjh(StringUtil.getValue(m.get("ZDJH")));
					node.setZdljdz(StringUtil.getValue(m.get("ZDLJDZ")));
					node.setZdgylx(StringUtil.getValue(m.get("ZDGYLX")));
					node.setZdyt(StringUtil.getValue(m.get("ZDYT")));
					
					node.setZdlx(StringUtil.getValue(m.get("BJLX")));
					node.setBjlx(StringUtil.getValue(m.get("BJLX")));
					node.setZt(StringUtil.getValue(m.get("ZT")));
					node.setYhlx(yhlx);
					node.setDwdm(StringUtil.getValue(m.get("DWDM")));
					node.setHh(StringUtil.getValue(m.get("HH")));
					node.setHm(StringUtil.getValue(m.get("HM")));
					node.setByqid(StringUtil.getValue(m.get("BYQID")));
					node.setByqmc(StringUtil.getValue(m.get("BYQMC")));
					//表计类型  表计局号:状态
					tip = StringUtil.getValue(m.get("BJLXMC")) 
					+ " " + (String) m.get("BJJH") 
					+ ":" + StringUtil.getValue(m.get("BJZT"));
					node.setQtip(tip);
					String zt = StringUtil.getValue(m.get("ZT"));
					if (Constants.BJZT_RK.equals(zt)) {
						node.setIconCls("sb_dz");
					} else if (Constants.BJZT_ZC.equals(zt)) {
						//离线表
						node.setIconCls("bj_ol");
					} else if (Constants.BJZT_TY.equals(zt)) {
						if ("1".equals(StringUtil.getValue(m.get("BZT")))) { // 取工况判断24小时通讯
							tip += StringUtil.getValue(m.get("ZDZT"));
							node.setIconCls("sb_yx");
						} else {
							tip += I18nUtil.getText("leftTree.zdzt.24hour", lang);
							node.setIconCls("sb_24");
						}
					} else if (Constants.BJZT_CH.equals(zt)) {
						node.setIconCls("sb_cc");
					}
					nodes.add(node);
				}else{
					String azdz = StringUtil.getValue(m.get("AZDZ"));
					//带括号
					String azdzNew = !StringUtil.isEmptyString(azdz)?"("+azdz+")":azdz;
//					azdz = StringUtil.isEmptyString(azdz) ? I18nUtil.getText("leftTree.nozd", lang) : azdz;
					
					//终端节点显示时加入终端局号显示
					ExtTreeNode node = new ExtTreeNode((String) m.get("ZDJH"),
							(String) m.get("ZDJH")+azdzNew, (String) m.get("TYPE"), viewType, false, expanded);
					node.setZdjh(StringUtil.getValue(m.get("ZDJH")));
					node.setZdljdz(StringUtil.getValue(m.get("ZDLJDZ")));
					node.setZdlx(StringUtil.getValue(m.get("ZDLX")));
					node.setZdgylx(StringUtil.getValue(m.get("ZDGYLX")));
					node.setByqid(StringUtil.getValue(m.get("BYQID")));
					node.setByqmc(StringUtil.getValue(m.get("BYQMC")));
					node.setHh(StringUtil.getValue(m.get("HH")));
					node.setHm(azdz);
					String zt = StringUtil.getValue(m.get("ZT"));
					String zdlx = StringUtil.getValue(m.get("ZDLX"));
					node.setZt(zt);
					node.setExpanded(expanded);
					tip =StringUtil.getValue(m.get("ZDLXMC"))  
					+ " " + StringUtil.getValue(m.get("ZDLJDZ")) 
					+ ":";
					if(Constants.TERMINAL_GPRS.equals(zdlx)){
						if (Constants.ZDZT_RK.equals(zt)) {
							tip += StringUtil.getValue(m.get("ZDZT"));
							node.setIconCls("sb_dz"); //入库 即 待装
						} else if (Constants.ZDZT_DT.equals(zt)) { //待投
							tip += StringUtil.getValue(m.get("ZDZT"));
							node.setIconCls("sb_dt");
						} else if (Constants.ZDZT_TY.equals(zt)) { //投运
							if ("1".equals(StringUtil.getValue(m.get("BZT")))) { // 取工况判断24小时通讯
								tip += StringUtil.getValue(m.get("ZDZT"));
								node.setIconCls("sb_yx");
							} else {
								tip += I18nUtil.getText("leftTree.zdzt.24hour", lang);
								node.setIconCls("sb_24");
							}
						} else if (Constants.ZDZT_CH.equals(zt)) { //拆回
							tip += StringUtil.getValue(m.get("ZDZT"));
							node.setIconCls("sb_cc");
						}
					}else if(Constants.TERMINAL_CONCENTOR.equals(zdlx)){
						if (Constants.ZDZT_RK.equals(zt)) {
							tip += StringUtil.getValue(m.get("ZDZT"));
//							node.setIconCls("sb_dz"); //入库 即 待装
							node.setIconCls("jzq");
						} else if (Constants.ZDZT_DT.equals(zt)) { //待投
							tip += StringUtil.getValue(m.get("ZDZT"));
//							node.setIconCls("sb_dt");
							node.setIconCls("jzq");
						} else if (Constants.ZDZT_TY.equals(zt)) { //投运
							if ("1".equals(StringUtil.getValue(m.get("BZT")))) { // 取工况判断24小时通讯
								tip += StringUtil.getValue(m.get("ZDZT"));
//								node.setIconCls("sb_yx");
								node.setIconCls("jzq");
							} else {
								tip += I18nUtil.getText("leftTree.zdzt.24hour", lang);
//								node.setIconCls("sb_24");
								node.setIconCls("jzq");
							}
						} else if (Constants.ZDZT_CH.equals(zt)) { //拆回
							tip += StringUtil.getValue(m.get("ZDZT"));
//							node.setIconCls("sb_cc");
							node.setIconCls("jzq");
						}
					}
					node.setYhlx(StringUtil.getValue(m.get("ZDYT")));
					node.setDwdm(StringUtil.getValue(m.get("DWDM")));
					node.setQtip(tip);
					nodes.add(node);
				}
			}
		}
		return nodes;
	}
	
	/**
	 * 右键访问页面权限
	 * @param operatorId
	 * @param actionName
	 * @return
	 */
	public boolean getCdqx(String operatorId, String actionName) {
		if ("admin".equals(operatorId))
			return true;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("operatorId", operatorId);
		paramMap.put("actionName", actionName);
		List<Object> checkList = baseDAOIbatis.queryForList(sqlId + "getQxcd",
				paramMap);
		Map<?, ?> check = (Map<?, ?>) checkList.get(0);
		if ("0".equals(check.get("ITEMCOUNT"))) {
			return true;
		} else if ("0".equals(check.get("CANACCESSCOUNT"))) {
			return false;
		}
		return true;
	}
	
	/**
	 * 获取单位列表
	 * @param dwdm
	 * @param fwdw
	 * @param type
	 * @return
	 */
	public List<Object> getAdvList(Map<String, Object> param) {
		List<Object> ls = null;
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("DWBM", "-1");
		m.put("DWMC", I18nUtil.getText("common.select.all", (String)param.get(Constants.APP_LANG)));
		ls = getDwList(param);
		ls.add(0,m);
		return ls;
	}
	
	/**
	 * 取单位列表
	 * @param dwdm
	 * @param fwdw
	 * @param fwjb
	 * @return
	 */
	private List<Object> getDwList(Map<String, Object> param) {
		param.put("viewType", "sb");
		return  baseDAOIbatis.queryForList(sqlId + "getDwDw", param);
	}
	
	/**
	 * 小型预付费，获取表计和集中器树
	 * @return
	 */
	public Map<String, Object> getSbS(String nodeType, String viewType,String nodeId,String czyid, String fwbj, String bmid,
			String count,String pages, String start, String end, String lang){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("nodeId", nodeId);
		param.put("yhlx", Constants.DYYH);
		param.put(Constants.APP_LANG, lang);
		param.put("viewType", viewType);
		
		List<ExtTreeNode> nodes = new ArrayList<ExtTreeNode>();
		List<Object> ls = null;
		
		if ("true".equals(pages)) {
			param.put("start",Integer.valueOf(start));
			param.put("limit",Integer.valueOf(end)-Integer.valueOf(start));
			param.put("pages",pages);
		} 
		
		long recCount = 0;
		if ("dw".equals(nodeType)) {
			if (viewType.equals("bj")) {
				viewType="allBj";
			}
			param.put("viewType", viewType);
			if ("true".equals(count)) {
				recCount = (Long) baseDAOIbatis.queryForObject(sqlId + "countSbS", param, Long.class);
			}else{
				ls = baseDAOIbatis.queryForList(sqlId + "getSbS", param);
				nodes.addAll(createSbNodeS(ls, viewType, false, lang)); 
			}
		}
		
		//集中器节点
		if("sb".equals(nodeType)){
			//根据nodeId判断是否是集中器
			Map<String,Object> param1 = new HashMap<String,Object>();
			param1.put("zdjh", nodeId);
			String zdlx = (String) baseDAOIbatis.queryForObject(sqlId+"queryZDLX", param1, String.class);
			if(Constants.TERMINAL_CONCENTOR.equals(zdlx)){
				viewType = "plcBj";
				param.put("viewType", viewType);
				
				if ("true".equals(count)) {
					recCount = (Long) baseDAOIbatis.queryForObject(sqlId + "countSbS", param, Long.class);
				}else{
					ls = baseDAOIbatis.queryForList(sqlId + "getSbS", param);
					nodes.addAll(createSbNodeS(ls, viewType,true, lang)); 
				}
			}
		}
		
		/*if ("bj".equals(nodeType)) {
			viewType = "lxbj";
			param.put("viewType", viewType);
			//离线预付费表，PLC表
			ls = baseDAOIbatis.queryForList(sqlId + "getSbS", param);
			viewType = "bj";
			param.put("viewType", viewType);
			nodes.addAll(createSbNodeS(ls, viewType, true, lang));
			
			if ("true".equals(pages)) {
				recCount = (Long) baseDAOIbatis.queryForObject(sqlId + "countSbS", param, Long.class);
			}
		}*/
		
		Map<String, Object> re = new HashMap<String, Object>();
		if (recCount==0) {
			recCount = new Long(nodes.size());
		}
		re.put("count", recCount);
		re.put("nodes", nodes);
		return re;
	}
}
