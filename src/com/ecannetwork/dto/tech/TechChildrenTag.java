package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李伟
 * 2015-8-10下午9:40:47
 */
public class TechChildrenTag extends AbstractTechChildrenTag{

	/**
	 * 
	 */
	private static final long serialVersionUID = -480992254461574449L;

	private TechContentTag mainTag;
	
	List<TechContentTag> mlist= new ArrayList<TechContentTag>();

	public TechContentTag getMainTag() {
		return mainTag;
	}

	public void setMainTag(TechContentTag mainTag) {
		this.mainTag = mainTag;
	}

	public List<TechContentTag> getMlist() {
		return mlist;
	}

	public void setMlist(List<TechContentTag> mlist) {
		this.mlist = mlist;
	}
	
	
	
}
