package com.ecannetwork.dto.tech;

import java.io.Serializable;


public class AbstractEcanUserMessage extends
com.ecannetwork.core.module.db.dto.DtoSupport implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -1075681099831761627L;

	
	private String id;
	private String userid;
	private String mesid;
	private String status;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMesid() {
		return mesid;
	}
	public void setMesid(String mesid) {
		this.mesid = mesid;
	}
	


}
