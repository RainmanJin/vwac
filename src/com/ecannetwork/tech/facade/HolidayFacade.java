package com.ecannetwork.tech.facade;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.DateUtils;
import com.ecannetwork.dto.tech.TechHoliday;

/**
 * 假期服务类:::提供了判定用户是否休假、用户是否本某周有修改等静态方法
 * 
 * @author leo
 * 
 */
@Component
public class HolidayFacade
{
	private static Log log = LogFactory.getLog(HolidayFacade.class);

	@Autowired
	private CommonService commonService;
	/**
	 * 所有的假期缓存:::key=日期_用户编号(公共假期时无用户编号)
	 */
	private static Map<String, TechHoliday> holidays;

	/**
	 * 一个星期中有一天为假期则整周为假期,六日除外。Map<year * 100 + 1-52周编号, Set<用户编号/common>>
	 */
	private static Map<Integer, Set<String>> weekHoliday;

	/**
	 * 初始化方法，更刷新数据库中所有修改记录
	 */
	@SuppressWarnings({ "unchecked" })
	@PostConstruct
	public void init()
	{
		Map<String, TechHoliday> holidays = new HashMap<String, TechHoliday>();
		Map<Integer, Set<String>> weekHoliday = new HashMap<Integer, Set<String>>();

		List<TechHoliday> list = commonService.list(TechHoliday.class);
		for (TechHoliday holiday : list)
		{
			Date date = holiday.getBeginDay();
			Calendar c = Calendar.getInstance();
			c.setTime(date);

			while (date.compareTo(holiday.getEndDay()) <= 0)
			{
				Integer weekOfYear = c.get(Calendar.YEAR) * 100 +  c.get(Calendar.WEEK_OF_YEAR);
				int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

				// 每周的休假人
				Set<String> holidayMenOfWeek = weekHoliday.get(weekOfYear);
				if (holidayMenOfWeek == null)
				{
					holidayMenOfWeek = new HashSet<String>();
					weekHoliday.put(weekOfYear, holidayMenOfWeek);
				}

				if (StringUtils.isBlank(holiday.getUserId()))
				{
					// 公共假期
					holidays.put(DateUtils.formartYMD(date), holiday);
					if (dayOfWeek != 1 && dayOfWeek != 7)
					{// 非周六日
						holidayMenOfWeek.add(PUBLIC_USER_ID);
					}
				} else
				{
					// 个人假期
					holidays.put(
							DateUtils.formartYMD(date) + "_"
									+ holiday.getUserId(), holiday);
					if (dayOfWeek != 1 && dayOfWeek != 7)
					{// 非周六日
						holidayMenOfWeek.add(holiday.getUserId());
					}
				}

				c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
				date = c.getTime();
			}
		}

		HolidayFacade.holidays = holidays;
		HolidayFacade.weekHoliday = weekHoliday;
	}

	private static final String PUBLIC_USER_ID = "common";

	/**
	 * 判定用户是否在休假：：：包含公共假期
	 * 
	 * @param ymd
	 * @param userid
	 * @return
	 */
	public static boolean isHoliday(String ymd, String userid)
	{
		if (!holidays.containsKey(ymd))
		{// 非法定假日
			return holidays.containsKey(ymd + "_" + userid);
		}
		return true;
	}

	/**
	 * 判定是否是公共假日
	 * 
	 * @param ymd
	 * @return
	 */
	public static boolean isHoliday(String ymd)
	{
		return holidays.containsKey(ymd);
	}

	/**
	 * 判定用户是否在休假：：：包含公共假期
	 * 
	 * @param ymd
	 * @param userid
	 * @return
	 */
	public static TechHoliday getHoliday(String ymd, String userid)
	{
		TechHoliday h = holidays.get(ymd);

		if (h == null && StringUtils.isNotBlank(userid))
		{// 非法定假日
			return holidays.get(ymd + "_" + userid);
		}
		return h;
	}

	/**
	 * 判定是否是公共假日
	 * 
	 * @param ymd
	 * @return
	 */
	public static TechHoliday getHoliday(String ymd)
	{
		return holidays.get(ymd);
	}

	/**
	 * 该周是否有公共假期
	 * 
	 * @param week
	 * @return
	 */
	public static boolean isHolidayWeek(Integer year, Integer week)
	{
		return isHolidayWeek(year, week, PUBLIC_USER_ID);
	}

	/**
	 * 该周是否有某个用户的假期
	 * 
	 * @param week
	 *            周编号1-52
	 * @param userid
	 *            用户编号:::公共假期为Holidayfacade.PUBLIC_USER_ID
	 * @return
	 */
	public static boolean isHolidayWeek(Integer year, Integer week, String userid)
	{
		Set<String> set = weekHoliday.get(year * 100 + week);
		if (set != null)
		{
			return set.contains(userid);
		}
		return false;
	}

	public static void main(String[] args)
	{
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DAY_OF_MONTH, 6);
		System.out.println(c.get(Calendar.WEEK_OF_YEAR));
	}
}
