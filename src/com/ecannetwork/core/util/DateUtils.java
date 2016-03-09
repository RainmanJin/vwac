package com.ecannetwork.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期助手类
 * 
 * @author liulibin
 * 
 */
public class DateUtils
{
	public static Date getNextMonth()
	{
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONDAY, 1);
		return c.getTime();
	}

	/**
	 * 获取当前年
	 * 
	 * @return
	 */
	public static Integer getYear()
	{
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 获取当前年
	 * 
	 * @return
	 */
	public static Integer getMonth()
	{
		return Calendar.getInstance().get(Calendar.MONTH);
	}

	/**
	 * 获取当前年
	 * 
	 * @return
	 */
	public static Integer getDay()
	{
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 解析日期
	 * 
	 * @param ymd
	 *            yyyy-MM-dd
	 * @return
	 */
	public static Date parseY_M_D(String ymd)
	{
		try
		{
			return sdfY_M_D.parse(ymd);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析日期
	 * 
	 * @param ymd
	 *            格式为yyyyMMdd
	 * @return
	 */
	public static Date parseYMD(String ymd)
	{
		try
		{
			return sdfYMD.parse(ymd);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化日期:yyyyMMdd
	 * 
	 * @param date
	 * @return
	 */
	public static String formartYMD(Date date)
	{
		return sdfYMD.format(date);
	}

	/**
	 * 格式化日期：yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formartY_M_D(Date date)
	{
		return sdfY_M_D.format(date);

	}

	public static Calendar firstWeekOfYear(Integer year)
	{
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.WEEK_OF_YEAR, 1);
		c.set(Calendar.DAY_OF_WEEK, 1);

		if (c.get(Calendar.YEAR) < year)
		{// 计算一年的第一个星期一
			c.add(Calendar.WEEK_OF_YEAR, 1);
		}
		return c;
	}
	
	public static void main(String[] args)
	{
		System.out.println(DateUtils.formartY_M_D(DateUtils.firstWeekOfYear(2012).getTime()));
		System.out.println(DateUtils.formartY_M_D(DateUtils.weekOfYear(2012, 15).getTime()));
	}

	public static Calendar weekOfYear(Integer year, Integer week)
	{
		Calendar c = DateUtils.firstWeekOfYear(year);
		c.add(Calendar.WEEK_OF_YEAR, week - 1);
		return c;
	}

	private static final SimpleDateFormat sdfY_M_D = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final SimpleDateFormat sdfYMD = new SimpleDateFormat(
			"yyyyMMdd");

}
