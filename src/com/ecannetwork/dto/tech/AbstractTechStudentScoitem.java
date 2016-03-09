package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechStudentScoitem entity provides the base persistence definition of
 * the TechStudentScoitem entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechStudentScoitem extends DtoSupport implements
		java.io.Serializable
{

	// Fields

	private String id;
	private String studentId;
	private String itemId;
	private String courseId;
	private String courseInstanceId;

	// Constructors

	/** default constructor */
	public AbstractTechStudentScoitem()
	{
	}

	/** full constructor */
	public AbstractTechStudentScoitem(String studentId, String itemId,
			String courseId, String courseInstanceId)
	{
		this.studentId = studentId;
		this.itemId = itemId;
		this.courseId = courseId;
		this.courseInstanceId = courseInstanceId;
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

	public String getStudentId()
	{
		return this.studentId;
	}

	public void setStudentId(String studentId)
	{
		this.studentId = studentId;
	}

	public String getItemId()
	{
		return this.itemId;
	}

	public void setItemId(String itemId)
	{
		this.itemId = itemId;
	}

	public String getCourseId()
	{
		return this.courseId;
	}

	public void setCourseId(String courseId)
	{
		this.courseId = courseId;
	}

	public String getCourseInstanceId()
	{
		return this.courseInstanceId;
	}

	public void setCourseInstanceId(String courseInstanceId)
	{
		this.courseInstanceId = courseInstanceId;
	}

}