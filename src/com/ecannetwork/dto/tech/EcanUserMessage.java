package com.ecannetwork.dto.tech;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class EcanUserMessage extends AbstractEcanUserMessage{

	private static final long serialVersionUID = 8576085698098542987L;
	
	private TechUserMessage message;

	public TechUserMessage getMessage() {
		return message;
	}

	public void setMessage(TechUserMessage message) {
		this.message = message;
	}
	
}
