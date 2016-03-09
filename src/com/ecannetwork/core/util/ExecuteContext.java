package com.ecannetwork.core.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ecannetwork.core.app.auth.Menu;
import com.ecannetwork.dto.core.EcanUser;

/**
 * 执行环境:::用于存放一个请求线程内的数据， 确保该数据可以全局读取， 屏蔽掉参数传递造成的代码混乱，但需要慎用，以免造成代码可读性差
 * 
 * @author leo
 * 
 */
public class ExecuteContext
{
	private static final ThreadLocal<Map<String, Object>> local = new ThreadLocal<Map<String, Object>>();

	/**
	 * 初始化
	 */
	public static void init()
	{
		if (local.get() == null)
			local.set(new HashMap<String, Object>());
	}

	/**
	 * 应用路径
	 * 
	 * @return
	 */
	public static String contextPath()
	{
		return ExecuteContext.request().getContextPath();
	}

	/**
	 * 文件路径 yufei,2015-01-25
	 * 
	 * @return
	 */
	public static String realPath()
	{
		return ExecuteContext.request().getRealPath("/");
	}

	/**
	 * 保存一个对象
	 * 
	 * @param key
	 * @param value
	 */
	public static void put(String key, Object value)
	{
		init();
		local.get().put(key, value);
	}

	/**
	 * 放入一个用户
	 * 
	 * @param user
	 */
	public static void put(EcanUser user)
	{
		put(CoreConsts.ExecuteContextKeys.USER, user);
	}

	/**
	 * 获取当前登录的用户：：：登录用户在EcanFilter中已经保存了
	 * 
	 * @return
	 */
	public static EcanUser user()
	{
		return (EcanUser) get(CoreConsts.ExecuteContextKeys.USER);
	}

	/**
	 * 放入request
	 * 
	 * @param user
	 */
	public static void put(HttpServletRequest request)
	{
		put(CoreConsts.ExecuteContextKeys.REQUEST, request);
	}

	/**
	 * 获取当前的HttpServletRequest
	 * 
	 * @return
	 */
	public static HttpServletRequest request()
	{
		return (HttpServletRequest) get(CoreConsts.ExecuteContextKeys.REQUEST);
	}

	/**
	 * 放入一个session
	 * 
	 * @param session
	 */
	public static void put(HttpSession session)
	{
		put(CoreConsts.ExecuteContextKeys.SESSION, session);
	}

	/**
	 * 获取session：：：登录用户在EcanFilter中已经保存了
	 * 
	 * @return
	 */
	public static HttpSession session()
	{
		return (HttpSession) get(CoreConsts.ExecuteContextKeys.SESSION);
	}

	/**
	 * 从执行环境中获取一个对象
	 * 
	 * @param key
	 */
	public static Object get(String key)
	{
		init();
		return local.get().get(key);
	}

	/**
	 * 清理执行环境
	 */
	public static void clean()
	{
		local.remove();
	}

	/**
	 * 设定当前用户访问的菜单
	 * 
	 * @param menu
	 */
	public static void put(Menu menu)
	{
		put(CoreConsts.ExecuteContextKeys.CURRENT_MENU, menu);
	}

	/**
	 * 获取当前的菜单
	 * 
	 * @return
	 */
	public static Menu menu()
	{
		return (Menu) get(CoreConsts.ExecuteContextKeys.CURRENT_MENU);
	}

	/**
	 * 设置当前使用的语言
	 * 
	 * @param lang
	 */
	public static void putCurrentLang(String lang)
	{
		ExecuteContext.put("CURRENT_LANG", lang);
	}

	/**
	 * 当前使用的语言
	 * 
	 * @return
	 */
	public static String getCurrentLang()
	{
		return (String) ExecuteContext.get("CURRENT_LANG");
	}
}
