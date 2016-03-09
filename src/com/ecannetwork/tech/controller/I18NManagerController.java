package com.ecannetwork.tech.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.dto.core.EcanI18nDTO;

/**
 * 国际化支持管理。修改后，应该调用i18nFacade重新加载所有国际化语言
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping("i18nmanager")
public class I18NManagerController
{
	/**
	 * 显示所有国际化支持语言
	 * 
	 * @param model
	 * @return
	 */
	public String index(Model model)
	{
		return null;
	}

	/**
	 * 编辑语言文件内容:::国际化支持语言暂不支持新建,系统默认初始化所有语言版本<br />
	 * 界面上id是不能修改的语言编码
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	public String view(Model model, @RequestParam("id") String id)
	{
		return null;
	}

	/**
	 * 保存:::提示用户保存成功后，应刷新页面，确保切换语言功能刷新
	 * 
	 * @param model
	 * @param i18n
	 * @param result
	 * @return
	 */
	public @ResponseBody
	AjaxResponse save(Model model, @Valid EcanI18nDTO i18n, BindingResult result)
	{
		return null;
	}

	/**
	 * 禁用某种语言::提示用户保存后，应刷新页面，确保切换语言功能刷新
	 * 
	 * @param id
	 * @return
	 */
	public @ResponseBody
	AjaxResponse disable(@RequestParam("id") String id)
	{
		return null;
	}

	/**
	 * 启用某种语言::提示用户保存后，应刷新页面，确保切换语言功能刷新
	 * 
	 * @param id
	 * @return
	 */
	public @ResponseBody
	AjaxResponse active(@RequestParam("id") String id)
	{
		return null;
	}
}
