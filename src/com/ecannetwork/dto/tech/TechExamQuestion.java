package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
@JsonSerialize(include = Inclusion.NON_NULL)
public class TechExamQuestion extends AbstractTechExamQuestion{


	private List<TechExamAnswer> tea= new ArrayList<TechExamAnswer>();

	public List<TechExamAnswer> getTea() {
		return tea;
	}

	public void setTea(List<TechExamAnswer> tea) {
		this.tea = tea;
	}
	

	private List<TechExamAnswer> items = new ArrayList<TechExamAnswer>();

	public List<TechExamAnswer> getItems() {
		return items;
	}

	public void setItems(List<TechExamAnswer> items) {
		this.items = items;
	}

}
