package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public class TechTestActiveFeedback extends DtoSupport
{
	private static final long serialVersionUID = 5742483742197243722L;

	private String id;
	private String userId;
	private String dimension;
	private String testActiveId;
	private String advantage;
	private String weakness;
	private String lastModifyUser;
	private Date lastModifyTime;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getDimension()
	{
		return dimension;
	}

	public void setDimension(String dimension)
	{
		this.dimension = dimension;
	}

	public String getTestActiveId()
	{
		return testActiveId;
	}

	public void setTestActiveId(String testActiveId)
	{
		this.testActiveId = testActiveId;
	}

	public String getAdvantage()
	{
		return advantage;
	}

	public void setAdvantage(String advantage)
	{
		this.advantage = advantage;
	}

	public String getWeakness()
	{
		return weakness;
	}

	public void setWeakness(String weakness)
	{
		this.weakness = weakness;
	}

	public String getLastModifyUser()
	{
		return lastModifyUser;
	}

	public void setLastModifyUser(String lastModifyUser)
	{
		this.lastModifyUser = lastModifyUser;
	}

	public Date getLastModifyTime()
	{
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime)
	{
		this.lastModifyTime = lastModifyTime;
	}

}
