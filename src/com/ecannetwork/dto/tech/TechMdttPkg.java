/**
 * 
 */
package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * @author leo
 * 
 */
public class TechMdttPkg extends AbstractTechMdttPkg 
{
	private static final long serialVersionUID = 1L;

	/**
	 * 改造资料库修改关联人员权限
	 * 2015-08-19修改
	 * ******************************
	 */
	private List<TechMdttPkgUsers> tmus= new ArrayList<TechMdttPkgUsers>();
	private Set<String> pusers = new HashSet<String>();
	private Set<String> motorcycles= new HashSet<String>();
	
	public void setMotorcycles(String motorcycle)
	{
		//super.setCourseware(courseware);
		//super.setUsername(username);
		super.setMotorcycle(motorcycle);

		if (StringUtils.isNotBlank(motorcycle))
		{
			String mcs[] = motorcycle.split(",");
			for (String mc : mcs)
			{
				motorcycles.add(mc);
			}
		}
	}
	

	public Set<String> getMotorcycles() {
		return motorcycles;
	}


	public void setMotorcycles(Set<String> motorcycles) {
		this.motorcycles = motorcycles;
	}


	public List<TechMdttPkgUsers> getTmus() {
		return tmus;
	}

	public void setTmus(List<TechMdttPkgUsers> tmus) {
		this.tmus = tmus;
	}
	
	public Set<String> getPusers() {
		return pusers;
	}

	public void setPusers(Set<String> pusers) {
		this.pusers = pusers;
	}
	
	public void setPusers(List<TechMdttPkgUsers> tmus) {
	       this.tmus = tmus;
			
			for (TechMdttPkgUsers eum : tmus)
			{
				this.pusers.add(eum.getUserId());
			}
		}
	/****************************/
	public static final class CONTENT_TYPE
	{
		public static final String CLASS_ROOM = "CP";
		public static final String SSP = "SSP";
	}
}
