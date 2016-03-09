package com.ecannetwork.core.util;

/**
 * 全局编号
 * 
 * @author leo
 * 
 */
public class UUID
{
	public static String randomUUID()
	{
		return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static void main(String[] args)
	{
		System.out.println(UUID.randomUUID());
	}
}
