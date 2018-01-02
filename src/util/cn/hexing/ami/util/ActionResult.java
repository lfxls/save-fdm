package cn.hexing.ami.util;

/**
 * 存放dwrAction.doDbWorks的返回信息
 * 
 * @author jun
 * 
 */
public class ActionResult {
	// 操作结果标志
	private boolean success;
	// 返回信息
	private String msg;
	// service层返回对象
	private Object dataObject;

	public Object getDataObject() {
		return dataObject;
	}

	public void setDataObject(Object dataObject) {
		this.dataObject = dataObject;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public void setMsg(String msg, String lang) {
		this.msg = I18nUtil.getText(msg, lang);
	}

	public void setMsg(String msg, String lang, String[] args) {
		this.msg = I18nUtil.getText(msg, lang, args);
	}

	public ActionResult(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	public ActionResult() {
		super();
	}

}
