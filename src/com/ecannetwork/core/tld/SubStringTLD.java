package com.ecannetwork.core.tld;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SubStringTLD extends IBaseTag
{
	private static final long serialVersionUID = 1L;
	private String content;
	private Integer begin;
	private Integer end;
	private String fix;

	private static Log log = LogFactory.getLog(ViewDtoTLD.class);

	@Override
	public int doEndTag() throws JspException
	{
		JspWriter out = this.pageContext.getOut();

		try
		{
			if (StringUtils.isNotBlank(content))
			{

				if (this.begin == null)
				{
					this.begin = 0;
				}

				int _end = this.end;
				if (this.end == null || this.end == 0
						|| this.end > content.length())
				{
					_end = content.length();
				}

				out.print(content.substring(this.begin, _end));

				if (_end < content.length() && StringUtils.isNotBlank(this.fix))
				{
					out.print(this.fix);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	public Integer getEnd()
	{
		return end;
	}

	public void setEnd(Integer end)
	{
		this.end = end;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getFix()
	{
		return fix;
	}

	public void setFix(String fix)
	{
		this.fix = fix;
	}

	public Integer getBegin()
	{
		return begin;
	}

	public void setBegin(Integer begin)
	{
		this.begin = begin;
	}

	public static void main(String[] args)
	{
		System.out.println("hello".substring(3, 5));
	}
}