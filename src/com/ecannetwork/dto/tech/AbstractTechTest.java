package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechTest entity provides the base persistence definition of the
 * TechTest entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechTest extends DtoSupport implements
		java.io.Serializable
{

	// Fields

	private String id;
	private String testActiveId;
	private String testAcviceTitleId;

	// Constructors

	/** default constructor */
	public AbstractTechTest()
	{
	}

	/** full constructor */
	public AbstractTechTest(String testActiveId, String testAcviceTitleId)
	{
		this.testActiveId = testActiveId;
		this.testAcviceTitleId = testAcviceTitleId;
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

	public String getTestActiveId()
	{
		return this.testActiveId;
	}

	public void setTestActiveId(String testActiveId)
	{
		this.testActiveId = testActiveId;
	}

	public String getTestAcviceTitleId()
	{
		return this.testAcviceTitleId;
	}

	public void setTestAcviceTitleId(String testAcviceTitleId)
	{
		this.testAcviceTitleId = testAcviceTitleId;
	}

}