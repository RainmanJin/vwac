package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * AbstractTechmdttlnProgress entity provides the base persistence definition of
 * the TechmdttlnProgress entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechmdttlnProgress extends DtoSupport implements
		java.io.Serializable {

	// Fields

	private String id;
	private String mdttlnId;
	private String pkgid;
	private String userId;
	private String progress;

	// Constructors

	/** default constructor */
	public AbstractTechmdttlnProgress() {
	}

	/** full constructor */
	public AbstractTechmdttlnProgress(String mdttlnId, String userId,
			String progress) {
		this.mdttlnId = mdttlnId;
		this.userId = userId;
		this.progress = progress;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMdttlnId() {
		return this.mdttlnId;
	}

	public void setMdttlnId(String mdttlnId) {
		this.mdttlnId = mdttlnId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProgress() {
		return this.progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getPkgid() {
		return pkgid;
	}

	public void setPkgid(String pkgid) {
		this.pkgid = pkgid;
	}
	
}