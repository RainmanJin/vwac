package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public abstract class AbsctractTechBookCase extends DtoSupport
{

	private static final long serialVersionUID = -3341705742645430740L;
	
	private String id;
	private String pkgid;
	private String status;
	private String type;
	private String bookTime;
	private String userid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPkgid() {
		return pkgid;
	}
	public void setPkgid(String pkgid) {
		this.pkgid = pkgid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBookTime() {
		return bookTime;
	}
	public void setBookTime(String bookTime) {
		this.bookTime = bookTime;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
}
