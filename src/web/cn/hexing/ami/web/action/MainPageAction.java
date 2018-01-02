package cn.hexing.ami.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.service.MainPageManager;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.StringUtil;

public class MainPageAction extends BaseAction {
	private static final long serialVersionUID = -2197976050945072831L;

	MainPageManager mainPageManager = null;
	private String czyId;
	private List<Object> re = null;
	private List<Object> list = null; // CAI
	private String deptSize;// list size

	public String getDeptSize() {
		return deptSize;
	}

	public void setDeptSize(String deptSize) {
		this.deptSize = deptSize;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public List<Object> getRe() {
		return re;
	}

	public void setRe(List<Object> re) {
		this.re = re;
	}

	public String getCzyId() {
		return czyId;
	}

	public void setCzyId(String czyId) {
		this.czyId = czyId;
	}

	public String init() {
		return SUCCESS;
	}

	public String cai() {

		list = mainPageManager.getDetailMsg(); // 返回迭代数据
		deptSize = String.valueOf(list.size());
		return "CaiMain";
	}

	/**
	 * @desc 将待迭代集合整理，返回js
	 * @param param
	 * @param util
	 */
	public ActionResult getDetailMsg(Map<String, String> param, Util util) {
		ActionResult res = new ActionResult();
		List<Object> ls_total = mainPageManager.getDetailMsg();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ls_total.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) ls_total.get(i);
			if (i < ls_total.size() - 1) {
				sb.append(map.get("DWDM").toString() + ",")
						.append(map.get("DWMC").toString() + ",")
						.append(map.get("TOTAL").toString() + ",")
						.append(map.get("ALERT").toString()).append(";");
			} else {
				sb.append(map.get("DWDM").toString() + ",")
						.append(map.get("DWMC").toString() + ",")
						.append(map.get("TOTAL").toString() + ",")
						.append(map.get("ALERT").toString());
			}
		}
		res.setSuccess(true);
		res.setMsg(sb.toString());
		return res;
	}

	public ActionResult getDatj(Map<String, String> param, Util util) {
		return mainPageManager.getDatj(param, util);
	}

	public ActionResult getTxzxl(Map<String, String> param, Util util) {
		return mainPageManager.getTxzxl(param, util);
	}

	public ActionResult getCbcgl(Map<String, String> param, Util util) {
		return mainPageManager.getCbcgl(param, util);
	}

	public ActionResult getYctj(Map<String, String> param, Util util) {
		return mainPageManager.getYctj(param, util);
	}

	public String initMdM() {
		nodeId = StringUtil.isEmptyString(nodeId) ? getUnitCode() : nodeId;
		nodeType = StringUtil.isEmptyString(nodeType) ? "dw" : nodeType;
		czyId = StringUtil.isEmptyString(czyId) ? getCzyid() : czyId;
		re = getMDMDatj(this.getUnitCode(), this.getLang());
		return "MdMsy";
	}

	public List<Object> getMDMDatj(String dwdm, String lang) {
		return mainPageManager.getMDMDatj(dwdm, lang);
	}

	public ActionResult getMDMCbcgl(Map<String, String> param, Util util) {
		return mainPageManager.getMDMCbcgl(param, util);
	}

	public ActionResult getMDMYctj(Map<String, String> param, Util util) {
		return mainPageManager.getMDMYctj(param, util);
	}

	public ActionResult getMDMXlxs(Map<String, String> param, Util util) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fwbj", param.get("fwbj"));
		params.put("nodeId", param.get("nodeId"));
		params.put("bmid", param.get("bmid"));
		params.put("nodeType", param.get("nodeType"));
		params.put("main", param.get("main"));
		params.put("rq", DateUtil.getToday());
		params.put(Constants.APP_LANG, param.get(Constants.APP_LANG));
		DatabaseUtil.nodeFilter(params, param.get("nodeId"),
				param.get("nodeType"), param.get("nodeDwdm"),
				param.get(Constants.CURR_STAFFID),
				param.get(Constants.CURR_RIGTH),
				param.get(Constants.CURR_DEPT), "tj");
		return mainPageManager.getMDMXlxs(params, util);
	}

	public MainPageManager getMainPageManager() {
		return mainPageManager;
	}

	public void setMainPageManager(MainPageManager mainPageManager) {
		this.mainPageManager = mainPageManager;
	}

	// ------------------------------------
	public String initMdc() {
		return "MdCsy";
	}

	public ActionResult getMDCSbsl(Map<String, String> param, Util util) {
		return mainPageManager.getMDCSbsl(param, util);
	}

	public ActionResult getMDCCbcgl(Map<String, String> param, Util util) {
		return mainPageManager.getMDCCbcgl(param, util);
	}

	public ActionResult getMDCGjtj(Map<String, String> param, Util util) {
		return mainPageManager.getMDCGjtj(param, util);
	}

}
