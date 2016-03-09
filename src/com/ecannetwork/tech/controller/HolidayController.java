package com.ecannetwork.tech.controller;

import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.app.user.service.UserService;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.controller.DateBindController;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.DateUtils;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechHoliday;
import com.ecannetwork.tech.facade.HolidayFacade;

/**
 * 假期管理String
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping("/holiday")
public class HolidayController extends DateBindController
{
	@Autowired
	private CommonService commonService;
	@Autowired
	private UserService userService;

	@Autowired
	private HolidayFacade holidayFacade;

	/**
	 * 显示某个月的公共日历或某个用户的日历。<br />
	 * 用户应该支持按姓名搜索和按所属搜索
	 * 
	 * <pre>
	 * -----------------------------------<select>y/m/d, user</select>
	 * 1,2,3,4,..<br />
	 * </pre>
	 * 
	 * @param model
	 * @param year
	 *            没有写则默认为本年
	 * @param month
	 *            默认本月
	 * @param user
	 *            没写则显示公共日历
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "user", required = false) String user)
	{
		if (year == null || year.intValue() == 0)
		{
			year = DateUtils.getYear();
		}
		if (month == null)
		{// month 默认一月为0
			month = DateUtils.getMonth();
		}

		model.addAttribute("year", year);
		model.addAttribute("month", month);

		if (StringUtils.isNotBlank(user))
		{
			model.addAttribute("userid", user);
			model.addAttribute("user", commonService.get(user, EcanUser.class));
		}
		model.addAttribute("users", userService.listByRole("teacher"));

		return "tech/holiday/index";
	}

	/**
	 * 查看某天的日历事件
	 * 
	 * @param model
	 * @param ymd
	 *            添加时不能为空，作为默认假期的开始和截止时间。yyyyMMdd
	 * @param userid
	 *            用户编号， 没有则表示添加公共假期
	 * @param id
	 *            日历的编号,有则表示编辑，没有则表示添加新假期
	 * 
	 * @return
	 */
	@RequestMapping("view")
	public String view(
			Model model,
			@RequestParam(value = "ymd") String ymd,
			@RequestParam(value = "userid", required = false) String userid,
			@RequestParam(value = "id", required = false) String id)
	{
		TechHoliday dto = null;
		if (StringUtils.isNotBlank(id))
		{
			dto = (TechHoliday) commonService.get(id, TechHoliday.class);
		} else
		{
			dto = new TechHoliday();
			dto.setUserId(userid);
			Date date = DateUtils.parseYMD(ymd);
			dto.setBeginDay(date);
			dto.setEndDay(date);
		}

		model.addAttribute("dto", dto);


		return "tech/holiday/view";
	}

	/**
	 * 删除假期，删除成功后，应该页面js跳转到之前的月页面。
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse delete(Model model, @RequestParam("id") String id)
	{
		this.commonService.deleteTX(TechHoliday.class, id);
		holidayFacade.init();
		return new AjaxResponse();
	}

	/**
	 * 保存假期
	 * 
	 * @param holiday
	 * @param result
	 * @return
	 */
	@RequestMapping("save")
	public @ResponseBody
	AjaxResponse save(@Valid TechHoliday holiday, BindingResult result)
	{
		if (StringUtils.isBlank(holiday.getId()))
		{
			holiday.setId(null);
		}
		
		if(holiday.getBeginDay()!=null && holiday.getEndDay()!=null)
		{
			long begin=holiday.getBeginDay().getTime();
			long end = holiday.getEndDay().getTime();
			if(end < begin)
			{
				return new AjaxResponse(false);
			}
		}

		this.commonService.saveOrUpdateTX(holiday);
		holidayFacade.init();
		return new AjaxResponse();
	}
}
