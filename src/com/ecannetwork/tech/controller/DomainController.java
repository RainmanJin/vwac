package com.ecannetwork.tech.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.app.domain.DomainFacade;
import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.i18n.I18nFacade;
import com.ecannetwork.core.i18n.I18nTypeString;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.dto.core.EcanDomainDTO;
import com.ecannetwork.dto.core.EcanDomainvalueDTO;
import com.ecannetwork.dto.core.EcanI18NPropertiesDTO;

/**
 * 域管理
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping("domain")
public class DomainController
{
	@Autowired
	private DomainFacade domainFacade;

	@Autowired
	private CommonService commonService;

	/**
	 * 域管理首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model,
			@RequestParam(value = "domain", required = false) String domain)
	{
		List<EcanDomainDTO> domains = domainFacade.listCustomDomains();
		model.addAttribute("domains", domains);
		if (domain == null)
		{
			domain = domains.get(0).getId();
		}

		model.addAttribute("domain", domainFacade.getDomainMap().get(domain));

		// 获取domain值直接从缓存中读取
		List<EcanDomainvalueDTO> values = domainFacade.getDomainMap()
				.get(domain).getValues();
		model.addAttribute("values", values);

		return "tech/domain/index";
	}

	/**
	 * 删除域值
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("status")
	public @ResponseBody
	AjaxResponse status(@RequestParam("id") final String id,
			@RequestParam("del") final String del)
	{
		// this.commonService.deleteTX(EcanDomainvalueDTO.class, id);
		// EcanDomainvalueDTO dto = (EcanDomainvalueDTO) this.commonService.get(
		// id, EcanDomainvalueDTO.class);
		//
		// if (dto != null)
		// {
		// dto.setIsDelete(del);
		//
		// //获取ID
		//
		// this.commonService.updateTX(dto);
		// }

		this.commonService.executeCallbackTX(new HibernateCallback()
		{
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException
			{
				try
				{

					Connection conn = session.connection();
					PreparedStatement pst = conn
							.prepareStatement("update ECAN_DOMAINVALUE set IS_DELETE = ? where id=?");
					pst.setString(1, del);
					pst.setString(2, id);
					pst.executeUpdate();
					pst.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				return null;
			}
		});

		// 重新初始化domain
		this.domainFacade.init();
		return new AjaxResponse(true);
	}

	/**
	 * 编辑或新建某个域
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("view")
	public String view(Model model,
			@RequestParam(value = "value", required = false) String value,
			@RequestParam(value = "domain") String domain)
	{
		if (StringUtils.isNotBlank(value))
		{
			EcanDomainvalueDTO dv = this.domainFacade.getDomainMap()
					.get(domain).getByValue(value);

			if (dv != null)
			{
				model.addAttribute("dv", dv);
			}
		}

		return "tech/domain/view";
	}

	/**
	 * 保存
	 * 
	 * @param domain
	 * @param ids
	 * @param labels
	 * @param names
	 * @return
	 */
	@RequestMapping("save")
	public @ResponseBody
	AjaxResponse save(@RequestParam("domain") String domain,
			@RequestParam(value = "id", required = false) final String id,
			@RequestParam("label") final String label,
			@RequestParam("value") String value, @RequestParam("idx") Long idx)
	{
		EcanDomainvalueDTO dv = this.domainFacade.getDomainMap().get(domain)
				.getByValue(value);
		if (StringUtils.isBlank(id) && dv != null)
		{// 非编辑状态,value不能重复
			return new AjaxResponse(false,
					I18N.parse("i18n.domain.value.conflict"));
		} else
		{
			dv = new EcanDomainvalueDTO();
			// dv.setDomainlabel(label);
			// 对label单独做国际化处理
			if (StringUtils.isNotBlank(id))
			{
				dv.setId(id);
				String dbLabel = (String) this.commonService
						.executeCallbackTX(new HibernateCallback<String>()
						{
							@Override
							public String doInHibernate(Session session)
									throws HibernateException, SQLException
							{
								String re = null;
								try
								{

									Connection conn = session.connection();
									PreparedStatement pst = conn
											.prepareStatement("select DOMAINLABEL from  ECAN_DOMAINVALUE where id=?");
									pst.setString(1, id);
									ResultSet rs = pst.executeQuery();
									if (rs.next())
									{
										re = rs.getString(1);
									}
									pst.close();
								} catch (Exception e)
								{
									e.printStackTrace();
								}
								return re;
							}
						});

				String pid = null;
				EcanI18NPropertiesDTO property = null;
				if (dbLabel.startsWith(I18nTypeString.I18N_PREFIX))
				{
					pid = dbLabel.substring(I18nTypeString.I18N_PREFIX_LENGTH);
					property = (EcanI18NPropertiesDTO) this.commonService
							.listOnlyObject(
									"from EcanI18NPropertiesDTO t where t.propertyId = ? and t.langType = ?",
									pid, ExecuteContext.getCurrentLang());
				}
				if (property != null)
				{
					property.setTextValue(label);
					this.commonService.updateTX(property);
				} else
				{
					property = new EcanI18NPropertiesDTO();
					property.setAppId("SYSTEM");
					property.setLangType(ExecuteContext.getCurrentLang());
					property.setPropertyId(pid == null ? UUID.randomUUID()
							: pid);
					property.setTextValue(label);
					this.commonService.saveTX(property);
				}

				dv.setDomainlabel(I18nTypeString.I18N_PREFIX
						+ property.getPropertyId());
			} else
			{// 新建的,直接创建一个property
				EcanI18NPropertiesDTO property = new EcanI18NPropertiesDTO();
				property.setAppId("SYSTEM");
				property.setLangType(ExecuteContext.getCurrentLang());
				property.setPropertyId(UUID.randomUUID());
				property.setTextValue(label);
				this.commonService.saveTX(property);

				dv.setDomainlabel(I18nTypeString.I18N_PREFIX
						+ property.getPropertyId());
			}

			dv.setDomainvalue(value);
			dv.setIndexsequnce(idx);
			dv.setDomainId(domain);

			this.commonService.saveOrUpdateTX(dv);

			I18nFacade.getInstance().init();

			this.domainFacade.init();
			return new AjaxResponse(true);
		}
	}
}
