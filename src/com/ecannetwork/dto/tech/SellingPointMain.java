package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.List;

/**
 * SellingPointMain entity. @author MyEclipse Persistence Tools
 */
public class SellingPointMain extends AbstractSellingPointMain implements
		java.io.Serializable {

	// Constructors
	private List<SellingPointConent> spc= new ArrayList<SellingPointConent>();
	
	public List<SellingPointConent> getSpc() {
		return spc;
	}

	public void setSpc(List<SellingPointConent> spc) {
		this.spc = spc;
	}

	/** default constructor */
	public SellingPointMain() {
	}

	/** full constructor */
	public SellingPointMain(String title, String createTime, String status,
			String contentId, String pkgid) {
		super(title, createTime, status, contentId, pkgid);
	}

}
