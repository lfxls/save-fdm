package cn.hexing.ami.service.main.insMgt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.MsgCodeConstants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.listener.AppEnv;

/**
 * @Description 安装反馈
 * @author xcx
 * @Copyright 
 * @time：
 * @version FDM
 */

public class InsFbManager implements InsFbManagerInf {

	private BaseDAOIbatis baseDAOIbatis;
	private String sqlId = "insFb.";
	private String menuId="12400";
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}
	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return null;
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);
	}

	/**
	 * 查询表反馈信息
	 */
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "queryMeterFb", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 查询集中器反馈信息
	 */
	public Map<String, Object> queryDcuFb(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "queryDcuFb", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 查询采集器反馈信息
	 */
	public Map<String, Object> queryColFb(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "queryColFb", start, limit, dir, sort, isExcel);
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}
	
	public Map<String, Object> querySetParam(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "querySetPm", start, limit, dir, sort, isExcel);
	}
	
	public Map<String, Object> queryTestParam(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "queryTestPm", start, limit, dir, sort, isExcel);
	}
	
	public Map<String, Object> queryTestOBIS(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "queryTestOBIS", start, limit, dir, sort, isExcel);
	}
	
	public Map<String, Object> queryReadParam(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "queryReadPm", start, limit, dir, sort, isExcel);
	}

	/**
	 * 根据计划id获取图片
	 * @param pid 计划ID
	 * @param flag 0=查看新表图片，1=查看老表图片
	 * @return
	 */
	public String getPicsPath(String pid,String flag) {
		String pics = "";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pid", pid);
		List<Object> list = baseDAOIbatis.queryForList(sqlId + "getPics", map);
		if(list.size() > 0) {
			Map<String,String> resMap = (Map<String, String>) list.get(0);
			if("0".equals(flag)) {//获取新表图片
				pics = resMap.get("NPICS");
			} else {//获取老表图片
				pics = resMap.get("OPICS");
			}
		}
		return pics;
	}
	
	/**
	 * 保存安装反馈基本数据
	 * @param paramList 安装计划反馈数据
	 * @return
	 */
	public ActionResult storeMeterFBBasicData(List<Object> paramList) {
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
		String lang = "";
		if (sysMap != null) {
			lang = sysMap.get("interfaceLang");
		}
		ActionResult re = new ActionResult(false,"");
		List<Object> insMeterList = new ArrayList<Object>();
		List<Object> updMeterList = new ArrayList<Object>();
		List<Object> insCustList = new ArrayList<Object>();
		List<Object> updCustList = new ArrayList<Object>();
		List<Object> insSimList = new ArrayList<Object>();//sim卡信息列表
		Map<String,Object> qMap = new HashMap<String,Object>();
		List<String> meterNoList = new ArrayList<String>();//表号列表
		List<String> custNoList = new ArrayList<String>();//户号列表
		List<String> simNoList = new ArrayList<String>();//sim号列表
		for(Object obj : paramList) {
			Map<String,String> plnFBMap = (Map<String, String>) obj;
			String pid = StringUtil.getValue(plnFBMap.get("S"));//计划号
			String nmsn = StringUtil.getValue(plnFBMap.get("NO"));//新表号
			String omsn = StringUtil.getValue(plnFBMap.get("ONO"));//老表号
			String cno = StringUtil.getValue(plnFBMap.get("CNO"));//户号
			String simNo = StringUtil.getValue(plnFBMap.get("SIM"));//sim卡号
			if(!StringUtil.isEmptyString(simNo)) {//sim卡号不为空
				//标志sim卡号是否相同，true=相同，fasle=不相同
				boolean existSameSim = false;
				for(Object simNoObj : simNoList) {
					String tempSimNo = (String) simNoObj;
					if(simNo.equals(tempSimNo)) {
						existSameSim = true;//存在相同sim卡号
						break;
					}
				}
				if(existSameSim) {
					//存在相同sim卡号，返回失败信息
					re.setSuccess(false);
					re.setMsg(MsgCodeConstants.HX20005);
					re.setDataObject(I18nUtil.getText("mainModule.ifs.uploadWO.sim.same", lang, new String[]{pid,simNo}));
					break;
				} else {
					//记录sim卡号
					simNoList.add(simNo);
				}
			}
			if(!StringUtil.isEmptyString(nmsn)) {
				meterNoList.add(nmsn);
			}
			if(!StringUtil.isEmptyString(omsn)) {
				meterNoList.add(omsn);
			}
			if(!StringUtil.isEmptyString(cno)) {
				custNoList.add(cno);
			}
		}
		qMap.put("msns", meterNoList.toArray(new String[]{}));
		qMap.put("cnos", custNoList.toArray(new String[]{}));
		qMap.put("simNos", simNoList.toArray(new String[]{}));
		//查询在系统档案中已存在的表档案
		List<Object> meterList = baseDAOIbatis.queryForList(sqlId + "queryExistMeters", qMap);
		//查询在系统档案中已存在的用户档案
		List<Object> custList = baseDAOIbatis.queryForList(sqlId + "queryExistCusts", qMap);
		//查询在系统档案中已存在的sim卡档案
		List<Object> simList = baseDAOIbatis.queryForList(sqlId + "queryExistSims", qMap);
		//记录反馈安装计划中绑定的sim信息列表
		List<Object> fbSimList = new ArrayList<Object>();
		for(Object obj : paramList) {
			Map<String,String> plnFBMap = (Map<String, String>) obj;
			//处理表反馈数据，将表相关档案信息记录到list列表中
			procMeterFBData(meterList,custList,simList,insMeterList,updMeterList,insCustList,updCustList,insSimList,plnFBMap);
			//处理并记录反馈数据中使用的sim卡号
			recordMeterFBSim(plnFBMap,fbSimList);
		}
		if(insCustList.size() > 0) {
			baseDAOIbatis.executeBatch(sqlId + "insCusts", insCustList);
		}
		if(updCustList.size() > 0) {
			baseDAOIbatis.executeBatch(sqlId + "updCusts", updCustList);
		}
		if(insSimList.size() > 0) {
			baseDAOIbatis.executeBatch(sqlId + "insSims", insSimList);
		}
		if(insMeterList.size() > 0) {
			baseDAOIbatis.executeBatch(sqlId + "insMeters", insMeterList);
		}
		if(updMeterList.size() > 0) {
			baseDAOIbatis.executeBatch(sqlId + "updMeters", updMeterList);
		}
		if(fbSimList.size() > 0) {
			//更新系统中与当前反馈表绑定相同sim卡的表的sim卡号为null
			baseDAOIbatis.executeBatch(sqlId + "updMeterSim", fbSimList);
		}
		if(paramList.size() > 0) {
			baseDAOIbatis.executeBatch(sqlId + "insMeterFB", paramList);
		}
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 保存安装反馈测试数据（设置，读取，测试）
	 * @param setList 设置数据列表
	 * @param readList 读取数据列表
	 * @param testPMMap 测试数据Map对象
	 * @return
	 */
	public ActionResult storeMeterFBTestData(List<Object> setList, List<Object> readList, 
			Map<String,List<Object>> testPMMap) {
		ActionResult re = new ActionResult(false,"");
		List<Object> testList = new ArrayList<Object>();
		List<Object> testPMList = new ArrayList<Object>();
		if(setList.size() > 0) {
			baseDAOIbatis.executeBatch(sqlId + "delSetFB", setList);
			baseDAOIbatis.executeBatch(sqlId + "insSetFB", setList);
		}
		if(readList.size() > 0) {
			baseDAOIbatis.executeBatch(sqlId + "delReadFB", readList);
			baseDAOIbatis.executeBatch(sqlId + "insReadFB", readList);
		}
		for(Map.Entry<String,List<Object>> cEntry : testPMMap.entrySet()) {
			Map<String,String> map = new HashMap<String,String>();
			String key = cEntry.getKey();
			List<Object> testObisList = cEntry.getValue();
			String pid = StringUtil.getValue(key.split(":")[0]);
			String msn = StringUtil.getValue(key.split(":")[1]);
			String tiid = StringUtil.getValue(key.split(":")[2]);
			String trst = StringUtil.getValue(key.split(":")[3]);
			map.put("PID",pid);
			map.put("MSN",msn);
			map.put("TIID",tiid);
			map.put("RST",trst);
			testList.add(map);
			for(Object obj : testObisList) {
				Map<String,String> testObisMap = (Map<String, String>) obj;
				testObisMap.put("PID", pid);
				testObisMap.put("MSN", msn);
				testObisMap.put("TIID", tiid);
			}
			testPMList.addAll(testObisList);
		}
		if(testList.size() > 0) {
			baseDAOIbatis.executeBatch(sqlId + "delTestFB", testList);
			baseDAOIbatis.executeBatch(sqlId + "insTestFB", testList);
		}
		if(testPMList.size() > 0) {
			baseDAOIbatis.executeBatch(sqlId + "delTestPMCodeFB", testPMList);
			baseDAOIbatis.executeBatch(sqlId + "insTestPMCodeFB", testPMList);
		}
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 处理表反馈数据，得到反馈上来的表和用户档案信息
	 * @param meterList 档案中已存在的表信息
	 * @param custList 档案中已存在的用户信息
	 * @param so,List 档案中已存在的Sim卡信息
	 * @param insMeterList 记录要插入表档案的数据
	 * @param updMeterList 记录要更新表档案的数据
	 * @param insCustList 记录要插入用户档案的数据
	 * @param insSimList 记录要插入Sim档案的数据
	 * @param plnFBMap 安装反馈的表信息
	 * @return
	 */
	public void procMeterFBData(List<Object> meterList, List<Object> custList, 
			List<Object> simList, List<Object> insMeterList, List<Object> updMeterList, 
			List<Object> insCustList, List<Object> updCustList, List<Object> insSimList, Map<String,String> plnFBMap) {
		//安装计划类型
		String type = StringUtil.getValue(plnFBMap.get("BUSSTYPE"));
		String tfid = StringUtil.getValue(plnFBMap.get("TFNO"));
		String nmsn = StringUtil.getValue(plnFBMap.get("NO"));//表号
		String omsn = StringUtil.getValue(plnFBMap.get("ONO"));//老表号
		String cno = StringUtil.getValue(plnFBMap.get("CNO"));//户号
		String simNo = StringUtil.getValue(plnFBMap.get("SIM"));//sim卡号
		String fbRst = StringUtil.getValue(plnFBMap.get("STS"));//反馈结果
		boolean msnExist = false;//标志表号是否存在
		boolean omsnExist = false;//标志老表号是否存在
		boolean cnoExist = false;//标志户号是否存在
		boolean simExist = false;//标志sim是否存在
		//单位代码
		String uid = getUID(tfid,type,omsn,cno);
		
		for(Object mObj : meterList) {
			Map<String,String> mMap = (Map<String, String>) mObj;
			String tempMsn = StringUtil.getValue(mMap.get("MSN"));
			if(!StringUtil.isEmptyString(nmsn)) {
				if(nmsn.equals(tempMsn)) {
					msnExist = true;//存在表号
				}
			}
			if(!StringUtil.isEmptyString(omsn)) {
				if(omsn.equals(tempMsn)) {
					omsnExist = true;//存在老表号
				}
			}
		}
		
		for(Object cObj : custList) {
			Map<String,String> cMap = (Map<String, String>) cObj;
			String eCno = StringUtil.getValue(cMap.get("CNO"));//系统中存在的户号
			if(cno.equals(eCno)) {
				cnoExist = true;//存在户号
				break;
			}
		}
		
		if(cnoExist == false) {//户号不存在
			Map<String,String> insCustMap = new HashMap<String,String>();
			insCustMap.put("CNO", plnFBMap.get("CNO"));
			insCustMap.put("CNAME", plnFBMap.get("CNAME"));
			insCustMap.put("ADDR", plnFBMap.get("ADDR"));
			insCustMap.put("DATASRC", Constants.ARCH_SOURCE_LOCALFB);
			insCustMap.put("UID", uid);
			if(Constants.PLN_FB_FAIL.equals(fbRst)) {//安装计划反馈失败情况下,建立新户信息
				insCustMap.put("DSTATUS", "1");//已派工
				if(Constants.PLN_BUSSTYPE_INSTALLATION.equals(type)) {
					insCustMap.put("STATUS","0");//设置成新开户
				} else {
					insCustMap.put("STATUS","1");//设置已装表
				}
			} else {//安装计划反馈非失败情况下
				insCustMap.put("DSTATUS", "0");
				if(!Constants.PLN_BUSSTYPE_UNINSTALLATION.equals(type)) {//非拆表情况下
					insCustMap.put("STATUS", "1");//设置成已装表
				} else {
					insCustMap.put("STATUS","2");//设置注销
				}
			}
			insCustList.add(insCustMap);
		} else {//存在户号
			Map<String,String> updCustMap = new HashMap<String,String>();
			updCustMap.put("CNO", plnFBMap.get("CNO"));
			updCustMap.put("CNAME", plnFBMap.get("CNAME"));
			updCustMap.put("ADDR", plnFBMap.get("ADDR"));
			updCustMap.put("UID", uid);
			if(Constants.PLN_FB_FAIL.equals(fbRst)) {//安装计划反馈失败情况下
				
			} else {//安装计划反馈非失败情况下
				updCustMap.put("DSTATUS", "0");
				if(!Constants.PLN_BUSSTYPE_UNINSTALLATION.equals(type)) {//非拆表情况下
					updCustMap.put("STATUS", "1");//设置成已装表
				} else {
					//获取该户号下其他装出表
					Map<String,String> param = new HashMap<String,String>();
					param.put("CNO", cno);
					param.put("MSN", omsn);
					param.put("STATUS", "1");
					List<Object> mList = baseDAOIbatis.queryForList(sqlId + "getMeterByCno", param);
					if(mList == null || mList.size() == 0) {//不存在其他表
						updCustMap.put("STATUS","2");//设置成销户
					} else {
						updCustMap.put("STATUS","1");//设置成已装表
					}
				}
			}
			updCustList.add(updCustMap);
		}
		
		if(!Constants.PLN_FB_FAIL.equals(fbRst)) {//安装计划反馈失败，不做业务处理
			for(Object sObj : simList) {
				Map<String,String> sMap = (Map<String, String>) sObj;
				String eSimNo = StringUtil.getValue(sMap.get("SIMNO"));//系统中存在的SIM卡号
				if(simNo.equals(eSimNo)) {
					simExist = true;//存在卡号
					break;
				}
			}
			
			if(!Constants.PLN_BUSSTYPE_UNINSTALLATION.equals(type)) {
				if(simExist == false && 
						!StringUtil.isEmptyString(StringUtil.getValue(plnFBMap.get("SIM")))) {//卡号不存在
					Map<String,String> insSimMap = new HashMap<String,String>();
					insSimMap.put("SIMNO", plnFBMap.get("SIM"));
					insSimMap.put("SINSN", plnFBMap.get("SIMSN"));
					insSimMap.put("MSP", plnFBMap.get("MC"));
					insSimList.add(insSimMap);
				}
			}
		} 
		
		//验证新表号和老表号是否存在
		if(msnExist == true) {
			Map<String,String> updMeterMap = getMeterData("newMeter",nmsn,uid,plnFBMap);
			updMeterList.add(updMeterMap);
		} else if(msnExist == false && !StringUtil.isEmptyString(nmsn)) {
			Map<String,String> insMeterMap = getMeterData("newMeter",nmsn,uid,plnFBMap);
			insMeterMap.put("DATASRC", Constants.ARCH_SOURCE_LOCALFB);
			insMeterList.add(insMeterMap);
		}
		
		//风险：如果现场随便反馈老表号，会导致把已有表拆除的情况
		if(Constants.PLN_BUSSTYPE_REPLACEMENT.equals(type)) {
			//掌机反馈换表业务中老表号与实际派发下去的老表号不一致（现场业务需求）
			proFbAnotherMeter(plnFBMap,updMeterList,updCustList);
		}
		
		if(omsnExist == true) {
			Map<String,String> updMeterMap = getMeterData("oldMeter",omsn,uid,plnFBMap);
			updMeterList.add(updMeterMap);
		} else if(omsnExist == false && !StringUtil.isEmptyString(omsn)) {
			Map<String,String> insMeterMap = getMeterData("oldMeter",omsn,uid,plnFBMap);
			insMeterMap.put("DATASRC", Constants.ARCH_SOURCE_LOCALFB);
			insMeterList.add(insMeterMap);
		}
	}
	
	/**
	 * 获取需要建立用户档案的用户数据
	 * @param custList 档案中已存在的用户信息
	 * @param plnFBMap 安装反馈的表信息
	 * @return
	 */
	public List<Object> getNewCusts(List<Object> custList, Map<String,String> plnFBMap) {
		List<Object> list = new ArrayList<Object>();
		String cno = StringUtil.getValue(plnFBMap.get("CNO"));//户号
		boolean cnoExist = false;//标志户号是否存在
		for(Object obj : custList) {
			Map<String,String> map = (Map<String, String>) obj;
			String tempCNO = StringUtil.getValue(map.get("CNO"));
			if(!StringUtil.isEmptyString(cno)) {
				if(cno.equals(tempCNO)) {
					cnoExist = true;//存在户号
				}
			}
		}
		if(!cnoExist) {
			Map<String,String> map = new HashMap<String,String>();
			map.putAll(plnFBMap);
			map.put("DSTATUS", "1");
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 获取表档案数据
	 * @param mType 表类型:newMeter=新表,oldMeter=老表
	 * @param msn 表号
	 * @param uid 单位id
	 * @param plnFBMap 安装计划反馈数据
	 * @return
	 */
	public Map<String,String> getMeterData(String mType, String msn, String uid, Map<String,String> plnFBMap) {
		Map<String,String> map = new HashMap<String,String>();
		String fbRst = StringUtil.getValue(plnFBMap.get("STS"));//安装计划反馈结果
		map.put("MSN", msn);
		if(!StringUtil.isEmptyString(uid)) {
			map.put("UID", uid);
		} else {
			map.put("UID", null);
		}
		if("newMeter".equals(mType)) {
			map.putAll(plnFBMap);
			map.put("INSDATE", plnFBMap.get("DT"));
			if(fbRst.equals(Constants.PLN_FB_FAIL)) {//安装计划反馈失败，记录表基本信息
				map.put("CNO", null);
				map.put("TFNO", null);
				map.put("MSTATUS", Constants.METER_STATUS_WAREH);//表为入库状态
			} else {//反馈成功
				map.put("MSTATUS", Constants.METER_STATUS_INS);//表为已装表状态
			}
		} else {
			map.put("CT", plnFBMap.get("CT"));
			map.put("PT", plnFBMap.get("PT"));
			map.put("LGT", plnFBMap.get("LGT"));
			map.put("LAT", plnFBMap.get("LAT"));
			map.put("MB", plnFBMap.get("MB"));
			if(fbRst.equals(Constants.PLN_FB_FAIL)) {//安装计划反馈失败，记录表基本信息
				map.put("CNO", plnFBMap.get("CNO"));
				map.put("TFNO", plnFBMap.get("TFNO"));
				map.put("MSTATUS", Constants.METER_STATUS_INS);//表为已装表状态
				map.put("INSDATE", plnFBMap.get("DT"));
			} else {//反馈成功
				map.put("MSTATUS", Constants.METER_STATUS_REM);//表为拆回状态
				map.put("UNINSDATE", plnFBMap.get("DT"));
				map.put("CNO", null);
				map.put("TFNO", null);
			}
		}
		return map;
	}
	
	/**
	 * 获取未同步给MDC或同步失败的反馈成功或异常的安装计划反馈数据
	 * @return
	 */
	public List<Object> getMeterPlnFBData() {
		Map<String,Object> map = new HashMap<String,Object>();
		String[] opSts = new String[]{Constants.PLN_FB_SUCCESS,Constants.PLN_FB_ABNORMAL};
		String[] synSts = new String[]{Constants.PLN_TOMDC_UNSYNC,Constants.PLN_TOMDC_SYNCFAIL};
		map.put("synSts", synSts);
		map.put("opSts", opSts);
		return baseDAOIbatis.queryForList(sqlId + "getMeterPlnFBData", map);
	}
	
	/**
	 * 获取未同步给MDC或同步失败的反馈成功或异常的安装计划中表对应的底度信息
	 * @return
	 */
	public List<Object> getMeterEvData() {
		Map<String,Object> map = new HashMap<String,Object>();
		String[] opSts = new String[]{Constants.PLN_FB_SUCCESS,Constants.PLN_FB_ABNORMAL};
		String[] synSts = new String[]{Constants.PLN_TOMDC_UNSYNC,Constants.PLN_TOMDC_SYNCFAIL};
		String[] obiss = new String[]{"3#1.0.1.8.0.255#2","3#1.0.1.8.1.255#2","3#1.0.1.8.2.255#2",
				"3#1.0.1.8.3.255#2","3#1.0.1.8.4.255#2"};
		map.put("synSts", synSts);
		map.put("opSts", opSts);
		map.put("obiss", obiss);
		return baseDAOIbatis.queryForList(sqlId + "getMeterEVData", map);
	}
	
	/**
	 * 更新表安装计划反馈数据同步到MDC状态
	 * @param paramList
	 */
	public void updMeterFBSynStatus(List<Object> paramList) {
		baseDAOIbatis.executeBatch(sqlId + "updMeterFBSynStatus", paramList);
	}
	
	/**
	 * 获取单位
	 * @param tfid 变压器ID
	 * @param type 安装计划类型
	 * @param omsn 老表号
	 * @param cno 户号
	 * @return
	 */
	public String getUID(String tfid, String type, String omsn, String cno) {
		String uid = "";
		if(StringUtil.isEmptyString(tfid)) {//变压器id为空（拆表情况，掌机反馈变压器id为空）
			if(Constants.PLN_BUSSTYPE_UNINSTALLATION.equals(type)) {//拆表安装计划
				List<Object> cInfoList = baseDAOIbatis.queryForList("insProcess.getCustByCno", cno);
				if(cInfoList.size() > 0) {//系统中存在用户
					Map<String,String> cMap = (Map<String, String>) cInfoList.get(0);
					uid = StringUtil.getValue(cMap.get("UID"));//用户单位
				} else {
					String msn = omsn;
					List<Object> mInfoList = baseDAOIbatis.queryForList("insProcess.getMeterByMsn", msn);
					if(mInfoList.size() > 0) {//系统中存在表档案
						Map<String,String> mMap = (Map<String, String>) mInfoList.get(0);
						uid = StringUtil.getValue(mMap.get("UID"));//表单位
					} else {
						//根单位
						uid = (String) baseDAOIbatis.queryForObject(sqlId + "getRootUnit", null, String.class);
					}
				}
			} else {
				//根单位
				uid = (String) baseDAOIbatis.queryForObject(sqlId + "getRootUnit", null, String.class);
			}
		} else {
			//变压器单位
			uid = (String) baseDAOIbatis.queryForObject(sqlId + "getTFUID", tfid, String.class);
		}
		return uid;
	}
	
	/**
	 * 记录安装计划反馈表中存在绑定sim卡的信息
	 * @param plnFBMap 安装计划反馈数据
	 * @param fbSimList 反馈sim信息列表
	 */
	public void recordMeterFBSim(Map<String,String> plnFBMap,List<Object> fbSimList) {
		Map<String,String> map = new HashMap<String,String>();
		String msn = StringUtil.getValue(plnFBMap.get("NO"));
		String simNo = StringUtil.getValue(plnFBMap.get("SIM"));
		if(!StringUtil.isEmptyString(simNo)) {
			//安装计划反馈表存在绑定的sim卡号(拆表掌机不返回sim卡号)
			map.put("msn", msn);
			map.put("simNo", simNo);
			map.put("status", Constants.METER_STATUS_INS);
			fbSimList.add(map);
		}
	}
	
	/**
	 * 处理反馈的老表号与下发计划中老表号不一致情况
	 * @param plnFBMap 表安装计划反馈数据
	 * @param updMeterList 更新表对象列表
	 * @param updCustList 更新用户对象列表
	 */
	private void proFbAnotherMeter(Map<String,String> plnFBMap, List<Object> updMeterList, List<Object> updCustList) {
		String omsn = StringUtil.getValue(plnFBMap.get("ONO"));//老表号
		String fbRst = StringUtil.getValue(plnFBMap.get("STS"));//反馈结果
		//获取派发的安装计划信息
		Map<String,String> dispPlnMap =  (Map<String, String>) baseDAOIbatis.queryForObject(sqlId + "getDispPlan", plnFBMap, HashMap.class);
		if(dispPlnMap != null && dispPlnMap.size() > 0) {//存在派发下去的安装计划
			String p_o_msn = StringUtil.getValue(dispPlnMap.get("P_O_MSN"));//计划中的老表号
			String m_msn = StringUtil.getValue(dispPlnMap.get("M_MSN"));//档案中存在的表号
			String uid = StringUtil.getValue(dispPlnMap.get("UID"));//档案中存在表所属的单位
			if(!p_o_msn.equals(omsn) && !StringUtil.isEmptyString(m_msn)) {//下发与反馈的老表号不一致
				Map<String,String> updMeterMap = getMeterData("oldMeter",m_msn,uid,plnFBMap);
				updMeterList.add(updMeterMap);
				//更新拆除老表号对应的用户信息
				addOMCustMap(omsn,fbRst,updCustList);
			}
		}
	}
	
	/**
	 * 更新拆除老表号对应的用户信息
	 * @param msn 表号
	 * @param fbRst 反馈结果
	 * @param updCustList 更新用户对象列表
	 */
	private void addOMCustMap(String msn,String fbRst, List<Object> updCustList) {
		//获取用户反馈老表对应的用户信息
		Map<String,String> fbMeterCustMap = (Map<String, String>) baseDAOIbatis.queryForObject(sqlId + "getFbMeterCust", msn, HashMap.class);
		if(fbMeterCustMap != null && fbMeterCustMap.size() > 0) {//存在表号对应的用户信息
			String cno = StringUtil.getValue(fbMeterCustMap.get("CNO"));//档案中存在的户号
			if(!StringUtil.isEmptyString(cno)) {//存在表对应的户号
				Map<String,String> updCustMap = new HashMap<String,String>();
				updCustMap.put("CNO", cno);
				updCustMap.put("CNAME", StringUtil.getValue(fbMeterCustMap.get("CNAME")));
				updCustMap.put("ADDR", StringUtil.getValue(fbMeterCustMap.get("ADDR")));
				if(!Constants.PLN_FB_FAIL.equals(fbRst)) {//安装计划反馈非失败情况下
					updCustMap.put("DSTATUS", "0");
					//获取该户号下其他装出表
					Map<String,String> param = new HashMap<String,String>();
					param.put("CNO", cno);
					param.put("MSN", msn);
					param.put("STATUS", Constants.CUST_STATUS_INS);
					List<Object> mList = baseDAOIbatis.queryForList(sqlId + "getMeterByCno", param);
					if(mList == null || mList.size() == 0) {//不存在其他表
						updCustMap.put("STATUS",Constants.CUST_STATUS_LOGOFF);//设置成销户
					} else {
						updCustMap.put("STATUS",Constants.CUST_STATUS_INS);//设置成已装表
					}
				}
				updCustList.add(updCustMap);
			}
		}
	}
}