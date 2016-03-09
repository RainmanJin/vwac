package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public class TechTrainCourseItem extends DtoSupport
{
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String trainCourseId;

	public TechTrainCourseItem()
	{

	}

	public TechTrainCourseItem(String id, String name, String trainCourseId)
	{
		super();
		this.id = id;
		this.name = name;
		this.trainCourseId = trainCourseId;
	}

	public TechTrainCourseItem(TechTrainCourseItem item, TechTrainCourse course)
	{
		this(item.getId(), item.getName(), item.getTrainCourseId());

		this.trainCourse = course;
	}

	/**
	 * 辅助对象
	 */
	private TechTrainCourse trainCourse;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTrainCourseId()
	{
		return trainCourseId;
	}

	public void setTrainCourseId(String trainCourseId)
	{
		this.trainCourseId = trainCourseId;
	}

	public TechTrainCourse getTrainCourse()
	{
		return trainCourse;
	}

	public void setTrainCourse(TechTrainCourse trainCourse)
	{
		this.trainCourse = trainCourse;
	}
}
