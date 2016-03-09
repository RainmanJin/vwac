package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechCouseInstance entity provides the base persistence definition of
 * the TechCouseInstance entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechCouseInstance extends DtoSupport implements
		java.io.Serializable
{

	// Fields

	private String id;
	private String name;
	private String teacher;
	private String remark;
	private Date createTime;
	private String courseId;
	private String status;
	private Date expireTime;
	private String brand;
	private String proType;
	private String preview;

	// Constructors

	/** default constructor */
	public AbstractTechCouseInstance()
	{
	}

	/** full constructor */
	public AbstractTechCouseInstance(String name, String teacher,
			String remark, Date createTime, String courseId, String status,
			Date expireTime,String brand,String proType,String preview)
	{
		this.name = name;
		this.teacher = teacher;
		this.remark = remark;
		this.createTime = createTime;
		this.courseId = courseId;
		this.status = status;
		this.expireTime = expireTime;
		this.brand = brand;
		this.proType = proType;
		this.preview = preview;
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

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTeacher()
	{
		return this.teacher;
	}

	public void setTeacher(String teacher)
	{
		this.teacher = teacher;
	}

	public String getRemark()
	{
		return this.remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public Date getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getCourseId()
	{
		return this.courseId;
	}

	public void setCourseId(String courseId)
	{
		this.courseId = courseId;
	}

	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Date getExpireTime()
	{
		return this.expireTime;
	}

	public void setExpireTime(Date expireTime)
	{
		this.expireTime = expireTime;
	}

	 

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

}