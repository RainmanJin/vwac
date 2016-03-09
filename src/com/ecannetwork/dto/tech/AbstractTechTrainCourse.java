package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechTrainCourse entity provides the base persistence definition of
 * the TechTrainCourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechTrainCourse extends DtoSupport implements
		java.io.Serializable
{

	// Fields

	private String id;
	private String name;
	private String proType;
	private String brand;
	private String status;
	private String days;
	private String type;
    private String motorcycle;
	// Constructors

	/** default constructor */
	public AbstractTechTrainCourse()
	{
	}

	/** full constructor */
	public AbstractTechTrainCourse(String name, String proType, String brand,
			String status)
	{
		this.name = name;
		this.proType = proType;
		this.brand = brand;
		this.status = status;
	}
	/** full constructor */
	public AbstractTechTrainCourse(String name, String proType, String brand,
			String status,String motorcycle)
	{
		this.name = name;
		this.proType = proType;
		this.brand = brand;
		this.status = status;
		this.motorcycle=motorcycle;
	}

	// Property accessors

	public String getmotorcycle()
	{
		return this.motorcycle;
	}

	public void setmotorcycle(String motorcycle)
	{
		this.motorcycle = motorcycle;
	}
	
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

	public String getDays()
	{
		return days;
	}

	public void setDays(String days)
	{
		this.days = days;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

}