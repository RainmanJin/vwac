package com.ecannetwork.dto.tech;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class TechExam implements Serializable{
	
	private static final long serialVersionUID = 4581355724284310457L;
	private String id;
     private String title;
     private Integer timeFlag;
     private Integer showAnswer;
     private Integer mtype;
     private Integer singleScort;
     private Integer multiScort;
     private Integer leftCount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getTimeFlag() {
		return timeFlag;
	}
	public void setTimeFlag(Integer timeFlag) {
		this.timeFlag = timeFlag;
	}
	public Integer getShowAnswer() {
		return showAnswer;
	}
	public void setShowAnswer(Integer showAnswer) {
		this.showAnswer = showAnswer;
	}
	public Integer getMtype() {
		return mtype;
	}
	public void setMtype(Integer mtype) {
		this.mtype = mtype;
	}
	public Integer getSingleScort() {
		return singleScort;
	}
	public void setSingleScort(Integer singleScort) {
		this.singleScort = singleScort;
	}
	public Integer getMultiScort() {
		return multiScort;
	}
	public void setMultiScort(Integer multiScort) {
		this.multiScort = multiScort;
	}
	public Integer getLeftCount() {
		return leftCount;
	}
	public void setLeftCount(Integer leftCount) {
		this.leftCount = leftCount;
	}

}
