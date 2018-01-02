package cn.hexing.ami.service.system.qxgl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.AqCzy;
import cn.hexing.ami.dao.common.pojo.TreeCheckNode;
import cn.hexing.ami.dao.common.pojo.TreeNode;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.aes.RijndaelUtil;

/**
 * @Description 操作员管理manager
 * @author ycl
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-4-10
 * @version AMI3.0
 */
public class CzyglManager implements CzyglManagerInf {

	private static Logger logger = Logger.getLogger(CzyglManager.class.getName());
	BaseDAOIbatis baseDAOIbatis = null;
	static String menuId = "52100";
	static String sqlId = "czygl.";

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	public List<TreeNode> getJgTree(String nodeId, String nodeType, String unitCode, String opID, String deptID, String accessFlag) {
		List<TreeNode> ls = new ArrayList<TreeNode>();
		List<Object> re = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("nodeId", nodeId);
		param.put("czyid", opID);
		param.put("bmid", deptID);
		param.put("fwbj", accessFlag);
		DatabaseUtil.fwdwFilter(param, opID, accessFlag, deptID, "DW");
		if ("DW".equals(nodeType)) {// 取下级单位和机构
			re = baseDAOIbatis.queryForList(sqlId + "getDw", param);
			ls.addAll(getNodes(re));
			re = baseDAOIbatis.queryForList(sqlId + "getJg", param);
			ls.addAll(getNodes(re));
		} else if ("JG".equals(nodeType)) { // 取操作员
			re = baseDAOIbatis.queryForList(sqlId + "getCzy", param);
			ls.addAll(getNodes(re));
		} else { // 查询
			/*JSONObject job = JSONObject.fromObject(nodeId);
			Map<String, String> param = (Map<String, String>) JSONObject
					.toBean(job, Map.class);
			param.put("unitCode", unitCode);
			re = baseDAOIbatis.queryForList(sqlId + "queryCzy", param);
			ls.addAll(getNodes(re));*/
		}
		return ls;
	}

	private List<TreeNode> getNodes(List<Object> re) {
		List<TreeNode> ls = new ArrayList<TreeNode>();
		if (re != null && re.size() > 0) {
			for (int i = 0; i < re.size(); i++) {
				Map<?, ?> m = (Map<?, ?>) re.get(i);
				String nodeType = StringUtil.getValue(m.get("TYPE"));
				TreeNode node = new TreeNode(StringUtil.getValue(m.get("ID")),
						StringUtil.getValue(m.get("NAME")), nodeType, "JG"
								.equals(nodeType));
				node.setIconCls(nodeType.toLowerCase());
				String dwdm = StringUtil.getValue(m.get("INFO"));
				if (!StringUtil.isEmptyString(dwdm) && "JG".equals(nodeType))
					node.setInfo(dwdm);
				ls.add(node);
			}
		}
		return ls;
	}


	/**
	 * 初始化操作员
	 * 
	 * @param czyid
	 * @return
	 */
	public AqCzy initCzy(String czyid) {
		AqCzy op = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("czyid", czyid);
		List<Object> l = baseDAOIbatis.queryForList("login.getAqCzy", param);
		if (l != null && l.size() > 0) {
			op = (AqCzy) l.get(0);
			String pwd = op.getMm();
			try {
				op.setMm(RijndaelUtil.decodePassword(pwd));
			} catch (Exception e) {
				logger.error(StringUtil.getExceptionDetailInfo(e));
			}
		}
		return op;
	}

	/**
	 * 角色树
	 * 
	 * @param czyid
	 * @return
	 */
	public List<TreeCheckNode> getJsTree(String czyid) {
		List<TreeCheckNode> nodes = new ArrayList<TreeCheckNode>();
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("czyid", czyid);
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "getJsTree", mp);
		if (ls != null && ls.size() > 0) {
			for (int i = 0; i < ls.size(); i++) {
				Map<?, ?> m = (Map<?, ?>) ls.get(i);
				TreeCheckNode tcn = new TreeCheckNode(StringUtil.getValue(m
						.get("JSID")), StringUtil.getValue(m.get("JSMC")), "",
						true);
				tcn.setChecked("1"
						.equals(StringUtil.getValue(m.get("CHECKED"))));
				nodes.add(tcn);
			}
		}
		return nodes;
	}

	/**
	 * 访问权限树
	 * 
	 * @param czyid
	 * @param dwdm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TreeCheckNode> getQxFwTree(String czyid, String dwdm) {
		List<TreeCheckNode> nodes = new ArrayList<TreeCheckNode>();
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("czyid", czyid);
		mp.put("dwdm", dwdm);
		mp.put("root", "true");
		List<?> root = baseDAOIbatis.queryForList(sqlId + "getQxFwTree", mp);
		mp.put("root", "false");
		List<?> leafs = baseDAOIbatis.queryForList(sqlId + "getQxFwTree", mp);
		Map<String, Object> obj = (Map<String, Object>) root.get(0);
		TreeCheckNode node = map2TreeNode(obj);
		if (leafs.size() > 0) {
			List<TreeCheckNode> children = new ArrayList<TreeCheckNode>();
			for (int i = 0, len = leafs.size(); i < len; i++) {
				Map<String, Object> o = (Map<String, Object>) leafs.get(i);
				TreeCheckNode children_node = map2TreeNode(o);
				children.add(children_node);
			}
			node.setLeaf(false);
			node.setChildren(children);
			node.setExpanded(true);
		}
		nodes.add(node);
		return nodes;
	}

	private TreeCheckNode map2TreeNode(Map<String, Object> o) {
		String s_dw = StringUtil.getValue(o.get("DWDM"));
		String s_jb = StringUtil.getValue(o.get("JB"));
		String s_dwmc = StringUtil.getValue(o.get("DWMC"));
		String s_checked = StringUtil.getValue(o.get("CHECKED"));
		TreeCheckNode e = new TreeCheckNode(s_dw, s_dwmc, s_jb, true);
		e.setChecked("1".equals(s_checked));
		return e;
	}

	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);
	}

	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		String type = param.get("type")==null?"":param.get("type");
		
		//操作员
		if ("czy".equals(type)) {
			if ((menuId + Constants.ADDOPT).equals(czid)) { // 新增
				re = addCzy(param);
			} else if ((menuId + "02").equals(czid)) { // 更新
				re = updateCzy(param);
			} else if ((menuId + "03").equals(czid)) { // 删除
				re = deleteCzy(param);
			} else if((menuId + "04").equals(czid)) {
				re = updPwd(param);
			} else if((menuId + "05").equals(czid)) { //解锁
				re = updMmcwcs(param);
			}
		}
		
		//售电点授权
		if("pos".equals(type)){
			if ((menuId + Constants.ADDOPT).equals(czid)) { // 新增
				re = addPos(param);
			} else if ((menuId + Constants.DELOPT).equals(czid)) { // 更新
				re = deletePos(param);
			} 
		}
		
		//pos机授权
		if("posMachine".equals(type)){
			if ((menuId + Constants.ADDOPT).equals(czid)) { //授权pos机
				re = addPosMachine(param);
			} else if ((menuId + Constants.DELOPT).equals(czid)) { //取消授权pos机
				re = deleteMachine(param);
			} 
		}
		
		return re;
	}

	private ActionResult updPwd(Map<String, String> param) {
		ActionResult re = new ActionResult();
		Map<String, Object> o = new HashMap<String, Object>();
		o.put("czyid", param.get(Constants.CURR_STAFFID));
		AqCzy op = (AqCzy)baseDAOIbatis.queryForObject("login.getAqCzy", o, AqCzy.class);
		String oldPwd = param.get("oldPwd");
		String newPwd = param.get("newPwd");
		if(!RijndaelUtil.encodePassword(oldPwd).equals(op.getMm())) {
			re.setSuccess(false);
			re.setMsg("sysModule.qxgl.czygl.wrong_pwd", param.get(Constants.APP_LANG));
		} else {
			o.put("czyid", op.getCzyid());
			o.put("xm", param.get("xm"));
			o.put("dhhm", param.get("dhhm"));
			o.put("sjhm", param.get("sjhm"));
			o.put("yxdz", param.get("yxdz"));
			o.put("zt", op.getZt());
			o.put("mm", RijndaelUtil.encodePassword(newPwd));
			o.put("bmid", op.getBmid());
			baseDAOIbatis.update(sqlId + "updCzy", o);
			re.setSuccess(true);
			re.setMsg("sysModule.qxgl.czygl.updPwd_success", param.get(Constants.APP_LANG));
		}
		return re;
	}
	
	/**
	 * 解锁操作员
	 * 
	 * @param param
	 * @return
	 */
	
	private ActionResult updMmcwcs(Map<String, String> param) {
		ActionResult re = new ActionResult();
		String czyid = param.get("czyid");
		Map<String, Object> o = new HashMap<String, Object>();
		o.put("czyId", czyid);
		o.put("mmcwcs", null);
		o.put("zt", Constants.ACCOUNTSTATUS_NORMAL);
		baseDAOIbatis.update("login.updateMmcwcs", o);
		re.setMsg("sysModule.qxgl.czygl.unlock_success", param.get(Constants.APP_LANG));
		re.setSuccess(true);
		return re;
	}

	/**
	 * 新增操作员
	 * 
	 * @param param
	 * @return
	 */
	private ActionResult addCzy(Map<String, String> param) {
		ActionResult re = new ActionResult();
		String dwdm = param.get("dwdm");
		String bmid = param.get("bmid");
		String czyid = param.get("czyid");
		String xm = param.get("xm");
		String sjhm = param.get("sjhm");
		String dhhm = param.get("dhhm");
		String zt = param.get("zt");
		String mm = param.get("mm");
		String ip = param.get("ip");
		String sysjq = param.get("sysjq");
		String sysjz = param.get("sysjz");
		String dwdms=param.get("dwdms");
		//手机通讯服务商
		String txfws = param.get("txfws");
		//安装队
		String tno = param.get("tno");
		
		Map<String, Object> o = new HashMap<String, Object>();
		o.put("czyid", czyid);
		o.put("xm", xm);
		o.put("dwdm", dwdm);
		o.put("bmid", bmid);
		o.put("dhhm", dhhm);
		o.put("sjhm", sjhm);
		o.put("yxdz", param.get("yxdz"));
		o.put("zt", zt);
		o.put("ip", ip);
		o.put("sysjq", sysjq);
		o.put("sysjz", sysjz);
		o.put("dwdms", dwdms);
		o.put("txfws", txfws);
		o.put("tno", tno);
		List<String> sqlIds = new ArrayList<String>();
		
		param.put("czyid", czyid);
		List<?> ls = baseDAOIbatis.queryForList("login.getAqCzy", param);
		if (ls != null && ls.size() > 0) {
			re.setSuccess(false);
			re.setMsg("sysModule.qxgl.czygl.add_existsCzyid", param.get(Constants.APP_LANG));
		} else {
			if(StringUtil.isEmptyString(mm)) {
				o.put("mm", RijndaelUtil.encodePassword("1"));// 默认密码1
			} else {
				o.put("mm", RijndaelUtil.encodePassword(mm));
			}
			sqlIds.add(sqlId + "insCzy");
			sqlIds.add(sqlId + "insCzyBm");
			doCzyJs(param, sqlIds, o);
			doFwqx(param, sqlIds, o);
			baseDAOIbatis.executeBatchTransaction(sqlIds, o);
			re.setMsg("sysModule.qxgl.czygl.add_success", param.get(Constants.APP_LANG));
			re.setSuccess(true);
		}
		return re;
	}

	/**
	 * 修改操作员
	 * 
	 * @param param
	 * @return
	 */
	private ActionResult updateCzy(Map<String, String> param) {
		ActionResult re = new ActionResult();
		String dwdm = param.get("dwdm");
		String bmid = param.get("bmid");
		String czyid = param.get("czyid");
		String xm = param.get("xm");
		String sjhm = param.get("sjhm");
		String dhhm = param.get("dhhm");
		String zt = param.get("zt");
		String mm = param.get("mm");
		String ip = param.get("ip");
		String sysjq = param.get("sysjq");
		String sysjz = param.get("sysjz");
		String dwdms=param.get("dwdms");
		//手机通讯服务商
		String txfws = param.get("txfws");
		//安装队
		String tno = param.get("tno");
		
		Map<String, Object> o = new HashMap<String, Object>();
		o.put("czyid", czyid);
		o.put("xm", xm);
		o.put("dwdm", dwdm);
		o.put("bmid", bmid);
		o.put("dhhm", dhhm);
		o.put("sjhm", sjhm);
		o.put("yxdz", param.get("yxdz"));
		o.put("zt", zt);
		o.put("ip", ip);
		o.put("sysjq", sysjq);
		o.put("sysjz", sysjz);
		o.put("dwdms", dwdms);
		o.put("txfws", txfws);
		o.put("tno", tno);
		
		if(!StringUtil.isEmptyString(mm))
		o.put("mm", RijndaelUtil.encodePassword(mm));
		
		List<String> sqlIds = new ArrayList<String>();
		sqlIds.add(sqlId + "updCzy");//更新操作员
		sqlIds.add(sqlId + "updCzyBm");//更新操作员部门
		
		doCzyJs(param, sqlIds, o);//更新操作员角色
		doFwqx(param, sqlIds, o);//更新操作员访问权限
		baseDAOIbatis.executeBatchTransaction(sqlIds, o);
		re.setMsg("sysModule.qxgl.czygl.upd_success", param.get(Constants.APP_LANG));
		re.setSuccess(true);
		return re;
	}

	/**
	 * 删除操作员
	 * 
	 * @param param
	 * @return
	 */
	private ActionResult deleteCzy(Map<String, String> param) {
		ActionResult re = new ActionResult();
		String czyid = param.get("czyid");
		//超级管理账号不能删除
		if (czyid.equals(Constants.SUPER_ADMIN)) {
			re.setSuccess(false);
			re.setMsg("sysModule.qxgl.czygl.del_admin",param.get(Constants.APP_LANG));
			return re;
		}
		Map<String, Object> o = new HashMap<String, Object>();
		o.put("czyid", czyid);
		List<String> sqlIds = new ArrayList<String>();
		
		List<Object> ls = this.baseDAOIbatis.queryForList(sqlId + "cxstatus", czyid);
		if ((ls != null) && (ls.size() > 0))
	    {
	      re.setSuccess(false);
	      re.setMsg("sysModule.qxgl.czygl.stsenable", (String)param.get("appLang"));
	    } else {
	    	sqlIds.add(sqlId + "delCzyJs");
			sqlIds.add(sqlId + "delCzyBm");
			sqlIds.add(sqlId + "delCzyFwqx");
			sqlIds.add(sqlId + "delCzy");
			baseDAOIbatis.executeBatchTransaction(sqlIds, o);
			re.setMsg("sysModule.qxgl.czygl.del_success", param.get(Constants.APP_LANG));
			re.setSuccess(true);
	    }
				
		return re;
	}

	/**
	 * 售电点授权
	 * @param param
	 * @return
	 */
	private ActionResult addPos(Map<String, String> param){
		ActionResult re = new ActionResult();
		baseDAOIbatis.delete(sqlId+"authPos", param);
		re.setMsg("sysModule.qxgl.czygl.pos.add_success", param.get(Constants.APP_LANG));
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 售电点授权取消
	 * @param param
	 * @return
	 */
	private ActionResult deletePos(Map<String, String> param){
		ActionResult re = new ActionResult();
		baseDAOIbatis.delete(sqlId+"cancelAuthPos", param);
		re.setMsg("sysModule.qxgl.czygl.pos.del_success", param.get(Constants.APP_LANG));
		re.setSuccess(true);
		return re;
	}
	/**
	 * 操作员角色处理
	 * 
	 * @param param
	 * @param sqlIds
	 * @param o
	 */
	private void doCzyJs(Map<String, String> param, List<String> sqlIds,
			Map<String, Object> o) {
		String jsids = param.get("jsids");
		if (!StringUtil.isEmptyString(jsids)) {
			o.put("jsids", jsids.split(","));
			sqlIds.add(sqlId + "delCzyJs");
			sqlIds.add(sqlId + "insCzyJs");
		}
	}

	/**
	 * 操作员权限处理
	 * 
	 * @param param
	 * @param sqlIds
	 * @param o
	 */
	private void doFwqx(Map<String, String> param, List<String> sqlIds,
			Map<String, Object> o) {
		String qxfw = param.get("qxfw"); // 修改访问权限
		if ("true".equals(qxfw)) {
			String fwdws = param.get("dwdms");
			sqlIds.add(sqlId + "delCzyFwqx");
			sqlIds.add(sqlId + "insFwdwRoot");// 先存上级节点
			if (!StringUtil.isEmptyString(fwdws)) {
				sqlIds.add(sqlId + "insFwdw");// 递归存下级节点
				o.put("fwdws", fwdws.split(","));
			}
		}else{
			//取消授权
			sqlIds.add(sqlId + "delCzyFwqx");
		}
	}
	
	/**
	 * 查询售电点列表
	 */
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "getDeptPosList", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 获取用户列表
	 */
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap =  baseDAOIbatis.getExtGrid(param, sqlId + "getUserList", start, limit, dir, sort, isExcel);
		
		//转换单位名称显示，显示所有上级单位名称
		List<Object> resultList = (List<Object>)resultMap.get("result");
		List<Object> newResultList = new ArrayList<Object>();
		if (resultList!=null) {
			for (int i = 0; i < resultList.size(); i++) {
				Map rowMap = (Map)resultList.get(i);
				String dwdm = (String)rowMap.get("DWDM");
				String dwmc = (String)rowMap.get("DWMC");
				param.put("dwdm", dwdm);
				List<Object> dwList = baseDAOIbatis.queryForList(sqlId+"getAllParentDw", param);
				String newDwmc = dwList2Str(dwmc,dwList);
				rowMap.put("DWMC", newDwmc);
				newResultList.add(rowMap);
			}
		}
		
		resultMap.put("result", newResultList);
		return resultMap;
	}
	
	/**
	 * 已经授权的售电点列表
	 */
	public Map<String, Object> getAuthorizedPosList(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "getAuthorizedPosList", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 获取单位树
	 * @param sjdwdm
	 * @param czyId
	 * @param bmid
	 * @param fwbj
	 * @return
	 */
	public List<TreeNode> getDwTree(String sjdwdm, String czyId, String bmid,
			String fwbj) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("nodeId", sjdwdm);
		DatabaseUtil.fwdwFilter(param, czyId, fwbj, bmid, "DW");
		List<Object> ls = baseDAOIbatis.queryForList(sqlId+"getDwDw", param);
		List<TreeNode> tree = new ArrayList<TreeNode>();
		for (int i = 0, len = ls.size(); i < len; i++) {
			Map<String, Object> m = (Map<String, Object>) ls.get(i);
			String dwdm = StringUtil.getValue(m.get("DWBM"));
			String dwmc = StringUtil.getValue(m.get("DWMC"));
			String dwlx = StringUtil.getValue(m.get("DWLX"));
			TreeNode node = new TreeNode(dwdm, dwmc, dwlx, "06".equals(dwlx));
			node.setIconCls("dw");
			tree.add(node);
		}
		return tree;
	}
	/**
	 * 上级单位显示拼接字符串
	 * @param oldDwmc 未拼接前单位名称
	 * @param dwList
	 * @return
	 */
	private String dwList2Str(String oldDwmc,List<Object> dwList){
		String dwStr = oldDwmc+"(";
		if (dwList!=null) {
			for (int i = 0; i < dwList.size()-1; i++) {
				String dwmc  = (String)((Map)dwList.get(i)).get("DWMC");
				if(i==dwList.size()-2 && dwList.size()>1){
					dwStr += dwmc+")"; 
				}else{
					dwStr += dwmc + "/";
				}
			}
		}
		if (dwStr.endsWith("(")) {
			dwStr = dwStr.substring(0,dwStr.length()-1);
		}
		return dwStr;
	}

	/**
	 * 获取未授权的pos机列表
	 */
	@Override
	public Map<String, Object> getPosMachineList(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"getPosMachineList", start, limit, dir, sort, isExcel);
	}

	/**
	 * 获取已授权的pos机列表
	 */
	@Override
	public Map<String, Object> getAuthorizedPosMachineList(
			Map<String, Object> param, String start, String limit, String dir,
			String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"getAuthorizedPosMachineList", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 取消将某个pos机授权给某个操作员
	 * @param param
	 * @return
	 */
	private ActionResult deleteMachine(Map<String, String> param) {
		ActionResult re = new ActionResult();
		baseDAOIbatis.delete(sqlId+"cancelAuthPosMachine", param);
		re.setMsg("sysModule.qxgl.posMachine.cancelAuth",param.get(Constants.APP_LANG));
		re.setSuccess(true);
		return re;	
	}

	/**
	 * 将某个pos机授权给某个操作员
	 * @param param
	 * @return
	 */
	private ActionResult addPosMachine(Map<String, String> param) {
		ActionResult re = new ActionResult();
		baseDAOIbatis.insert(sqlId+"authPosMachine", param);
		re.setMsg("sysModule.qxgl.posMachine.auth",param.get(Constants.APP_LANG));
		re.setSuccess(true);
		return re;	
	}
}
