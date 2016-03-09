package com.ecannetwork.core.tld;

import org.apache.commons.lang.StringUtils;

public abstract class IHTMLControlTLD extends IBaseTag
{
	private static final long serialVersionUID = 1L;
	private String name;
	private String styleClass;
	private String uid;
	private String disabled;
	private String multiple;
	private String onblur;
	private String onchange;
	private String onclick;
	private String onfocus;
	private String ondblclick;
	private String onmousedown;
	private String onmousemove;
	private String onmouseout;
	private String onmouseover;
	private String onmouseup;
	private String onhelp;
	private String size;
	private String style;
	private String onkeydown;
	private String onkeypress;
	private String onkeyup;
	private String title;

	public String getHtmlAttributes()
	{
		StringBuffer sb = new StringBuffer();

		if (!StringUtils.isBlank(this.name))
		{
			sb.append(" name=\"" + this.name + "\"");
		}
		if (!StringUtils.isBlank(this.styleClass))
		{
			sb.append(" class=\"" + this.styleClass + "\"");
		}
		if (!StringUtils.isBlank(this.uid))
		{
			sb.append(" id=\"" + this.uid + "\"");
		}
		if (!StringUtils.isBlank(this.disabled))
		{
			sb.append(" disabled=\"" + this.disabled + "\"");
		}
		if (!StringUtils.isBlank(this.multiple))
		{
			sb.append(" multiple=\"" + this.multiple + "\"");
		}
		if (!StringUtils.isBlank(this.onblur))
		{
			sb.append(" onblur=\"" + this.onblur + "\"");
		}
		if (!StringUtils.isBlank(this.onchange))
		{
			sb.append(" onchange=\"" + this.onchange + "\"");
		}
		if (!StringUtils.isBlank(this.onclick))
		{
			sb.append(" onclick=\"" + this.onclick + "\"");
		}
		if (!StringUtils.isBlank(this.onfocus))
		{
			sb.append(" onfocus=\"" + this.onfocus + "\"");
		}
		if (!StringUtils.isBlank(this.ondblclick))
		{
			sb.append(" ondblclick=\"" + this.ondblclick + "\"");
		}
		if (!StringUtils.isBlank(this.onmousedown))
		{
			sb.append(" onmousedown=\"" + this.onmousedown + "\"");
		}
		if (!StringUtils.isBlank(this.onmousemove))
		{
			sb.append(" onmousemove=\"" + this.onmousemove + "\"");
		}
		if (!StringUtils.isBlank(this.onmouseout))
		{
			sb.append(" onmouseout=\"" + this.onmouseout + "\"");
		}
		if (!StringUtils.isBlank(this.onmouseover))
		{
			sb.append(" onmouseover=\"" + this.onmouseover + "\"");
		}
		if (!StringUtils.isBlank(this.onmouseup))
		{
			sb.append(" onmouseup=\"" + this.onmouseup + "\"");
		}
		if (!StringUtils.isBlank(this.onhelp))
		{
			sb.append(" onhelp=\"" + this.onhelp + "\"");
		}
		if (!StringUtils.isBlank(this.size))
		{
			sb.append(" size=\"" + this.size + "\"");
		}
		if (!StringUtils.isBlank(this.style))
		{
			sb.append(" style=\"" + this.style + "\"");
		}
		if (!StringUtils.isBlank(this.onkeydown))
		{
			sb.append(" onkeydown=\"" + this.onkeydown + "\"");
		}
		if (!StringUtils.isBlank(this.onkeypress))
		{
			sb.append(" onkeypress=\"" + this.onkeypress + "\"");
		}
		if (!StringUtils.isBlank(this.onkeyup))
		{
			sb.append(" onkeyup=\"" + this.onkeyup + "\"");
		}
		if (!StringUtils.isBlank(this.title))
		{
			sb.append(" title=\"" + this.title + "\"");
		}

		return sb.toString();
	}

	public static void main(String[] args)
	{
		System.out.println(new StringBuffer().toString());
	}

	public String getStyleClass()
	{
		return this.styleClass;
	}

	public void setStyleClass(String styleClass)
	{
		this.styleClass = styleClass;
	}

	public String getUid()
	{
		return this.uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getDisabled()
	{
		return this.disabled;
	}

	public void setDisabled(String disabled)
	{
		this.disabled = disabled;
	}

	public String getMultiple()
	{
		return this.multiple;
	}

	public void setMultiple(String multiple)
	{
		this.multiple = multiple;
	}

	public String getOnblur()
	{
		return this.onblur;
	}

	public void setOnblur(String onblur)
	{
		this.onblur = onblur;
	}

	public String getOnchange()
	{
		return this.onchange;
	}

	public void setOnchange(String onchange)
	{
		this.onchange = onchange;
	}

	public String getOnclick()
	{
		return this.onclick;
	}

	public void setOnclick(String onclick)
	{
		this.onclick = onclick;
	}

	public String getOnfocus()
	{
		return this.onfocus;
	}

	public void setOnfocus(String onfocus)
	{
		this.onfocus = onfocus;
	}

	public String getOndblclick()
	{
		return this.ondblclick;
	}

	public void setOndblclick(String ondblclick)
	{
		this.ondblclick = ondblclick;
	}

	public String getOnmousedown()
	{
		return this.onmousedown;
	}

	public void setOnmousedown(String onmousedown)
	{
		this.onmousedown = onmousedown;
	}

	public String getOnmousemove()
	{
		return this.onmousemove;
	}

	public void setOnmousemove(String onmousemove)
	{
		this.onmousemove = onmousemove;
	}

	public String getOnmouseout()
	{
		return this.onmouseout;
	}

	public void setOnmouseout(String onmouseout)
	{
		this.onmouseout = onmouseout;
	}

	public String getOnmouseover()
	{
		return this.onmouseover;
	}

	public void setOnmouseover(String onmouseover)
	{
		this.onmouseover = onmouseover;
	}

	public String getOnmouseup()
	{
		return this.onmouseup;
	}

	public void setOnmouseup(String onmouseup)
	{
		this.onmouseup = onmouseup;
	}

	public String getOnhelp()
	{
		return this.onhelp;
	}

	public void setOnhelp(String onhelp)
	{
		this.onhelp = onhelp;
	}

	public String getSize()
	{
		return this.size;
	}

	public void setSize(String size)
	{
		this.size = size;
	}

	public String getStyle()
	{
		return this.style;
	}

	public void setStyle(String style)
	{
		this.style = style;
	}

	public String getOnkeydown()
	{
		return this.onkeydown;
	}

	public void setOnkeydown(String onkeydown)
	{
		this.onkeydown = onkeydown;
	}

	public String getOnkeypress()
	{
		return this.onkeypress;
	}

	public void setOnkeypress(String onkeypress)
	{
		this.onkeypress = onkeypress;
	}

	public String getOnkeyup()
	{
		return this.onkeyup;
	}

	public void setOnkeyup(String onkeyup)
	{
		this.onkeyup = onkeyup;
	}

	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}