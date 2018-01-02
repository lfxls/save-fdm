package cn.hexing.ami.service.system.ggdmgl;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.system.pojo.ggdmgl.*;
import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;
/**
 * DLMS字典管理
 * @ClassName:DlmszdManagerInf
 * @Description:TODO
 * @author kexl
 * @date 2012-7-16 下午02:57:45
 *
 */
public interface DlmszdManagerInf extends DbWorksInf, QueryInf{
	/**
	 * 查询所有的DLMS规约
	 * @param param
	 * @return
	 */
	public List<Object> getDlmsSub(Map<String, String> param);
	/**
	 * 根据规约ID查询数据项分类
	 * @param param
	 * @return
	 */
	public List<Object> getDlmsDataSort(Map<String, String> param);
	/**
	 * 根据数据项ID查询数据项信息
	 * @param param
	 * @return
	 */
	public List<Object> getDlmsParams(Map<String, String> param);
	/**
	 * 获取父规约ID
	 * @param dlms_sub_protocol
	 * @return
	 */
	public String getParentDlmsSub(Map<String, String> param);
}
