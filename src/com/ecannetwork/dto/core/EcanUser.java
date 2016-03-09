package com.ecannetwork.dto.core;

import java.util.Date;

/**
 * EcanUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class EcanUser extends AbstractEcanUser implements java.io.Serializable
{
	// Fields
	/**
	 * 用户状态
	 * 
	 * @author leo
	 * 
	 */
	public static class STATUS
	{
		/**
		 * 正常用户：：：
		 */
		public static final String ACTIVE = "1";
		/**
		 * 已停用的用户,停用一切系统操作
		 */
		public static final String SUSPENDED = "2";
		/**
		 * 批量增加的用户，状态默认为未激活， 仅可登录，登录后修改用户信息后方可激活
		 */
		public static final String INACTIVE = "3";

		/**
		 * 超级管理员
		 */
		public static final String SUPERMEN = "4";
	}

	// Constructors

	/** default constructor */
	public EcanUser()
	{
	}

	/** full constructor */
	public EcanUser(String name, String tel, String mobile, String email,
	        String jobName, String company, String loginName,
	        String loginPasswd, String status, String headImg, Date createTime,
	        Date lastTime, String lang, String sex, String caller,
	        String position, String information, String birthday,
	        String nickName, String cardId)
	{
		super(name, tel, mobile, email, jobName, company, loginName,
		        loginPasswd, status, headImg, createTime, lastTime, lang, sex,
		        caller, position, information, birthday, nickName, cardId);
	}

	private EcanRole role;

	public EcanRole getRole()
	{
		return role;
	}

	public void setRole(EcanRole role)
	{
		this.role = role;
	}

	/**
	 * 辅助字段
	 */
	private Boolean checked = Boolean.FALSE;

	public Boolean getChecked()
	{
		return checked;
	}

	public void setChecked(Boolean checked)
	{
		this.checked = checked;
	}

}
