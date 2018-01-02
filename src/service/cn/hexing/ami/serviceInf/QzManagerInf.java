package cn.hexing.ami.serviceInf;

import java.util.List;
import java.util.Map;

public interface QzManagerInf extends DbWorksInf{
	List<Object> allGroup(Map<String,Object> paramMap);
	List<Object> getList(Map<String, Object> param);
	List<Object> getAdvZdjh(Map<String,Object> paramMap);
}
