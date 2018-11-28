package com.shiroSpringboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
	
	@Value("${redis.host}")
	private String host;
	
	@Value("${redis.port}")
	private String port;
	
	@Value("${redis.pool.minIdle}")
	private String minIdle;
	
	@Value("${redis.pool.maxIdle}")
	private String maxIdle;
	
	@Value("${redis.pool.maxActive}")
	private String maxActive;
	
	@Value("${redis.pool.maxWait}")
	private String maxWait;
	
	@Value("${redis.pool.timeOut}")
	private String timeOut;
	
	 /**
     * 连接池配置信息
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(Integer.parseInt(maxActive));
        //最小空闲连接数
        jedisPoolConfig.setMinIdle(Integer.parseInt(minIdle));
       //最大空闲连接数
        jedisPoolConfig.setMaxIdle(Integer.parseInt(maxIdle));
        //当池内没有可用的连接时，最大等待时间
        jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(maxWait));
        //连接超时
        jedisPoolConfig.setMaxWaitMillis(Long.parseLong(timeOut));
        //------其他属性根据需要自行添加-------------
        return jedisPoolConfig;
    }

    /**
     * jedis连接工厂
     * @param jedisPoolConfig
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        //单机版jedis
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName(host);
        //设置默认使用的数据库
        redisStandaloneConfiguration.setDatabase(0);
        //设置密码
       // redisStandaloneConfiguration.setPassword(RedisPassword.of("123456"));
        //设置redis的服务的端口号
        redisStandaloneConfiguration.setPort(Integer.parseInt(port));
        //获得默认的连接池构造器(怎么设计的，为什么不抽象出单独类，供用户使用呢)
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
        //指定jedisPoolConifig来修改默认的连接池构造器（真麻烦，滥用设计模式！）
        jpcb.poolConfig(jedisPoolConfig);
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        //单机配置 + 客户端配置 = jedis连接工厂
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }
    
    
    
    @Bean
    public RedisTemplate<Object,Object> redisTmplate(RedisConnectionFactory redisConnectionFactory ){
    	RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
    	redisTemplate.setConnectionFactory(redisConnectionFactory); 
	    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new  
	    Jackson2JsonRedisSerializer<Object>(Object.class);
	    redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
    	return redisTemplate;
    	
    }
    
    
}
