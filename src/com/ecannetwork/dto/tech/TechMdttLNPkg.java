package com.ecannetwork.dto.tech;

/**
 * @author 李伟
 * 2015-7-28上午9:09:17
 */
public class TechMdttLNPkg extends AbstractTechMdttLNPkg{
	private TechMdttPkg tmpkg;
	private TechMdttLN tmln;
	private String version;
	private String type;
	public TechMdttPkg getTmpkg() {
		return tmpkg;
	}

	public void setTmpkg(TechMdttPkg tmpkg) {
		this.tmpkg = tmpkg;
	}

	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TechMdttLN getTmln() {
		return tmln;
	}

	public void setTmln(TechMdttLN tmln) {
		this.tmln = tmln;
	}
	 
}
