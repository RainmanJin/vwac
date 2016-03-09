package com.ecannetwork.dto.tech;

import java.io.Serializable;

public class SiteStatQuestion implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Float count;
	private String dbname;

	public SiteStatQuestion(Float count, String dbname)
	{
		this.count = count;
		this.dbname = dbname;
	}

//	public String getType()
//	{
//		return type;
//	}
//
//	public void setType(String type)
//	{
//		this.type = type;
//	}

	public Float getCount()
	{
		return count;
	}

	public void setCount(Float count)
	{
		this.count = count;
	}

	public String getDbname()
	{
		return dbname;
	}

	public void setDbname(String dbname)
	{
		this.dbname = dbname;
	}

}
