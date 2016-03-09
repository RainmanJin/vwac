package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TechCourseTestingDb entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechCourseTestingDb extends AbstractTechCourseTestingDb implements
        java.io.Serializable
{

	// Constructors

	/** default constructor */
	public TechCourseTestingDb()
	{
	}

	/** full constructor */
	public TechCourseTestingDb(String title, String status, Date createTime,
	        String createUser, String answerType, String proType,
	        List<TechCourseTestingDb> list)
	{
		super(title, status, createTime, createUser, answerType, proType, list);
	}

	/**
	 * 所有的答案
	 */
	private List<TechCourseTestingAnswer> ans = new ArrayList<TechCourseTestingAnswer>();

	public List<TechCourseTestingAnswer> getAns()
	{
		return ans;
	}

	public void setAns(List<TechCourseTestingAnswer> ans)
	{
		this.ans = ans;
	}

	// 是否已答题
	private Boolean hasAns;

	public Boolean getHasAns()
	{
		return hasAns;
	}

	public void setHasAns(Boolean hasAns)
	{
		this.hasAns = hasAns;
	}

}
