package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.List;

/**
 * SellingPointConent entity. @author MyEclipse Persistence Tools
 */
public class SellingPointConent extends AbstractSellingPointConent implements
		java.io.Serializable {

	// Constructors
private SellingPointMain mainPoint;
	
	List<SellingPointMain> mplist= new ArrayList<SellingPointMain>();
	


	public SellingPointMain getMainPoint() {
		return mainPoint;
	}

	public void setMainPoint(SellingPointMain mainPoint) {
		this.mainPoint = mainPoint;
	}

	public List<SellingPointMain> getMplist() {
		return mplist;
	}

	public void setMplist(List<SellingPointMain> mplist) {
		this.mplist = mplist;
	}

	/** default constructor */
	public SellingPointConent() {
	}

	/** full constructor */
	public SellingPointConent(String title, String content, String contentTag,
			String createTime,Long contentSize) {
		super(title, content, contentTag, createTime,contentSize);
	}

}
