package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechCourseTesting entity provides the base persistence definition of
 * the TechCourseTesting entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechCourseTesting extends DtoSupport implements
		java.io.Serializable
{

	// Fields

	private String id;
	private String courseId;
	private String titleId;
	private Integer idx;

	// Constructors

	/** default constructor */
	public AbstractTechCourseTesting()
	{
	}

	/** full constructor */
	public AbstractTechCourseTesting(String courseId, String titleId)
	{
		this.courseId = courseId;
		this.titleId = titleId;
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

	public String getTitleId()
	{
		return this.titleId;
	}

	public void setTitleId(String titleId)
	{
		this.titleId = titleId;
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