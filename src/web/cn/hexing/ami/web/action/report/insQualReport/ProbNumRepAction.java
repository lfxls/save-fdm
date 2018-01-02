package cn.hexing.ami.web.action.report.insQualReport;

import java.util.HashMap;
import java.util.Map;

import cn.hexing.ami.service.report.insQualReport.ProbNumRepManagerInf;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.QueryInf;

public class ProbNumRepAction extends BaseAction implements QueryInf {
	private ProbNumRepManagerInf probNumRepManager;
	private String tno,failNum,abnorNum,rst,pid,tiid,verid;
	@Override
	public String init() {
		return SUCCESS;
	}
	public String initDetail() {
		return "Detail";
	}
	public String initTestDetail(){
		return "testDetail";
	}
	public String queryTestDetail() {
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("appLang", getLang());
		if(!StringUtil.isEmptyString(pid)){
			map.put("pid", pid);
		}
		if(!StringUtil.isEmptyString(tiid)){
			map.put("tiid", tiid);
		}
		if(!StringUtil.isEmptyString(verid)){
			map.put("verid", verid);
		}
		Map<String, Object> re=probNumRepManager.queryTestDetail(map, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	@Override
	public String query() throws Exception {
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("appLang", getLang());
		if(!StringUtil.isEmptyString(tno)){
			map.put("tno", tno);
		}
		if(!StringUtil.isEmptyString(failNum)){
			map.put("failNum", failNum);
		}
		if(!StringUtil.isEmptyString(abnorNum)){
			map.put("abnorNum", abnorNum);
		}
		Map<String, Object> re=probNumRepManager.query(map, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	@Override
	public String queryDetail() {
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("appLang", getLang());
		if(!StringUtil.isEmptyString(tno)){
			map.put("tno", tno);
		}
		if(!StringUtil.isEmptyString(rst)){
			map.put("rst", rst);
		}
		Map<String, Object> re=probNumRepManager.queryDetail(map, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	public ProbNumRepManagerInf getProbNumRepManager() {
		return probNumRepManager;
	}
	public String getTno() {
		return tno;
	}
	public String getFailNum() {
		return failNum;
	}
	public void setProbNumRepManager(ProbNumRepManagerInf probNumRepManager) {
		this.probNumRepManager = probNumRepManager;
	}
	public void setTno(String tno) {
		this.tno = tno;
	}
	public void setFailNum(String failNum) {
		this.failNum = failNum;
	}
	public String getAbnorNum() {
		return abnorNum;
	}
	public void setAbnorNum(String abnorNum) {
		this.abnorNum = abnorNum;
	}
	public String getRst() {
		return rst;
	}
	public void setRst(String rst) {
		this.rst = rst;
	}
	public String getPid() {
		return pid;
	}
	public String getTiid() {
		return tiid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public void setTiid(String tiid) {
		this.tiid = tiid;
	}
	public String getVerid() {
		return verid;
	}
	public void setVerid(String verid) {
		this.verid = verid;
	}
	
	
}
