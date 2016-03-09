package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechStudentStatus entity provides the base persistence definition of
 * the TechStudentStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechStudentStatus extends DtoSupport implements
        java.io.Serializable
{

	// Fields

	private String id;
	private String courseInstanceId;
	private String studentId;
	private Date startTime;
	private String isFinish;
	private Integer testCount;
	private Double avgPoint;
	private Double highPoint;
	private Double studentCoursePro;

	// Constructors

	public Double getStudentCoursePro()
    {
    	return studentCoursePro;
    }

	public void setStudentCoursePro(Double studentCoursePro)
    {
    	this.studentCoursePro = studentCoursePro;
    }

	public Double getHighPoint()
	{
		return highPoint;
	}

	public void setHighPoint(Double highPoint)
	{
		this.highPoint = highPoint;
	}

	/** default constructor */
	public AbstractTechStudentStatus()
	{
	}

	/** full constructor */
	public AbstractTechStudentStatus(String courseInstanceId, String studentId,
	        Date startTime, String isFinish, Integer testCount,
	        Double avgPoint, Double highPoint,Double studentCoursePro)
	{
		this.courseInstanceId = courseInstanceId;
		this.studentId = studentId;
		this.startTime = startTime;
		this.isFinish = isFinish;
		this.testCount = testCount;
		this.avgPoint = avgPoint;
		this.highPoint = highPoint;
		this.studentCoursePro = studentCoursePro;
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

	public String getCourseInstanceId()
	{
		return this.courseInstanceId;
	}

	public void setCourseInstanceId(String courseInstanceId)
	{
		this.courseInstanceId = courseInstanceId;
	}

	public String getStudentId()
	{
		return this.studentId;
	}

	public void setStudentId(String studentId)
	{
		this.studentId = studentId;
	}

	public Date getStartTime()
	{
		return this.startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public String getIsFinish()
	{
		return this.isFinish;
	}

	public void setIsFinish(String isFinish)
	{
		this.isFinish = isFinish;
	}

	public Integer getTestCount()
	{
		return this.testCount;
	}

	public void setTestCount(Integer testCount)
	{
		this.testCount = testCount;
	}

	public Double getAvgPoint()
	{
		return this.avgPoint;
	}

	public void setAvgPoint(Double avgPoint)
	{
		this.avgPoint = avgPoint;
	}

}