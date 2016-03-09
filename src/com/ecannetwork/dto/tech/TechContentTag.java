package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 李伟
 * 2015-8-10下午4:13:43
 */
public class TechContentTag extends AbstractTechContentTag{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 696250865547416366L;
	
	//private TechChildrenTag childrenTag;
	private List<TechChildrenTag> tcdt= new ArrayList<TechChildrenTag>();
	private Set<String> tcts = new HashSet<String>();
	public List<TechChildrenTag> getTct() {
		return tcdt;
	}
	public void setTcdt(List<TechChildrenTag> tcdt) {
		this.tcdt = tcdt;
	}
	public Set<String> getTcts() {
		return tcts;
	}
	public void setTcts(Set<String> tcts) {
		this.tcts = tcts;
	}
	public void setTcts(List<TechChildrenTag> tcdt) {
	       this.tcdt = tcdt;
			
			for (TechChildrenTag t : tcdt)
			{
				this.tcts.add(t.getId());
			}
		}
/*	public TechChildrenTag getChildrenTag() {
		return childrenTag;
	}
	public void setChildrenTag(TechChildrenTag childrenTag) {
		this.childrenTag = childrenTag;
	}*/
	
	
}
