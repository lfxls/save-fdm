package cn.hexing.ami.service.report.insInfoReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.charts.Category;
import cn.hexing.ami.util.charts.DataSet;
import cn.hexing.ami.util.charts.Dom4jChart;
import cn.hexing.ami.util.charts.Set;

public class InsNumRepManager implements InsNumRepManagerInf {
	
	private String sqlId="insNumRep.";
	private BaseDAOIbatis baseDAOIbatis;
	
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		
		return baseDAOIbatis.getExtGrid(param, sqlId+"getInsNum", start, limit, dir, sort, isExcel);
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		// TODO Auto-generated method stub
		return baseDAOIbatis.getExtGrid(param, sqlId+"getInsNumDetail", start, limit, dir, sort, isExcel);
	}
	@Override
	public String getChart(Map<String, Object> params) {
		String lang= (String) params.get(Constants.APP_LANG);
		String chartType=(String) params.get("chartType");
		String dateType=(String) params.get("dateType");
		List<Object>ls =new ArrayList<Object>();
		boolean MaxNum=false;
		List<Category> categoryList = new ArrayList<Category>();
		List<DataSet> dataSets = new ArrayList<DataSet>();
		List<Set> sets = new ArrayList<Set>();
		//取得结果集
		if("0".equals(dateType)){
			ls = baseDAOIbatis.queryForList( sqlId + "getChart",params);
		}
		else{
			ls = baseDAOIbatis.queryForList( sqlId + "getChart2",params);
		}
		
			String tname="";
			for(int i=0;i<ls.size();i++){
				Map<String,Object> map=(Map<String, Object>) ls.get(i);
				Map<String, String> properties = new HashMap<String, String>();
				String opdate=StringUtil.getValue(map.get("OPDATE"));
				 tname=StringUtil.getValue(map.get("TNAME"));
				 Category category = new Category(opdate);
		            categoryList.add(category);
				sets.add(new Set(opdate,StringUtil.getValue(map.get("TOLNUM"))));
			double num=Double.parseDouble(StringUtil.getValue(map.get("TOLNUM")));
			if(num>10){
				MaxNum=true;
			}
			}
			DataSet dataSet = new DataSet(tname, null);
			dataSet.setSets(sets);
		     dataSets.add(dataSet);
		     Map<String, String> properties= getProperties(I18nUtil.getText("report.insInfoReport.insNumRep.chart.title", lang));
			if(!MaxNum){
				properties.put("yAxisMaxValue","10");
			}
		     if("0".equals(chartType)){
				return Dom4jChart.getMultiLine( properties, categoryList,dataSets);
			}
			else if("1".equals(chartType)){
				
				return Dom4jChart.getBarXml(categoryList,properties, dataSets);
			}
			else{
				return null;
			}

			
//			String xml=	Dom4jChart.getQxXml(
//				(String) params.get("btmc"),
//				(String) params.get("chartType"),
//				(String) params.get("field"),
//				ls
//			);
//			return xml;
	}

	 private Map<String, String> getProperties(String caption) {
	        Map<String, String> properties = new HashMap<String, String>();
	        properties.put("caption", caption);
	        properties.put("palette", "2");
	        properties.put("bgColor", "ffffff");
	        properties.put("showBorder", "0");
	        properties.put("showValues", "0");
	        properties.put("useRoundEdges", "1");
	        properties.put("baseFont", "Arial");
	    	properties.put("baseFontSize", "11");
			properties.put("labelDisplay", "Rotate");
			properties.put("slantLabels", "1");
			properties.put("chartLeftMargin","30");
			 
	        return properties;
	    }

	public ActionResult addNumRep(List<Object> dateLs){
		ActionResult re=new ActionResult();
		List<Object> paramNewList =  new ArrayList<Object>();
		for(int i=0;i<dateLs.size();i++){
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("opDate", dateLs.get(i));
			paramNewList.add(param);
		}
		baseDAOIbatis.executeBatch(sqlId+"insNum", paramNewList);
		re.setSuccess(true);
		return re;
	}
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

}
