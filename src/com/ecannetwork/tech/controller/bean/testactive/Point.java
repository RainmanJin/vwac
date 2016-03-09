package com.ecannetwork.tech.controller.bean.testactive;

public class Point
{
	private String dim;
	private Double point;

	public Point(String dim, Double point)
	{
		this.dim = dim;
		this.point = point;
	}

	public String getDim()
	{
		return dim;
	}

	public void setDim(String dim)
	{
		this.dim = dim;
	}

	public Double getPoint()
	{
		return point;
	}

	public void setPoint(Double point)
	{
		this.point = point;
	}

}
