package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TechCouseInstance entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechCouseInstance extends AbstractTechCouseInstance implements
        java.io.Serializable
{

	// Constructors

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	/** default constructor */
	public TechCouseInstance()
	{
	}

	// 参与课程的人数
	private Integer studentCount = 0;

	private Integer isFinishCount = 0;
	
	//未进行测试人数
	private Integer noFinishCount = 0 ;
	
	//课程对应的学员
	private List<TechStudentStatus> studentList=new ArrayList<TechStudentStatus>();
	
	private String progress ="";

	private String avgPoint = "";

	private String courseStatus = "";
	private boolean hasDbs=false;

	/** full constructor */
	public TechCouseInstance(String name, String teacher, String remark,
	        Date createTime, String courseId, String status, Date expireTime,
	        String brand, String proType, String preview)
	{
		super(name, teacher, remark, createTime, courseId, status, expireTime,
		        brand, proType, preview);
	}

	public Integer getStudentCount()
	{
		return studentCount;
	}

	public void setStudentCount(Integer studentCount)
	{
		this.studentCount = studentCount;
	}

	public String getCourseStatus()
	{
		return courseStatus;
	}

	public void setCourseStatus(String courseStatus)
	{
		this.courseStatus = courseStatus;
	}

	public Integer getIsFinishCount()
	{
		return isFinishCount;
	}

	public void setIsFinishCount(Integer isFinishCount)
	{
		this.isFinishCount = isFinishCount;
	}

	public String getAvgPoint()
	{
		return avgPoint;
	}

	public void setAvgPoint(String avgPoint)
	{
		this.avgPoint = avgPoint;
	}

	public Integer getNoFinishCount()
    {
    	return noFinishCount;
    }

	public void setNoFinishCount(Integer noFinishCount)
    {
    	this.noFinishCount = noFinishCount;
    }

	public String getProgress()
    {
    	return progress;
    }

	public void setProgress(String progress)
    {
    	this.progress = progress;
    }

	public List<TechStudentStatus> getStudentList()
    {
    	return studentList;
    }

	public void setStudentList(List<TechStudentStatus> studentList)
    {
    	this.studentList = studentList;
    }

	public boolean isHasDbs()
    {
    	return hasDbs;
    }

	public void setHasDbs(boolean hasDbs)
    {
    	this.hasDbs = hasDbs;
    }

}
