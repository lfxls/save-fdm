package cn.hexing.ami.util.charts;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.listener.AppEnv;

/**
 * fusionchar共通处理
 * @author jun
 *
 */
public class Dom4jChart { 
	/**
	 * 2D,3D column,bar 
	 * @param categories
	 * @param properties
	 * @param dataSet
	 * @return
	 * @throws Exception
	 */
	public static String getBarXml(List<Category> categoryList,Map<String,String> properties,List<DataSet> dataSet){
	    Chart chart=new Chart();
	    //保留2位小数
		properties.put("decimals", "2");
	    //1=将数值转换成K (Thousands) & M (Millions) 0=不转换
		properties.put("formatNumberScale", "0");
	    chart.setProperties(properties);
	    chart.setCategories(new Categories(categoryList));
	    chart.setDataset(dataSet);
		return  getXml(chart);
	}

	/**
	 * 3D柱状图(带曲线) 
	 * @param categories
	 * @param properties
	 * @param dataSet
	 * @return
	 * @throws Exception
	 */
	public static String getColBarXml(List<Category> categoryList,Map<String,String> properties,List<DataSet> dataSet){
		Chart chart=new Chart();
		//保留2位小数
		properties.put("decimals", "2");
	    //1=将数值转换成K (Thousands) & M (Millions) 0=不转换
		properties.put("formatNumberScale", "0");
	    chart.setProperties(properties);
	    chart.setCategories(new Categories(categoryList));
	    chart.setDataset(dataSet);
		return  getXml(chart);
	}
	/**
	 * 复杂柱图
	 * @param categoryList
	 * @param properties
	 * @param dataSet
	 * @param tls
	 * @return
	 */
	public static String getComBiBarXml(List<Category> categoryList,Map<String,String> properties,List<DataSet> dataSet, List<TrendLines> tls){
	    Chart chart=new Chart();
	    //保留2位小数
		properties.put("decimals", "2");
        //1=将数值转换成K (Thousands) & M (Millions) 0=不转换
		properties.put("formatNumberScale", "0");
	    chart.setProperties(properties);
	    chart.setCategories(new Categories(categoryList));
	    chart.setDataset(dataSet);
	    chart.setTls(tls);
		return  getXml(chart);
}
	
	/**
	 * 获取单条曲线
	 * @param properties
	 * @param sets
	 * @return
	 * @throws Exception
	 */
	
    public static String getSingleLine(Map<String, String> properties, List<Set> sets){
        Chart chart = new Chart();
        //设置图表属性
        chart.setProperties(properties);
        //设置图表数据
        chart.setSets(sets);
        return getXml(chart);
    }
    
   /**
    * 获取多条曲线
    * @param properties
    * @param categoryList
    * @param datasetList
    * @return
    * @throws Exception
    */
    public static String getMultiLine(Map<String, String> properties, List<Category> categoryList, List<DataSet> datasetList){
        Chart chart = new Chart();
        //保留2位小数
		properties.put("decimals", "2");
        //1=将数值转换成K (Thousands) & M (Millions) 0=不转换
		properties.put("formatNumberScale", "0");
        //设置图表属性
        chart.setProperties(properties);
        //设置图表坐标
        chart.setCategories(new Categories(categoryList));
        //设置图表数据
        chart.setDataset(datasetList);
        return getXml(chart);
    }
    
    /**
     * 
     * @param properties
     * @param categoryList
     * @param datasetList
     * @param trendLines
     * @return
     */
    public static String getMultiLine(Map<String, String> properties, List<Category> categoryList, List<DataSet> datasetList, List<TrendLines> tls){
        Chart chart = new Chart();
        //设置图表属性
        chart.setProperties(properties);
        //设置图表坐标
        chart.setCategories(new Categories(categoryList));
        //设置图表数据
        chart.setDataset(datasetList);
        //
        chart.setTls(tls);
        return getXml(chart);
    }
    
	/**
	 * 3D,2D饼图
	 * @param properties 
	 * @param sets
	 * @return
	 * @throws Exception
	 */
	public static String getPie(String caption, List<Set> sets){
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("caption", caption);
		properties.put("bgColor", "DFE8F6");
		properties.put("baseFontSize", "12");
		properties.put("showBorder", "1");
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
		properties.put("enableSmartLabels", "1");
		properties.put("pieYScale", "80");
		Chart chart=new Chart();
		chart.setSets(sets);
		chart.setProperties(properties);
		return getXml(chart);
	}
	
	
	/**
	 * 自定义propertiese
	 * 3D,2D饼图
	 * @param properties 
	 * @param sets
	 * @return
	 * @throws Exception
	 */
	public static String getPie(String caption, List<Set> sets,Map<String, String> properties ){
		Chart chart=new Chart();
		chart.setSets(sets);
		chart.setProperties(properties);
		return getXml(chart);
	}
	/**
	 * FusionWidgets Angular gauges指针图形
	 * @param properties
	 * @param colorRange
	 * @param value
	 * @param trendPoints
	 * @param annotations
	 * @return
	 */
	public static String getAngularGauges(Map<String ,String> properties,ColorRange colorRange,TrendPoints trendPoints,Annotations annotations,Dials dials){
		Chart chart=new Chart();
		chart.setAnnotations(annotations);
		chart.setColorRange(colorRange);
		chart.setTrendPoints(trendPoints);
		chart.setDials(dials);
		chart.setProperties(properties);
		return getXml(chart);
	}
	/**
	 * FusionWigets linear gauges 图形
	 * @param properties
	 * @param colorRange
	 * @param value
	 * @param trendPoints
	 * @param annotations
	 * @return
	 * @throws Exception
	 */
	public static String getLinearGauges(Map<String ,String> properties,ColorRange colorRange,String value,TrendPoints trendPoints,Annotations annotations){
		Chart chart=new Chart();
		chart.setAnnotations(annotations);
		chart.setColorRange(colorRange);
		chart.setProperties(properties);
		chart.setTrendPoints(trendPoints);
		chart.setValue(value);
		return getXml(chart);
	}
	
	/**
     * 纵向LED图形
     * @param properties
     * @param colorRange
     * @param value
     * @return
     */
    public static String getVLED(Map<String ,String> properties, ColorRange colorRange, String value) {
        Chart chart = new Chart();
        chart.setColorRange(colorRange);
        chart.setProperties(properties);
        chart.setValue(value);
        return getXml(chart);
    }
    
	private static String getXml(Object c){
		return creatXml(c,null).asXML().replace("\"", "'");
	}
	
	@SuppressWarnings("rawtypes")
	private static Element creatXml(Object c,Element root)  {
		Class target=c.getClass();
		if(root==null){
		  Document document=DocumentHelper.createDocument();
		  root=document.addElement(target.getName().substring(target.getName().lastIndexOf(".")+1).toLowerCase());
		  root.addAttribute("exportEnabled", "1"); //默认所有的图标都支持导出
		  root.addAttribute("exportAtClient", "0");
		  root.addAttribute("exportAction", "Download");
		  root.addAttribute("exportHandler" , AppEnv.getServletContext()!=null?AppEnv.getServletContext().getContextPath() + "/FCExporter":"FCExporter");
		  root.addAttribute("exportCallback" , "FC_Exported");
		  //防止link属性的中文乱码
		  root.addAttribute("unescapeLinks", "0");
		}
		else 
		  root=root.addElement(target.getName().substring(target.getName().lastIndexOf(".")+1).toLowerCase());
		
		Field[] fields=target.getDeclaredFields();
		for(Field item:fields){
			item.setAccessible(true);
			try {
				if(item.get(c)!=null){
					if(Map.class.equals(item.getType())){//元素属性的赋值ֵ
						Map properties=(Map)item.get(c);
						java.util.Set keys= (java.util.Set)properties.keySet();
						for(Object key:keys){
							if(properties.get(key)!=null)
							  root.addAttribute(key.toString(), properties.get(key).toString());
						}
					}else if(List.class.equals(item.getType())){//对List中子对象的递归解析
						for(Object o:(List)item.get(c)){
							creatXml(o,root);
						}
					}else if(String.class.equals(item.getType())){//用于解析类似于linear gauges 图中的value标签
						root.addElement(item.getName().toLowerCase()).addText(item.get(c).toString());
					}else {//非list的子对象的递归解析
						creatXml(item.get(c),root);
					}
				}
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		return root;
	}
	
	/**
	 * 获取图表
	 * @param btmc 标题名称
	 * @param txlx 折线/柱图
	 * @param field 字段
	 * @param list 结果集
	 * @return
	 */
	public static String getQxXml(String btmc, String txlx, String field, List<Object> list) {
		List<Category> categoryList = new ArrayList<Category>();
		List<DataSet> dataSets = new ArrayList<DataSet>();
		
		if(list != null && list.size() > 0) {
			if(!StringUtil.isEmptyString(field)){
				String[] arrField = field.split(";");
				
				//根据总查询字段个数，初始化Set
				ArrayList<ArrayList<Set>> setList = new ArrayList<ArrayList<Set>>();
				for(int i = 0; i < arrField.length; i++) {
					setList.add(new ArrayList<Set>());
				}
				
				for (int i = list.size() - 1; i >= 0; i--) {
					Map<?, ?> map = (Map<?, ?>) list.get(i);
					Category category = new Category((String) map.get("SJSJ"));//
					categoryList.add(category);
					
					String[] subField = null;
					for(int m = 0; m < setList.size(); m++) {
						subField = arrField[m].split(",");
							setList.get(m).add(new Set((String) map.get("SJSJ"), StringUtil
									.getValue(map.get(subField[0]))));
					}
				}

				//根据总查询字段个数，初始化DataSet
				List<DataSet> dataSetList = new ArrayList<DataSet>();
				for(int i = 0; i < arrField.length; i++){
					dataSetList.add(new DataSet(arrField[i].split(",")[1], null));
				}
				
				for(int j = 0; j < dataSetList.size(); j++){
					dataSetList.get(j).setSets(setList.get(j));
					dataSets.add(dataSetList.get(j));
				}			
			}
		}

		if("01".equals(txlx)){
			return Dom4jChart.getMultiLine(getProperties(btmc, getLabelStep(list)), categoryList, dataSets);
		} else if("02".equals(txlx)){
			return Dom4jChart.getBarXml(categoryList, getProperties(btmc, getLabelStep(list)), dataSets);
		} else {
			return null;
		}
	}
	
	/**
	 * 获取图表属性
	 * @param caption
	 * @return
	 */
	public static Map<String, String> getProperties(String caption, String labelStep) {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("caption", caption);
		properties.put("palette", "2");
		properties.put("showLegend", "1");
		properties.put("bgColor", "ffffff");
		properties.put("showBorder", "1");
		properties.put("showValues", "1");
		properties.put("numVDivLines", "10");
		properties.put("divLineAlpha", "30");
		properties.put("labelDisplay", "ROTATE");
		properties.put("slantLabels", "1");
		properties.put("baseFont", "Arial");
		properties.put("baseFontSize", "12");
		properties.put("labelStep", labelStep);
		if(Integer.parseInt(labelStep) == 1){ //label个数小于10，则将柱状图的间隔增大
			properties.put("plotSpacePercent" , "50");
		}
		return properties;
	}
	/**
	 * 通过list的size确定图标横坐标显示的间隔数
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getLabelStep(List list) {
		String labelStep = "1";
		if (list != null) {
			if (list.size() < 10) {// 如果list的大小为小于等于10
				labelStep = "1";
			} else if (list.size() >= 10 && list.size() < 20) {// 如果list的大小大于10并且小于20
				labelStep = "2";
			} else {// 如果list的大小大于10
				labelStep = String.valueOf(list.size() / 10 + 1);
			}
		}
		return labelStep;
	}

	public static Object getLineXml(String btmc, String txlx, String field, List<Object> list, Map<String, String> properties) {
		List<Category> categoryList = new ArrayList<Category>();
		List<DataSet> dataSets = new ArrayList<DataSet>();
		
		if(list != null && list.size() > 0) {
			if(!StringUtil.isEmptyString(field)){
				String[] arrField = field.split(";");
				
				//根据总查询字段个数，初始化Set
				ArrayList<ArrayList<Set>> setList = new ArrayList<ArrayList<Set>>();
				for(int i = 0; i < arrField.length; i++) {
					setList.add(new ArrayList<Set>());
				}
				
				for (int i = list.size() - 1; i >= 0; i--) {
					Map<?, ?> map = (Map<?, ?>) list.get(i);
					Category category = new Category((String) map.get("SJSJ"));//
					categoryList.add(category);
					
					String[] subField = null;
					for(int m = 0; m < setList.size(); m++) {
						subField = arrField[m].split(",");
							setList.get(m).add(new Set((String) map.get("SJSJ"), StringUtil
									.getValue(map.get(subField[0]))));
					}
				}

				//根据总查询字段个数，初始化DataSet
				List<DataSet> dataSetList = new ArrayList<DataSet>();
				for(int i = 0; i < arrField.length; i++){
					dataSetList.add(new DataSet(arrField[i].split(",")[1], null));
				}
				
				for(int j = 0; j < dataSetList.size(); j++){
					dataSetList.get(j).setSets(setList.get(j));
					dataSets.add(dataSetList.get(j));
				}			
			}
		}

		if("01".equals(txlx)){
			return Dom4jChart.getMultiLine(properties, categoryList, dataSets);
		} else if("02".equals(txlx)){
			return Dom4jChart.getBarXml(categoryList, properties, dataSets);
		} else {
			return null;
		}
	}
		
}
