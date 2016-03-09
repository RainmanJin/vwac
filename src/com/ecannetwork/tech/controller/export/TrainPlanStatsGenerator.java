package com.ecannetwork.tech.controller.export;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.dto.tech.TechTrainPlan;

@Component
public class TrainPlanStatsGenerator
{
	@Autowired
	private CommonService commonService;

	public boolean generator(String template, OutputStream out, String year,
			String brand, String course)
	{
		List<TechTrainPlan> plans = this.commonService
				.list("from com.ecannetwork.dto.tech.TechTrainPlan t where t.trainId=? and t.brand=? and t.yearValue = ?",
						course, brand, year);

		return true;
	}
}

class PlanWeeks
{
	private Date date;

	private Integer tmdPlan;// 学员数量*课程天数
	private Integer numberOfCoursePlan;// 课程次数（1）
	private Integer participantsPlan;// 学员数量
	private Integer dayPlan;// 培训天数

	private Integer tmd;
	private Integer numberOfCourse;
	private Integer participants;
	private Integer day;

}