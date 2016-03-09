package com.ecannetwork.dto.tech;

import java.util.Date;

/**
 * TechSubitmesDb entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechSubitmesDb extends AbstractTechSubitmesDb implements
        java.io.Serializable
{

	// Constructors

	/** default constructor */
	public TechSubitmesDb()
	{
	}

	/** full constructor */
	public TechSubitmesDb(String subitmesId, String dbAnswer,
	        String dbAnswerUser, Date dbAnswerDate, String dbAnswerType,
	        String dbAnswerContent, String questionId, String planId,
	        Integer subitmesIdx)
	{
		super(subitmesId, dbAnswer, dbAnswerUser, dbAnswerDate, dbAnswerType,
		        dbAnswerContent, questionId, planId, subitmesIdx);
	}

}
