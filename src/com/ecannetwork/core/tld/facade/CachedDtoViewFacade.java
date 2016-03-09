package com.ecannetwork.core.tld.facade;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecannetwork.core.cache.lru.ExpireLRUCache;
import com.ecannetwork.core.cache.lru.ICacheObjectFacotry;
import com.ecannetwork.core.module.db.dto.DtoSupport;
import com.ecannetwork.core.module.service.CommonService;

/**
 * 根据ID获取某个对象， 为提高性能， 提供了缓存机制<br />
 * 
 * @author leo
 * 
 */
public class CachedDtoViewFacade implements
		ICacheObjectFacotry<String, DtoSupport>
{
	private ExpireLRUCache<String, DtoSupport> expireLruCache;

	private int cacheTimeMS;
	private int cacheCount;

	@PostConstruct
	public void init()
	{
		expireLruCache = new ExpireLRUCache<String, DtoSupport>(cacheTimeMS,
				cacheCount, this);
	}

	@Autowired
	private CommonService commonService;

	public DtoSupport get(String id, String dtoName)
	{
		return expireLruCache.get(dtoName + "|" + id);
	}

	@Override
	public DtoSupport getFreshObject(String k)
	{
		String[] ks = k.split("\\|");
		if (ks.length != 2)
		{
			return null;
		}
		List list = this.commonService.list("from " + ks[0]
				+ " t where t.id = ?", ks[1]);
		if (list.size() > 0)
		{
			return (DtoSupport) list.get(0);
		} else
		{
			return null;
		}
	}

	public int getCacheTimeMS()
	{
		return cacheTimeMS;
	}

	public void setCacheTimeMS(int cacheTimeMS)
	{
		this.cacheTimeMS = cacheTimeMS;
	}

	public int getCacheCount()
	{
		return cacheCount;
	}

	public void setCacheCount(int cacheCount)
	{
		this.cacheCount = cacheCount;
	}

}
