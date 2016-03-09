package com.ecannetwork.core.tld;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang.StringUtils;

import com.ecannetwork.core.module.facade.TemplateFacade;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;

/**
 * 做授权的按钮
 * 
 * @author leo
 * 
 */
public class DateInputTLD extends IBaseTag
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
		if (this.templateFacade == null)
		{
			templateFacade = (TemplateFacade) this.getBean("templateFacade");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		if (this.value != null)
		{
			map.put("date", new SimpleDateFormat(
					this.formart == null ? "yyyy-MM-dd" : this.formart)
					.format(this.value));
		}
		map.put("name", this.name);
		map.put("class", this.className);

		// 国际化
		String lang = (String) ExecuteContext.session().getAttribute(
				CoreConsts.LANG);
		if (lang != null)
		{
			if (lang.equals(CoreConsts.LANGS.zh_CN))
			{
				lang = "zh-cn";
			} else
			{
				if (lang.equals(CoreConsts.LANGS.zh_TW))
				{
					lang = "zh-tw";
				} else
				{
					lang = "en";
				}
			}
		}
		map.put("lang", lang);

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
			return this.templateFacade.process(map, DATE_INPUT_TEMPLATE_NAME);
		}
	}

	private static final String DATE_INPUT_TEMPLATE_NAME = "input/dateInput.ftl";

	private Object value;

	private String formart;

	private String name;

	private String className;

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	/**
	 * 模板，可选
	 */
	private String ftl;

	/**
	 * 参数，多个参数格式如下：p1=value1&p2=value2&p3=value3,参数可在模板中直接使用
	 */
	private String params;

	public String getParams()
	{
		return params;
	}

	public void setParams(String params)
	{
		this.params = params;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	public String getFormart()
	{
		return formart;
	}

	public void setFormart(String formart)
	{
		this.formart = formart;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getFtl()
	{
		return ftl;
	}

	public void setFtl(String ftl)
	{
		this.ftl = ftl;
	}

}
