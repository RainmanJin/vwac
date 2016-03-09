package com.ecannetwork.dto.tech;

import java.util.Date;

/**
 * AbstractTechQuestionManager entity provides the base persistence definition
 * of the TechQuestionManager entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechQuestionManager extends
        com.ecannetwork.core.module.db.dto.DtoSupport implements
        java.io.Serializable
{

	// Fields

	private String id;
	private String questionName;
	private String questionStatus;
	private Date questionDate;
	private String questionUser;
	private String questionRemark;
	private String trainPlanId;
	private boolean is_Answer;
	private String eid;

	// Constructors

	public String getTrainPlanId()
	{
		return trainPlanId;
	}

	public void setTrainPlanId(String trainPlanId)
	{
		this.trainPlanId = trainPlanId;
	}

	/** default constructor */
	public AbstractTechQuestionManager()
	{
	}

	/** full constructor */
	public AbstractTechQuestionManager(String questionName,
	        String questionStatus, Date questionDate, String questionUser,
	        String questionRemark, String trainPlanId, boolean is_Answer)
	{
		this.questionName = questionName;
		this.questionStatus = questionStatus;
		this.questionDate = questionDate;
		this.questionUser = questionUser;
		this.questionRemark = questionRemark;
		this.trainPlanId = trainPlanId;
		this.is_Answer = is_Answer;
	}

	// Property accessors

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getQuestionName()
	{
		return this.questionName;
	}

	public void setQuestionName(String questionName)
	{
		this.questionName = questionName;
	}

	public String getQuestionStatus()
	{
		return this.questionStatus;
	}

	public void setQuestionStatus(String questionStatus)
	{
		this.questionStatus = questionStatus;
	}

	public Date getQuestionDate()
	{
		return this.questionDate;
	}

	public void setQuestionDate(Date questionDate)
	{
		this.questionDate = questionDate;
	}

	public String getQuestionUser()
	{
		return this.questionUser;
	}

	public void setQuestionUser(String questionUser)
	{
		this.questionUser = questionUser;
	}

	public String getQuestionRemark()
	{
		return this.questionRemark;
	}

	public void setQuestionRemark(String questionRemark)
	{
		this.questionRemark = questionRemark;
	}

	public boolean isIs_Answer()
    {
    	return is_Answer;
    }

	public void setIs_Answer(boolean is_Answer)
    {
    	this.is_Answer = is_Answer;
    }

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

 

}