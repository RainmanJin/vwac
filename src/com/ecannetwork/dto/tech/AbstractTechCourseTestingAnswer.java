package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechCourseTestingAnswer entity provides the base persistence
 * definition of the TechCourseTestingAnswer entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechCourseTestingAnswer extends DtoSupport
		implements java.io.Serializable {

	// Fields

	private String id;
	private String testingId;
	private String title;
	private String isRight;
	private Integer idx;

	// Constructors

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	/** default constructor */
	public AbstractTechCourseTestingAnswer() {
	}

	/** full constructor */
	public AbstractTechCourseTestingAnswer(String testingId, String title,
			String isRight, Integer idx) {
		this.testingId = testingId;
		this.title = title;
		this.isRight = isRight;
		this.idx = idx;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTestingId() {
		return this.testingId;
	}

	public void setTestingId(String testingId) {
		this.testingId = testingId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsRight() {
		return this.isRight;
	}

	public void setIsRight(String isRight) {
		this.isRight = isRight;
	}

}