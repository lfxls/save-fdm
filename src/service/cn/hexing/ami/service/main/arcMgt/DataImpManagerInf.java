package cn.hexing.ami.service.main.arcMgt;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.util.ActionResult;

public interface DataImpManagerInf  {
	public ActionResult parseExcel(FileInputStream fis,Map<String, String> param,String archivesType,String lang);
	public String saveArchivesData(String archivesType,List<Object> archivesList,String lang);
	public List<Object> getCode(String codeType,String value);
}
