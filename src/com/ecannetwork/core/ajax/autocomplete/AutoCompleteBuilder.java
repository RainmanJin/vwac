package com.ecannetwork.core.ajax.autocomplete;

import java.util.LinkedList;
import java.util.List;

import com.ecannetwork.core.util.JsonFactory;

public class AutoCompleteBuilder<V, D>
{
	private IAutoCompleteAble<V, D> prepare = null;

	private List<AutoCompleteItem<V, D>> objects = new LinkedList<AutoCompleteItem<V, D>>();

	public AutoCompleteBuilder(IAutoCompleteAble<V, D> prepare)
	{
		this.prepare = prepare;
	}

	public void addObject(D o)
	{
		this.objects.add(new AutoCompleteItem<V, D>(prepare.getValue(o), o));
	}

	/**
	 * 转换成JSON对象
	 * 
	 * @return
	 */
	public String toJSON()
	{
		return JsonFactory.toJson(objects);
	}
}
