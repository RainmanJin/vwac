package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public abstract class AbstractTechMdttQustion extends DtoSupport
{
	private static final long serialVersionUID = 1L;
	private String id;
	private String qusName;
	private String qusType;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getQusName()
	{
		return qusName;
	}

	public void setQusName(String qusName)
	{
		this.qusName = qusName;
	}

	public String getQusType()
	{
		return qusType;
	}

	public void setQusType(String qusType)
	{
		this.qusType = qusType;
	}
}
