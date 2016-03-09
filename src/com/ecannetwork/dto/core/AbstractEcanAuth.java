package com.ecannetwork.dto.core;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractEcanAuth entity provides the base persistence definition of the
 * EcanAuth entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractEcanAuth extends DtoSupport implements java.io.Serializable
{

	// Fields

	private String id;
	private String roleId;
	private String appId;

	// Constructors

	/** default constructor */
	public AbstractEcanAuth()
	{
	}

	/** full constructor */
	public AbstractEcanAuth(String roleId, String appId)
	{
		this.roleId = roleId;
		this.appId = appId;
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

	public String getRoleId()
	{
		return this.roleId;
	}

	public void setRoleId(String roleId)
	{
		this.roleId = roleId;
	}

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}
 

}