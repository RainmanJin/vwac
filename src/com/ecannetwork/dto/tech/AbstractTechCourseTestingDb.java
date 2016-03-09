package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechCourseTestingDb entity provides the base persistence definition
 * of the TechCourseTestingDb entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechCourseTestingDb extends DtoSupport implements
		java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String status;
	private Date createTime;
	private String createUser;
	private String answerType;
	private String proType;
	private String trainCourseId;
	private String brand;
	private String moduleId;
	
	private List<TechCourseTestingDb> list = new ArrayList<TechCourseTestingDb>();

	// Constructors

	/** default constructor */
	public AbstractTechCourseTestingDb() {
	}

	/** full constructor */
	public AbstractTechCourseTestingDb(String title, String status,
			Date createTime, String createUser, String answerType,String proType,
			List<TechCourseTestingDb> list) {
		this.title = title;
		this.status = status;
		this.createTime = createTime;
		this.createUser = createUser;
		this.answerType = answerType;
		this.proType = proType;
		this.list = list;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getAnswerType() {
		return this.answerType;
	}

	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}

	public List<TechCourseTestingDb> getList() {
		return list;
	}

	public void setList(List<TechCourseTestingDb> list) {
		this.list = list;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public String getTrainCourseId()
	{
		return trainCourseId;
	}

	public void setTrainCourseId(String trainCourseId)
	{
		this.trainCourseId = trainCourseId;
	}

	public String getBrand()
	{
		return brand;
	}

	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	public String getModuleId()
	{
		return moduleId;
	}

	public void setModuleId(String moduleId)
	{
		this.moduleId = moduleId;
	}

}