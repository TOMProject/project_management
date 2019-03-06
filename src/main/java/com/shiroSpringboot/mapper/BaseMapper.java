package com.shiroSpringboot.mapper;

import java.util.List;


import com.shiroSpringboot.vo.Page;

public interface BaseMapper<T,DIT> {

	int deleteByPrimaryKey(Integer id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
    
    List<T> selectListSelective(T recourd);
    
    List<T> selectListSelectivePaging(Page page);
	
}
