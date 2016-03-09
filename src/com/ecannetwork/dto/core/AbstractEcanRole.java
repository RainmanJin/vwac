package com.ecannetwork.dto.core;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractEcanRole entity provides the base persistence definition of the
 * EcanRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractEcanRole extends DtoSupport implements java.io.Serializable
{

	// Fields

	private String id;
	private String name;
	private String remark;
	private String homeUrl;

	// Constructors

	/** default constructor */
	public AbstractEcanRole()
	{
	}

	/** full constructor */
	public AbstractEcanRole(String name, String remark)
	{
		this.name = name;
		this.remark = remark;
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

	public String getRemark()
	{
		return this.remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getHomeUrl()
	{
		return homeUrl;
	}

	public void setHomeUrl(String homeUrl)
	{
		this.homeUrl = homeUrl;
	}

}