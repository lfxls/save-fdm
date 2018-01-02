package cn.hexing.ami.service.system.qyjggl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.TreeCheckNode;
import cn.hexing.ami.dao.common.pojo.TreeNode;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;

/**
 * @Description 机构管理manager
 * @author  ycl
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-4-10
 * @version AMI3.0
 */
public class JgglManager implements JgglManagerInf {

	private static String sqlId = "jggl.";
	private static String menuId = "51100";
	BaseDAOIbatis baseDAOIbatis = null;

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	/**
	 * 单位树
	 * 
	 * @param sjdwdm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> getDwTree(String sjdwdm, String nodeType) {
		List<TreeNode> tree = new ArrayList<TreeNode>();
		if("jg".equals(nodeType)) { //机构节点
			List<?> ls = baseDAOIbatis
			.queryForList(sqlId + "getJgBySjdwdm", sjdwdm);
			createJgNode(ls, tree);
		} else {
			List<?> ls = baseDAOIbatis
			.queryForList(sqlId + "getDwBySjdwdm", sjdwdm);
			for (int i = 0, len = ls.size(); i < len; i++) {
				Map<String, Object> obj = (Map<String, Object>) ls.get(i);
				String dwdm = StringUtil.getValue(obj.get("DWDM"));
//				String sjdw = StringUtil.getValue(obj.get("SJDWDM"));
				String jb = StringUtil.getValue(obj.get("JB"));
				String dwmc = StringUtil.getValue(obj.get("DWMC"));
				TreeNode node = new TreeNode(dwdm, dwmc, jb, false);
				node.setIconCls("dw");
				node.setInfo(dwdm);
				tree.add(node);
			}
			ls.clear();
			ls = baseDAOIbatis
			.queryForList(sqlId + "getJgBySjdwdm", sjdwdm);
			createJgNode(ls, tree);
		}
		return tree;
	}

	@SuppressWarnings("unchecked")
	private void createJgNode(List<?> ls, List<TreeNode> tree) {
		for (int i = 0, len = ls.size(); i < len; i++) {
			Map<String, Object> obj = (Map<String, Object>) ls.get(i);
			String jgid = StringUtil.getValue(obj.get("BMID"));
			String dwdm = StringUtil.getValue(obj.get("DWDM"));
			String jgmc = StringUtil.getValue(obj.get("BMMC"));
			TreeNode node = new TreeNode(jgid, jgmc, "jg", true);
			node.setIconCls("jg");
			node.setInfo(dwdm);
			tree.add(node);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TreeCheckNode> getQxFwTree(String dwdm, String bmid) {
		List<TreeCheckNode> tree = new ArrayList<TreeCheckNode>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dwdm", dwdm);
		param.put("bmid", bmid);
		param.put("root", "true");
		List<?> root = baseDAOIbatis.queryForList(sqlId + "getQxFwTree", param);
		param.put("root", "false");
		List<?> leafs = baseDAOIbatis
				.queryForList(sqlId + "getQxFwTree", param);
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
		tree.add(node);
		return tree;
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
		String type = param.get("type");
		if("dw".equals(type)){
			if((menuId + Constants.ADDOPT).equals(czid)) { 
				//新增单位
				re = addDw(param);
			} else if((menuId + Constants.UPDOPT).equals(czid)) {
				//修改单位
				re = updateDw(param);
			} else if((menuId + Constants.DELOPT).equals(czid)){
				//删除单位
				re = deleteDw(param);
			}
		}else if("jg".equals(type)){
			if((menuId + Constants.ADDOPT).equals(czid)) { 
				//新增部门
				re = addJg(param);
			} else if((menuId + Constants.UPDOPT).equals(czid)){ 
				//更新部门
				re = updateJg(param);
			} else if((menuId + Constants.DELOPT).equals(czid)){ 
				//删除部门
				re = deleteJg(param);
			} else if((menuId + "04").equals(czid)){ 
				//修改权限
				re = updateQx(param);
			}
		}
		return re;
	}
	
	/**
	 * 新增单位
	 * @param param
	 * @return
	 */
	private ActionResult addDw(Map<String, String> param) {
		ActionResult re = new ActionResult();
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "existsDwmc", param);
		//判断当前单位名称是否已经存在
		if(ls!=null && ls.size() > 0) {
			//已经存在，给出提示
			re.setSuccess(false);
			re.setMsg("sysModule.qyjggl.jggl.existsDwName", param.get(Constants.APP_LANG));
		} else {
			//不存在，新增机构
			//获取上级 的单位级别，来算出下级的单位级别 
			String sjdwjb = param.get("sjdwjb");
			String dwjb = "0"+(Integer.parseInt(sjdwjb)+1)+"";
			param.put("dwjb", dwjb);
			baseDAOIbatis.insert(sqlId + "insDw", param);
			
			//部门范围权限表更新，所有上级部门对应该单位代码的权限关联信息都需要更新到qx_bmfwqx
			ls = baseDAOIbatis.queryForList(sqlId+"getAllParentBm", param);
			if (ls!=null && ls.size()>0) {
				List<String> sqlIds = new ArrayList<String>();
				List<Object> paramsLs = new ArrayList<Object>();
				for (int i = 0; i < ls.size(); i++) {
					Map<String, Object> o = new HashMap<String, Object>();
					o.put("dwdm", param.get("dwdm"));
					o.put("dwjb",dwjb);
					o.put("bmid", ((Map<String, Object>)ls.get(i)).get("BMID"));
					paramsLs.add(o);
					sqlIds.add(sqlId+"insBmfwqx");
				}
				baseDAOIbatis.executeBatchMultiSql(sqlIds, paramsLs);
			}
			
			//如果是新增小区级单位(06)则做虚拟线路、台区、变压器处理 zhouh
			/*if(dwjb.equals("06")){
//				String dwdm06 = StringUtil.getValue(param.get("dwdm"));//新的06级（小区级单位代码）
				//插入线路
				baseDAOIbatis.insert(sqlId + "insXnxl", param);
				
				//插入台区
				baseDAOIbatis.insert(sqlId + "insXntq", param);
				
				//插入线路台区关系
				baseDAOIbatis.insert(sqlId + "insXnxltq", param);
				
				//插入变压器
				baseDAOIbatis.insert(sqlId + "insXnbyq", param);
			}*/
			
			re.setSuccess(true);
			re.setMsg("sysModule.qyjggl.jggl.addDw_success", param.get(Constants.APP_LANG));
		}
		return re;
	}
	
	/**
	 * 修改单位
	 * @param param
	 * @return
	 */
	private ActionResult updateDw(Map<String, String> param){
		ActionResult re = new ActionResult();
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "existsDwmc", param);
		//判断当前单位名称是否已经存在
		if(ls.size() > 0) {
			//已经存在，给出提示
			re.setSuccess(false);
			re.setMsg("sysModule.qyjggl.jggl.existsDwName", param.get(Constants.APP_LANG));
		} else {
			//不存在，新增机构
			baseDAOIbatis.insert(sqlId + "updDw", param);
			
			Map<String, Object> qyMap = (Map<String, Object>) baseDAOIbatis.queryForObject(sqlId + "quyQxQy", param, HashMap.class);
			/*if(qyMap != null){
				//如果是06级单位保存则校验看有没有虚拟电网存在
				String dwjb = StringUtil.getValue(qyMap.get("JB")); 
				if(dwjb.equals("06")){
					Integer num = (Integer) baseDAOIbatis.queryForObject(sqlId + "getXnDwByDwdm", param, Integer.class);
					if(num <= 0){
						
						//插入线路
						baseDAOIbatis.insert(sqlId + "insXnxl", param);
						
						//插入台区
						baseDAOIbatis.insert(sqlId + "insXntq", param);
						
						//插入线路台区关系
						baseDAOIbatis.insert(sqlId + "insXnxltq", param);
						
						//插入变压器
						baseDAOIbatis.insert(sqlId + "insXnbyq", param);
					}
				}
			}*/
			
			re.setSuccess(true);
			re.setMsg("sysModule.qyjggl.jggl.updDw_success",param.get(Constants.APP_LANG));
		}
		return re;
	}
	
	/**
	 * 删除单位
	 * @param param
	 * @return
	 */
	private ActionResult deleteDw(Map<String, String> param){
		ActionResult re = new ActionResult();
		//判断改单位下面是否有下级单位和下级部门
		Integer xjBm = (Integer) baseDAOIbatis.queryForObject(sqlId + "existsXjBm", param, Integer.class);
		Integer xjDw = (Integer) baseDAOIbatis.queryForObject(sqlId + "existsXjDw", param, Integer.class);
		if(xjBm > 0 || xjDw > 0){ //存在下级单位和部门
			re.setSuccess(false);
			re.setMsg("sysModule.qyjggl.jggl.deldw_error",param.get(Constants.APP_LANG));
		}else{
			//删除部门
			baseDAOIbatis.delete(sqlId+"delDw", param);
			
			//删除单位部门权限
			baseDAOIbatis.delete(sqlId+"delDwbmqx", param);
			
			re.setSuccess(true);
			re.setMsg("sysModule.qyjggl.jggl.deldw_success",param.get(Constants.APP_LANG));
		}
		return re;
	}

	/**
	 * 新增机构
	 * @param param
	 * @return
	 */
	private ActionResult addJg(Map<String, String> param){
		ActionResult re = new ActionResult();
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "existsJgmc", param);
		//判断当前机构名称是否已经存在
		if(ls.size() > 0) {
			//已经存在，给出提示
			re.setSuccess(false);
			re.setMsg("sysModule.qyjggl.jggl.existsName", param.get(Constants.APP_LANG));
		} else {
			//不存在，新增机构
			baseDAOIbatis.insert(sqlId + "insBm", param);
			//插入默认机构访问单位（控制访问权限）
			baseDAOIbatis.insert(sqlId + "insDefaultFwdw", param);
			re.setSuccess(true);
			re.setMsg("sysModule.qyjggl.jggl.addbm_success", param.get(Constants.APP_LANG));
		}
		return re;
	}
	
	/**
	 * 修改机构权限
	 * @param param
	 * @return
	 */
	private ActionResult updateQx(Map<String, String> param){
		ActionResult re = new ActionResult();
		String bmid = param.get("bmid");
		String dwdm = param.get("dwdm");
		String fwdws = param.get("dwdms");
		Map<String, Object> o = new HashMap<String, Object>();
		o.put("dwdm", dwdm);
		o.put("bmid", bmid);
		o.put("dwdms", param.get("dwdms"));
		List<String> sqls = new ArrayList<String>();
		sqls.add(sqlId + "delFwdw");
		//覆盖
		if("1".equals(param.get("cover"))) { 
			sqls.add(sqlId + "delCzyFwdw");
		}
		sqls.add(sqlId + "insFwdwRoot");//先存上级节点
		if(!StringUtil.isEmptyString(fwdws)) {
			sqls.add(sqlId + "insFwdw");//递归存下级节点
			o.put("fwdws", fwdws.split(","));
		}
		baseDAOIbatis.executeBatchTransaction(sqls, o);
		re.setSuccess(true);
		re.setMsg("sysModule.qyjggl.jggl.updbmqx_success", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * 修改机构
	 * @param param
	 * @return
	 */
	private ActionResult updateJg(Map<String, String> param){
		ActionResult re = new ActionResult();
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "existsJgmc", param);
		//判断当前机构名称是否已经存在
		if(ls.size() > 0) {
			//已经存在，给出提示
			re.setSuccess(false);
			re.setMsg("sysModule.qyjggl.jggl.existsName", param.get(Constants.APP_LANG));
		} else {
			//不存在，修改机构信息
			baseDAOIbatis.update(sqlId+"updBm", param);
			re.setSuccess(true);
			re.setMsg("sysModule.qyjggl.jggl.updbm_success",param.get(Constants.APP_LANG));
		}
		return re;
	}
	
	/**
	 * 删除机构
	 * @param param
	 * @return
	 */
	private ActionResult deleteJg(Map<String, String> param){
		ActionResult re = new ActionResult();
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "existsCzy", param);
		if(ls.size() > 0) { //存在操作员不能删除
			re.setSuccess(false);
			re.setMsg("sysModule.qyjggl.jggl.del_hasCzy", param.get(Constants.APP_LANG));
		} else {
			ls = baseDAOIbatis.queryForList(sqlId + "getJgBySjdwdm", param.get("bmid"));
			if(ls.size() > 0) {  //存在部门不能删除
				re.setSuccess(false);
				re.setMsg("sysModule.qyjggl.jggl.del_hasBm", param.get(Constants.APP_LANG));
			} else {
				List<String> sqls = new ArrayList<String>();
				sqls.add(sqlId + "delFwdw");
				sqls.add(sqlId + "delBm");
				baseDAOIbatis.executeBatchTransaction(sqls, param);
				re.setSuccess(true);
				re.setMsg("sysModule.qyjggl.jggl.delbm_success", param.get(Constants.APP_LANG));
			}
		}
		return re;
	}
	
	
	/**
	 * 根据区域代码模糊查询区域信息
	 * @param dwdm
	 * @return
	 */
	public Map<String, Object> getQyList(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel){
		return baseDAOIbatis.getExtGrid(param, sqlId + "qy", start, limit,
				dir, sort, isExcel);
	}
	
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "Xjjg", start, limit,
				dir, sort, isExcel);
	}

	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		// TODO Auto-generated method stub
		return null;
	}
	public ActionResult getPf(Map<String,String> param){
		ActionResult re=new ActionResult();
		List<Object> ls=baseDAOIbatis.queryForList(sqlId+"getPf", param);
		if (ls != null && ls.size() > 0) { 
			re.setSuccess(false);
		
			return re;
		}
		re.setSuccess(true);
		return re;
	}
}
