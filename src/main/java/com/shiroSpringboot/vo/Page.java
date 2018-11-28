package com.shiroSpringboot.vo;

public class Page {
	/**当前页数*/
	private Integer pageNum = 1;
	/**每页显示数量*/
	private Integer pageSize = 10;
	/**总的页数*/
	private Integer pageCount;
	
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	
	
}
