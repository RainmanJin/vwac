package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechScormPkg entity provides the base persistence definition of the
 * TechScormPkg entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechScormPkg extends DtoSupport implements
		java.io.Serializable
{
	// Fields

	private String id;
	private String name;
	private String url;
	private Date uploadTime;
	private String uploadUser;
	private String remark;
	private String brand;
	private String proType;
	private String contentType;
	private String preview;
	private String status;

	// Constructors

	/** default constructor */
	public AbstractTechScormPkg()
	{
	}

	/** full constructor */
	public AbstractTechScormPkg(String name, String url, Date uploadTime,
			String uploadUser, String remark)
	{
		this.name = name;
		this.url = url;
		this.uploadTime = uploadTime;
		this.uploadUser = uploadUser;
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

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUrl()
	{
		return this.url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public Date getUploadTime()
	{
		return this.uploadTime;
	}

	public void setUploadTime(Date uploadTime)
	{
		this.uploadTime = uploadTime;
	}

	public String getUploadUser()
	{
		return this.uploadUser;
	}

	public void setUploadUser(String uploadUser)
	{
		this.uploadUser = uploadUser;
	}

	public String getRemark()
	{
		return this.remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
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

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public String getPreview()
	{
		return preview;
	}

	public void setPreview(String preview)
	{
		this.preview = preview;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

}