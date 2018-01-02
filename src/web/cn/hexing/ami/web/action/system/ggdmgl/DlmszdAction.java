package cn.hexing.ami.web.action.system.ggdmgl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.common.util.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.hexing.ami.service.system.ggdmgl.DlmszdManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * DLMS字典
 * @ClassName:DlmszdAction
 * @Description:TODO
 * @author Administrator
 * @date 2012-7-14 下午03:19:53
 *
 */

public class DlmszdAction extends BaseAction implements DbWorksInf,QueryInf{
	private Logger logger = LoggerFactory.getLogger(DlmszdAction.class);
	private static final long serialVersionUID = 1L;
	public static String FILEITEM_SEPARATOR = "@";
	public static String FILENAME = "dlms-scale-config.txt";
	
	private String STRUCTITEM_SEPARATOR = "#";
	private String STRUCTITEM_ATTRIBUTE_SEPARATOR = ".";
	private String lang="";
	//语言下拉列表框
	private List<Object> langList = null;
	private List<Object> dataTypeList = null; //DLMS数据项数据类型
	private List<Object> callingTypeList = null;//DLMS数据项调用方数据类型
	private List<Object> optTypeList = null;//DLMS数据项操作类型
	//所有的DLMS规约
	private List<?> dlms_subList = null;
	private String dlms_sub_protocol; //当前规约ID
	private Map<?,?> dlms_sub; //当前规约详细
	//数据项分类
	List<Object> dlmsDataSortList = null;
	private Map<?,?> dlms_data_sort; //当前数据项分类详细
	private String item_sort; //当前数据项分类
	
	//数据项
	List<Object> dlmsParamsList = null;
	List<Object> selectedItemList = null;
	private Map<?,?> dlms_params; //当前数据项详细
	private String item_name;
	private String item_id,item_id_query;
	
	//操作类型
	private String opt;
	
	private DlmszdManagerInf  dlmszdManager;
	
	/**
	 * 获取系统当前语言
	 * @return
	 */
	private String getSessionLang(){
		return session.getAttribute(Constants.APP_LANG).toString();
	}
	/**
	 * 初始化字典管理的页面
	 */
	public String init(){
		//列出所有的语言
		if(StringUtil.isEmptyString(lang)) lang = this.getSessionLang(); //默认选中系统语言
		langList = CommonUtil.getCodeNoLoale("dlyy",false);
		//列出所有的规约
		Map<String,String> param = new HashMap<String,String>();
		param.put("lang", lang);
		dlms_subList = dlmszdManager.getDlmsSub(param);
		//默认选中第一个规约
		if(dlms_subList!=null && dlms_subList.size()>0){
			if(StringUtil.isEmptyString(dlms_sub_protocol)) {
				dlms_sub = (Map<?,?>)dlms_subList.get(0);
			}else{
				for(Object obj : dlms_subList){
					Map<?,?> map = (Map<?,?>) obj;
					if(dlms_sub_protocol.equals(map.get("DLMS_SUB_PROTOCOL"))){
						dlms_sub = map;
						break;
					}
				}
			}
			dlms_sub_protocol = (String)dlms_sub.get("DLMS_SUB_PROTOCOL");
			//当前规约的所有数据项分类
			param.put("dlms_sub_protocol", dlms_sub_protocol);
			dlmsDataSortList = dlmszdManager.getDlmsDataSort(param);
			if(dlmsDataSortList!=null && dlmsDataSortList.size()>0){
				if(StringUtil.isEmptyString(item_sort)) {
					dlms_data_sort = (Map<?,?>)dlmsDataSortList.get(0);
				}else{
					for(Object obj : dlmsDataSortList){
						Map<?,?> map = (Map<?,?>) obj;
						if(item_sort.equals(map.get("ITEM_SORT"))){
							dlms_data_sort = map;
							break;
						}
					}
				}
				item_sort = (String)dlms_data_sort.get("ITEM_SORT");
			}
		}
		return SUCCESS;
	}
	/**
	 * 跳转到数据项tab
	 * @return
	 */
	public String queryItem(){
		return "queryItem";
	}
	/**
	 * 数据项分页显示
	 */
	public String query() throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("dlms_sub_protocol", dlms_sub_protocol);
		param.put("item_sort", item_sort);
		param.put("item_name", item_name);
		param.put("item_id_query", item_id_query);
		param.put("lang", lang);
		Map<String, Object> re = dlmszdManager.query(param, start, limit, dir,
				sort, isExcel);
		responseGrid(re, getExcelHead(), "dlms_params", "DLMS字典");
		return null;
	}

	public String queryDetail() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 进入规约新增、修改页面
	 * @return
	 * @throws Exception
	 */
	public String initDlmsSub() throws Exception{
		Map<String,String> param = new HashMap<String,String>();
		param.put("lang", lang);
		dlms_subList = dlmszdManager.getDlmsSub(param);
		if(StringUtil.isEmptyString(dlms_sub_protocol)){//规约ID传空  ，则为新增规约
			opt = Constants.ADDOPT;
		}else{ //修改规约
			opt = Constants.UPDOPT;
			for(Object obj : dlms_subList){
				Map<?,?> map = (Map<?,?>) obj;
				if(dlms_sub_protocol.equals(map.get("DLMS_SUB_PROTOCOL"))){
					dlms_sub = map;
					break;
				}
			}
			if(dlms_sub == null){
				throw new Exception("规约不存在，不能修改！");
			}
		}
		return "initDlmsSub";
	}
	/**
	 * 进入参数项分类新增、修改页面
	 * @return
	 * @throws Exception
	 */
	public String initDataSort() throws Exception{
		if(StringUtil.isEmptyString(item_sort)){//规约ID传空  ，则为新增参数项分类
			opt = Constants.ADDOPT;
		}else{ //修改参数项分类
			opt = Constants.UPDOPT;
			Map<String,String> param = new HashMap<String,String>();
			param.put("lang", lang);
			param.put("dlms_sub_protocol", dlms_sub_protocol);
			param.put("item_sort", item_sort);
			dlmsDataSortList = dlmszdManager.getDlmsDataSort(param);
			if(dlmsDataSortList == null || dlmsDataSortList.size()!=1){
				throw new Exception("规约不存在，不能修改！");
			}else{
				dlms_data_sort = (Map<?,?>)dlmsDataSortList.get(0);
			}
		}
		return "initDataSort";
	}
	/**
	 * 进入数据项新增、修改页面
	 * @return
	 * @throws Exception
	 */
	public String initDlmsParamsNormal() throws Exception{
		dataTypeList = CommonUtil.getCode("dlms_sjlx", this.getLang(), false);
		callingTypeList = CommonUtil.getCode("dlms_dyfsjlx", this.getLang(), false);
		optTypeList = CommonUtil.getCode("dlms_czlx", this.getLang(), false);
		if(StringUtil.isEmptyString(item_id)){//数据项ID传空  ，则为新增数据项
			opt = Constants.ADDOPT;
		}else{ //修数据项
			opt = Constants.UPDOPT;
			Map<String,String> param = new HashMap<String,String>();
			param.put("lang", lang);
			param.put("item_id", item_id);
			dlmsParamsList = dlmszdManager.getDlmsParams(param);
			if(dlmsParamsList == null || dlmsParamsList.size()!=1){
				throw new Exception("数据项不存在，不能修改！");
			}else{
				dlms_params = (Map<?,?>)dlmsParamsList.get(0);
			}
		}
		return "initDlmsParamsNormal";
	}
	public String initDlmsParamsStruct() throws Exception{
		dataTypeList = CommonUtil.getCode("dlms_sjlx", this.getLang(), false);
		callingTypeList = CommonUtil.getCode("dlms_dyfsjlx", this.getLang(), false);
		optTypeList = CommonUtil.getCode("dlms_czlx", this.getLang(), false);
		Map<String,String> param = new HashMap<String,String>();
		param.put("lang", lang);
		param.put("dlms_sub_protocol", dlms_sub_protocol);
		//当前规约的所有数据项
		dlmsParamsList = dlmszdManager.getDlmsParams(param);
		//父规约的所有数据项
		//parentDlmsParamsList = new ArrayList<Object>();
		//generateParentParamsList(parentDlmsParamsList , lang , dlms_sub_protocol);
		
		if(StringUtil.isEmptyString(item_id)){//数据项ID传空  ，则为新增数据项
			opt = Constants.ADDOPT;
		}else{ //修数据项
			opt = Constants.UPDOPT;
			for(Object obj : dlmsParamsList){
				Map<?,?> map = (Map<?,?>) obj;
				if(item_id.equals(map.get("ITEM_ID"))){
					dlms_params = map; 
					break;
				}
			}
			if(dlms_params == null){
				throw new Exception("规约不存在，不能修改！");
			}
			selectedItemList = transferArrayStructItemToList((String)dlms_params.get("ARRAY_STRUCT_ITEM"));
		}
		return "initDlmsParamsStruct";
	}
	/**
	 * 将结构体内容转换为List
	 * @param arrayStructItem
	 * @return
	 */
	private List<Object> transferArrayStructItemToList(String arrayStructItem){
		if(arrayStructItem == null) return null;
		List<Object> res = new ArrayList<Object>();
		if("".equals(arrayStructItem)) return res;
		String[] items = arrayStructItem.split(this.STRUCTITEM_SEPARATOR); //分隔数据项
		for(String item : items){
			Map<String,String> itemMap = new HashMap<String,String>();
			itemMap.put("baseAttributes", item);
			itemMap.put("itemName", item);
			res.add(itemMap);
		}
		return res;
	}
	//递归获取所的数据项(包含父规约的）
	private void generateParamsList(List<Object> dlmsParamsList ,String lang, String dlms_sub_protocol){
		Map<String,String> param = new HashMap<String,String>();
		param.put("lang", lang);
		param.put("dlms_sub_protocol", dlms_sub_protocol);
		dlmsParamsList.addAll(dlmszdManager.getDlmsParams(param));
		//获取父规约
		Map<String,String> temp = new HashMap<String,String>();
		temp.put("dlms_sub_protocol", dlms_sub_protocol);
		String parentDlmsSub = dlmszdManager.getParentDlmsSub(temp);
		if(StringUtils.isEmpty(parentDlmsSub)){
			return ;
		}else{
			generateParamsList(dlmsParamsList , lang ,parentDlmsSub);
		}
		//获取父规约所有的数据项
		
	}

	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return dlmszdManager.doDbWorks(czid, param);
	}

	public String expDlmsSub(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");
		response.setHeader("Content-disposition","attachment;filename=" + FILENAME);
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			
			Map<String ,String > param = new HashMap<String ,String>();
			param.put("lang", lang);
			dlms_subList = dlmszdManager.getDlmsSub(null);
			for(int i=0; i<dlms_subList.size() ; i++){
				Map<?,?> dlmsSub = (Map<?,?>)dlms_subList.get(i);
				String dlmsSubCode = (String)dlmsSub.get("DLMS_SUB_PROTOCOL");
				List<Object> dlmsParamsList = new ArrayList<Object>();
				generateParamsList(dlmsParamsList, lang, dlmsSubCode); //获取规约下参数
				
				for(int j=dlmsParamsList.size()-1 ; j>=0 ; j--){ //倒着输出，先输出父规约数据项，再输出子规约
					StringBuffer text = new StringBuffer();
					Map<?,?> params = (Map<?,?>)dlmsParamsList.get(j);
					text.append(trimNull(params.get("ITEM_ID"))).append(FILEITEM_SEPARATOR);
					text.append(trimNull(params.get("DLMS_SUB_PROTOCOL"))).append(FILEITEM_SEPARATOR);
					text.append(trimNull(params.get("ITEM_SORT"))).append(FILEITEM_SEPARATOR);
					text.append(trimNull(params.get("CLASS_ID"))).append(FILEITEM_SEPARATOR);
					text.append(trimNull(params.get("OBIS"))).append(FILEITEM_SEPARATOR);
					text.append(trimNull(params.get("ATTRIBUTE_ID"))).append(FILEITEM_SEPARATOR);
					text.append(trimNull(params.get("SCALE"))).append(FILEITEM_SEPARATOR);
					text.append(trimNull(params.get("DLMS_DATA_TYPE"))).append(FILEITEM_SEPARATOR);
					text.append(trimNull(params.get("CALLING_DATA_TYPE"))).append(FILEITEM_SEPARATOR);
					text.append(trimNull(params.get("ARRAY_STRUCT_LEN"))).append(FILEITEM_SEPARATOR);
					text.append(trimNull(params.get("ARRAY_STRUCT_ITEM"))).append(FILEITEM_SEPARATOR);
					text.append(trimNull(params.get("CUSTOMIZE_CLASS"))).append(FILEITEM_SEPARATOR);
					text.append(trimNull(params.get("OPT_TYPE")));
					text.append("\n");
					out.write(text.toString().getBytes());
				}
			}
			out.flush();
		} catch (IOException e) {
			logger.error("导出文件"+FILENAME+"出错！", e);
		}finally{
			if(out!=null)try{out.close();}catch (Exception e2) {logger.error("关闭输出流出错", e2);}
		}
		
		return null;
	}
	private  static Object  trimNull(Object str){
		if(str==null) return " ";
		return str;
	}
	public List<String> getExcelHead() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		// TODO Auto-generated method stub
	}


	public String getLang() {
		return lang;
	}


	public void setLang(String lang) {
		this.lang = lang;
	}


	public String getDlms_sub_protocol() {
		return dlms_sub_protocol;
	}


	public void setDlms_sub_protocol(String dlms_sub_protocol) {
		this.dlms_sub_protocol = dlms_sub_protocol;
	}


	public DlmszdManagerInf getDlmszdManager() {
		return dlmszdManager;
	}


	public void setDlmszdManager(DlmszdManagerInf dlmszdManager) {
		this.dlmszdManager = dlmszdManager;
	}


	public List<Object> getLangList() {
		return langList;
	}


	public void setLangList(List<Object> langList) {
		this.langList = langList;
	}


	public List<?> getDlms_subList() {
		return dlms_subList;
	}


	public void setDlms_subList(List<?> dlms_subList) {
		this.dlms_subList = dlms_subList;
	}


	public Map<?, ?> getDlms_sub() {
		return dlms_sub;
	}


	public void setDlms_sub(Map<?, ?> dlms_sub) {
		this.dlms_sub = dlms_sub;
	}
	public List<Object> getDlmsDataSortList() {
		return dlmsDataSortList;
	}
	public void setDlmsDataSortList(List<Object> dlmsDataSortList) {
		this.dlmsDataSortList = dlmsDataSortList;
	}
	public Map<?, ?> getDlms_data_sort() {
		return dlms_data_sort;
	}
	public void setDlms_data_sort(Map<?, ?> dlms_data_sort) {
		this.dlms_data_sort = dlms_data_sort;
	}
	public String getItem_sort() {
		return item_sort;
	}
	public void setItem_sort(String item_sort) {
		this.item_sort = item_sort;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public List<Object> getDlmsParamsList() {
		return dlmsParamsList;
	}
	public void setDlmsParamsList(List<Object> dlmsParamsList) {
		this.dlmsParamsList = dlmsParamsList;
	}
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public Map<?, ?> getDlms_params() {
		return dlms_params;
	}
	public void setDlms_params(Map<?, ?> dlms_params) {
		this.dlms_params = dlms_params;
	}
	public List<Object> getDataTypeList() {
		return dataTypeList;
	}
	public void setDataTypeList(List<Object> dataTypeList) {
		this.dataTypeList = dataTypeList;
	}
	public List<Object> getCallingTypeList() {
		return callingTypeList;
	}
	public void setCallingTypeList(List<Object> callingTypeList) {
		this.callingTypeList = callingTypeList;
	}
	public List<Object> getSelectedItemList() {
		return selectedItemList;
	}
	public void setSelectedItemList(List<Object> selectedItemList) {
		this.selectedItemList = selectedItemList;
	}
	public List<Object> getOptTypeList() {
		return optTypeList;
	}
	public void setOptTypeList(List<Object> optTypeList) {
		this.optTypeList = optTypeList;
	}
	public String getSTRUCTITEM_SEPARATOR() {
		return STRUCTITEM_SEPARATOR;
	}
	public void setSTRUCTITEM_SEPARATOR(String sTRUCTITEM_SEPARATOR) {
		STRUCTITEM_SEPARATOR = sTRUCTITEM_SEPARATOR;
	}
	public String getSTRUCTITEM_ATTRIBUTE_SEPARATOR() {
		return STRUCTITEM_ATTRIBUTE_SEPARATOR;
	}
	public void setSTRUCTITEM_ATTRIBUTE_SEPARATOR(
			String sTRUCTITEM_ATTRIBUTE_SEPARATOR) {
		STRUCTITEM_ATTRIBUTE_SEPARATOR = sTRUCTITEM_ATTRIBUTE_SEPARATOR;
	}
	public String getItem_id_query() {
		return item_id_query;
	}
	public void setItem_id_query(String item_id_query) {
		this.item_id_query = item_id_query;
	}
	
}
