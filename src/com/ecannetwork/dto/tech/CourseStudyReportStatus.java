package com.ecannetwork.dto.tech;

/**
 * TechCouseInstance entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class CourseStudyReportStatus implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private Double avg = new Double(0);
	private String courseInstanceId;

	public CourseStudyReportStatus()
	{

	}

	public CourseStudyReportStatus(Double avg,String courseInstanceId)
	{
		this.courseInstanceId = courseInstanceId;
		this.avg = avg;

	}

	public String getCourseInstanceId()
	{
		return courseInstanceId;
	}

	public void setCourseInstanceId(String courseInstanceId)
	{
		this.courseInstanceId = courseInstanceId;
	}

	public Double getAvg()
	{
		return avg;
	}

	public void setAvg(Double avg)
	{
		this.avg = avg;
	}

}
