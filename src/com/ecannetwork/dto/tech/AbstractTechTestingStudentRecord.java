package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechTestingStudentRecord entity provides the base persistence
 * definition of the TechTestingStudentRecord entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechTestingStudentRecord extends DtoSupport
		implements java.io.Serializable {

	// Fields

	private String id;
	private String studentId;
	private String courseInstanceId;
	private String courseId;
	private String titleId;
	private String answerId;
	private String courseTestingId;
	private String testingInstanceId;

	// Constructors

	public String getTestingInstanceId() {
		return testingInstanceId;
	}

	public void setTestingInstanceId(String testingInstanceId) {
		this.testingInstanceId = testingInstanceId;
	}

	/** default constructor */
	public AbstractTechTestingStudentRecord() {
	}

	/** full constructor */
	public AbstractTechTestingStudentRecord(String studentId,
			String courseInstanceId, String courseId, String titleId,
			String answerId, String courseTestingId, String testingInstanceId) {
		this.studentId = studentId;
		this.courseInstanceId = courseInstanceId;
		this.courseId = courseId;
		this.titleId = titleId;
		this.answerId = answerId;
		this.courseTestingId = courseTestingId;
		this.testingInstanceId = testingInstanceId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStudentId() {
		return this.studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getCourseInstanceId() {
		return this.courseInstanceId;
	}

	public void setCourseInstanceId(String courseInstanceId) {
		this.courseInstanceId = courseInstanceId;
	}

	public String getCourseId() {
		return this.courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getTitleId() {
		return this.titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getAnswerId() {
		return this.answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public String getCourseTestingId() {
		return this.courseTestingId;
	}

	public void setCourseTestingId(String courseTestingId) {
		this.courseTestingId = courseTestingId;
	}

}