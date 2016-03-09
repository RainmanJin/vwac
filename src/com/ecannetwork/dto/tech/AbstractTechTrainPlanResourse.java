package com.ecannetwork.dto.tech;

/**
 * AbstractTechTrainPlanResourse entity provides the base persistence definition
 * of the TechTrainPlanResourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechTrainPlanResourse extends
		com.ecannetwork.core.module.db.dto.DtoSupport implements
		java.io.Serializable
{

	// Fields

	private String id;
	private String planType;
	private String resId;
	private Float resSum;
	private String planId;

	// Constructors

	/** default constructor */
	public AbstractTechTrainPlanResourse()
	{
	}

	/** full constructor */
	public AbstractTechTrainPlanResourse(String planType, String resId,
			Float resSum)
	{
		this.planType = planType;
		this.resId = resId;
		this.resSum = resSum;
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

	public String getPlanType()
	{
		return this.planType;
	}

	public void setPlanType(String planType)
	{
		this.planType = planType;
	}

	public String getResId()
	{
		return this.resId;
	}

	public void setResId(String resId)
	{
		this.resId = resId;
	}

	public Integer getResSumInt()
	{
		return this.resSum.intValue();
	}
	public Float getResSum()
	{
		return this.resSum;
	}

	public void setResSum(Float resSum)
	{
		this.resSum = resSum;
	}

	public String getPlanId()
	{
		return planId;
	}

	public void setPlanId(String planId)
	{
		this.planId = planId;
	}

}