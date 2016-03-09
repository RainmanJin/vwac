package com.ecannetwork.dto.tech;

/**
 * TechCourseTestingAnswer entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechCourseTestingAnswer extends AbstractTechCourseTestingAnswer
        implements java.io.Serializable
{

	// Constructors

	/** default constructor */
	public TechCourseTestingAnswer()
	{
	}

	/** full constructor */
	public TechCourseTestingAnswer(String testingId, String title,
	        String isRight, Integer idx)
	{
		super(testingId, title, isRight, idx);
	}

	/**
	 * 辅助字段
	 */
	private Boolean checked = Boolean.FALSE;

	public Boolean getChecked()
	{
		return checked;
	}

	public void setChecked(Boolean checked)
	{
		this.checked = checked;
	}

}
