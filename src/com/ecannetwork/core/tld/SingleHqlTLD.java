package com.ecannetwork.core.tld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.tld.facade.CachedDtoViewFacade;

/**
 * 仅适用于主键适用字符串并名称为id的对象
 * 
 * @author leo
 * 
 */
public class SingleHqlTLD extends IBaseTag
{
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(SingleHqlTLD.class);

	private CommonService commonService;

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
		if (commonService == null)
		{
			commonService = (CommonService) this.getBean("commonService");
		}
		try
		{
			List<Object> args = new ArrayList<Object>(5);
			if (arg0 != null)
				args.add(arg0);
			if (arg1 != null)
				args.add(arg1);
			if (arg2 != null)
				args.add(arg2);
			if (arg3 != null)
				args.add(arg3);
			if (arg4 != null)
				args.add(arg4);

			List<Object> list = this.commonService.list(hql, args.toArray());
			if(list.size()>0)
			{
				return list.get(0);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return "";
	}

	private String hql;
	private Object arg0;
	private Object arg1;
	private Object arg2;
	private Object arg3;
	private Object arg4;

	public String getHql()
	{
		return hql;
	}

	public void setHql(String hql)
	{
		this.hql = hql;
	}

	public Object getArg0()
	{
		return arg0;
	}

	public void setArg0(Object arg0)
	{
		this.arg0 = arg0;
	}

	public Object getArg1()
	{
		return arg1;
	}

	public void setArg1(Object arg1)
	{
		this.arg1 = arg1;
	}

	public Object getArg2()
	{
		return arg2;
	}

	public void setArg2(Object arg2)
	{
		this.arg2 = arg2;
	}

	public Object getArg3()
	{
		return arg3;
	}

	public void setArg3(Object arg3)
	{
		this.arg3 = arg3;
	}

	public Object getArg4()
	{
		return arg4;
	}

	public void setArg4(Object arg4)
	{
		this.arg4 = arg4;
	}
}
