package com.ecannetwork.core.tld.domain;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.ecannetwork.core.app.domain.DomainFacade;
import com.ecannetwork.core.tld.IHTMLControlTLD;
import com.ecannetwork.dto.core.EcanDomainDTO;

public abstract class IDomainTag extends IHTMLControlTLD
{
	private static final long serialVersionUID = 1L;

	private String domain;
	private String value;

	public int doEndTag() throws JspException
	{
		String result = formartResult();
		if (result != null)
		{
			JspWriter out = this.pageContext.getOut();
			try
			{
				out.print(result);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	
	/**
	 * 从缓存中获取到所有的域信息
	 * 
	 * @return
	 */
	public Map<String, EcanDomainDTO> getDomains()
	{
		DomainFacade ds = (DomainFacade) this.getBean("domainFacade");
		return ds.getDomainMap();
	}

	/**
	 * 获取域信息
	 * 
	 * @param domain
	 * @return
	 */
	public EcanDomainDTO getDomain(String domain)
	{
		return this.getDomains().get(domain);
	}

	// public List filterList(String start, List source, int levelSplit)
	// {
	// List list = new ArrayList();
	//
	// if (!StringUtils.isBlank(start))
	// {
	// int length = start.length() + levelSplit;
	//
	// for (Iterator it = source.iterator(); it.hasNext();)
	// {
	// TEcanDomainvalueDTO dv = (TEcanDomainvalueDTO) it.next();
	// String domainLevel = dv.getDomainLevel();
	// if ((domainLevel == null) || (domainLevel.length() != length)
	// || (!domainLevel.startsWith(start)))
	// continue;
	// list.add(dv);
	// }
	// } else
	// {
	// for (Iterator it = source.iterator(); it.hasNext();)
	// {
	// TEcanDomainvalueDTO dv = (TEcanDomainvalueDTO) it.next();
	// String domainLevel = dv.getDomainLevel();
	// if ((domainLevel == null)
	// || (domainLevel.length() != levelSplit))
	// continue;
	// list.add(dv);
	// }
	//
	// }
	//
	// return list;
	// }
	//
	// public Iterator filterIterator(String start, int levelSplit)
	// {
	// Map dm = (Map) this.pageContext.getServletContext().getAttribute(
	// "BMSDOMAIN");
	// List a = (List) dm.get(this.domain);
	// if (a == null)
	// {
	// throw new RuntimeException("����ϵͳ�в��������Ϊ:" + this.domain
	// + "����.");
	// }
	//
	// return filterList(start, a, levelSplit).iterator();
	// }

	public abstract String formartResult();

	public String getValue()
	{
		return this.value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getDomain()
	{
		return this.domain;
	}

	public void setDomain(String domain)
	{
		this.domain = domain;
	}
}