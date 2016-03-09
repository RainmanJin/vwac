package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * TechTrainPlan entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechTrainPlan extends AbstractTechTrainPlan implements
		java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6641797928810803760L;

	public static class Status
	{
		public static final String draft = "draft";// 草稿:::界面不可见
		public static final String fill = "fill";// 待设置资源
		public static final String plan = "plan";// 计划中，资源已设置完成
		public static final String confirm = "confirm";// 已确认，已经完成的进行确认
		public static final String cancel = "cancel";// 已取消，取消计划中的排期

	}

	private Float resSum;
	private String resName;

	// Constructors

	public Float getResSum()
	{
		return resSum;
	}

	public void setResSum(Float resSum)
	{
		this.resSum = resSum;
	}

	/** default constructor */
	public TechTrainPlan()
	{
	}

	/** full constructor */
	public TechTrainPlan(String trainId, String yearValue, Integer planWeek,
			String techerId, String status, Date beginTime, Date endTime,
			String createUser, Date createTime)
	{
		super(trainId, yearValue, planWeek, techerId, status, beginTime,
				endTime, createUser, createTime);
	}

	private Set<String> teacherIds = new HashSet<String>();
	private Set<String> studentIds = new HashSet<String>();

	public Set<String> getStudentIds()
	{
		return studentIds;
	}

	public void setStudentIds(Set<String> studentIds)
	{
		this.studentIds = studentIds;
	}

	public Set<String> getTeacherIds()
	{
		return teacherIds;
	}

	public void setTeacherIds(Set<String> teacherIds)
	{
		this.teacherIds = teacherIds;
	}

	public void setTecherId(String techerId)
	{
		super.setTecherId(techerId);
		if (techerId != null)
		{
			String[] ids = this.getTecherId().split(",");
			for (String id : ids)
			{
				if (StringUtils.isNotBlank(id.trim()))
				{
					this.teacherIds.add(id);
				}
			}
		}
	}

	public List<String> getWeekDays()
	{
		if (StringUtils.isNotBlank(this.getPlanDays()))
		{
			String pdays[] = this.getPlanDays().split(",");
			List<String> set = new ArrayList<String>();

			for (String d : pdays)
			{
				set.add(d);
			}
			return set;
		} else
		{
			return null;
		}
	}

	public String getResName()
	{
		return resName;
	}

	public void setResName(String resName)
	{
		this.resName = resName;
	}

	public String getYearWeek()
	{
		return this.getYearValue() + " / " + this.getPlanWeek();
	}

	private String trainCourseName;

	public String getTrainCourseName()
	{
		return trainCourseName;
	}

	public void setTrainCourseName(String trainCourseName)
	{
		this.trainCourseName = trainCourseName;
	}

}
