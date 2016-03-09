package com.ecannetwork.tech.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecannetwork.core.module.db.dao.CommonDAO;
import com.ecannetwork.core.module.service.ServiceSupport;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.dto.tech.CourseStudyReportStatus;
import com.ecannetwork.dto.tech.TechCouseInstance;
import com.ecannetwork.dto.tech.TechStudentStatus;

@Service
public class CourseStudyReportService extends ServiceSupport
{
	@Autowired
	private CommonDAO commonDAO;
	private static DecimalFormat decimalFormat = new DecimalFormat("####.##");

	// private

	@SuppressWarnings("unchecked")
	public List<TechCouseInstance> queryStudentStatus(String userid)
	{
		List<TechCouseInstance> courseList = null;
		if (userid != null && !"".equals(userid))
		{
			courseList = commonDAO
			        .list(
			                "from TechCouseInstance t where t.teacher = ? and t.status<>? and t.expireTime <?",
			                userid, CoreConsts.COURSESTATUS.STOP, new Date());
		} else
		{
			courseList = commonDAO
			        .list(
			                "from TechCouseInstance t where t.status<>? and t.expireTime <?",
			                CoreConsts.COURSESTATUS.STOP, new Date());
		}

		StringBuilder sb = new StringBuilder();
		if (courseList != null && courseList.size() > 0)
		{
			int i = 0;
			for (TechCouseInstance techCouseInstance : courseList)
			{
				i++;
				sb.append("'").append(techCouseInstance.getId()).append("'");
				if (i != courseList.size())
				{
					sb.append(",");
				}

			}
			List<CourseStudyReportStatus> studentList = commonDAO
			        .list("select  new com.ecannetwork.dto.tech.CourseStudyReportStatus(avg(t.avgPoint), t.courseInstanceId) from TechStudentStatus t where t.id in ("
			                + sb.toString() + ") group by t.courseInstanceId");
			Map<String, String> map = new HashMap<String, String>();
			for (CourseStudyReportStatus courseStudyReportStatus : studentList)
			{
				map.put(courseStudyReportStatus.getCourseInstanceId(),
				        decimalFormat.format(courseStudyReportStatus.getAvg()));
			}
			for (TechCouseInstance techCouseInstance : courseList)
			{
				String avg = map.get(techCouseInstance.getId());
				if (avg != null)
				{
					techCouseInstance.setAvgPoint(avg);
				}
			}
		}
		return courseList;
	}

	/**
	 * 通过课程ID 查出全部的学员以及学习状态
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TechStudentStatus> studentStatusList(String id)
	{
		// List<TechStudentStatus> studentList = commonDAO.list(
		// "from TechStudentStatus t where t.courseInstanceId=?", id);
		//		
		// "select studentid,itemid from tabls where courseinstanceid=? group by
		// student";
		return null;
	}
}