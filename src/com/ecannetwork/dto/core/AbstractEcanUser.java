package com.ecannetwork.dto.core;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractEcanUser entity provides the base persistence definition of the
 * EcanUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractEcanUser extends DtoSupport implements
        java.io.Serializable
{

	private String id;
	@NotNull
	@Size(min = 1, max = 100)
	private String name;
	@NotNull
	@Size(min = 1, max = 15)
	private String tel;
	@NotNull
	@Size(min = 1, max = 11)
	private String mobile;
	@Email
	private String email;
	private String jobName;
	private String company;
	private String loginName;
	private String loginPasswd;
	private String status;
	private String headImg;
	private Date createTime = new Date();
	private Date lastTime = new Date();
	private String lang;
	private String roleId;
	private String caller;
	private String position;
	private String information;
	private String birthday;
	private String nickName;
	private String cardId;
	// private String proType;
	private String sex;

	// Constructors

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	/** default constructor */
	public AbstractEcanUser()
	{
	}

	/** full constructor */
	public AbstractEcanUser(String name, String tel, String mobile,
	        String email, String jobName, String company, String loginName,
	        String loginPasswd, String status, String headImg, Date createTime,
	        Date lastTime, String lang, String sex, String caller,
	        String position, String information, String birthday,
	        String nickName, String cardId)
	{
		this.name = name;
		this.tel = tel;
		this.mobile = mobile;
		this.email = email;
		this.jobName = jobName;
		this.company = company;
		this.loginName = loginName;
		this.loginPasswd = loginPasswd;
		this.status = status;
		this.headImg = headImg;
		this.createTime = createTime;
		this.lastTime = lastTime;
		this.lang = lang;
		this.sex = sex;
		this.caller = caller;
		this.position = position;
		this.information = information;
		this.birthday = birthday;
		this.nickName = nickName;
		this.cardId = cardId;
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

	public String getTel()
	{
		return this.tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public String getMobile()
	{
		return this.mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getJobName()
	{
		return this.jobName;
	}

	public void setJobName(String jobName)
	{
		this.jobName = jobName;
	}

	public String getCompany()
	{
		return this.company;
	}

	public void setCompany(String company)
	{
		this.company = company;
	}

	public String getLoginName()
	{
		return this.loginName;
	}

	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}

	public String getLoginPasswd()
	{
		return this.loginPasswd;
	}

	public void setLoginPasswd(String loginPasswd)
	{
		this.loginPasswd = loginPasswd;
	}

	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getHeadImg()
	{
		if (StringUtils.isBlank(this.headImg))
		{
			return "/images/defaultHeaderImg.jpg";
		}
		return this.headImg;
	}

	public void setHeadImg(String headImg)
	{
		this.headImg = headImg;
	}

	public Date getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getLastTime()
	{
		return this.lastTime;
	}

	public void setLastTime(Date lastTime)
	{
		this.lastTime = lastTime;
	}

	public String getLang()
	{
		return this.lang;
	}

	public void setLang(String lang)
	{
		this.lang = lang;
	}

	public String getRoleId()
	{
		return roleId;
	}

	public void setRoleId(String roleId)
	{
		this.roleId = roleId;
	}

	public String getCaller()
	{
		return caller;
	}

	public void setCaller(String caller)
	{
		this.caller = caller;
	}

	public String getPosition()
	{
		return position;
	}

	public void setPosition(String position)
	{
		this.position = position;
	}

	public String getInformation()
	{
		return information;
	}

	public void setInformation(String information)
	{
		this.information = information;
	}

	public String getBirthday()
	{
		return birthday;
	}

	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public String getCardId()
	{
		return cardId;
	}

	public void setCardId(String cardId)
	{
		this.cardId = cardId;
	}

	// public String getProType() {
	// return proType;
	// }
	//
	// public void setProType(String proType) {
	// this.proType = proType;
	// }
}