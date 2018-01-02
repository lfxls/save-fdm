package cn.hexing.ami.dao.main.pojo.hhuService;

import java.io.Serializable;

/**
 * @Description 数据记录
 * @author zhaoyy
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time 2016-4-12
 * @version FDM2.0
 */
public class DataRecord implements Serializable {
	private static final long serialVersionUID = -4729720531476867273L;
	//数据类型：0=变压器，1=参数方案，2=基础代码，3=TOKEN
	private String dataType;
	//操作类型：0=新增，1=修改，2=删除
	private String opType;
	//操作员ID
	private String optID;
	
	//变压器-变压器ID
	private String tfID;
	//变压器-变压器Name
	private String tfName;
	
	//参数方案-分类编码
	private String cateNo;
	//参数方案-分类名称
	private String cate_name;
	//参数方案-参数项编码
	private String obis;
	//参数方案-参数类型
	private String pmType;
	//参数方案-内控版本号
	private String verId;
	
	//基础代码-老代码分类
	private String codeTypeO;
	//基础代码-代码分类
	private String codeType;
	//基础代码-分类名称
	private String cateName;
	//基础代码-老代码值
	private String valueO;
	//基础代码-代码值
	private String value;
	//基础代码-代码名
	private String name;
	//基础代码-语言
	private String lang;
	
	//TOKEN-TOKEN唯一标识
	private String tokenID;
	//TOKEN-token号
	private String token;
	//TOKEN-表号
	private String msn;
	
	//状态：0=待更新，1=已完成
	private String status;
	//操作日期
	private String opDate;
	//操作标识
	private String opID;
	
	public DataRecord(){
		this.dataType = "";
		this.opType = "";
		this.optID = "";
		this.tfID = "";
		this.obis = "";
		this.pmType = "";
		this.codeType = "";
		this.value = "";
		this.lang = "";
		this.status = "";
		this.opDate = "";
	}
	
	public DataRecord(String dataType, String opType, String optID, String status){
		this.dataType = dataType;
		this.opType = opType;
		this.optID = optID;
		this.status = status;
		this.tfID = "";
		this.obis = "";
		this.pmType = "";
		this.codeType = "";
		this.value = "";
		this.lang = "";
		this.opDate = "";
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	public String getOptID() {
		return optID;
	}
	public void setOptID(String optID) {
		this.optID = optID;
	}
	public String getTfID() {
		return tfID;
	}
	public void setTfID(String tfID) {
		this.tfID = tfID;
	}
	
	public String getTfName() {
		return tfName;
	}

	public void setTfName(String tfName) {
		this.tfName = tfName;
	}

	public String getCateNo() {
		return cateNo;
	}
	public void setCateNo(String cateNo) {
		this.cateNo = cateNo;
	}
	public String getObis() {
		return obis;
	}
	public void setObis(String obis) {
		this.obis = obis;
	}
	public String getPmType() {
		return pmType;
	}
	public void setPmType(String pmType) {
		this.pmType = pmType;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpDate() {
		return opDate;
	}

	public void setOpDate(String opDate) {
		this.opDate = opDate;
	}

	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

	public String getOpID() {
		return opID;
	}

	public void setOpID(String opID) {
		this.opID = opID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getCate_name() {
		return cate_name;
	}

	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}

	public String getVerId() {
		return verId;
	}

	public void setVerId(String verId) {
		this.verId = verId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getCodeTypeO() {
		return codeTypeO;
	}

	public void setCodeTypeO(String codeTypeO) {
		this.codeTypeO = codeTypeO;
	}

	public String getValueO() {
		return valueO;
	}

	public void setValueO(String valueO) {
		this.valueO = valueO;
	}
}
