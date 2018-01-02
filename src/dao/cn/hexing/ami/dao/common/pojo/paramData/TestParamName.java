package cn.hexing.ami.dao.common.pojo.paramData;

public class TestParamName {
	private String  lang,tiName,fhint;

	
	public TestParamName() {
	}

	public TestParamName(String lang, String tiName, String fhint) {
		super();
		this.lang = lang;
		this.tiName = tiName;
		this.fhint = fhint;
	}

	public String getLang() {
		return lang;
	}

	public String getTiName() {
		return tiName;
	}

	public String getFhint() {
		return fhint;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public void setTiName(String tiName) {
		this.tiName = tiName;
	}

	public void setFhint(String fhint) {
		this.fhint = fhint;
	}
	
	
}
