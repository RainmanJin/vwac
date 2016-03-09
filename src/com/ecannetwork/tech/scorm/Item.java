package com.ecannetwork.tech.scorm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

public class Item
{
	private String title;
	private String identifier;
	private String identifierref;
	private String isvisible;
	private Resource resource;
	private List<Item> items;

	public Item(Element e, Map<String, Resource> resouceMap)
	{
		this.title = e.elementTextTrim("title");
		this.identifier = e.attributeValue("identifier");
		this.identifierref = e.attributeValue("identifierref");
		this.isvisible = e.attributeValue("isvisible");
		this.resource = resouceMap.get(this.identifierref);

		List its = e.elements("item");
		if (its != null && its.size() > 0)
		{
			items = new ArrayList<Item>();
			for (int i = 0; i < its.size(); i++)
			{
				Element ee = (Element) its.get(i);
				Item item = new Item(ee, resouceMap);
				items.add(item);
			}
		}
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getIdentifier()
	{
		return identifier;
	}

	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}

	public String getIdentifierref()
	{
		return identifierref;
	}

	public void setIdentifierref(String identifierref)
	{
		this.identifierref = identifierref;
	}

	public String getIsvisible()
	{
		return isvisible;
	}

	public void setIsvisible(String isvisible)
	{
		this.isvisible = isvisible;
	}

	public Resource getResource()
	{
		return resource;
	}

	public void setResource(Resource resource)
	{
		this.resource = resource;
	}

	public List<Item> getItems()
	{
		return items;
	}

	public void setItems(List<Item> items)
	{
		this.items = items;
	}

	@Override
	public String toString()
	{
		return "Item [title=" + title + ", identifier=" + identifier
				+ ", identifierref=" + identifierref + ", isvisible="
				+ isvisible + ", resource=" + resource + ", items=" + items
				+ "]";
	}

}
