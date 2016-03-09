package com.ecannetwork.core.cache.lru;

import org.apache.commons.collections.map.LRUMap;

public class LRUCache<K, V> implements ILRUCache<K, V>
{
	private ICacheObjectFacotry<K, V> factory = null;
	private org.apache.commons.collections.map.LRUMap lruMap = null;

	public LRUCache(int maxSize, ICacheObjectFacotry<K, V> facotry)
	{
		this.factory = facotry;
		lruMap = new LRUMap(maxSize);
	}

	public V get(K k)
	{
		V v = (V) lruMap.get(k);
		if (v == null)
		{
			v = factory.getFreshObject(k);
			lruMap.put(k, v);
		}

		return v;
	}

	@Override
	public void refresh(K k, V v)
	{
		lruMap.put(k, v);
	}
}
