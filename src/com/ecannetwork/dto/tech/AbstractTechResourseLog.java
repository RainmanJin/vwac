package com.ecannetwork.dto.tech;

import java.util.Date;

/**
 * AbstractTechResourseLog entity provides the base persistence definition of
 * the TechResourseLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechResourseLog extends
        com.ecannetwork.core.module.db.dto.DtoSupport implements
        java.io.Serializable
{

	// Fields

	private String id;
	private String resType;
	private String resId;
	private String logInfo;
	private Float resSum;
	private Integer intResSum;
	private Date insertDate;
	private String operName;
	private String operType;

	// Constructors

	public String getOperType()
	{
		return operType;
	}

	public void setOperType(String operType)
	{
		this.operType = operType;
	}

	/** default constructor */
	public AbstractTechResourseLog()
	{
	}

	/** full constructor */
	public AbstractTechResourseLog(String resType, String resId,
	        String logInfo, Float resSum, Date insertDate, String operName,
	        String operType)
	{
		this.resType = resType;
		this.resId = resId;
		this.logInfo = logInfo;
		this.resSum = resSum;
		this.insertDate = insertDate;
		this.operName = operName;
		this.operType = operType;
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

	public String getResType()
	{
		return this.resType;
	}

	public void setResType(String resType)
	{
		this.resType = resType;
	}

	public String getResId()
	{
		return this.resId;
	}

	public void setResId(String resId)
	{
		this.resId = resId;
	}

	public String getLogInfo()
	{
		return this.logInfo;
	}

	public void setLogInfo(String logInfo)
	{
		this.logInfo = logInfo;
	}

	public Float getResSum()
	{
		return this.resSum;
	}

	public void setResSum(Float resSum)
	{
		this.resSum = resSum;
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

	public Date getInsertDate()
	{
		return insertDate;
	}

	public void setInsertDate(Date insertDate)
	{
		this.insertDate = insertDate;
	}

	public String getOperName()
	{
		return operName;
	}

	public void setOperName(String operName)
	{
		this.operName = operName;
	}

}