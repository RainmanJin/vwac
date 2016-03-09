package com.ecannetwork.core.tld.domain;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ecannetwork.core.util.JoinHelper;
import com.ecannetwork.dto.core.EcanDomainDTO;
import com.ecannetwork.dto.core.EcanDomainvalueDTO;

public class ViewDomainTLD extends IDomainTag
{
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(ViewDomainTLD.class);

	private String domain;
	private String value;
	private String separator;

	public String formartResult()
	{
		String exvalue = String.valueOf(getExValue(this.value));

		if (!StringUtils.isBlank(exvalue))
		{
			EcanDomainDTO domainDto = this.getDomain(this.domain);

			if (exvalue.indexOf("|") != -1)
			{
				String[] ds = exvalue.split("\\|");
				String[] results = new String[ds.length];

				for (int i = 0; i < ds.length; i++)
				{
					EcanDomainvalueDTO dto = (EcanDomainvalueDTO) domainDto
							.getByValue(ds[i]);
					if (dto != null)
					{
						results[i] = dto.getI18nLabel();
					}
				}

				return JoinHelper.join(results, StringUtils
						.isBlank(this.separator) ? "," : this.separator);
			}

			EcanDomainvalueDTO dto = domainDto.getByValue(exvalue);
			if (dto != null)
			{
				return dto.getI18nLabel();
			}
		}

		return "";
	}

	public String getSeparator()
	{
		return this.separator;
	}

	public void setSeparator(String separator)
	{
		this.separator = separator;
	}

	public String getDomain()
	{
		return this.domain;
	}

	public void setDomain(String domain)
	{
		this.domain = domain;
	}

	public String getValue()
	{
		return this.value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}