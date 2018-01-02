package cn.hexing.ami.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.proxy.dwr.Util;


import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.TreeNode;
import cn.hexing.ami.serviceInf.QzszManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.StringUtil;

/**
 * 群组设置
 * @Description 
 * @author  jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-12-15
 * @version AMI3.0
 */
public class QzszManager implements QzszManagerInf{
	BaseDAOIbatis baseDAOIbatis = null;

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	String sqlId = "qzsz.";
	String menuId = "00000";
	
	/**
	 * 群组更新操作
	 */
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult(true,"");
		//新增群组
		if((menuId + Constants.ADDOPT).equals(czid)){
			SimpleDateFormat forma= new SimpleDateFormat("yyyy-MM-dd");
			//生命周期是页面有效时间来的
			if(param.get("smzq").equals("1")){
				int i = DateUtil.getDate(forma.format(new Date()),param.get("yxrq"));
				param.put("smzq", String.valueOf(i==0?1:i));
			}
			
			// 查找群组名称是否存在，存在的话提示
			List<Object> qzmList = baseDAOIbatis.queryForList(sqlId + "qzmExsit",param);
			if(qzmList != null && qzmList.size() != 0){
				re.setSuccess(false);
				re.setMsg(I18nUtil.getText("common.qz.exist_qz", param.get(Constants.APP_LANG)));
				return re;
			}else{
				// 创建群组定义
				param.put("dwdm", param.get(Constants.UNIT_CODE));
				param.put("czyid", param.get(Constants.CURR_STAFFID));
				param.put("qzfl", "02"); //本地抄表默认就是表计分类
				
				baseDAOIbatis.insert(sqlId + "insQzdy",param);
				re.setMsg(I18nUtil.getText("common.qzsz.edit.addSuccess",param.get(Constants.APP_LANG)));
			}
			
		}else if ((menuId + Constants.UPDOPT).equals(czid)) { // 更新群组信息
			
			// 查找群组名称是否存在，存在的话提示
			List<Object> qzmList = baseDAOIbatis.queryForList(sqlId + "qzmExsits",param);
			if(qzmList != null && qzmList.size() != 0){
				re.setSuccess(false);
				re.setMsg(I18nUtil.getText("common.qz.exist_qz", param.get(Constants.APP_LANG)));
				return re;
			}else{
				SimpleDateFormat forma= new SimpleDateFormat("yyyy-MM-dd");
				if(param.get("smzq").equals("1")){
					int i=DateUtil.getDate(forma.format(new Date()),param.get("yxrq"));
					param.put("smzq", String.valueOf(i==0?1:i));
				}
				baseDAOIbatis.update(sqlId + "saveQz", param);
				re.setMsg(I18nUtil.getText("common.qzsz.edit.updateSuccess",param.get(Constants.APP_LANG)));
			}
			
		} else if ((menuId + Constants.DELOPT).equals(czid)) { // 根据终端局号删除群组下用户，并更新群组总数信息
			//设备号 表计或者终端 非全选
			if (param.get("zdjhArray")!=null) {
				Map<String, Object> bjjhParam = new HashMap<String,Object>();
				Map<String, Object> zdjhParam = new HashMap<String,Object>();
				bjjhParam.putAll(param);
				zdjhParam.putAll(param);
				String[] zdjhArray = param.get("zdjhArray").split("@");
				
				List<String> zdjhList = new ArrayList<String>();
				List<String> bjjhList = new ArrayList<String>();
				
				//设备类型 sb/bj
				String[] sblxArray = param.get("sblxArray").split("@");
				for (int i = 0; i < zdjhArray.length; i++) {
					//表计
					if (sblxArray[i]!=null && sblxArray[i].equals("bj")) {
						bjjhList.add(zdjhArray[i]);
					}
					//设备
					if (sblxArray[i]!=null && sblxArray[i].equals("sb")) {
						zdjhList.add(zdjhArray[i]);
					}
				}
				
				if (bjjhList.size()>0) {
					bjjhParam.put("qzlx", "bj");
					bjjhParam.put("zdjh", bjjhList.toArray(new String[bjjhList.size()]));
					//删除群组中表计信息
					baseDAOIbatis.delete(sqlId + "delUser", bjjhParam);
				}
				if (zdjhList.size()>0) {
					zdjhParam.put("qzlx", "sb");
					zdjhParam.put("zdjh",zdjhList.toArray(new String[zdjhList.size()]));
					//删除群组中终端信息
					baseDAOIbatis.delete(sqlId + "delUser", zdjhParam);
				}
			}else{
				//全部删除
				baseDAOIbatis.delete(sqlId + "delUser", param);
			}
			
			re.setMsg(I18nUtil.getText("common.qz.del_succ",param.get(Constants.APP_LANG)));
		}
		return re;
	}
	
	/**
	 * 数据库操作日志插入
	 */
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);
	}
	
	/**
	 * 群组查询操作
	 */
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "getQzGrid",start,limit,dir,sort,isExcel);
	}
	
	/**
	 * 根据群组ID 获取用户列表
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> getUpdateGrid(Map<String, Object> paramMap, String start,
			String limit, String dir, String sort, String isExcel){
	     return baseDAOIbatis.getExtGrid(paramMap, sqlId + "getUpdateGrid",start,limit,dir,sort,isExcel);
	}
	
	/**
	 * 根据群组ID 获取群组明细
	 * @param paramMap
	 * @return
	 */
	public List<Object> getQzDetail(String qzid){
	     return baseDAOIbatis.queryForList(sqlId + "getQzDetail", qzid);
	}
	
	/**
	 * 查询表计群组明细
	 */
	public Map<String, Object> queryDetailQzBj(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "bjda",start,limit,dir,sort,isExcel);
	}
	
	/**
	 * 行业树
	 */
	public List<TreeNode> getHyTree(String sjhyid,String lang) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sjhyid", sjhyid);
		param.put(Constants.APP_LANG, lang);
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "getHyTree", param);
		List<TreeNode> tree = new ArrayList<TreeNode>();
		for (int i = 0, len = ls.size(); i < len; i++) {
			Map<String, Object> m = (Map<String, Object>) ls.get(i);
			String hyid = StringUtil.getValue(m.get("HYID"));
			String hymc = StringUtil.getValue(m.get("HYMC"));
			String leaf = StringUtil.getValue(m.get("LEAF"));
			TreeNode node = new TreeNode(hyid, hymc, "hy", "0".equals(leaf));
			node.setIconCls("hy");
			tree.add(node);
		}
		return tree;
	}

	/**
	 * 用电属性树
	 */
	public List<TreeNode> getYdsxTree(String lang) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, lang);
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "getYdsxTree", param);
		List<TreeNode> tree = new ArrayList<TreeNode>();
		for (int i = 0, len = ls.size(); i < len; i++) {
			Map<String, Object> m = (Map<String, Object>) ls.get(i);
			String hyid = StringUtil.getValue(m.get("BM"));
			String hymc = StringUtil.getValue(m.get("MC"));
			TreeNode node = new TreeNode(hyid, hymc, "ydsx", true);
			tree.add(node);
		}
		return tree;
	}
	
	/**
	 * 获取群组名称
	 */
	public List<Object> getQzMc(Map<String, Object> param) {
		return  baseDAOIbatis.queryForList(sqlId+"getQzMc", param);
	}
	
	/**
	 * 加入群组是查询群组
	 */
	public Map<String, Object> queryDetailQz(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "queryDetailQz",start,limit,dir,sort,isExcel);
	}
	
	/**
	 * 保存加入群组
	 */
	public ActionResult save(Map<String, String> param,Util util){
		ActionResult re =  new ActionResult(true, "");
		SimpleDateFormat forma= new SimpleDateFormat("yyyy-MM-dd");
		String qzlx = StringUtil.getValue(param.get("qzlx"));
		 // 新增
		String[] jhs = param.get("jh").split(",");
		if(param.get("tab").equals("tab1")){
			if(param.get("smzq").equals("1")){
				int i=DateUtil.getDate(forma.format(new Date()),param.get("yxrq"));
				param.put("smzq", String.valueOf(i == 0 ? 1 : i));
			}
			
			// 查找群组名称是否存在，存在的话提示
			List<Object> qzmList = baseDAOIbatis.queryForList(sqlId + "qzmExsit",param);
			if(qzmList != null && qzmList.size() != 0){
				re.setSuccess(false);
				re.setMsg(I18nUtil.getText("common.qz.exist_qz", param.get(Constants.APP_LANG)));
				return re;
			}
			
			// 创建群组定义
			param.put("dwdm", param.get(Constants.UNIT_CODE));
			param.put("czyid", param.get(Constants.CURR_STAFFID));
			param.put("qzfl", qzlx.equals("zd") ? "01" : "02");
			baseDAOIbatis.insert(sqlId + "insQzdy",param);
			
		}else {
			
			param.put("qzfl", qzlx.equals("zd")? "01" : "02");
			baseDAOIbatis.insert(sqlId + "updateQzfl",param);
		}
		
		//获取批量全选标志
		String selectAllFlg = StringUtil.getString(param.get("selectAllFlg"));
		if (!StringUtil.isEmptyString(selectAllFlg) && selectAllFlg.toLowerCase().equals("true")) {
			String totalCount = param.get("totalCount"); 
			List<Object> resultList=null;
			if(qzlx.equals("zd")){ //终端
				String lx="zd";
				param = DatabaseUtil.nodeFilter(param, lx);
				resultList = baseDAOIbatis.getLimitResult(param, sqlId+"getZdxxQuery", "0", totalCount);
				jhs = new String[resultList.size()];
				for (int i = 0; i < resultList.size(); i++) {
					Map<String,Object> rowMap = (Map<String,Object>)resultList.get(i);
					//item 格式：终端局号:单位代码:终端规约类型:测量点:表计规约类型:表计局号
					jhs[i] = StringUtil.getValue(rowMap.get("ZDJH"));
				}
			}else if(qzlx.equals("bj")){//表计
				String lx = param.get("nodeType").equals("bj") || param.get("nodeType").equals("bjqz") ? "bj" : "cld";
				param = DatabaseUtil.nodeFilter(param, lx);
				resultList = baseDAOIbatis.getLimitResult(param, sqlId + "bjdaQuery", "0", totalCount);
				jhs = new String[resultList.size()];
				for (int i = 0; i < resultList.size(); i++) {
					Map<String,Object> rowMap = (Map<String,Object>)resultList.get(i);
					//item 格式：终端局号:单位代码:终端规约类型:测量点:表计规约类型:表计局号
					jhs[i] = StringUtil.getValue(rowMap.get("BJJH"));
				}
			}
			
		}
		// 创建群组明细
		List<Object> reLs = new ArrayList<Object>();
		for(String jh : jhs){
			Map<String, String> in = new HashMap<String, String>();
			in.put("qzid", StringUtil.getString(param.get("qzid")));
			in.put("qzlx", qzlx);
			in.put("jh", jh);
			if ("bj".equals(qzlx)) {
				in.put("bjjhIns", jh);
				in.put("zdjhIns", "");
			} else {
				in.put("zdjhIns", jh);
				in.put("bjjhIns", "");
			}
			
			//在加入之前查询数据库有没有，有的话则直接跳过，没有则插入
			Integer num = (Integer) baseDAOIbatis.queryForObject(sqlId + "queryQzMxExist", in, Integer.class);
			if(num > 0){
				continue;
			}
			
			reLs.add(in);
		}
		
		//批量插入数据库
		List<String> statementID = new ArrayList<String>();
		statementID.add(sqlId+ "insQzmx"); 
		baseDAOIbatis.executeBatchTransaction(statementID, reLs);
		
		re.setSuccess(true);
		re.setMsg(I18nUtil.getText("common.qz.add_succ", param.get(Constants.APP_LANG)));
		return re;
	}
	
	/**
	 * 获取序列
	 */
	public String getSeq(){
		return (String) baseDAOIbatis.queryForObject(sqlId + "getSeq", null, String.class);
	}
	
	/**
	 * 导入插入数据
	 */
	public void insqzsj(List<Object> reLs ){
		baseDAOIbatis.delete(sqlId+"delsj", null);
		List<String> statementID = new ArrayList<String>();
		statementID.add(sqlId+ "insQzsj"); 
		baseDAOIbatis.executeBatchTransaction(statementID, reLs);
	}
	
	/**
	 * 根据户号，户名查询
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryForYh(Map<String, Object> paramMap, String start,
			String limit, String dir, String sort, String isExcel){
	     return baseDAOIbatis.getExtGrid(paramMap, sqlId + "queryForYh",start,limit,dir,sort,isExcel);
	}
	
	/**
	 * 取得终端的信息
	 */
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "getZdxx",start,limit,dir,sort,isExcel);
	}
	
	/**
	 * 保存
	 */
/*	public ActionResult save(Map<String, String> param,Util util){
		ActionResult re =  new ActionResult(true,"");
		SimpleDateFormat forma= new SimpleDateFormat("yyyy-MM-dd");
		String qzlx = param.get("qzlx");
		 // 新增
		String[] jhs = param.get("jh").split(",");
		if(param.get("tab").equals("tab1")){
		if(param.get("smzq").equals("1")){
			int i=DateUtil.getDate(param.get("yxrq"),forma.format(new Date()));
			param.put("smzq", String.valueOf(i==0?1:i));
		}
		
		// 查找群组名称是否存在，存在的话提示
		List<Object> qzmList = baseDAOIbatis.queryForList(sqlId + "qzmExsit",param);
		if(qzmList != null && qzmList.size() != 0){
			re.setSuccess(false);
			re.setMsg(I18nUtil.getText("common.qz.exist_qz", param.get(Constants.APP_LANG)));
			return re;
		}
		// 创建群组定义
		param.put("dwdm", param.get(Constants.UNIT_CODE));
		param.put("czyid", param.get(Constants.CURR_STAFFID));
		param.put("qzfl", qzlx.equals("zd")?"01":"02");
		baseDAOIbatis.insert(sqlId + "insQzdy",param);
		}
		
		//获取批量全选标志
		String selectAllFlg = param.get("selectAllFlg");
		if (!StringUtil.isEmptyString(selectAllFlg) && selectAllFlg.toLowerCase().equals("true")) {
			String totalCount = param.get("totalCount"); 
			List<Object> resultList=null;
			if(qzlx.equals("zd")){
				String lx="zd";
				param = DatabaseUtil.nodeFilter(param, lx);
				resultList = baseDAOIbatis.getLimitResult(param, sqlId+"getZdxxQuery", "0", totalCount);
				jhs = new String[resultList.size()];
				for (int i = 0; i < resultList.size(); i++) {
					Map<String,Object> rowMap = (Map<String,Object>)resultList.get(i);
					//item 格式：终端局号:单位代码:终端规约类型:测量点:表计规约类型:表计局号
					jhs[i] = StringUtil.getValue(rowMap.get("ZDJH"));
				}
				
			}else if(qzlx.equals("bj")){
				String lx=param.get("nodeType").equals("bj")||param.get("nodeType").equals("bjqz")?"bj":"cld";
				param = DatabaseUtil.nodeFilter(param, lx);
				resultList = baseDAOIbatis.getLimitResult(param, sqlId+"bjdaQuery", "0", totalCount);
				jhs = new String[resultList.size()];
				for (int i = 0; i < resultList.size(); i++) {
					Map<String,Object> rowMap = (Map<String,Object>)resultList.get(i);
					//item 格式：终端局号:单位代码:终端规约类型:测量点:表计规约类型:表计局号
					jhs[i] = StringUtil.getValue(rowMap.get("BJJH"));
				}
			}
			
		}
		// 创建群组明细
		List<Object> reLs = new ArrayList<Object>();
		for(String jh:jhs){
				Map<String, String> in = new HashMap<String, String>();
				in.put("qzid", param.get("qzid"));
				in.put("qzlx", qzlx);
				in.put("jh", jh);
				if ("bj".equals(qzlx)) {
					in.put("bjjhIns", jh);
					in.put("zdjhIns", "");
				} else {
					in.put("zdjhIns", jh);
					in.put("bjjhIns", "");
				}
				reLs.add(in);
		}
		List<String> statementID = new ArrayList<String>();
		statementID.add(sqlId+ "insQzmx"); 
		baseDAOIbatis.executeBatchTransaction(statementID, reLs);
		re.setSuccess(true);
		re.setMsg(I18nUtil.getText("common.qz.add_succ", param.get(Constants.APP_LANG)));
		return re;
	}*/
	
	/**
	 * 群组更新操作
	 */
/*	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult(true,"Operate Success!");
		//新增群组
		if((menuId + Constants.ADDOPT).equals(czid)){
			// 创建群组定义
			param.put("dwdm", param.get(Constants.UNIT_CODE));
			param.put("czyid", param.get(Constants.CURR_STAFFID));
			
			baseDAOIbatis.insert("qz.insQzdy", param);
			re.setMsg("common.qzsz.edit.addSuccess",param.get(Constants.APP_LANG));
		}else if ((menuId + Constants.UPDOPT).equals(czid)) { // 更新群组信息
			baseDAOIbatis.update(sqlId + "saveQz", param);
			re.setMsg("common.qzsz.edit.updateSuccess",param.get(Constants.APP_LANG));
		} else if ((menuId + Constants.DELOPT).equals(czid)) { // 根据终端局号删除群组下用户，并更新群组总数信息
			//设备号 表计或者终端 非全选
			if (param.get("zdjhArray")!=null) {
				Map<String, Object> bjjhParam = new HashMap<String,Object>();
				Map<String, Object> zdjhParam = new HashMap<String,Object>();
				bjjhParam.putAll(param);
				zdjhParam.putAll(param);
				String[] zdjhArray = param.get("zdjhArray").split("@");
				
				List<String> zdjhList = new ArrayList<String>();
				List<String> bjjhList = new ArrayList<String>();
				
				//设备类型 sb/bj
				String[] sblxArray = param.get("sblxArray").split("@");
				for (int i = 0; i < zdjhArray.length; i++) {
					//表计
					if (sblxArray[i]!=null && sblxArray[i].equals("bj")) {
						bjjhList.add(zdjhArray[i]);
					}
					//表计
					if (sblxArray[i]!=null && sblxArray[i].equals("sb")) {
						zdjhList.add(zdjhArray[i]);
					}
				}
				
				if (bjjhList.size()>0) {
					bjjhParam.put("qzlx", "bj");
					bjjhParam.put("zdjh", bjjhList.toArray(new String[bjjhList.size()]));
					//删除群组中表计信息
					baseDAOIbatis.delete(sqlId + "delUser", bjjhParam);
				}
				if (zdjhList.size()>0) {
					zdjhParam.put("qzlx", "sb");
					zdjhParam.put("zdjh",zdjhList.toArray(new String[zdjhList.size()]));
					//删除群组中终端信息
					baseDAOIbatis.delete(sqlId + "delUser", zdjhParam);
				}
			}else{
				//全部删除
				baseDAOIbatis.delete(sqlId + "delUser", param);
			}
			
			re.setMsg("common.qzsz.edit.delSuccess",param.get(Constants.APP_LANG));
		}
		return re;
	}*/
	
	/**
	 * 获得当前所有费率方案
	 * @param 
	 * @return
	 */
	public List<Object> getAllTariff(){
		return baseDAOIbatis.queryForList(sqlId+"queryAllTariffPlan",null);
	}
	
	/**
	 * group setting, enable delete meters or terminals in a certain group when Edit the Group
	 * @param String czid, Map<String,String>
	 * @return ActionResult
	 * @date 2015.01.06
     * @author yaoj
	 */
	public ActionResult delBjsInGroup(String czid, Map<String, String> param){
		ActionResult res = new ActionResult(false,"Sorry,Delete Failed.");
		if(param.get("sfqx").equals("all")){	 //删除整个群组
			baseDAOIbatis.delete(sqlId + "delUser", param);
			res.setSuccess(true);
			res.setMsg("common.qzsz.edit.delSuccess",param.get(Constants.APP_LANG));
		}
		else if(param.get("zdjhArray")!=null) {
			Map<String, Object> bjjhParam = new HashMap<String,Object>();
			Map<String, Object> zdjhParam = new HashMap<String,Object>();
			bjjhParam.putAll(param);
			zdjhParam.putAll(param);
			
			String[] zdjhArray = param.get("zdjhArray").split("@");
			String[] sblxArray = param.get("sblxArray").split("@");
			
			List<String> zdjhList = new ArrayList<String>();
			List<String> bjjhList = new ArrayList<String>();
			
			//设备类型 sb/bj
			
			for (int i = 0; i < zdjhArray.length; i++) {
				//表计
				if (sblxArray[i]!=null && sblxArray[i].equals("bj")) {
					bjjhList.add(zdjhArray[i]);
				}
				//表计
				if (sblxArray[i]!=null && sblxArray[i].equals("sb")) {
					zdjhList.add(zdjhArray[i]);
				}
			}
			
			if (bjjhList.size()>0) {
				bjjhParam.put("qzlx", "bj");
				bjjhParam.put("zdjh", bjjhList.toArray(new String[bjjhList.size()]));
				//删除群组中表计信息
				baseDAOIbatis.delete(sqlId + "delUser", bjjhParam);
			}
			if (zdjhList.size()>0) {
				zdjhParam.put("qzlx", "sb");
				zdjhParam.put("zdjh",zdjhList.toArray(new String[zdjhList.size()]));
				//删除群组中终端信息
				baseDAOIbatis.delete(sqlId + "delUser", zdjhParam);
			}
			
			res.setSuccess(true);
			res.setMsg("common.qzsz.edit.delSuccess",param.get(Constants.APP_LANG));
		}
		
		return res;
	}
}
