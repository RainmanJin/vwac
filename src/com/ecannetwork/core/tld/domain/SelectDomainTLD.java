package com.ecannetwork.core.tld.domain;

import java.util.Iterator;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ecannetwork.dto.core.EcanDomainvalueDTO;

public class SelectDomainTLD extends IDomainTag
{
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(SelectDomainTLD.class);

	public String formartResult()
	{
		String exvalue = (String) getExValue(getValue());

		boolean def = !StringUtils.isBlank(exvalue);
		String[] vs = (String[]) null;
		if (def)
		{
			vs = exvalue.split("\\|");
		}

		StringBuffer sb = new StringBuffer();

		sb.append("\n<select " + getHtmlAttributes() + ">");

		Iterator it = this.getDomain(this.getDomain()).getValues().iterator();

		while (it.hasNext())
		{
			EcanDomainvalueDTO dto = (EcanDomainvalueDTO) it.next();
			if(dto.isDeleted())
			{
				continue;
			}
			
			sb.append("<option value=\"" + dto.getDomainvalue() + "\" ");

			if ((def) && (ArrayUtils.contains(vs, dto.getDomainvalue())))
			{
				sb.append(" selected=\"selected\" ");
			}

			sb.append(">" + dto.getI18nLabel() + "</option>");
		}

		sb.append("</select>\n");

		return sb.toString();
	}
}