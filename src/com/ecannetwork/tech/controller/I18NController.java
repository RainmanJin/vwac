package com.ecannetwork.tech.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.i18n.I18NCache;
import com.ecannetwork.core.i18n.I18nFacade;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.dto.core.EcanI18NPropertiesDTO;

/**
 * 语言包管理
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping("/i18nmanager")
public class I18NController
{
	@Autowired
	private CommonService commonService;
	@Autowired
	private I18nFacade i18nFacade;

	/**
	 * 列举系统支持的所有语言
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("index")
	public String index(Model model)
	{

		List<EcanI18NPropertiesDTO> properties = this.commonService
				.list("from EcanI18NPropertiesDTO");

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

		for (EcanI18NPropertiesDTO p : properties)
		{
//			if (p.getAppId() != null && p.getAppId().equals("SYSTEM"))
//			{
//				continue;
//			}

			Map<String, String> temp = map.get(p.getPropertyId());
			if (temp == null)
			{
				temp = new HashMap<String, String>();
				map.put(p.getPropertyId(), temp);
			}
			temp.put("id", p.getPropertyId());
			temp.put(p.getLangType(), p.getTextValue());
			if (temp.get("comments") == null)
				temp.put("comments", p.getAppId());
		}

		list.addAll(map.values());
		model.addAttribute("list", list);

		return "tech/i18nmanager/index";
	}

	/**
	 *  查看语言包
	 * 
	 * @param model
	 * @param id
	 * 
	 * @return
	 */
	@RequestMapping("view")
	public String view(Model model, @RequestParam("id") String id)
	{
		model.addAttribute("dto", I18NCache.getInstance().getCache().get(id));
		model.addAttribute("id", id);

		return "tech/i18nmanager/view";
	}

	/**
	 * 保存:::i18n仅支持编辑，不能新增、删除
	 * 
	 * @param i18n
	 * @return
	 */
	@RequestMapping("save")
	public @ResponseBody
	AjaxResponse save(@RequestParam("id") String id,
			@RequestParam("en") String en, @RequestParam("zh_CN") String zh_CN)
	{
		Map<String, String> langI18N = I18NCache.getInstance().getCache().get(
				id);
		langI18N.put("en", en);
		langI18N.put("zh_CN", zh_CN);

		// 更新数据库的值
		I18nFacade.getInstance().updatePropertySyncToDB("en", id, en, "SYSTEM");
		I18nFacade.getInstance().updatePropertySyncToDB("zh_CN", id, zh_CN, "SYSTEM");
		I18nFacade.getInstance().init();

		return new AjaxResponse();
	}
}
