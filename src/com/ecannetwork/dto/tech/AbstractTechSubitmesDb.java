package com.ecannetwork.dto.tech;

import java.util.Date;

/**
 * AbstractTechSubitmesDb entity provides the base persistence definition of the
 * TechSubitmesDb entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechSubitmesDb extends
        com.ecannetwork.core.module.db.dto.DtoSupport implements
        java.io.Serializable
{

	// Fields

	private String id;
	private String subitmesId;
	private String dbAnswer;
	private String dbAnswerUser;
	private Date dbAnswerDate;
	private String dbAnswerType;
	private String dbAnswerContent;
	private String questionId;
	private String planId;
	private Integer subitmesIdx;

	// Constructors

	public Integer getSubitmesIdx()
    {
    	return subitmesIdx;
    }

	public void setSubitmesIdx(Integer subitmesIdx)
    {
    	this.subitmesIdx = subitmesIdx;
    }

	public String getPlanId()
	{
		return planId;
	}

	public void setPlanId(String planId)
	{
		this.planId = planId;
	}

	public String getQuestionId()
	{
		return questionId;
	}

	public void setQuestionId(String questionId)
	{
		this.questionId = questionId;
	}

	/** default constructor */
	public AbstractTechSubitmesDb()
	{
	}

	/** full constructor */
	public AbstractTechSubitmesDb(String subitmesId, String dbAnswer,
	        String dbAnswerUser, Date dbAnswerDate, String dbAnswerType,
	        String dbAnswerContent, String questionId, String planId,Integer subitmesIdx)
	{
		this.subitmesId = subitmesId;
		this.dbAnswer = dbAnswer;
		this.dbAnswerUser = dbAnswerUser;
		this.dbAnswerDate = dbAnswerDate;
		this.dbAnswerType = dbAnswerType;
		this.dbAnswerContent = dbAnswerContent;
		this.questionId = questionId;
		this.planId = planId;
		this.subitmesIdx=subitmesIdx;
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

	public String getSubitmesId()
	{
		return this.subitmesId;
	}

	public void setSubitmesId(String subitmesId)
	{
		this.subitmesId = subitmesId;
	}

	public String getDbAnswer()
	{
		return this.dbAnswer;
	}

	public void setDbAnswer(String dbAnswer)
	{
		this.dbAnswer = dbAnswer;
	}

	public String getDbAnswerUser()
	{
		return this.dbAnswerUser;
	}

	public void setDbAnswerUser(String dbAnswerUser)
	{
		this.dbAnswerUser = dbAnswerUser;
	}

	public Date getDbAnswerDate()
	{
		return this.dbAnswerDate;
	}

	public void setDbAnswerDate(Date dbAnswerDate)
	{
		this.dbAnswerDate = dbAnswerDate;
	}

	public String getDbAnswerType()
	{
		return this.dbAnswerType;
	}

	public void setDbAnswerType(String dbAnswerType)
	{
		this.dbAnswerType = dbAnswerType;
	}

	public String getDbAnswerContent()
	{
		return this.dbAnswerContent;
	}

	public void setDbAnswerContent(String dbAnswerContent)
	{
		this.dbAnswerContent = dbAnswerContent;
	}

}