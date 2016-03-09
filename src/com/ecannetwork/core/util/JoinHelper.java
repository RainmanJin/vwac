package com.ecannetwork.core.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class JoinHelper
{
	
	public static String joinToSql(Collection<String> coll)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("'");
		for (String s : coll)
		{
			sb.append(s).append("','");
		}
		String s = sb.toString();
		return s.substring(0, s.length() - 2);
	}

	/**
	 * 合并数组为一个字符串
	 * 
	 * @param results
	 * @param split
	 * @return
	 */
	public static String join(String[] results, String split)
	{
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < results.length; i++)
		{
			sb.append(results[i]);
			if (i != results.length - 1)
			{
				sb.append(split);
			}
		}

		return sb.toString();
	}

	/**
	 * 合并数组为一个字符串
	 * 
	 * @param set
	 * @param split
	 * @return
	 */
	public static String join(Set<String> set, String split)
	{
		StringBuilder sb = new StringBuilder();

		int i = 0;
		for (Iterator<String> it = set.iterator(); it.hasNext();)
		{
			sb.append(it.next());
			if (i != set.size() - 1)
			{
				sb.append(split);
			}
		}

		return sb.toString();
	}

}
