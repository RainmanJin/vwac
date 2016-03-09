package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechTrainPlan entity provides the base persistence definition of the
 * TechTrainPlan entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechTrainPlan extends DtoSupport implements
		java.io.Serializable
{

	// Fields

	private String id;
	/**
	 * 课程编号
	 */
	private String trainId;
	private String yearValue;
	private Integer planWeek;
	private String techerId;
	private String status;
	private Date beginTime;
	private Date endTime;
	private String createUser;
	private Date createTime;
	private String brand;
	private String remark;
	private String moduleId;
	private String planDays;// 计划培训时间:::1,2,3,4,5
	private String planChged;// 计划是否变更
	private String planQuesID;
	private String cancelReason;

	// Constructors

	/** default constructor */
	public AbstractTechTrainPlan()
	{
	}

	/** full constructor */
	public AbstractTechTrainPlan(String trainId, String yearValue,
			Integer planWeek, String techerId, String status, Date beginTime,
			Date endTime, String createUser, Date createTime)
	{
		this.trainId = trainId;
		this.yearValue = yearValue;
		this.planWeek = planWeek;
		this.techerId = techerId;
		this.status = status;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.createUser = createUser;
		this.createTime = createTime;
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

	public String getTrainId()
	{
		return this.trainId;
	}

	public void setTrainId(String trainId)
	{
		this.trainId = trainId;
	}

	public String getYearValue()
	{
		return this.yearValue;
	}

	public void setYearValue(String yearValue)
	{
		this.yearValue = yearValue;
	}

	public Integer getPlanWeek()
	{
		return this.planWeek;
	}

	public void setPlanWeek(Integer planWeek)
	{
		this.planWeek = planWeek;
	}

	public String getTecherId()
	{
		return this.techerId;
	}

	public void setTecherId(String techerId)
	{
		this.techerId = techerId;
	}

	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Date getBeginTime()
	{
		return this.beginTime;
	}

	public void setBeginTime(Date beginTime)
	{
		this.beginTime = beginTime;
	}

	public Date getEndTime()
	{
		return this.endTime;
	}

	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}

	public String getCreateUser()
	{
		return this.createUser;
	}

	public void setCreateUser(String createUser)
	{
		this.createUser = createUser;
	}

	public Date getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getBrand()
	{
		return brand;
	}

	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getModuleId()
	{
		return moduleId;
	}

	public void setModuleId(String moduleId)
	{
		this.moduleId = moduleId;
	}

	public String getPlanDays()
	{
		return planDays;
	}

	public void setPlanDays(String planDays)
	{
		this.planDays = planDays;
	}

	public String getPlanChged()
	{
		return planChged;
	}

	public void setPlanChged(String planChged)
	{
		this.planChged = planChged;
	}

	public String getPlanQuesID()
	{
		return planQuesID;
	}

	public void setPlanQuesID(String planQuesID)
	{
		this.planQuesID = planQuesID;
	}

	public String getCancelReason()
	{
		return cancelReason;
	}

	public void setCancelReason(String cancelReason)
	{
		this.cancelReason = cancelReason;
	}
}