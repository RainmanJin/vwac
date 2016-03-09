package com.ecannetwork.tech.controller;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechCourseItem;
import com.ecannetwork.dto.tech.TechCouseInstance;
import com.ecannetwork.dto.tech.TechStudentScoitem;
import com.ecannetwork.dto.tech.TechStudentStatus;
import com.ecannetwork.dto.tech.TechTestingInstance;
import com.ecannetwork.tech.service.CourseStudyReportService;
import com.ecannetwork.tech.util.TechConsts;

/**
 * 学习中心：：：讲师,操作员，操作员可以看到所有课程、所有学员的情况
 * 
 * @author think
 * 
 */
@Controller
@RequestMapping("/studyreport")
public class CourseStudyReportController
{
	@Autowired
	private CommonService commonService;
	@Autowired
	private CourseStudyReportService courseStudyReportService;
	private static DecimalFormat decimalFormat = new DecimalFormat("####.##");

	/**
	 * 按时间顺序列举出当前讲师所有的已分派的课程， 时间倒序:::::显示学习情况。<br />
	 * 切割成已完成和未完成两个列表， <br />
	 * 课程是否完成：：过期的就认为完成<br />
	 * 学生是否学习完成：是否点击了（完成并开始测试)<br />
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("courseIndex")
	public String courseIndex(Model model)
	{
		EcanUser user = ExecuteContext.user();
		List<TechCouseInstance> courseList = null;
		List<TechCouseInstance> expCourseList = null;
		if (user.getRoleId().equals(TechConsts.Role.ADMIN))
		{
			courseList = commonService
			        .list(
			                "from TechCouseInstance t where t.status <>? and t.expireTime>?",
			                CoreConsts.YORN.YES, new Date());
			// 查出全部过期的课程
			expCourseList = commonService
			        .list(
			                "from TechCouseInstance t where  t.status <>? and t.expireTime<?",
			                CoreConsts.YORN.YES, new Date());
		} else
		{
			courseList = commonService
			        .list(
			                "from TechCouseInstance t where t.teacher=? and t.status <>? and t.expireTime>?",
			                user.getId(), CoreConsts.YORN.YES, new Date());
			expCourseList = commonService
			        .list(
			                "from TechCouseInstance t where t.teacher=? and t.status <>? and t.expireTime<?",
			                user.getId(), CoreConsts.YORN.YES, new Date());
		}
		// 查出全部没有过期的课程

		for (TechCouseInstance techCouseInstance : courseList)
		{
			List<TechStudentStatus> studentList = commonService.list(
			        "from TechStudentStatus t where t.courseInstanceId=?",
			        techCouseInstance.getId());
			// 当前课程下面所有学员的学习状态
			Double pro = new Double(0);
			Integer is_FinishCount = 0;
			for (TechStudentStatus techStudentStatus : studentList)
			{
				if (techStudentStatus != null
				        && techStudentStatus.getStudentCoursePro() != null
				        && !techStudentStatus.getStudentCoursePro().equals(
				                new Double(0)))
				{
					is_FinishCount++;
				}
				if (techStudentStatus != null
				        && techStudentStatus.getStudentCoursePro() != null)
				{
					pro = pro + techStudentStatus.getStudentCoursePro();
				}
			}
			techCouseInstance.setNoFinishCount(studentList.size()
			        - is_FinishCount);
			techCouseInstance.setProgress(decimalFormat.format(pro
			        / studentList.size()));
			techCouseInstance.setStudentList(studentList);
		}

		for (TechCouseInstance techCouseInstance : expCourseList)
		{
			List<TechStudentStatus> studentList = commonService.list(
			        "from TechStudentStatus t where t.courseInstanceId=?",
			        techCouseInstance.getId());
			// 当前课程下面所有学员的学习状态
			Double pro = new Double(0);
			Integer isFinishCount = 0;
			for (TechStudentStatus techStudentStatus : studentList)
			{
				if (techStudentStatus != null)
				{
					if (CoreConsts.YORN.YES.equals(techStudentStatus
					        .getIsFinish()))
					{
						isFinishCount++;
					}
					if (techStudentStatus != null
					        && techStudentStatus.getStudentCoursePro() != null)
					{
						pro = pro + techStudentStatus.getStudentCoursePro();
					}

				}
			}
			techCouseInstance.setNoFinishCount(studentList.size()
			        - isFinishCount);
			if (!pro.equals(new Double(0)))
			{
				techCouseInstance.setProgress(decimalFormat.format(pro) + "%");
			} else
			{
				techCouseInstance.setProgress("0%");
			}

			techCouseInstance.setStudentList(studentList);
		}
		model.addAttribute("courseList", courseList);
		model.addAttribute("courseListSize", courseList.size()
		        + expCourseList.size());
		model.addAttribute("ingListSize", courseList.size());
		model.addAttribute("expCourseList", expCourseList);
		return "tech/studyreport/courseIndex";
	}

	/**
	 * 按时间顺序列举出当前讲师所有的已分派的课程， 时间倒序：：：：显示测试的情况
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("testtingIndex")
	public String testingIndex(Model model)
	{
		EcanUser user = ExecuteContext.user();
		List<TechCouseInstance> courseList = null;
		List<TechCouseInstance> doList = null;
		if (user.getRoleId().equals(TechConsts.Role.ADMIN))
		{
			courseList = commonService
			        .list(
			                "from TechCouseInstance t where t.status<>? and t.expireTime >?",
			                CoreConsts.COURSESTATUS.STOP, new Date());
			doList = courseStudyReportService.queryStudentStatus("");
		} else
		{
			courseList = commonService
			        .list(
			                "from TechCouseInstance t where t.teacher = ? and t.status<>? and t.expireTime >?",
			                user.getId(), CoreConsts.COURSESTATUS.STOP,
			                new Date());
			doList = courseStudyReportService.queryStudentStatus(user.getId());
		}

		for (TechCouseInstance techCouseInstance : courseList)
		{
			Integer isFinishCount = 0;
			Double avg_Point = new Double(0.00);
			List<TechStudentStatus> studentList = commonService.list(
			        "from TechStudentStatus t where t.courseInstanceId=?",
			        techCouseInstance.getId());
			for (TechStudentStatus techStudentStatus : studentList)
			{
				if (CoreConsts.YORN.YES.equals(techStudentStatus.getIsFinish()))
				{
					isFinishCount++;
				}
				if (techStudentStatus != null
				        && techStudentStatus.getAvgPoint() != null)
				{

					avg_Point = avg_Point + (techStudentStatus.getAvgPoint());
				}
			}
			if (avg_Point.equals(new Double(0)) || isFinishCount == 0)
			{
				techCouseInstance.setAvgPoint("0%");
			} else
			{
				techCouseInstance.setAvgPoint(decimalFormat
				        .format((avg_Point / isFinishCount)));
			}
			techCouseInstance.setIsFinishCount(isFinishCount);
			techCouseInstance.setStudentCount(studentList.size());
			techCouseInstance.setNoFinishCount(studentList.size()
			        - isFinishCount);
			Double f_count = new Double(isFinishCount);
			if (f_count != null && !f_count.equals(new Double(0)))
			{
				techCouseInstance.setProgress(decimalFormat
				        .format((f_count / studentList.size()) * 100)
				        + "%");
			} else
			{
				techCouseInstance.setProgress("0%");
			}

		}

		// 正在进行的课程
		model.addAttribute("courseList", courseList);
		model.addAttribute("courseListSize", courseList.size());
		// 已经完成的课程
		model.addAttribute("doList", doList);
		// // 全部分派的课程数量
		model.addAttribute("courseCount", doList.size() + courseList.size());
		return "tech/studyreport/testtingIndex";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("viewstudentstatus")
	public String viewstudentstatus(Model model, @RequestParam(value = "id")
	String id)
	{
		// List<TechStudentStatus> studentList = commonService.list(
		// "from TechStudentStatus t where t.courseInstanceId=?", id);
		List<TechTestingInstance> testingInstanceList = commonService
		        .list(
		                "from TechTestingInstance t where t.courseInstanceId=? order by testingTime desc",
		                id);

		Map<String, TechTestingInstance> map = new TreeMap<String, TechTestingInstance>();
		for (TechTestingInstance techTestingInstance : testingInstanceList)
		{
			if (map.containsKey(techTestingInstance.getStudentId()))
			{
				if (map.get(techTestingInstance.getStudentId())
				        .getTestingTime().before(
				                techTestingInstance.getTestingTime()))
				{
					map.put(techTestingInstance.getStudentId(),
					        techTestingInstance);
				}
			} else
			{
				map
				        .put(techTestingInstance.getStudentId(),
				                techTestingInstance);
			}
		}

		model.addAttribute("list", map);
		return "tech/studyreport/viewstudentstatus";
	}

	/**
	 * 查看学习状态
	 * 
	 * @param model
	 * @param id
	 * @param courseId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("viewstudystatus")
	public String viewStudyStatus(Model model, @RequestParam(value = "id")
	String id, @RequestParam(value = "courseId")
	String courseId)
	{
		List<TechStudentStatus> studentList = commonService.list(
		        "from TechStudentStatus t where t.courseInstanceId=?", id);

		for (TechStudentStatus techStudentStatus : studentList)
		{
			List<TechStudentScoitem> techStudentScoitemList = commonService
			        .list(
			                "from TechStudentScoitem t where t.courseInstanceId=? and t.studentId=? and t.courseId=?",
			                techStudentStatus.getCourseInstanceId(),
			                techStudentStatus.getStudentId(), courseId);
			techStudentStatus.setScoitemList(techStudentScoitemList);
			List<TechCourseItem> items = commonService.list(
			        "from TechCourseItem t where t.courseId=?", courseId);
			techStudentStatus.setScoitemCount(items.size());
			techStudentStatus.setStudentScoitem(techStudentScoitemList.size());

		}

		model.addAttribute("list", studentList);
		return "tech/studyreport/viewstudystatus";
	}

	public static void main(String[] args)
	{

		Double i = new Double(1);
		Integer t = 3;
		String s = decimalFormat.format((i / t) * 100);
		System.out.println(s);

	}

	/**
	 * 查看课程测试学习报告
	 * 
	 * @param model
	 * @param courseInstanceId
	 * @return
	 */
	public String viewReport(Model model, @RequestParam("courseInstanceId")
	String courseInstanceId)
	{

		return null;
	}
}
