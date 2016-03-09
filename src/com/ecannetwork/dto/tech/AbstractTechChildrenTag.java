package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * @author 李伟
 * 2015-8-10下午9:42:50
 */
public class AbstractTechChildrenTag extends DtoSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3190116535944607253L;
	private String id;
	private String ctagName;
	private String mainId;
	//private TechContentTag techContentTag;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCtagName() {
		return ctagName;
	}
	public void setCtagName(String ctagName) {
		this.ctagName = ctagName;
	}
	public String getMainId() {
		return mainId;
	}
	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	
	
}
