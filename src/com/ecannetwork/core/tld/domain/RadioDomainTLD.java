package com.ecannetwork.core.tld.domain;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ecannetwork.dto.core.EcanDomainvalueDTO;

public class RadioDomainTLD extends IDomainTag
{
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(RadioDomainTLD.class);
	private String separator;

	public String formartResult()
	{
		String exvalue = (String) getExValue(getValue());

		this.separator = (StringUtils.isBlank(this.separator) ? "&nbsp;&nbsp;&nbsp;"
				: this.separator);

		StringBuffer sb = new StringBuffer();

		Iterator it = this.getDomain(this.getDomain()).getValues().iterator();

		while (it.hasNext())
		{
			EcanDomainvalueDTO dto = (EcanDomainvalueDTO) it.next();
			if(dto.isDeleted())
			{
				continue;
			}
			
			sb.append("<input type=\"radio\"");

			if ((exvalue != null) && (exvalue.equals(dto.getDomainvalue())))
			{
				sb.append(" checked=\"checked\" ");
			}

			sb.append(" value=\"" + dto.getDomainvalue() + "\" "
					+ getHtmlAttributes() + " />" + dto.getI18nLabel()
					+ this.separator);
		}

		return sb.toString();
	}

	public String getSeparator()
	{
		return this.separator;
	}

	public void setSeparator(String separator)
	{
		this.separator = separator;
	}
}