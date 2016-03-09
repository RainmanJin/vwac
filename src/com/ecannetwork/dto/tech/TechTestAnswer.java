package com.ecannetwork.dto.tech;

/**
 * TechTestAnswer entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechTestAnswer extends AbstractTechTestAnswer implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TechTestAnswer() {
	}

	/** full constructor */
	public TechTestAnswer(String testingId, String title, String isRight,
			Integer idx) {
		super(testingId, title, isRight, idx);
	}

}
