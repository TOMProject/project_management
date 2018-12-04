package com.shiroSpringboot.config.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shiroSpringboot.config.RedisCache;

/**
 * 自定义redis保存session extens abstractSessionDao
 * @author 
 *
 */
@Repository
public class RedisSessionDao extends EnterpriseCacheSessionDAO {
	
	private static final Logger log = LoggerFactory.getLogger(RedisSessionDao.class);

	private static final String SESSION_KEY_PREFIX="sessionkey.";
	@Autowired
	private RedisCache redisCache;
	/**
	 *更新session 
	 */
	@Override
	public void update(Session session) throws UnknownSessionException {
		log.debug("更新session,id=[{}]",session.getId().toString());
		try {
			redisCache.setTimeOut(SESSION_KEY_PREFIX+session.getId().toString(), session, 30);	
		} catch (Exception e) {
			log.error("更新session失败-》，"+e.getMessage(),e);
		}
	}
	/**
	 * 删除session
	 */
	@Override
	public void delete(Session session) {
		log.debug("删除session,id=[{}]",session.getId().toString());
		try {
			redisCache.deleteKey(SESSION_KEY_PREFIX+session.getId().toString());	
		} catch (Exception e) {
			log.error("删除session失败-》"+e.getMessage(),e);
		}
		
	}
	/**
	 * 获取存活的session
	 */
	@Override
	public Collection<Session> getActiveSessions() {
		log.info("获取存活的session");
		try {
			Set<Session> sessions = redisCache.getkeys(SESSION_KEY_PREFIX+"*");
			return sessions;
		} catch (Exception e) {
			log.error("获取存活的session失败"+e.getMessage(),e);
		}
		return null;
	}
	/**
	 * 创建session
	 */
	@Override
	protected Serializable doCreate(Session session) {
		 Serializable sessionId = generateSessionId(session);  
	        assignSessionId(session, sessionId);  
	        log.debug("创建seesion,id=[{}]", session.getId().toString());  
	        try {  
	        	redisCache.setTimeOut(SESSION_KEY_PREFIX+session.getId().toString(), session,30);  
	        } catch (Exception e) {  
	            log.error(e.getMessage(),e);  
	        }  
	        return sessionId; 

	}
	/**
	 * 获取session
	 */
	@Override
	protected Session doReadSession(Serializable sessionId) {
		  log.debug("获取seesion,id=[{}]", sessionId.toString());  
	        Session readSession = null;  
	        try {  
	            readSession=(Session) redisCache.get(SESSION_KEY_PREFIX+sessionId.toString());  
	        } catch (Exception e) {  
	            log.error(e.getMessage());  
	        }  
	        return readSession; 

	}

}
