package com.shiroSpringboot.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shiroSpringboot.mapper.BaseMapper;
import com.shiroSpringboot.service.BaseService;
import com.shiroSpringboot.vo.AjaxResponse;
import com.shiroSpringboot.vo.Page;

@Service
public class BaseServiceImpl<T,DIT> implements BaseService<T, DIT> {

	private static final Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);
	
	@Autowired
	BaseMapper<T,DIT> baseMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		int num = baseMapper.deleteByPrimaryKey(id);
		log.info(new Date()+"删除出了数据"+num+"条");
		return num;
	}

	@Override
	public int insert(T record) {
		int insert = baseMapper.insert(record);
		log.info("新增数据"+insert+"条");
		return insert;
	}

	@Override
	public int insertSelective(T record) {
		int insert = baseMapper.insertSelective(record);
		log.info("选择性新增数据"+insert+"条");
		return insert;
	}

	@Override
	public T selectByPrimaryKey(Integer id) {
		T num = baseMapper.selectByPrimaryKey(id);
		log.info("根据主键id查询数据"+num+"条");
		return num;
	}

	@Override
	public int updateByPrimaryKeySelective(T record) {
		int num = baseMapper.updateByPrimaryKeySelective(record);
		log.info("选择性修改数据"+num+"条");
		return num;
	}

	@Override
	public int updateByPrimaryKey(T record) {
		int num = baseMapper.updateByPrimaryKey(record);
		log.info("根据主键id修改数据"+num+"条");
		return num;
	}
	
	/**
	 * 分页
	 */
	@Override
	public List<T> selectListSelectivePaging(Page page) {
	   int index = page.getPageNum();
	    int size = page.getPageSize();
	    PageHelper.startPage(index, size);
	    List<T> commList = baseMapper.selectListSelectivePaging(page);
	    PageInfo<T> p= new PageInfo<T>(commList) ;
	    List<T> list = p.getList();
	    page.setPageCount((int)p.getTotal());
	    return list;
	
	}

}
