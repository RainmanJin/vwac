package com.ecannetwork.core.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ecannetwork.core.tld.facade.CachedDtoViewFacade;

/**
 * 仅适用于主键适用字符串并名称为id的对象
 * 
 * @author leo
 * 
 */
public class ViewDtoTLD extends IBaseTag
{
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(ViewDtoTLD.class);

	private CachedDtoViewFacade cachedDtoViewFacade;

	@Override
	public int doEndTag() throws JspException
	{
		Object result = formartResult();
		if (result != null)
		{
			JspWriter out = this.pageContext.getOut();
			try
			{
				out.print(result.toString());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return 0;
	}

	private Object formartResult()
	{
		if(StringUtils.isBlank(id))
		{
			return null;
		}
		
		if (cachedDtoViewFacade == null)
		{
			cachedDtoViewFacade = (CachedDtoViewFacade) this
					.getBean("cachedDtoViewFacade");
		}
		try
		{
			Object o = cachedDtoViewFacade.get(id, dtoName);
			if (o != null)
			{

				return PropertyUtils.getProperty(o, this.property);

			} else
			{
				log.warn("get object null:\t " + this.id + "\t" + this.dtoName
						+ "\t" + this.property);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return this.id;
	}

	/**
	 * dto类的名称，可以是类名，出现冲突时，应该使用类的全名
	 */
	private String dtoName;
	/**
	 * 主键编号
	 */
	private String id;

	/**
	 * 要查看的属性名称
	 */
	private String property;

	public String getDtoName()
	{
		return dtoName;
	}

	public void setDtoName(String dtoName)
	{
		this.dtoName = dtoName;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getProperty()
	{
		return property;
	}

	public void setProperty(String property)
	{
		this.property = property;
	}

}
