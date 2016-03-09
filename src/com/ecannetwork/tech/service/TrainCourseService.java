package com.ecannetwork.tech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecannetwork.core.module.db.dao.CommonDAO;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechTeacherTrainCourse;
import com.ecannetwork.dto.tech.TechTrainCourse;

/**
 * 培训课程管理。
 * 
 * @author liulibin
 * 
 */
@Service
public class TrainCourseService
{
	@Autowired
	private CommonDAO commonDAO;

	/**
	 * 列举讲师可以管理的课程
	 * 
	 * @param teacherId
	 * @return
	 */
	public List<TechTrainCourse> listByTeacher(String teacherId)
	{
		List<TechTrainCourse> courses = this.commonDAO
				.list(
						"select c from TechTrainCourse c, TechTeacherTrainCourse t where c.id = t.trainCourseId and t.teacherId=?",
						teacherId);

		return courses;
	}

	/**
	 * 列举可上某个培训课程的讲师
	 * 
	 * @param trainCourseId
	 * @return
	 */
	public List<EcanUser> listByCourse(String trainCourseId)
	{
		List<EcanUser> users = this.commonDAO
				.list(
						"select u from EcanUser u, TechTeacherTrainCourse t where u.id = t.teacherId and t.trainCourseId=?",
						trainCourseId);

		return users;
	}

	/**
	 * 删除某个讲师与某个课程的关联关系
	 * 
	 * @param teacherId
	 * @param trainCourseId
	 */
	public void deleteTeacherTrainCourseTX(String teacherId,
			String trainCourseId)
	{
		List<TechTeacherTrainCourse> tcc = this.commonDAO
				.list(
						"from TechTeacherTrainCourse t where t.teacherId=? and t.trainCourseId=?",
						teacherId, trainCourseId);
		this.commonDAO.deleteAll(tcc);
	}
}
