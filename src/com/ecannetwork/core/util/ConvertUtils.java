package com.ecannetwork.core.util;

/**
 * 数据类型转换 add by yufei,2015-01-25
 * 
 * @author Administrator
 * 
 */
public class ConvertUtils
{
	/**
	 * 将字符串转换为Int32类型
	 * 
	 * @param expression要转换的字符串
	 * @param defValue缺省值
	 * @return转换后的int类型结果
	 */
	public static int StrToInt(String expression, Integer defValue)
	{
		// 如果不符合整数格式，则返回默认值
		String intRegex = "^([-]|[0-9])[0-9]*(\\.\\w*)?$";
		if (expression.isEmpty() || expression == null || expression.length() > 11 || !expression.trim().matches(intRegex))
		{
			return defValue;
		}
		Integer integer = Integer.valueOf(expression);
		return integer.intValue();
	}

	/**
	 * string型转换为float型
	 * 
	 * @param strValue要转换的字符串
	 * @param defValue缺省值
	 * @return转换后的int类型结果
	 */
	public static Float StrToFloat(String strValue, Float defValue)
	{
		if ((strValue == null) || (strValue.length() > 10))
		{
			return defValue;
		}

		float intValue = defValue;
		if (strValue != null)
		{
			String intRegex = "^([-]|[0-9])[0-9]*(\\.\\w*)?$";
			boolean IsFloat = strValue.trim().matches(intRegex);
			if (IsFloat)
			{
				Float floatee = Float.valueOf(strValue);
				return floatee.floatValue();
			}
		}
		return intValue;
	}

	public static void main(String[] args)
	{
		System.out.println(StrToFloat("1234", 2.0f));
	}
}
