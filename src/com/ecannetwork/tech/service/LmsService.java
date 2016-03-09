package com.ecannetwork.tech.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecannetwork.core.module.db.dao.CommonDAO;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.tech.TechLMSRecord;

/**
 * lms接口
 * 
 * @author liulibin
 * 
 */
@Service
public class LmsService
{
	@Autowired
	private CommonDAO commonDAO;

	/**
	 * 获取lms值
	 * 
	 * @param courseInstanceId
	 *            课程编号
	 * @param channelId
	 *            章节编号
	 * @param key
	 *            lmsKey
	 * @param userid
	 *            用户编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getValue(String courseInstanceId, String channelId,
			String key, String userid)
	{
		List<TechLMSRecord> records = (List<TechLMSRecord>) this.commonDAO
				.list(
						"from TechLMSRecord t where t.courseIntanceId = ? and t.channelId=? and t.lmsKey=? and t.userId=?",
						courseInstanceId, channelId, key, userid);

		if (records.size() > 0)
		{
			return records.get(0).getLmsValue();
		} else
		{
			return null;
		}

	}

	/**
	 * 保存lms值
	 * 
	 * @param courseInstanceId
	 *            课程编号
	 * @param channelId
	 *            章节编号
	 * @param key
	 *            lmsKey
	 * @param userid
	 *            用户编号
	 * @param value
	 *            lmsValue
	 */
	public void setValueTX(String courseInstanceId, String channelId,
			String key, String userid, String value)
	{

		List<TechLMSRecord> records = (List<TechLMSRecord>) this.commonDAO
				.list(
						"from TechLMSRecord t where t.courseIntanceId = ? and t.channelId=? and t.lmsKey=? and t.userId=?",
						courseInstanceId, channelId, key, ExecuteContext.user()
								.getId());

		if (records.size() > 0)
		{
			TechLMSRecord record = records.get(0);

			record.setLmsValue(value);
			record.setCreateTime(new Date());
			this.commonDAO.update(record);

		} else
		{
			TechLMSRecord record = new TechLMSRecord();
			record.setCourseIntanceId(courseInstanceId);
			record.setChannelId(channelId);
			record.setUserId(ExecuteContext.user().getId());
			record.setLmsKey(key);
			record.setLmsValue(value);
			record.setCreateTime(new Date());

			this.commonDAO.save(record);

		}
	}
}
