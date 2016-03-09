package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * @author 李伟
 * 2015-8-19下午9:29:43
 */
public class AbstractTechMdttPkgUsers extends DtoSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3727275227175843136L;
	private String id;
	private String userId;
	private String pkgId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPkgId() {
		return pkgId;
	}
	public void setPkgId(String pkgId) {
		this.pkgId = pkgId;
	}
	
}
