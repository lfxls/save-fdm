package cn.hexing.ami.web.action.report.insQualReport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.service.report.insQualReport.InsFailRepManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.QueryInf;

public class InsFailRepAction extends BaseAction implements QueryInf{

	private InsFailRepManagerInf  insFailRepManager;
	private String tno,tiid,level;
	private List<Object>  tiLs,levelLs;
	
	
	public String init(){
		levelLs=CommonUtil.getCode("level",getLang(), true);
		tiLs=insFailRepManager.getTestItem(getLang());
		
		return SUCCESS;
	}
	@Override
	public String query() throws Exception {
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("appLang", getLang());
		if(!StringUtil.isEmptyString(tno)){
			map.put("tno", tno);
		}
		if(!StringUtil.isEmptyString(tiid)){
			map.put("tiid", tiid);
		}
		if(!StringUtil.isEmptyString(level)){
			map.put("level", level);
		}
		Map<String, Object> re=insFailRepManager.query(map, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	public ActionResult getChart(Map<String, String> params,Util util) {
		ActionResult res = new ActionResult();
		if(!StringUtil.isEmptyString(tno)){
			params.put("tno", tno);
		}
		String result =  insFailRepManager.getChart(params);	
		res.setSuccess(true);
		res.setMsg(result);
		return res;
	}

	@Override
	public String queryDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	public InsFailRepManagerInf getInsFailRepManager() {
		return insFailRepManager;
	}

	public void setInsFailRepManager(InsFailRepManagerInf insFailRepManager) {
		this.insFailRepManager = insFailRepManager;
	}

	public String getTno() {
		return tno;
	}

	public String getTiid() {
		return tiid;
	}

	public String getLevel() {
		return level;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public void setTiid(String tiid) {
		this.tiid = tiid;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	public List<Object> getTiLs() {
		return tiLs;
	}
	public List<Object> getLevelLs() {
		return levelLs;
	}
	public void setTiLs(List<Object> tiLs) {
		this.tiLs = tiLs;
	}
	public void setLevelLs(List<Object> levelLs) {
		this.levelLs = levelLs;
	}

}
