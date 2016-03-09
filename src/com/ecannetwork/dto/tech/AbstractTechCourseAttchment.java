package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechCourseAttchment entity provides the base persistence definition
 * of the TechCourseAttchment entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechCourseAttchment extends DtoSupport implements
		java.io.Serializable
{

	// Fields

	private String id;
	private String courseId;
	private String name;
	private String url;
	private Date uploadTime;
	private String uploadUser;
	private String remark;

	// Constructors

	/** default constructor */
	public AbstractTechCourseAttchment()
	{
	}

	/** full constructor */
	public AbstractTechCourseAttchment(String courseId, String name,
			String url, Date uploadTime, String uploadUser, String remark)
	{
		this.courseId = courseId;
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

	public String getCourseId()
	{
		return this.courseId;
	}

	public void setCourseId(String courseId)
	{
		this.courseId = courseId;
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

}