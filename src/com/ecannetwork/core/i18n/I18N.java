package com.ecannetwork.core.i18n;

import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 国际化翻译辅助类
 * 
 * @author leo
 * 
 */
public class I18N
{
	/**
	 * 是否开启国际化
	 */
	public static boolean open;

	private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

	public static void set(Map<String, Object> lang)
	{
		threadLocal.set(lang);
	}

	/**
	 * 带变量的配置文件格式
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public static String parse(String key, Object... args)
	{
		String k = I18N.parse(key);
		k= MessageFormat.format(k, args);
		return k;
	}

	/**
	 * 解析一个国际化的key
	 * 
	 * @param key
	 *            如果没有找到该key对应的文本信息或国际化未开启，将返回key本身
	 * @return
	 */
	public static String parse(String key)
	{
		if (!open)
		{// 可能不支持国际化：：也可能是没有配置国际化信息
			return key;
		}
		if(StringUtils.isBlank(key))
			return key;

		if (!key.startsWith("i18n"))
		{// 必须是i18n开头的
			return key;
		}

		Map<String, Object> map = threadLocal.get();

		String[] keys = key.split("\\.");

		String result = null;
		for (int i = 0; i < keys.length; i++)
		{
			if (i == 0 && keys[i].equals("i18n"))
			{
				continue;
			}

			if (i != keys.length - 1)
			{
				map = (Map<String, Object>) map.get(keys[i]);
				if (map == null)
				{
					break;
				}
			} else
			{
				result = (String) map.get(keys[i]);
			}
		}
		return result == null ? key : result;
	}

	public static void clean()
	{
		threadLocal.remove();
	}
}
