package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public class TechTestRecommend extends DtoSupport
{
	private static final long serialVersionUID = 1L;

	private String id;
	private String trainCourseId;
	private String moduleId;
	private String courseDays;
	private String testActiveId;
	private String userId;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTrainCourseId()
	{
		return trainCourseId;
	}

	public void setTrainCourseId(String trainCourseId)
	{
		this.trainCourseId = trainCourseId;
	}

	public String getModuleId()
	{
		return moduleId;
	}

	public void setModuleId(String moduleId)
	{
		this.moduleId = moduleId;
	}

	public String getCourseDays()
	{
		return courseDays;
	}

	public void setCourseDays(String courseDays)
	{
		this.courseDays = courseDays;
	}

	public String getTestActiveId()
	{
		return testActiveId;
	}

	public void setTestActiveId(String testActiveId)
	{
		this.testActiveId = testActiveId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
}
