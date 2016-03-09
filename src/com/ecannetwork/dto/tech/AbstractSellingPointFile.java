package com.ecannetwork.dto.tech;

/**
 * AbstractSellingPointFile entity provides the base persistence definition of
 * the SellingPointFile entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractSellingPointFile extends
com.ecannetwork.core.module.db.dto.DtoSupport implements java.io.Serializable {

	// Fields

	private String id;
	private String fileName;
	private String tail;
	private String filePath;
	private String createTime;
	private Long fileSize;
	private String fileType;

	// Constructors

	/** default constructor */
	public AbstractSellingPointFile() {
	}

	/** full constructor */
	public AbstractSellingPointFile(String fileName, String tail,
			String filePath, String createTime, Long fileSize,
			String fileType) {
		this.fileName = fileName;
		this.tail = tail;
		this.filePath = filePath;
		this.createTime = createTime;
		this.fileSize = fileSize;
		this.fileType = fileType;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTail() {
		return this.tail;
	}

	public void setTail(String tail) {
		this.tail = tail;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}