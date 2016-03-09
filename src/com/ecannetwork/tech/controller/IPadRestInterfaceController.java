package com.ecannetwork.tech.controller;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.app.domain.DomainFacade;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.facade.TemplateFacade;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.CoreConsts.YORN;
import com.ecannetwork.core.util.DateUtils;
import com.ecannetwork.core.util.FileUploadHelper;
import com.ecannetwork.core.util.JoinHelper;
import com.ecannetwork.core.util.JsonFactory;
import com.ecannetwork.core.util.MD5;
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechMdttAns;
import com.ecannetwork.dto.tech.TechMdttG7CompetitorsDTO;
import com.ecannetwork.dto.tech.TechMdttNotes;
import com.ecannetwork.dto.tech.TechMdttPkg;
import com.ecannetwork.dto.tech.TechMdttQA;
import com.ecannetwork.dto.tech.TechMdttQustion;
import com.ecannetwork.dto.tech.TechMdttUnLock;
import com.ecannetwork.dto.tech.TechTrainCourse;
import com.ecannetwork.dto.tech.TechTrainPlan;
import com.ecannetwork.dto.tech.TechTrainPlanUser;
import com.ecannetwork.tech.controller.bean.G7StatDetailBean;
import com.ecannetwork.tech.controller.bean.RestResponse;
import com.ecannetwork.tech.controller.bean.RestUserInfo;
import com.ecannetwork.tech.controller.bean.TrainPlan;
import com.ecannetwork.tech.util.TechConsts;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@RequestMapping("iPadRest")
@Controller
public class IPadRestInterfaceController
{
	@Autowired
	private CommonService commonService;
	@Autowired
	private DomainFacade domainFacade;
	@Autowired
	private TemplateFacade templateFacade;

	/**
	 * 登陆
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public @ResponseBody
	RestResponse login(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam(value = "version", required = false) Integer version)
	{
		if (version == null || version < Configs.getInt("iPad.app.version"))
		{// 我们已发布新版本， 请访问如下链接安装最新版本
			RestResponse resp = new RestResponse(403, "version.mustupdate");
			resp.setData(Configs.get("iPad.app.url"));
			return resp;
		}

		RestResponse resp = this.validateUser(username, password);
		if (resp.success())
		{
			EcanUser user = (EcanUser) resp.getData();

			RestUserInfo ru = new RestUserInfo();
			ru.setId(user.getId());
			ru.setUserName(user.getLoginName());
			ru.setRealName(user.getName());
			ru.setHeadImgURL(user.getHeadImg());
			ru.setDeptName(getDomainLabel("POSITION", user.getPosition(), null));
			ru.setRoleName(getDomainLabel("ROLE", user.getRoleId(), null));
			ru.setRoleID(user.getRoleId());
			resp.setData(ru);
		} else
		{
			resp.setData(null);
		}
		return resp;
	}

	private RestResponse validateUser(String username, String password)
	{
		EcanUser user = (EcanUser) this.commonService.listOnlyObject(
				"from EcanUser t where t.loginName = ?", username); //username
		

		if (user == null)
		{
			return RestResponse.authedFailedWithErrorUserIDOrPasswd();
		} else
		{
			String md5Passwd = MD5.encode(user.getLoginPasswd());
			if (!StringUtils.equals(password, md5Passwd))
			{
				return RestResponse.authedFailedWithErrorUserIDOrPasswd();
			} else
			{
				if (!StringUtils.equals(user.getStatus(),
						EcanUser.STATUS.ACTIVE))
				{
					return RestResponse.authedFailedWithUserStatus();
				} else
				{
					user.setLoginPasswd(null);

					return RestResponse.success(user);
				}
			}
		}
		

	}

	private String getDomainLabel(String domain, String domainValue, String lang)
	{
		String label = domainFacade.getDomainMap(/* lang */).get(domain)
				.getByValue(domainValue).getDomainlabel();
		return label;
	}

	/**
	 * 用户的排期信息
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("schedule")
	public @ResponseBody
	RestResponse schedule(@RequestHeader("username") String username,
			@RequestHeader("password") String password)
	{
		RestResponse resp = this.validateUser(username, password);
		if (resp.success())
		{
			EcanUser user = (EcanUser) resp.getData();

			// 查询出所有的排期计划
			List<TechTrainPlan> plans = this.commonService
					.list("select p from TechTrainPlan p, TechTrainPlanUser u where u.userId = ? and p.id = u.planId",
							user.getId());
			List<TrainPlan> plansResult = new ArrayList<TrainPlan>(plans.size());

			if (plans.size() > 0)
			{
				// 查询课程
				Set<String> idSet = new HashSet<String>();
				for (TechTrainPlan p : plans)
				{
					idSet.add(p.getTrainId());
				}
				String ids = JoinHelper.joinToSql(idSet);
				List<TechTrainCourse> courses = this.commonService
						.list("from TechTrainCourse t where t.id in (" + ids
								+ ")");

				// 查询课件包
				idSet.clear();
				for (TechTrainPlan p : plans)
				{
					idSet.add(p.getId());
				}
				ids = JoinHelper.joinToSql(idSet);
				List<TechMdttPkg> pkgs = this.commonService.list(
						"from TechMdttPkg t where t.status=? and t.trianPlanID in ("
								+ ids + ")", YORN.YES);
				Map<String, TechMdttPkg> pkgsMap = new HashMap<String, TechMdttPkg>();
				for (TechMdttPkg pkg : pkgs)
				{
					pkgsMap.put(pkg.getTrianPlanID(), pkg);
				}

				// 组装返回值
				Map<String, TechTrainCourse> courseMap = new HashMap<String, TechTrainCourse>();
				for (TechTrainCourse c : courses)
				{
					courseMap.put(c.getId(), c);
				}

				//
				for (TechTrainPlan p : plans)
				{// 显示所有排期， 领域取课程领域的值，没有课件包的排期pkgID==null

					TrainPlan plan = new TrainPlan();

					TechTrainCourse c = courseMap.get(p.getTrainId());
					plan.setId(p.getId());
					plan.setCourseName(c.getName());
					plan.setRemark(p.getRemark());
					plan.setProType(c.getProType());
					plansResult.add(plan);

					TechMdttPkg pkg = pkgsMap.get(p.getId());

					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, Integer.valueOf(p.getYearValue()));
					cal.set(Calendar.WEEK_OF_YEAR, p.getPlanWeek());
					String[] days = p.getPlanDays().split(",");
					for (String d : days)
					{
						cal.set(Calendar.DAY_OF_WEEK, Integer.valueOf(d));
						plan.getPlanDates().add(
								DateUtils.formartYMD(cal.getTime()));
					}

					if (c != null && pkg != null)
					{// 只显示有课程包的
						plan.setPkgID(pkg.getId());
					}
				}
			}

			resp.setData(plansResult);
		} else
		{
			resp.setData(null);
		}
		return resp;
	}

	@RequestMapping("scheduleStudents")
	@SuppressWarnings("unchecked")
	public @ResponseBody
	RestResponse scheduleStudents(@RequestHeader("username") String username,
			@RequestHeader("password") String password,
			@RequestParam("planID") String planID)
	{
		RestResponse resp = this.validateUser(username, password);

		if (resp.success())
		{
			List<EcanUser> students = this.commonService
					.list("select u from TechTrainPlanUser t, EcanUser u where t.planId = ? and t.userType = ? and u.id = t.userId",
							planID, TechTrainPlanUser.UserType.STUDENT);

			List<Map<String, String>> studentsResp = new ArrayList<Map<String, String>>();
			for (EcanUser u : students)
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", u.getName());

				String dept = getDomainLabel("POSITION", u.getPosition(), null);
				String company = getDomainLabel("COMPANY", u.getCompany(), null);
				map.put("dept", dept);
				map.put("company", company);
				studentsResp.add(map);
			}
			resp.setData(studentsResp);
		} else
		{
			resp.setData(null);
		}
		return resp;
	}

	/**
	 * 初始化问题
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("ans")
	public String ans(
			@RequestParam("uid") String uid,
			@RequestParam("pkgID") String pkgID,
			@RequestParam("menuID") String menuID,// /
			@RequestParam("qID") String qID, @RequestParam("ans") String ans,
			HttpServletRequest request, HttpServletResponse response)
	{
		if (qID.startsWith("g7Comp"))
		{
			g7CompetitorsSubmit(uid, ans, response);
		} else if ("isT".equals(qID))
		{
			isTeacherRequest(uid, response);

		} else if ("Stat".equals(qID))
		{
			g7StatInner(uid, response);
		} else if ("Detail".equals(qID))
		{
			g7StatDetail(uid, response);
		} else if ("StatS".equals(qID))
		{
			g7StatSInner(uid, response);
		} else if ("StatK".equals(qID))
		{
			g7StatKInner(uid, response);
		} else
		{

			commonAnsRequest(uid, pkgID, menuID, qID, ans, response);
		}

		return null;
	}

	private void g7StatSInner(String uid, HttpServletResponse response)
	{
		List<List<Integer>> statTypes = new ArrayList<List<Integer>>();
		List<Integer> s = new ArrayList<Integer>();
		s.add(1);
		statTypes.add(s);
		s = new ArrayList<Integer>();
		s.add(2);
		statTypes.add(s);
		s = new ArrayList<Integer>();
		s.add(3);
		statTypes.add(s);
		s = new ArrayList<Integer>();
		s.add(4);
		statTypes.add(s);
		s = new ArrayList<Integer>();
		s.add(5);
		statTypes.add(s);
		s = new ArrayList<Integer>();
		s.add(6);
		statTypes.add(s);

		List<TechMdttG7CompetitorsDTO> myList = this.commonService
				.list("from TechMdttG7CompetitorsDTO t where t.userid=? order by carid, qid",
						uid);
		List<TechMdttG7CompetitorsDTO> allList = this.commonService
				.list("from TechMdttG7CompetitorsDTO t order by carid, qid");

		Map<String, List<List<Double>>> map = new HashMap<String, List<List<Double>>>();

		// 初始化结构::Map<题目, Map<车, 问题得分序列平均值>
		Map<Integer, Map<Integer, Double>> mineMap = g7ProcessToStatMap(myList);
		Map<Integer, Map<Integer, Double>> avgMap = g7ProcessToStatMap(allList);

		// List[多个维度[多个车的试题平均值]]
		List<List<Double>> myStat = new ArrayList<List<Double>>();
		g7StatCalculator(statTypes, mineMap, myStat);
		map.put("mine", myStat);

		List<List<Double>> avgStat = new ArrayList<List<Double>>();
		g7StatCalculator(statTypes, avgMap, avgStat);
		map.put("avg", avgStat);

		try
		{
			response.getWriter().write(
					"ajaxStatSCallback(" + JsonFactory.toJson(map) + ");");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void g7StatKInner(String uid, HttpServletResponse response)
	{
		List<List<Integer>> statTypes = new ArrayList<List<Integer>>();
		List<Integer> s = new ArrayList<Integer>();
		s.add(54);
		statTypes.add(s);
		s = new ArrayList<Integer>();
		s.add(55);
		statTypes.add(s);
		s = new ArrayList<Integer>();
		s.add(56);
		statTypes.add(s);
		s = new ArrayList<Integer>();
		s.add(57);
		statTypes.add(s);
		s = new ArrayList<Integer>();
		s.add(58);
		statTypes.add(s);

		List<TechMdttG7CompetitorsDTO> myList = this.commonService
				.list("from TechMdttG7CompetitorsDTO t where t.userid=? order by carid, qid",
						uid);
		List<TechMdttG7CompetitorsDTO> allList = this.commonService
				.list("from TechMdttG7CompetitorsDTO t order by carid, qid");

		Map<String, List<List<Double>>> map = new HashMap<String, List<List<Double>>>();

		// 初始化结构::Map<题目, Map<车, 问题得分序列平均值>
		Map<Integer, Map<Integer, Double>> mineMap = g7ProcessToStatMap(myList);
		Map<Integer, Map<Integer, Double>> avgMap = g7ProcessToStatMap(allList);

		// List[多个维度[多个车的试题平均值]]
		List<List<Double>> myStat = new ArrayList<List<Double>>();
		g7StatCalculator(statTypes, mineMap, myStat);
		map.put("mine", myStat);

		List<List<Double>> avgStat = new ArrayList<List<Double>>();
		g7StatCalculator(statTypes, avgMap, avgStat);
		map.put("avg", avgStat);

		try
		{
			response.getWriter().write(
					"ajaxStatKCallback(" + JsonFactory.toJson(map) + ");");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void g7StatDetail(String uid, HttpServletResponse response)
	{

		List<TechMdttG7CompetitorsDTO> myList = this.commonService
				.list("from TechMdttG7CompetitorsDTO t where t.userid=? order by carid, qid",
						uid);
		List<TechMdttG7CompetitorsDTO> allList = this.commonService
				.list("from TechMdttG7CompetitorsDTO t order by carid, qid");

		Map<String, List<List<Double>>> map = new HashMap<String, List<List<Double>>>();

		// 初始化结构::Map<题目, Map<车, 问题得分序列平均值>
		Map<Integer, Map<Integer, Double>> mineMap = g7ProcessToStatMap(myList);
		Map<Integer, Map<Integer, Double>> avgMap = g7ProcessToStatMap(allList);

		// List[多个维度[多个车的试题平均值]]
		List<List<Double>> myStat = new ArrayList<List<Double>>();
		for (Integer qid : mineMap.keySet())
		{
			Map<Integer, Double> carScoreMap = mineMap.get(qid);
			List<Double> carsScore = new ArrayList<Double>();
			for (Integer carid : carScoreMap.keySet())
			{
				Double score = carScoreMap.get(carid);
				carsScore.add(score);
			}
			myStat.add(carsScore);
		}
		map.put("mine", myStat);

		List<List<Double>> avgStat = new ArrayList<List<Double>>();
		for (Integer qid : avgMap.keySet())
		{
			Map<Integer, Double> carScoreMap = avgMap.get(qid);
			List<Double> carsScore = new ArrayList<Double>();
			for (Integer carid : carScoreMap.keySet())
			{
				Double score = carScoreMap.get(carid);
				carsScore.add(score);
			}
			avgStat.add(carsScore);
		}
		map.put("avg", avgStat);

		try
		{
			response.getWriter().write(
					"ajaxStatDetailCallback(" + JsonFactory.toJson(map) + ");");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void g7StatInner(String uid, HttpServletResponse response)
	{
		List<List<Integer>> statTypes = getG7StatInnerList();

		List<TechMdttG7CompetitorsDTO> myList = this.commonService
				.list("from TechMdttG7CompetitorsDTO t where t.userid=? order by carid, qid",
						uid);
		List<TechMdttG7CompetitorsDTO> allList = this.commonService
				.list("from TechMdttG7CompetitorsDTO t order by carid, qid");

		Map<String, List<List<Double>>> map = new HashMap<String, List<List<Double>>>();

		// 初始化结构::Map<题目, Map<车, 问题得分序列平均值>
		Map<Integer, Map<Integer, Double>> mineMap = g7ProcessToStatMap(myList);
		Map<Integer, Map<Integer, Double>> avgMap = g7ProcessToStatMap(allList);

		// List[多个维度[多个车的试题平均值]]
		List<List<Double>> myStat = new ArrayList<List<Double>>();
		g7StatCalculator(statTypes, mineMap, myStat);
		map.put("mine", myStat);

		List<List<Double>> avgStat = new ArrayList<List<Double>>();
		g7StatCalculator(statTypes, avgMap, avgStat);
		map.put("avg", avgStat);

		try
		{
			response.getWriter().write(
					"ajaxStatCallback(" + JsonFactory.toJson(map) + ");");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void g7StatCalculator(List<List<Integer>> statTypes,
			Map<Integer, Map<Integer, Double>> mineMap,
			List<List<Double>> myStat)
	{
		for (int i = 0; i < statTypes.size(); i++)
		{// 循环每个维度需要统计的试题列表清单
			List<Integer> types = statTypes.get(i);

			List<Integer> qustionsList = statTypes.get(i);

			List<Double> stat = new ArrayList<Double>();

			// 维度中所有问题对应每个车的得分
			Map<Integer, Double> allCarsScoreMap = new TreeMap<Integer, Double>();

			for (Integer qid : qustionsList)
			{// 当个问题的得分平均数
				Map<Integer, Double> carsScoreMap = mineMap.get(qid);
				if (carsScoreMap != null)
				{
					for (Integer carid : carsScoreMap.keySet())
					{
						Double score = carsScoreMap.get(carid);
						Double totalScore = allCarsScoreMap.get(carid);
						if (totalScore == null)
						{
							totalScore = score;
						} else
						{
							totalScore += score;
						}
						allCarsScoreMap.put(carid, totalScore);
					}
				}
			}

			for (Integer carid : allCarsScoreMap.keySet())
			{
				Double score = allCarsScoreMap.get(carid);
				score = score / types.size();
				stat.add(score);
			}

			myStat.add(stat);
		}
	}

	private Map<Integer, Map<Integer, Double>> g7ProcessToStatMap(
			List<TechMdttG7CompetitorsDTO> list)
	{
		// 初始化结构::Map<车, Map<题号, Map<人,得分>>
		Map<Integer, Map<Integer, Map<String, Integer>>> tmpMap = new HashMap<Integer, Map<Integer, Map<String, Integer>>>();

		for (TechMdttG7CompetitorsDTO m : list)
		{
			Map<Integer, Map<String, Integer>> quesMap = tmpMap.get(m
					.getCarid());
			if (quesMap == null)
			{// 车
				quesMap = new TreeMap<Integer, Map<String, Integer>>();
				tmpMap.put(m.getCarid(), quesMap);
			}

			Map<String, Integer> personMap = quesMap.get(m.getQid());
			if (personMap == null)
			{// 题目
				personMap = new TreeMap<String, Integer>();
				quesMap.put(m.getQid(), personMap);
			}

			personMap.put(m.getUserid(), m.getValue());
		}

		// 初始化结构::Map<题目, Map<车, 问题得分序列平均值>
		Map<Integer, Map<Integer, Double>> map = new TreeMap<Integer, Map<Integer, Double>>();
		for (Integer carid : tmpMap.keySet())
		{
			Map<Integer, Map<String, Integer>> quesMap = tmpMap.get(carid);
			for (Integer qid : quesMap.keySet())
			{
				Map<String, Integer> personMap = quesMap.get(qid);
				Double score = Double.valueOf(0);// 当前车每个问题的平均分
				for (Integer v : personMap.values())
				{
					score += v;
				}
				score = score / personMap.size();

				// 当前问题的每辆车的得分平均值记录
				Map<Integer, Double> tmp1 = map.get(qid);
				if (tmp1 == null)
				{
					tmp1 = new HashMap<Integer, Double>();
					map.put(qid, tmp1);
				}
				tmp1.put(carid, score);
			}
		}

		return map;
	}

	private List<List<Integer>> getG7StatInnerList()
	{
		List<List<Integer>> list = new ArrayList<List<Integer>>();

		List<Integer> s1 = new ArrayList<Integer>();
		// "[16, 20, 21, 24, 33],"
		s1.add(16);
		list.add(s1);

		s1 = new ArrayList<Integer>();
		// "[7, 32, 37, 43, 44, 45, 50, 51, 52, 53],"
		s1.add(7);
		s1.add(11);
		s1.add(32);
		s1.add(37);
		s1.add(45);
		s1.add(51);
		s1.add(52);
		s1.add(53);
		list.add(s1);

		s1 = new ArrayList<Integer>();
		// "[17, 18, 46, 47, 48],"
		s1.add(43);
		s1.add(44);
		list.add(s1);

		s1 = new ArrayList<Integer>();
		// "[14, 34, 35, 49],"
		s1.add(14);
		s1.add(34);
		s1.add(35);
		s1.add(49);
		list.add(s1);

		s1 = new ArrayList<Integer>();
		// "[8, 9, 10, 22, 23],"
		s1.add(8);
		s1.add(22);
		s1.add(23);
		list.add(s1);

		s1 = new ArrayList<Integer>();
		// "[13, 19, 26, 41]]"
		s1.add(18);
		s1.add(19);
		s1.add(26);
		list.add(s1);

		return list;
	}

	private void commonAnsRequest(String uid, String pkgID, String menuID,
			String qID, String ans, HttpServletResponse response)
	{
		TechMdttQustion qustion = (TechMdttQustion) this.commonService.get(qID,
				TechMdttQustion.class);
		if (qustion != null)
		{
			// 删除之前的答题记录
			this.commonService
					.deleteAllTX(
							"from TechMdttAns t where t.questionID = ? and t.userID = ?",
							qID, uid);

			if (qustion.getQusType().equals(TechMdttQustion.QustionType.RADIO))
			{// Radio
				TechMdttAns dto = new TechMdttAns();
				dto.setAnsTime(new Date());
				dto.setOpt(ans);
				dto.setPkgID(pkgID);
				dto.setQuestionID(qID);
				dto.setUserID(uid);
				dto.setMenuID(menuID);
				this.commonService.saveTX(dto);

			} else if (qustion.getQusType().equals(
					TechMdttQustion.QustionType.CHECKBOX))
			{// Checkbox

				String[] opts = ans.split(",");
				for (String opt : opts)
				{
					if (StringUtils.isNotBlank(opt))
					{
						TechMdttAns dto = new TechMdttAns();
						dto.setAnsTime(new Date());
						dto.setOpt(opt);
						dto.setPkgID(pkgID);
						dto.setQuestionID(qID);
						dto.setUserID(uid);
						dto.setMenuID(menuID);

						this.commonService.saveTX(dto);
					}
				}

			} else
			{// Input
				TechMdttAns dto = new TechMdttAns();
				dto.setAnsTime(new Date());
				dto.setOpt(ans);
				dto.setPkgID(pkgID);
				dto.setQuestionID(qID);
				dto.setUserID(uid);
				dto.setMenuID(menuID);

				this.commonService.saveTX(dto);
			}
		}

		try
		{
			response.getWriter().write(
					this.templateFacade.process(new HashMap<String, Object>(),
							"iPad/iPadAnsResult.ftl"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void isTeacherRequest(String qID, HttpServletResponse response)
	{
		EcanUser u = (EcanUser) this.commonService.get(qID, EcanUser.class);
		boolean isTeacher = false;
		if (u != null && u.getRoleId().equals("teacher"))
		{
			isTeacher = true;
		}

		try
		{
			response.getWriter().write(
					"ajaxIsCurrentTeacherCallback(" + isTeacher + ")");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void g7CompetitorsSubmit(String uid, String ans,
			HttpServletResponse response)
	{
		int step = -1;

		String respString = "";
		if (StringUtils.isNotBlank(ans))
		{

			List<TechMdttG7CompetitorsDTO> list = new ArrayList<TechMdttG7CompetitorsDTO>();

			String anss[] = ans.split("\\|");
			for (int carIndex = 0; carIndex < anss.length; carIndex++)
			{
				String values[] = anss[carIndex].split(",");
				if (values.length == 6)
				{// 是外观
					step = 1;
				} else if (values.length == 5)
				{
					step = 3;
				} else
				{
					step = 2;
				}

				for (int qustionIndex = 0; qustionIndex < values.length; qustionIndex++)
				{
					Integer value = Integer.valueOf(values[qustionIndex]);

					TechMdttG7CompetitorsDTO dto = new TechMdttG7CompetitorsDTO();
					dto.setUserid(uid);
					dto.setCarid(carIndex + 1);
					dto.setValue(value);
					switch (step)
					{
					case 1:
						dto.setQid(qustionIndex + 1);
						break;
					case 2:
						dto.setQid(qustionIndex + 7);
						break;
					case 3:
						dto.setQid(qustionIndex + 54);
						break;
					default:
						break;
					}
					dto.setId(dto.getUserid() + "_" + dto.getCarid() + "_"
							+ dto.getQid());

					list.add(dto);
				}
			}

			if (list.size() > 0)
			{
				this.commonService.saveOrUpdateTX(list);
			}

			respString = "submitSuccess" + step + "()";
		}
		try
		{
			response.getWriter().write(respString);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@RequestMapping("ansStat")
	public String ansStat(@RequestParam("pkgID") String pkgID,
			@RequestParam("menuID") String menuID,// /
			HttpServletRequest request, HttpServletResponse response)
	{
		List<TechMdttAns> ansList = this.commonService.list(
				"from TechMdttAns t where t.pkgID = ? and t.menuID = ?", pkgID,
				menuID);

		Set<String> qusSet = new HashSet<String>();
		for (TechMdttAns ans : ansList)
		{
			qusSet.add(ans.getQuestionID());
		}

		Map<String, Object> model = new HashMap<String, Object>();

		if (qusSet.size() > 0)
		{

			List<TechMdttQustion> qusList = this.commonService
					.list("from TechMdttQustion t where t.id in ("
							+ JoinHelper.joinToSql(qusSet) + ")");

			Map<String, TechMdttQustion> qusMap = new HashMap<String, TechMdttQustion>();
			for (TechMdttQustion qus : qusList)
			{// 构建成map
				qusMap.put(qus.getId(), qus);
			}

			for (TechMdttAns ans : ansList)
			{
				TechMdttQustion q = qusMap.get(ans.getQuestionID());
				if (q != null)
				{
					q.addAns(ans);
					// 设置用户名
					EcanUser u = (EcanUser) commonService.get(ans.getUserID(),
							EcanUser.class);
					ans.setUserID(u.getName());
				}
			}

			if (qusList.size() > 0)
				model.put("qusList", qusList);
		}

		try
		{
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(
					this.templateFacade.process(model, "iPad/iPadAnsStat.ftl"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * QA清单
	 * 
	 * @param brand
	 * @param proType
	 * @param key
	 * @return
	 */
	@RequestMapping("qa")
	public @ResponseBody
	RestResponse qa(
			@RequestParam(value = "version", required = false) Integer version)
	{
		if (version == null)
		{
			version = 0;
		}

		String hql = "from TechMdttQA t where t.versionCode >? and (t.status = ? or t.status = ?) order by t.versionCode desc";
		List<TechMdttQA> list = this.commonService.list(hql, version, YORN.YES,
				"D");
		RestResponse response = RestResponse.success(list);

		if (list.size() > 0)
		{
			response.setVersion(String.valueOf(list.get(0).getVersionCode()));
		}

		return response;
	}

	/**
	 * 提交一个新问题
	 * 
	 * @param proType
	 * @param userid
	 * @return
	 */
	@RequestMapping("newqa")
	public @ResponseBody
	RestResponse newqa(@RequestHeader("username") String username,
			@RequestHeader("password") String password,
			@RequestParam("proType") String proType,
			@RequestParam("userid") String userid,
			@RequestParam("qus") String qus)
	{
		RestResponse resp = this.validateUser(username, password);
		if (resp.success())
		{
			TechMdttQA qa = new TechMdttQA();
			qa.setBrand("VW");
			qa.setLastUpdateTime(new Date());
			qa.setProType(proType);
			qa.setQusUserID(userid);
			qa.setQuestion(qus);
			qa.setStatus("2");
			qa.setviews(1);
			qa.setVersionCode(1);
			this.commonService.saveTX(qa);
		}
		resp.setData(null);
		return resp;
	}
	/**
	 * 更新点击次数
	 * 
	 * @param proType
	 * @param userid
	 * @return
	 */
	
	@RequestMapping("viewqa")
	public @ResponseBody
	RestResponse viewqa(@RequestParam("id") String id)
	{
		TechMdttQA qa = (TechMdttQA) this.commonService.get(id, TechMdttQA.class);
		if(qa!=null)
		{
			RestResponse resp = RestResponse.success(null);
			qa.setviews(qa.getviews()+1);
			qa.setVersionCode(this.newVersion());
			this.commonService.updateTX(qa);
			return resp;
		}
		else
		{
			RestResponse resp=new RestResponse(404,"");
			return resp;
		}
	}

	private synchronized Integer newVersion()
	{
		TechMdttQA qa = (TechMdttQA) this.commonService
				.listOnlyObject("from TechMdttQA t order by t.versionCode desc");
		if (qa != null)
		{
			return qa.getVersionCode() + 1;
		} else
		{
			return 1;
		}
	}
	
	@RequestMapping("pkg")
	public @ResponseBody
	RestResponse pkg(@RequestHeader("username") String username,
			@RequestHeader("password") String password,
			@RequestParam(value = "version", required = false) Integer version,
			@RequestParam(value = "type", required = false) String type)
	{
		RestResponse resp = this.validateUser(username, password);
		if (resp.success())
		{
			if (version == null)
			{
				version = 0;
			}

			String hql = "from TechMdttPkg t where t.versionCode >? order by t.versionCode desc";
			List<TechMdttPkg> list = null;
			
			if(type!=null)
			{
				hql = "from TechMdttPkg t where t.versionCode >? and (t.osType = ? or t.osType = ?) order by t.versionCode desc";
				list=this.commonService.list(hql, version,type,"0");
			}
			else
			{
				list=this.commonService.list(hql, version);
			}

			resp.setData(list);

			if (list.size() > 0)
			{
				resp.setVersion(String.valueOf(list.get(0).getVersionCode()));
			}

		} else
		{
			resp.setData(null);
		}
		return resp;
	}

	@RequestMapping("notes")
	public @ResponseBody
	RestResponse notes(@RequestHeader("username") String username,
			@RequestHeader("password") String password,
			@RequestParam(value = "version", required = false) Integer uid,
			@RequestParam("pkgID") String pkgID,
			@RequestParam("ownerID") String ownerID, HttpServletRequest req)
	{
		RestResponse resp = this.validateUser(username, password);
		if (resp.success())
		{
			if (uid == null)
			{
				uid = 0;
			}

			List<TechMdttNotes> notes = this.commonService
					.list("from TechMdttNotes t where t.ownerID = ? and t.pkgID=? and uid>?",
							ownerID, pkgID, uid);

			for (TechMdttNotes note : notes)
			{// fix to uri
				String contectPath = req.getContextPath();
				if (!contectPath.equals("/"))
				{
					note.setContent(contectPath + note.getContent());
				}
			}

			resp.setData(notes);
		} else
		{
			resp.setData(null);
		}

		return resp;
	}
	RestResponse teachernotes(@RequestHeader("username") String username,
			@RequestHeader("password") String password,
			@RequestParam(value = "version", required = false) Integer uid,
			@RequestParam("pkgID") String pkgID, HttpServletRequest req)
	{
		RestResponse resp = this.validateUser(username, password);
		if (resp.success())
		{
			if (uid == null)
			{
				uid = 0;
			}

			List<TechMdttNotes> notes = this.commonService
					.list("from TechMdttNotes t where t.pkgID=? and uid>?",
							pkgID, uid);

			for (TechMdttNotes note : notes)
			{// fix to uri
				String contectPath = req.getContextPath();
				if (!contectPath.equals("/"))
				{
					note.setContent(contectPath + note.getContent());
				}
			}

			resp.setData(notes);
		} else
		{
			resp.setData(null);
		}

		return resp;
	}

	@RequestMapping("newNote")
	public @ResponseBody
	RestResponse newNote(@RequestHeader("username") String username,
			@RequestHeader("password") String password,
			@RequestParam(value = "version", required = false) Integer uid,
			@RequestParam("pkgID") String pkgID,
			@RequestParam("menuID") String menuID,
			@RequestParam("contentType") String contentType,
			@RequestParam("content") String content,
			@RequestParam("ownerID") String ownerID, HttpServletRequest req)
	{
		RestResponse resp = this.validateUser(username, password);
		if (resp.success())
		{
			TechMdttNotes note = new TechMdttNotes();
			note.setPkgID(pkgID);
			note.setMenuID(menuID);
			note.setContentType(contentType);
			note.setOwnerID(ownerID);
			note.setCreateTime(new Date());

			if ("img".equals(contentType))
			{// 图片
				String filePathString = File.separator
						+ TechConsts.UPLOAD_IPAD_NOTES_IMG + File.separator
						+ UUID.randomUUID();

				String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
						+ filePathString;

				List<String> exts = new ArrayList<String>();
				exts.add("jpg");
				AjaxResponse response = FileUploadHelper.upload(req,
						storeFileNameWithPath + "_orign", "image", exts, false);

				if (response.isSuccess())
				{
					note.setContent(filePathString);

					// 生成预览图
					this.createThumbnail(storeFileNameWithPath + "_orign.jpg",
							236, 150, 100, storeFileNameWithPath + ".jpg");
				}
			} else
			{// 文本
				note.setContent(content);
			}

			if (uid != null && uid.intValue() > 0)
			{
				note.setUid(uid);
				this.commonService.updateTX(note);
			} else
			{
				this.commonService.saveTX(note);
			}

			resp.setData(note.getUid());
		} else
		{
			resp.setData(null);
		}
		return resp;
	}

	@RequestMapping("delNote")
	public @ResponseBody
	RestResponse delNote(@RequestHeader("username") String username,
			@RequestHeader("password") String password,
			@RequestParam(value = "version", required = false) Integer uid)
	{
		RestResponse resp = this.validateUser(username, password);
		if (resp.success())
		{
			this.commonService.deleteAllTX(
					"from TechMdttNotes t where t.uid=?", uid);
		} else
		{
			resp.setData(null);
		}
		return resp;
	}

	void createThumbnail(String filename, int thumbWidth, int thumbHeight,
			int quality, String outFilename)
	{
		try
		{

			// load image from filename
			Image image = Toolkit.getDefaultToolkit().getImage(filename);
			MediaTracker mediaTracker = new MediaTracker(new Container());
			mediaTracker.addImage(image, 0);
			mediaTracker.waitForID(0);
			// use this to test for errors at this point:
			// System.out.println(mediaTracker.isErrorAny());
			// determine thumbnail size from WIDTH and HEIGHT
			double thumbRatio = (double) thumbWidth / (double) thumbHeight;
			int imageWidth = image.getWidth(null);
			int imageHeight = image.getHeight(null);
			double imageRatio = (double) imageWidth / (double) imageHeight;
			if (thumbRatio < imageRatio)
			{
				thumbHeight = (int) (thumbWidth / imageRatio);
			} else
			{
				thumbWidth = (int) (thumbHeight * imageRatio);
			}

			// draw original image to thumbnail image object and
			// scale it to the new size on-the-fly
			BufferedImage thumbImage = new BufferedImage(thumbWidth,
					thumbHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);

			// save thumbnail image to outFilename
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(outFilename));
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder
					.getDefaultJPEGEncodeParam(thumbImage);
			quality = Math.max(0, Math.min(quality, 100));
			param.setQuality((float) quality / 100.0f, false);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(thumbImage);
			out.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 列举某个培训的某个课件的解锁列表
	 * 
	 * @param username
	 * @param password
	 * @param pkgID
	 * @param planID
	 * @return
	 */
	@RequestMapping("unlocks")
	public @ResponseBody
	RestResponse unlocks(@RequestHeader("username") String username,
			@RequestHeader("password") String password,
			@RequestParam(value = "pkgID") String pkgID,
			@RequestParam(value = "planID") String planID)
	{
		RestResponse resp = this.validateUser(username, password);
		if (resp.success())
		{
			List<TechMdttUnLock> unlocks = this.commonService.list(
					"from TechMdttUnLock t where t.pkgID=? and t.planID=?",
					pkgID, planID);
			if (unlocks != null)
			{
				Set<String> menuIDSet = new HashSet<String>();
				for (TechMdttUnLock lock : unlocks)
				{
					menuIDSet.add(lock.getMenuID());
				}

				resp.setData(menuIDSet);
			}
		} else
		{
			resp.setData(null);
		}
		return resp;
	}

	/**
	 * 解锁
	 * 
	 * @param username
	 * @param password
	 * @param pkgID
	 * @param menuID
	 * @param planID
	 * @return
	 */
	@RequestMapping(value = "unlock", method = RequestMethod.POST)
	public @ResponseBody
	RestResponse unlock(@RequestHeader("username") String username,
			@RequestHeader("password") String password,
			@RequestParam(value = "pkgID") String pkgID,
			@RequestParam(value = "menuID") String menuID,
			@RequestParam(value = "planID") String planID)
	{
		RestResponse resp = this.validateUser(username, password);
		if (resp.success())
		{
			this.commonService
					.deleteAllTX(
							"from TechMdttUnLock t where t.pkgID=? and t.planID=? and t.menuID=?",
							pkgID, planID, menuID);

			TechMdttUnLock unlock = new TechMdttUnLock();
			unlock.setMenuID(menuID);
			unlock.setPkgID(pkgID);
			unlock.setPlanID(planID);
			unlock.setUpdateTime(new Date());

			this.commonService.saveTX(unlock);
		} else
		{
			resp.setData(null);
		}
		return resp;
	}

	@RequestMapping(value = "lock", method = RequestMethod.POST)
	public @ResponseBody
	RestResponse lock(@RequestHeader("username") String username,
			@RequestHeader("password") String password,
			@RequestParam(value = "pkgID") String pkgID,
			@RequestParam(value = "menuID") String menuID,
			@RequestParam(value = "planID") String planID)
	{
		RestResponse resp = this.validateUser(username, password);
		if (resp.success())
		{
			this.commonService
					.deleteAllTX(
							"from TechMdttUnLock t where t.pkgID=? and t.planID=? and t.menuID=?",
							pkgID, planID, menuID);
		} else
		{
			resp.setData(null);
		}
		return resp;
	}

	/** to be delete **/
	@RequestMapping("teststatinner")
	public @ResponseBody
	Object teststatinner(HttpServletResponse response)
	{
		String hql = "select new com.ecannetwork.tech.controller.bean.G7StatDetailBean(avg(value), qid, carid) from TechMdttG7CompetitorsDTO group by qid, carid";

		List<G7StatDetailBean> beans = this.commonService.list(hql);
		// Map<Qid, Map<Carid, Avg>>
		Map<Integer, Map<Integer, Double>> statMap = new HashMap<Integer, Map<Integer, Double>>();
		for (G7StatDetailBean bean : beans)
		{
			Map<Integer, Double> carsMap = statMap.get(bean.getQid());
			if (carsMap == null)
			{
				carsMap = new HashMap<Integer, Double>();
				statMap.put(bean.getQid(), carsMap);
			}
			carsMap.put(bean.getCarid(), bean.getAvg());
		}

		StringBuilder sb = new StringBuilder();

		List<List<Integer>> statTypes = getG7StatInnerList();
		for (List<Integer> lst : statTypes)
		{
			for (Integer qid : lst)
			{
				Map<Integer, Double> carsMap = statMap.get(qid);
				sb.append(qid).append(",");
				for (int carId = 0; carId < 6; carId++)
				{
					sb.append(carsMap.get(carId+1)).append(",");
				}
				sb.append("\n");
				System.out.println(carsMap);
			}
		}

		try
		{
			FileWriter fw = new FileWriter(new File("/Users/leo/temp/s.csv"));
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return sb;
	}

	/** to be delete **/
	@RequestMapping("teststat")
	public @ResponseBody
	Object teststat(HttpServletResponse response)
	{
		String[] ids = new String[]
		{ "ff808081407fa090014081c3d2890001",
				"ff808081407fa090014081c43ea70002",
				"ff808081407fa090014081c4a99d0003",
				"ff808081407fa090014081c519010004",
				"ff808081407fa090014081c572d10005",
				"ff808081407fa090014081c5dd4a0006" };

		List<List<List<Double>>> list = new ArrayList<List<List<Double>>>();
		List<List<Double>> avgStat = null;
		for (int i = 0; i < ids.length; i++)
		{
			String uid = ids[i];

			List<List<Integer>> statTypes = getG7StatInnerList();

			List<TechMdttG7CompetitorsDTO> myList = this.commonService
					.list("from TechMdttG7CompetitorsDTO t where t.userid=? order by carid, qid",
							uid);
			List<TechMdttG7CompetitorsDTO> allList = this.commonService
					.list("from TechMdttG7CompetitorsDTO t order by carid, qid");

			Map<String, List<List<Double>>> map = new HashMap<String, List<List<Double>>>();

			// 初始化结构::Map<题目, Map<车, 问题得分序列平均值>
			Map<Integer, Map<Integer, Double>> mineMap = g7ProcessToStatMap(myList);
			Map<Integer, Map<Integer, Double>> avgMap = g7ProcessToStatMap(allList);

			// List[多个维度[多个车的试题平均值]]
			List<List<Double>> myStat = new ArrayList<List<Double>>();
			g7StatCalculator(statTypes, mineMap, myStat);
			list.add(myStat);

			if (avgStat == null)
			{
				avgStat = new ArrayList<List<Double>>();
				g7StatCalculator(statTypes, avgMap, avgStat);
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int qid = 0; qid < 6; qid++)
		{// 题号
			sb.append(qid).append(",");

			for (int carid = 0; carid < 6; carid++)
			{// 车号
				for (int u = 0; u < 6; u++)
				{// 用户
					sb.append(list.get(u).get(qid).get(carid));
					sb.append(",");
				}
				sb.append(avgStat.get(qid).get(carid)).append(",");
			}
			sb.append("\n");
		}

		try
		{
			FileWriter fw = new FileWriter(new File("/Users/leo/temp/s.csv"));
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return sb;
	}

	@RequestMapping("teststats")
	public @ResponseBody
	Object teststats(HttpServletResponse response)
	{
		String[] ids = new String[]
		{ "ff808081407fa090014081c3d2890001",
				"ff808081407fa090014081c43ea70002",
				"ff808081407fa090014081c4a99d0003",
				"ff808081407fa090014081c519010004",
				"ff808081407fa090014081c572d10005",
				"ff808081407fa090014081c5dd4a0006" };

		List<List<List<Double>>> list = new ArrayList<List<List<Double>>>();
		List<List<Double>> avgStat = null;
		for (int i = 0; i < ids.length; i++)
		{
			String uid = ids[i];
			List<List<Integer>> statTypes = new ArrayList<List<Integer>>();
			List<Integer> s = new ArrayList<Integer>();
			s.add(1);
			statTypes.add(s);
			s = new ArrayList<Integer>();
			s.add(2);
			statTypes.add(s);
			s = new ArrayList<Integer>();
			s.add(3);
			statTypes.add(s);
			s = new ArrayList<Integer>();
			s.add(4);
			statTypes.add(s);
			s = new ArrayList<Integer>();
			s.add(5);
			statTypes.add(s);
			s = new ArrayList<Integer>();
			s.add(6);
			statTypes.add(s);

			List<TechMdttG7CompetitorsDTO> myList = this.commonService
					.list("from TechMdttG7CompetitorsDTO t where t.userid=? order by carid, qid",
							uid);
			List<TechMdttG7CompetitorsDTO> allList = this.commonService
					.list("from TechMdttG7CompetitorsDTO t order by carid, qid");

			Map<String, List<List<Double>>> map = new HashMap<String, List<List<Double>>>();

			// 初始化结构::Map<题目, Map<车, 问题得分序列平均值>
			Map<Integer, Map<Integer, Double>> mineMap = g7ProcessToStatMap(myList);
			Map<Integer, Map<Integer, Double>> avgMap = g7ProcessToStatMap(allList);

			// List[多个维度[多个车的试题平均值]]
			List<List<Double>> myStat = new ArrayList<List<Double>>();
			g7StatCalculator(statTypes, mineMap, myStat);
			map.put("mine", myStat);

			list.add(myStat);

			//
			if (avgStat == null)
			{
				avgStat = new ArrayList<List<Double>>();
				g7StatCalculator(statTypes, avgMap, avgStat);
				map.put("avg", avgStat);
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int qid = 0; qid < 6; qid++)
		{// 题号
			sb.append(qid).append(",");

			for (int carid = 0; carid < 6; carid++)
			{// 车号
				for (int u = 0; u < 6; u++)
				{// 用户
					sb.append(list.get(u).get(qid).get(carid));
					sb.append(",");
				}
				sb.append(avgStat.get(qid).get(carid)).append(",");
			}
			sb.append("\n");
		}

		try
		{
			FileWriter fw = new FileWriter(new File("/Users/leo/temp/s.csv"));
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return sb;
	}

	@RequestMapping("teststatk")
	public @ResponseBody
	Object teststatk(HttpServletResponse response)
	{
		String[] ids = new String[]
		{ "ff808081407fa090014081c3d2890001",
				"ff808081407fa090014081c43ea70002",
				"ff808081407fa090014081c4a99d0003",
				"ff808081407fa090014081c519010004",
				"ff808081407fa090014081c572d10005",
				"ff808081407fa090014081c5dd4a0006" };

		List<List<List<Double>>> list = new ArrayList<List<List<Double>>>();
		List<List<Double>> avgStat = null;
		for (int i = 0; i < ids.length; i++)
		{
			String uid = ids[i];

			List<List<Integer>> statTypes = new ArrayList<List<Integer>>();
			List<Integer> s = new ArrayList<Integer>();
			s.add(54);
			statTypes.add(s);
			s = new ArrayList<Integer>();
			s.add(55);
			statTypes.add(s);
			s = new ArrayList<Integer>();
			s.add(56);
			statTypes.add(s);
			s = new ArrayList<Integer>();
			s.add(57);
			statTypes.add(s);
			s = new ArrayList<Integer>();
			s.add(58);
			statTypes.add(s);

			List<TechMdttG7CompetitorsDTO> myList = this.commonService
					.list("from TechMdttG7CompetitorsDTO t where t.userid=? order by carid, qid",
							uid);
			List<TechMdttG7CompetitorsDTO> allList = this.commonService
					.list("from TechMdttG7CompetitorsDTO t order by carid, qid");

			Map<String, List<List<Double>>> map = new HashMap<String, List<List<Double>>>();

			// 初始化结构::Map<题目, Map<车, 问题得分序列平均值>
			Map<Integer, Map<Integer, Double>> mineMap = g7ProcessToStatMap(myList);
			Map<Integer, Map<Integer, Double>> avgMap = g7ProcessToStatMap(allList);

			// List[多个维度[多个车的试题平均值]]
			List<List<Double>> myStat = new ArrayList<List<Double>>();
			g7StatCalculator(statTypes, mineMap, myStat);
			list.add(myStat);

			if (avgStat == null)
			{
				avgStat = new ArrayList<List<Double>>();
				g7StatCalculator(statTypes, avgMap, avgStat);
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int qid = 0; qid < 5; qid++)
		{// 题号
			sb.append(qid).append(",");

			for (int carid = 0; carid < 6; carid++)
			{// 车号
				for (int u = 0; u < 6; u++)
				{// 用户
					sb.append(list.get(u).get(qid).get(carid));
					sb.append(",");
				}
				sb.append(avgStat.get(qid).get(carid)).append(",");
			}
			sb.append("\n");
		}

		try
		{
			FileWriter fw = new FileWriter(new File("/Users/leo/temp/s.csv"));
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return sb;
	}
}
