package cn.hexing.ami.dao.common.pojo.paramData;

import java.util.List;

public class ParamData {

	private String cate_no,obis,pm_type,buss_type,sort,status,optid,upt_date,value,ulimit,dlimit,level,opID,opType,verId,model,cate_name;
	private String op_type, dlms_data_type,scale,unit,xctpt;
	private String jud_way,pass_flag,param1,param2;
	private List<ParamName> paramNameLs;
	public ParamData(){
		
	}

	public ParamData(String cate_no, String obis, String pm_type,
			String buss_type, String sort, String status, String optid,
			String upt_date, String value, String ulimit, String dlimit,
			String level,  String verId,
			String model, String op_type, String dlms_data_type, String scale,
			String unit, String xctpt, 
			String jud_way, String pass_flag, String param1,
			String param2) {
		this.cate_no = cate_no;
		this.obis = obis;
		this.pm_type = pm_type;
		this.buss_type = buss_type;
		this.sort = sort;
		this.status = status;
		this.optid = optid;
		this.upt_date = upt_date;
		this.value = value;
		this.ulimit = ulimit;
		this.dlimit = dlimit;
		this.level = level;
		this.verId = verId;
		this.model = model;
		this.op_type = op_type;
		this.dlms_data_type = dlms_data_type;
		this.scale = scale;
		this.unit = unit;
		this.xctpt = xctpt;
		this.jud_way = jud_way;
		this.pass_flag = pass_flag;
		this.param1 = param1;
		this.param2 = param2;
	}





	public String getSort() {
		return sort;
	}

	public String getStatus() {
		return status;
	}

	public String getOptid() {
		return optid;
	}

	public String getUpt_date() {
		return upt_date;
	}

	public String getValue() {
		return value;
	}

	public String getUlimit() {
		return ulimit;
	}

	public String getDlimit() {
		return dlimit;
	}

	public String getLevel() {
		return level;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setOptid(String optid) {
		this.optid = optid;
	}

	public void setUpt_date(String upt_date) {
		this.upt_date = upt_date;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setUlimit(String ulimit) {
		this.ulimit = ulimit;
	}

	public void setDlimit(String dlimit) {
		this.dlimit = dlimit;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	public String getOpID() {
		return opID;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpID(String opID) {
		this.opID = opID;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}

	public String getCate_name() {
		return cate_name;
	}

	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}

	public String getCate_no() {
		return cate_no;
	}

	public String getObis() {
		return obis;
	}

	public String getPm_type() {
		return pm_type;
	}

	public String getBuss_type() {
		return buss_type;
	}

	public String getVerId() {
		return verId;
	}

	public void setCate_no(String cate_no) {
		this.cate_no = cate_no;
	}

	public void setObis(String obis) {
		this.obis = obis;
	}
	public void setPm_type(String pm_type) {
		this.pm_type = pm_type;
	}

	public void setBuss_type(String buss_type) {
		this.buss_type = buss_type;
	}

	public void setVerId(String verId) {
		this.verId = verId;
	}
	public String getOp_type() {
		return op_type;
	}
	public String getDlms_data_type() {
		return dlms_data_type;
	}
	public String getScale() {
		return scale;
	}
	public String getUnit() {
		return unit;
	}
	public String getXctpt() {
		return xctpt;
	}
	
	public void setOp_type(String op_type) {
		this.op_type = op_type;
	}
	public void setDlms_data_type(String dlms_data_type) {
		this.dlms_data_type = dlms_data_type;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public void setXctpt(String xctpt) {
		this.xctpt = xctpt;
	}
	


	


	public String getJud_way() {
		return jud_way;
	}


	public String getPass_flag() {
		return pass_flag;
	}


	public String getParam1() {
		return param1;
	}


	public String getParam2() {
		return param2;
	}


	


	public void setJud_way(String jud_way) {
		this.jud_way = jud_way;
	}


	public void setPass_flag(String pass_flag) {
		this.pass_flag = pass_flag;
	}


	public void setParam1(String param1) {
		this.param1 = param1;
	}


	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public List<ParamName> getParamNameLs() {
		return paramNameLs;
	}

	public void setParamNameLs(List<ParamName> paramNameLs) {
		this.paramNameLs = paramNameLs;
	}

 }
