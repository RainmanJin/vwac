package com.ecannetwork.dto.tech;

/**
 * TechUserCourseInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechUserCourseInfo extends AbstractTechUserCourseInfo implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TechUserCourseInfo() {
	}

	/** full constructor */
	public TechUserCourseInfo(String userId, String courseId) {
		super(userId, courseId);
	}

}
