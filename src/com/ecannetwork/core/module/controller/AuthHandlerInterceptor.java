package com.ecannetwork.core.module.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ecannetwork.core.app.auth.AuthFacade;
import com.ecannetwork.core.app.auth.Menu;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;

/**
 * 权限拦截器
 * 
 * @author leo
 * 
 */
public class AuthHandlerInterceptor extends HandlerInterceptorAdapter
{
	@Autowired
	private AuthFacade authFacade;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception
	{
		String uri = request.getRequestURI();
		uri = uri.substring(request.getContextPath().length());
		if (StringUtils.isNotBlank(uri))
		{
			String uris[] = uri.split("/");
			if (uris.length == 4)
			{// modeName/appCode/funcCode
				String modelName = uris[1];
				String appCode = uris[2];
				String funcCode = uris[3];

				if (StringUtils.equals("iPadRest", appCode)
						|| StringUtils.equals("rest", appCode) || StringUtils.equals("client", appCode))
				{// fix new rest service for iPad(Mobile Device Trainning tool) or dmp
					return true;
				}

				if (notAuthUri.contains(uri))
				{
					return true;
				}

				String u = appCode + "/" + funcCode;

				// 已授权的应用列表
				Map<String, Menu> authedMap = (Map<String, Menu>) request
						.getSession().getAttribute(
								CoreConsts.SessionScopeKeys.AUTHED_MAP);

				if (authedMap == null)
				{// 没有登录
					response.sendRedirect(ExecuteContext.contextPath()
							+ "/p/login");
					return false;
				} else
				{
					if (authFacade.isNeedAuthed(appCode, funcCode)
							|| "home".equals(appCode))
					{// 已经登录，并且该url是需要授权的

						Menu menu = authedMap.get(u);
						if (menu == null)
						{// 已经登录，但没有该菜单的权限
							response.sendRedirect(ExecuteContext.contextPath()
									+ "/p/login");
							return false;
						} else
						{
							ExecuteContext.put(menu);
							// 当前菜单之所以放置在session中，是因为一个菜单下可能包含多个未注册的请求
							request.getSession().setAttribute(
									CoreConsts.SessionScopeKeys.CURRENT_MENU,
									menu);

						}
					}
				}
			}
		}

		return true;
	}

	/**
	 * 无须登录即可访问的url
	 */
	private Set<String> notAuthUri = new HashSet<String>();

	public Set<String> getNotAuthUri()
	{
		return notAuthUri;
	}

	public void setNotAuthUri(Set<String> notAuthUri)
	{
		this.notAuthUri = notAuthUri;
	}

}
