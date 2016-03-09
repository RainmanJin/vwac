package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author 李伟
 * 2015-7-17上午10:16:36
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class TechMdttLN extends AbstractTechMdttLN{
	
	private String isExist;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5809794040114707873L;
	
	private Set<String> coursewareIds = new HashSet<String>();
	private List<TechMdttLNPkg> lnpkg= new ArrayList<TechMdttLNPkg>();
	//private List<TechMdttLNUser> lnuser = new ArrayList<TechMdttLNUser>();
	private Set<String> lnpkgs = new HashSet<String>();
	private Set<String> usernames = new HashSet<String>();
	
	/**
	 * 重写该方法， 将观察人分割成Set
	 *//*public void setCourseware(String courseware) {
			this.courseware = courseware;
		}*/
	
	public void setCourseware(String courseware)
	{
		super.setCourseware(courseware);

		if (StringUtils.isNotBlank(courseware))
		{
			String ids[] = courseware.split(",");
			for (String id : ids)
			{
				coursewareIds.add(id);
			}
		}
	}
	public void setUsernames(String username)
	{
		//super.setCourseware(courseware);
		super.setUsername(username);

		if (StringUtils.isNotBlank(username))
		{
			String names[] = username.split(",");
			for (String name : names)
			{
				usernames.add(name);
			}
		}
	}
	public Set<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(Set<String> usernames) {
		this.usernames = usernames;
	}

	public Set<String> getCoursewareIds() {
		return coursewareIds;
	}

	public void setCoursewareIds(Set<String> coursewareIds) {
		this.coursewareIds = coursewareIds;
	}
	
	


	public Set<String> getLnpkgs() {
		return lnpkgs;
	}

	public void setLnpkgs(Set<String> lnpkgs) {
		this.lnpkgs = lnpkgs;
	}

	public void setLnpkgs(List<TechMdttLNPkg> lnpkg) {
       this.lnpkg = lnpkg;
		
		for (TechMdttLNPkg pkg : lnpkg)
		{
			this.lnpkgs.add(pkg.getPkgid());
		}
	}

	public List<TechMdttLNPkg> getLnpkg() {
		return lnpkg;
	}

	public void setLnpkg(List<TechMdttLNPkg> lnpkg) {
		this.lnpkg = lnpkg;
	}
	public String getIsExist() {
		return isExist;
	}
	public void setIsExist(String isExist) {
		this.isExist = isExist;
	}

	/*public List<TechMdttLNUser> getLnuser() {
		return lnuser;
	}

	public void setLnuser(List<TechMdttLNUser> lnuser) {
		this.lnuser = lnuser;
	}*/
	
}
