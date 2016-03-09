package com.ecannetwork.core.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ecannetwork.core.util.AjaxUtils;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.core.EcanUser;

/**
 * 杂项过滤器
 * 
 * @author leo
 * 
 */
public class EcanFilter implements Filter
{
	private static Log log = LogFactory.getLog(EcanFilter.class);

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

		// 清理缓存
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		
		// 标记ajax
		ajaxRequestTag(req);

		try
		{
			ExecuteContext.init();

			// 放入当前登录的用户:::可能为空
			ExecuteContext.put((EcanUser) session
					.getAttribute(CoreConsts.SessionScopeKeys.CURRENT_USER));
			ExecuteContext.put(session);
			ExecuteContext.put(req);

			arg2.doFilter(arg0, arg1);
		} finally
		{
			ExecuteContext.clean();
		}
	}

	/**
	 * 标记是否是ajax请求
	 * 
	 * @param req
	 */
	private void ajaxRequestTag(HttpServletRequest req)
	{
		String requestWith = req.getHeader("X-Requested-With");
		if (AjaxUtils.isAjaxRequest(requestWith))
		{
			req.setAttribute("ajaxRequest", Boolean.TRUE);
		}

		if (AjaxUtils.isAjaxUploadRequest(req))
		{
			req.setAttribute("ajaxUpload", Boolean.TRUE);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		// 存储绝对路径
		CoreConsts.Runtime.APP_ABSOLUTE_PATH = arg0.getServletContext()
				.getRealPath("/");

		// 存储执行环境
		CoreConsts.Runtime.servletContext = arg0.getServletContext();
	}
}
