package com.ecannetwork.core.i18n;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.ecannetwork.core.app.domain.DomainFacade;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.core.EcanI18NPropertiesDTO;
import com.ecannetwork.dto.core.EcanI18nDTO;

/**
 * I18n服务
 * 
 * @author leo
 * 
 */
public class I18nFacade
{

	@Autowired
	private CommonService commonService;
	@Autowired
	private DomainFacade domainFacade;

	// 是否开启
	private boolean open;
	// 数据库支持
	private boolean db;
	// 默认语言
	private String defaultLang;

	// 支持的语言:::从配置文件中配置的支持的语言列表
	private List<String> supportLangs = new ArrayList<String>();

	// i18n数据缓存
	private Map<String, Object> i18nDatabase = new HashMap<String, Object>();

	// 支持的语言
	private List<I18nLangBean> langs = new ArrayList<I18nLangBean>();

	/**
	 * 初始化
	 */
	@SuppressWarnings("unchecked")
	public void init()
	{
		_instance = this;

		I18NCache.getInstance().setDefaultLang(this.defaultLang);

		log.info("Begin to reload i18n data");
		// i18n数据缓存
		Map<String, Object> i18nDatabase = new HashMap<String, Object>();

		// 支持的语言
		List<I18nLangBean> langs = new ArrayList<I18nLangBean>();

		// 记录是否开启国际化
		I18N.open = this.open;

		I18NCache.getInstance().getCache().clear();
		if (open)
		{// 是否开启国际化支持
			// 读取默认配置文件
			if (db)
			{// 查询数据库中的语言
				List<EcanI18nDTO> list = this.commonService
						.list(EcanI18nDTO.class);

				for (EcanI18nDTO dto : list)
				{// 加入多语言支持版本
					if (dto.getStatus().equals(CoreConsts.YORN.YES))
					{
						langs.add(new I18nLangBean(dto.getId(), dto.getName()));
					}
				}

				List<EcanI18NPropertiesDTO> properties = this.commonService
						.list(EcanI18NPropertiesDTO.class);

				for (EcanI18NPropertiesDTO p : properties)
				{
					I18NCache.getInstance().set(p.getLangType(),
							p.getPropertyId(), p.getTextValue());

					if (p.getAppId() != null && p.getAppId().equals("SYSTEM"))
					{// 系统中配置的编码
						Map<String, Object> map = (Map<String, Object>) i18nDatabase
								.get(p.getLangType());
						if (map == null)
						{
							map = new HashMap<String, Object>();
							i18nDatabase.put(p.getLangType(), map);
						}
						
						this.addLang(p.getPropertyId(), p.getTextValue(), map);
					}
				}
			}
		}

		log.info("Load i18n finished\t" + langs);
		this.i18nDatabase = i18nDatabase;
		this.langs = langs;

		domainFacade.init();
	}

	/**
	 * 增加一个语言
	 * 
	 * @param id
	 * @param name
	 * @param content
	 */
	private void addLang(String key, String value, Map<String, Object> map)
	{
		Map<String, Object> temp = map;
		String keys[] = key.split("\\.");
		for (int i = 0; i < keys.length; i++)
		{
			String k = keys[i].trim();

			if (i != keys.length - 1)
			{
				@SuppressWarnings("unchecked")
				Map<String, Object> m = (Map<String, Object>) temp.get(k);
				if (m == null)
				{
					m = new HashMap<String, Object>();
					temp.put(k, m);
				}
				temp = m;
			} else
			{
				temp.put(k, value);
			}
		}

	}

	public boolean isOpen()
	{
		return open;
	}

	public void setOpen(boolean open)
	{
		this.open = open;
	}

	public boolean isDb()
	{
		return db;
	}

	public void setDb(boolean db)
	{
		this.db = db;
	}

	public String getDefaultLang()
	{
		return defaultLang;
	}

	public void setDefaultLang(String defaultLang)
	{
		this.defaultLang = defaultLang;
	}

	public List<String> getSupportLangs()
	{
		return supportLangs;
	}

	public void setSupportLangs(List<String> supportLangs)
	{
		this.supportLangs = supportLangs;
	}

	public Map<String, Object> getI18nDatabase()
	{
		return i18nDatabase;
	}

	public List<I18nLangBean> getLangs()
	{
		return langs;
	}

	private static Log log = LogFactory.getLog(I18nFacade.class);

	/**
	 * 启用
	 * 
	 * @param id
	 * @return
	 */
	public AjaxResponse activeTX(String id)
	{
		EcanI18nDTO i18n = (EcanI18nDTO) this.commonService.get(id,
				EcanI18nDTO.class);
		i18n.setStatus(CoreConsts.YORN.YES);
		this.commonService.updateTX(i18n);

		this.init();

		return new AjaxResponse();
	}

	/**
	 * 停用
	 * 
	 * @param id
	 * @return
	 */
	public AjaxResponse disableTX(String id)
	{
		if (id.equals(this.defaultLang))
		{
			return new AjaxResponse(false, I18N
					.parse("i18n.i18n.msg.disableDefaultLang"));
		} else
		{
			String lang = (String) ExecuteContext.session().getAttribute(
					CoreConsts.LANG);
			if (lang != null && id.equals(lang))
			{
				return new AjaxResponse(false, I18N
						.parse("i18n.i18n.msg.disableUsingLang"));
			}
		}

		EcanI18nDTO i18n = (EcanI18nDTO) this.commonService.get(id,
				EcanI18nDTO.class);
		i18n.setStatus(CoreConsts.YORN.NO);
		this.commonService.updateTX(i18n);

		this.init();

		return new AjaxResponse();
	}

	/**
	 * 保存属性i18n
	 * 
	 * @param p
	 */
	public void saveProperty(final EcanI18NPropertiesDTO p)
	{
		// 插入到缓存中，保证前台能使用
		I18NCache.getInstance().set(p.getLangType(), p.getPropertyId(),
				p.getTextValue());

		exe.execute(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					commonService.saveTX(p);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public void updatePropertySyncToDB(final String lang,
			final String propertyId, final String value, final String comments)
	{

		EcanI18NPropertiesDTO p = (EcanI18NPropertiesDTO) commonService
				.listOnlyObject(
						"from EcanI18NPropertiesDTO t where t.propertyId = ? and t.langType=?",
						propertyId, lang);
		if (p != null)
		{
			p.setTextValue(value);
			commonService.updateTX(p);
		} else
		{
			p = new EcanI18NPropertiesDTO();
			p.setPropertyId(propertyId);
			p.setLangType(lang);
			p.setTextValue(value);
			p.setAppId(comments);
			commonService.saveTX(p);
		}

	}

	/**
	 * 更新i18n属性
	 * 
	 * @param lang
	 * @param propertyId
	 * @param value
	 */
	public void updatePropertyAsync(final String lang, final String propertyId,
			final String value, final String comments)
	{
		// 插入到缓存中，保证前台能使用
		I18NCache.getInstance().set(lang, propertyId, value);

		exe.execute(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					updatePropertySyncToDB(lang, propertyId, value, comments);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 删除i18n属性
	 * 
	 * @param oldPropertyId
	 */
	public void deleteI18nProperty(final String oldPropertyId)
	{
		I18NCache.getInstance().deleteProperty(oldPropertyId);

		exe.submit(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					commonService
							.deleteAllTX(
									"from EcanI18NPropertiesDTO t where t.propertyId = ?",
									oldPropertyId);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * jdbc查询某条记录的一个或多个属性值
	 * 
	 * @param sql
	 * @param id
	 * @param count
	 * @return
	 */
	public String[] queryI18nPropertyID(final String sql, final String id,
			final int count)
	{
		final String[] re = new String[count];

		this.commonService.executeCallbackTX(new HibernateCallback()
		{
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException
			{
				try
				{

					Connection conn = session.connection();
					PreparedStatement pst = conn.prepareStatement(sql);
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					if (rs.next())
					{
						for (int i = 0; i < count; i++)
						{
							re[i] = rs.getString(i + 1);
						}
					}
					pst.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				return null;
			}
		});
		return re;
	}

	private ExecutorService exe = Executors.newFixedThreadPool(2);

	// //////////////////////////////////////////////////////////////
	private static I18nFacade _instance;

	public static I18nFacade getInstance()
	{
		return _instance;
	}

}
