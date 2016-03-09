package com.ecannetwork.core.cache.lru;

public interface ILRUCache<K, V>
{

	public abstract V get(K k);

	public abstract void refresh(K k, V v);

}