package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class TechExamMain extends AbstractTechExamMain{
	
	private List<TechExamQuestion> questionList = new ArrayList<TechExamQuestion>();

	public List<TechExamQuestion> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<TechExamQuestion> questionList) {
		this.questionList = questionList;
	}
	
	

}
