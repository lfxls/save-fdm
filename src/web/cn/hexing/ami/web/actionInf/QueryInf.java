package cn.hexing.ami.web.actionInf;


//查询类接口
public interface QueryInf extends BaseInf{

	// 主查询
	public String query() throws Exception;

	// 查询明细
	public String queryDetail();
}
