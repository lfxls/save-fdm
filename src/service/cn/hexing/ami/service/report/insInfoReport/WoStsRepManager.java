package cn.hexing.ami.service.report.insInfoReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.charts.Dom4jChart;
import cn.hexing.ami.util.charts.Set;

public class WoStsRepManager implements WoStsRepManagerInf{
	private BaseDAOIbatis baseDAOIbatis;
	private String sqlId="woStsRep.";
	
	
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		
		return baseDAOIbatis.getExtGrid(param, sqlId+"getWoSts", start, limit, dir, sort, isExcel);
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public String getChart(Map<String, String> params) {
		List<Set> sets = new ArrayList<Set>();
		Map<String, String> zb = new HashMap<String, String>();
		List<Object> statusLs=baseDAOIbatis.queryForList(sqlId+"getStatusLs", params);
		for(int i=0;i<statusLs.size();i++){
			Map<String, String> properties = new HashMap<String, String>();
			String status=StringUtil.getValue(((Map<String, Object>) statusLs.get(i)).get("VALUE"));
			String statusName=StringUtil.getValue(((Map<String, Object>) statusLs.get(i)).get("NAME"));
			params.put("status", status);
			
			
			int count=(Integer) baseDAOIbatis.queryForObject(sqlId+"getChart", params,Integer.class);
			properties.put("value", String.valueOf(count));
			properties.put("label",statusName);
			properties.put("toolText",statusName+":"+count);
			properties.put("link", "JavaScript:reload("+status+")");
			sets.add(new Set(properties));
		}
		String caption=I18nUtil.getText("report.insInfoReport.woStsRep.chart.title", params.get("appLang"));
		
		String pieXml = Dom4jChart.getPie(caption, sets,getProperties(caption));
		zb.put("pieXml", pieXml);
		String msg = JSONObject.fromObject(zb).toString();
		return msg;
	}
	
	private Map<String, String> getProperties(String caption){
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("caption", caption);
		properties.put("bgColor", "FFFFFF");
		properties.put("baseFontSize", "12");
		properties.put("showBorder", "0");
		//保留2位小数
		properties.put("decimals", "2");
		//1=将数值转换成K (Thousands) & M (Millions) 0=不转换
		properties.put("formatNumberScale", "0");
		properties.put("enableSmartLabels", "1");
		properties.put("palette", "4");
		properties.put("enableRotation", "0");
		properties.put("bgRatio", "0,100");
		properties.put("bgAngle", "360");
		properties.put("bgAlpha", "40,100");
		properties.put("startingAngle", "70");
		properties.put("showPercentValues", "0");
		properties.put("showPercentInToolTip", "0");
		properties.put("showValues", "1");
		properties.put("enableSmartLabels", "1");
		properties.put("pieYScale", "80");
		return properties;
	}


	
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	

}
