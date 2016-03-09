package com.ecannetwork.dto.tech;

/**
 * TechStudentScoitem entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechStudentScoitem extends AbstractTechStudentScoitem implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TechStudentScoitem() {
	}

	/** full constructor */
	public TechStudentScoitem(String studentId, String itemId, String courseId,
			String courseInstanceId) {
		super(studentId, itemId, courseId, courseInstanceId);
	}

}
