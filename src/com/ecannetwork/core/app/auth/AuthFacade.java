package com.ecannetwork.core.app.auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.dto.core.EcanApp;
import com.ecannetwork.dto.core.EcanAuth;

/**
 * 获取用户的权限信息
 * 
 * @author leo
 * 
 */
@Component("authFacade")
public class AuthFacade
{
	@Autowired
	private CommonService commonService;

	/**
	 * 将权限菜单转换为验证权限的set
	 * 
	 * @param menus
	 * @return
	 */
	public Map<String, Menu> authMap(List<Menu> menus)
	{
		Map<String, Menu> map = new HashMap<String, Menu>();
		menuToMap(menus, map);
		return map;
	}

	private void menuToMap(List<Menu> menus, Map<String, Menu> map)
	{
		for (Menu menu : menus)
		{
			if (StringUtils.isNotBlank(menu.getFuncCode()))
			{
				map.put(menu.getAppCode() + "/" + menu.getFuncCode(), menu);
			}

			menuToMap(menu.getSubmenu(), map);
		}
	}

	/**
	 * 某个角色的菜单:::数据库操作，性能较低,不可用于实时请求
	 * 
	 * @param roleId
	 *            roleId为空则为所有菜单，相当于getMenus()
	 * @return
	 */
	public List<Menu> getMenus(String roleId)
	{
		// 获取角色授权的功能或菜单列表
		Set<String> authSet = getAuthedMap(roleId);

		// 获取已经授权的所有菜单
		List<Menu> menus = getAuthedTreeMenu(authSet);

		// 删除空菜单
		removeEmptyMenu(menus.iterator());
		// 排序
		sortMenus(menus);
		return menus;
	}

	/**
	 * 按照idx排序
	 * 
	 * @param menus
	 */
	public void sortMenus(List<Menu> menus)
	{
		if (menus.size() > 0)
		{
			Collections.sort(menus, new Comparator<Menu>()
			{
				@Override
				public int compare(Menu o1, Menu o2)
				{
					return o1.getIdx().compareTo(o2.getIdx());
				}
			});
		}

		for (Menu menu : menus)
		{
			sortMenus(menu.getSubmenu());
		}
	}

	/**
	 * 删除空菜单
	 * 
	 * @param it
	 */
	private void removeEmptyMenu(Iterator<Menu> it)
	{
		while (it.hasNext())
		{
			Menu menu = it.next();
			// 先删子菜单
			removeEmptyMenu(menu.getSubmenu().iterator());

			if (StringUtils.isBlank(menu.getFuncCode())
					&& menu.getSubmenu().size() == 0)
			{// 空菜单
				it.remove();
			}
		}
	}

	/**
	 * 是否需要授权
	 * 
	 * @param appCode
	 * @param funcCode
	 * @return
	 */
	public boolean isNeedAuthed(String appCode, String funcCode)
	{
		return allAuthUri.contains(appCode + "/" + funcCode);
	}

	/**
	 * 所有需要授权的URLgetAppId
	 */
	private Set<String> allAuthUri = new HashSet<String>();

	@PostConstruct
	public void init()
	{
		List<EcanApp> apps = commonService.list(EcanApp.class);
		for (EcanApp app : apps)
		{
			if (StringUtils.isNotBlank(app.getFunCode()))
			{// 功能
				allAuthUri.add(app.getAppCode() + "/" + app.getFunCode());

			}
		}
	}

	@SuppressWarnings("unchecked")
	private List<Menu> getAuthedTreeMenu(Set<String> authSet)
	{
		List<EcanApp> apps = commonService
				.list("from EcanApp t order by t.levelCode");

		/*
		 * <pre> Menu{ appName,appCode, funcCode, funcName; subMenu; } </pre>
		 */
		// 存储一级菜单
		List<Menu> menus = new ArrayList<Menu>();

		// 将菜单构建成树
		Map<String, Menu> temp = new HashMap<String, Menu>();
		for (EcanApp app : apps)
		{
			if (StringUtils.isNotBlank(app.getFunCode()))
			{// 功能
				if (!authSet.contains(app.getId()))
				{// 没有授权，并且非菜单
					continue;
				}
			}

			Menu menu = new Menu(app.getAppCode(), app.getAppName(),
					app.getFunCode(), app.getFunName(), app.getLevelCode(),
					app.getIdx(),app.getOutsideUrl());
			if (app.getLevelCode().length() == EcanApp.LEVEL_CODE_LENGTH)
			{
				menus.add(menu);
			} else
			{
				Menu parent = temp.get(app.getParentLevelCode());
				if (parent != null)
				{
					parent.getSubmenu().add(menu);
					menu.setParent(parent);
				}
			}
			// 放入map 中， 已备子节点索引
			temp.put(app.getLevelCode(), menu);
		}
		return menus;
	}

	/**
	 * 拿到某个角色所有授权的APP编号
	 * 
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Set<String> getAuthedMap(String roleId)
	{
		List<EcanAuth> auths = null;
		if (StringUtils.isNotBlank(roleId))
		{
			auths = commonService.list("from EcanAuth t where t.roleId=?",
					roleId);
		} else
		{
			auths = commonService.list(EcanAuth.class);
		}

		// 将权限初始化为map
		Set<String> authSet = new HashSet<String>();
		for (EcanAuth auth : auths)
		{
			authSet.add(auth.getAppId());
		}
		return authSet;
	}
}
