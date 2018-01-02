package cn.hexing.ami.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.dao.common.pojo.TreeNode;
import cn.hexing.ami.serviceInf.QzszManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;
import cn.hexing.ami.web.listener.AppEnv;

/**
 * @Description 群组设置
 * @author jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-8-15
 * @version AMI3.0
 */
public class QzszAction extends BaseAction implements DbWorksInf, QueryInf {

	private static Logger logger = Logger.getLogger(QzszAction.class.getName());
	private static final long serialVersionUID = -7434783112973139417L;
	private String qzfl, qzm, cjkssj, cjjssj, qzid, qzlx, hyid, ydsx, sfgxs;

	private QzszManagerInf qzszManager;

	private String selectAllFlg;
	private String totalCount;
	private String nodeType;
	private String nodeId;
	private String nodeDwdm;
	private String nodeText;
	private String tariffGroup,voltageGroup;
	private List<Object> tariffGroupLs  = null;
	private List<Object>  voltageGroupLs = null;
	private String type;
	private String zd;
	private String zxsj;
	private String zxzt;
	private String yxrq;
	private String jh;
	private String sjid;
	private String zdjh;
	private String bjjh;
	private String yhh;
	private String yhm;
	private String smzq;
	private String hh, hm;

	private String csbm, bjzt, bjlx, bjgylx, cjfs, zdgylx, zdzt, zdlx, bjms, gddy, jxfs;
	private List<Object> qzmcList = null;
	private List<Object> bjztLst = null;// 表计状态
	private List<Object> bjlxLst = null;// 表计类型
	private List<Object> csLst = null;// 厂商

	private List<Object> bjgylxLst = null;// 表计规约类型
	private List<Object> cjfsLst = null;// 采集方式
	private List<Object> zdgylxLst = null;// 终端规约类型
	private List<Object> zdztLst = null;// 终端状态
	private List<Object> zdlxLst = null;// 终端状态
	private List<Object> bjmsLs = null;// 表计模式
	private List<Object> jxfsLst = null;// 接线方式
	private List<Object> gddyLst = null;// 供电电压
	private File Upload;// 上传
	private String treeType; // 树类型 01机构 02行业 03用电属性

	/**
	 * 初始化主页面
	 */
	public String init() {
		setCjkssj(DateUtil.getMonth(new Date(), -3));
		setCjjssj(DateUtil.getToday());
		zd = getText("common.qzsz.zd");
		bj = getText("common.qzsz.bj");
		HashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("zd", zd);
		map.put("bj", bj);

		request.setAttribute("sbMap", map);
		return SUCCESS;
	}

	/**
	 * 新增群组
	 * 
	 * @return
	 */
	public String initNewGroup() {
		yxrq = StringUtil.isEmptyString(yxrq) ? DateUtil.getCurrentlyDate() : yxrq;
		return "newGroup";
	}

	/**
	 * 数据库操作
	 */
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		Map<String, Object> p = new HashMap<String, Object>();
		ActionResult re = new ActionResult(true,"");
		p.putAll(param);
		if (("00001" + Constants.DELOPT).equals(czid)) {//yaoj add，use for delete meters in one certain group
			re = qzszManager.delBjsInGroup(czid,param);
		}
		else{
			re = qzszManager.doDbWorks(czid, param);
		}
		return re;
	}
	
	/**
	 * 数据库日志
	 */
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		qzszManager.dbLogger(czid, cznr, czyId, unitCode);
	}

	/**
	 * 页面查询
	 */
	public String query() throws Exception {
		Map<String, Object> newMap = new HashMap<String, Object>();
		if (!StringUtil.isEmptyString(qzm)){
			newMap.put("qzm", qzm);
		}
		newMap.put("czyid", this.getCzyid());
		newMap.put("qzlx", qzlx);
		newMap.put("cjkssj", cjkssj);
		newMap.put("cjjssj", cjjssj);
		newMap.put(Constants.APP_LANG, getLang());
		Map<String, Object> re = qzszManager.query(newMap, start, limit, dir,
				sort, isExcel);
		responseGrid(re);
		return null;
	}

	/**
	 * 加入群组是查询群组
	 */
	public String queryDetail() {
		Map<String, Object> newMap = new HashMap<String, Object>();
		if (!StringUtil.isEmptyString(qzm)){
			newMap.put("qzm", qzm);
		}
		newMap.put("czyid", this.getCzyid());
		newMap.put(Constants.APP_LANG, getLang());
		Map<String, Object> re = qzszManager.queryDetailQz(newMap, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}

	/**
	 * 更新群组 弹出window框 修改
	 * 
	 * @return
	 */
	public String updateGroup() {
		List<Object> ls = qzszManager.getQzDetail(qzid);

		if (ls != null && ls.size() != 0) {
			Map<String, Object> result = (Map<String, Object>) ls.get(0);
			qzm = String.valueOf(result.get("QZMC"));
			sfgxs = String.valueOf(result.get("SFGX"));
			smzq = StringUtil.isEmptyString(String.valueOf(result.get("SMZQ"))) ? "0" : String.valueOf(result .get("SMZQ"));
		} else {
			qzm = "";
			sfgxs = "";
			smzq="";
			
		}
		return "updateQz";
	}

	/**
	 * 批量xls导入（群组）
	 * 
	 * @return
	 */
	public String batchUpload() {
		ActionResult result = new ActionResult();
		FileInputStream fis = null;
		try {
			List<Object> reLs = new ArrayList<Object>();
			if (Upload != null) {
				fis = new FileInputStream(Upload);
				String sjid = qzszManager.getSeq();
				reLs = parseExcel(fis,sjid);
				Upload.delete();
				qzszManager.insqzsj(reLs);
				result.setDataObject(sjid);
			}

			result.setSuccess(true);
		} catch (Exception ex) {
			result.setSuccess(false);
			logger.error("Group module import excle an exception occurs:"
					+ StringUtil.getExceptionDetailInfo(ex));
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException ex) {
					logger.error("Group module import excle(close FileInputStream) an exception occurs:"
							+ StringUtil.getExceptionDetailInfo(ex));
				}
			}
		}

		// 输出信息
		response.setContentType("text/html;charset=UTF-8");
		try {
			response.getWriter().print(
					"{success:'" + result.isSuccess() + "',msgType:'"
							+ result.getDataObject() + "', errMsg:'"
							+ result.getMsg() + "'}");
		} catch (IOException e) {
			logger.error(StringUtil.getExceptionDetailInfo(e));
		}

		return null;
	}

	/**
	 * 解析批量导入名单
	 * 
	 * @param fis
	 * @param lang
	 * @return
	 */
	private List<Object> parseExcel(FileInputStream fis,String sjid) {
		Workbook workbook = null;
		List<Object> reLs = new ArrayList<Object>();
		String ids = "";
		// 暂时只读取第一个sheet的内容
		try {
			workbook = Workbook.getWorkbook(fis);
			Sheet sheet = workbook.getSheet(0);
			int rowNum = sheet.getRows();

			for (int i = 1; i < rowNum; i++) {
				Cell[] row = sheet.getRow(i);
				String cellValue = row[0] == null ? "" : row[0].getContents();
				Map<String, String> in = new HashMap<String, String>();
				if (!StringUtil.isEmptyString(cellValue)) {
					in.put("jh", cellValue);
					in.put("sjid", sjid);
					reLs.add(in);
				}
			}
		} catch (Exception e) {
			logger.error("batch import exception occurs:"
					+ StringUtil.getExceptionDetailInfo(e));
		} finally {
			if (workbook != null) {
				workbook.close();
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error(StringUtil.getExceptionDetailInfo(e));
				}
			}
		}
		return reLs;
	}

	/**
	 * 更新群组 弹出window框 修改
	 * 
	 * @return
	 */
	public String getUpdateGrid() {
		Map<String, Object> newMap = new HashMap<String, Object>();
		newMap.put("qzfl", qzfl);
		newMap.put("qzid", qzid);
		newMap.put("qzlx", qzlx);

		Map<String, Object> re = qzszManager.getUpdateGrid(newMap, start,
				limit, dir, sort, isExcel);

		// 设备类型字段显示国际化
		List<Map<String, String>> sblxLs = getZslxLs();
		List<Object> resultNewList = new ArrayList<Object>();
		List<Object> resultList = (List<Object>) re.get("result");
		for (int i = 0; i < resultList.size(); i++) {
			Map<String, Object> rowMap = (Map<String, Object>) resultList.get(i);
			String sblx = String.valueOf(rowMap.get("SBLX"));
			for (int j = 0; j < sblxLs.size(); j++) {
				Map<String, String> tmpMap = sblxLs.get(j);
				String tmpBmStr = tmpMap.get("BM");
				String tmpMcStr = tmpMap.get("MC");
				if (sblx.equals(tmpBmStr)) {
					rowMap.put("SBLXMC", tmpMcStr);
				}
			}
			resultNewList.add(rowMap);
		}

		resultList = null;
		re.put("result", resultNewList);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 根据户号，户名查询信息
	 * 
	 * @return
	 */
	public String qeryForYh() {
		if (!StringUtil.isEmptyString(hh) || !StringUtil.isEmptyString(hm)) {
			Map<String, Object> newMap = new HashMap<String, Object>();
			if (!StringUtil.isEmptyString(hh)) {
				newMap.put("hh", hh);
			}
			if (!StringUtil.isEmptyString(hm)) {
				newMap.put("hm", hm);
			}
			newMap.put("qzfl", qzfl);
			newMap.put("qzlx", qzlx);
			DatabaseUtil.fwdwFilter(newMap, this.getCzyid(), this.getFwbj(),
					this.getBm(), "YH");
			responseJson(qzszManager.queryForYh(newMap, start, limit, dir,
					sort, isExcel), true);
		}
		return null;
	}

	/**
	 * mdm群组表计
	 * @return
	 */
	public String initMdmQzBj() {
		bjztLst = CommonUtil.getCode("dbzt", this.getLang(), true);
		bjlxLst = CommonUtil.getCodeNumber("dblx", this.getLang(), "ASC", true);
		csLst = CommonUtil.getCode("bjcs", this.getLang(), true);
		bjgylxLst = CommonUtil.getCode("dbgylx", this.getLang(), true);
		bjmsLs = CommonUtil.getCode("bjms", this.getLang(), true);
		jxfsLst = CommonUtil.getCode("jxfs", this.getLang(), true);
		gddyLst=CommonUtil.getCode("dydj", this.getLang(), true);
		
		nodeText = StringUtil.isEmptyString(nodeText) ? getUnitName() : nodeText;
		nodeId = StringUtil.isEmptyString(nodeId) ? getUnitCode() : nodeId;
		nodeType = StringUtil.isEmptyString(nodeType) ?"dw" : nodeType;
//		tariffGroupLs  = qzszManager.getAllTariff();
//		voltageGroupLs =  CommonUtil.getCode("meter_dydj", this.getLang(), false);
		return "mdm_qzbj";
	}
	
	/**
	 * mdm群组终端
	 * 
	 * @return
	 */
	public String initMdmQzZd() {
		Map<String, String> sysPara = (Map<String, String>) AppEnv.getObject(Constants.SYSPARA);
		zxsj = sysPara.get("SBZXJKZXSJ");
		zxztlst = CommonUtil.getCode("ywzt", this.getLang(), false);
		zdlxLst = CommonUtil.getCode("zdlx", this.getLang(), true);
		zdgylxLst = CommonUtil.getCode("zdgylx", this.getLang(), true);
		zdztLst = CommonUtil.getCode("zdzt", this.getLang(), true);
		csLst = CommonUtil.getCode("bjcs", this.getLang(), true);
		cjfsLst = CommonUtil.getCode("cjfs", this.getLang(), true);
		
		nodeText = StringUtil.isEmptyString(nodeText) ? getUnitName() : nodeText;
		nodeId = StringUtil.isEmptyString(nodeId) ? getUnitCode() : nodeId;
		nodeType = StringUtil.isEmptyString(nodeType) ?"dw" : nodeType;
		gddyLst=CommonUtil.getCode("dydj", this.getLang(), true);
		return "mdm_qzzd";
	}
	
	/**
	 * 批量导入
	 * @return
	 */
	public String initBatch() {
		return "qzpldr";
	}

	/**
	 * 查询终端
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMdmQzZd() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!StringUtil.isEmptyString(nodeId)) {
			param.put("nodeId", nodeId);
		}
		if (!StringUtil.isEmptyString(type)) {// 导入
			param.put("type", type);
		}
		if (!StringUtil.isEmptyString(sjid)) {// 
			param.put("sjid", sjid);
		}
		if (!StringUtil.isEmptyString(zxzt)) {
			param.put("zxzt", zxzt);
		}
		if (!StringUtil.isEmptyString(nodeType)) {
			param.put("nodeType", nodeType);
		}
		if (!StringUtil.isEmptyString(zxsj)) {
			param.put("zxsj", zxsj);
		}
		if (!StringUtil.isEmptyString(zdlx)) {
			param.put("zdlx", zdlx);
		}
		if (!StringUtil.isEmptyString(zdgylx)) {
			param.put("zdgylx", zdgylx);
		}
		if (!StringUtil.isEmptyString(csbm)) {
			param.put("csbm", csbm);
		}
		if (!StringUtil.isEmptyString(cjfs)) {
			param.put("cjfs", cjfs);
		}
		if (!StringUtil.isEmptyString(zdzt)) {
			param.put("zdzt", zdzt);
		}if (!StringUtil.isEmptyString(zdjh)) {
			param.put("zdjh", zdjh);
		}

		// 加入语言
		param.put(Constants.APP_LANG, this.getLang());
		// 单位权限过滤
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, getCzyid(),
				getFwbj(), getBm(), "ZD");
		Map<String, Object> re = new HashMap<String, Object>();
		re = qzszManager.queryDetail(param, start, limit, dir, sort, isExcel);
		responseGrid(re);

		return null;
	}

	/**
	 * 表计查询
	 * @return
	 * @throws Exception
	 */
	public String queryMdmQzBj() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

		if (!StringUtil.isEmptyString(nodeId)) {
			param.put("nodeId", nodeId);
		}
		if (!StringUtil.isEmptyString(type)) {
			param.put("type", type);
		}
		if (!StringUtil.isEmptyString(sjid)) { 
			param.put("sjid", sjid);
		}
		if (!StringUtil.isEmptyString(nodeType)) {
			param.put("nodeType", nodeType);
		}
		if (!StringUtil.isEmptyString(bjlx)) {
			param.put("bjlx", bjlx);
		}
		if (!StringUtil.isEmptyString(bjgylx)) {
			param.put("bjgylx", bjgylx);
		}
		if (!StringUtil.isEmptyString(csbm)) {
			param.put("csbm", csbm);
		}
		if (!StringUtil.isEmptyString(bjms)) {
			param.put("bjms", bjms);
		}
		if (!StringUtil.isEmptyString(bjzt)) {
			param.put("bjzt", bjzt);
		}
		if (!StringUtil.isEmptyString(jxfs)) {
			param.put("jxfs", jxfs);
		}
		if (!StringUtil.isEmptyString(bjjh)) {
			param.put("bjjh", bjjh);
		}
		if (!StringUtil.isEmptyString(hyid)) {
			param.put("hyid", hyid);
		}
		if (!StringUtil.isEmptyString(ydsx)) {
			param.put("ydsx", ydsx);
		}
		if (!StringUtil.isEmptyString(gddy)) {
			param.put("gddy", gddy);
		}
		if (!StringUtil.isEmptyString(yhh)) {
			param.put("yhh", yhh);
		}
		if (!StringUtil.isEmptyString(yhm)) {
			param.put("yhm", yhm);
		}
		
		/*
		if (!StringUtil.isEmptyString(tariffGroup)) {
			param.put("tariffGroup", tariffGroup);
		}
		if (!StringUtil.isEmptyString(voltageGroup)) {
			param.put("voltageGroup", voltageGroup);
		}
		*/
		// 加入语言
		param.put(Constants.APP_LANG, this.getLang());
		// 单位权限过滤
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, getCzyid(),
						getFwbj(), getBm(),
						nodeType.equals("bj") || nodeType.equals("bjqz") ? "bj" : "cld");
		Map<String, Object> re = new HashMap<String, Object>();
		
		//yaoj 2014.1.6 屏蔽群组根节"Group"对其他单位表计的信息泄漏
		if(nodeType.equals("qzRoot")){
			return null;
		}
		re = qzszManager.queryDetailQzBj(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		
		return null;
	}

	/**
	 * 加入群组
	 * @return
	 */
	public String initMdmjrqz() {
		yxrq = StringUtil.isEmptyString(yxrq) ? DateUtil.getCurrentlyDate(): yxrq;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("qzfl", qzlx.equals("zd") ? "01" : "02");
		param.put("czyid", this.getCzyid());
		qzmcList = qzszManager.getQzMc(param);
		return "mdm_jrqz";
	}
	
	/**
	 * 初始化树
	 * @return
	 */
	public String initTree() {
		return "tree";
	}
	
	/**
	 * 获取树
	 * @return
	 */
	public String getTree() {
		List<TreeNode> tree = null;
		if ("hy".equals(treeType)) {
			if("root".equals(nodeId)) {
				nodeId = "0000";
			}
			tree = qzszManager.getHyTree(nodeId,this.getLang());
		} else if ("sx".equals(treeType)) {
			tree = qzszManager.getYdsxTree(this.getLang());
		}
		responseJson(tree, false);
		return null;
	}
	
    /**
     * 保存加入群组
     * @param param
     * @param util
     * @return
     */
	public ActionResult save(Map<String, String> param, Util util) {
		return qzszManager.save(param, util);
	}

	public String getYxrq() {
		return yxrq;
	}

	public void setYxrq(String yxrq) {
		this.yxrq = yxrq;
	}

	public String getTariffGroup() {
		return tariffGroup;
	}

	public void setTariffGroup(String tariffGroup) {
		this.tariffGroup = tariffGroup;
	}

	public List<Object> getTariffGroupLs() {
		return tariffGroupLs;
	}

	public void setTariffGroupLs(List<Object> tariffGroupLs) {
		this.tariffGroupLs = tariffGroupLs;
	}

	public String getVoltageGroup() {
		return voltageGroup;
	}

	public void setVoltageGroup(String voltageGroup) {
		this.voltageGroup = voltageGroup;
	}

	public List<Object> getVoltageGroupLs() {
		return voltageGroupLs;
	}

	public void setVoltageGroupLs(List<Object> voltageGroupLs) {
		this.voltageGroupLs = voltageGroupLs;
	}

	public List<Object> getGddyLst() {
		return gddyLst;
	}

	public void setGddyLst(List<Object> gddyLst) {
		this.gddyLst = gddyLst;
	}

	public String getHyid() {
		return hyid;
	}

	public void setHyid(String hyid) {
		this.hyid = hyid;
	}

	public String getYdsx() {
		return ydsx;
	}
	public void setYdsx(String ydsx) {
		this.ydsx = ydsx;
	}

	public String getGddy() {
		return gddy;
	}

	public void setGddy(String gddy) {
		this.gddy = gddy;
	}

	public String getTreeType() {
		return treeType;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}

	public String getYhh() {
		return yhh;
	}

	public void setYhh(String yhh) {
		this.yhh = yhh;
	}

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}
	
	public String getNodeText() {
		return nodeText;
	}

	public void setNodeText(String nodeText) {
		this.nodeText = nodeText;
	}
	
	public String getZdjh() {
		return zdjh;
	}

	public void setZdjh(String zdjh) {
		this.zdjh = zdjh;
	}

	public String getBjjh() {
		return bjjh;
	}

	public void setBjjh(String bjjh) {
		this.bjjh = bjjh;
	}
	
	public String getSfgxs() {
		return sfgxs;
	}

	public void setSfgxs(String sfgxs) {
		this.sfgxs = sfgxs;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getSjid() {
		return sjid;
	}

	public void setSjid(String sjid) {
		this.sjid = sjid;
	}
	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeDwdm() {
		return nodeDwdm;
	}

	public void setNodeDwdm(String nodeDwdm) {
		this.nodeDwdm = nodeDwdm;
	}

	public String getSelectAllFlg() {
		return selectAllFlg;
	}

	public void setSelectAllFlg(String selectAllFlg) {
		this.selectAllFlg = selectAllFlg;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getJxfs() {
		return jxfs;
	}

	public void setJxfs(String jxfs) {
		this.jxfs = jxfs;
	}

	public List<Object> getJxfsLst() {
		return jxfsLst;
	}

	public void setJxfsLst(List<Object> jxfsLst) {
		this.jxfsLst = jxfsLst;
	}

	public String getCsbm() {
		return csbm;
	}

	public void setCsbm(String csbm) {
		this.csbm = csbm;
	}

	public String getBjzt() {
		return bjzt;
	}

	public void setBjzt(String bjzt) {
		this.bjzt = bjzt;
	}

	public String getBjlx() {
		return bjlx;
	}

	public void setBjlx(String bjlx) {
		this.bjlx = bjlx;
	}

	public String getBjgylx() {
		return bjgylx;
	}

	public void setBjgylx(String bjgylx) {
		this.bjgylx = bjgylx;
	}

	public String getCjfs() {
		return cjfs;
	}

	public void setCjfs(String cjfs) {
		this.cjfs = cjfs;
	}

	public String getZdgylx() {
		return zdgylx;
	}

	public void setZdgylx(String zdgylx) {
		this.zdgylx = zdgylx;
	}

	public String getZdzt() {
		return zdzt;
	}

	public void setZdzt(String zdzt) {
		this.zdzt = zdzt;
	}

	public String getZdlx() {
		return zdlx;
	}

	public void setZdlx(String zdlx) {
		this.zdlx = zdlx;
	}

	public String getBjms() {
		return bjms;
	}

	public void setBjms(String bjms) {
		this.bjms = bjms;
	}

	public QzszManagerInf getQzszManager() {
		return qzszManager;
	}

	public List<Object> getQzmcList() {
		return qzmcList;
	}

	public void setQzmcList(List<Object> qzmcList) {
		this.qzmcList = qzmcList;
	}

	public String getJh() {
		return jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public String getZxzt() {
		return zxzt;
	}

	public void setZxzt(String zxzt) {
		this.zxzt = zxzt;
	}

	private List<Object> zxztlst = null;

	public List<Object> getZxztlst() {
		return zxztlst;
	}

	public void setZxztlst(List<Object> zxztlst) {
		this.zxztlst = zxztlst;
	}

	public String getZd() {
		return zd;
	}

	public void setZd(String zd) {
		this.zd = zd;
	}

	public String getBj() {
		return bj;
	}

	public String getZxsj() {
		return zxsj;
	}

	public void setZxsj(String zxsj) {
		this.zxsj = zxsj;
	}

	public void setBj(String bj) {
		this.bj = bj;
	}

	private String bj;

	public void setQzszManager(QzszManagerInf qzszManager) {
		this.qzszManager = qzszManager;
	}

	public String getQzm() {
		return qzm;
	}

	public void setQzm(String qzm) {
		this.qzm = qzm;
	}

	public String getCjkssj() {
		return cjkssj;
	}

	public void setCjkssj(String cjkssj) {
		this.cjkssj = cjkssj;
	}

	public String getCjjssj() {
		return cjjssj;
	}

	public void setCjjssj(String cjjssj) {
		this.cjjssj = cjjssj;
	}

	public String getQzid() {
		return qzid;
	}

	public void setQzid(String qzid) {
		this.qzid = qzid;
	}

	public String getQzfl() {
		return qzfl;
	}

	public void setQzfl(String qzfl) {
		this.qzfl = qzfl;
	}

	public String getQzlx() {
		return qzlx;
	}

	public void setQzlx(String qzlx) {
		this.qzlx = qzlx;
	}
	
	public List<Object> getBjztLst() {
		return bjztLst;
	}

	public void setBjztLst(List<Object> bjztLst) {
		this.bjztLst = bjztLst;
	}

	public List<Object> getBjlxLst() {
		return bjlxLst;
	}

	public void setBjlxLst(List<Object> bjlxLst) {
		this.bjlxLst = bjlxLst;
	}

	public List<Object> getCsLst() {
		return csLst;
	}

	public void setCsLst(List<Object> csLst) {
		this.csLst = csLst;
	}

	public List<Object> getBjgylxLst() {
		return bjgylxLst;
	}

	public void setBjgylxLst(List<Object> bjgylxLst) {
		this.bjgylxLst = bjgylxLst;
	}

	public List<Object> getCjfsLst() {
		return cjfsLst;
	}

	public void setCjfsLst(List<Object> cjfsLst) {
		this.cjfsLst = cjfsLst;
	}

	public List<Object> getZdgylxLst() {
		return zdgylxLst;
	}

	public void setZdgylxLst(List<Object> zdgylxLst) {
		this.zdgylxLst = zdgylxLst;
	}

	public List<Object> getZdztLst() {
		return zdztLst;
	}

	public void setZdztLst(List<Object> zdztLst) {
		this.zdztLst = zdztLst;
	}

	public List<Object> getZdlxLst() {
		return zdlxLst;
	}

	public void setZdlxLst(List<Object> zdlxLst) {
		this.zdlxLst = zdlxLst;
	}

	public List<Object> getBjmsLs() {
		return bjmsLs;
	}

	public void setBjmsLs(List<Object> bjmsLs) {
		this.bjmsLs = bjmsLs;
	}
	public String getHh() {
		return hh;
	}

	public void setHh(String hh) {
		this.hh = hh;
	}

	public String getHm() {
		return hm;
	}

	public void setHm(String hm) {
		this.hm = hm;
	}

	public File getUpload() {
		return Upload;
	}

	public void setUpload(File upload) {
		Upload = upload;
	}
	
	public String getSmzq() {
		return smzq;
	}

	public void setSmzq(String smzq) {
		this.smzq = smzq;
	}
}
