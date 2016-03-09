package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * @author 李伟
 * 2015-7-28上午9:15:41
 */
public abstract class AbstractTechMdttLNPkg extends DtoSupport{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5719480751002381587L;
	private String id;
    private String pkgid;
    private String pkgname;
    private String pkgfilepath;
    private String mdttlnid;
    
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
	public String getPkgname() {
		return pkgname;
	}
	public void setPkgname(String pkgname) {
		this.pkgname = pkgname;
	}
	public String getPkgfilepath() {
		return pkgfilepath;
	}
	public void setPkgfilepath(String pkgfilepath) {
		this.pkgfilepath = pkgfilepath;
	}
	public String getMdttlnid() {
		return mdttlnid;
	}
	public void setMdttlnid(String mdttlnid) {
		this.mdttlnid = mdttlnid;
	}
    
}
