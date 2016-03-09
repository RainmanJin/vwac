package com.ecannetwork.dto.tech;

import java.io.Serializable;
import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public abstract class AbstractTechLMSRecord extends DtoSupport implements
		Serializable
{
	private static final long serialVersionUID = 1L;

	private String id;
	private String courseIntanceId;
	private String channelId;
	private String lmsKey;
	private String lmsValue;
	private Date createTime;
	private String userId;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getCourseIntanceId()
	{
		return courseIntanceId;
	}

	public void setCourseIntanceId(String courseIntanceId)
	{
		this.courseIntanceId = courseIntanceId;
	}

	public String getChannelId()
	{
		return channelId;
	}

	public void setChannelId(String channelId)
	{
		this.channelId = channelId;
	}

	public String getLmsKey()
	{
		return lmsKey;
	}

	public void setLmsKey(String lmsKey)
	{
		this.lmsKey = lmsKey;
	}

	public String getLmsValue()
	{
		return lmsValue;
	}

	public void setLmsValue(String lmsValue)
	{
		this.lmsValue = lmsValue;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

}
