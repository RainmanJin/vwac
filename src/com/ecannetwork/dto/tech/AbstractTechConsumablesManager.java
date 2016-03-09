package com.ecannetwork.dto.tech;

/**
 * AbstractTechConsumablesManager entity provides the base persistence
 * definition of the TechConsumablesManager entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechConsumablesManager extends
        com.ecannetwork.core.module.db.dto.DtoSupport implements
        java.io.Serializable
{

	// Fields

	private String id;
	private String conName;
	private String conType;
	private Float conSum = Float.valueOf(0);
	private String conRemark;
	private String conStatus;
	private Integer intConSum = Integer.valueOf(0);
	private Float conPrice = Float.valueOf(0);

	// Constructors

	/** default constructor */
	public AbstractTechConsumablesManager()
	{
	}

	/** full constructor */
	public AbstractTechConsumablesManager(String conName, String conType,
	        Float conSum, String conRemark, String conStatus,Float conPrice)
	{
		this.conName = conName;
		this.conType = conType;
		this.conSum = conSum;
		this.conRemark = conRemark;
		this.conStatus = conStatus;
		this.conPrice = conPrice;
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

	public String getConName()
	{
		return this.conName;
	}

	public void setConName(String conName)
	{
		this.conName = conName;
	}

	public String getConType()
	{
		return this.conType;
	}

	public void setConType(String conType)
	{
		this.conType = conType;
	}

	public Float getConSum()
	{
		return this.conSum;
	}

	public void setConSum(Float conSum)
	{
		this.conSum = conSum;
	}

	public String getConRemark()
	{
		return this.conRemark;
	}

	public void setConRemark(String conRemark)
	{
		this.conRemark = conRemark;
	}

	public String getConStatus()
	{
		return conStatus;
	}

	public void setConStatus(String conStatus)
	{
		this.conStatus = conStatus;
	}

	public Integer getIntConSum()
	{
		intConSum = conSum.intValue();
		return intConSum;
	}

	public void setIntConSum(Integer intConSum)
	{
		this.intConSum = intConSum;
	}

	public Float getConPrice()
    {
    	return conPrice;
    }

	public void setConPrice(Float conPrice)
    {
    	this.conPrice = conPrice;
    }

}