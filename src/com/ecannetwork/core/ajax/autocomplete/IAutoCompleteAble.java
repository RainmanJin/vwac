package com.ecannetwork.core.ajax.autocomplete;

public interface IAutoCompleteAble<V, D>
{
	// 获取用于进行索引的字段
	public V getValue(D d);
}
