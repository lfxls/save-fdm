package cn.hexing.ami.dao.common.pojo;

import java.util.ArrayList;
import java.util.List;

public class Page {
	public static final int DEFAULT_PAGE_SIZE = 20;
	
	private int pageSize = DEFAULT_PAGE_SIZE; //每页记录数
	
	private long start;                       //每页第一条记录在list中的位置,从0开始
	private List data;                        //当前页的结果集
	private long totalCount;                  //总记录数
	
	/**
	 * 构造空页
	 */
	public Page(){
		this(0,0,DEFAULT_PAGE_SIZE,new ArrayList());
	}
	/**
	 * 默认构造方法
	 */
	public Page(int start,int totalSize,int pageSize,List data){
		this.pageSize = pageSize;
		this.start = start;
		this.totalCount = totalSize;
		this.data = data;
	}
	/**
	 * 取总页数
	 * @return
	 */
	public long getTotalPageCount(){
		if(totalCount%pageSize == 0){
			return totalCount/pageSize;
		}else{
			return totalCount/pageSize+1;
		}
	}
	/**
	 * 取总记录数
	 * @return
	 */
	public long getTotalCount(){
		return this.totalCount;
	}
	/**
	 * 取该页当前页码，从1开始
	 * @return
	 */
	public long getCurrentPageNo(){
		return start/pageSize + 1;
	}
	
	/**
	 * 是否有下一页
	 * @return
	 */
	public boolean isHasNextPage(){
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}
	
	/**
	 * 该页是否有上一页
	 * @return
	 */
	public boolean isHasPreviousPage(){
		return this.getCurrentPageNo() > 1;
	}
	
	/**
	 * 获取任一页在数据集中的位置
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static int getStartOfPage(int pageNo,int pageSize){
		return (pageNo-1) * pageSize;
	}
	
	public static int getStartOfPage(int pageNo){
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
}
