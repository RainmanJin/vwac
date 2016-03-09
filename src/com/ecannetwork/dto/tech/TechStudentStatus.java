package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TechStudentStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechStudentStatus extends AbstractTechStudentStatus implements
        java.io.Serializable
{

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public TechStudentStatus()
	{
	}

	// 课程对应的scom包list
	public List<TechStudentScoitem> scoitemList = new ArrayList<TechStudentScoitem>();

	// 学员学习课程条数
	private Integer studentScoitem = 0;

	// 课程对应的scom包章节数
	private Integer scoitemCount = 0;

	public Integer getScoitemCount()
	{
		return scoitemCount;
	}

	public void setScoitemCount(Integer scoitemCount)
	{
		this.scoitemCount = scoitemCount;
	}

	public List<TechStudentScoitem> getScoitemList()
	{
		return scoitemList;
	}

	public void setScoitemList(List<TechStudentScoitem> scoitemList)
	{
		this.scoitemList = scoitemList;
	}

	public Integer getStudentScoitem()
	{
		return studentScoitem;
	}

	public void setStudentScoitem(Integer studentScoitem)
	{
		this.studentScoitem = studentScoitem;
	}

	/** full constructor */
	public TechStudentStatus(String courseInstanceId, String studentId,
	        Date startTime, String isFinish, Integer testCount,
	        Double avgPoint, Double highPoint, Double studentCoursePro)
	{
		super(courseInstanceId, studentId, startTime, isFinish, testCount,
		        avgPoint, highPoint, studentCoursePro);
	}

}
