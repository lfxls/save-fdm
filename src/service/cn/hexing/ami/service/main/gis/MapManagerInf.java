package cn.hexing.ami.service.main.gis;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.pojo.arc.Meter;
import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;

/**
 * @Description 地图展现服务接口
 * @author zhaoyy
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time 2016-6-14
 * @version FDM2.0
 */
public interface MapManagerInf  extends QueryInf,DbWorksInf{
	/**
	 * 获取地图上需要显示的所有表计信息
	 * @param param
	 * @return
	 */
	public List<Object> getMeters(Map<String, String> param);
	
	/**
	 * 获取在地图上需要定位的表计信息
	 * @param param
	 * @return
	 */
	public Meter getMeter(Map<String, String> param);

}
