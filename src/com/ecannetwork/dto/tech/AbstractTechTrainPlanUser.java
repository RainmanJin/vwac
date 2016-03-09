package com.ecannetwork.dto.tech;

/**
 * AbstractTechTrainPlanUser entity provides the base persistence definition of
 * the TechTrainPlanUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechTrainPlanUser extends
        com.ecannetwork.core.module.db.dto.DtoSupport implements
        java.io.Serializable
{

	// Fields
	private String id;
	private String userType;
	private String userId;
	private String planId;
	private String finish;

	// Constructors

	/** default constructor */
	public AbstractTechTrainPlanUser()
	{
	}

	/** full constructor */
	public AbstractTechTrainPlanUser(String userType, String userId)
	{
		this.userType = userType;
		this.userId = userId;
	}

	// Property accessors

	public String getPlanId()
	{
		return planId;
	}

	public void setPlanId(String planId)
	{
		this.planId = planId;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUserType()
	{
		return this.userType;
	}

	public void setUserType(String userType)
	{
		this.userType = userType;
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getFinish()
	{
		return finish;
	}

	public void setFinish(String finish)
	{
		this.finish = finish;
	}

}