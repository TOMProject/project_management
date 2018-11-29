package com.shiroSpringboot.vo;

import java.io.Serializable;
import java.util.List;
/**
 * 返回分页对象
 * @author 
 *
 * @param <T>
 */
public class ShowPage<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer pageNo; // 当前页
	private Integer pageCount;// 总记录数
	private Integer pageSize; // 每页记录数
	private List<T> list; // 分页数据集
	
	//这里自己添加的有参数的构造方法
	public <T> ShowPage(Page entity, List list) {
		this.pageNo = entity.getPageNum();
		this.pageCount = entity.getPageCount();
		this.pageSize = entity.getPageSize();
		this.list = list;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}



	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
