package com.ecannetwork.dto.tech;

import java.util.Date;

/**
 * TechHoliday entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechHoliday extends AbstractTechHoliday implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TechHoliday() {
	}

	/** full constructor */
	public TechHoliday(Date beginDay, Date endDay, String userId) {
		super(beginDay, endDay, userId);
	}

}
