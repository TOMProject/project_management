package com.shiroSpringboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisCache {
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	/**
	 * 保存redis value
	 * @param key
	 * @param value
	 */
	public void set(String key,Object value) {
	 redisTemplate.opsForValue().set(key, value);
	}
}
