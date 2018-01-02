package cn.hexing.ami.web.action;

import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.BeanUtils;

import cn.hexing.ami.dao.common.pojo.AqCzy;
import cn.hexing.ami.service.LoginManager;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.ExcelExportForExtGrid;
import cn.hexing.ami.util.PdfExportForExtGrid;
import cn.hexing.ami.util.SpringContextUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.TxtExportForExtGrid;
import cn.hexing.ami.util.WordExportForExtGrid;

import com.opensymphony.xwork2.ActionSupport;

/**
 * action基础类，包括导出功能
 * 
 * @Description
 * @author jun
 * @Copyright 2013 hexing Inc. All rights reserved
 * @time：2013-3-20
 * @version AMI3.0
 */
public class BaseAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware {

	private static final long serialVersionUID = 3389516739497567629L;
	private static Logger logger = Logger.getLogger(BaseAction.class.getName());

	public HttpServletRequest request;

	public HttpServletResponse response;

	public HttpSession session;

	public String nodeId, nodeType, zxsl, nodeText, nodeDwdm, nodeDwmc, yhlx,
			objectValue;
	public String start, limit, sort, dir, isExcel;
	public String menuid;
	private Excel excel;
	private boolean needWrite = true;

	// 当前tab的ID
	private String curTabId;

	public String getCurTabId() {
		return curTabId;
	}

	public void setCurTabId(String curTabId) {
		this.curTabId = curTabId;
	}

	public boolean isNeedWrite() {
		return needWrite;
	}

	public void setNeedWrite(boolean needWrite) {
		this.needWrite = needWrite;
	}

	public Excel getExcel() {
		return excel;
	}

	public String getNodeDwmc() {
		return nodeDwmc;
	}

	public String getZxsl() {
		return zxsl;
	}

	public void setZxsl(String zxsl) {
		this.zxsl = zxsl;
	}

	public void setNodeDwmc(String nodeDwmc) {
		this.nodeDwmc = nodeDwmc;
	}

	public void setExcel(Excel excel) {
		this.excel = excel;
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	@SuppressWarnings("unused")
	private List<Object> result, yhlxLs, yhlxAllLs;

	public List<Object> getYhlxAllLs() {
		return CommonUtil.getCodeNumber("yhlx", this.getLang(), "DESC", true);
	}

	public void setYhlxAllLs(List<Object> yhlxAllLs) {
		this.yhlxAllLs = yhlxAllLs;
	}

	public List<Object> getResult() {
		return result;
	}

	public void setResult(List<Object> result) {
		this.result = result;
	}

	public String init() {
		return SUCCESS;
	}

	public void setServletRequest(HttpServletRequest req) {
		this.request = req;
		this.session = req.getSession();
	}

	public void setServletResponse(HttpServletResponse rep) {
		this.response = rep;
	}

	/**
	 * ajax返回 字符串
	 * 
	 * @param text
	 */
	public void responseText(String text) {
		LOG.debug("txt:" + text);
		response.setContentType("text/xml; charset=UTF-8");
		try {
			response.getOutputStream().write(StringUtil.getUTF8Bytes(text));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(StringUtil.getExceptionDetailInfo(e));
		}
	}

	/**
	 * 返回ExtGrid结果（json或导出Excel）
	 * 
	 * @param result
	 * @param headList
	 * @param xlsName
	 * @param shtName
	 */
	@SuppressWarnings("unchecked")
	public void responseGrid(Map<String, Object> result, List<String> headList,
			String xlsName, String shtName) {
		if (StringUtil.isEmptyString(isExcel)) {
			responseJsonForGrid(result, true);
		} else {
			List<Object> bodyList = (List<Object>) result.get("result");
			responseExcel(headList, bodyList, xlsName, shtName);
		}
	}

	/**
	 * 返回ExtGrid结果
	 * 
	 * @param result
	 * @param headList
	 * @param xlsName
	 * @param shtName
	 */
	public void responseGrid(Map<String, Object> result) {
		if(result==null){
			result = new HashMap<String, Object>();
			result.put("result", new ArrayList<Object>());
			result.put("rows", 0);
		}
		responseJsonForGrid(result, true);
	}

	/**
	 * 返回json 供Ext使用
	 * 
	 * @param obj
	 * @param isStore
	 */
	public void responseJson(Object obj, boolean isStore) {
		try {
			JSONArray jsonarray = JSONArray.fromObject(obj);
			String json = jsonarray.toString();
			if (isStore) {
				json = json.substring(1, json.length() - 1);
			}
			LOG.debug("json:" + json);
			if (needWrite) {
				response.setContentType("text/json; charset=UTF-8");
				response.getOutputStream().write(
						StringUtil.getUTF8Bytes(json.toString()));
			}

		} catch (Exception e) {
			logger.error(StringUtil.getExceptionDetailInfo(e));
		}
	}

	/**
	 * 返回json 供Ext使用
	 * 
	 * @param obj
	 * @param isStore
	 */
	@SuppressWarnings("unchecked")
	public void responseJsonForGrid(Object obj, boolean isStore) {
		try {

			try {
				List<Object> resultList = (List<Object>) ((Map<String, Object>) obj)
						.get("result");
				setResult(resultList);
			} catch (Exception e) {
			}
			JSONArray jsonarray = JSONArray.fromObject(obj);
			String json = jsonarray.toString();
			if (isStore) {
				json = json.substring(1, json.length() - 1);
			}
			LOG.debug("json:" + json);
			if (needWrite) {
				response.setContentType("text/json; charset=UTF-8");
				response.getOutputStream().write(
						StringUtil.getUTF8Bytes(json.toString()));
			}
		} catch (Exception e) {
			logger.error(StringUtil.getExceptionDetailInfo(e));
		}
	}
	
	/**
	 * 带表头信息——导出Excel
	 * @param headList
	 * @param bodyList
	 * @param xlsName
	 * @param sheetName
	 * @param excelTitle 报表头，多行以;分开
	 */
	public void responseExcel(List<String> headList, List<?> bodyList,	//方法重载，新增参数excelTitle，使能导出表头
			String xlsName, String sheetName,String excelTitle){
		try {
			request.setCharacterEncoding("GBK");
		} catch (UnsupportedEncodingException e) {
			logger.error(StringUtil.getExceptionDetailInfo(e));
		}
		String[] excelTextList = excelTitle.split(";");
		response.setContentType("application/excel");
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ xlsName + ".xls\"");
		WritableWorkbook wbook = null;
		try {
			wbook = Workbook.createWorkbook(response.getOutputStream());// 建立excel文件
			if (bodyList == null) {
				bodyList = new ArrayList<Map<String, ?>>();
			}
			float sn = (float) bodyList.size() / 60000;
			int n = bodyList.size() % 60000;
			if (sn > 1) {
				int sheetNum = n > 0 ? (int) sn + 1 : (int) sn;
				for (int i = 0; i < sheetNum; i++) {
					int start = i * 60000;
					createSheet(wbook, bodyList, headList, i, start, sheetName,excelTextList);
				}
			} else {
				createSheet(wbook, bodyList, headList, 0, 0, sheetName,excelTextList);
			}
			wbook.write();
		} catch (Exception e) {
			LOG.error("导出EXCEL数据出错");
			logger.error(StringUtil.getExceptionDetailInfo(e));
		} finally {
			if (wbook != null) {
				try {
					wbook.close();
					wbook = null;
					bodyList = null;
				} catch (WriteException e) {
					logger.error(StringUtil.getExceptionDetailInfo(e));
				} catch (IOException e) {
					logger.error(StringUtil.getExceptionDetailInfo(e));
				}
			}
		}
	}
	/*带表头导出功能，新增参数excelTextList*/
	private void createSheet(WritableWorkbook wbook, List<?> bodyList,			
			List<String> headList, int sheetNum, int start, String sheetName,
			String[] excelTextList) throws RowsExceededException,
			WriteException {

		// 设置excel标题字体
		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 9,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE); // 字体
		WritableCellFormat wcfFC = new WritableCellFormat(wfont); // 字体格式
		sheetName = sheetNum > 0 ? sheetName + (sheetNum + "") : sheetName;

		WritableSheet sheet = wbook.createSheet(sheetName, sheetNum); // sheet名称
		String[] keys = new String[headList.size()];

		// //////////////////////////excel 表头///////////////////////////////
		if (excelTextList != null&&excelTextList.length>0) {
			//设置字体，16号字体，粗体
			WritableFont wfExcel = new WritableFont(WritableFont.TIMES, 16,
					WritableFont.BOLD, false);
			//新建可写单元格
			WritableCellFormat wcfExcel = new WritableCellFormat(wfExcel);
			// 设置背景色
			wcfExcel.setBackground(Colour.WHITE);
			// 设置边框线
			wcfExcel.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 设置居中
			wcfExcel.setAlignment(Alignment.CENTRE);
			// 换行
			wcfExcel.setWrap(true);

			int excelTextListLength = excelTextList.length;
			String excelTextListString = "";
			//所有表头信息内容换行格式调整
			for (int i = 0; i < excelTextListLength; i++) {
				if (i != excelTextListLength - 1) {
					excelTextListString = excelTextListString
							+ excelTextList[i] + "\n";
				} else {
					excelTextListString = excelTextListString
							+ excelTextList[i];
				}
			}
			//Lable包容可写单元格wcfExcel格式以及信息内容excelTextListString
			Label excelTitleLabel = new Label(0, start, excelTextListString,
					wcfExcel);
			//sheet包容Lable
			sheet.addCell(excelTitleLabel);
			//扩展单元格大小mergeCells(start_col,start_row,xx,end_col,end_row)
			sheet.mergeCells(0, start, headList.size() - 1, start
					+ excelTextListLength * 2);
			start += excelTextListLength * 2 + 1;		//start的下一个行数值
		}
		// //////////////////////////excel 表头///////////////////////////////

		// 设置表格头——对应Extgrid的列名
		for (int i = 0; i < headList.size(); i++) {
			// 11号字体，粗体
			WritableFont wf = new WritableFont(WritableFont.TIMES, 11,
					WritableFont.BOLD, false);
			WritableCellFormat wcf = new WritableCellFormat(wf);
			// 设置背景色
			wcf.setBackground(Colour.PALE_BLUE);
			// 设置边框线
			wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 设置居中
			wcf.setAlignment(Alignment.CENTRE);

			sheet.setColumnView(i, 22);

			Label lb = new Label(i, start, headList.get(i).split(",")[1], wcf);
			sheet.addCell(lb);
			/*
			 * sheet.addCell(new Label(i, 0, headList.get(i).split(",")[1],
			 * wcfFC));
			 */

			keys[i] = headList.get(i).split(",")[0];
		}
		
		//start+=1;

		// 设置表格内容
		int end = (start + 60000) > bodyList.size() ? bodyList.size()
				: start + 60000;
		for (int r = start; r < end+start; r++) {
			Map<?, ?> m = (Map<?, ?>) bodyList.get(r-start);
			for (int c = 0; c < keys.length; c++) {
				WritableFont wf = new WritableFont(WritableFont.TIMES, 10,
						WritableFont.NO_BOLD, false);
				WritableCellFormat wcf = new WritableCellFormat(wf);

				// 设置背景色，斑马线效果
				if (r % 2 == 0) {
					wcf.setBackground(Colour.LIGHT_TURQUOISE);
				}
				// 设置边框线
				wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
				// 设置居中
				wcf.setAlignment(Alignment.CENTRE);
				wcf.setWrap(true);
				Label lb = new Label(c, r + 1, StringUtil.getValue(			//c表示第几列
						m.get(keys[c])).replaceAll("<br/>", "\n"), wcf);
				sheet.addCell(lb);

				/*
				 * sheet.addCell(new Label(c, r + 1, StringUtil.getValue(m
				 * .get(keys[c])))); // 第二行开始
				 */
			}
		}
	}
	/**
	 * 不带表头信息——导出Excel
	 * 
	 * @param headList
	 * @param bodyList
	 * @param xlsName
	 * @param sheetName
	 */
	public void responseExcel(List<String> headList, List<?> bodyList,
			String xlsName, String sheetName) {
		try {
			request.setCharacterEncoding("GBK");
		} catch (UnsupportedEncodingException e) {
			logger.error(StringUtil.getExceptionDetailInfo(e));
		}
		String excelText = request.getParameter("excelTextList");
		String[] excelTextList = null;
		if (!StringUtil.isEmptyString(excelText)) {
			excelTextList = excelText.split(";");
		}
		
		response.setContentType("application/excel");
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ xlsName + ".xls\"");
		WritableWorkbook wbook = null;
		try {
			wbook = Workbook.createWorkbook(response.getOutputStream());// 建立excel文件
			if (bodyList == null) {
				bodyList = new ArrayList<Map<String, ?>>();
			}
			float sn = (float) bodyList.size() / 60000;
			int n = bodyList.size() % 60000;
			if (sn > 1) {
				int sheetNum = n > 0 ? (int) sn + 1 : (int) sn;
				for (int i = 0; i < sheetNum; i++) {
					int start = i * 60000;
					if(excelTextList==null){
					createSheet(wbook, bodyList, headList, i, start, sheetName);}
					else{
						createSheet(wbook, bodyList, headList, i, start, sheetName,excelTextList);
					}
				}
			} else {
				if(excelTextList==null){
				createSheet(wbook, bodyList, headList, 0, 0, sheetName);
				}else{
					createSheet(wbook, bodyList, headList, 0, 0, sheetName,excelTextList);
				}
			}
			wbook.write();
		} catch (Exception e) {
			LOG.error("导出EXCEL数据出错");
			logger.error(StringUtil.getExceptionDetailInfo(e));
		} finally {
			if (wbook != null) {
				try {
					wbook.close();
					wbook = null;
					bodyList = null;
				} catch (WriteException e) {
					logger.error(StringUtil.getExceptionDetailInfo(e));
				} catch (IOException e) {
					logger.error(StringUtil.getExceptionDetailInfo(e));
				}
			}
		}
	}

	private void createSheet(WritableWorkbook wbook, List<?> bodyList,
			List<String> headList, int sheetNum, int start, String sheetName)
			throws RowsExceededException, WriteException {

		// 设置excel标题字体
		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 9,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE); // 字体
		WritableCellFormat wcfFC = new WritableCellFormat(wfont); // 字体格式
		sheetName = sheetNum > 0 ? sheetName + (sheetNum + "") : sheetName;

		WritableSheet sheet = wbook.createSheet(sheetName, sheetNum); // sheet名称
		String[] keys = new String[headList.size()];

		// 设置表格头
		for (int i = 0; i < headList.size(); i++) {
			// 11号字体，粗体
			WritableFont wf = new WritableFont(WritableFont.TIMES, 11,
					WritableFont.BOLD, false);
			WritableCellFormat wcf = new WritableCellFormat(wf);
			// 设置背景色
			wcf.setBackground(Colour.PALE_BLUE);
			// 设置边框线
			wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 设置居中
			wcf.setAlignment(Alignment.CENTRE);

			sheet.setColumnView(i, 22);

			Label lb = new Label(i, 0, headList.get(i).split(",")[1], wcf);
			sheet.addCell(lb);
			/*
			 * sheet.addCell(new Label(i, 0, headList.get(i).split(",")[1],
			 * wcfFC));
			 */

			keys[i] = headList.get(i).split(",")[0];
		}

		// 设置表格内容
		int end = (start + 60000) > bodyList.size() ? bodyList.size()
				: start + 60000;
		for (int r = start; r < end; r++) {
			Map<?, ?> m = (Map<?, ?>) bodyList.get(r);
			for (int c = 0; c < keys.length; c++) {
				WritableFont wf = new WritableFont(WritableFont.TIMES, 10,
						WritableFont.NO_BOLD, false);
				WritableCellFormat wcf = new WritableCellFormat(wf);

				// 设置背景色，斑马线效果
				if (r % 2 == 0) {
					wcf.setBackground(Colour.LIGHT_TURQUOISE);
				}
				// 设置边框线
				wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
				// 设置居中
				wcf.setAlignment(Alignment.CENTRE);
				wcf.setWrap(true);
				Label lb = new Label(c, r + 1, StringUtil.getValue(
						m.get(keys[c])).replaceAll("<br/>", "\n"), wcf);
				sheet.addCell(lb);

				/*
				 * sheet.addCell(new Label(c, r + 1, StringUtil.getValue(m
				 * .get(keys[c])))); // 第二行开始
				 */}
		}
	}


	/**
	 * 返回当前的操作人员的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public String getUserIp() {
		return request.getRemoteAddr();
	}

	/**
	 * 获取登录时选择的单位的单位代码
	 */
	public String getUnitCode() {
		return (String) session.getAttribute(Constants.UNIT_CODE);
	}

	/**
	 * 获取登录时选择的单位的单位代码的级别
	 */
	public String getUnitLevel() {
		return (String) session.getAttribute(Constants.UNIT_LEVEL);
	}

	/**
	 * 获取登录时选择的单位的单位名称
	 */
	public String getUnitName() {
		return (String) session.getAttribute(Constants.UNIT_NAME);
	}

	/**
	 * 获取登录时操作员id
	 */
	public String getCzyid() {
		return (String) session.getAttribute(Constants.CURR_STAFFID);
	}

	/**
	 * 获取登录操作员
	 */
	public AqCzy getCurrentOp() {
		return (AqCzy) session.getAttribute(Constants.CURR_STAFF);
	}

	/**
	 * 获取登录部门
	 */
	public String getBm() {
		return (String) session.getAttribute(Constants.CURR_DEPT);
	}

	/**
	 * 获取访问标记（操作员---1 or部门---0）
	 */
	public String getFwbj() {
		LoginManager loginManager = (LoginManager)SpringContextUtil.getBean("loginManager");
		return loginManager.getFwbj((String) session.getAttribute(Constants.CURR_STAFFID), (String) session.getAttribute(Constants.UNIT_CODE));
		//return (String) session.getAttribute(Constants.CURR_RIGTH);
	}

	/**
	 * 获取展示类型列表
	 * 
	 * @return
	 */
	public List<Map<String, String>> getZslxLs() {
		List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
		Map<String, String> m = new HashMap<String, String>();
		m.put("BM", "sb");
		m.put("MC", getText("left.tab.sb"));
		ls.add(m);
		Map<String, String> m1 = new HashMap<String, String>();
		m1.put("BM", "bj");
		m1.put("MC", getText("left.tab.bj"));
		ls.add(m1);
		return ls;
	}

	/**
	 * 获取国际化标准
	 * 
	 * @return
	 */
	public String getLang() {
		return session == null ? "" : (String) session
				.getAttribute(Constants.APP_LOCALE);
	}

	public String getContextPath(HttpServletRequest request) {
		return request.getContextPath();
	}

	// 文件名
	public String getFileName(String FileName) {
		int pos = FileName.lastIndexOf(".");
		String extension = FileName.substring(pos);
		return System.currentTimeMillis() + extension;
	}

	// 文件上传
	public void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						16 * 1024);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						16 * 1024);
				byte[] buffer = new byte[16 * 1024];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			logger.error(StringUtil.getExceptionDetailInfo(e));
		}
	}

	/**
	 * 通过反射获取对应grid的对象值
	 * 
	 * @return
	 */
	private List getRootList(HttpServletRequest request) {
		String method = request.getParameter("excel.gridMethod");
		try {
			// 不输出json到response
			setNeedWrite(false);
			// 调用此方法，初始化数据
			Method fm = BeanUtils.findMethod(this.getClass(), method,
					new Class[] {});
			fm.invoke(this, new Object[] {});
		} catch (Exception e) {
			logger.error("通过反射调用aciton中的方法" + method + "失败，原因为"
					+ StringUtil.getExceptionDetailInfo(e));
			throw new RuntimeException("没有在action的url中找到对应的的方法");
		}

		String root = request.getParameter("excel.gridRoot");
		root = StringUtils.isBlank(root) ? "root" : root;

		List rootList = null;
		try {
			rootList = (List) findRootData(this, root);
		} catch (Exception e) {
			logger.error(StringUtil.getExceptionDetailInfo(e));
			throw new RuntimeException("没有找到指定的root对应的数据，请确认！");
		}
		return rootList;
	}

	/**
	 * 页面表格excel导出公用程序
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String excel() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		List rootList = getRootList(request);
		String excelText = request.getParameter("excelTextList");
		String[] excelTextList = null;
		if (!StringUtil.isEmptyString(excelText)) {
			excelTextList = excelText.split(";");

		}

		ExcelExportForExtGrid excelExport = new ExcelExportForExtGrid();
		excelExport.exportExcel(request, response, rootList, excelTextList);
		return null;
	}

	/**
	 * 导出成文本格式
	 * 
	 * @return
	 */
	public String txt() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		List rootList = getRootList(request);

		TxtExportForExtGrid txtExport = new TxtExportForExtGrid();
		txtExport.exportTxt(request, response, rootList);
		return null;
	}

	/**
	 * 导出成文本格式
	 * 
	 * @return
	 */
	public String pdf() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		List rootList = getRootList(request);

		PdfExportForExtGrid pdfExport = new PdfExportForExtGrid();
		pdfExport.exportPdf(request, response, rootList);
		return null;
	}

	/**
	 * 导出成文本格式
	 * 
	 * @return
	 */
	public String word() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		List rootList = getRootList(request);

		WordExportForExtGrid wordExport = new WordExportForExtGrid();
		wordExport.exportWord(request, response, rootList);
		return null;
	}

	/**
	 * 获取root对象对应的数据结构
	 * 
	 * @param obj
	 * @param desc
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object findRootData(Object obj, String desc)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Object temp = obj;
		String[] arr = StringUtils.split(desc, ".");
		for (int i = 0; i < arr.length; i++) {
			String name = arr[i];
			PropertyDescriptor now = BeanUtils.getPropertyDescriptor(
					temp.getClass(), name);
			if (null == now) {
				throw new RuntimeException("没有在" + temp + "中找到属性配置" + name);
			}
			Method m = now.getReadMethod();
			temp = m.invoke(temp, new Object[] {});
		}
		return temp;
	}

	/**
	 * 设置导出参数
	 * 
	 * @return
	 */
	public String setExportParamters() {
		String title = request.getParameter("excel.gridTitle");
		System.out.println("title---set---:" + title);
		request.setAttribute("title", title);
		return null;
	}

	public String jumpDown() {
		String title = (String) request.getAttribute("title");
		request.setAttribute("title", title);
		return "down";
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeText() {
		return nodeText;
	}

	public void setNodeText(String nodeText) {
		this.nodeText = nodeText;
	}

	public String getNodeDwdm() {
		return nodeDwdm;
	}

	public void setNodeDwdm(String nodeDwdm) {
		this.nodeDwdm = nodeDwdm;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getIsExcel() {
		return isExcel;
	}

	public void setIsExcel(String isExcel) {
		this.isExcel = isExcel;
	}

	public String getYhlx() {
		return yhlx;
	}

	public void setYhlx(String yhlx) {
		this.yhlx = yhlx;
	}

	public List<Object> getYhlxLs() {
		return CommonUtil.getCodeNumber("yhlx", this.getLang(), "DESC", false);
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public String getObjectValue() {
		return objectValue;
	}
}
