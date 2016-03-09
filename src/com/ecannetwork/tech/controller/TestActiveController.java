package com.ecannetwork.tech.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.app.domain.DomainFacade;
import com.ecannetwork.core.app.user.service.UserService;
import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.controller.DateBindController;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.tld.facade.CachedDtoViewFacade;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.CoreConsts.YORN;
import com.ecannetwork.core.util.DateUtils;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.core.util.JoinHelper;
import com.ecannetwork.dto.core.EcanDomainDTO;
import com.ecannetwork.dto.core.EcanDomainvalueDTO;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechTestActive;
import com.ecannetwork.dto.tech.TechTestActiveFeedback;
import com.ecannetwork.dto.tech.TechTestActivePoint;
import com.ecannetwork.dto.tech.TechTestActivePointIndex;
import com.ecannetwork.dto.tech.TechTestActiveStandard;
import com.ecannetwork.dto.tech.TechTestAnswer;
import com.ecannetwork.dto.tech.TechTestDb;
import com.ecannetwork.dto.tech.TechTestRecommend;
import com.ecannetwork.dto.tech.TechTestRecord;
import com.ecannetwork.dto.tech.TechTestUser;
import com.ecannetwork.tech.controller.bean.testactive.Point;
import com.ecannetwork.tech.controller.bean.testactive.Points;
import com.ecannetwork.tech.controller.bean.testactive.Row;
import com.ecannetwork.tech.controller.bean.testactive.StatPoint;
import com.ecannetwork.tech.controller.bean.testactive.StatRow;
import com.ecannetwork.tech.controller.export.PTPChartGenerator;
import com.ecannetwork.tech.controller.export.PointStatChartGenerator;
import com.ecannetwork.tech.facade.TestActiveFacade;
import com.ecannetwork.tech.facade.TestActiveReportGenerator;
import com.ecannetwork.tech.notification.mail.MailSender;
import com.ecannetwork.tech.service.TestActiveIndexStandardFacade;
import com.ecannetwork.tech.service.TestActiveService;
import com.ecannetwork.tech.util.TechConsts;

/**
 * 测评活动
 * 
 * @author think
 * 
 */
@Controller
@RequestMapping("/testactive")
public class TestActiveController extends DateBindController
{
	@Autowired
	private CommonService commonService;

	@Autowired
	private DomainFacade domainFacade;

	@Autowired
	private TestActiveService activeService;

	@Autowired
	private UserService userService;

	@Autowired
	private TestActiveFacade testActiveFacade;

	@Autowired
	private CachedDtoViewFacade cacheDtoViewFacade;

	@Autowired
	private TestActiveReportGenerator testActiveReportGenerator;

	@Autowired
	private TestActiveIndexStandardFacade testActiveIndexStandardFacade;

	@Autowired
	private MailSender mailSender;

	/**
	 * 查看活动:::讲师只能查看自己创建的测评活动,管理员可以查看所有测评活动
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("index")
	public String index(Model model)
	{
		String userid = ExecuteContext.user().getId();

		// 我发起的活动：：：查询出所有活动，过滤
		List<TechTestActive> lists = null;

		String role = ExecuteContext.user().getRoleId();
		if (TechConsts.Role.ADMIN.equals(role))
		{
			lists = this.commonService
					.list("from TechTestActive t order by t.creatTime desc");
		} else
		{
			// 我发起的活动：：：查询出所有活动，过滤
			lists = this.commonService
					.list("from TechTestActive t where t.hosterId=? or t.mainManId=? order by t.creatTime desc",
							userid, userid);
		}

		model.addAttribute("mines", lists);

		// 我参与的活动

		return "tech/testactive/index";

		// 将需求变更为
		/*
		 * 
		 * // 我发起的活动：：：查询出所有活动，过滤 List<TechTestActive> lists =
		 * this.commonService
		 * .list("from TechTestActive t order by t.creatTime desc");
		 * 
		 * // 我发起的活动 List<TechTestActive> mines = new
		 * ArrayList<TechTestActive>(); // 我参与的活动 List<TechTestActive> incl =
		 * new ArrayList<TechTestActive>();
		 * 
		 * String userid = ExecuteContext.user().getId();
		 * 
		 * for (TechTestActive active : lists) { if (active.getMainManId() !=
		 * null && active.getMainManId().equals(userid)) {// 主试人
		 * mines.add(active); } else { if (active.getHosterId() != null &&
		 * active.getHosterId().equals(userid)) {// 主持人 incl.add(active); } else
		 * { if (active.getWatchMens() != null &&
		 * active.getWatchMenIds().contains(userid)) {// 观察人 incl.add(active); }
		 * } } }
		 * 
		 * model.addAttribute("mines", mines); model.addAttribute("incl", incl);
		 * 
		 * // 我参与的活动
		 * 
		 * return "tech/testactive/index";
		 */
	}

	/**
	 * 添加
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("beforeAdd")
	public String beforeAdd(Model model)
	{
		TechTestActive active = new TechTestActive();
		// DELETE PROTYPE
		// active.setProType(ExecuteContext.user().getProType());
		active.setCreatTime(new Date());

		model.addAttribute("dto", active);

		return "tech/testactive/beforeAdd";
	}

	/**
	 * 创建
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("add")
	public @ResponseBody
	AjaxResponse add(Model model, @RequestParam("creatTime") Date creatTime,
			@RequestParam("proType") String proType,
			@RequestParam(value = "group", required = false) String group)
	{
		String name = domainFacade.getDomainMap().get("PROTYPE")
				.getByValue(proType).getDomainlabel()
				+ "_" + DateUtils.formartYMD(creatTime);

		if (StringUtils.isNotBlank(group)
				&& StringUtils.isNotBlank(group.trim()))
		{
			name += "_" + group.trim();
		}

		List<TechTestActive> list = this.commonService.list(
				"from TechTestActive t where t.name=?", name);

		if (list.size() > 0)
		{
			return new AjaxResponse(false,
					I18N.parse("i18n.testactive.msg.nameConfict"));
		}

		TechTestActive active = new TechTestActive();
		active.setCreatTime(creatTime);
		active.setProType(proType);
		active.setStatus(TechTestActive.STATUS.EDIT);
		active.setMainManId(ExecuteContext.user().getId());
		active.setFinAddPoint(CoreConsts.YORN.NO);
		active.setFinTest(CoreConsts.YORN.NO);
		// 编号默认为领域+日期
		active.setName(name);

		this.commonService.saveTX(active);

		return new AjaxResponse(true, active.getId());
	}

	/**
	 * 编辑测评活动或创建测评活动<br />
	 * 
	 * 活动相关讲师AJAX待选列表：CommonResourceController.listTeacher, 专业方向必须和活动专业方向一致<br />
	 * 活动相关用户AJAX待选列表：CommonResourceController.listUser<br />
	 * 
	 * @param model
	 * @param id
	 *            为空时表示创建
	 * @return
	 */
	@RequestMapping("view")
	public String view(Model model,
			@RequestParam(value = "id", required = false) String id)
	{
		TechTestActive active = (TechTestActive) this.activeService.get(id);

		model.addAttribute("dto", active);

		return "tech/testactive/view";
	}

	/**
	 * 保存测评活动:::编辑时，需要提示将清空所有测评活动信息,已完成的测评活动不能编辑<br />
	 * 保存测评活动时，应抽取测评活动试卷
	 * 
	 * @param model
	 * @param brand
	 * @param testcount
	 * @param testtime
	 * @param hosterid
	 * @param id
	 * @param type
	 *            是否自动提交：：：在选择用户时，需要自动保存当前页面的信息，以确保刷新时不丢失信息
	 * @return
	 */
	@RequestMapping("save")
	public @ResponseBody
	AjaxResponse saveOrUpdate(Model model, @RequestParam("brand") String brand,
			@RequestParam("testcount") Integer testcount,
			@RequestParam("testtime") Integer testtime,
			@RequestParam("hosterid") String hosterid,
			@RequestParam("id") String id,
			@RequestParam(value = "type", required = false) String type)
	{
		TechTestActive active = (TechTestActive) this.activeService.get(id);

		active.setBrand(brand);
		active.setHosterId(hosterid);
		active.setTestTimelimit(testtime);
		active.setTestCount(testcount);

		if (!"auto".equals(type))
		{

			if (testcount <= 0)
			{// 题目数量必须大于0
				// return new AjaxResponse(false, I18N
				// .parse("i18n.testactive.msg.testCountMaxThenZero"));
				testcount = 0;
				testtime = 0;
			} else
			{
				if (testtime <= 0)
				{// 测试时间必须大于0
					return new AjaxResponse(
							false,
							I18N.parse("i18n.testactive.msg.testTimeMaxThenZero"));
				}
			}

			if (active.getWatchMenIds().size() == 0)
			{// 观察员不能为空
				return new AjaxResponse(false,
						I18N.parse("i18n.testactive.msg.nonWatchmens"));
			}

			if (active.getUserids().size() == 0)
			{// 候选人不能为空
				return new AjaxResponse(false,
						I18N.parse("i18n.testactive.msg.nonUserids"));
			}

			// 测试题数量是否有更改，有则需要重新生成
			if (active.getTestCount() != null && active.getTestCount() > 0)
			{// 生成试卷
				AjaxResponse response = this.testActiveFacade
						.generatorTest(active);
				if (!response.isSuccess())
				{
					return response;
				}
			}

			// 设定为提交状态
			active.setStatus(TechTestActive.STATUS.READY);

			// 发送邮件通知
			Map<String, Object> datas = new HashMap<String, Object>();
			datas.put("dc", active);
			EcanUser mainMan = (EcanUser) this.commonService.get(active.getMainManId(), EcanUser.class);
			datas.put("mainMan", mainMan);
			EcanUser u = null;
			// 主持人
			for (String wid : active.getWatchMenIds())
			{
				u = (EcanUser) this.commonService.get(wid, EcanUser.class);
				mailSender.send(u, datas,
						"notification/mail/dcCreateSubject_mainMan_"
								+ ExecuteContext.getCurrentLang() + ".ftl",
						"notification/mail/dcCreateContent_mainMan_"
								+ ExecuteContext.getCurrentLang() + ".ftl");
			}
			// 主试人:::就是自己，暂不发邮件
			// mailSender.send(u, datas,
			// "notification/mail/dcCreateSubject_mainMan.ftl",
			// "notification/mail/dcCreateContent_mainMan.ftl");

			// 观察员
			u = (EcanUser) this.commonService.get(active.getMainManId(),
					EcanUser.class);
			mailSender.send(u, datas,
					"notification/mail/dcCreateSubject_watchMan_"
							+ ExecuteContext.getCurrentLang() + ".ftl",
					"notification/mail/dcCreateContent_watchMan_"
							+ ExecuteContext.getCurrentLang() + ".ftl");
			// 候选人:::暂不发邮件
		}

		this.commonService.updateTX(active);

		return new AjaxResponse();
	}

	/**
	 * 列举讲师
	 * 
	 * @param model
	 * @param id
	 *            活动编号
	 * @param type
	 *            视图名称：：：主持人=hoster, 观察员=watchmen， 候选人=user
	 * @return
	 */
	@RequestMapping("listUsers")
	public String listUsers(Model model, @RequestParam("id") String id,
			@RequestParam("type") String type)
	{
		TechTestActive active = null;

		List<EcanUser> users = null;
		if ("hoster".equals(type) || "watchmen".equals(type))
		{// 选择主持人// 选择观察员
			active = (TechTestActive) this.commonService.get(id,
					TechTestActive.class);

			users = this.userService.listByProType(active.getProType(),
					TechConsts.Role.TEACHER);

			for (Iterator<EcanUser> it = users.iterator(); it.hasNext();)
			{ // 过滤掉已经选择的讲师
				EcanUser u = it.next();
				if (StringUtils.equals(u.getId(), active.getHosterId())
						|| StringUtils.equals(u.getId(), active.getMainManId())
						|| active.getWatchMenIds().contains(u.getId()))
				{
					it.remove();
				}
			}

		} else
		{// 选择候选人

			active = (TechTestActive) this.activeService.get(id);

			users = this.userService.listByProType(active.getProType(),
					TechConsts.Role.STUDENT);

			// 过滤掉所有曾经参加过活动并已经打过分的候选人
			List<String> userids = this.commonService
					.list("select distinct t.userId from TechTestActivePoint t",
							null);

			Set<String> useridsSet = new HashSet<String>();
			useridsSet.addAll(userids);

			for (Iterator<EcanUser> it = users.iterator(); it.hasNext();)
			{ // 过滤掉已经选择的学员, // 过滤掉所有曾经参加过活动并已经打过分的候选人
				EcanUser u = it.next();
				if (active.getUserids().contains(u.getId())
						|| useridsSet.contains(u.getId()))
				{
					it.remove();
				}
			}

		}

		model.addAttribute("list", users);

		return "tech/testactive/selectuser";
	}

	/**
	 * 删除观察员或者候选人
	 * 
	 * @param userid
	 * @param type
	 *            观察员：watchmen， 候选人：user
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("delUsers")
	public @ResponseBody
	AjaxResponse delUsers(@RequestParam("userid") String userid,
			@RequestParam("type") String type, @RequestParam("id") String id)
	{
		if ("watchmen".equals(type))
		{// 删除观察员
			TechTestActive active = (TechTestActive) this.commonService.get(id,
					TechTestActive.class);
			active.getWatchMenIds().remove(userid);
			active.setWatchMens(JoinHelper.join(active.getWatchMenIds(), ","));
			this.commonService.updateTX(active);
		} else
		{// 删除候选人
			List<TechTestUser> users = this.commonService
					.list("from TechTestUser t where t.testActiveId=? and t.userId=?",
							id, userid);
			if (users != null)
			{
				this.commonService.deleteAllTX(users);
			}
		}

		return new AjaxResponse();
	}

	/**
	 * 添加用户
	 * 
	 * @param userids
	 * @param type
	 *            观察员：watchmen， 候选人：user
	 * @param id
	 * @return
	 */
	@RequestMapping("addUsers")
	public @ResponseBody
	AjaxResponse addUsers(@RequestParam("userids") String userids,
			@RequestParam("type") String type, @RequestParam("id") String id)
	{
		String uids[] = userids.split(",");

		if ("watchmen".equals(type))
		{// 观察员
			TechTestActive active = (TechTestActive) this.commonService.get(id,
					TechTestActive.class);
			for (String uid : uids)
			{
				active.getWatchMenIds().add(uid);
			}

			if (active.getWatchMenIds().size() > 6)
			{
				return new AjaxResponse(false,
						I18N.parse("i18n.testactive.msg.maxSixWatchMen"));
			}

			active.setWatchMens(JoinHelper.join(active.getWatchMenIds(), ","));
			this.commonService.updateTX(active);
		} else
		{// 候选人
			for (String uid : uids)
			{
				TechTestUser user = new TechTestUser();
				user.setUserId(uid);
				user.setTestActiveId(id);
				this.commonService.saveTX(user);
			}
		}

		return new AjaxResponse();
	}

	/**
	 * 更改主持人
	 * 
	 * @param id
	 * @param hosterid
	 * @return
	 */
	@RequestMapping("chgHoster")
	public @ResponseBody
	AjaxResponse chgHoster(@RequestParam("id") String id,
			@RequestParam("hosterid") String hosterid)
	{
		TechTestActive active = (TechTestActive) this.commonService.get(id,
				TechTestActive.class);
		active.setHosterId(hosterid);
		this.commonService.updateTX(active);

		return new AjaxResponse();
	}

	/**
	 * 进入成绩添加页面:::应该列举11个维度、5阶段、每个观察员都必须打分,页面应该能直接切换到其他候选人
	 * 
	 * @param mode
	 * 
	 * @param id
	 *            活动编号
	 * @param userid
	 *            用户编号::::用户可以为空，表示直接进入
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("addPoint")
	public String addPoint(Model mode, @RequestParam("id") String id,
			@RequestParam(value = "userid", required = false) String userid)
	{

		// 当前编辑的学员
		mode.addAttribute("curUserid", userid);

		TechTestActive active = this.activeService.get(id);
		mode.addAttribute("dto", active);

		// 保存状态
		ExecuteContext.session().setAttribute("_sessionActiveStatus",
				active.getStatus());

		// 已打分的
		List<TechTestActivePoint> users = this.commonService.list(
				"from TechTestActivePoint t where t.testActiveId=?", id);

		Set<String> hasPoints = new HashSet<String>();

		// 维度编号做为key,多个观察员的打分列表
		Map<String, List<TechTestActivePoint>> points = new HashMap<String, List<TechTestActivePoint>>();
		for (TechTestActivePoint u : users)
		{
			hasPoints.add(u.getUserId());

			active.getUserids().remove(u.getUserId());

			if (StringUtils.equals(userid, u.getUserId()))
			{// 记录用户的分数
				List<TechTestActivePoint> list = points.get(u.getDimension());
				if (list == null)
				{
					list = new ArrayList<TechTestActivePoint>();
					points.put(u.getDimension(), list);
				}
				list.add(u);
			}
		}

		mode.addAttribute("hasPoints", hasPoints);

		// 未打分学员数量
		mode.addAttribute("hasPointCount", hasPoints.size());
		// 已打分学员数量
		mode.addAttribute("haventPointCount", active.getUserids().size());

		if (StringUtils.isNotBlank(userid))
		{// 维度信息
			mode.addAttribute("dimension",
					testActiveFacade.parseDimensionRows(points));
		}
		return "tech/testactive/addPoint";
	}

	/**
	 * 保存打分信息:::需要使用到Request.getParameterNames()<br />
	 * 分数的input框命名规则：name="point_" + 11维度编号（域）+ "_" + 5个阶段的阶段编号(域)+ "_" + 观察员编号
	 * 
	 * @param model
	 * @param id
	 * @param userid
	 * @param request
	 * @return
	 */
	@RequestMapping("savePoint")
	public @ResponseBody
	AjaxResponse savePoint(Model model, @RequestParam("id") String id,
			@RequestParam("watcherId") String watcherId,
			@RequestParam("dimId") String dimId,
			@RequestParam("point") Double point,
			@RequestParam("userid") String userid)
	{
		String status = (String) ExecuteContext.session().getAttribute(
				"_sessionActiveStatus");
		if (TechTestActive.STATUS.READY.equals(status))
		{
			TechTestActive active = (TechTestActive) this.commonService.get(id,
					TechTestActive.class);
			active.setStatus(TechTestActive.STATUS.RUNNING);
			this.commonService.updateTX(active);
		}

		return this.activeService.saveOrUpdatePointTX(id, watcherId, dimId,
				point, userid);
	}

	/**
	 * 提交打分记录：：如果已经完成测试则更改状态为完成
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("submitPoint")
	public @ResponseBody
	AjaxResponse submitPoint(@RequestParam("id") String id)
	{
		TechTestActive active = (TechTestActive) this.commonService.get(id,
				TechTestActive.class);
		if (CoreConsts.YORN.YES.equals(active.getFinTest()))
		{
			active.setStatus(TechTestActive.STATUS.FIN);
		}
		active.setFinAddPoint(CoreConsts.YORN.YES);
		this.commonService.updateTX(active);

		return new AjaxResponse();
	}

	/**
	 * 查看线下活动计分总表
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("viewPointStat")
	public String viewPointStat(Model mode, @RequestParam("id") String id,
			@RequestParam("userid") String userid, HttpServletRequest request)
	{
		// 当前编辑的学员
		mode.addAttribute("curUserid", userid);

		TechTestActive active = this.activeService.get(id);
		mode.addAttribute("dto", active);

		// 已打分的
		List<TechTestActivePoint> users = this.commonService
				.list("from TechTestActivePoint t where t.testActiveId=? and t.userId=?",
						id, userid);

		// 维度编号做为key,多个观察员的打分列表
		Map<String, List<TechTestActivePoint>> points = new HashMap<String, List<TechTestActivePoint>>();
		for (TechTestActivePoint u : users)
		{
			List<TechTestActivePoint> list = points.get(u.getDimension());
			if (list == null)
			{
				list = new ArrayList<TechTestActivePoint>();
				points.put(u.getDimension(), list);
			}

			list.add(u);
		}

		// 维度信息
		mode.addAttribute("dimension",
				testActiveFacade.parseDimensionRows(points));

		// 统计维度信息：：：合并了重复项，适合展示的
		List<StatRow> stats = testActiveFacade.parseDimensionStat(active,
				points);

		// 供画图使用:::resultImage
		List<StatPoint> statspoint = new ArrayList<StatPoint>();
		for (StatRow srow : stats)
		{
			statspoint.add(new StatPoint(srow.getDimName(), srow.getAvg28(),
					srow.getPointIndex()));
		}
		request.getSession().setAttribute("_statspoints", statspoint);

		mode.addAttribute("stats", stats);

		return "tech/testactive/viewPointStat";
	}

	/**
	 * 查看线下活动计分总表
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("viewPointLevels")
	public String viewPointLevels(Model mode, @RequestParam("id") String id,
			@RequestParam("userid") String userid, HttpServletRequest request)
	{
		TechTestActive active = this.activeService.get(id);

		// 已打分的
		List<TechTestActivePoint> users = this.commonService
				.list("from TechTestActivePoint t where t.testActiveId=? and t.userId=?",
						id, userid);

		// 维度编号做为key,多个观察员的打分列表
		Map<String, List<TechTestActivePoint>> points = new HashMap<String, List<TechTestActivePoint>>();
		for (TechTestActivePoint u : users)
		{
			List<TechTestActivePoint> list = points.get(u.getDimension());
			if (list == null)
			{
				list = new ArrayList<TechTestActivePoint>();
				points.put(u.getDimension(), list);
			}

			list.add(u);
		}

		// // 维度信息
		// mode.addAttribute("dimension", testActiveFacade
		// .parseDimensionRows(points));

		// 统计维度信息：：：合并了重复项，适合展示的
		List<StatRow> stats = testActiveFacade.parseDimensionStat(active,
				points);

		mode.addAttribute("stats", stats);

		// 分数线
		TechTestActiveStandard stand = testActiveIndexStandardFacade
				.getJiaoXueFa();
		mode.addAttribute("levelOne", stand.getLevelOne());
		mode.addAttribute("levelTwo", stand.getLevelTwo());

		return "tech/testactive/viewPointLevels";
	}

	/**
	 * 查看线下活动计分总表
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("downloadPointStat")
	public String downloadPointStat(Model mode, @RequestParam("id") String id,
			@RequestParam("userid") String userid, HttpServletResponse response)
	{
		// 当前编辑的学员:::userid

		TechTestActive active = this.activeService.get(id);

		// 已打分的
		List<TechTestActivePoint> users = this.commonService
				.list("from TechTestActivePoint t where t.testActiveId=? and t.userId=?",
						id, userid);

		// 维度编号做为key,多个观察员的打分列表
		Map<String, List<TechTestActivePoint>> points = new HashMap<String, List<TechTestActivePoint>>();
		for (TechTestActivePoint u : users)
		{
			List<TechTestActivePoint> list = points.get(u.getDimension());
			if (list == null)
			{
				list = new ArrayList<TechTestActivePoint>();
				points.put(u.getDimension(), list);
			}

			list.add(u);
		}

		// 维度信息
		List<Row> rows = testActiveFacade.parseDimensionRows(points);
		// 统计维度信息：：：合并了重复项，适合展示的
		List<StatRow> stats = testActiveFacade.parseDimensionStat(active,
				points);

		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition",
				"attachment; filename=stats.xls");

		try
		{
			testActiveReportGenerator.generator(response.getOutputStream(),
					userid, active, rows, stats);
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 开始测试::::显示所有候选人列表， 已答题的不能再继续答题。 如角色为管理员则可继续答题。
	 * 
	 * @param model
	 * @param testActiveId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("beginTest")
	public String beginTest(Model model, @RequestParam("id") String id)
	{
		TechTestActive active = (TechTestActive) this.commonService.get(id,
				TechTestActive.class);
		model.addAttribute("dto", active);

		List<TechTestUser> users = this.commonService.list(
				"from TechTestUser t where t.testActiveId = ?", id);
		for (TechTestUser user : users)
		{// 设定用户名称
			EcanUser u = (EcanUser) cacheDtoViewFacade.get(user.getUserId(),
					EcanUser.class.getName());
			user.setName(u.getName());
		}

		model.addAttribute("list", users);

		return "tech/testactive/beginTest";
	}

	/**
	 * 进入答题界面
	 * 
	 * @param model
	 * @param testActiveId
	 *            测评活动编号
	 * @param activeTitleId
	 *            题目编号，对应的是题库的题目编号,非试卷表题目标号
	 * @param userid
	 *            候选人编号(记录为答题人)
	 * @return
	 */
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test(Model model,
			@RequestParam("testUserId") String testUserId,
			HttpServletRequest request)
	{
		fetchTest(model, testUserId, false);

		return "tech/testactive/test";
	}

	/**
	 * 加载测试试卷
	 * 
	 * @param model
	 * @param testUserId
	 * @param isConfirm
	 *            是否是复核
	 */
	private void fetchTest(Model model, String testUserId, boolean isConfirm)
	{
		TechTestUser testUser = (TechTestUser) this.commonService.get(
				testUserId, TechTestUser.class);
		model.addAttribute("user", testUser);

		TechTestActive active = (TechTestActive) this.commonService.get(
				testUser.getTestActiveId(), TechTestActive.class);
		if (active.getStatus().equals(TechTestActive.STATUS.READY))
		{// 有用户开始测试就更改状态为执行
			active.setStatus(TechTestActive.STATUS.RUNNING);
			this.commonService.updateTX(active);
		}

		model.addAttribute("active", active);

		/**
		 * 是复合或还没有开始测试
		 */
		if (isConfirm
				|| testUser.getStatus().equals(TechTestUser.Status.TO_TEST))
		{// 待测试
			List<TechTestDb> tests = this.activeService.listTestDB(testUser
					.getTestActiveId());

			if (isConfirm)
			{// 如果是复核，查出用户选择的所有答案
				List<TechTestRecord> records = this.commonService
						.list("from TechTestRecord t where t.userId=? and t.testActiveId=?",
								testUser.getUserId(),
								testUser.getTestActiveId());

				Set<String> ansSet = new HashSet<String>();
				for (TechTestRecord r : records)
				{
					ansSet.add(r.getAnswerId());
				}

				for (TechTestDb db : tests)
				{
					for (TechTestAnswer ans : db.getAns())
					{
						if (ansSet.contains(ans.getId()))
						{
							ans.addExts("checked", "checked");
						}
					}
				}
			}
			model.addAttribute("list", tests);
		}
	}

	/**
	 * 提交答题答案:::完成测试
	 * 
	 * @param model
	 * @param testActiveId
	 *            测评活动编号
	 * @param activeTitleId
	 *            题目编号，对应的是题库的题目编号,非试卷表题目标号
	 * @param userid
	 *            候选人编号(记录为答题人)
	 * @return
	 */
	@RequestMapping(value = "test", method = RequestMethod.POST)
	public @ResponseBody
	AjaxResponse testSubmit(Model model,
			@RequestParam("testUserId") String testUserId,
			HttpServletRequest request)
	{
		TechTestUser testUser = (TechTestUser) this.commonService.get(
				testUserId, TechTestUser.class);
		model.addAttribute("user", testUser);

		TechTestActive active = (TechTestActive) this.commonService.get(
				testUser.getTestActiveId(), TechTestActive.class);
		model.addAttribute("active", active);

		// 提交答题记录
		String anss[] = request.getParameterValues("ans");

		// 将所有的题干放置在一个set中
		Set<String> ansSet = new HashSet<String>();

		if (anss != null)
		{
			for (String ans : anss)
			{// 保存答题记录
				String a[] = ans.split("_");
				// a[0]=提干编号　，a[1]=答案编号
				ansSet.add(a[0]);
			}
		}

		List<TechTestDb> list = this.activeService.listTestDB(active.getId());
		for (TechTestDb db : list)
		{
			if (!ansSet.contains(db.getId()))
			{
				return new AjaxResponse(false,
						I18N.parse("i18n.testactive.msg.notAllQuestionsAnsed"));
			}
		}

		this.activeService.saveUserTestAnsTX(active, testUser, anss);

		return new AjaxResponse();
	}

	/**
	 * 进入答题界面:::复核
	 * 
	 * @param model
	 * @param testActiveId
	 *            测评活动编号
	 * @param activeTitleId
	 *            题目编号，对应的是题库的题目编号,非试卷表题目标号
	 * @param userid
	 *            候选人编号(记录为答题人)
	 * @return
	 */
	@RequestMapping(value = "confirm", method = RequestMethod.GET)
	public String confirm(Model model,
			@RequestParam("testUserId") String testUserId,
			HttpServletRequest request)
	{
		this.fetchTest(model, testUserId, true);

		return "tech/testactive/confirm";
	}

	/**
	 * 提交答题答案:::复核
	 * 
	 * @param model
	 * @param testActiveId
	 *            测评活动编号
	 * @param activeTitleId
	 *            题目编号，对应的是题库的题目编号,非试卷表题目标号
	 * @param userid
	 *            候选人编号(记录为答题人)
	 * @return
	 */
	@RequestMapping(value = "confirm", method = RequestMethod.POST)
	public @ResponseBody
	AjaxResponse confirmSubmit(Model model,
			@RequestParam("testUserId") String testUserId,
			HttpServletRequest request)
	{
		return this.testSubmit(model, testUserId, request);
	}

	/**
	 * 查看学员的分课程模块得分情况
	 * 
	 * @param model
	 * @param testUserId
	 * @param request
	 * @return
	 */
	@RequestMapping("viewTestResult")
	public String viewTestResult(Model model, @RequestParam("id") String id,
			@RequestParam("userid") String userid, HttpServletRequest request)
	{
		TechTestActive active = (TechTestActive) this.commonService.get(id,
				TechTestActive.class);
		// 分数线
		TechTestActiveStandard stand = testActiveIndexStandardFacade
				.getZhuanYeKe(active.getProType());

		model.addAttribute("levelOne",
				stand.getLevelOne() == 0 ? 75 : stand.getLevelOne());
		model.addAttribute("levelTwo",
				stand.getLevelTwo() == 0 ? 80 : stand.getLevelTwo());

		List<TechTestUser> users = this.commonService.list(
				"from TechTestUser t where t.userId=? and t.testActiveId=?",
				userid, id);
		TechTestUser testUser = null;
		if (users.size() > 0)
		{
			testUser = users.get(0);
			model.addAttribute("testUser", testUser);
			model.addAttribute("zyk", this.getZhuanyeZhishi(testUser));

		}

		// 查询出该用户的测试记录， 并计算所有课程、模块下的得分
		Map<String, Map<String, Points>> result = this.testActiveFacade
				.viewTestResult(userid, id);

		model.addAttribute("result", result);

		return "tech/testactive/viewTestResult";
	}

	/**
	 * 确认的是得分：：：给用户显示中国和德国积分法的得分，用户选择一个
	 * 
	 * @param testUserId
	 *            测试用户编号（非用户编号）
	 * @param type
	 *            cn/de
	 * @return
	 */
	@RequestMapping(value = "confirmTest", method = RequestMethod.GET)
	public String toConfirmTest(Model model,
			@RequestParam("testUserId") String testUserId)
	{
		TechTestUser testUser = (TechTestUser) this.commonService.get(
				testUserId, TechTestUser.class);

		model.addAttribute("dto", testUser);

		model.addAttribute("zyk", this.getZhuanyeZhishi(testUser));

		return "tech/testactive/confirmTest";
	}

	/**
	 * 查询用户的专业知识部分得分
	 * 
	 * @param testUser
	 * @return
	 */
	private Integer getZhuanyeZhishi(TechTestUser testUser)
	{
		TechTestActive active = (TechTestActive) this.commonService.get(
				testUser.getTestActiveId(), TechTestActive.class);

		EcanDomainDTO domain = this.domainFacade.getDomainMap().get("TESTSTEP");

		Set<String> dims1 = new HashSet<String>();
		Set<String> dims2 = new HashSet<String>();

		for (EcanDomainvalueDTO dv : domain.getValues())
		{
			if (dv.getDomainLevel().length() > 8
					&& dv.getDomainLevel().startsWith("20031005"))
			{// 两个小阶段中的专业知识子项
				dims1.add(dv.getDomainvalue());
			} else
			{
				if (dv.getDomainLevel().length() > 8
						&& dv.getDomainLevel().startsWith("30011005"))
				{
					dims2.add(dv.getDomainvalue());
				}
			}
		}

		Double avg1 = new Double(0);

		if (dims1.size() > 0)
		{
			List<Double> avg1s = this.commonService
					.list("select avg(t.points) from com.ecannetwork.dto.tech.TechTestActivePoint t where t.dimension in ("
							+ JoinHelper.joinToSql(dims1)
							+ ") and testActiveId = ? and t.userId = ? group by t.watchUserId",
							active.getId(), testUser.getUserId());

			System.out.println(avg1s);

			for (Double a : avg1s)
			{
				avg1 += a;
			}

			avg1 /= avg1s.size();
		}

		Double avg2 = new Double(0);

		if (dims2.size() > 0)
		{
			List<Double> avg2s = this.commonService
					.list("select avg(t.points) from com.ecannetwork.dto.tech.TechTestActivePoint t where t.dimension in ("
							+ JoinHelper.joinToSql(dims2)
							+ ") and testActiveId = ? and t.userId = ? group by t.watchUserId",
							active.getId(), testUser.getUserId());
			System.out.println(avg2s);
			for (Double a : avg2s)
			{
				avg2 += a;
			}

			avg2 /= avg2s.size();
		}

		Double avg = new Double(0);

		if (avg1 != 0 && avg2 != 0)
		{
			avg = (avg1 + avg2) / 2;
		} else
		{
			if (avg1 != 0 && avg2 == 0)
			{
				avg = avg1;
			} else
			{
				avg = avg2;
			}
		}

		if (avg.intValue() == 0)
		{
			return 100;
		}

		// 取得指标值
		List<TechTestActivePointIndex> idxs = this.commonService
				.list("from TechTestActivePointIndex t where t.dimension = '1044' and t.proType=?",
						active.getProType());

		if (idxs.size() == 0)
		{
			return 100;
		} else
		{
			avg = avg * 100 / idxs.get(0).getPoint();
			return avg.intValue();
		}
	}

	/**
	 * 确认学员得分
	 * 
	 * @param testUserId
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "confirmTest", method = RequestMethod.POST)
	public @ResponseBody
	AjaxResponse confirmTest(@RequestParam("testUserId") String testUserId,
			@RequestParam("type") String type)
	{
		TechTestUser testUser = (TechTestUser) this.commonService.get(
				testUserId, TechTestUser.class);

		int zyk = this.getZhuanyeZhishi(testUser);

		if ("cn".equals(type))
		{
			testUser.setPoints((testUser.getPointCn() + zyk) / 2);
		} else
		{
			testUser.setPoints((testUser.getPointDe() + zyk) / 2);
		}

		this.commonService.updateTX(testUser);

		boolean finAll = true;
		// 检查所有人员都测试完成了
		TechTestActive active = (TechTestActive) this.activeService
				.get(testUser.getTestActiveId());
		for (TechTestUser u : active.getUsers())
		{
			if (!u.getStatus().equals(TechTestUser.Status.SUBMITTED))
			{
				finAll = false;
				break;
			}
		}

		if (finAll)
		{// 都完成了，则更改状态
			active.setFinTest(YORN.YES);
			if (active.getFinAddPoint().equals(YORN.YES))
			{
				active.setStatus(TechTestActive.STATUS.FIN);
			}
		}
		this.commonService.updateTX(active);

		return new AjaxResponse();
	}

	/**
	 * 添加评语
	 * 
	 * @param model
	 * @param id
	 *            活动编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "feedback", method = RequestMethod.GET)
	public String addFeedback(Model model, @RequestParam("id") String id,
			@RequestParam(value = "userid", required = false) String userid)
	{
		TechTestActive active = this.activeService.get(id);
		model.addAttribute("dto", active);

		if (StringUtils.isNotBlank(userid))
		{
			// 当前编辑的学员
			model.addAttribute("curUserid", userid);

			List<TechTestActiveFeedback> list = this.commonService
					.list("from TechTestActiveFeedback t where t.testActiveId=? and t.userId=?",
							active.getId(), userid);

			for (TechTestActiveFeedback fb : list)
			{
				model.addAttribute("p" + fb.getDimension(), fb);
			}

			List<TechTestRecommend> recommends = this.commonService
					.list("from TechTestRecommend t where t.testActiveId=? and userId=?",
							id, userid);

			model.addAttribute("list", recommends);

		}

		return "tech/testactive/feedback";
	}

	/**
	 * 保存某个学员的评语
	 * 
	 * @param request
	 * @param userid
	 *            用户编号
	 * @return
	 */
	@RequestMapping(value = "feedback", method = RequestMethod.POST)
	public @ResponseBody
	AjaxResponse saveFeedback(@RequestParam("userid") String userid,
			@RequestParam("activeid") String activeid,
			@RequestParam("dim") String[] dim,
			@RequestParam("advantage") String[] advantage,
			@RequestParam("weakness") String[] weakness,
			HttpServletRequest request)
	{
		for (int i = 0; i < dim.length; i++)
		{
			List<TechTestActiveFeedback> fbs = this.commonService
					.list("from TechTestActiveFeedback t where t.testActiveId=? and t.userId=? and t.dimension=?",
							activeid, userid, dim[i]);

			TechTestActiveFeedback fb = null;
			if (fbs.size() > 0)
			{
				fb = fbs.get(0);
			}
			if (fb == null)
			{
				fb = new TechTestActiveFeedback();
				fb.setTestActiveId(activeid);
				fb.setUserId(userid);
				fb.setDimension(dim[i]);
			}
			fb.setAdvantage(advantage[i]);
			fb.setWeakness(weakness[i]);
			this.commonService.saveOrUpdateTX(fb);
		}

		List<TechTestRecommend> recommends = new ArrayList<TechTestRecommend>();

		// 建议课程部分
		Enumeration<String> em = request.getParameterNames();
		while (em.hasMoreElements())
		{
			String name = em.nextElement();
			if (name.startsWith("cs_") && !"cs_".equals(name))
			{
				String[] names = name.split("_");
				if (names.length > 1)
				{
					String courseId = names[1];
					String moduleId = names.length > 2 ? names[2] : "";

					String values[] = request.getParameterValues(name);
					if (values.length > 0)
					{// 选择重复的只记录一次
						TechTestRecommend r = new TechTestRecommend();
						r.setTrainCourseId(courseId);
						r.setModuleId(moduleId);
						r.setUserId(userid);
						r.setTestActiveId(activeid);
						r.setCourseDays(values[0]);
						recommends.add(r);
					}
				}
			}
		}

		this.commonService.deleteAllTX(
				"from TechTestRecommend t where t.testActiveId=? and userId=?",
				activeid, userid);
		this.commonService.saveTX(recommends);

		return new AjaxResponse();
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse del(@RequestParam("id") String id)
	{
		this.activeService.delActiveTX(id);

		return new AjaxResponse();
	}

	/**
	 * ptp图表
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("ptpImage.jpeg")
	public String ptpImage(Model model, @RequestParam("id") String id,
			@RequestParam("userid") String userid, HttpServletResponse response)
	{
		TechTestActive active = (TechTestActive) this.commonService.get(id,
				TechTestActive.class);
		model.addAttribute("dto", active);

		List<EcanDomainvalueDTO> list = this.domainFacade.getDomainMap()
				.get("TESTSTEP").listChilds(TechConsts.PTP);

		Collections.sort(list, new Comparator<EcanDomainvalueDTO>()
		{// 排序
					public int compare(EcanDomainvalueDTO o1,
							EcanDomainvalueDTO o2)
					{
						return o1.getIndexsequnce().compareTo(
								o2.getIndexsequnce());
					}
				});

		StringBuilder ids = new StringBuilder();
		for (int i = 0; i < list.size(); i++)
		{
			ids.append("'").append(list.get(i).getDomainvalue()).append("'");

			if (i != list.size() - 1)
			{
				ids.append(",");
			}
		}

		List<TechTestActivePoint> points = this.commonService
				.list("from TechTestActivePoint t where t.testActiveId=? and t.userId=? and t.dimension in ("
						+ ids.toString() + ")", id, userid);

		Map<String, Double> pointMap = new HashMap<String, Double>();
		for (TechTestActivePoint p : points)
		{// 构建成map
			pointMap.put(p.getDimension(), p.getPoints());
		}

		List<Point> datas = new ArrayList<Point>();
		for (EcanDomainvalueDTO dv : list)
		{
			Double p = pointMap.get(dv.getDomainvalue());

			if (p == null)
			{
				continue;
			}

			datas.add(new Point(dv.getI18nLabel(), p));
		}

		try
		{
			new PTPChartGenerator(I18N.parse("i18n.testactive.ptpimage.title"),
					I18N.parse("i18n.testactive.ptpimage.y"),
					I18N.parse("i18n.testactive.ptpimage.x"), datas).generator(
					response.getOutputStream(), 500, 500);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ptp图表
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("resultImage")
	public String resultImage(Model model, @RequestParam("id") String id,
			@RequestParam("userid") String userid, HttpServletRequest request,
			HttpServletResponse response)
	{
		List<StatPoint> list = (List<StatPoint>) request.getSession()
				.getAttribute("_statspoints");

		try
		{
			new PointStatChartGenerator(
					I18N.parse("i18n.testactive.resultImage.titleIndex"),
					I18N.parse("i18n.testactive.resultImage.titleUser"),
					I18N.parse("i18n.testactive.resultImage.x"),
					I18N.parse("i18n.testactive.resultImage.y"), list)
					.generator(response.getOutputStream(), 1200, 500);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		request.getSession().removeAttribute("_statspoints");

		return null;
	}

	/**
	 * 查看测评活动报告
	 * 
	 * @param model
	 * @param id
	 * @param userid
	 * @return
	 */
	@RequestMapping("viewReport")
	public String viewReport(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam("userid") String userid)
	{
		TechTestActive active = (TechTestActive) this.activeService.get(id);

		model.addAttribute("dto", active);

		return "tech/testactive/viewReport";
	}
}
