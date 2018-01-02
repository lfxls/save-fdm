package cn.hexing.ami.web.action.main.hhuService;

import java.util.Map;

import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;

/**
 * @Description HHU数据更新服务Action
 * @author zhaoyy
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time 2016-4-12
 * @version FDM2.0
 */
public class DataUpdateAction extends BaseAction implements QueryInf,
		DbWorksInf {
	/**
	 * 
	 */
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public String query() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryDetail() {
		// TODO Auto-generated method stub
		return null;
	}

}
