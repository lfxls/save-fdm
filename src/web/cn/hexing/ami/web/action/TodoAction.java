package cn.hexing.ami.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.inf.ToDoInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.actionInf.QueryInf;

/** 
 * @Description  xxxxx
 * @author  xxx
 * @Copyright 2014 hexing Inc. All rights reserved
 * @time：2014-7-17
 * @version AMI3.0 
 */
public class TodoAction extends BaseAction implements QueryInf {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3486584031945097767L;
	private String allCnt;
	private String dbzt, ywlx;
	private List<Object> flLs = null;
	private List<Object> ywlxLs = null;
	private List<Object> dbztLs = null;
	private ToDoInf toDoInf = null;
	
	/**
	 * 
	 * @return
	 */
	public String init() {
		ywlx = StringUtil.isEmptyString(ywlx)? "":ywlx;
		dbzt = StringUtil.isEmptyString(dbzt)? "0":dbzt;
		ywlxLs = CommonUtil.getCode("todotype", this.getLang(), true);
		dbztLs = CommonUtil.getCode("db_zt", this.getLang(), true);
		return SUCCESS;
	}

	public String query() throws Exception {
		//统一待办处理
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("czyId", this.getCzyid());
		param.put("ywlx", ywlx);
		param.put("dbzt", dbzt);
		param.put(Constants.APP_LANG, this.getLang());
		Map<String, Object> re = toDoInf.query(param, start, limit, dir,
				sort, isExcel);
		responseGrid(re);
		return null;
	}

	@Override
	public String queryDetail() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ActionResult initTodo(Map<String, String> param, Util util) {
		ActionResult re = new ActionResult(true, "");
		//统一待办处理
		Map<String, String> p = new HashMap<String, String>();
		p.put("czyId", param.get(Constants.CURR_STAFFID));
		p.put(Constants.APP_LANG, param.get(Constants.APP_LANG));
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("allCnt", toDoInf.getTodoCnt(p));
		ret.put("flLs", toDoInf.getTodoFl(p));
		re.setDataObject(ret);
		return re;
	}

	public String getDbzt() {
		return dbzt;
	}

	public void setDbzt(String dbzt) {
		this.dbzt = dbzt;
	}

	public String getYwlx() {
		return ywlx;
	}

	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}

	public List<Object> getYwlxLs() {
		return ywlxLs;
	}

	public void setYwlxLs(List<Object> ywlxLs) {
		this.ywlxLs = ywlxLs;
	}

	public List<Object> getDbztLs() {
		return dbztLs;
	}

	public void setDbztLs(List<Object> dbztLs) {
		this.dbztLs = dbztLs;
	}

	public String getAllCnt() {
		return allCnt;
	}

	public void setAllCnt(String allCnt) {
		this.allCnt = allCnt;
	}

	public List<Object> getFlLs() {
		return flLs;
	}

	public void setFlLs(List<Object> flLs) {
		this.flLs = flLs;
	}

	public ToDoInf getToDoInf() {
		return toDoInf;
	}

	public void setToDoInf(ToDoInf toDoInf) {
		this.toDoInf = toDoInf;
	}

}

