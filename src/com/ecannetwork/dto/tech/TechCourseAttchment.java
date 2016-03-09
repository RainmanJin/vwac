package com.ecannetwork.dto.tech;

import java.util.Date;

/**
 * TechCourseAttchment entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechCourseAttchment extends AbstractTechCourseAttchment implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TechCourseAttchment() {
	}

	/** full constructor */
	public TechCourseAttchment(String courseId, String name, String url,
			Date uploadTime, String uploadUser, String remark) {
		super(courseId, name, url, uploadTime, uploadUser, remark);
	}

}
