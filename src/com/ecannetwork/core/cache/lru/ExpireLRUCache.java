package com.ecannetwork.core.cache.lru;

import org.apache.commons.collections.map.LRUMap;

/**
 * 可失效的LRU缓存队列
 * 
 * @author leo
 * 
 * @param <K>
 * @param <V>
 */
public class ExpireLRUCache<K, V> implements ILRUCache<K, V>
{
	private int keepAliveMs;
	private ICacheObjectFacotry<K, V> factory = null;
	private LRUMap lruMap = null;

	public ExpireLRUCache(int keepAliveMs, int maxSize,
			ICacheObjectFacotry<K, V> facotry)
	{
		this.keepAliveMs = keepAliveMs;
		this.factory = facotry;

		this.lruMap = new LRUMap(maxSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abane.core.cache.lru.ILRUCache#get(K)
	 */
	@Override
	public V get(K k)
	{
		ExpireCacheItem<V> item = (ExpireCacheItem<V>) lruMap.get(k);
		V v = null;
		if (item != null)
			v = item.get(keepAliveMs);

		if (v == null)
		{
			v = factory.getFreshObject(k);
			if (v != null)
				this.lruMap.put(k, new ExpireCacheItem<V>(v));
		}

		return v;
	}

	@Override
	public void refresh(K k, V v)
	{
		this.lruMap.put(k, new ExpireCacheItem<V>(v));
	}

}
