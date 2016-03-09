package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public abstract class AbstractTechMdttQA extends DtoSupport
{
	private static final long serialVersionUID = 1L;
	private String id;
	private String question;
	private String ans;
	private String brand;
	private String proType;
	private Date lastUpdateTime;
	private String status;
	private String userID;
	private String qusUserID;
	private Integer versionCode;
	private Integer views;
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion(String question)
	{
		this.question = question;
	}

	public String getAns()
	{
		return ans;
	}

	public void setAns(String ans)
	{
		this.ans = ans;
	}

	public String getBrand()
	{
		return brand;
	}

	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	public String getProType()
	{
		return proType;
	}

	public void setProType(String proType)
	{
		this.proType = proType;
	}

	public Date getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getUserID()
	{
		return userID;
	}

	public void setUserID(String userID)
	{
		this.userID = userID;
	}

	public Integer getVersionCode()
	{
		return versionCode;
	}

	public void setVersionCode(Integer versionCode)
	{
		this.versionCode = versionCode;
	}

	public String getQusUserID()
	{
		return qusUserID;
	}

	public void setQusUserID(String qusUserID)
	{
		this.qusUserID = qusUserID;
	}
	public Integer getviews()
	{
		return views;
	}

	public void setviews(Integer views)
	{
		this.views = views;
	}
	
}
