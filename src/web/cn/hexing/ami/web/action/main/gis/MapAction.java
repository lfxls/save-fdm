package cn.hexing.ami.web.action.main.gis;

import java.util.List;
import java.util.Map;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.dao.common.pojo.arc.Meter;
import cn.hexing.ami.service.main.gis.MapManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;
import cn.hexing.ami.web.listener.AppEnv;

/**
 * @Description 地图展现
 * @author zhaoyy
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time 2016-6-14
 * @version FDM2.0
 */
public class MapAction extends BaseAction implements QueryInf, DbWorksInf {
	private MapManagerInf mapManager;
	//中心经度
	private String centerLon;
	//中心纬度
	private String centerLat;
	
	public MapManagerInf getMapManager() {
		return mapManager;
	}

	public void setMapManager(MapManagerInf mapManager) {
		this.mapManager = mapManager;
	}
	
	public String getCenterLon() {
		return centerLon;
	}

	public void setCenterLon(String centerLon) {
		this.centerLon = centerLon;
	}

	public String getCenterLat() {
		return centerLat;
	}

	public void setCenterLat(String centerLat) {
		this.centerLat = centerLat;
	}

	public String init(){
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		centerLon = StringUtil.isEmptyString(sysMap.get("centerLon"))? "0":sysMap.get("centerLon");
		centerLat = StringUtil.isEmptyString(sysMap.get("centerLat"))? "0":sysMap.get("centerLat");
		return SUCCESS;
	}

	/**
	 * 获取地图上需要显示的所有表计信息
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getMeters(Map<String, String> param, Util util) {
		ActionResult re = new ActionResult();
		List<Object> meterList = mapManager.getMeters(param);
		re.setSuccess(true);
		re.setDataObject(meterList);
		return re;
	}
	
	/**
	 * 获取在地图上需要定位的表计信息
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getMeter(Map<String, String> param, Util util) {
		ActionResult re = new ActionResult();
		Meter meter = mapManager.getMeter(param);
		re.setSuccess(true);
		re.setDataObject(meter);
		return re;
	}

	@Override
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
