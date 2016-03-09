package com.ecannetwork.tech.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.app.user.service.UserService;
import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.CoreConsts.YORN;
import com.ecannetwork.core.util.DateUtils;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechConsumablesManager;
import com.ecannetwork.dto.tech.TechQuestionManager;
import com.ecannetwork.dto.tech.TechResourseManager;
import com.ecannetwork.dto.tech.TechTrainCourse;
import com.ecannetwork.dto.tech.TechTrainCourseItem;
import com.ecannetwork.dto.tech.TechTrainPlan;
import com.ecannetwork.dto.tech.TechTrainPlanResourse;
import com.ecannetwork.dto.tech.TechTrainPlanUser;
import com.ecannetwork.tech.facade.HolidayFacade;
import com.ecannetwork.tech.facade.TrainPlanReportGenerator;
import com.ecannetwork.tech.notification.mail.MailSender;
import com.ecannetwork.tech.service.TrainCourseService;
import com.ecannetwork.tech.service.TrainPlanService;
import com.ecannetwork.tech.util.TechConsts;

/**
 * 培训计划管理<br />
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping("/trainplanmanager")
public class TrainPlanManagerController
{
	@Autowired
	private TrainPlanService trainPlanService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private TrainCourseService trainCourseService;

	@Autowired
	private TrainPlanReportGenerator trainPlanReportGenerator;

	@Autowired
	private UserService userService;

	@Autowired
	private MailSender mailSender;

	/**
	 * 显示所有年度期计划
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model)
	{
		List<TechTrainPlan> list = this.commonService.list(TechTrainPlan.class);

		// 过滤：：：一个品牌只保留一个年度
		Map<String, TechTrainPlan> plans = new HashMap<String, TechTrainPlan>();
		for (TechTrainPlan plan : list)
		{
			String key = plan.getYearValue() + "_" + plan.getBrand();
			if (plans.get(key) == null)
			{
				plans.put(key, plan);
			}
		}

		model.addAttribute("list", plans.values());

		return "tech/trainplanmanager/index";
	}

	/**
	 *  创建之前选择品牌和年度
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("createPre")
	public String createPre(Model model)
	{
		model.addAttribute("year", DateUtils.getYear());
		return "tech/trainplanmanager/createPre";
	}

	/**
	 * 创建
	 * 
	 * @param year
	 * @param brand
	 * @return
	 */
	@RequestMapping("create")
	public @ResponseBody
	AjaxResponse create(@RequestParam("year") String year,
			@RequestParam("brand") String brand)
	{
		List<TechTrainPlan> plans = this.trainPlanService.listByYearAndBrand(
				year, brand);
		if (plans.size() > 0)
		{
			return new AjaxResponse(false, I18N.parse(
					"i18n.trainplan.msg.createExsist", year));
		}

		return new AjaxResponse();
	}

	/**
	 * 删除域值一个排期
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse del(@RequestParam("id") String id)
	{
		TechTrainPlan plan = (TechTrainPlan) this.commonService.get(id,
				TechTrainPlan.class);

		if (plan != null)
		{
			// 释放消耗品
			List<TechTrainPlanResourse> ress = this.commonService.list(
					"from TechTrainPlanResourse t where t.planId=?",
					plan.getId());

			if (!plan.getStatus().equals(TechTrainPlan.Status.cancel))
			{
				this.releaseResource(ress);
			}

			// 删除资源
			this.commonService.deleteAllTX(ress);

			// 删除讲师人员
			this.commonService.deleteAllTX(
					"from TechTrainPlanUser t where t.planId=?", id);

			this.commonService.deleteTX(plan);
		}
		return new AjaxResponse(true);
	}

	/**
	 * 取消排期
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("cancel")
	public @ResponseBody
	AjaxResponse cancel(@RequestParam("id") String id,
			@RequestParam("reason") String reason)
	{
		TechTrainPlan plan = (TechTrainPlan) this.commonService.get(id,
				TechTrainPlan.class);

		if (plan != null)
		{
			if (plan.getStatus().equals(TechTrainPlan.Status.draft))
			{
				this.commonService.deleteTX(plan);
			} else
			{
				// 释放消耗品
				List<TechTrainPlanResourse> ress = this.commonService.list(
						"from TechTrainPlanResourse t where t.planId=?",
						plan.getId());

				this.releaseResource(ress);

				plan.setStatus(TechTrainPlan.Status.cancel);
				plan.setCancelReason(reason);

				this.commonService.updateTX(plan);
			}
		}
		return new AjaxResponse(true);
	}

	/**
	 * 完成排期计划
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("complete")
	public @ResponseBody
	AjaxResponse complete(@RequestParam("id") String id)
	{
		TechTrainPlan plan = (TechTrainPlan) this.commonService.get(id,
				TechTrainPlan.class);

		if (plan != null)
		{
			plan.setStatus(TechTrainPlan.Status.confirm);
			this.commonService.updateTX(plan);

			// 查询人员列表
			List<TechTrainPlanUser> users = this.commonService
					.list("from TechTrainPlanUser t where t.planId = ? and t.userType = ?",
							plan.getId(), TechTrainPlanUser.UserType.TEACHER);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("plan", plan);
			map.put("course", this.commonService.get(plan.getTrainId(),
					TechTrainCourse.class));

			for (TechTrainPlanUser u : users)
			{
				// 给用户发送通知邮件
				EcanUser user = (EcanUser) this.commonService.get(
						u.getUserId(), TechTrainPlanUser.class);

				mailSender.send(user, map,
						"notification/mail/trainPlanStatusToCompleteSubject_"
								+ ExecuteContext.getCurrentLang() + ".ftl",
						"notification/mail/trainPlanStatusToCompleteContent_"
								+ ExecuteContext.getCurrentLang() + ".ftl");
			}
		}

		return new AjaxResponse(true);
	}

	/**
	 * 导出培训参加人员
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("exportUsers")
	public String exportUsers(@RequestParam("id") String id, Model model)
	{
		TechTrainPlan plan = (TechTrainPlan) this.commonService.get(id,
				TechTrainPlan.class);

		if (plan != null)
		{
			// 查询人员列表
			List<TechTrainPlanUser> users = this.commonService
					.list("from TechTrainPlanUser t where t.planId = ?",
							plan.getId());

			List<EcanUser> us = new ArrayList<EcanUser>(users.size());
			// 设置用户信息
			for (TechTrainPlanUser user : users)
			{
				EcanUser u = (EcanUser) this.commonService.get(
						user.getUserId(), EcanUser.class);
				if (u != null)
				{
					us.add(u);
				}
			}

			model.addAttribute("list", us);
		}

		return "tech/trainplanmanager/exportUsers";
	}

	/**
	 * 当某个排期计划被取消或由取消状态删除时，释放消耗的资源
	 * 
	 * @param ress
	 */
	private void releaseResource(List<TechTrainPlanResourse> ress)
	{
		// =
		// this.commonService.list("from TechTrainPlanResourse t where t.planId=?",planid);
		for (TechTrainPlanResourse res : ress)
		{
			if (res.getPlanType().equals(
					TechTrainPlanResourse.PlanType.XiaoHaoPin))
			{// 消耗品需要释放资源
				TechConsumablesManager resource = (TechConsumablesManager) this.commonService
						.get(res.getResId(), TechConsumablesManager.class);
				if (resource != null)
				{
					resource.setConSum(res.getResSum() + resource.getConSum());
					this.commonService.updateTX(resource);
				}
			}
		}
	}

	/**
	 * 查看或编辑排期计划,,br />
	 * 
	 * @param model
	 * @param year
	 *            创建时，应该在列表页面提示用户输入排期年度，传递到智能排期页面
	 * @param brand
	 * 
	 * @param beginMonth
	 *            开始月份
	 * @param endMonth
	 *            截止月份
	 * @return
	 */
	@RequestMapping("view")
	public String view(
			Model model,
			@RequestParam("year") String year,
			@RequestParam("brand") String brand,
			@RequestParam(value = "beginMonth", required = false) Integer beginMonth,
			@RequestParam(value = "endMonth", required = false) Integer endMonth)
	{
		// Map<proType,List<Course>
		Map<String, List<TechTrainCourse>> map = this.trainPlanService
				.listTranPlayTable(year, brand);
		model.addAttribute("map", map);

		// 查询出每个月的周，设定为月份:Map<month, List<week>
		Map<Integer, List<Integer>> monthWeeks = new HashMap<Integer, List<Integer>>();

		Integer yyyy = Integer.valueOf(year);
		model.addAttribute("year", yyyy);

		Calendar c = DateUtils.firstWeekOfYear(yyyy);

		for (int i = 1; i <= 52; i++)
		{
			Integer month = c.get(Calendar.MONTH);
			List<Integer> weeks = monthWeeks.get(month);
			if (weeks == null)
			{
				weeks = new ArrayList<Integer>();
				monthWeeks.put(month, weeks);
			}
			weeks.add(i);

			c.add(Calendar.WEEK_OF_YEAR, 1);
		}

		model.addAttribute("monthWeeks", monthWeeks);

		if (beginMonth == null)
		{
			beginMonth = Calendar.getInstance().get(Calendar.MONTH);
		}

		if (endMonth == null)
		{
			endMonth = 11;
		}

		model.addAttribute("beginMonth", beginMonth);
		model.addAttribute("endMonth", endMonth);

		// 删除草稿状态的排期计划
		List<TechTrainPlan> draftPlans = this.commonService.list(
				"from TechTrainPlan t where t.status = ?",
				TechTrainPlan.Status.draft);
		for (TechTrainPlan p : draftPlans)
		{
			this.del(p.getId());
		}
		return "tech/trainplanmanager/view";
	}

	/**
	 * 物资使用情况报表
	 * 
	 * @param model
	 * @param year
	 * @param brand
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	@RequestMapping("resReport")
	public String resReport(
			Model model,
			@RequestParam("year") String year,
			@RequestParam("brand") String brand,
			@RequestParam(value = "beginMonth", required = false) Integer beginMonth,
			@RequestParam(value = "endMonth", required = false) Integer endMonth)
	{
		List<TechTrainPlanResourse> list = this.commonService
				.list("select t from TechTrainPlanResourse t, TechTrainPlan p where p.id= t.planId and t.planType=? and (p.status = ? or p.status = ?) and p.yearValue = ? and p.brand = ?",
						TechTrainPlanResourse.PlanType.XiaoHaoPin,
						TechTrainPlan.Status.plan,
						TechTrainPlan.Status.confirm, year, brand);

		Map<String, TechConsumablesManager> map = new HashMap<String, TechConsumablesManager>();
		for (TechTrainPlanResourse rs : list)
		{
			TechConsumablesManager cm = map.get(rs.getResId());
			if (cm == null)
			{
				cm = (TechConsumablesManager) this.commonService.get(
						rs.getResId(), TechConsumablesManager.class);
				if (cm != null)
				{
					cm.setConSum(new Float(0));
					map.put(rs.getResId(), cm);
				}
			}

			if (cm != null)
			{
				cm.setConSum(cm.getConSum() + rs.getResSum());
			}
		}

		model.addAttribute("list", map.values());

		return "tech/trainplanmanager/resReport";
	}

	/**
	 * 讲师、课程、模块统计报表
	 * 
	 * @param model
	 * @param year
	 * @param brand
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	@RequestMapping("stats")
	public String stats(Model model, @RequestParam("year") String year,
			@RequestParam(value = "brand", required = false) String brand,
			@RequestParam(value = "course", required = false) String course,
			HttpServletResponse response, HttpServletRequest request)
	{
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition",
				"attachment; filename=stats.xls");

		String file = CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ "tech/trainplanmanager/template.xls";

		// 将排期计划设计成Map<Week, List<Plan>>结构
		Map<Integer, List<TechTrainPlan>> map = new HashMap<Integer, List<TechTrainPlan>>();
		List<TechTrainPlan> plans = null;
		if (StringUtils.isNotBlank(brand) && StringUtils.isNotBlank(course))
		{
			plans = this.commonService
					.list("from TechTrainPlan t where t.yearValue = ? and t.brand = ? and t.trainId = ? and (t.status = ? or t.status = ?)",
							year, brand, course, TechTrainPlan.Status.plan,
							TechTrainPlan.Status.confirm);
		} else
		{
			if (StringUtils.isNotBlank(brand))
			{
				plans = this.commonService
						.list("from TechTrainPlan t where t.yearValue = ? and t.brand = ? and (t.status = ? or t.status = ?)",
								year, brand, TechTrainPlan.Status.plan,
								TechTrainPlan.Status.confirm);
			} else
			{
				plans = this.commonService
						.list("from TechTrainPlan t where t.yearValue = ? and (t.status = ? or t.status = ?)",
								year, TechTrainPlan.Status.plan,
								TechTrainPlan.Status.confirm);
			}
		}

		for (TechTrainPlan plan : plans)
		{
			List<TechTrainPlan> ps = map.get(plan.getPlanWeek());
			if (ps == null)
			{
				ps = new ArrayList<TechTrainPlan>();
				map.put(plan.getPlanWeek(), ps);
			}

			// 查询出所有的学员和讲师
			List<TechTrainPlanUser> users = this.commonService
					.list("from TechTrainPlanUser t where t.planId = ?",
							plan.getId());
			for (TechTrainPlanUser u : users)
			{
				if (u.getUserType().equals(TechConsts.Role.STUDENT))
				{
					plan.getStudentIds().add(u.getUserId());
				} else
				{
					plan.getTeacherIds().add(u.getUserId());
				}
			}

			ps.add(plan);
		}

		FileInputStream fi = null;
		try
		{
			fi = new FileInputStream(file);
			HSSFWorkbook wb = new HSSFWorkbook(fi);
			Sheet sheet = wb.getSheetAt(0);

			// 生成报表
			for (int i = 1; i <= 52; i++)
			{// 循环52周
				short rowNum = (short) (i + 5);
				int tmdPlan = 0;
				Set<String> coursesPlan = new HashSet<String>();
				Set<String> studentsCountPlan = new HashSet<String>();
				int daysPlan = 0;

				int tmdActual = 0;
				Set<String> coursesActual = new HashSet<String>();
				Set<String> studentsCountActual = new HashSet<String>();
				int daysActual = 0;

				List<TechTrainPlan> ps = map.get(i);
				if (ps != null && ps.size() > 0)
				{
					for (TechTrainPlan p : ps)
					{
						daysPlan += p.getWeekDays().size();
						studentsCountPlan.addAll(p.getStudentIds());
						tmdPlan += p.getStudentIds().size()
								* p.getWeekDays().size();
						coursesPlan.add(p.getTrainId());

						if (p.getStatus().equals(TechTrainPlan.Status.confirm))
						{
							daysActual += p.getWeekDays().size();
							studentsCountActual.addAll(p.getStudentIds());
							tmdActual += p.getStudentIds().size()
									* p.getWeekDays().size();
							coursesActual.add(p.getTecherId());
						}
					}

					// 写入xls
					Row row = sheet.getRow(rowNum);
					row.getCell(3).setCellValue(tmdPlan);
					row.getCell(4).setCellValue(coursesPlan.size());
					row.getCell(5).setCellValue(studentsCountPlan.size());
					row.getCell(6).setCellValue(daysPlan);

					row.getCell(11).setCellValue(tmdActual);
					row.getCell(12).setCellValue(coursesActual.size());
					row.getCell(13).setCellValue(studentsCountActual.size());
					row.getCell(14).setCellValue(daysActual);
				} else
				{
					// // 写入xls
					// Row row = sheet.getRow(rowNum);
					// row.getCell(3).setCellValue(tmdPlan);
					// row.getCell(4).setCellValue(coursesPlan.size());
					// row.getCell(5).setCellValue(studentsCountPlan.size());
					// row.getCell(6).setCellValue(daysPlan);
					//
					// row.getCell(11).setCellValue(tmdActual);
					// row.getCell(12).setCellValue(coursesActual.size());
					// row.getCell(13).setCellValue(studentsCountActual.size());
					// row.getCell(14).setCellValue(daysActual);
				}
			}

			// 写入响应流
			wb.write(response.getOutputStream());
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (fi != null)
			{
				try
				{
					fi.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	/**
	 * 下载,同view保持一致,br />
	 * 
	 * @param model
	 * @param year
	 *            创建时，应该在列表页面提示用户输入排期年度，传递到智能排期页面
	 * @param brand
	 * 
	 * @param beginMonth
	 *            开始月份
	 * @param endMonth
	 *            截止月份
	 * @return
	 */
	@RequestMapping("download")
	public String download(Model model, @RequestParam("year") String year,
			@RequestParam("brand") String brand, HttpServletResponse response)
	{
		// Map<proType,List<Course>
		Map<String, List<TechTrainCourse>> map = this.trainPlanService
				.listTranPlayTable(year, brand);

		// 查询出每个月的周，设定为月份:Map<month, List<week>
		Map<Integer, List<Integer>> monthWeeks = new HashMap<Integer, List<Integer>>();

		Calendar c = Calendar.getInstance();
		Integer yyyy = Integer.valueOf(year);
		model.addAttribute("year", yyyy);

		c.set(Calendar.YEAR, yyyy);
		c.set(Calendar.WEEK_OF_YEAR, 1);
		c.set(Calendar.DAY_OF_WEEK, 1);

		if (c.get(Calendar.YEAR) < yyyy)
		{// 计算一年的第一个星期一
			c.add(Calendar.WEEK_OF_YEAR, 1);
		}

		for (int i = 1; i <= 52; i++)
		{
			Integer month = c.get(Calendar.MONTH);
			List<Integer> weeks = monthWeeks.get(month);
			if (weeks == null)
			{
				weeks = new ArrayList<Integer>();
				monthWeeks.put(month, weeks);
			}
			weeks.add(i);

			c.add(Calendar.WEEK_OF_YEAR, 1);
		}

		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition",
				"attachment; filename=plan.xls");

		try
		{
			trainPlanReportGenerator.generator(monthWeeks, map, yyyy,
					response.getOutputStream());
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 智能排期
	 * 
	 * @param year
	 * @param traincourseid
	 * @param beginweek
	 * @param endweek
	 * @param count
	 * @return
	 */
	@RequestMapping("intelligenceView")
	public String intelligenceView(Model model,
			@RequestParam("year") String year,
			@RequestParam("brand") String brand)
	{

		return "tech/trainplanmanager/intelligenceView";
	}

	/**
	 * 智能排期
	 * 
	 * @param year
	 * @param traincourseid
	 * @param beginweek
	 * @param endweek
	 * @param count
	 * @return
	 */
	@RequestMapping("intelligencePlan")
	public @ResponseBody
	AjaxResponse intelligencePlan(@RequestParam("year") Integer year,
			@RequestParam("brand") String brand,
			@RequestParam("traincourseid") String traincourseid,
			@RequestParam("beginweek") Integer beginweek,
			@RequestParam("endweek") Integer endweek,
			@RequestParam("count") Integer count)
	{
		if (endweek < beginweek)
		{// 开始周不能大于截止周
			return new AjaxResponse(false,
					I18N.parse("i18n.trainplan.msg.intelligencePlan"));
		}

		List<Integer> weeks = new ArrayList();
		int weekCount = 0;
		for (int i = beginweek; i <= endweek; i++)
		{// 去除公共假期的周
			if (!HolidayFacade.isHolidayWeek(year, i))
			{
				weekCount++;
				weeks.add(i);
			}
		}

		if (count > weekCount)
		{// 总周数小于排期数
			return new AjaxResponse(false,
					I18N.parse("i18n.trainplan.msg.intelligencePlan"));
		}

		int step = weekCount / count;
		int fix = weekCount % count;
		int point = 0;
		for (int i = 0; i < count; i++)
		{// 循环每一次
			System.out
					.println("========================:\t" + weeks.get(point));
			TechTrainPlan plan = new TechTrainPlan();
			plan.setPlanWeek(weeks.get(point));
			plan.setYearValue(String.valueOf(year));
			plan.setBrand(brand);
			plan.setTrainId(traincourseid);
			plan.setStatus(TechTrainPlan.Status.fill);

			this.commonService.saveTX(plan);

			if (fix > 0)
			{// 偏移量
				point = point + 1;
				fix--;
			}
			point += step;
		}

		return new AjaxResponse();
	}

	@RequestMapping("viewPlanWithPlanID")
	public String viewPlanWithId(Model model,
			@RequestParam("planid") String planid)
	{
		TechTrainPlan plan = (TechTrainPlan) this.commonService.get(planid,
				TechTrainPlan.class);

		List<TechTrainPlan> plans = this.trainPlanService.get(
				plan.getPlanWeek(), plan.getYearValue(), plan.getBrand(),
				plan.getTrainId());

		// 计算未选择的周
		Set<String> emptyDays = new HashSet<String>();
		emptyDays.add("1");
		emptyDays.add("2");
		emptyDays.add("3");
		emptyDays.add("4");
		emptyDays.add("5");
		emptyDays.add("6");
		emptyDays.add("7");

		for (TechTrainPlan p : plans)
		{
			List<String> tmp = p.getWeekDays();
			if (tmp != null)
				emptyDays.removeAll(tmp);
		}
		model.addAttribute("emptyDays", emptyDays);

		model.addAttribute("plans", plans);
		model.addAttribute("showTab", Boolean.TRUE);

		// 查询人员列表
		List<TechTrainPlanUser> users = this.commonService.list(
				"from TechTrainPlanUser t where t.planId = ?", plan.getId());

		List<TechTrainPlanUser> students = new ArrayList<TechTrainPlanUser>();
		List<TechTrainPlanUser> teachers = new ArrayList<TechTrainPlanUser>();

		for (TechTrainPlanUser u : users)
		{
			if (u.getUserType().equals(TechTrainPlanUser.UserType.STUDENT))
			{
				students.add(u);
			} else
			{
				teachers.add(u);
			}
		}

		model.addAttribute("students", students);
		model.addAttribute("teachers", teachers);

		// 查询资源列表
		List<TechTrainPlanResourse> resources = this.commonService
				.list("from TechTrainPlanResourse t where t.planId = ?",
						plan.getId());
		List<TechTrainPlanResourse> csresources = new ArrayList<TechTrainPlanResourse>();
		List<TechTrainPlanResourse> xhpresources = new ArrayList<TechTrainPlanResourse>();

		for (TechTrainPlanResourse r : resources)
		{
			if (r.getPlanType().equals(TechTrainPlanResourse.PlanType.ChangShe))
			{
				csresources.add(r);
			} else
			{
				xhpresources.add(r);
			}
		}

		model.addAttribute("csresources", csresources);
		model.addAttribute("xhpresources", xhpresources);

		/*
		 * 查询模块信息
		 */

		List<TechTrainCourseItem> modules = this.commonService.list(
				"from TechTrainCourseItem t where t.trainCourseId=?",
				plan.getTrainId());
		model.addAttribute("modules", modules);

		model.addAttribute("yearWeek", Integer.parseInt(plan.getYearValue())
				* 100 + plan.getYearValue());

		model.addAttribute("dto", plan);

		if (plan != null && plan.getStatus().equals(TechTrainPlan.Status.draft))
		{// 查询出问卷列表
			List<TechQuestionManager> ques = this.commonService.list(
					"from TechQuestionManager t where t.questionStatus = ?",
					YORN.YES);
			model.addAttribute("ques", ques);
			model.addAttribute("quesSize", ques.size());
		}

		return "tech/trainplanmanager/viewPlan";
	}

	/**
	 * 查看排期课程或创建排期课程
	 * 
	 * @param model
	 * @param id
	 *            排期编号
	 * @param week
	 *            可以为空,创建时不能为空
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("viewPlan")
	public String viewPlan(Model model,
			@RequestParam(value = "week") Integer week,
			@RequestParam(value = "year") String year,
			@RequestParam(value = "brand") String brand,
			@RequestParam(value = "courseId") String courseId,
			@RequestParam(value = "fc", required = false) String forceCreate,
			HttpServletResponse response)
	{
		List<TechTrainPlan> plans = this.trainPlanService.get(week, year,
				brand, courseId);
		// for (Iterator<TechTrainPlan> it = plans.iterator(); it.hasNext();)
		// {// 删除之前的草稿先
		// TechTrainPlan p = it.next();
		//
		// if (p.getStatus().equals(TechTrainPlan.Status.draft))
		// {
		// this.del(p.getId());
		// it.remove();
		// }
		// }

		TechTrainPlan plan = null;

		if (plans == null || plans.size() == 0
				|| StringUtils.isNotBlank(forceCreate))
		{
			plan = new TechTrainPlan();
			plan.setYearValue(year);
			plan.setBrand(brand);
			plan.setTrainId(courseId);
			plan.setPlanWeek(week);
			plan.setStatus(TechTrainPlan.Status.draft);
			model.addAttribute("dto", plan);
		} else
		{
			plan = plans.get(0);
			return this.viewPlanWithId(model, plan.getId());
		}
		return "tech/trainplanmanager/viewPlan";
	}

	/**
	 * 选择要创建的排期的日期
	 * 
	 * @param model
	 * @param week
	 * @param year
	 * @param brand
	 * @param courseId
	 * @param planDays
	 * @return
	 */
	@RequestMapping("checkCreatePlanDays")
	public String checkCreatePlanDays(Model model,
			@RequestParam(value = "week") Integer week,
			@RequestParam(value = "year") String year,
			@RequestParam(value = "brand") String brand,
			@RequestParam(value = "courseId") String courseId)
	{
		List<TechTrainPlan> plans = this.trainPlanService.get(week, year,
				brand, courseId);

		// 计算未选择的周
		Set<String> emptyDays = new HashSet<String>();
		emptyDays.add("1");
		emptyDays.add("2");
		emptyDays.add("3");
		emptyDays.add("4");
		emptyDays.add("5");
		emptyDays.add("6");
		emptyDays.add("7");

		for (TechTrainPlan p : plans)
		{
			List<String> tmp = p.getWeekDays();
			if (tmp != null)
				emptyDays.removeAll(tmp);
		}

		model.addAttribute("emptyDays", emptyDays);

		return "tech/trainplanmanager/checkCreatePlanDays";
	}

	@RequestMapping("createPlanDays")
	public @ResponseBody
	AjaxResponse createPlanDays(Model model,
			@RequestParam(value = "week") Integer week,
			@RequestParam(value = "year") String year,
			@RequestParam(value = "brand") String brand,
			@RequestParam(value = "courseId") String courseId,
			@RequestParam(value = "planDays") String planDays,
			@RequestParam(value = "remark", required = false) String remark)
	{
		if (StringUtils.isBlank(planDays))
		{
			return new AjaxResponse(false, "日期不能为空");
		}

		TechTrainPlan plan = new TechTrainPlan();
		plan.setPlanWeek(week);
		plan.setYearValue(year);
		plan.setBrand(brand);
		plan.setTrainId(courseId);
		plan.setPlanDays(planDays);
		plan.setStatus(TechTrainPlan.Status.draft);
		plan.setRemark(remark);
		this.commonService.saveTX(plan);

		return new AjaxResponse(true, plan.getId());
	}

	/**
	 * 保存培训计划:::根据周自动计算出开始和截止时间
	 * 
	 * @param plan
	 * @param result
	 * @return
	 */
	@RequestMapping("savePlan")
	public @ResponseBody
	AjaxResponse savePlan(
			Model model,
			@RequestParam("id") String id,
			@RequestParam(value = "status") String status,
			@RequestParam(value = "module", required = false) String module,
			@RequestParam(value = "remark", required = false) String remark,
			@RequestParam(value = "planQuesID", required = false) String planQuesID)
	{//
		TechTrainPlan plan = (TechTrainPlan) this.commonService.get(id,
				TechTrainPlan.class);

		// 检查人员情况
		// 查询人员列表
		List<TechTrainPlanUser> users = this.commonService.list(
				"from TechTrainPlanUser t where t.planId = ?", plan.getId());

		List<TechTrainPlanUser> students = new ArrayList<TechTrainPlanUser>();
		List<TechTrainPlanUser> teachers = new ArrayList<TechTrainPlanUser>();

		for (TechTrainPlanUser u : users)
		{
			if (u.getUserType().equals(TechTrainPlanUser.UserType.STUDENT))
			{
				students.add(u);
			} else
			{
				teachers.add(u);
			}
		}

		// if (students.size() == 0)
		// {
		// return new AjaxResponse(false,
		// I18N.parse("i18n.trainplan.msg.traineeEmpty"));
		// } else
		// {
		// if (teachers.size() == 0)
		// {
		// return new AjaxResponse(false,
		// I18N.parse("i18n.trainplan.msg.trainerEmpty"));
		// }
		// }

		// 开始保存
		plan.setRemark(remark);
		plan.setModuleId(module);

		if (TechTrainPlan.Status.draft.equals(status))
		{
			plan.setStatus(TechTrainPlan.Status.fill);
			plan.setPlanQuesID(planQuesID);
			// 发送邮件给资源管理员

			List<EcanUser> us = this.userService
					.listByRole(TechConsts.Role.RES_ADM);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("plan", plan);
			map.put("course", this.commonService.get(plan.getTrainId(),
					TechTrainCourse.class));
			// 给用户发送通知邮件
			mailSender.send(us, map,
					"notification/mail/trainPlanStatusToResSubject_"
							+ ExecuteContext.getCurrentLang() + ".ftl",
					"notification/mail/trainPlanStatusToResContent_"
							+ ExecuteContext.getCurrentLang() + ".ftl");
		} else if (TechTrainPlan.Status.fill.equals(status))
		{
			plan.setStatus(TechTrainPlan.Status.plan);
			// 发送邮件给计划管理员
			List<EcanUser> us = this.userService
					.listByRole(TechConsts.Role.PLAN_ADM);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("plan", plan);
			map.put("course", this.commonService.get(plan.getTrainId(),
					TechTrainCourse.class));

			// 给用户发送通知邮件
			mailSender.send(us, map,
					"notification/mail/trainPlanStatusToPlanSubject_"
							+ ExecuteContext.getCurrentLang() + ".ftl",
					"notification/mail/trainPlanStatusToPlanContent_"
							+ ExecuteContext.getCurrentLang() + ".ftl");
		}

		this.commonService.updateTX(plan);
		if (plan.getStatus().equals(TechTrainPlan.Status.cancel))
		{// 状态变更为取消时，释放资源

			List<TechTrainPlanResourse> ress = this.commonService.list(
					"from TechTrainPlanResourse t where t.planId=?",
					plan.getId());
			this.releaseResource(ress);

			// 发送邮件给计划管理员
			List<EcanUser> us = this.userService
					.listByRole(TechConsts.Role.PLAN_ADM);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("plan", plan);
			map.put("course", this.commonService.get(plan.getTrainId(),
					TechTrainCourse.class));

			// 给用户发送通知邮件
			mailSender.send(us, map,
					"notification/mail/trainPlanStatusToCancelSubject_"
							+ ExecuteContext.getCurrentLang() + ".ftl",
					"notification/mail/trainPlanStatusToCancelContent_"
							+ ExecuteContext.getCurrentLang() + ".ftl");
		}

		return new AjaxResponse();
	}

	@RequestMapping("saveBasic")
	public @ResponseBody
	AjaxResponse saveBasic(
			Model model,
			@RequestParam("id") String id,
			@RequestParam(value = "module", required = false) String module,
			@RequestParam(value = "remark", required = false) String remark,
			@RequestParam(value = "planQuesID", required = false) String planQuesID)
	{//
		TechTrainPlan plan = (TechTrainPlan) this.commonService.get(id,
				TechTrainPlan.class);

		plan.setModuleId(module);
		plan.setRemark(remark);
		plan.setPlanQuesID(planQuesID);
		this.commonService.updateTX(plan);

		return new AjaxResponse();
	}

	/**
	 * 列举常设资源
	 * 
	 * @param model
	 * @param trainCourseId
	 * @param yearWeek
	 * @param brand
	 * @return
	 */
	@RequestMapping("listCSResources")
	public String listCSResources(Model model, @RequestParam("id") String id)
	{
		List<TechResourseManager> list = this.commonService
				.list("from TechResourseManager t where t.resStatus = ? and t.status=?",
						CoreConsts.YORN.YES, CoreConsts.YORN.YES);

		// 构建map索引
		Map<String, TechResourseManager> resMap = new HashMap<String, TechResourseManager>();
		for (TechResourseManager res : list)
		{
			resMap.put(res.getId(), res);
			res.setIdleSum(res.getResSum());
		}

		TechTrainPlan plan = (TechTrainPlan) this.commonService.get(id,
				TechTrainPlan.class);

		List<Object[]> resources = this.commonService
				.list("select t,p from TechTrainPlanResourse t, TechTrainPlan p where p.id = t.planId and p.yearValue = ? and p.planWeek = ?",
						plan.getYearValue(), plan.getPlanWeek());

		Float usedCount = Float.valueOf(0);
		for (Object[] o : resources)
		{
			TechTrainPlanResourse r = (TechTrainPlanResourse) o[0];
			TechTrainPlan p = (TechTrainPlan) o[1];
			Set<String> days = new HashSet<String>();
			days.addAll(p.getWeekDays());
			for (String d : plan.getWeekDays())
			{
				if (days.contains(d))
				{
					TechResourseManager res = resMap.get(r.getResId());
					if (res != null)
					{
						res.setIdleSum(res.getIdleSum() - r.getResSum());
					}
					break;
				}
			}
		}

		model.addAttribute("list", list);

		return "tech/trainplanmanager/csResources";
	}

	/**
	 * 列举消耗品资源
	 * 
	 * @param model
	 * @param trainCourseId
	 * @param yearWeek
	 * @param brand
	 * @return
	 */
	@RequestMapping("listXHPResources")
	public String listXHPResources(Model model)
	{
		List<TechConsumablesManager> list = this.commonService.list(
				"from TechConsumablesManager t where t.conStatus = ?",
				CoreConsts.YORN.YES);

		model.addAttribute("list", list);

		return "tech/trainplanmanager/xhpResources";
	}

	@RequestMapping("selResources")
	public @ResponseBody
	AjaxResponse selResources(@RequestParam("id") String id,
			@RequestParam("count") String counts,
			@RequestParam("type") String type,
			@RequestParam("resourceid") String recourceids)
	{
		TechTrainPlan plan = (TechTrainPlan) this.commonService.get(id,
				TechTrainPlan.class);

		String[] recourceidsArr = recourceids.split(",");
		String[] countsArr = counts.split(",");

		// 判定库存是否充足
		if (TechTrainPlanResourse.PlanType.ChangShe.equals(type))
		{// 常设::::检查本周该常设物品占用了几个
			for (int i = 0; i < recourceidsArr.length; i++)
			{// 校验资源占用
				String resourceid = recourceidsArr[i];
				String countStr = countsArr[i];
				if (countStr.indexOf(".") != -1
						&& !(countStr.endsWith(".5") || countStr.endsWith(".0")))
				{
					return new AjaxResponse(
							false,
							I18N.parse("i18n.trainplan.msg.inputResourceCountError2"));
				}
				Float count = Float.valueOf(countStr);

				List<Object[]> resources = this.commonService
						.list("select t,p from TechTrainPlanResourse t, TechTrainPlan p where p.id = t.planId and p.yearValue = ? and p.planWeek = ? and t.resId = ?",
								plan.getYearValue(), plan.getPlanWeek(),
								resourceid);

				Float usedCount = Float.valueOf(0);
				for (Object[] o : resources)
				{
					TechTrainPlanResourse r = (TechTrainPlanResourse) o[0];
					TechTrainPlan p = (TechTrainPlan) o[1];
					Set<String> days = new HashSet<String>();
					days.addAll(p.getWeekDays());
					for (String d : plan.getWeekDays())
					{
						if (days.contains(d))
						{
							usedCount += r.getResSum();
							break;
						}
					}
				}

				TechResourseManager resouce = (TechResourseManager) this.commonService
						.get(resourceid, TechResourseManager.class);

				if (resouce.getResSum() < usedCount + count)
				{// 剩余数量不够，资源已被占用
					return new AjaxResponse(false,
							I18N.parse("i18n.trainplan.msg.resourceHasConfict")
									+ ": " + resouce.getResName());
				}
			}

			for (int i = 0; i < recourceidsArr.length; i++)
			{// 分配资源
				String recourceid = recourceidsArr[i];
				Float count = Float.valueOf(countsArr[i]);

				this.allocationResource(id, recourceid, count, type);
			}
		} else
		{// 消耗品
			Map<String, TechConsumablesManager> ress = new HashMap<String, TechConsumablesManager>();

			for (int i = 0; i < recourceidsArr.length; i++)
			{
				String resourceid = recourceidsArr[i];
				Integer count = Integer.valueOf(countsArr[i]);

				TechConsumablesManager resource = (TechConsumablesManager) this.commonService
						.get(resourceid, TechConsumablesManager.class);

				if (resource.getConSum() < count)
				{
					return new AjaxResponse(false,
							I18N.parse("i18n.trainplan.msg.resourceHasLess"));
				} else
				{
					ress.put(resourceid, resource);
				}
			}

			for (int i = 0; i < recourceidsArr.length; i++)
			{
				String resourceid = recourceidsArr[i];
				Float count = Float.valueOf(countsArr[i]);

				TechConsumablesManager resource = ress.get(resourceid);

				// 直接删减库存
				resource.setConSum(resource.getConSum() - count);
				this.commonService.updateTX(resource);

				this.allocationResource(id, resourceid, count, type);
			}
		}

		return new AjaxResponse(true);
	}

	/**
	 * 分配资源
	 * 
	 * @param planId
	 * @param recourceid
	 * @param count
	 * @param type
	 */
	private void allocationResource(String planId, String recourceid,
			Float count, String type)
	{
		List<TechTrainPlanResourse> list = this.commonService
				.list("from TechTrainPlanResourse t where t.planId = ? and t.resId = ?",
						planId, recourceid);
		if (list.size() > 0)
		{// 合并已有资源数量
			TechTrainPlanResourse recourse = list.get(0);
			recourse.setResSum(recourse.getResSum() + count);
			this.commonService.updateTX(recourse);
		} else
		{
			TechTrainPlanResourse resource = new TechTrainPlanResourse();
			resource.setPlanId(planId);
			resource.setPlanType(type);
			resource.setResId(recourceid);
			resource.setResSum(count);
			this.commonService.saveOrUpdateTX(resource);
		}
	}

	@RequestMapping("delResource")
	public @ResponseBody
	AjaxResponse delResource(
			@RequestParam("planResourceId") String planResourceId,
			@RequestParam("type") String type)
	{
		TechTrainPlanResourse plan = (TechTrainPlanResourse) this.commonService
				.get(planResourceId, TechTrainPlanResourse.class);

		if (TechTrainPlanResourse.PlanType.XiaoHaoPin.equals(type))
		{// 消耗品需要还原库存
			TechConsumablesManager xhp = (TechConsumablesManager) this.commonService
					.get(plan.getResId(), TechConsumablesManager.class);
			xhp.setConSum(xhp.getConSum() + plan.getResSum());
			this.commonService.updateTX(xhp);
		}
		this.commonService.deleteTX(plan);
		return new AjaxResponse(true);
	}

	/**
	 * 列举学员
	 * 
	 * @param model
	 * @param courseId
	 * @param yearWeek
	 * @param brand
	 * @return
	 */
	@RequestMapping("listStudents")
	public String listStudents(
			Model model,
			@RequestParam(value = "courseId", required = false) String courseId,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "week", required = false) Integer week)
	{
		List<EcanUser> students = userService
				.listByRole(TechConsts.Role.STUDENT);

		// 滤掉已经选择的学员
		Set<String> studentsIds = new HashSet<String>();
		List<TechTrainPlanUser> users = this.commonService
				.list("select t from com.ecannetwork.dto.tech.TechTrainPlanUser t, com.ecannetwork.dto.tech.TechTrainPlan p where t.planId=p.id and p.yearValue = ? and p.planWeek = ?",
						String.valueOf(year), week);

		for (TechTrainPlanUser p : users)
		{
			studentsIds.add(p.getUserId());
		}

		for (Iterator<EcanUser> it = students.iterator(); it.hasNext();)
		{
			EcanUser u = it.next();
			if (studentsIds.contains(u.getId()))
			{
				it.remove();
			}
		}

		model.addAttribute("list", students);

		return "tech/trainplanmanager/studentsList";
	}

	/**
	 * 列举未休假的讲师，以列表方式控件形式返回<br />
	 * 需要和培训排期、讲师假期日历做校验
	 * 
	 * @param model
	 * @param courseId
	 *            培训课程编号
	 * @param yearWeek
	 *            年周：：：例如201101表示2011年第一周，为空时不剔除修改的讲师
	 * @param value
	 *            默认选择的值
	 * @return
	 */
	@RequestMapping("listOnWorkTeacher")
	public String listOnWorkTeacher(
			Model model,
			@RequestParam(value = "courseId", required = false) String courseId,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "week", required = false) Integer week,
			@RequestParam(value = "brand", required = false) String brand,
			@RequestParam(value = "id") String id)
	{
		// 列举可以教授该课程的讲师
		List<EcanUser> list = this.trainCourseService.listByCourse(courseId);

		// 拿到培训计划
		TechTrainPlan plan = this.trainPlanService.get(id);

		// 将本周已安排的所有讲师保存到teacherIdSet中
		Set<String> teacherIdSet = new HashSet<String>();
		List<Object[]> plans = this.commonService
				.list("select t,p from com.ecannetwork.dto.tech.TechTrainPlanUser t, com.ecannetwork.dto.tech.TechTrainPlan p where t.planId=p.id and p.yearValue = ? and p.planWeek = ?",
						String.valueOf(year), week);

		for (Object[] o : plans)
		{
			TechTrainPlanUser u = (TechTrainPlanUser) o[0];
			TechTrainPlan p = (TechTrainPlan) o[1];

			Set<String> days = new HashSet<String>();
			days.addAll(p.getWeekDays());
			for (String d : plan.getWeekDays())
			{
				if (days.contains(d))
				{
					teacherIdSet.add(u.getUserId());
					break;
				}
			}
		}

		for (Iterator<EcanUser> it = list.iterator(); it.hasNext();)
		{
			EcanUser u = it.next();
			if (HolidayFacade.isHolidayWeek(year, week, u.getId()))
			{// 用户休假的踢出
				it.remove();
			}

			// 过滤同一周有培训计划安排的讲师
			if (teacherIdSet.contains(u.getId()))
			{
				it.remove();
			}
		}

		model.addAttribute("list", list);

		return "tech/trainplanmanager/teacherList";
	}

	/**
	 * 保存讲师或用户
	 * 
	 * @param model
	 * @param ids
	 * @param type
	 * @param planId
	 * @return
	 */
	@RequestMapping("saveTeacherOrStudents")
	public @ResponseBody
	AjaxResponse saveTeacherOrStudents(Model model,
			@RequestParam("ids") String ids, @RequestParam("type") String type,
			@RequestParam("planId") String planId)
	{
		String id[] = ids.split(",");
		for (String i : id)
		{
			if (StringUtils.isNotBlank(i))
			{
				TechTrainPlanUser user = new TechTrainPlanUser();
				user.setPlanId(planId);
				user.setUserType(type);
				user.setUserId(i);
				this.commonService.saveTX(user);
			}
		}

		return new AjaxResponse(true);
	}

	/**
	 * 删除讲师或学员
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteTeacherOrStudent")
	public @ResponseBody
	AjaxResponse deleteTeacherOrStudent(Model model,
			@RequestParam("id") String id)
	{
		this.commonService.deleteTX(TechTrainPlanUser.class, id);

		return new AjaxResponse(true);
	}

	/**
	 * 常设资源使用报表
	 * 
	 * @param year
	 * @param brand
	 * @param model
	 * @return
	 */
	@RequestMapping("csResourceUsedReport")
	public String csResourceUsedReport(@RequestParam("year") String year,
			@RequestParam("brand") String brand, Model model)
	{
		List<TechResourseManager> list = this.commonService
				.list("from TechResourseManager t where t.resStatus = ? and t.status=?",
						CoreConsts.YORN.YES, CoreConsts.YORN.YES);

		// 构建map索引
		Map<String, TechResourseManager> resMap = new HashMap<String, TechResourseManager>();
		for (TechResourseManager res : list)
		{
			resMap.put(res.getId(), res);
			res.setIdleSum(res.getResSum());
		}

		List<Object[]> resources = this.commonService
				.list("select t,p from TechTrainPlanResourse t, TechTrainPlan p where p.id = t.planId and p.yearValue = ? and p.brand = ?",
						year, brand);

		Float usedCount = Float.valueOf(0);
		for (Object o[] : resources)
		{
			TechTrainPlanResourse r = (TechTrainPlanResourse) o[0];
			TechTrainPlan p = (TechTrainPlan) o[1];

			TechResourseManager res = resMap.get(r.getResId());
			if (res != null)
			{
				p.setResSum(r.getResSum());
				Float total = res.getWeekUsedMap().get(p.getPlanWeek());
				if (total == null)
				{
					total = r.getResSum();
				} else
				{
					total += r.getResSum();
				}
				res.getWeekUsedMap().put(p.getPlanWeek(), total);
				res.addWeekPlan(p);
			}
		}

		model.addAttribute("list", list);

		return "tech/trainplanmanager/csResourcesReport";
	}
}
