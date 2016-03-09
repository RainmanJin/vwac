package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechTestActive entity provides the base persistence definition of the
 * TechTestActive entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechTestActive extends DtoSupport implements
		java.io.Serializable
{

	// Fields

	private String id;
	private String name;
	private Date creatTime;
	private String proType;
	private String brand;
	private String status;
	private String hosterId;
	private String mainManId;
	private Integer testTimelimit;
	private Integer testCount;
	// 观察员，逗号分割
	private String watchMens;

	private String finAddPoint;
	private String finTest;

	// Constructors

	/** default constructor */
	public AbstractTechTestActive()
	{
	}

	/** full constructor */
	public AbstractTechTestActive(String name, Date creatTime, String proType,
			String brand, String status, String hosterId, String mainManId,
			Integer testTimelimit, Integer testCount)
	{
		this.name = name;
		this.creatTime = creatTime;
		this.proType = proType;
		this.brand = brand;
		this.status = status;
		this.hosterId = hosterId;
		this.mainManId = mainManId;
		this.testTimelimit = testTimelimit;
		this.testCount = testCount;
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

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Date getCreatTime()
	{
		return this.creatTime;
	}

	public void setCreatTime(Date creatTime)
	{
		this.creatTime = creatTime;
	}

	public String getProType()
	{
		return this.proType;
	}

	public void setProType(String proType)
	{
		this.proType = proType;
	}

	public String getBrand()
	{
		return this.brand;
	}

	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getHosterId()
	{
		return this.hosterId;
	}

	public void setHosterId(String hosterId)
	{
		this.hosterId = hosterId;
	}

	public String getMainManId()
	{
		return this.mainManId;
	}

	public void setMainManId(String mainManId)
	{
		this.mainManId = mainManId;
	}

	public Integer getTestTimelimit()
	{
		return this.testTimelimit;
	}

	public void setTestTimelimit(Integer testTimelimit)
	{
		this.testTimelimit = testTimelimit;
	}

	public Integer getTestCount()
	{
		return this.testCount;
	}

	public void setTestCount(Integer testCount)
	{
		this.testCount = testCount;
	}

	public String getWatchMens()
	{
		return watchMens;
	}

	public void setWatchMens(String watchMens)
	{
		this.watchMens = watchMens;
	}

	public String getFinAddPoint()
	{
		return finAddPoint;
	}

	public void setFinAddPoint(String finAddPoint)
	{
		this.finAddPoint = finAddPoint;
	}

	public String getFinTest()
	{
		return finTest;
	}

	public void setFinTest(String finTest)
	{
		this.finTest = finTest;
	}
}