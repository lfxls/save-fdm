package cn.hexing.ami.util;

import java.util.HashMap;
import java.util.Map;

public class DatabaseUtil {
	
	/**
	 * 访问单位权限控制传参
	 * 
	 * @param paramMap
	 * @return
	 */
	public static void fwdwFilterString(Map<String, String> paramMap, String czyid,
			String fwbj, String bmid, String mainTable) {
		if (paramMap == null)
			return;
		paramMap.put("fwbj", fwbj);
		if ("0".equals(fwbj)) {
			paramMap.put("bmid", bmid);
		} else {
			paramMap.put("czyid", czyid);
		}
		// admin 特殊处理
		if ("admin".equals(czyid)) {
			paramMap.put("fwbj", "-1");
		}
		paramMap.put("main", mainTable);
	}
	
	/**
	 * 访问单位权限控制传参
	 * 
	 * @param paramMap
	 * @return
	 */
	public static void fwdwFilter(Map<String, Object> paramMap, String czyid,
			String fwbj, String bmid, String mainTable) {
		if (paramMap == null)
			return;
		paramMap.put("fwbj", fwbj);
		if ("0".equals(fwbj)) {
			paramMap.put("bmid", bmid);
		} else {
			paramMap.put("czyid", czyid);
		}
		// admin 特殊处理
		if ("admin".equals(czyid)) {
			paramMap.put("fwbj", "-1");
		}
		paramMap.put("main", mainTable);
	}
	/**
	 * 节点过滤权限控制组参
	 * 
	 * @param queryInfo
	 * @return
	 */
	public static Map<String, String> nodeFilter(Map<String, String> paramMap,String mainTable) {
		if (paramMap == null) {
			paramMap = new HashMap<String, String>();
		}
		paramMap.put("main", mainTable);
		
		String nodeId = StringUtil.getValue(paramMap.get("nodeId"));
		String nodeType = StringUtil.getValue(paramMap.get("nodeType"));
		String nodeDwdm = StringUtil.getValue(paramMap.get("nodeDwdm"));
		String czyid = StringUtil.getValue(paramMap.get(Constants.CURR_STAFFID));
		String fwbj = StringUtil.getValue(paramMap.get(Constants.CURR_RIGTH));
		String bmid = StringUtil.getValue(paramMap.get(Constants.CURR_DEPT));
		paramMap.put("nodeId", nodeId);
		paramMap.put("nodeType", nodeType);
		if (!StringUtil.isEmptyString(nodeDwdm)) {
			paramMap.put("nodeDwdm", nodeDwdm + "%");
		}
		if ("dw".equals(nodeType)) { // 单位
			paramMap.put("nodeId", nodeId + "%");
			paramMap.remove("nodeDwdm");
			fwdwFilterString(paramMap, czyid, fwbj, bmid, mainTable);
		} else if ("bdz".equals(nodeType)) { // 变电站

		} else if ("xl".equals(nodeType)) { // 线路

		} else if ("tq".equals(nodeType)) { // 台区

		} else if ("qz".equals(nodeType)) { // 群组

		} else if ("sb".equals(nodeType)) { // 设备

		} else if ("cx".equals(nodeType)) { // 查询
			fwdwFilterString(paramMap, czyid, fwbj, bmid, mainTable);
		}
		return paramMap;
	}
	/**
	 * 节点过滤权限控制组参
	 * 
	 * @param queryInfo
	 * @return
	 */
	public static Map<String, Object> nodeFilter(Map<String, Object> paramMap,
			String nodeId, String nodeType, String nodeDwdm, String czyid,
			String fwbj, String bmid, String mainTable) {
		if (paramMap == null) {
			paramMap = new HashMap<String, Object>();
		}
		
		//处理变压器线损情况
		if(null != nodeType && nodeType.equals("gbbyq")){
			nodeType = "byq";
		}
		
		paramMap.put("main", mainTable);
		paramMap.put("nodeId", nodeId);
		paramMap.put("nodeType", nodeType);
		if (!StringUtil.isEmptyString(nodeDwdm)) {
			paramMap.put("nodeDwdm", nodeDwdm + "%");
		}
		if ("dw".equals(nodeType)) { // 单位
			paramMap.put("nodeId", nodeId + "%");
			paramMap.remove("nodeDwdm");
			fwdwFilter(paramMap, czyid, fwbj, bmid, mainTable);
		} else if ("bdz".equals(nodeType)) { // 变电站

		} else if ("xl".equals(nodeType)) { // 线路

		} else if ("tq".equals(nodeType)) { // 台区

		} else if ("qz".equals(nodeType)) { // 群组

		} else if ("sb".equals(nodeType)) { // 设备

		} else if ("cx".equals(nodeType)) { // 查询
			fwdwFilter(paramMap, czyid, fwbj, bmid, mainTable);
		}
		return paramMap;
	}
	
	/**
	 * 统计公共过滤
	 * @param dwdm
	 * @param dwjb
	 * @param sfxz
	 * @param czyid
	 * @param fwbj
	 * @param bmid
	 * @param mainTable
	 * @return
	 */
	public static Map<String,Object> tj_qxkz(String dwdm, String dwjb, String sfxz, String czyid,
			String fwbj, String bmid, String mainTable) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("dwdm", dwdm);
		if("06".equals(dwjb)) {
			param.put("dwjb", dwjb);
		} else {
			int jb = Integer.valueOf(dwjb) + 1;
			param.put("dwjb", "0" + jb);  //下级级别
		}
		if("TRUE".equals(sfxz)) { 
			param.put("sfxz", sfxz);
			param.put("main", mainTable);
		} else { //非下转则加入单位过滤
			fwdwFilter(param, czyid, fwbj, bmid, mainTable);
		}
		return param;
	}
}
