package com.ecannetwork.tech.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.EcanUserToken;

/**
 * 处理登录与退出
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping
public class LoginController
{
	@Autowired
	private UserService userService;
	@Autowired
	private AuthFacade authFacade;

	@Autowired
	private CommonService commonService;

	/**
	 * 进入登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginpage(Model model)
	{
		EcanUser user = ExecuteContext.user();
		if (user != null && user.getRole() != null)
		{// 已经登录的用户不能进入登录页面
			return "redirect:" + "/techc/home/" + user.getRole().getHomeUrl();
		}

		model.addAttribute("isLoginView", "1");

		return "login";
	}

	/**
	 * 接收登录请求
	 * 
	 * @param model
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password)
	{
		//System.out.println("yufeia-20150118");

		EcanUser user = ExecuteContext.user();
		if (user != null && user.getRole() != null)
		{// 已经登录的用户不能进入登录页面
			return "redirect:" + "/techc/home/" + user.getRole().getHomeUrl();
		}

		//
		user = userService.getByUserName(username);
		if (user == null || !user.getLoginPasswd().equals(password))
		{
			model.addAttribute("msg",
					I18N.parse("i18n.login.errorNameOrPassword"));
		}
		else
		{// 登录成功
			if (EcanUser.STATUS.SUSPENDED.equals(user.getStatus()))
			{// 禁用的用户
				model.addAttribute("msg",
						I18N.parse("i18n.login.errorNameOrPassword"));
			}
			else
			{
				if (EcanUser.STATUS.INACTIVE.equals(user.getStatus()))
				{// 未激活
					ExecuteContext.session().setAttribute("inactiveUser", user);
					return "redirect:/p/active";
				}
				else
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

					String token = UUID.randomUUID();
					ExecuteContext.session().setAttribute("token", token);
					// DMP跳转
					dmpLink(user, token);

					return "redirect:" + "/techc/home/"
							+ user.getRole().getHomeUrl();
				}
			}
		}

		model.addAttribute("isLoginView", "1");
		return "login";
	}

	private void dmpLink(EcanUser user, String token)
	{
		if (Configs.getInt("dmp.enable") == 1)
		{// 开启了dmp
			// String token = UUID.randomUUID();

			ExecuteContext.session().setAttribute("dmpEnable", Boolean.TRUE);
			String url = Configs.get("dmp.loginUrl");
			if (url.indexOf('?') == -1)
			{
				url += "?token=" + token;
			}
			else
			{
				url += "&token=" + token;
			}
			ExecuteContext.session().setAttribute("dmpURL", url);

			EcanUserToken t = (EcanUserToken) this.commonService.get(
					user.getId(), EcanUserToken.class);
			if (t == null)
			{
				t = new EcanUserToken();
				t.setId(user.getId());
				t.setToken(token);
				t.setUpdateTime(new Date());
				this.commonService.saveTX(t);
			}
			else
			{
				t.setToken(token);
				t.setUpdateTime(new Date());
				this.commonService.updateTX(t);
			}
		}
	}

	/**
	 * 进入激活页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/active", method = RequestMethod.GET)
	public String active(Model model)
	{
		EcanUser user = (EcanUser) ExecuteContext.session().getAttribute(
				"inactiveUser");

		// Fix user center modify userinfo
		EcanUser u = new EcanUser();
		u.setId(user.getId());
		u.setLoginName(user.getLoginName());
		u.setLoginPasswd(user.getLoginPasswd());
		ExecuteContext.session().setAttribute(
				CoreConsts.SessionScopeKeys.CURRENT_USER, u);

		// fix auth intecepter

		ExecuteContext.session().setAttribute(
				CoreConsts.SessionScopeKeys.AUTHED_MAP,
				new HashMap<String, Menu>());
		// user info for profile page
		if (user != null)
		{
			model.addAttribute("curUser", user);
			model.addAttribute("head_img", user.getHeadImg());
		}
		model.addAttribute("activePage", Boolean.TRUE);
		model.addAttribute("isLoginView", "1");
		return "tech/usercenter/index";
	}

	/**
	 * 激活
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/actived")
	public String activeUser(Model model)
	{
		EcanUser u = ExecuteContext.user();
		EcanUser dto = (EcanUser) this.commonService.get(u.getId(),
				EcanUser.class);
		dto.setStatus(EcanUser.STATUS.ACTIVE);
		this.commonService.updateTX(dto);

		ExecuteContext.session().removeAttribute("inactiveUser");

		return this.login(model, dto.getLoginName(), dto.getLoginPasswd());
	}

	@RequestMapping("/logout")
	public String logout(Model model, HttpServletRequest request)
	{
		request.getSession().invalidate();
		return "redirect:/p/login";
	}

	@RequestMapping("/ajaxLogout")
	public @ResponseBody
	AjaxResponse ajaxLogout(HttpServletRequest request)
	{
		request.getSession().invalidate();
		return new AjaxResponse();
	}
}
