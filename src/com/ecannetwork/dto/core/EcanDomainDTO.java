package com.ecannetwork.dto.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.ecannetwork.core.i18n.I18N;

public class EcanDomainDTO extends AbstractEcanDomainDTO
{
	private static final long serialVersionUID = 1L;

	/**
	 * 所有的域对应的信息
	 */
	private List<EcanDomainvalueDTO> values = new ArrayList<EcanDomainvalueDTO>();

	private Map<String, EcanDomainvalueDTO> valuesMap = null;

	/**
	 * 获取某个域值对应的子域列表::: DomainLevel不能为空
	 * 
	 * @param value
	 * @return
	 */
	public List<EcanDomainvalueDTO> listChilds(String value)
	{
		EcanDomainvalueDTO v = this.getByValue(value);

		List<EcanDomainvalueDTO> list = new ArrayList<EcanDomainvalueDTO>();

		for (EcanDomainvalueDTO dv : values)
		{
			if (v.getLevel() + 1 == dv.getLevel()
					&& dv.getParentLevelCode().equals(v.getDomainLevel()))
			{
				list.add(dv);
			}
		}
		return list;
	}

	/**
	 * 根据值获取标签
	 * 
	 * @param value
	 * @return
	 */
	public EcanDomainvalueDTO getByValue(String value)
	{
		if (valuesMap == null)
		{
			valuesMap = new HashMap<String, EcanDomainvalueDTO>(values.size());
			for (EcanDomainvalueDTO dv : values)
			{
				valuesMap.put(dv.getDomainvalue(), dv);
			}
		}

		return valuesMap.get(value);
	}

	public List<EcanDomainvalueDTO> getValues()
	{
		return this.values;
	}

	private boolean sorted = false;

	/**
	 * 构建成树的结构
	 * 
	 * @return
	 */
	public List<EcanDomainvalueDTO> getValuesAsTree()
	{
		if (!sorted)
		{
			Collections.sort(this.values, new Comparator<EcanDomainvalueDTO>()
			{
				@Override
				public int compare(EcanDomainvalueDTO o1, EcanDomainvalueDTO o2)
				{
					return o1.getDomainLevel().compareTo(o2.getDomainLevel());
				}
			});
		}

		return this.values;
	}

	public void setValues(List<EcanDomainvalueDTO> values)
	{
		this.values = values;
	}

	public EcanDomainDTO()
	{
	}

	public boolean equals(Object other)
	{
		if (!(other instanceof EcanDomainDTO))
			return false;
		EcanDomainDTO castOther = (EcanDomainDTO) other;
		return new EqualsBuilder().append(getId(), castOther.getId())
				.isEquals();
	}

	public String getI18nRemark()
	{
		return I18N.parse(this.getRemark());
	}

	public int hashCode()
	{
		return new HashCodeBuilder().append(getId()).toHashCode();
	}
}