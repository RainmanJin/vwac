package com.ecannetwork.tech.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.db.dao.CommonDAO;
import com.ecannetwork.core.module.service.ServiceSupport;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.tech.TechCourse;
import com.ecannetwork.dto.tech.TechCourseAttchment;
import com.ecannetwork.dto.tech.TechCourseItem;
import com.ecannetwork.dto.tech.TechCourseTesting;
import com.ecannetwork.dto.tech.TechCouseInstance;
import com.ecannetwork.dto.tech.TechScormPkg;
import com.ecannetwork.tech.dao.ScormDAO;
import com.ecannetwork.tech.scorm.Item;
import com.ecannetwork.tech.scorm.Organization;
import com.ecannetwork.tech.scorm.Organizations;

/**
 * 负责解析scorm包， 解压缩、解析、保存等工作
 * 
 * @author leo
 * 
 */
@Service
public class ScormCorseService extends ServiceSupport
{
	/**
	 * 获取课程信息包完整信息，内含章节信息
	 * 
	 * @param id
	 * @return
	 */
	public TechCourse getCourseWithItems(String id)
	{
		TechCourse course = this.scormDAO.get(id);
		if (course != null)
		{
			List<TechCourseItem> items = this.scormDAO.listItems(id);
			course.setItems(items);
		}
		return course;
	}

	/**
	 * 保存课件信息到数据库
	 * 
	 * @param orgs
	 * @param courseId
	 */
	public TechCourse saveToDatabaseTX(Organizations orgs, String courseId,
			TechScormPkg scormPkg)
	{
		TechCourse course = new TechCourse();
		course.setName(scormPkg.getName());
		course.setStatus(CoreConsts.YORN.NO);
		// course.setControl(TechConsts.scormPlayControlType.nav);
		course.setId(courseId);
		course.setCreateTime(new Date());
		course.setCreateUser(ExecuteContext.user().getId());

		// copy properties
		course.setScormId(scormPkg.getId());
		course.setBrand(scormPkg.getBrand());
		course.setProType(scormPkg.getProType());
		course.setPreview(scormPkg.getPreview());
		course.setRemark(scormPkg.getRemark());
		course.setContentType(scormPkg.getContentType());

		this.scormDAO.save(course);

		for (Organization org : orgs.getOrgnizations())
		{// 保存课件章节信息
			int idx = 100;
			for (Item item : org.getItems())
			{
				saveItem(item, courseId, String.valueOf(idx), 1);

				idx++;
			}
		}
		return course;
	}

	private void saveItem(Item item, String courseId, String idx, Integer level)
	{
		TechCourseItem ci = new TechCourseItem();
		ci.setCourseId(courseId);
		ci.setIndentify(item.getIdentifier());
		ci.setTitle(item.getTitle());
		ci.setIdx(this.fixLevel(idx, level));
		ci.setThelevel(level);

		if (item.getResource() != null)
		{
			ci.setType(item.getResource().getType());
			ci.setLanunch(item.getResource().getHref());
		}

		if (item.getItems() != null)
		{
			int idx2 = 100;
			for (Item it : item.getItems())
			{
				saveItem(it, courseId, idx + idx2, level + 1);

				idx2++;
			}
		}
		// if (true)
		// {
		// throw new RuntimeException();
		// }
		this.scormDAO.save(ci);
	}

	/**
	 * 三个一级，最多6级
	 * 
	 * @param idx
	 * @param level
	 * @return
	 */
	private String fixLevel(String idx, Integer level)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(idx);
		for (int i = idx.length(); i < 18; i++)
		{
			sb.append("0");
		}

		return sb.toString();
	}

	/**
	 * 删除课件
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public AjaxResponse deleteCourseTX(String id)
	{
		List<TechCouseInstance> course = this.commonDAO.list(
				"from TechCouseInstance t where t.courseId=?", id);
		if (course.size() > 0)
		{
			return new AjaxResponse(false, I18N
					.parse("i18n.course.msg.delInstancedCourse"));
		} else
		{
			// 删除课件附件
			List<TechCourseAttchment> attchs = this.commonDAO.list(
					"from TechCourseAttchment t where t.courseId=?", id);

			// 删除附件
			for (TechCourseAttchment attch : attchs)
			{
				File file = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
						+ attch.getUrl());
				FileUtils.deleteQuietly(file);
			}

			this.commonDAO.deleteAll(attchs);

			// 删除章节信息
			List<TechCourseItem> items = this.commonDAO.list(
					"from TechCourseItem t where t.courseId = ?", id);
			this.commonDAO.deleteAll(items);

			// 删除测试题
			List<TechCourseTesting> testting = this.commonDAO.list(
					"from TechCourseTesting t where t.courseId = ?", id);
			this.commonDAO.deleteAll(testting);

			//删除发布的文件
			File file = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH + "/tech/upload/scorm/" + id);
			FileUtils.deleteQuietly(file);
			
			this.commonDAO.delete(TechCourse.class, id);

			return new AjaxResponse();
		}

	}

	@Autowired
	private ScormDAO scormDAO;

	@Autowired
	private CommonDAO commonDAO;
}