package com.ecannetwork.tech.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.controller.DateBindController;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.DateUtils;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechHoliday;
import com.ecannetwork.dto.tech.TechTrainPlan;
import com.ecannetwork.dto.tech.TechTrainPlanUser;
import com.ecannetwork.tech.facade.HolidayFacade;

/**
 * 个人中心
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping("/userscheduling")
public class UserSchedulingController extends DateBindController
{
	@Autowired
	private CommonService commonService;
	@Autowired
	private HolidayFacade holidayFacade;
	private static Log log = LogFactory.getLog(UserSchedulingController.class);

	/**
	 * 显示某个月的公共日历或某个用户的日历。<br />
	 * 用户应该支持按姓名搜索和按所属搜索
	 * 
	 * <pre>
	 * -----------------------------------&lt;select&gt;y/m/d, user&lt;/select&gt;
	 * 1,2,3,4,..
	 * <br />
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
	@SuppressWarnings("unchecked")
	@RequestMapping("index")
	public String scheduling(Model model,
	        @RequestParam(value = "year", required = false)
	        Integer year, @RequestParam(value = "month", required = false)
	        Integer month)
	{
		EcanUser user = ExecuteContext.user();
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

		// 查出当前用户全部的排期
		List<TechTrainPlanUser> list = commonService.list(
		        "from TechTrainPlanUser t where t.userId=?", user.getId());
		int i = 0;
		StringBuilder sb = new StringBuilder();
		for (TechTrainPlanUser techTrainPlanUser : list)
		{
			i++;
			sb.append("'").append(techTrainPlanUser.getPlanId()).append("'");
			if (i != list.size())
			{
				sb.append(",");
			}
		}
		log.info("当前用户排期的ID" + sb.toString() + "\t 当前用户" + user.getName());
		// Set<Integer> weeks = new HashSet<Integer>();
		Map<Integer, TechTrainPlan> _weeks = new HashMap<Integer, TechTrainPlan>();

		// 通过排期ID查出培训计划表
		if (sb != null && !"".equals(sb.toString()))
		{
			List<TechTrainPlan> planList = commonService
			        .list("from TechTrainPlan t where t.id in ("
			                + sb.toString() + ")");
			for (TechTrainPlan techTrainPlan : planList)
			{
				if (techTrainPlan.getYearValue().equals(year + ""))
				{
					// weeks.add(techTrainPlan.getPlanWeek());
					_weeks.put(techTrainPlan.getPlanWeek(), techTrainPlan);
				}
			}
		}

		// 等到排期计划当前选择年月的周分布

		model.addAttribute("week", _weeks);
		model.addAttribute("userid", user.getId());
		model.addAttribute("user", user);
		// }
		// model.addAttribute("users", userService.listByRole("teacher"));

		return "tech/userscheduling/scheduling";
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
	AjaxResponse delete(Model model, @RequestParam("id")
	String id)
	{
		this.commonService.deleteTX(TechHoliday.class, id);
		holidayFacade.init();
		return new AjaxResponse();
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
	public String view(Model model, @RequestParam(value = "ymd")
	String ymd, @RequestParam(value = "userid", required = false)
	String userid, @RequestParam(value = "id", required = false)
	String id)
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

		return "tech/userscheduling/view";
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
	AjaxResponse save(@Valid
	TechHoliday holiday, BindingResult result)
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

	public static int getWeek(String date)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		try
		{
			cal.setTime(format.parse(date));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		return week;
	}
	
	public static void main(String[] args)
    {
	   String str="2010-02-24";
	   String year = str.substring(8,10);
	   System.out.println(year);
    }

}
