package com.ecannetwork.core.tld;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang.StringUtils;

import com.ecannetwork.core.app.auth.Menu;
import com.ecannetwork.core.module.facade.TemplateFacade;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;

/**
 * 做授权的按钮
 * 
 * @author leo
 * 
 */
public class AuthButtonTLD extends IBaseTag
{
	private static final long serialVersionUID = 1L;

	private TemplateFacade templateFacade;

	public int doEndTag() throws JspException
	{
		String result = formartResult();
		if (result != null)
		{
			JspWriter out = this.pageContext.getOut();
			try
			{
				out.print(result);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 生成按钮
	 * 
	 * @return
	 */
	private String formartResult()
	{
		// 已授权的应用列表
		Map<String, Menu> authedMap = (Map<String, Menu>) ExecuteContext
				.session().getAttribute(
						CoreConsts.SessionScopeKeys.AUTHED_MAP);

		if (authedMap == null
				|| !authedMap.containsKey(this.appCode + "/" + this.funcCode))
		{// 没有登录或没有授权
			return null;
		} else
		{//
			if (this.templateFacade == null)
			{
				templateFacade = (TemplateFacade) this
						.getBean("templateFacade");
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("appCode", this.appCode);
			map.put("funcCode", this.funcCode);
			map.put("text", this.text);
			map.put("action", this.action);

			if (StringUtils.isNotBlank(this.params))
			{// 解析params为map
				String[] ps = this.params.split("&");
				for (int i = 0; i < ps.length; i++)
				{
					if (StringUtils.isNotBlank(ps[i]))
					{
						String p[] = ps[i].split("=");
						if (p.length == 2 && !StringUtils.isBlank(p[0]))
						{
							map.put(p[0], p[1]);
						}
					}
				}
			}

			if (StringUtils.isNotBlank(this.ftl))
			{
				return templateFacade.process(map, this.ftl);
			} else
			{
				return this.templateFacade.process(map,
						AUTH_BUTTON_TEMPLATE_NAME);
			}
		}
	}

	private static final String AUTH_BUTTON_TEMPLATE_NAME = "btn/authBtn.ftl";

	/**
	 * 应用程序编码
	 */
	private String appCode;

	/**
	 * 功能编码
	 */
	private String funcCode;

	/**
	 * 显示的文本
	 */
	private String text;

	/**
	 * 模板，可选，默认使用btn/authBtn.ftl
	 */
	private String ftl;

	/**
	 * 点击时的动作，默认作为onClick事件传递，如自定义模板，可自行设定
	 */
	private String action;

	/**
	 * 参数，多个参数格式如下：p1=value1&p2=value2&p3=value3,参数可在模板中直接使用
	 */
	private String params;

	public String getAppCode()
	{
		return appCode;
	}

	public void setAppCode(String appCode)
	{
		this.appCode = appCode;
	}

	public String getFuncCode()
	{
		return funcCode;
	}

	public void setFuncCode(String funcCode)
	{
		this.funcCode = funcCode;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getFtl()
	{
		return ftl;
	}

	public void setFtl(String ftl)
	{
		this.ftl = ftl;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	public String getParams()
	{
		return params;
	}

	public void setParams(String params)
	{
		this.params = params;
	}

}
