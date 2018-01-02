package cn.hexing.ami.util.cache;

import java.util.HashMap;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

/** 
 * @Description 用户权限菜单缓存
 * @author  jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-5-20
 * @version AMI3.0 
 */
public class RoleMenusCache {
	private static Logger logger = Logger.getLogger(RoleMenusCache.class.getName());
	private Cache cache;

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public Cache getCache() {
		return this.cache;
	}
	
	/**
	 * 数据存放到缓存 
	 * map对象
	 * @param key
	 * @param obj
	 */
	public void putData2Cache(String key,HashMap obj) {
		if(key==null || obj == null) return;
		
		Element element = new Element(key, obj);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		this.cache.put(element);
	}
	
	/**
	 * 数据存放到缓存 
	 * list对象
	 * @param key
	 * @param obj
	 */
	public void putData2Cache(String key,List<?> obj) {
		if(key==null || obj == null) return;
		
		Element element = new Element(key, obj);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		this.cache.put(element);
	}
	
	/**
	 * 删除缓存
	 * @param key
	 */
	public void removeCache(String key) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + key);
		}
		this.cache.remove(key);
	}
	
	/**
	 * 获取缓存数据
	 * @param key
	 * @return
	 */
	public Object getCache(String key) {
		if(key==null || "".equals(key)) return null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Cache get: " + key);
		}
		
		Element result = cache.get(key);
		if(result!=null) {
			return result.getValue(); 
		}
        return null;
	}
}
