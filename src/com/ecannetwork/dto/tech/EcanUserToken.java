package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public class EcanUserToken extends DtoSupport
{
	private static final long serialVersionUID = -3334834972744255366L;

	private String id;
	private String token;
	private Date updateTime;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public boolean isValid()
	{
		return (new Date().getTime() - this.updateTime.getTime()) < (24 * 60 * 60 * 1000);
	}
}
