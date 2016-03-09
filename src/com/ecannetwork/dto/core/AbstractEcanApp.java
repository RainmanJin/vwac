package com.ecannetwork.dto.core;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractEcanApp entity provides the base persistence definition of the
 * EcanApp entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractEcanApp extends DtoSupport implements
		java.io.Serializable
{

	// Fields

	private String id;
	private String appName;
	private String appCode;
	private String levelCode;
	private String funName;
	private String funCode;
	private String isOutside;
	private String outsideUrl;

	// 顺序号
	private Integer idx;

	// Constructors

	/** default constructor */
	public AbstractEcanApp()
	{
	}

	/** full constructor */
	public AbstractEcanApp(String appName, String appCode, String levelCode,
			String funName, String funCode, String isOutside, String outsideUrl)
	{
		this.appName = appName;
		this.appCode = appCode;
		this.levelCode = levelCode;
		this.funName = funName;
		this.funCode = funCode;
		this.isOutside = isOutside;
		this.outsideUrl = outsideUrl;
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

	public String getAppName()
	{
		return this.appName;
	}

	public void setAppName(String appName)
	{
		this.appName = appName;
	}

	public String getAppCode()
	{
		return this.appCode;
	}

	public void setAppCode(String appCode)
	{
		this.appCode = appCode;
	}

	public String getLevelCode()
	{
		return this.levelCode;
	}

	public void setLevelCode(String levelCode)
	{
		this.levelCode = levelCode;
	}

	public String getFunName()
	{
		return this.funName;
	}

	public void setFunName(String funName)
	{
		this.funName = funName;
	}

	public String getFunCode()
	{
		return this.funCode;
	}

	public void setFunCode(String funCode)
	{
		this.funCode = funCode;
	}

	public String getIsOutside()
	{
		return this.isOutside;
	}

	public void setIsOutside(String isOutside)
	{
		this.isOutside = isOutside;
	}

	public String getOutsideUrl()
	{
		return this.outsideUrl;
	}

	public void setOutsideUrl(String outsideUrl)
	{
		this.outsideUrl = outsideUrl;
	}

	public Integer getIdx()
	{
		return idx;
	}

	public void setIdx(Integer idx)
	{
		this.idx = idx;
	}

}