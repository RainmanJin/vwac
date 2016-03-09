package com.ecannetwork.core.module.db.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

//import sun.org.mozilla.javascript.internal.ObjToIntMap;

/**
 * @author leo
 */
public abstract class DtoSupport implements Serializable
{
	private static final long serialVersionUID = 1L;
	public abstract String getId();

	/**
	 * 扩展属性，主要用于界面现实等的辅助字段
	 */
	private Map<Object, Object> exts = new HashMap<Object, Object>();

	public final Map<Object, Object> getExts()
	{
		return exts;
	}

	public final void setExts(Map<Object, Object> exts)
	{
		this.exts = exts;
	}

	public final void addExts(Object key, Object value)
	{
		exts.put(key, value);
	}

	public final boolean contains(Object key)
	{
		return this.exts.containsKey(key);
	}

	public final Object getExts(Object key)
	{
		return this.exts.get(key);
	}

	public final Object removeExts(Object key)
	{
		return this.exts.remove(key);
	}

}
