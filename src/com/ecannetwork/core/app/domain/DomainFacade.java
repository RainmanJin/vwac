package com.ecannetwork.core.app.domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecannetwork.core.i18n.I18nFacade;
import com.ecannetwork.core.i18n.I18nLangBean;
import com.ecannetwork.core.module.db.dto.DtoSupport;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.core.EcanDomainDTO;
import com.ecannetwork.dto.core.EcanDomainvalueDTO;

@Component("domainFacade")
public class DomainFacade
{
	@Autowired
	private CommonService commonService;

	// 结构：Map<lang,Map<domainid, Domain>>
	private Map<String, Map<String, EcanDomainDTO>> domainMap = new HashMap<String, Map<String, EcanDomainDTO>>();

	/**
	 * 列举可修改的域
	 * 
	 * @return
	 */
	public List<EcanDomainDTO> listCustomDomains()
	{
		return this.commonService.list(
				"from EcanDomainDTO t where t.canmodify=?", "1");
	}

	/**
	 * 初始化加载所有域信息到内存中
	 */
	// 关闭自动加载功能，由国际化来开启加载 @PostConstruct
	public void init()
	{
		Map<String, Map<String, EcanDomainDTO>> temp = new HashMap<String, Map<String, EcanDomainDTO>>();

		String oldLang = ExecuteContext.getCurrentLang();

		for (I18nLangBean bean : I18nFacade.getInstance().getLangs())
		{
			// 设置加载的语言版本
			ExecuteContext.putCurrentLang(bean.getId());
			Map<String, EcanDomainDTO> domainMap = new HashMap<String, EcanDomainDTO>();
			temp.put(bean.getId(), domainMap);

			// 加载所有的域信息
			List<DtoSupport> domains = commonService.list(EcanDomainDTO.class);
			List<DtoSupport> domainValues = commonService
					.list(EcanDomainvalueDTO.class);

			for (int i = 0; i < domains.size(); i++)
			{
				EcanDomainDTO domain = (EcanDomainDTO) domains.get(i);
				domainMap.put(domain.getId(), domain);
			}

			for (int i = 0; i < domainValues.size(); i++)
			{// 增加域值
				EcanDomainvalueDTO dv = (EcanDomainvalueDTO) domainValues
						.get(i);
				EcanDomainDTO domain = domainMap.get(dv.getDomainId());
				domain.getValues().add(dv);
			}

			// 排序
			for (int i = 0; i < domains.size(); i++)
			{
				EcanDomainDTO domain = (EcanDomainDTO) domains.get(i);
				Collections.sort(domain.getValues(),
						new Comparator<EcanDomainvalueDTO>()
						{
							@Override
							public int compare(EcanDomainvalueDTO o1,
									EcanDomainvalueDTO o2)
							{
								return o1.getIndexsequnce().compareTo(
										o2.getIndexsequnce());
							}
						});
			}

		}

		ExecuteContext.putCurrentLang(oldLang);

		this.domainMap = temp;
	}

	public void saveToApplicationContext()
	{
		Map<String, EcanDomainDTO> map = this.domainMap.get(ExecuteContext
				.getCurrentLang());

		if (CoreConsts.Runtime.servletContext != null)
		{// 刷新servletContext中的domain信息
			for (EcanDomainDTO domain : map.values())
			{
				// 存储domain信息
				CoreConsts.Runtime.servletContext.setAttribute("DOMAIN_"
						+ domain.getId(), domain.getValues());
			}
		}
	}
	
	public Map<String, EcanDomainDTO> getDomainMap(String lang)
	{
		return domainMap.get(lang);
	}

	public Map<String, EcanDomainDTO> getDomainMap()
	{
		return domainMap.get(ExecuteContext.getCurrentLang());
	}
}
