package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechHoliday entity provides the base persistence definition of the
 * TechHoliday entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechHoliday extends DtoSupport implements
		java.io.Serializable
{

	// Fields

	private String id;
	private Date beginDay;
	private Date endDay;
	private String userId;
	private String remark;

	// Constructors

	/** default constructor */
	public AbstractTechHoliday()
	{
	}

	/** full constructor */
	public AbstractTechHoliday(Date beginDay, Date endDay, String userId)
	{
		this.beginDay = beginDay;
		this.endDay = endDay;
		this.userId = userId;
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

	public Date getBeginDay()
	{
		return this.beginDay;
	}

	public void setBeginDay(Date beginDay)
	{
		this.beginDay = beginDay;
	}

	public Date getEndDay()
	{
		return this.endDay;
	}

	public void setEndDay(Date endDay)
	{
		this.endDay = endDay;
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

}