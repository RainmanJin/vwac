package com.ecannetwork.dto.tech;

/**
 * AbstractTechUserCourseInfo entity provides the base persistence definition of
 * the TechUserCourseInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechUserCourseInfo extends
		com.ecannetwork.core.module.db.dto.DtoSupport implements java.io.Serializable {

	// Fields

	private String id;
	private String userId;
	private String courseId;

	// Constructors

	/** default constructor */
	public AbstractTechUserCourseInfo() {
	}

	/** full constructor */
	public AbstractTechUserCourseInfo(String userId, String courseId) {
		this.userId = userId;
		this.courseId = courseId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCourseId() {
		return this.courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

}