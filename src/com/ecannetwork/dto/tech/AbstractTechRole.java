package com.ecannetwork.dto.tech;

/**
 * AbstractTechRole entity provides the base persistence definition of the
 * TechRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechRole extends
		com.ecannetwork.core.module.db.dto.DtoSupport implements java.io.Serializable {

	// Fields

	private String id;
	private String userId;
	private String roleId;

	// Constructors

	/** default constructor */
	public AbstractTechRole() {
	}

	/** full constructor */
	public AbstractTechRole(String userId, String roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}