package com.ecannetwork.dto.tech;

/**
 * AbstractSellingPointConent entity provides the base persistence definition of
 * the SellingPointConent entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractSellingPointConent extends
com.ecannetwork.core.module.db.dto.DtoSupport implements
		java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String content;
	private String contentTag;
	private String createTime;
	private Long contentSize;
	private String mainId;

	// Constructors

	/** default constructor */
	public AbstractSellingPointConent() {
	}

	/** full constructor */
	public AbstractSellingPointConent(String title, String content,
			String contentTag, String createTime,Long contentSize) {
		this.title = title;
		this.content = content;
		this.contentTag = contentTag;
		this.createTime = createTime;
		this.contentSize = contentSize;
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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentTag() {
		return this.contentTag;
	}

	public void setContentTag(String contentTag) {
		this.contentTag = contentTag;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getContentSize() {
		return contentSize;
	}

	public void setContentSize(Long contentSize) {
		this.contentSize = contentSize;
	}

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

}