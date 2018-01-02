package cn.hexing.ami.dao.common.pojo.paramData;

public class ParamName {

	private String item_name,lang,hint,fhint;

	public ParamName() {
	}
	
	public ParamName(String item_name, String lang, String hint, String fhint) {
		super();
		this.item_name = item_name;
		this.lang = lang;
		this.hint = hint;
		this.fhint=fhint;
	}

	public String getItem_name() {
		return item_name;
	}

	public String getLang() {
		return lang;
	}

	public String getHint() {
		return hint;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getFhint() {
		return fhint;
	}

	public void setFhint(String fhint) {
		this.fhint = fhint;
	}
	
}
