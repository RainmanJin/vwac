package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechTestUser entity provides the base persistence definition of the
 * TechTestUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechTestUser extends DtoSupport implements
		java.io.Serializable
{

	// Fields

	private String id;
	private String userId;
	private String userType;
	private String testActiveId;
	private Double pointCn;
	private Double pointDe;
	private String comment;
	private Double points;
	private String isSubmit;


	// Constructors

	/** default constructor */
	public AbstractTechTestUser()
	{
	}

	/** full constructor */
	public AbstractTechTestUser(String userId, String userType,
			String testActiveId, Double pointCn, Double pointDe,
			String comment, Double points, String isSubmit)
	{
		this.userId = userId;
		this.userType = userType;
		this.testActiveId = testActiveId;
		this.pointCn = pointCn;
		this.pointDe = pointDe;
		this.comment = comment;
		this.points = points;
		this.isSubmit = isSubmit;
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

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getUserType()
	{
		return this.userType;
	}

	public void setUserType(String userType)
	{
		this.userType = userType;
	}

	public String getTestActiveId()
	{
		return this.testActiveId;
	}

	public void setTestActiveId(String testActiveId)
	{
		this.testActiveId = testActiveId;
	}

	public Double getPointCn()
	{
		return this.pointCn;
	}

	public void setPointCn(Double pointCn)
	{
		this.pointCn = pointCn;
	}

	public Double getPointDe()
	{
		return this.pointDe;
	}

	public void setPointDe(Double pointDe)
	{
		this.pointDe = pointDe;
	}

	public String getComment()
	{
		return this.comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public Double getPoints()
	{
		return this.points;
	}

	public void setPoints(Double points)
	{
		this.points = points;
	}

	public String getIsSubmit()
	{
		return this.isSubmit;
	}

	public void setIsSubmit(String isSubmit)
	{
		this.isSubmit = isSubmit;
	}

}