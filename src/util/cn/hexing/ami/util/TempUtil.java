package cn.hexing.ami.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class TempUtil {
	public static Logger log = Logger.getLogger(TempUtil.class);
	public static String SPL = "&&&";
	public static String FILEDIR = "comm/";

	/**
	 * 分页取文件
	 * 
	 * @param fileName
	 * @param start
	 * @param pageSize
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Object> filePaging(String fileName, int start,
			int pageSize) throws IOException {
		//只存放一页数据
		start = 0;
		File file = new File(Constants.TMPEFILE+FILEDIR + fileName);
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> rows = new LinkedList<String>();
		List<String> lineList = FileUtils.readLines(file, "UTF-8");
		if(lineList.size()>0){
		//总记录数
		String lastLine = lineList.get(lineList.size()-1);
		lineList.remove(lineList.size()-1);
		
		for (int i=0;  i < start + pageSize; i++) {
			if (i+1<=lineList.size()) {
				String line = lineList.get(i);
				line = line.replaceAll("=", "\":\"");
				line = line.replaceAll(SPL, "\",\"");
				line = "{\"" + line + "\"}";
				rows.add(line);
			}
		}
		
		//lastLine 格式：总记录数,查询耗时
		String[] lastArr = lastLine.split(",");
		result.put("rows", Integer.parseInt(lastArr[0]));
		result.put("result", rows);
		//查询耗时 单位：秒
		result.put("time", lastArr[1]);
		}
		return result;
	}
	
	/**
	 * 
	 * @param fileName
	 * @param lines
	 * @param idColNum
	 *            文件中id的列号
	 * @param idProperty
	 *            id的Map key
	 * @param cover
	 *            覆盖
	 * @param orders
	 *            写入顺序
	 * @param 总记录数
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void listToFile(String fileName, List<?> lines, int idColNum,
			String idProperty, boolean cover, String[] orders,String allRows)
			throws IOException {
		File file = new File(Constants.TMPEFILE+FILEDIR + fileName);
		List<String> newlines = new LinkedList<String>();
		String[] idProperties = null;
		if (lines != null && lines.size() > 0) {
			idProperties = new String[lines.size()];
			for (int i = 0; i < lines.size(); i++) {
				Map<?, ?> m = (Map<?, ?>) lines.get(i);
				oO o = new oO(m, orders);
				newlines.add(o.toString());
				if (!StringUtil.isEmptyString(idProperty)) {
					idProperties[i] = StringUtil.getValue(m.get(idProperty));
				}
			}
		}
		if (file.exists() && !cover) {
			List<String> ls = FileUtils.readLines(file, "UTF-8"); // 验证重复
			try {
				if (ls != null && ls.size() > 0) {
					for (String line : ls) {
						String[] properties = line.split(SPL);
						boolean exist = false;
						String id = properties[idColNum].split("=")[1]; // id列
						if (idProperties != null) {
							for (String d : idProperties) {
								if (d.equals(id)) {
									exist = true;
								}
							}
						}
						if (!exist) { // 加入不重复列
							newlines.add(line);
						}
					}
				}
			} catch (Exception e) {
				log.info(e.getStackTrace());
				e.getStackTrace();
			}
		}
		
		//额外增加一行,总记录数
		newlines.add(String.valueOf(allRows));
		FileUtils.writeLines(file, "UTF-8", newlines); // 写入文件
	}

	/**
	 * 从文件中删除
	 * 
	 * @param fileName
	 * @param idColNum
	 * @param idProperties
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void removeFormFile(String fileName, int idColNum,
			String[] idProperties) throws IOException {
		File file = new File(Constants.TMPEFILE+FILEDIR + fileName);
		List<String> lines = new LinkedList<String>();
		if (file.exists()) {
			List<String> ls = FileUtils.readLines(file, "UTF-8");
			try {
				if (ls != null && ls.size() > 0) {
					for (String line : ls) {
						String[] ids = line.split(SPL);
						boolean remove = false;
						String d = ids[idColNum].split("=")[1];
						for (String id : idProperties) {
							if (id.equals(d)) {
								remove = true;
							}
						}
						if (!remove)
							lines.add(line);
					}
				}
			} catch (Exception e) {
				log.info(e.getStackTrace());
				e.getStackTrace();
			}
			FileUtils.writeLines(file, "UTF-8", lines);
		}
	}

	/**
	 * 删除成功或失败
	 * 
	 * @param fileName
	 * @param fail
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void removeScOrFail(String fileName, int resultColNum,
			boolean fail) throws IOException {
		File file = new File(Constants.TMPEFILE+FILEDIR + fileName);
		List<String> lines = new LinkedList<String>();
		if (file.exists()) {
			List<String> ls = FileUtils.readLines(file, "UTF-8");
			try {
				if (ls != null && ls.size() > 0) {
					for (String line : ls) {
						String[] ids = line.split(SPL);
						boolean remove = false;
						if (ids.length >= resultColNum) { // 确保有结果
							if (ids[resultColNum].split("=").length >= 2) {
								String d = ids[resultColNum].split("=")[1];
								if (fail && d.indexOf("成功") == -1) { // 删除失败
									remove = true;
								}
								if (!fail && d.indexOf("成功") > -1) { // 删除成功
									remove = true;
								}
							}
						}
						if (!remove) {
							lines.add(line);
						}
					}
				}
			} catch (Exception e) {
				log.info(e.getStackTrace());
				e.getStackTrace();
			}
			FileUtils.writeLines(file, "UTF-8", lines);
		}
	}

	/**
	 * 比较某列内容并删除
	 * 
	 * @param fileName
	 * @param delColNum
	 * @param compare
	 *            （比较内容）
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void removeRecord(String fileName, int delColNum,
			String compare) throws IOException {
		File file = new File(Constants.TMPEFILE+FILEDIR + fileName);
		List<String> lines = new LinkedList<String>();
		if (file.exists()) {
			List<String> ls = FileUtils.readLines(file, "UTF-8");
			try {
				if (ls != null && ls.size() > 0) {
					for (String line : ls) {
						String[] ids = line.split(SPL);
						boolean remove = false;
						if (ids.length >= delColNum) { // 确保有此列
							if (ids[delColNum].split("=").length >= 2) {
								String d = ids[delColNum].split("=")[1];
								if (compare.equals(d)) { // 删除
									remove = true;
								}
							}
						}
						if (!remove) {
							lines.add(line);
						}
					}
				}
			} catch (Exception e) {
				log.info(e.getStackTrace());
				e.getStackTrace();
			}
			FileUtils.writeLines(file, "UTF-8", lines);
		}
	}

	/**
	 * 虚拟全选的时候调用文件，取里面所有的ID列
	 * 
	 * @param fileName
	 * @param idColNum
	 *            -- 第几列（虚拟全选的时候选择的唯一性标识--zdjh，bjjh等）
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static String getIdFromFile(String fileName, int idColNum)
			throws IOException {
		String idArray = "";
		File file = new File(Constants.TMPEFILE+FILEDIR + fileName);
		if (file.exists()) {
			List<String> ls = FileUtils.readLines(file, "UTF-8"); // 验证重复
			try {
				if (ls != null && ls.size() > 0) {
					for (String line : ls) {
						String[] idProperties = line.split(SPL);
						String id = idProperties[idColNum].split("=")[1];
						idArray += id + ",";
					}
				}
			} catch (Exception e) {
				log.info(e.getStackTrace());
				e.getStackTrace();
			}
		}

		if (StringUtil.isEmptyString(idArray)) {
			return idArray;
		} else {
			return idArray = idArray.substring(0, idArray.length() - 1);
		}
	}
	
	/**
	 * 根据
	 * @param fileName
	 * @param recId
	 * @return
	 * @throws IOException
	 */
	public static String getIdFromFileByName(String fileName, String recName)throws IOException {
		String idArray = "";
		File file = new File(Constants.TMPEFILE+FILEDIR + fileName);
		if (file.exists()) {
			List<String> ls = FileUtils.readLines(file, "UTF-8"); // 验证重复
			try {
				if (ls != null && ls.size() > 0) {
					for (String line : ls) {
						String[] idProperties = line.split(SPL);
						for (int i = 0; i < idProperties.length; i++) {
							String[] tmpIds = idProperties[i].split("=");
							String idValue = tmpIds[0];
							String id = tmpIds[1];
							if (recName.equals(idValue)) {
								idArray += id + ",";
								break;
							}
						}
					}
				}
			} catch (Exception e) {
				log.info(e.getStackTrace());
				e.getStackTrace();
			}
		}
		
		if (StringUtil.isEmptyString(idArray)) {
			return idArray;
		} else {
			return idArray = idArray.substring(0, idArray.length() - 1);
		}

	}

	/**
	 * 将匹配内容重写入文件
	 * 
	 * @param file
	 * @param idColNum
	 * @param resultMap
	 * @param totalCol
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void addToFile(String fileName, int idColNum,
			Map<String, String> resultMap, int totalCol) throws IOException {
		List<String> newlines = new LinkedList<String>();
		File file = new File(Constants.TMPEFILE+FILEDIR + fileName);
		if (file.exists()) {
			List<String> ls = FileUtils.readLines(file, "UTF-8"); // 验证重复
			try {
				if (ls != null && ls.size() > 0) {
					for (String line : ls) {
						String[] idProperties = line.split(SPL);
						// 对操作行进行设置时会增加列的情况，兼容其他行
						if (idProperties != null
								&& idProperties.length > idColNum) {
							String id = idProperties[idColNum].split("=")[1];
							String replaceStr = resultMap.get(id);
							if (!StringUtil.isEmptyString(replaceStr)) { // 补结果
								line = toLine(idProperties, totalCol, "rp")
										+ SPL + replaceStr;
							} else {
								line = toLine(idProperties, totalCol, ""); // 不补的把以前的干掉
							}
						}
						newlines.add(line);
					}
				}
			} catch (Exception e) {
				log.info(e.getStackTrace());
			}
		}

		FileUtils.writeLines(file, "UTF-8", newlines);
	}

	private static String toLine(String[] str, int total, String sfRp) {
		String line = "";
		int len;
		if (str.length < total) {
			len = str.length;
		} else {
			len = total;
		}
		for (int i = 0; i < len; i++) {
			line += str[i] + SPL;
		}
		return line.substring(0, line.length() - 3);
	}

	/**
	 * 清空文件
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public static void clearFile(String fileName) throws IOException {
		File f = new File(Constants.TMPEFILE+FILEDIR + fileName);
		if (!f.exists()) {
			return;
		}
		RandomAccessFile rf = null;
		try {
			rf = new RandomAccessFile(Constants.TMPEFILE+FILEDIR + fileName, "rw");
			rf.setLength(0);
		} catch (Exception e) {
			log.error(StringUtil.getExceptionDetailInfo(e));
		} finally {
			if (rf != null) {
				rf.close();
			}
		}
	}

	/**
	 * 把文件里的内容转化为List(里面是Map) 导出Excel用
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> getFileToList(String fileName)
			throws IOException {
		List<Object> ls = new ArrayList<Object>();
		File file = new File(Constants.TMPEFILE+FILEDIR + fileName);
		if (file.exists()) {
			List<String> list = FileUtils.readLines(file, "UTF-8"); // 验证重复
			try {
				if (list != null && list.size() > 0) {
					for (String line : list) {
						Map<String, Object> resMap = new HashMap<String, Object>();
						String[] idProperties = line.split(SPL);
						for (String idPropertie : idProperties) {
							String id = idPropertie.split("=")[0];
							String value = idPropertie.split("=")[1];

							resMap.put(id, value);
						}

						ls.add(resMap);
					}
				}
			} catch (Exception e) {
				log.info(e.getStackTrace());
				e.getStackTrace();
			}
		}
		return ls;
	}
}

/**
 * 重写Map的toString
 * 
 * @author Administrator
 * 
 */
class oO {
	private Map<?, ?> o = null;
	private String[] b = null;
	private static String SPL = "&&&";

	public oO(Map<?, ?> mp, String[] orderToFile) {
		o = mp;
		b = orderToFile;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (o != null && o.size() > 0) {
			if (b == null) {
				Iterator<?> it = o.keySet().iterator();
				String k = (String) it.next();
				sb.append(k + "=" + o.get(k));
				while (it.hasNext()) {
					k = (String) it.next();
					sb.append(SPL + k + "=" + o.get(k));
				}
			} else {
				sb.append(b[0] + "=" + o.get(b[0]));
				for (int j = 1; j < b.length; j++) {
					String orederName = b[j];
					if (o.get(orederName) != null) {
						sb.append(SPL + orederName + "=" + o.get(orederName));
					} else {
						sb.append(SPL + orederName + "=");
					}
				}
			}
		}
		String s = sb.toString().replaceAll("\r\n", "");
		return s;
	}
}
