package cn.hexing.ami.dao.common.pojo.paramData;

import java.util.List;

public class TestParam {
	//测试参数项列表
	private List<ParamData> paramList;
	private List<TestParamName> testParamNameList;
	//测试项名称，失败提示信息，测试项ID，状态，排序，操作ID，操作类型，严重等级，分类编码
	private String tiid,status,sort,opID,opType,level,cate_no,m_model,verid,cate_name;
	


	public TestParam() {
	}
	
	
	public TestParam(List<ParamData> paramList,
			String tiid, String status, String sort, String level,
			String cate_no, String m_model, String verid) {
		super();
		this.paramList = paramList;
		this.tiid = tiid;
		this.status = status;
		this.sort = sort;
		this.level = level;
		this.cate_no = cate_no;
		this.m_model = m_model;
		this.verid = verid;
	}


	public List<ParamData> getParamList() {
		return paramList;
	}
	public String getTiid() {
		return tiid;
	}
	public String getStatus() {
		return status;
	}
	public String getSort() {
		return sort;
	}
	public String getOpID() {
		return opID;
	}
	public String getOpType() {
		return opType;
	}
	public String getLevel() {
		return level;
	}
	public String getCate_no() {
		return cate_no;
	}
	public void setParamList(List<ParamData> paramList) {
		this.paramList = paramList;
	}

	public void setTiid(String tiid) {
		this.tiid = tiid;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public void setOpID(String opID) {
		this.opID = opID;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public void setCate_no(String cate_no) {
		this.cate_no = cate_no;
	}

	public String getM_model() {
		return m_model;
	}



	public void setM_model(String m_model) {
		this.m_model = m_model;
	}
	public String getVerid() {
		return verid;
	}


	public void setVerid(String verid) {
		this.verid = verid;
	}


	public List<TestParamName> getTestParamNameList() {
		return testParamNameList;
	}


	public void setTestParamNameList(List<TestParamName> testParamNameList) {
		this.testParamNameList = testParamNameList;
	}


	public String getCate_name() {
		return cate_name;
	}


	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}
	
	
	
}
