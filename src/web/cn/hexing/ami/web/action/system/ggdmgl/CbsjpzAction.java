package cn.hexing.ami.web.action.system.ggdmgl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.service.system.ggdmgl.CbsjpzManagerInf;
import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.QueryInf;
import cn.hexing.ami.web.listener.AppEnv;

/** 
 * @Description  抄表数据配置
 * @author  panc
 * @Copyright 2013 hexing Inc. All rights reserved
 * @time：2013-12-20
 * @version AMI3.0 
 */
public class CbsjpzAction extends BaseAction implements QueryInf, DbWorksInf {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2466785353080160391L;
	CbsjpzManagerInf cbsjpzManager = null;
	
	private String dlx, xlx, txlx, xzyy; // 大类型、小类型、选择语言
	private List<Object> dlxList, xlxList, txlxList, ctptList = null;
	private List<Object> xzyyList, sfxsList = null;
	
	private String sjx, sjxmc;
	private String xlid, xlmc, pxbm;
	private String txid, txmc;
	private String dw, kd, fmt, ctpt, sfxs; 

	public String init() {
		Map<String, Object> param = new HashMap<String, Object>();
		dlxList = CommonUtil.getCode("cbsjpz", this.getLang(), false);
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
		String[] config = sysMap.get("lmrOperateConfigure").split(",");	
		List<Object> rqlxLs = new ArrayList<Object>();
		for (int i = 0; i < dlxList.size(); i++) {
			Map<String,Object> rowMap = (Map<String,Object> )dlxList.get(i);
			String bm = String.valueOf(rowMap.get("BM"));
			if(bm.equals("01")){ //日
				for(int j = 0; j < config.length; j++){
					if(StringUtil.getString(config[j]).trim().equals("01")){ //01:日冻结
						rqlxLs.add(rowMap);
					}else{
						continue;
					}
				}
				
			}else if(bm.equals("02")){//月
				for(int j = 0; j < config.length; j++){
					if(StringUtil.getString(config[j]).trim().equals("02")){ //02:月冻结
						rqlxLs.add(rowMap);
					}else{
						continue;
					}
				}
				
			}else if(bm.equals("05")){//抄表日
				for(int j = 0; j < config.length; j++){
					if(StringUtil.getString(config[j]).trim().equals("05")){ //05:抄表日
						rqlxLs.add(rowMap);
					}else{
						continue;
					}
				}
				
			}else{
				continue;
			}
			
		}
		dlxList = rqlxLs; //整个list重新赋值

		if(StringUtil.isEmptyString(dlx)){
			if(null != dlxList && dlxList.size() > 0){
				dlx = (String)((Map<String, Object>)dlxList.get(0)).get("BM");
			}
		}
		
		param.put("dlx", dlx);
		param.put(Constants.APP_LANG, this.getLang());
		xlxList = cbsjpzManager.queryXlxDrop(param);
		if(StringUtil.isEmptyString(xlx)){
			if(null != xlxList && xlxList.size() > 0){
				xlx = (String)((Map<String, Object>)xlxList.get(0)).get("BM");
			}
		}
		return SUCCESS;
	}
	
	public String query() throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("dlx", dlx);
		param.put("xlx", xlx);
		param.put(Constants.APP_LANG, this.getLang());
		Map<String, Object> re = cbsjpzManager.query(param, start, limit, dir,
				sort, isExcel);
		responseGrid(re);
		return null;
	}

	public String initDetail(){
		return "selSjx";
	}
	
	public String queryDetail() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("dlx", dlx);
		param.put(Constants.APP_LANG, this.getLang());
		Map<String, Object> re = cbsjpzManager.queryDetail(param, start, limit, dir,
				sort, isExcel);
		responseGrid(re);		
		return null;
	}
	
	/**
	 * 配置
	 * @return
	 */
	public String initCbpz() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("dlx", dlx);
		param.put("xlx", xlx);
		param.put("sjx", sjx);
		param.put(Constants.APP_LANG, getLang());
		param.put("rwlx", "01");

		sfxsList = CommonUtil.getCode("sfxm", getLang(), false);
		ctptList = CommonUtil.getCode("cctpt", getLang(), false);
		txlxList = cbsjpzManager.queryTxDrop(param);
		Map<String,Object> ret = cbsjpzManager.getObject(param);
		if(null != ret){
			sjx = (String)ret.get("SJX");
			sjxmc = (String)ret.get("SJXMC");
			dw = StringUtil.getValue(ret.get("DW"));
			kd = StringUtil.getValue(ret.get("WIDTH"));
			fmt = StringUtil.getValue(ret.get("FORMAT"));
			ctpt = StringUtil.getValue(ret.get("XCTPT"));
			sfxs = StringUtil.getValue(ret.get("SFXS"));
			pxbm = StringUtil.getValue(ret.get("PXBM"));
			txid = StringUtil.getValue(ret.get("TXID"));
		} else {
			kd = "120";
		}
		
		return "cbpz";
	}	
	
	/**
	 * 数据项字段
	 * @return
	 */
	public String initSjx() {
		dlxList = CommonUtil.getCode("cbsjpz", getLang(), false);
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
		String[] config = sysMap.get("lmrOperateConfigure").split(",");	
		List<Object> rqlxLs = new ArrayList<Object>();
		for (int i = 0; i < dlxList.size(); i++) {
			Map<String,Object> rowMap = (Map<String,Object> )dlxList.get(i);
			String bm = String.valueOf(rowMap.get("BM"));
			if(bm.equals("01")){ //日
				for(int j = 0; j < config.length; j++){
					if(StringUtil.getString(config[j]).trim().equals("01")){ //01:日冻结
						rqlxLs.add(rowMap);
					}else{
						continue;
					}
				}
				
			}else if(bm.equals("02")){//月
				for(int j = 0; j < config.length; j++){
					if(StringUtil.getString(config[j]).trim().equals("02")){ //02:月冻结
						rqlxLs.add(rowMap);
					}else{
						continue;
					}
				}
				
			}else if(bm.equals("05")){//抄表日
				for(int j = 0; j < config.length; j++){
					if(StringUtil.getString(config[j]).trim().equals("05")){ //05:抄表日
						rqlxLs.add(rowMap);
					}else{
						continue;
					}
				}
				
			}else{
				continue;
			}
			
		}
		dlxList = rqlxLs; //整个list重新赋值
		return "sjxgl";
	}
	
	public String querySjx() throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("dlx", dlx);
		param.put(Constants.APP_LANG, getLang());
		Map<String, Object> re = cbsjpzManager.querySjx(param, start, limit, dir,
				sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	public String editSjx() {
		Map<String,Object> param = new HashMap<String,Object>();
		xzyyList = CommonUtil.getCode("dlyy", "ALL", false);
		param.put("dlx", dlx);
		param.put("sjx", sjx);
		param.put("rwlx", "02");
		param.put(Constants.APP_LANG, getLang());
		
		Map<String,Object> ret = cbsjpzManager.getObject(param);
		if(null != ret){
			sjx = (String)ret.get("SJX");
			sjxmc = (String)ret.get("SJXMC");
			xzyy = (String)ret.get("LANG");
		}
		return "sjx";
	}
	
	/**
	 * 小类型
	 * @return
	 */
	public String initXlx() {
		dlxList = CommonUtil.getCode("cbsjpz", this.getLang(), false);
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
		String[] config = sysMap.get("lmrOperateConfigure").split(",");	
		List<Object> rqlxLs = new ArrayList<Object>();
		for (int i = 0; i < dlxList.size(); i++) {
			Map<String,Object> rowMap = (Map<String,Object> )dlxList.get(i);
			String bm = String.valueOf(rowMap.get("BM"));
			if(bm.equals("01")){ //日
				for(int j = 0; j < config.length; j++){
					if(StringUtil.getString(config[j]).trim().equals("01")){ //01:日冻结
						rqlxLs.add(rowMap);
					}else{
						continue;
					}
				}
				
			}else if(bm.equals("02")){//月
				for(int j = 0; j < config.length; j++){
					if(StringUtil.getString(config[j]).trim().equals("02")){ //02:月冻结
						rqlxLs.add(rowMap);
					}else{
						continue;
					}
				}
				
			}else if(bm.equals("05")){//抄表日
				for(int j = 0; j < config.length; j++){
					if(StringUtil.getString(config[j]).trim().equals("05")){ //05:抄表日
						rqlxLs.add(rowMap);
					}else{
						continue;
					}
				}
				
			}else{
				continue;
			}
			
		}
		dlxList = rqlxLs; //整个list重新赋值
		return "xlxgl";
	}
	
	public String queryXlx() throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("dlx", dlx);
		param.put("xlid", xlid);
		param.put(Constants.APP_LANG, this.getLang());
		Map<String, Object> re = cbsjpzManager.queryXlx(param, start, limit, dir,
				sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	public String editXlx() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("dlx", dlx);
		param.put("xlid", xlid);
		param.put("rwlx", "03");
		param.put(Constants.APP_LANG, this.getLang());
		xzyyList = CommonUtil.getCode("dlyy", "ALL", false);
		Map<String,Object> ret = cbsjpzManager.getObject(param);
		if(null != ret){
			xlid = (String)ret.get("XLID");
			xlmc = (String)ret.get("XLMC");
			pxbm = (String)ret.get("PXBM");
			xzyy = (String)ret.get("LANG");
		}
		return "xlx";
	}	
	
	/**
	 * 图形类型
	 * @return
	 */
	public String initTxlx() {
		dlxList = CommonUtil.getCode("cbsjpz", this.getLang(), false);
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
		String[] config = sysMap.get("lmrOperateConfigure").split(",");	
		List<Object> rqlxLs = new ArrayList<Object>();
		for (int i = 0; i < dlxList.size(); i++) {
			Map<String,Object> rowMap = (Map<String,Object> )dlxList.get(i);
			String bm = String.valueOf(rowMap.get("BM"));
			if(bm.equals("01")){ //日
				for(int j = 0; j < config.length; j++){
					if(StringUtil.getString(config[j]).trim().equals("01")){ //01:日冻结
						rqlxLs.add(rowMap);
					}else{
						continue;
					}
				}
				
			}else if(bm.equals("02")){//月
				for(int j = 0; j < config.length; j++){
					if(StringUtil.getString(config[j]).trim().equals("02")){ //02:月冻结
						rqlxLs.add(rowMap);
					}else{
						continue;
					}
				}
				
			}else if(bm.equals("05")){//抄表日
				for(int j = 0; j < config.length; j++){
					if(StringUtil.getString(config[j]).trim().equals("05")){ //05:抄表日
						rqlxLs.add(rowMap);
					}else{
						continue;
					}
				}
				
			}else{
				continue;
			}
			
		}
		dlxList = rqlxLs; //整个list重新赋值
		return "txgl";
	}
	
	public String queryTxlx() throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("dlx", dlx);
		param.put(Constants.APP_LANG, this.getLang());
		Map<String, Object> re = cbsjpzManager.queryTxlx(param, start, limit, dir,
				sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	public String editTxlx() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("dlx", dlx);
		param.put("txid", txid);
		param.put("rwlx", "04");
		param.put(Constants.APP_LANG, this.getLang());
		xzyyList = CommonUtil.getCode("dlyy", "ALL", false);
		Map<String,Object> ret = cbsjpzManager.getObject(param);
		if(null != ret){
			txid = (String)ret.get("TXID");
			txmc = (String)ret.get("TXMC");
			xzyy = (String)ret.get("LANG");
		}
		return "tx";
	}	
		
	/**
	 * 编辑操作
	 * @param czid
	 * @param param
	 */
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return cbsjpzManager.doDbWorks(czid, param);
	}

	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		cbsjpzManager.dbLogger(czid, cznr, czyId, unitCode);
	}

	public CbsjpzManagerInf getCbsjpzManager() {
		return cbsjpzManager;
	}

	public void setCbsjpzManager(CbsjpzManagerInf cbsjpzManager) {
		this.cbsjpzManager = cbsjpzManager;
	}

	public String getDlx() {
		return dlx;
	}

	public void setDlx(String dlx) {
		this.dlx = dlx;
	}

	public String getXlx() {
		return xlx;
	}

	public void setXlx(String xlx) {
		this.xlx = xlx;
	}

	public List<Object> getDlxList() {
		return dlxList;
	}

	public void setDlxList(List<Object> dlxList) {
		this.dlxList = dlxList;
	}

	public List<Object> getXlxList() {
		return xlxList;
	}

	public void setXlxList(List<Object> xlxList) {
		this.xlxList = xlxList;
	}

	public String getTxlx() {
		return txlx;
	}

	public void setTxlx(String txlx) {
		this.txlx = txlx;
	}

	public List<Object> getTxlxList() {
		return txlxList;
	}

	public void setTxlxList(List<Object> txlxList) {
		this.txlxList = txlxList;
	}

	public String getCtpt() {
		return ctpt;
	}

	public void setCtpt(String ctpt) {
		this.ctpt = ctpt;
	}

	public List<Object> getCtptList() {
		return ctptList;
	}

	public void setCtptList(List<Object> ctptList) {
		this.ctptList = ctptList;
	}

	public String getSjx() {
		return sjx;
	}

	public void setSjx(String sjx) {
		this.sjx = sjx;
	}

	public String getSjxmc() {
		return sjxmc;
	}

	public void setSjxmc(String sjxmc) {
		this.sjxmc = sjxmc;
	}

	public String getXlid() {
		return xlid;
	}

	public void setXlid(String xlid) {
		this.xlid = xlid;
	}

	public String getXlmc() {
		return xlmc;
	}

	public void setXlmc(String xlmc) {
		this.xlmc = xlmc;
	}

	public String getPxbm() {
		return pxbm;
	}

	public void setPxbm(String pxbm) {
		this.pxbm = pxbm;
	}

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public String getTxmc() {
		return txmc;
	}

	public void setTxmc(String txmc) {
		this.txmc = txmc;
	}

	public String getDw() {
		return dw;
	}

	public void setDw(String dw) {
		this.dw = dw;
	}

	public String getKd() {
		return kd;
	}

	public void setKd(String kd) {
		this.kd = kd;
	}

	public String getFmt() {
		return fmt;
	}

	public void setFmt(String fmt) {
		this.fmt = fmt;
	}

	public String getSfxs() {
		return sfxs;
	}

	public void setSfxs(String sfxs) {
		this.sfxs = sfxs;
	}

	public String getXzyy() {
		return xzyy;
	}

	public void setXzyy(String xzyy) {
		this.xzyy = xzyy;
	}

	public List<Object> getXzyyList() {
		return xzyyList;
	}

	public void setXzyyList(List<Object> xzyyList) {
		this.xzyyList = xzyyList;
	}

	public List<Object> getSfxsList() {
		return sfxsList;
	}

	public void setSfxsList(List<Object> sfxsList) {
		this.sfxsList = sfxsList;
	}

}


