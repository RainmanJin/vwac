package com.ecannetwork.dto.tech;

/**
 * SellingPointFile entity. @author MyEclipse Persistence Tools
 */
public class SellingPointFile extends AbstractSellingPointFile implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public SellingPointFile() {
	}

	/** full constructor */
	public SellingPointFile(String fileName, String tail, String filePath,
			String createTime, Long fileSize, String fileType) {
		super(fileName, tail, filePath, createTime, fileSize, fileType);
	}

}
