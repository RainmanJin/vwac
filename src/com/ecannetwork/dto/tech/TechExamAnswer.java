package com.ecannetwork.dto.tech;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;



/**
 * TechExamAnswer entity. @author MyEclipse Persistence Tools
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class TechExamAnswer extends AbstractTechExamAnswer{


	private TechExamQuestion examQuestion;

	public TechExamQuestion getExamQuestion() {
		return examQuestion;
	}

	public void setExamQuestion(TechExamQuestion examQuestion) {
		this.examQuestion = examQuestion;
	}
	
}
