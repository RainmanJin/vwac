package com.ecannetwork.core.app.adm;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.app.auth.AuthFacade;
import com.ecannetwork.core.app.auth.Menu;
import com.ecannetwork.core.app.user.service.UserService;
import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.core.EcanUser;

/**
 * 处理登录与退出
 * 
 * @author leo
 * 
 */
//@RequestMapping("plogin")
public class AdmLoginController extends BaseAdmController
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthFacade authFacade;

	/**
	 * 进入登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginpage(Model model)
	{
		return ADMIN_VIEW + "login";
	}

	/**
	 * 接收登录请求<br />
	 * 返回ajax结果，登陆成功则data中为用户编号，登陆失败则data为错误消息
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody
	AjaxResponse login(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password)
	{
		String msg = null;
		EcanUser user = userService.getByUserName(username);
		if (user == null || !user.getLoginPasswd().equals(password))
		{
			msg = I18N.parse("i18n.login.errorNameOrPassword");
		} else
		{// 登录成功
			if (EcanUser.STATUS.SUSPENDED.equals(user.getStatus()))
			{// 禁用的用户
				msg = I18N.parse("i18n.login.errorNameOrPassword");

			} else
			{
				if (EcanUser.STATUS.INACTIVE.equals(user.getStatus()))
				{// 未激活
					msg = I18N.parse("i18n.login.errorNameOrPassword");
				} else
				{// 超级管理员或正常用户

					// 初始化session信息
					// 用户信息
					ExecuteContext.session().setAttribute(
							CoreConsts.SessionScopeKeys.CURRENT_USER, user);

					// 用户的权限菜单
					List<Menu> menus = authFacade.getMenus(user.getRoleId());

					// 为用户增加首页
					menus.add(0, new Menu("home", "i18n.appname.home", user
							.getRole().getHomeUrl(), "i18n.appname.home",
							"0000", 0));

					// 用户所有的已授权的可访问的功能菜单
					Map<String, Menu> authMap = authFacade.authMap(menus);

					// 菜单列表
					ExecuteContext.session().setAttribute(
							CoreConsts.SessionScopeKeys.MENULIST, menus);
					// 已授权的功能列表:::map结构
					ExecuteContext.session().setAttribute(
							CoreConsts.SessionScopeKeys.AUTHED_MAP, authMap);
				}
			}
		}

		if (msg == null)
		{
			return new AjaxResponse(true, user.getId());
		} else
		{
			return new AjaxResponse(false, msg);
		}
	}

	@RequestMapping("/logout")
	public @ResponseBody
	AjaxResponse ajaxLogout(HttpServletRequest request)
	{
		request.getSession().invalidate();
		return new AjaxResponse();
	}
}
