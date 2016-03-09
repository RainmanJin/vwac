package com.ecannetwork.dto.tech;

import java.util.Date;

/**
 * TechQuestionManager entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechQuestionManager extends AbstractTechQuestionManager implements
        java.io.Serializable
{

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public TechQuestionManager()
	{
	}

	/** full constructor */
	public TechQuestionManager(String questionName, String questionStatus,
	        Date questionDate, String questionUser, String questionRemark,
	        String trainPlanId, boolean is_Answer)
	{
		super(questionName, questionStatus, questionDate, questionUser,
		        questionRemark, trainPlanId, is_Answer);
	}

}
