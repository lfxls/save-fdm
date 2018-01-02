package cn.hexing.ami.web.action.report.insInfoReport;

import java.util.HashMap;
import java.util.Map;

import cn.hexing.ami.service.report.insInfoReport.InsDetailRepManagerInf;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.QueryInf;

public class InsDetailRepAction extends BaseAction implements QueryInf {

	
	private InsDetailRepManagerInf insDetailRepManager;
	private String woid,pid,tno,proType,bussType,opDate,dateType;
	
	@Override
	public String query() throws Exception {
		
		HashMap<String, Object> param=new HashMap<String, Object>();
		param.put(Constants.APP_LANG, getLang());
		if(!StringUtil.isEmptyString(woid)){
			param.put("woid", woid);
		}
		if(!StringUtil.isEmptyString(pid)){
			param.put("pid", pid);
		}
		if(!StringUtil.isEmptyString(tno)){
			param.put("tno", tno);
		}
		if(!StringUtil.isEmptyString(proType)){
			param.put("proType", proType);
		}
		if(!StringUtil.isEmptyString(bussType)){
			param.put("bussType", bussType);
		}
		if(!StringUtil.isEmptyString(opDate)){
			param.put("opDate", opDate);
		}
		if(!StringUtil.isEmptyString(dateType)){
			param.put("dateType", dateType);
		}
		Map<String, Object>re=  insDetailRepManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}

	@Override
	public String queryDetail() {
		return null;
	}
	
	public String getWoid() {
		return woid;
	}

	public String getPid() {
		return pid;
	}

	public String getTno() {
		return tno;
	}

	public void setWoid(String woid) {
		this.woid = woid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public InsDetailRepManagerInf getInsDetailRepManager() {
		return insDetailRepManager;
	}

	public void setInsDetailRepManager(InsDetailRepManagerInf insDetailRepManager) {
		this.insDetailRepManager = insDetailRepManager;
	}

	public String getProType() {
		return proType;
	}

	public String getBussType() {
		return bussType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	public String getOpDate() {
		return opDate;
	}

	public void setOpDate(String opDate) {
		this.opDate = opDate;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}



}
