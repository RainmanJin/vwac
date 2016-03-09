package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TechCourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechCourse extends AbstractTechCourse implements
		java.io.Serializable
{

	// Constructors

	/** default constructor */
	public TechCourse()
	{
	}

	/** full constructor */
	public TechCourse(String scormId, String name, String brand,
			String proType, Date createTime, String createUser, String status,
			String remark)
	{
		super(scormId, name, brand, proType, createTime, createUser, status,
				remark);
	}

	public List<TechCourseItem> getItems()
	{
		return items;
	}

	public void setItems(List<TechCourseItem> items)
	{
		this.items = items;
	}

	List<TechCourseItem> items = new ArrayList<TechCourseItem>();
}
