package com.ecannetwork.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class Configs
{
	private static Properties properties = new Properties();
	static
	{
		try
		{
			properties.load(Configs.class
					.getResourceAsStream("/config.properties"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static String get(String name)
	{
		return properties.getProperty(name);
	}

	/**
	 * 获取一个配置列表，按逗号分割进行划分，并返回成set类型
	 * 
	 * @param name
	 * @return 如果未配置或配置为空，则返回一个空的list
	 */
	public static List<String> getAsList(String name)
	{
		List<String> set = new ArrayList<String>();

		String value = properties.getProperty(name);
		if (StringUtils.isNotBlank(value))
		{
			String[] vs = value.split(",");

			for (String v : vs)
			{
				if (StringUtils.isNotBlank(v.trim()))
					set.add(v.trim());
			}
		}
		return set;
	}

	public static Integer getInt(String name)
	{
		return Integer.valueOf(properties.getProperty(name));
	}

	public static long getLong(String name)
	{
		return Long.valueOf(properties.getProperty(name));
	}
}
