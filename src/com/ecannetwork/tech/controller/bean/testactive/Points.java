package com.ecannetwork.tech.controller.bean.testactive;

public class Points
{
	/**
	 * 题干积分
	 */
	private Double pointCn = new Double(0);
	/**
	 * 题干计分总数
	 */
	private Integer pointTotalCn = new Integer(0);

	/**
	 * 选项积分
	 */
	private Double pointDe = new Double(0);

	/**
	 * 选项积分方式的总分
	 */
	private Integer pointTotalDe = new Integer(0);

	public Double getPointCn100()
	{
		return this.pointCn * 100 / this.pointTotalCn;
	}

	public Double getPointDe100()
	{
		return this.pointDe * 100 / this.pointTotalDe;
	}

	public Double getPointCn()
	{
		return pointCn;
	}

	public void setPointCn(Double pointCn)
	{
		this.pointCn = pointCn;
	}

	public Double getPointDe()
	{
		return pointDe;
	}

	public void setPointDe(Double pointDe)
	{
		this.pointDe = pointDe;
	}

	public Integer getPointTotalDe()
	{
		return pointTotalDe;
	}

	public void setPointTotalDe(Integer pointTotalDe)
	{
		this.pointTotalDe = pointTotalDe;
	}

	public Integer getPointTotalCn()
	{
		return pointTotalCn;
	}

	public void setPointTotalCn(Integer pointTotalCn)
	{
		this.pointTotalCn = pointTotalCn;
	}
}
