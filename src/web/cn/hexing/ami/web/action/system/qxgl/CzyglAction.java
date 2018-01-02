package cn.hexing.ami.web.action.system.qxgl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.dao.common.pojo.AqCzy;
import cn.hexing.ami.dao.common.pojo.TreeCheckNode;
import cn.hexing.ami.dao.common.pojo.TreeNode;
import cn.hexing.ami.service.system.qxgl.CzyglManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;
import cn.hexing.ami.web.listener.AppEnv;

/**
 * @Description 操作员管理action
 * @author ycl
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-4-10
 * @version AMI3.0
 */
public class CzyglAction extends BaseAction implements DbWorksInf,QueryInf{

	private static final long serialVersionUID = 2063643156865721642L;

	CzyglManagerInf czyglManager = null;

	public void setCzyglManager(CzyglManagerInf czyglManager) {
		this.czyglManager = czyglManager;
	}

	private String czyid, bmid, bmmc, dwdm, xm;
	//安装队名
	private String tname;
	//安装队编号
	private String tno;
	private List<Object> czyzt,txfwsLs;
	private String operateType;
	private AqCzy czy;
	//页面部门选择方式 leaf 表示只能到根节点
	private String selType;
	//预付费模式
	private String prepayMode;
	//是否支持POS机
	
	//密码输入最大错误次数
	private String wrongPwdTimes;
	private String usePosMach;
	private String zt;
	private String posName;
	//售电点
	private String posId;
	//单位
	private String departId;
	//pos机编号
	private String posmid;
	
	
	
	public String getWrongPwdTimes() {
		return wrongPwdTimes;
	}

	public void setWrongPwdTimes(String wrongPwdTimes) {
		this.wrongPwdTimes = wrongPwdTimes;
	}

	public List<Object> getTxfwsLs() {
		return txfwsLs;
	}

	public void setTxfwsLs(List<Object> txfwsLs) {
		this.txfwsLs = txfwsLs;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getPrepayMode() {
		return prepayMode;
	}

	public void setPrepayMode(String prepayMode) {
		this.prepayMode = prepayMode;
	}

	public String getSelType() {
		return selType;
	}

	public void setSelType(String selType) {
		this.selType = selType;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public AqCzy getCzy() {
		return czy;
	}

	public void setCzy(AqCzy czy) {
		this.czy = czy;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getUsePosMach() {
		return usePosMach;
	}

	public void setUsePosMach(String usePosMach) {
		this.usePosMach = usePosMach;
	}

	public String getTno() {
		return tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String init() {
		HashMap<String,String> sysMap = (HashMap<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		prepayMode = StringUtil.isEmptyString(sysMap.get(Constants.PREPAYMODE))? "false":sysMap.get(Constants.PREPAYMODE);
		usePosMach = StringUtil.isEmptyString(sysMap.get(Constants.USEPOSMACH))? "false":sysMap.get(Constants.USEPOSMACH);
		wrongPwdTimes = StringUtil.isEmptyString(sysMap.get("wrongPwdTimes")) ? "3":sysMap.get("wrongPwdTimes");
		czyzt = CommonUtil.getCode("czyzt", getLang(), true);
		return SUCCESS;
	}
	
	/**
	 * 编辑操作员界面
	 * @return
	 */
	public String initEditCzy() {
		czyzt = CommonUtil.getCode("czyzt", getLang(), false);
		txfwsLs = CommonUtil.getCode("txfwsLs", getLang(), true);
		if (czyid != null) {
			String czyid1;
			try {
				czyid1 = URLDecoder.decode(czyid, "utf8");
				// 编辑状态
				if ("edit".equals(operateType)) {
					czy = czyglManager.initCzy(czyid1);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "editCzy";
	}
	
	/**
	 * 售电点授权界面
	 * @return
	 */
	public String initPosAuthorize() {
		return "posAuthorize";
	}
	
	/**
	 * pos机授权界面 
	 */
	public String initPosMachineAuthorize(){
		return "posMachineAuthorize";
	}
	
	/**
	 * 部门树界面
	 * @return
	 */
	public String initBmTree() {
		czyzt = CommonUtil.getCode("czyzt", getLang(), false);
		return "bmTree";
	}
	
	/**
	 * 获取部门树
	 * @return
	 */
	public String getJgTree() {
		String czyID = (String) session.getAttribute(Constants.CURR_STAFFID);
		List<TreeNode> tree = czyglManager.getJgTree(nodeId, nodeType, this.getUnitCode(), czyID, this.getBm(), this.getFwbj());
		responseJson(tree, false);
		return null;
	}
	
	/**
	 * 部门树界面
	 * @return
	 */
	public String initDwTree() {
		return "dwTree";
	}
	/**
	 * 获取单位树
	 * @return
	 */
	public String getDwTree() {
		if("root".equals(nodeId)) {
			nodeId = this.getUnitCode();
		}
		String czyid = (String)session.getAttribute(Constants.CURR_STAFFID);
		String bm = (String) session.getAttribute(Constants.CURR_DEPT);
		List<TreeNode> tree = czyglManager.getDwTree(nodeId, czyid, bm,this.getFwbj());
		responseJson(tree, false);
		return null;
	}

	public String getJsTree() {
		List<TreeCheckNode> tree = czyglManager.getJsTree(czyid);
		responseJson(tree, false);
		return null;
	}

	public String getQxFwTree() {
		List<TreeCheckNode> tree = czyglManager.getQxFwTree(czyid, dwdm);
		responseJson(tree, false);
		return null;
	}

	public ActionResult initCzy(Map<String, String> param, Util util) {
		ActionResult re = new ActionResult();
		String czyid = param.get("czyid");
		AqCzy op = czyglManager.initCzy(czyid);
		JSONObject jsonO = JSONObject.fromObject(op);
		String json = jsonO.toString();
		re.setMsg(json);
		return re;
	}

	public void dbLogger(String czid, String cznr, String czyId, String ip) {
		czyglManager.dbLogger(czid, cznr, czyId, ip);
	}

	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return czyglManager.doDbWorks(czid, param);
	}
	
	/**
	 * 查询用户列表
	 */
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dwdm", StringUtil.isEmptyString(dwdm)? this.getUnitCode():dwdm);
		param.put("deptID", bmid);
		param.put("tno", tno);
		param.put("optID", czyid);
		param.put("xm", xm);
		param.put("zt", zt);
		param.put(Constants.APP_LANG, this.getLang());
		HashMap<String,String> sysMap = (HashMap<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		wrongPwdTimes = StringUtil.isEmptyString(sysMap.get("wrongPwdTimes")) ? "3":sysMap.get("wrongPwdTimes");
	
		DatabaseUtil.fwdwFilter(param, super.getCzyid(), this.getFwbj(), this.getBm(), "T");
		Map<String, Object> m = czyglManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(m);
		return null;
	}
	
	/**
	 * 查询售电点列表
	 */
	public String queryDetail() {
		Map<String, Object> param = new HashMap<String, Object>();
		if(!StringUtil.isEmptyString(dwdm)){
			param.put("dwdm", dwdm.trim());
		}
		
		if(!StringUtil.isEmptyString(posName)){
			param.put("posName", posName.trim());
		}
		
		if(!StringUtil.isEmptyString(czyid)){
			param.put("czyid", czyid.trim());
		}
		
		Map<String, Object> m = czyglManager.queryDetail(param, start, limit, dir, sort, isExcel);
		responseGrid(m);
		return null;
	}
	
	/**
	 * 查询已授权售电点列表
	 */
	public String getAuthorizedPosList() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("czyid", czyid);
		Map<String, Object> m = czyglManager.getAuthorizedPosList(param, start, limit, dir, sort, isExcel);
		responseGrid(m);
		return null;
	}
	
	/**
	 * 查询未授权的pos机列表
	 * @return
	 */
	public String getPosMachineList(){
		Map<String,Object> param = new HashMap<String,Object>();
//		if(!StringUtil.isEmptyString(posId)){
//			param.put("posId", posId.trim());
//		}
//		
//		if(!StringUtil.isEmptyString(departId)){
//			param.put("departId", departId.trim());
//		}
		
		if(!StringUtil.isEmptyString(czyid)){
			param.put("czyid", czyid.trim());
		}
		
		if(!StringUtil.isEmptyString(posmid)){
			param.put("posmid", posmid.trim());
		}
		Map<String,Object> m = czyglManager.getPosMachineList(param, start, limit, dir, sort, isExcel);
		responseGrid(m);
		return null;
	}
	
	/**
	 * 查询已授权的pos机列表
	 * @return
	 */
	public String getAuthorizedPosMachineList(){
		Map<String,Object> param = new HashMap<String,Object>();
		if(!StringUtil.isEmptyString(czyid)){
			param.put("czyid", czyid.trim());
		}
		Map<String,Object> m = czyglManager.getAuthorizedPosMachineList(param, start, limit, dir, sort, isExcel);
		responseGrid(m);
		return null;
	}
	
	/**
	 * 编辑个人信息
	 * @return
	 */
	public String initPwd() {
		czy = czyglManager.initCzy(super.getCzyid());
		return "pwd";
	}

	public String getCzyid() {
		return czyid;
	}

	public void setCzyid(String czyid) {
		this.czyid = czyid;
	}

	public String getBmid() {
		return bmid;
	}

	public void setBmid(String bmid) {
		this.bmid = bmid;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getDwdm() {
		return dwdm;
	}

	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}

	public List<Object> getCzyzt() {
		return czyzt;
	}

	public void setCzyzt(List<Object> czyzt) {
		this.czyzt = czyzt;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getPosmid() {
		return posmid;
	}

	public void setPosmid(String posmid) {
		this.posmid = posmid;
	}

	public CzyglManagerInf getCzyglManager() {
		return czyglManager;
	}
}
