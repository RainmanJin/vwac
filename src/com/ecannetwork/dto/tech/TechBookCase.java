package com.ecannetwork.dto.tech;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class TechBookCase extends AbsctractTechBookCase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
