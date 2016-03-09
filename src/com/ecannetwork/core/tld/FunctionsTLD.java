package com.ecannetwork.core.tld;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.ecannetwork.core.tld.facade.CachedDtoViewFacade;
import com.ecannetwork.core.util.BeanContextUtil;

@Component
public class FunctionsTLD
{
	public static Object viewDTO(String dtoName, String id, String propertyName)
	{
		if (StringUtils.isBlank(dtoName))
		{
			throw new RuntimeException("dtoName must not empty");
		}

		if (StringUtils.isBlank(id))
		{
			throw new RuntimeException("id must not empty");
		}

		if (StringUtils.isBlank(propertyName))
		{
			throw new RuntimeException("propertyName must not empty");
		}

		try
		{
			init();
			Object o = cachedDtoViewFacade.get(id, dtoName);
			if (o != null)
			{

				return PropertyUtils.getProperty(o, propertyName);

			} else
			{
				return null;
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private static CachedDtoViewFacade cachedDtoViewFacade;

	public static void init()
	{
		if (cachedDtoViewFacade == null)
		{
			cachedDtoViewFacade = (CachedDtoViewFacade) BeanContextUtil.applicationContext
					.getBean("cachedDtoViewFacade");
		}
	}
}
