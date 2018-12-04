package com.shiroSpringboot.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;

@Repository
public class RedisCache {

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	/**
	 * 定义key序列化的方式
	 * @param redisTemplate
	 */
	@Autowired(required = false)
	public void setRedisTemplate(RedisTemplate<Object,Object> redisTemplate) {
		StringRedisSerializer stringSerializer = new StringRedisSerializer();
	    redisTemplate.setKeySerializer(stringSerializer);
	    redisTemplate.setHashKeySerializer(stringSerializer);
	    this.redisTemplate = redisTemplate;
	}

	/**
	 * 保存redis 单个 value
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}
	/**
	 * redis 获取单个值
	 * @param key
	 */
	public Object get(String key) {
		Object object = redisTemplate.opsForValue().get(key);
		return object;
	}
	
	/**
	 * 保存redis值并且设置 key的过期时间
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public void setTimeOut(String key, Object value ,long timeout) {
		redisTemplate.opsForValue().set(key, value, timeout,TimeUnit.MINUTES);		
	}
	/**
	 * redis根据key删除
	 * @param key
	 */
	public void deleteKey(String key) {
		redisTemplate.delete(key);
	}
	
	/**
	 * 模糊获取redis保存的key
	 * @param keys
	 */
	public Set<Session> getkeys(String keys) {
		
		Set<Session> sessions = new HashSet<>(); 
		Set<Object> keys2 = redisTemplate.keys(keys);
		if(CollectionUtils.isNotEmpty(keys2)) {
			for (Object key : keys2) {
				Session session = (Session) get((String)key);
				sessions.add(session);
			}
		}
		return sessions;
	}
	
	
	/**
	 * redis 保存 list
	 * @param key
	 * @param value
	 */
	public void setList(String key, Object value) {
		redisTemplate.opsForList().leftPush(key, value);
	}
	
	/**
	 * redis 根据key 获取所以list的值
	 * @param key
	 * @return
	 */
	public Object getList(String key) {
		Long size = redisTemplate.opsForList().size(key);
		List<Object> range = (List<Object>) redisTemplate.opsForList().range(key,0,size);
		return range;
	}
		

	/**
	 * 直接使用保存为byte 数组
	 * @param key
	 * @param value 值没有被序列化
	 */
	@SuppressWarnings("unchecked")
	public void setBety(String key ,Object value) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				 RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
	                byte[] k = redisSerializer.serialize(key);
	                byte[] v = JSON.toJSONString(value).getBytes();
	                connection.set(k, v);
				return true;
			}
		});
	}
	
	
	/**
	 * 通过key获取byte数组值，获取后的数据通过fastjson转换
	 * @param key
	 * @return
	 */
	public byte[] getBety(String key) {
		return (byte[]) redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				 RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
	                byte[] k = redisSerializer.serialize(key);    
	                byte[] bs = connection.get(k);
				return bs;
			}
			
		});
		
	}
	
	
	
}
