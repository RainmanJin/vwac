package com.ecannetwork.tech.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechCourseTesting;
import com.ecannetwork.dto.tech.TechCourseTestingDb;
import com.ecannetwork.dto.tech.TechCouseInstance;
import com.ecannetwork.dto.tech.TechStudentStatus;
import com.ecannetwork.dto.tech.TechTestDb;

/**
 * 首页
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping("/home")
public class HomeController
{

	@Autowired
	private CommonService commonService;

	@SuppressWarnings("unchecked")
	@RequestMapping("student")
	public String student(Model model)
	{
		EcanUser user = ExecuteContext.user();
		@SuppressWarnings("unused")
		// 查出学员未完成的课程 isFinish 0是未完成 1是已经完成
		List<TechStudentStatus> students = commonService.list(
				"from TechStudentStatus t where t.studentId=?", user.getId());

		// 查出全部进行中的课程
		List<TechCouseInstance> courseInstance = commonService
				.list("from TechCouseInstance t where t.status=? and t.expireTime > ?",
						CoreConsts.COURSESTATUS.ING, new Date());

		List<TechCouseInstance> courseTmp = new ArrayList<TechCouseInstance>();
		// 根据学院的全部课程替掉非进行中的课程
		Set<String> studentSet = new HashSet<String>();
		for (TechStudentStatus student : students)
		{
			studentSet.add(student.getCourseInstanceId());
		}

		for (Iterator iterator = courseInstance.iterator(); iterator.hasNext();)
		{
			TechCouseInstance techCouseInstance = (TechCouseInstance) iterator
					.next();
			if (studentSet.contains(techCouseInstance.getId()))
			{
				if (CoreConsts.COURSESTATUS.ING.equals(techCouseInstance
						.getStatus()))
				{
					List<TechCourseTesting> courseTesingList = commonService
							.list("from TechCourseTesting t where t.courseId=?",
									techCouseInstance.getCourseId());
					if (courseTesingList != null && courseTesingList.size() > 0)
					{
						techCouseInstance.setHasDbs(true);
					}
					courseTmp.add(techCouseInstance);
				}
			}

		}
		// // 查出进行中的课程是否有测试题

		// for (TechStudentStatus techCouseInstanceStudentStauts : students)
		// {
		// String courseId = techCouseInstanceStudentStauts
		// .getCourseInstance().getCourseId();
		// if (courseId != null)
		// {
		// @SuppressWarnings("unused")
		// List<TechCourseTesting> courseTesingList = commonService
		// .list("from TechCourseTesting t where t.courseId=?",
		// courseId);
		// if (courseTesingList != null && courseTesingList.size() > 0)
		// {
		// techCouseInstanceStudentStauts.getCourseInstance()
		// .setHasDbs(true);
		// }
		// }
		// }
		model.addAttribute("courseTmp", courseTmp);
		model.addAttribute("user", user);
		return getRealHome("student");
	}

	/**
	 * 
	 * 教师首页
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("teacher")
	public String teacher(Model model)
	{
		EcanUser user = ExecuteContext.user();
		// 查出当前教师状态非停用、未过期的课程
		List<TechCouseInstance> inglist = commonService.list(
				"from TechCouseInstance t where t.teacher=? and t.status<>?",
				user.getId(), CoreConsts.COURSESTATUS.STOP);

		// 每个课程学员个数及情况
		for (TechCouseInstance techCouseInstance : inglist)
		{

			Integer studentCount = (commonService.list(
					"from TechStudentStatus t where t.courseInstanceId=?",
					techCouseInstance.getId())).size();
			techCouseInstance.setStudentCount(studentCount);
			if (techCouseInstance.getExpireTime() != null
					&& techCouseInstance.getExpireTime().compareTo(new Date()) > 0)
			{
				techCouseInstance.setCourseStatus(CoreConsts.COURSESTATUS.ING);
			} else
			{
				techCouseInstance
						.setCourseStatus(CoreConsts.COURSESTATUS.EXPIRE);
			}
		}

		model.addAttribute("inglist", inglist);
		model.addAttribute("user", user);
		return getRealHome("teacher");
	}

	/**
	 * 课程及测试概览 用户概览
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("operman")
	public String operman(Model model)
	{
		EcanUser user = ExecuteContext.user();
		// 获取全部的课程
		List<TechCouseInstance> techCourseList = commonService
				.list(TechCouseInstance.class);
		// 获取课后测试的题目数量
		List<TechTestDb> testList = commonService.list(TechTestDb.class);
		// 获取在线测试的题目数量
		List<TechCourseTestingDb> testtingList = commonService
				.list(TechCourseTestingDb.class);
		// 激活用户的数量
		List<EcanUser> activelist = commonService.list(
				"from EcanUser t where t.status=?", EcanUser.STATUS.ACTIVE);
		// 全部用户数量
		List<EcanUser> userlist = commonService.list(EcanUser.class);

		model.addAttribute("courseCount", techCourseList.size());
		model.addAttribute("testListSize", testList.size());
		model.addAttribute("testtingListSize", testtingList.size());
		model.addAttribute("activelistSize", activelist.size());
		model.addAttribute("userlistSize", userlist.size());
		model.addAttribute("user", user);
		return getRealHome("operman");
	}

	/**
	 * 
	 * 
	 * @param model
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("admin")
	public String admin(Model model)
	{

		EcanUser user = ExecuteContext.user();
		// 获取全部的课程
		List<TechCouseInstance> techCourseList = commonService
				.list(TechCouseInstance.class);
		// 获取课后测试的题目数量
		List<TechTestDb> testList = commonService.list(TechTestDb.class);
		// 获取在线测试的题目数量
		List<TechCourseTestingDb> testtingList = commonService
				.list(TechCourseTestingDb.class);
		// 激活用户的数量
		List<EcanUser> activelist = commonService.list(
				"from EcanUser t where t.status=?", EcanUser.STATUS.ACTIVE);
		// 全部用户数量
		List<EcanUser> userlist = commonService.list(EcanUser.class);

		model.addAttribute("courseCount", techCourseList.size());
		model.addAttribute("testListSize", testList.size());
		model.addAttribute("testtingListSize", testtingList.size());
		model.addAttribute("activelistSize", activelist.size());
		model.addAttribute("userlistSize", userlist.size());
		model.addAttribute("user", user);

		return getRealHome("admin");
	}

	public String getRealHome(String roleHomeUrl)
	{
		EcanUser user = ExecuteContext.user();
		if (user == null)
		{
			return "redirect:/p/login";
		} else
		{
			if (user.getRole().getHomeUrl().equals(roleHomeUrl))
				return "tech/home/" + user.getRole().getHomeUrl();
			else
			{
				return "redirect:/techc/home/" + user.getRole().getHomeUrl();
			}
		}
	}

	// resadm
	// planadm

	/**
	 * 计划管理员
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("planadm")
	public String planadm(Model model)
	{
		EcanUser user = ExecuteContext.user();

		model.addAttribute("user", user);
		return getRealHome("planadm");
	}

	/**
	 * 资源管理员
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("resadm")
	public String resadm(Model model)
	{
		EcanUser user = ExecuteContext.user();

		model.addAttribute("user", user);
		return getRealHome("resadm");
	}
}
