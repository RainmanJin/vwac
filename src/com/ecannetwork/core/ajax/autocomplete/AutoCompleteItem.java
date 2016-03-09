package com.ecannetwork.core.ajax.autocomplete;

import java.io.Serializable;

public class AutoCompleteItem<V, D> implements Serializable
{
	private static final long serialVersionUID = 1L;
	private V value;
	private D data;

	public AutoCompleteItem(V value, D data)
	{
		this.value = value;
		this.data = data;
	}

	public V getValue()
	{
		return value;
	}

	public void setValue(V value)
	{
		this.value = value;
	}

	public D getData()
	{
		return data;
	}

	public void setData(D data)
	{
		this.data = data;
	}

}
