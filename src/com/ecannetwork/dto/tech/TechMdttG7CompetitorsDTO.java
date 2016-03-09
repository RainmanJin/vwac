package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public class TechMdttG7CompetitorsDTO extends DtoSupport
{
	private String id;
	private String userid;
	private Integer qid;
	private Integer value;
	private Integer carid;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUserid()
	{
		return userid;
	}

	public void setUserid(String userid)
	{
		this.userid = userid;
	}

	public Integer getQid()
	{
		return qid;
	}

	public void setQid(Integer qid)
	{
		this.qid = qid;
	}

	public Integer getValue()
	{
		return value;
	}

	public void setValue(Integer value)
	{
		this.value = value;
	}

	public Integer getCarid()
	{
		return carid;
	}

	public void setCarid(Integer carid)
	{
		this.carid = carid;
	}

}
