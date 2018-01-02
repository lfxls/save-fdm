package cn.hexing.ami.service.main.preMgt;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.pojo.pre.Token;
import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;
import cn.hexing.ami.util.ActionResult;

public interface TokenMgtManagerInf extends DbWorksInf, QueryInf {
	/**
	 * Excel导入
	 * @param fis
	 * @param param
	 * @param archivesType
	 * @param lang
	 * @return
	 */
	public ActionResult parseExcel(FileInputStream fis, Map<String, String> param, String importType, String lang);
	
	/**
	 * 获取所有的Token
	 * @return
	 */
	public List<Object> getAllToken();
	
	/**
	 * 获取所有的Token
	 * @return
	 */
	public List<Object> getPARTToken(String optid);
	/**
	 * 修改Token，包括表号、Token、执行状态
	 * @return
	 */
	public ActionResult UptToken(List<Token> tks);
	
	/**
	 * @Description 编辑Token获取信息
	 * @param param
	 * @return
	 */
	public Map<String,Object> getToken(Map<String, Object> param);
	
}
