package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TechTestingInstance entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechTestingInstance extends AbstractTechTestingInstance implements
        java.io.Serializable
{

	// Constructors

	/** default constructor */
	public TechTestingInstance()
	{
	}

	/** full constructor */
	public TechTestingInstance(String studentId, String courseInstanceId,
	        String courseId, Double testingPoint, Date testingTime,
	        String isFinish)
	{
		super(studentId, courseInstanceId, courseId, testingPoint, testingTime,
		        isFinish);
	}

	// 用户选择的所有答案编号
	private Set<String> userAns = new HashSet<String>();

	//试卷编号
	private String courseTesttingId;
	/**
	 * 所有的题目，包含答案
	 */
	private List<TechCourseTestingDb> testingDBS = new ArrayList<TechCourseTestingDb>();

	public Set<String> getUserAns()
	{
		return userAns;
	}

	public void setUserAns(Set<String> userAns)
	{
		this.userAns = userAns;
	}

	public List<TechCourseTestingDb> getTestingDBS()
	{
		return testingDBS;
	}

	public void setTestingDBS(List<TechCourseTestingDb> testingDBS)
	{
		this.testingDBS = testingDBS;
	}

	public String getCourseTesttingId()
    {
    	return courseTesttingId;
    }

	public void setCourseTesttingId(String courseTesttingId)
    {
    	this.courseTesttingId = courseTesttingId;
    }

}
