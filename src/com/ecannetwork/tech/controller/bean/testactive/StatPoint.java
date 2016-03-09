package com.ecannetwork.tech.controller.bean.testactive;

import java.io.Serializable;

public class StatPoint implements Serializable
{
	/**
	 * 维度名称
	 */
	private String dimName;

	/**
	 * 维度得分,2/8
	 */
	private Double point28;

	/**
	 * 指标值
	 */
	private Double index;

	public StatPoint(String dimName2, Double avg28, Double pointIndex)
	{
		this.dimName = dimName2;
		this.point28 = avg28;
		this.index = pointIndex;
	}

	public String getDimName()
	{
		return dimName;
	}

	public void setDimName(String dimName)
	{
		this.dimName = dimName;
	}

	public Double getPoint28()
	{
		if (point28 == null)
		{
			return 0d;
		}
		return point28;
	}

	public void setPoint28(Double point28)
	{
		this.point28 = point28;
	}

	public Double getIndex()
	{
		if(index == null)
			return 0d;
		return index;
	}

	public void setIndex(Double index)
	{
		this.index = index;
	}
}
