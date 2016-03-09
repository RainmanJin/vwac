package com.ecannetwork.dto.tech;

/**
 * AbstractTechQuestionSubitems entity provides the base persistence definition
 * of the TechQuestionSubitems entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechQuestionSubitems extends
        com.ecannetwork.core.module.db.dto.DtoSupport implements
        java.io.Serializable
{

	// Fields

	private String id;
	private String questionId;
	private String subitmesName;
	private int subitmesIdx;
	private String subitmeType;
	private String subitmeRemark;
	private String subitmeTypeString;

	// Constructors

	/** default constructor */
	public AbstractTechQuestionSubitems()
	{
	}

	/** full constructor */
	public AbstractTechQuestionSubitems(String questionId, String subitmesName,
	        int subitmesIdx, String subitmeType, String subitmeRemark)
	{
		this.questionId = questionId;
		this.subitmesName = subitmesName;
		this.subitmesIdx = subitmesIdx;
		this.subitmeType = subitmeType;
		this.subitmeRemark = subitmeRemark;
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

	public String getQuestionId()
	{
		return this.questionId;
	}

	public void setQuestionId(String questionId)
	{
		this.questionId = questionId;
	}

	public String getSubitmesName()
	{
		return this.subitmesName;
	}

	public void setSubitmesName(String subitmesName)
	{
		this.subitmesName = subitmesName;
	}


	public int getSubitmesIdx()
    {
    	return subitmesIdx;
    }

	public void setSubitmesIdx(int subitmesIdx)
    {
    	this.subitmesIdx = subitmesIdx;
    }

	public String getSubitmeType()
	{
		return this.subitmeType;
	}

	public void setSubitmeType(String subitmeType)
	{
		this.subitmeType = subitmeType;
	}

	public String getSubitmeRemark()
	{
		return this.subitmeRemark;
	}

	public void setSubitmeRemark(String subitmeRemark)
	{
		this.subitmeRemark = subitmeRemark;
	}

	public String getSubitmeTypeString()
	{
		if (subitmeType != null && !"".equals(subitmeType))
		{
			switch (Integer.valueOf(subitmeType))
			{
			case 1:
				subitmeTypeString = "i18n.question.type.radio";
				break;
			case 2:
				subitmeTypeString = "i18n.question.type.comment";
				break;
			default:
				subitmeTypeString = "i18n.question.type.radio";
				break;
			}
		}
		return subitmeTypeString;
	}

	public void setSubitmeTypeString(String subitmeTypeString)
	{
		this.subitmeTypeString = subitmeTypeString;
	}
}