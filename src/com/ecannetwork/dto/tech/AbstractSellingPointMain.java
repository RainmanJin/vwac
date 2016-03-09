package com.ecannetwork.dto.tech;

/**
 * AbstractSellingPointMain entity provides the base persistence definition of
 * the SellingPointMain entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractSellingPointMain extends
com.ecannetwork.core.module.db.dto.DtoSupport implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String createTime;
	private String status;
	private String contentId;
	private String customIndex;
	private String pkgid;

	// Constructors

	/** default constructor */
	public AbstractSellingPointMain() {
	}

	/** full constructor */
	public AbstractSellingPointMain(String title, String createTime,
			String status, String contentId, String pkgid) {
		this.title = title;
		this.createTime = createTime;
		this.status = status;
		this.contentId = contentId;
		this.pkgid = pkgid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContentId() {
		return this.contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getPkgid() {
		return this.pkgid;
	}

	public void setPkgid(String pkgid) {
		this.pkgid = pkgid;
	}

	public String getCustomIndex() {
		return customIndex;
	}

	public void setCustomIndex(String customIndex) {
		this.customIndex = customIndex;
	}

}