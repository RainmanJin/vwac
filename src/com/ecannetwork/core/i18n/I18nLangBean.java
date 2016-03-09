package com.ecannetwork.core.i18n;

public class I18nLangBean
{
	// 语言编码::zh_CN/en_US
	private String id;
	// 语言名称:::简体中文
	private String name;

	public I18nLangBean(String id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}


	@Override
	public String toString()
	{
		return "I18nLangBean [id=" + id + ", name=" + name 
				+ "]";
	}
}
