package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * 指标标准区间定义
 * 
 * @author liulibin
 * 
 */
public class TechTestActiveStandard extends DtoSupport
{
	private static final long serialVersionUID = 1L;

	private String id;
	private Double levelOne;
	private Double levelTwo;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Double getLevelOne()
	{
		return levelOne;
	}

	public void setLevelOne(Double levelOne)
	{
		this.levelOne = levelOne;
	}

	public Double getLevelTwo()
	{
		return levelTwo;
	}

	public void setLevelTwo(Double levelTwo)
	{
		this.levelTwo = levelTwo;
	}

	public int getIntOne()
	{
		return this.getLevelOne().intValue();
	}
	
	public int getIntTwo()
	{
		return this.getLevelTwo().intValue();
	}
	
	/**
	 * 类别
	 * 
	 * @author liulibin
	 * 
	 */
	public static class Type
	{
		public static final String JIAO_XUE_FA = "jiaoxuefa";
		public static final String ZHUAN_YE_KE = "zyk_";
	}
}
