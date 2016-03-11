package com.ecannetwork.dto.tech;

import java.util.List;

import com.ecannetwork.dto.core.EcanI18NPropertiesDTO;

public class Lang implements java.io.Serializable {

	private String key;
	private List<EcanI18NPropertiesDTO> langValueEs;
	
	public Lang() {

	}
	
	public Lang(String key,List<EcanI18NPropertiesDTO> langValueEs) {
		this.key=key;
		this.langValueEs=langValueEs;
	}
	
	public String getKey(){
		return this.key;
	}
	
	public void setKey(String key){
		this.key=key;
	}
	
	public List<EcanI18NPropertiesDTO> getLangValueEs(){
		return this.langValueEs;
	}
	
	public void setLangValueEs(List<EcanI18NPropertiesDTO> langValueEs){
		this.langValueEs=langValueEs;
	}
}
