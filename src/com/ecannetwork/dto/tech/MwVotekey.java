package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

// Generated Jan 23, 2016 11:15:36 PM by Hibernate Tools 3.4.0.CR1

/**
 * MwVotekey generated by hbm2java
 */
public class MwVotekey extends DtoSupport implements java.io.Serializable
{
	private String id;
	private Integer NSubId;
	private String CKeyTitle;
	private Integer NType;
	private Integer NOrderId;
	private String CRule;
	private Integer NScore;
	private String CLogicSub;
	private String imgpath;

	public MwVotekey()
	{
	}

	public MwVotekey(Integer NSubId, String CKeyTitle, Integer NType,
			Integer NOrderId, String CRule, Integer NScore, String CLogicSub,
			String imgpath)
	{
		this.NSubId = NSubId;
		this.CKeyTitle = CKeyTitle;
		this.NType = NType;
		this.NOrderId = NOrderId;
		this.CRule = CRule;
		this.NScore = NScore;
		this.CLogicSub = CLogicSub;
		this.imgpath = imgpath;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	
	public Integer getNSubId()
	{
		return this.NSubId;
	}

	public void setNSubId(Integer NSubId)
	{
		this.NSubId = NSubId;
	}

	public String getCKeyTitle()
	{
		return this.CKeyTitle;
	}

	public void setCKeyTitle(String CKeyTitle)
	{
		this.CKeyTitle = CKeyTitle;
	}

	public Integer getNType()
	{
		return this.NType;
	}

	public void setNType(Integer NType)
	{
		this.NType = NType;
	}

	public Integer getNOrderId()
	{
		return this.NOrderId;
	}

	public void setNOrderId(Integer NOrderId)
	{
		this.NOrderId = NOrderId;
	}

	public String getCRule()
	{
		return this.CRule;
	}

	public void setCRule(String CRule)
	{
		this.CRule = CRule;
	}

	public Integer getNScore()
	{
		return this.NScore;
	}

	public void setNScore(Integer NScore)
	{
		this.NScore = NScore;
	}

	public String getCLogicSub()
	{
		return this.CLogicSub;
	}

	public void setCLogicSub(String CLogicSub)
	{
		this.CLogicSub = CLogicSub;
	}

	public String getImgpath()
	{
		return this.imgpath;
	}

	public void setImgpath(String imgpath)
	{
		this.imgpath = imgpath;
	}

}
