package com.ecannetwork.core.i18n;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.RootClass;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.ecannetwork.core.module.db.dto.DtoSupport;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.dto.core.EcanDomainvalueDTO;
import com.ecannetwork.dto.core.EcanI18NPropertiesDTO;

public class I18NHibernateInterceptor extends EmptyInterceptor
{
	private static final long serialVersionUID = 1L;

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
	{
		return super.onSave(entity, id, state, propertyNames, types);
	}

	@Override
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
	{

		super.onDelete(entity, id, state, propertyNames, types);
	}

	@Autowired
	private LocalSessionFactoryBean sessionFacotry;

	@Override
	public void preFlush(Iterator entities)
	{
		init();
		while (entities.hasNext())
		{
			DtoSupport o = (DtoSupport) entities.next();
			if (o instanceof EcanDomainvalueDTO)
			{
				continue;
			}
			String className = o.getClass().getName();

			List<String> propertiesList = this.i18nEntryPropertiesMap.get(className);
			if (propertiesList != null)
			{// 如果该实体类有需要做I18N的属性字段
				String id = o.getId();

				String[] oldValues = null;
				if (StringUtils.isNotBlank(id))
				{// id不为空，则需要查询出原有的i18n property的属性
					oldValues = I18nFacade.getInstance().queryI18nPropertyID(i18nQueryPropertiesFromTableSQL.get(className), id, propertiesList.size());
				}
				else
				{// id 为空，表示是新插入的数据， 则认为老记录的值为null即可
					oldValues = new String[propertiesList.size()];
				}

				for (int i = 0; i < oldValues.length; i++)
				{// 循环每个oldValue

					String propertyName = propertiesList.get(i);
					String oldValue = oldValues[i];
					String newValue = getProperty(o, propertyName);

					if (StringUtils.isBlank(newValue))
					{// 新的值等于空
						if (StringUtils.isNotBlank(oldValue))
						{// 旧值不为空
							if (oldValue.startsWith(I18nTypeString.I18N_PREFIX))
							{// 旧值做个i18n，删除i18nProperites记录
								I18nFacade.getInstance().deleteI18nProperty(oldValue.substring(I18nTypeString.I18N_PREFIX_LENGTH));
							}// else{Do nth}
						}// else{老的值也是空}
					}
					else
					{
						// 新的值做了i18n

						if (StringUtils.isBlank(oldValue))
						{// 没有旧值

							EcanI18NPropertiesDTO p = new EcanI18NPropertiesDTO();
							p.setLangType(ExecuteContext.getCurrentLang());
							p.setPropertyId(UUID.randomUUID());
							p.setTextValue(newValue);
							p.setAppId(i18nEntryPropertiyesComments.get(className).get(propertyName));
							I18nFacade.getInstance().saveProperty(p);
							// 替换当前的值为_T_:propertiesID

							setProperty(o, propertyName, I18nTypeString.I18N_PREFIX + p.getPropertyId());

						}
						else
						{
							if (oldValue.startsWith(I18nTypeString.I18N_PREFIX))
							{// 旧值做了i18n,更新properties表，设置新的value为旧值
								String i18nPropertyID = oldValue.substring(I18nTypeString.I18N_PREFIX_LENGTH);

								// String i18nPropertyValue = I18NCache
								// .getInstance().getFixLang(
								// ExecuteContext
								// .getCurrentLang(),
								// i18nPropertyID);
								// if
								// (!StringUtils.equals(i18nPropertyValue,
								// newValue))
								// {// 旧的i18n值和新的i18n值相等，则不用更新i18n配置表
								I18nFacade.getInstance().updatePropertyAsync(ExecuteContext.getCurrentLang(), i18nPropertyID, newValue, i18nEntryPropertiyesComments.get(className).get(propertyName));
								// }

								setProperty(o, propertyName, oldValue);

							}
							else
							{// 旧值无i18n
								EcanI18NPropertiesDTO p = new EcanI18NPropertiesDTO();
								p.setLangType(ExecuteContext.getCurrentLang());
								p.setPropertyId(UUID.randomUUID());
								p.setTextValue(newValue);

								p.setAppId(i18nEntryPropertiyesComments.get(className).get(propertyName));

								I18nFacade.getInstance().saveProperty(p);
								// 替换当前的值为_T_:propertiesID

								setProperty(o, propertyName, I18nTypeString.I18N_PREFIX + p.getPropertyId());
							}
						}
					}
				}
			}
		}

		super.preFlush(entities);
	}

	private void setProperty(DtoSupport o, String propertyName, String oldValue)
	{
		try
		{
			BeanUtils.setProperty(o, propertyName, oldValue);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private String getProperty(DtoSupport o, String propertyName)
	{
		String newValue = null;
		try
		{
			newValue = BeanUtils.getProperty(o, propertyName);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		return newValue;
	}

	@SuppressWarnings("unchecked")
	public void init()
	{
		if (i18nEntryPropertiesMap.size() == 0)
		{// 如果没有初始化过：）
			Iterator<RootClass> it = sessionFacotry.getConfiguration().getClassMappings();

			RootClass entityClass = null;
			while (it.hasNext())
			{// 循环每个实体类

				entityClass = (RootClass) it.next();
				List<String> propertiesList = new ArrayList<String>();
				List<String> columnList = new ArrayList<String>();

				Iterator it2 = entityClass.getPropertyIterator();
				while (it2.hasNext())
				{// 循环每个属性
					Property p = (Property) it2.next();

					if (p.getType().getName().equals(I18nTypeString.class.getName()))
					{// 如果是自定义的i18n类型的字段
						propertiesList.add(p.getName());
						Column col = (Column) p.getColumnIterator().next();
						columnList.add(col.getName());

						// 保存注释信息
						Map<String, String> propertyCommentMap = i18nEntryPropertiyesComments.get(entityClass.getClassName());
						if (propertyCommentMap == null)
						{
							propertyCommentMap = new HashMap<String, String>();
							i18nEntryPropertiyesComments.put(entityClass.getClassName(), propertyCommentMap);
						}
						propertyCommentMap.put(p.getName(), col.getComment());
					}
				}

				if (propertiesList.size() > 0)
				{
					i18nEntryPropertiesMap.put(entityClass.getClassName(), propertiesList);

					StringBuilder sql = new StringBuilder();
					sql.append("select ");
					for (int i = 0; i < columnList.size(); i++)
					{
						if (i != columnList.size() - 1)
						{
							sql.append(columnList.get(i)).append(" ,");
						}
						else
						{
							sql.append(columnList.get(i)).append(" ");
						}
					}
					sql.append("from ").append(entityClass.getTable().getName()).append(" where ID=?");

					i18nQueryPropertiesFromTableSQL.put(entityClass.getClassName(), sql.toString());
				}
			}
		}
	}

	// 属性注释信息<DtoClassName, Map<PropertyName, Comments>>
	private Map<String, Map<String, String>> i18nEntryPropertiyesComments = new HashMap<String, Map<String, String>>();

	// 执行i18n属性查询的原生sql<DtoClassName, select p1,p2 from TableName ...>
	private Map<String, String> i18nQueryPropertiesFromTableSQL = new HashMap<String, String>();

	// 需要做i18n的属性配置文件列表：<DtoClassName, List<PropertyName>
	private Map<String, List<String>> i18nEntryPropertiesMap = new HashMap<String, List<String>>();
}
