package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.List;

public class TechMdttOption extends AbstractTechMdttOption
{
	private List<TechMdttAns> ansList = new ArrayList<TechMdttAns>();

	public List<TechMdttAns> getAnsList()
	{
		return ansList;
	}

	public void setAnsList(List<TechMdttAns> ansList)
	{
		this.ansList = ansList;
	}

}
