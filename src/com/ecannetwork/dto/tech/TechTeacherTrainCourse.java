package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public class TechTeacherTrainCourse extends DtoSupport
{
	private String id;
	private String teacherId;
	private String trainCourseId;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTeacherId()
	{
		return teacherId;
	}

	public void setTeacherId(String teacherId)
	{
		this.teacherId = teacherId;
	}

	public String getTrainCourseId()
	{
		return trainCourseId;
	}

	public void setTrainCourseId(String trainCourseId)
	{
		this.trainCourseId = trainCourseId;
	}
}
