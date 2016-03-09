package com.ecannetwork.core.tld;

import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.ecannetwork.core.util.BeanContextUtil;

public abstract class IBaseTag extends TagSupport
{
	private static final long serialVersionUID = 1L;

	/**
	 * 获取el表达式的值
	 * 
	 * @param expression
	 * @return
	 */
	public Object getExValue(String expression)
	{
		if (expression != null)
		{
			try
			{
				Object o = ExpressionEvaluatorManager.evaluate(expression,
						expression.toString(), Object.class, this,
						this.pageContext);
				if (o == null)
				{
					return "";
				}

				return o;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return "";
	}

	public Object getBean(String name)
	{
		return BeanContextUtil.applicationContext.getBean(name);
	}

}