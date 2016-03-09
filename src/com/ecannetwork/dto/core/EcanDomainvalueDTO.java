package com.ecannetwork.dto.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.extremecomponents.table.cell.FilterOption;

import com.ecannetwork.core.util.CoreConsts;

public class EcanDomainvalueDTO extends AbstractEcanDomainvalueDTO implements
		FilterOption
{
	private static final long serialVersionUID = 1L;
	public static final int LEVEL_LENGTH = 4;

	/**
	 * 级别
	 * 
	 * @return
	 */
	public Integer getLevel()
	{
		return this.getDomainLevel().length() / LEVEL_LENGTH;
	}

	/**
	 * 父级别编号
	 * 
	 * @return
	 */
	public String getParentLevelCode()
	{
		return this.getDomainLevel().substring(0,
				this.getDomainLevel().length() - LEVEL_LENGTH);
	}

	/**
	 * 获取国际化的标签
	 * 
	 * @return
	 */
	public String getI18nLabel()
	{
		// return I18N.parse(this.getDomainlabel());
		return this.getDomainlabel();
	}

	public EcanDomainvalueDTO()
	{
	}

	public EcanDomainvalueDTO(String id)
	{
		super(id);
	}

	public boolean equals(Object other)
	{
		if (!(other instanceof EcanDomainvalueDTO))
			return false;
		EcanDomainvalueDTO castOther = (EcanDomainvalueDTO) other;
		return new EqualsBuilder().append(getId(), castOther.getId())
				.isEquals();
	}

	public int hashCode()
	{
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public Object getLabel()
	{
		return this.getI18nLabel();
	}

	public Object getValue()
	{
		return getDomainvalue();
	}

	/**
	 * 是否删除
	 * 
	 * @return
	 */
	public boolean isDeleted()
	{
		return StringUtils.equals(this.getIsDelete(), CoreConsts.YORN.YES);
	}
}