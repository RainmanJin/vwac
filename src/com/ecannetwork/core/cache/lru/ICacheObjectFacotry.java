package com.ecannetwork.core.cache.lru;

public interface ICacheObjectFacotry<K, V>
{
	/**
	 * 获取对象
	 * 
	 * @param k
	 * @return
	 */
	public V getFreshObject(K k);
}
