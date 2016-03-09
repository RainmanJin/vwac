package com.ecannetwork.core.i18n;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.ecannetwork.core.app.domain.DomainFacade;
import com.ecannetwork.core.util.BeanContextUtil;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;

/**
 * 处理i18n
 * 
 * @author leo
 * 
 */
public class I18nFilter implements Filter
{

	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException
	{
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse resp = (HttpServletResponse) arg1;
		HttpSession session = req.getSession();

		// 加入jsp支持
		Map<String, Object> langs = i18nInit(req, resp, session);

		// 加入代码支持
		I18N.set(langs);
		try
		{
			arg2.doFilter(arg0, arg1);
		} finally
		{
			I18N.clean();
		}
	}

	private Map<String, Object> i18nInit(HttpServletRequest req,
			HttpServletResponse resp, HttpSession session)
	{
		Map<String, Object> langs;
		if (i18nFacade == null)
		{
			i18nFacade = (I18nFacade) BeanContextUtil.applicationContext
					.getBean("i18nFacade");
			domainFacade = (DomainFacade) BeanContextUtil.applicationContext
					.getBean("domainFacade");
		}
		// 设定全局变量：系统支持的语言
		session.getServletContext().setAttribute("supportLangs",
				i18nFacade.getLangs());
		String langParams[] = req.getParameterValues(CoreConsts.LANG);

		//取最后一个参数
		String lang = (langParams == null || langParams.length == 0) ? null
				: langParams[langParams.length - 1];
		
		if (StringUtils.isBlank(lang))
		{// 首先选取参数中的首选语言，没有则获取session中的参数
			lang = (String) session.getAttribute(CoreConsts.LANG);
			if (StringUtils.isBlank(lang))
			{// 读取cookie
				lang = getCookie(req, CoreConsts.LANG);

				if (StringUtils.isBlank(lang))
				{// 使用默认值
					lang = i18nFacade.getDefaultLang();
				}
			}
		}

		langs = (Map<String, Object>) i18nFacade.getI18nDatabase().get(lang);
		if (langs == null)
		{// 如果没有找到该语言，使用默认语言代替
			langs = (Map<String, Object>) i18nFacade.getI18nDatabase().get(
					i18nFacade.getDefaultLang());
			lang = i18nFacade.getDefaultLang();
		}

		// 将当前使用的语言设置到执行环境中
		ExecuteContext.putCurrentLang(lang);
		// 设置当前的domain语言版本
		domainFacade.saveToApplicationContext();

		langs = (Map<String, Object>) langs.get("i18n");

		req.setAttribute("i18n", langs);

		// 保存正在使用的语言
		storeLang(resp, session, lang);

		return langs;
	}

	/**
	 * 存储语言选项:::session和cookie
	 * 
	 * @param resp
	 * @param session
	 * @param lang
	 */
	private void storeLang(HttpServletResponse resp, HttpSession session,
			String lang)
	{
		session.setAttribute(CoreConsts.LANG, lang);

		Cookie ck = new Cookie(CoreConsts.LANG, lang);
		ck.setPath("/");
		ck.setMaxAge(3600 * 24 * 3600);
		resp.addCookie(ck);
	}

	/**
	 * 读取cookie
	 * 
	 * @param req
	 * @param name
	 * @return
	 */
	private String getCookie(HttpServletRequest req, String name)
	{
		Cookie[] cks = req.getCookies();
		if (cks == null)
		{
			return null;
		}
		for (Cookie ck : cks)
		{
			if (ck.getName().equals(name))
			{
				return ck.getValue();
			} else
			{
				continue;
			}
		}
		return null;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{

	}

	private I18nFacade i18nFacade;
	private DomainFacade domainFacade;
}
