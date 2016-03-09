package com.ecannetwork.tech.scorm;

import org.dom4j.Element;

public class Resource
{
	private String identifier;
	private String type;
	private String href;
	private String scormtype;

	public Resource(Element r)
	{
		this.identifier = r.attributeValue("identifier");
		this.type = r.attributeValue("type");
		this.href = r.attributeValue("href");
		this.scormtype = r.attributeValue("scormtype");
	}

	public String getIdentifier()
	{
		return identifier;
	}

	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getHref()
	{
		return href;
	}

	public void setHref(String href)
	{
		this.href = href;
	}

	public String getScormtype()
	{
		return scormtype;
	}

	public void setScormtype(String scormtype)
	{
		this.scormtype = scormtype;
	}

	@Override
	public String toString()
	{
		return "Resource [identifier=" + identifier + ", type=" + type
				+ ", href=" + href + ", scormtype=" + scormtype + "]";
	}

}
