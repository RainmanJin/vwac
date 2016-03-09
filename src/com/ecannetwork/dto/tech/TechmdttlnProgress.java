package com.ecannetwork.dto.tech;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * TechmdttlnProgress entity. @author MyEclipse Persistence Tools
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class TechmdttlnProgress extends AbstractTechmdttlnProgress{

	// Constructors

	/** default constructor */
	public TechmdttlnProgress() {
	}

	/** full constructor */
	public TechmdttlnProgress(String mdttlnId, String userId, String progress) {
		super(mdttlnId, userId, progress);
	}

}
