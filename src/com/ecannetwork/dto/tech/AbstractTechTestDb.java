package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechTestDb entity provides the base persistence definition of the
 * TechTestDb entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechTestDb extends DtoSupport implements java.io.Serializable
{

	// Fields

	private String id;
	private String title;
	private String status;
	private Date createTime;
	private String createUserId;
	private String answerType;
	private String proType;
	private String trainCourseId;
	private String brand;
	private String moduleId;

	private List<TechTestAnswer> list = new ArrayList<TechTestAnswer>();

	// Constructors

	/** default constructor */
	public AbstractTechTestDb()
	{
	}

	/** full constructor */
	public AbstractTechTestDb(String title, String status, Date createTime, String createUserId, String answerType, String proType, String trainCourseId, String brand, List<TechTestAnswer> list)
	{
		this.title = title;
		this.status = status;
		this.createTime = createTime;
		this.createUserId = createUserId;
		this.answerType = answerType;
		this.proType = proType;
		this.trainCourseId = trainCourseId;
		this.brand = brand;
		this.list = list;
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

	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Date getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getCreateUserId()
	{
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId)
	{
		this.createUserId = createUserId;
	}

	public String getAnswerType()
	{
		return this.answerType;
	}

	public void setAnswerType(String answerType)
	{
		this.answerType = answerType;
	}

	public String getProType()
	{
		return this.proType;
	}

	public void setProType(String proType)
	{
		this.proType = proType;
	}

	public String getTrainCourseId()
	{
		return this.trainCourseId;
	}

	public void setTrainCourseId(String trainCourseId)
	{
		this.trainCourseId = trainCourseId;
	}

	public String getBrand()
	{
		return this.brand;
	}

	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	public List<TechTestAnswer> getList()
	{
		return list;
	}

	public void setList(List<TechTestAnswer> list)
	{
		this.list = list;
	}

	public String getModuleId()
	{
		return moduleId;
	}

	public void setModuleId(String moduleId)
	{
		this.moduleId = moduleId;
	}

}