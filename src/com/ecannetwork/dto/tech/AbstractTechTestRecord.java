package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechTestRecord entity provides the base persistence definition of the
 * TechTestRecord entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechTestRecord extends DtoSupport implements
		java.io.Serializable
{

	// Fields

	private String id;
	private String userId;
	private String testActiveId;
	private String titleId;
	private String answerId;
	private String testId;

	// Constructors

	/** default constructor */
	public AbstractTechTestRecord()
	{
	}

	/** full constructor */
	public AbstractTechTestRecord(String userId, String testActiveId,
			String titleId, String answerId, String testId)
	{
		this.userId = userId;
		this.testActiveId = testActiveId;
		this.titleId = titleId;
		this.answerId = answerId;
		this.testId = testId;
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

	public String getTestActiveId()
	{
		return this.testActiveId;
	}

	public void setTestActiveId(String testActiveId)
	{
		this.testActiveId = testActiveId;
	}

	public String getTitleId()
	{
		return this.titleId;
	}

	public void setTitleId(String titleId)
	{
		this.titleId = titleId;
	}

	public String getAnswerId()
	{
		return this.answerId;
	}

	public void setAnswerId(String answerId)
	{
		this.answerId = answerId;
	}

	public String getTestId()
	{
		return this.testId;
	}

	public void setTestId(String testId)
	{
		this.testId = testId;
	}

}