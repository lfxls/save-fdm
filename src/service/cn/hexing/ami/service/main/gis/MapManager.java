package cn.hexing.ami.service.main.gis;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.arc.Meter;
import cn.hexing.ami.util.ActionResult;

/**
 * @Description 地图展现服务实现
 * @author zhaoyy
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time 2016-6-14
 * @version FDM2.0
 */
public class MapManager implements MapManagerInf {
	private String sqlId = "map.";
	private BaseDAOIbatis baseDAOIbatis;

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	/**
	 * 获取地图上需要显示的所有表计信息
	 * @param param
	 * @return
	 */
	public List<Object> getMeters(Map<String, String> param) {
		return baseDAOIbatis.queryForList(sqlId + "getMeters", param);
	}
	
	/**
	 * 获取在地图上需要定位的表计信息
	 * @param param
	 * @return
	 */
	public Meter getMeter(Map<String, String> param) {
		return (Meter)baseDAOIbatis.queryForObject(sqlId + "getMeter", param, Meter.class);
	}
	
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		// TODO Auto-generated method stub
		return null;
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

}
