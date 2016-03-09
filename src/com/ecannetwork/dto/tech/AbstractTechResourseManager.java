package com.ecannetwork.dto.tech;

/**
 * AbstractTechResourseManager entity provides the base persistence definition
 * of the TechResourseManager entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechResourseManager extends
        com.ecannetwork.core.module.db.dto.DtoSupport implements
        java.io.Serializable
{

	// Fields
	private String id;
	private String resName;
	private String resType;
	private Float resSum;
	private String resRemark;
	private Float idleSum;
	private String resStatus;
	private Integer intResSum;
	private String status;

	// Constructors
	/** default constructor */
	public AbstractTechResourseManager()
	{
	}

	/** full constructor */
	public AbstractTechResourseManager(String resName, String resType,
	        Float resSum, String resRemark, Float idleSum, String resStatus,
	        String status)
	{
		this.resName = resName;
		this.resType = resType;
		this.resSum = resSum;
		this.resRemark = resRemark;
		this.idleSum = idleSum;
		this.resStatus = resStatus;
		this.status = status;
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

	public String getResName()
	{
		return this.resName;
	}

	public void setResName(String resName)
	{
		this.resName = resName;
	}

	public String getResType()
	{
		return this.resType;
	}

	public void setResType(String resType)
	{
		this.resType = resType;
	}

	public Float getResSum()
	{
		return this.resSum;
	}

	public void setResSum(Float resSum)
	{
		this.resSum = resSum;
	}

	public String getResRemark()
	{
		return this.resRemark;
	}

	public void setResRemark(String resRemark)
	{
		this.resRemark = resRemark;
	}

	public Float getIdleSum()
	{
		return this.idleSum;
	}

	public void setIdleSum(Float idleSum)
	{
		this.idleSum = idleSum;
	}

	public String getResStatus()
	{
		return resStatus;
	}

	public void setResStatus(String resStatus)
	{
		this.resStatus = resStatus;
	}

	public Integer getIntResSum()
	{
		intResSum = resSum.intValue();
		return intResSum;
	}

	public void setIntResSum(Integer intResSum)
	{
		this.intResSum = intResSum;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

}