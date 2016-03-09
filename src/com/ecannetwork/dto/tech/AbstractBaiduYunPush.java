package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractBaiduYunPush entity provides the base persistence definition of the
 * BaiduYunPush entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractBaiduYunPush extends DtoSupport {

	// Fields

	private String id;
	private String loginName;
	private String userid;
	private String channelid;
	private Integer deviceType;

	// Constructors

	/** default constructor */
	public AbstractBaiduYunPush() {
	}

	/** full constructor */
	public AbstractBaiduYunPush(String loginName, String userid,
			String channelid, Integer deviceType) {
		this.loginName = loginName;
		this.userid = userid;
		this.channelid = channelid;
		this.deviceType = deviceType;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getChannelid() {
		return this.channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}


}