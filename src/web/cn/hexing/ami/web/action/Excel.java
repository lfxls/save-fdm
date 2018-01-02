package cn.hexing.ami.web.action;
/** 
 * @Description 
 * @author  jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-7-14
 * @version AMI3.0 
 */
public class Excel implements java.io.Serializable{
	private String gridHeaders;
	private String gridMethod;
	private String gridRoot;
	private String gridTypes;
	private String gridGrouupHeaders;
	public String getGridGrouupHeaders() {
		return gridGrouupHeaders;
	}
	public void setGridGrouupHeaders(String gridGrouupHeaders) {
		this.gridGrouupHeaders = gridGrouupHeaders;
	}
	public String getGridHeaders() {
		return gridHeaders;
	}
	public void setGridHeaders(String gridHeaders) {
		this.gridHeaders = gridHeaders;
	}
	public String getGridMethod() {
		return gridMethod;
	}
	public void setGridMethod(String gridMethod) {
		this.gridMethod = gridMethod;
	}
	public String getGridRoot() {
		return gridRoot;
	}
	public void setGridRoot(String gridRoot) {
		this.gridRoot = gridRoot;
	}
	public String getGridTypes() {
		return gridTypes;
	}
	public void setGridTypes(String gridTypes) {
		this.gridTypes = gridTypes;
	}
}
