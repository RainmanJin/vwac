package com.ecannetwork.dto.tech;

/**
 * TechTestingStudentRecord entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechTestingStudentRecord extends AbstractTechTestingStudentRecord
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public TechTestingStudentRecord() {
	}

	/** full constructor */
	public TechTestingStudentRecord(String studentId, String courseInstanceId,
			String courseId, String titleId, String answerId,
			String courseTestingId, String testingInstanceId) {
		super(studentId, courseInstanceId, courseId, titleId, answerId,
				courseTestingId, testingInstanceId);
	}

}
