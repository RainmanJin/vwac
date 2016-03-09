package com.ecannetwork.core.cache.lru;

public class ExpireCacheItem<V>
{
	private long incomeTime;
	private V cachedObject;

	public ExpireCacheItem(V v)
	{
		this.cachedObject = v;
		this.incomeTime = System.currentTimeMillis();
	}

	/**
	 * 未失效则返回，否则返回null
	 * 
	 * @param keepAliveMs
	 * @return
	 */
	public V get(long keepAliveMs)
	{
		return System.currentTimeMillis() - incomeTime < keepAliveMs ? cachedObject
				: null;
	}
}
