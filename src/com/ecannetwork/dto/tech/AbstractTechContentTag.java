package com.ecannetwork.dto.tech;

import java.util.Date;
import java.util.List;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * @author 李伟
 * 2015-8-10下午4:14:20
 */
public class AbstractTechContentTag extends DtoSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1347942369184019132L;
	private String id;
	//private String tagCode;
	private String tagName;
	//private String uid;
	private Date lastUpdateTime;
	//private Set TechChildrenTag = new HashSet();
	private List<TechChildrenTag> items;
	
	
	public List<TechChildrenTag> getItems() {
		return items;
	}
	public void setItems(List<TechChildrenTag> items) {
		this.items = items;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/*public Set getTechChildrenTag() {
		return TechChildrenTag;
	}
	public void setTechChildrenTag(Set techChildrenTag) {
		TechChildrenTag = techChildrenTag;
	}*/
	
	
}
