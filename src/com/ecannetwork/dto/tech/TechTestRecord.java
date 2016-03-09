package com.ecannetwork.dto.tech;

/**
 * TechTestRecord entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechTestRecord extends AbstractTechTestRecord implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TechTestRecord() {
	}

	/** full constructor */
	public TechTestRecord(String userId, String testActiveId, String titleId,
			String answerId, String testId) {
		super(userId, testActiveId, titleId, answerId, testId);
	}

}
