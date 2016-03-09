package com.ecannetwork.core.expression.el;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

/**
 * 表达式解析环境<br />
 * <b>使用方法</b>
 * 
 * <pre>
 * ExpressionContext ectx = new ExpressionContext();
 * ectx.putContext(&quot;requestScope&quot;, request.getParametersMap());
 * ectx.parse(&quot;requestScope.name&quot;);
 * </pre>
 * 
 * @author liulibin
 * 
 */
public class ExpressionContext
{

	private JexlEngine jexl = new JexlEngine();
	private JexlContext jc = new MapContext();

	/**
	 * 初始化执行环境， 设定执行环境变量
	 * 
	 * @param name
	 * @param o
	 */
	public void putContext(String name, Object o)
	{
		jc.set(name, o);
	}

	/**
	 * 解析表达式
	 * 
	 * @param expression
	 * @return
	 */
	public Object parse(String expression)
	{
		Expression e = jexl.createExpression(expression);
		return e.evaluate(jc);
	}

	/**
	 * 解析表达式
	 * 
	 * @param expression
	 * @return
	 */
	public Object parse(String expression, Object defaultValue)
	{
		Object o = this.parse(expression);
		if (o == null)
			return defaultValue;
		else
			return o;
	}
}
