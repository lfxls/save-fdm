package cn.hexing.ami.service.main.arcMgt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.arc.Transformer;
import cn.hexing.ami.dao.common.pojo.pre.Token;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.StringUtils;

/**
 * @Description 变压器管理manager
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-4-27
 * @version FDM2.0
 */

public class TransfMgtManager implements TransfMgtManagerInf {
	
	private BaseDAOIbatis baseDAOIbatis = null;
	static String menuId = "11800";
	static String sqlId = "transfMgt.";
	
	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	/**
	 * @Description 新增变压器
	 * @param param
	 * @return
	 */
	private ActionResult addTransf(Map<String, String> param){
		ActionResult re = new ActionResult();
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "existsTransf", param);
		if(ls != null && ls.size()>0){
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.transfMgt.add_existsTransf", param.get(Constants.APP_LANG));
		}else{		
			baseDAOIbatis.insert(sqlId + "insertTransf", param);
			
			Transformer t = new Transformer();
			String czyId=param.get(Constants.CURR_STAFFID);
			String lang=param.get(Constants.APP_LANG);
			List<Object> ls1=new ArrayList<Object>();
			t.setTfid(param.get("tfId"));
			t.setUid(param.get("uid"));
			t.setTfname(param.get("tfName"));
			t.setAddr(param.get("addr"));
			t.setCap(param.get("cap"));
			t.setStatus(param.get("status"));
			t.setNodeIddw(param.get("nodeIddw"));
			ls1.add(t);	
			//只有当时新增的变压器状态为running时，才需要调用增量更新接口，状态为disabled和removed时，不需要调用增量更新接口
			String sts = t.getStatus();
			if("0".equals(sts)) {
				CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_TF, Constants.DATAUPDATE_OPTYPE_NEW, czyId, ls1, lang);
			}						
			re.setSuccess(true);
			re.setMsg("mainModule.arcMgt.transfMgt.add_success", param.get(Constants.APP_LANG));		
		}		
		return re;
	} 
	
	/**
	 * @Description 更新变压器
	 * @param param
	 * @return
	 */
	private ActionResult updateTransf(Map<String, String> param){
		ActionResult re = new ActionResult();
		String tfId = param.get("tfId");
		
		//如果变压器下面有表，不允许修改变压器状态和单位，其他字段允许修改
		List<?> ls2 = baseDAOIbatis.queryForList(sqlId + "queryMeterUnderTf", tfId);		
		//从数据库中获取变压器修改之前的状态
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "queryStatus", param);
		String stsOld = StringUtil.getValue(ls.get(0));		
		//获取修改之前的单位，下面安装有表计的变压器不能修改单位
		List<Object> ls3 = baseDAOIbatis.queryForList(sqlId + "queryDW", param);
		String dwOld = StringUtil.getValue(ls3.get(0));		
		//如果变压器下面有安装工单（包括表计、变压器、采集器的安装计划），不允许修改变压器状态，其他字段允许修改
		List<Object> ls4 = baseDAOIbatis.queryForList(sqlId + "qyInsPlan", tfId);
				
		Transformer t = new Transformer();
		String czyId=param.get(Constants.CURR_STAFFID);
		String lang=param.get(Constants.APP_LANG);
		List<Object> ls1=new ArrayList<Object>();
		t.setTfid(param.get("tfId"));
		t.setUid(param.get("uid"));
		t.setTfname(param.get("tfName"));
		t.setAddr(param.get("addr"));
		t.setCap(param.get("cap"));
		t.setStatus(param.get("status"));
		t.setNodeIddw(param.get("nodeIddw"));
		ls1.add(t);
		//获取想修改成的状态
		String stsNew = t.getStatus();
		//获取想修改成的单位
		String dwNew = t.getNodeIddw();		
		if ( ls2 != null && ls2.size()>0 && !(dwOld.equals(dwNew)) ) {
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.transfMgt.update.meterUnderTf.dw", param.get(Constants.APP_LANG));
		} else if ( ls2 != null && ls2.size()>0 && !(stsOld.equals(stsNew)) ) {
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.transfMgt.update.meterUnderTf.status", param.get(Constants.APP_LANG));
		} else if ( ls4!=null && ls4.size()>0 && !(stsOld.equals(stsNew))) {
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.transfMgt.update.insPlanUnderTf.status", param.get(Constants.APP_LANG));
		} else {
			baseDAOIbatis.update(sqlId + "updateTransf", param);
			if("0".equals(stsOld)&&("1".equals(stsNew)||"2".equals(stsNew))){
				CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_TF, Constants.DATAUPDATE_OPTYPE_DEL, czyId, ls1, lang);
			}
			if(("1".equals(stsOld)||"2".equals(stsOld))&&"0".equals(stsNew)){
				CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_TF, Constants.DATAUPDATE_OPTYPE_NEW, czyId, ls1, lang);
			}
			if("0".equals(stsOld)&&"0".equals(stsNew)){
				CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_TF, Constants.DATAUPDATE_OPTYPE_UPT, czyId, ls1, lang);
			}			
			re.setSuccess(true);
			re.setMsg("mainModule.arcMgt.transfMgt.update_success", param.get(Constants.APP_LANG));
		}				
		return re;
	}
	
	/**
	 * @Description 删除变压器
	 * @param param
	 * @return
	 */
	private ActionResult deleteTransf(Map<String, String> param){
		ActionResult re = new ActionResult();
		String tfId = param.get("tfId");
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "queryStsIsRun", tfId);
		List<?> ls1 = baseDAOIbatis.queryForList(sqlId + "queryMeterUnderTf", tfId);
		//如果变压器下面有安装工单（包括表计、变压器、采集器的安装计划），不允许删除变压器
		List<Object> ls4 = baseDAOIbatis.queryForList(sqlId + "qyInsPlan", tfId);		
		if (ls != null && ls.size()>0) {
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.transfMgt.stsenabled", param.get(Constants.APP_LANG));
		} else if (ls1 != null && ls1.size()>0) {
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.transfMgt.del.meterUnderTf", param.get(Constants.APP_LANG));
		} else if ( ls4 !=null && ls4.size()>0) {
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.transfMgt.del.insPlanUnderTf", param.get(Constants.APP_LANG));
		} else {
			Transformer t = new Transformer();
			String czyId=param.get(Constants.CURR_STAFFID);
			String lang=param.get(Constants.APP_LANG);
			List<Object> ls2=new ArrayList<Object>();
			t.setTfid(param.get("tfId"));
			t.setUid(param.get("uid"));
			
			//根据tfId从数据库中查出tfName,param没有tfName
			List<Object> ls3 = baseDAOIbatis.queryForList(sqlId + "qyTfName", param);
			String tfName = StringUtil.getValue(ls3.get(0));
			t.setTfname(tfName);
			
			t.setAddr(param.get("addr"));
			t.setCap(param.get("cap"));
			t.setStatus(param.get("status"));
			t.setNodeIddw(param.get("nodeIddw"));
			ls2.add(t);	
			
			baseDAOIbatis.delete(sqlId + "deleteTransf", param);
			CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_TF, Constants.DATAUPDATE_OPTYPE_DEL, czyId, ls2, lang);
			
			re.setMsg("mainModule.arcMgt.transfMgt.delete_success", param.get(Constants.APP_LANG));
			re.setSuccess(true);
		}
		
		return re;
	}
	
	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		if ((menuId + "01").equals(czid)) {
			re = addTransf(param);
		} else if ((menuId + "02").equals(czid)) {
			re = updateTransf(param);
		} else if ((menuId + "03").equals(czid)) {
			re = deleteTransf(param);
		}
		return re;
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);		
	}
	
	/**
	 * @Description 变压器管理界面，查询变压器
	 * @param param
	 * @return
	 */
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap = baseDAOIbatis.getExtGrid(param, sqlId + "getTransfList", start, limit, dir, sort, isExcel);
		return resultMap;
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}
	
	/**
	 * @Description 变压器选择界面，查询变压器
	 * @param param
	 * @return
	 */
	@Override
	public Map<String, Object> querySel(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap = baseDAOIbatis.getExtGrid(param, sqlId + "transfSel", start, limit, dir, sort, isExcel);
		return resultMap;
	}
	
	/**
	 * @Description 获得所有的变压器
	 * @param param
	 * @return
	 */
	public List<Object> getAllTransformer() {
		List<Object> list = baseDAOIbatis.queryForList(sqlId + "getAllTransformer", null);
		return list;
	}
	
	/**
	 * @Description 编辑变压器获取信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> getTf(Map<String, Object> param) {
		return (Map<String, Object>) baseDAOIbatis.queryForList(sqlId+"getTf", param).get(0);
	}

}
