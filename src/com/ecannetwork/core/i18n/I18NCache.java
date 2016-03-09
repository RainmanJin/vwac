package com.ecannetwork.core.i18n;

import java.util.HashMap;
import java.util.Map;

public class I18NCache
{
	/**
	 * 存储i18n缓存Map<属性编号，Map<语言编码,值>
	 */
	private Map<String, Map<String, String>> cache = new HashMap<String, Map<String, String>>();

	// 默认语言编码
	private String defaultLang;

	/**
	 * 设置或更新一个i18n属性到缓存中
	 * 
	 * @param lang
	 * @param propertyId
	 * @param value
	 */
	public void set(String lang, String propertyId, String value)
	{
		Map<String, String> map = cache.get(propertyId);
		if (map == null)
		{
			map = new HashMap<String, String>();
			cache.put(propertyId, map);
		}

		map.put(lang, value);
	}

	/**
	 * 获取一个i18n属性:::没有取到对应属性时取默认属性， 默认属性没有取到时，任意取一个排在第一位的属性值返回
	 * 
	 * @param lang
	 * @param propertyId
	 * @return
	 */
	public String get(String lang, String propertyId)
	{
		Map<String, String> map = cache.get(propertyId);

		if (map == null || map.size() == 0)
		{
			return null;
		} else
		{

			String value = map.get(lang);
			if (value == null)
			{
				value = map.get(defaultLang);
				if (value == null)
				{
					value = map.values().iterator().next();
				}
			}
			return value;
		}
	}

	/**
	 * 获取编码的属性值，必须匹配，为空时，不取默认编码的语言
	 * 
	 * @param lang
	 * @param propertyId
	 * @return
	 */
	public String getFixLang(String lang, String propertyId)
	{
		Map<String, String> map = cache.get(propertyId);

		if (map == null || map.size() == 0)
		{
			return null;
		} else
		{
			return map.get(lang);
		}
	}

	/**
	 * 删除某个属性的缓存
	 * 
	 * @param oldPropertyId
	 */
	public void deleteProperty(String oldPropertyId)
	{
		this.cache.remove(oldPropertyId);
	}

	public String getDefaultLang()
	{
		return defaultLang;
	}

	public void setDefaultLang(String defaultLang)
	{
		this.defaultLang = defaultLang;
	}

	public Map<String, Map<String, String>> getCache()
	{
		return this.cache;
	}

	// ////////////////////////////////////////////
	private I18NCache()
	{

	}

	private static I18NCache _instance = new I18NCache();

	public static I18NCache getInstance()
	{
		return _instance;
	}

}
