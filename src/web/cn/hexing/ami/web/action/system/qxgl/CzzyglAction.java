package cn.hexing.ami.web.action.system.qxgl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.service.system.qxgl.CzzyglManagerInf;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.QueryInf;
 
/**
 * @Description 操作资源管理action
 * @author ycl
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-4-10
 * @version AMI3.0
 */
public class CzzyglAction extends BaseAction implements QueryInf {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6778160518532547645L;

	CzzyglManagerInf czzyglManager = null;

	public void setCzzyglManager(CzzyglManagerInf czzyglManager) {
		this.czzyglManager = czzyglManager;
	}

	public String query() throws Exception { 
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		Map<String, Object> m = czzyglManager.query(param, start, limit, dir,
				sort, isExcel);
		responseGrid(m, getExcelHead(), "", "");
		return null;
	}

	private String czlbid, sjcdbm;

	public String getCzTree() {
		List<Map<String, Object>> ls = czzyglManager.getCzTree(czlbid, sjcdbm,
				this.getLang());
		responseJson(ls, false);
		return null;
	}

	public List<String> getExcelHead() {
		// TODO Auto-generated method stub
		return null;
	}

	public String queryDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCzlbid() {
		return czlbid;
	}

	public void setCzlbid(String czlbid) {
		this.czlbid = czlbid;
	}

	public String getSjcdbm() {
		return sjcdbm;
	}

	public void setSjcdbm(String sjcdbm) {
		this.sjcdbm = sjcdbm;
	}

}
