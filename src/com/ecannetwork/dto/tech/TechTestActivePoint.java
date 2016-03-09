package com.ecannetwork.dto.tech;

/**
 * TechActivePoint entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechTestActivePoint extends AbstractTechTestActivePoint implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TechTestActivePoint() {
	}

	/** full constructor */
	public TechTestActivePoint(String step, String dimension, String watchUserId,
			String userId, Double points) {
		super(step, dimension, watchUserId, userId, points);
	}

}
