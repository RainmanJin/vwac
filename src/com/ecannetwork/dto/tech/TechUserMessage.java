/**  
 * 文件名：TechUserMessage.java  
 *   
 * 日期：2015年7月3日  
 *  
 */

package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;


/**
 * 
 * @author: 李超
 *
 * @version: 2015年7月3日 上午10:56:11
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class TechUserMessage extends AbstractTechUserMessage implements
		java.io.Serializable {

	private static final long serialVersionUID = 1430621493635738759L;
	
	private String description;
	
	
	private List<EcanUserMessage> euser= new ArrayList<EcanUserMessage>();
	private Set<String> eusers = new HashSet<String>();
	//private List<EcanUserMessage> euser = (List<EcanUserMessage>) new TechUserMessage();

	
	public TechUserMessage() {
		super();
	}


	public List<EcanUserMessage> getEuser() {
		return euser;
	}

	public void setEuser(List<EcanUserMessage> euser) {
		this.euser = euser;
	}

	public Set<String> getEusers() {
		return eusers;
	}

	public void setEusers(Set<String> eusers) {
		this.eusers = eusers;
	}
	public void setEusers(List<EcanUserMessage> euser) {
	       this.euser = euser;
			
			for (EcanUserMessage eum : euser)
			{
				this.eusers.add(eum.getUserid());
			}
		}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	

}
