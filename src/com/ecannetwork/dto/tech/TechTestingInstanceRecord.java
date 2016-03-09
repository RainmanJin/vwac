package com.ecannetwork.dto.tech;

import java.util.Date;

/**
 * TechTestingInstanceRecord entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
public class TechTestingInstanceRecord extends
		AbstractTechTestingInstanceRecord implements java.io.Serializable
{
	// Constructors

	/** default constructor */
	public TechTestingInstanceRecord()
	{
	}

	/** full constructor */
	public TechTestingInstanceRecord(String courseId, Double points,
			Date beginTime, Date endTime)
	{
		super(courseId, points, beginTime, endTime);
	}

}
