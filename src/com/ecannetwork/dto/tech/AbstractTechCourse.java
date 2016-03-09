package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechCourse entity provides the base persistence definition of the
 * TechCourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechCourse extends DtoSupport implements
		java.io.Serializable
{

	// Fields

	private String id;
	private String scormId;
	private String name;
	private String brand;
	private String proType;
	private Date createTime;
	private String createUser;
	private String status;
	private String remark;
	private String preview;
	
	private String contentType;

	// Constructors

	/** default constructor */
	public AbstractTechCourse()
	{
	}

	/** full constructor */
	public AbstractTechCourse(String scormId, String name, String brand,
			String proType, Date createTime, String createUser, String status,
			String remark)
	{
		this.scormId = scormId;
		this.name = name;
		this.brand = brand;
		this.proType = proType;
		this.createTime = createTime;
		this.createUser = createUser;
		this.status = status;
		this.remark = remark;
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

	public String getScormId()
	{
		return this.scormId;
	}

	public void setScormId(String scormId)
	{
		this.scormId = scormId;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getBrand()
	{
		return this.brand;
	}

	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	public String getProType()
	{
		return this.proType;
	}

	public void setProType(String proType)
	{
		this.proType = proType;
	}

	public Date getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getCreateUser()
	{
		return this.createUser;
	}

	public void setCreateUser(String createUser)
	{
		this.createUser = createUser;
	}

	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getRemark()
	{
		return this.remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getPreview()
	{
		return preview;
	}

	public void setPreview(String preview)
	{
		this.preview = preview;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

}