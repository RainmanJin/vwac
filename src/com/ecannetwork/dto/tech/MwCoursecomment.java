package com.ecannetwork.dto.tech;

// Generated Jan 23, 2016 11:15:36 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * MwCoursecomment generated by hbm2java
 */
public class MwCoursecomment extends DtoSupport implements java.io.Serializable
{

	private String id;
	private String uid;
	private String cid;
	private Integer ccid;
	private String title;
	private String remark;
	private Date createTime;
	private String reRemark;
	private Date reTime;
	private String reId;
	
	private String name;

	public MwCoursecomment()
	{
	}

	public MwCoursecomment(String uid, String cid, Integer ccid, String title,
			String remark, Date createTime, String reRemark, Date reTime,
			String reId)
	{
		this.uid = uid;
		this.cid = cid;
		this.ccid = ccid;
		this.title = title;
		this.remark = remark;
		this.createTime = createTime;
		this.reRemark = reRemark;
		this.reTime = reTime;
		this.reId = reId;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUid()
	{
		return this.uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getCid()
	{
		return this.cid;
	}

	public void setCid(String cid)
	{
		this.cid = cid;
	}

	public Integer getCcid()
	{
		return this.ccid;
	}

	public void setCcid(Integer ccid)
	{
		this.ccid = ccid;
	}

	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getRemark()
	{
		return this.remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public Date getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getReRemark()
	{
		return this.reRemark;
	}

	public void setReRemark(String reRemark)
	{
		this.reRemark = reRemark;
	}

	public Date getReTime()
	{
		return this.reTime;
	}

	public void setReTime(Date reTime)
	{
		this.reTime = reTime;
	}

	public String getReId()
	{
		return this.reId;
	}

	public void setReId(String reId)
	{
		this.reId = reId;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name=name;
	}

}
