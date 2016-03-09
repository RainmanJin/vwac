package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechActivePoint entity provides the base persistence definition of
 * the TechActivePoint entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechTestActivePoint extends DtoSupport implements
		java.io.Serializable
{

	// Fields

	private String id;
	private String step;
	private String dimension;
	private String watchUserId;
	private String userId;
	private Double points;
	private String testActiveId;

	// Constructors

	/** default constructor */
	public AbstractTechTestActivePoint()
	{
	}

	/** full constructor */
	public AbstractTechTestActivePoint(String step, String dimension,
			String watchUserId, String userId, Double points)
	{
		this.step = step;
		this.dimension = dimension;
		this.watchUserId = watchUserId;
		this.userId = userId;
		this.points = points;
	}

	// Property accessors

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getStep()
	{
		return this.step;
	}

	public void setStep(String step)
	{
		this.step = step;
	}

	public String getDimension()
	{
		return this.dimension;
	}

	public void setDimension(String dimension)
	{
		this.dimension = dimension;
	}

	public String getWatchUserId()
	{
		return this.watchUserId;
	}

	public void setWatchUserId(String watchUserId)
	{
		this.watchUserId = watchUserId;
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public Double getPoints()
	{
		if(this.points == null)
			return new Double(0);
		return this.points;
	}

	public void setPoints(Double points)
	{
		this.points = points;
	}

	public String getTestActiveId()
	{
		return testActiveId;
	}

	public void setTestActiveId(String testActiveId)
	{
		this.testActiveId = testActiveId;
	}
}