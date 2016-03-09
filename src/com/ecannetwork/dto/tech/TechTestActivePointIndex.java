package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * 维度标准指标
 * 
 * @author liulibin
 * 
 */
public class TechTestActivePointIndex extends DtoSupport
{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String dimension;
	private String proType;
	private Double point;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDimension()
	{
		return dimension;
	}

	public void setDimension(String dimension)
	{
		this.dimension = dimension;
	}

	public String getProType()
	{
		return proType;
	}

	public void setProType(String proType)
	{
		this.proType = proType;
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
