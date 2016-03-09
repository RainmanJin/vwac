package com.ecannetwork.tech.scorm;

import java.util.ArrayList;
import java.util.List;

public class Organization
{
	private String title;
	private String identifier;

	private List<Item> items = new ArrayList<Item>();

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public List<Item> getItems()
	{
		return items;
	}

	public void setItems(List<Item> items)
	{
		this.items = items;
	}

	public String getIdentifier()
	{
		return identifier;
	}

	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}

	@Override
	public String toString()
	{
		return "Organization [title=" + title + ", identifier=" + identifier
				+ ", items=" + items + "]";
	}
}
