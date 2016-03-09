package com.ecannetwork.dto.tech;

/**
 * TechUserScoinfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechUserScoinfo extends AbstractTechUserScoinfo implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TechUserScoinfo() {
	}

	/** full constructor */
	public TechUserScoinfo(String userId, String courseId, String scoid,
			String launch, String parameterstring, String TStatus,
			String prerequisites, String TExit, String entry,
			String masteryscore, String idx, String TType) {
		super(userId, courseId, scoid, launch, parameterstring, TStatus,
				prerequisites, TExit, entry, masteryscore, idx, TType);
	}

}
