package com.ecannetwork.dto.tech;

import java.util.Date;

/**
 * TechScormPkg entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechScormPkg extends AbstractTechScormPkg implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TechScormPkg() {
	}

	/** full constructor */
	public TechScormPkg(String name, String url, Date uploadTime,
			String uploadUser, String remark) {
		super(name, url, uploadTime, uploadUser, remark);
	}

}
