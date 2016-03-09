package com.ecannetwork.tech.controller.bean;

import java.util.HashSet;
import java.util.Set;

public class TrainPlan
{
	private String id;
	private String courseName;
	private String remark;
	private String proType;
	private String pkgID;

	private Set<String> planDates = new HashSet<String>();

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getPkgID()
	{
		return pkgID;
	}

	public void setPkgID(String pkgID)
	{
		this.pkgID = pkgID;
	}

	public String getProType()
	{
		return proType;
	}

	public void setProType(String proType)
	{
		this.proType = proType;
	}

	public String getCourseName()
	{
		return courseName;
	}

	public void setCourseName(String courseName)
	{
		this.courseName = courseName;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public Set<String> getPlanDates()
	{
		return planDates;
	}

	public void setPlanDates(Set<String> planDates)
	{
		this.planDates = planDates;
	}

}
