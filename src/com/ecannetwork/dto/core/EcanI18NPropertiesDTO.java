package com.ecannetwork.dto.core;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public class EcanI18NPropertiesDTO extends DtoSupport
{
	private static final long serialVersionUID = 1L;

	private String id;
	private String langType;
	private String textValue;
	private String propertyId;
	private String appId;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getLangType()
	{
		return langType;
	}

	public void setLangType(String langType)
	{
		this.langType = langType;
	}

	public String getTextValue()
	{
		return textValue;
	}

	public void setTextValue(String text)
	{
		this.textValue = text;
	}

	public String getPropertyId()
	{
		return propertyId;
	}

	public void setPropertyId(String propertyId)
	{
		this.propertyId = propertyId;
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
