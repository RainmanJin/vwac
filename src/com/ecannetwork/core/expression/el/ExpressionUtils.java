package com.ecannetwork.core.expression.el;

import java.util.HashMap;
import java.util.Map;

/**
 * 提供对表达式语言的支持:访问属性和执行方法
 * 
 * 
 */
public class ExpressionUtils
{
	private ExpressionUtils()
	{

	}

	/**
	 * <p>
	 * <ul>
	 * <li>以EL表达式解析expression</li>
	 * </ul>
	 * </p>
	 * 
	 * @param expression
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public static Object parse(String expression, Object o)
	{
		ExpressionContext ctx = new ExpressionContext();
		ctx.putContext("t", o);
		return ctx.parse("t." + expression);
	}

	/**
	 * 解析表达式
	 * 
	 * @param expression
	 * @param o
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static Object parse(String expression, Object o, Object defaultValue)
	{
		Object re = parse(expression, o);

		if (re != null)
			return re;
		else
			return defaultValue;
	}

	public static void main(String[] args)
	{
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		map.put("hello", new HashMap<String, String>());
		map.get("hello").put("name", "leo");

		System.out.println(ExpressionUtils.parse("hello.name", map));
	}
}
