package com.ecannetwork.dto.tech;

/**
 * TechQuestionSubitems entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechQuestionSubitems extends AbstractTechQuestionSubitems
        implements java.io.Serializable
{

	// Constructors

	/**
     * 
     */
    private static final long serialVersionUID = 8593588141055878473L;

	private String hasCheck;

	private String content;

	public String isHasCheck()
	{
		return hasCheck;
	}

	public void setHasCheck(String hasCheck)
	{
		this.hasCheck = hasCheck;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	/** default constructor */
	public TechQuestionSubitems()
	{
	}

	/** full constructor */
	public TechQuestionSubitems(String questionId, String subitmesName,
	        int subitmesIdx, String subitmeType, String subitmeRemark)
	{
		super(questionId, subitmesName, subitmesIdx, subitmeType, subitmeRemark);
	}

}
