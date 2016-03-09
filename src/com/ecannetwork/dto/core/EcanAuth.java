package com.ecannetwork.dto.core;

/**
 * EcanAuth entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class EcanAuth extends AbstractEcanAuth implements java.io.Serializable
{

	// Constructors

	/** default constructor */
	public EcanAuth()
	{
	}

	/** full constructor */
	public EcanAuth(String roleId, String appId)
	{
		super(roleId, appId);
	}

}
