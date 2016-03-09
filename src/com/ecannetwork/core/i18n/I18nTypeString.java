package com.ecannetwork.core.i18n;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.ecannetwork.core.util.ExecuteContext;

public class I18nTypeString implements UserType, Serializable
{
	private static final long serialVersionUID = -6859678535298540417L;

	public I18nTypeString()
	{
		super();
	}

	/**
	 * 该方法用于提供自定义类型的完全的复制的方法。它主要用于构造返回对象。
	 * 当nullSafeGet获取对象后，将会调用这个方法。进行复制一个完全相同的拷贝。 然后把这个拷贝返回给用户。
	 */
	public Object deepCopy(Object value) throws HibernateException
	{
		if (value == null)
			return null;
		return new String((String) value);
	}

	public Serializable disassemble(Object arg0) throws HibernateException
	{
		return null;
	}

	/**
	 * 这是自定义的数据的对比方法。如果返回的是false则hibernate认为数据发生了变化 将会把变化更新到库的表之中。
	 */
	public boolean equals(Object x, Object y) throws HibernateException
	{
		return (x == y) || (x != null && y != null && (x.equals(y)));
	}

	public int hashCode(Object arg0) throws HibernateException
	{
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * 用于设定我们的类是不是可变的。
	 */
	public boolean isMutable()
	{
		return false;
	}

	/**
	 * 从JDBC的ResultSet中获取到数据，然后返回为相应的类型。 其中names包含了要映射的字段的名称。
	 */
	public Object nullSafeGet(ResultSet rs, String[] names, Object ower)
			throws HibernateException, SQLException
	{
		String val = rs.getString(names[0]);
		if (null == val)
		{
			return null;
		} else
		{
			if (val.startsWith(I18N_PREFIX))
			{
				String propertyId = val.substring(I18N_PREFIX_LENGTH);

				return I18NCache.getInstance().get(
						ExecuteContext.getCurrentLang(), propertyId);
			}
			return val;
		}
	}

	public static String I18N_PREFIX = "_T_:";
	public static Integer I18N_PREFIX_LENGTH = 4;

	/**
	 * 这个方法将在数据保存时使用。本方法可以使用PreparedStatement将数据写入对应的数据库字段中。
	 * 其中的value表示的是要写入的值。index表示的是在statement的参数中的index.
	 */
	public void nullSafeSet(PreparedStatement st, Object value, int index)
			throws HibernateException, SQLException
	{
		if (value == null)
		{
			st.setNull(index, Types.VARCHAR);
		} else
		{
			String val = (String) value;
			// 值设置部分通过拦截器实现

			// if (val.startsWith(I18N_PREFIX))
			// {// 如果是需要使用i18n的属性
			// // 插入到i18n_properties表中
			// EcanI18NPropertiesDTO p = new EcanI18NPropertiesDTO();
			// p.setLangType(ExecuteContext.getCurrentLang());
			// p.setPropertyId(UUID.randomUUID());
			// p.setTextValue(val.substring(I18N_PREFIX_LENGTH));
			// // p.setAppId(ExecuteContext.menu().getAppCode());
			//
			// // 插入到数据库中
			// I18nFacade.getInstance().saveProperty(p);
			//
			// val = I18N_PREFIX + p.getPropertyId();
			// }

			st.setObject(index, val, Types.VARCHAR);
		}
	}

	public Object replace(Object arg0, Object arg1, Object arg2)
			throws HibernateException
	{
		LocalSessionFactoryBean t = null;
		return null;
	}

	/**
	 * 用于设定nullSafeGet所返回的数据的类型。即我们的自定义的数据类型。
	 */
	public Class returnedClass()
	{
		return String.class;
	}

	/**
	 * 该自定义对象类型所对应的SQL数据的类型。
	 */
	public int[] sqlTypes()
	{
		return new int[] { Types.VARCHAR };
	}

	public Object assemble(Serializable arg0, Object arg1)
			throws HibernateException
	{
		return null;
	}

}
