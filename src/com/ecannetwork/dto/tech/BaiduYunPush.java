package com.ecannetwork.dto.tech;

/**
 * BaiduYunPush entity. @author MyEclipse Persistence Tools
 */
public class BaiduYunPush extends AbstractBaiduYunPush implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public BaiduYunPush() {
	}

	/** full constructor */
	public BaiduYunPush(String loginName, String userid, String channelid,
			Integer deviceType) {
		super(loginName, userid, channelid, deviceType);
	}

}
