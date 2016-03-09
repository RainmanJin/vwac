package com.ecannetwork.dto.tech;

import java.util.Date;

/**
 * AbstractTechConsumablesManager entity provides the base persistence
 * definition of the TechConsumablesManager entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechConsumablesBatch extends
        com.ecannetwork.core.module.db.dto.DtoSupport implements
        java.io.Serializable
{

	// Fields

	private String id;
	private String batch;
	private String userName;
	private Float conPrice = Float.valueOf(0);
	private Float conSum = Float.valueOf(0);
	private String conRemark;
	private String consumablesId;
	private Date conDate;
	private Float surplusSum;
	private Integer intSum = Integer.valueOf(0);

	// Constructors

	/** default constructor */
	public AbstractTechConsumablesBatch()
	{
	}

	/** full constructor */
	public AbstractTechConsumablesBatch(String batch, String userName,
	        Float conPrice, Float conSum, String conRemark,
	        String consumablesId, Date conDate, Float surplusSum)
	{
		this.batch = batch;
		this.userName = userName;
		this.conPrice = conPrice;
		this.conSum = conSum;
		this.conRemark = conRemark;
		this.consumablesId = consumablesId;
		this.conDate = conDate;
		this.surplusSum = surplusSum;

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

	public String getBatch()
	{
		return batch;
	}

	public void setBatch(String batch)
	{
		this.batch = batch;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public Float getConPrice()
	{
		return conPrice;
	}

	public void setConPrice(Float conPrice)
	{
		this.conPrice = conPrice;
	}

	public Float getConSum()
	{
		return conSum;
	}

	public void setConSum(Float conSum)
	{
		this.conSum = conSum;
	}

	public String getConRemark()
	{
		return conRemark;
	}

	public void setConRemark(String conRemark)
	{
		this.conRemark = conRemark;
	}

	public String getConsumablesId()
	{
		return consumablesId;
	}

	public void setConsumablesId(String consumablesId)
	{
		this.consumablesId = consumablesId;
	}

	public Date getConDate()
	{
		return conDate;
	}

	public void setConDate(Date conDate)
	{
		this.conDate = conDate;
	}

	public Float getSurplusSum()
	{
		return surplusSum;
	}

	public void setSurplusSum(Float surplusSum)
	{
		this.surplusSum = surplusSum;
	}

	public Integer getIntSum()
	{
		intSum = conSum.intValue();
		return intSum;
	}

	public void setIntSum(Integer intSum)
	{
		this.intSum = intSum;
	}

}