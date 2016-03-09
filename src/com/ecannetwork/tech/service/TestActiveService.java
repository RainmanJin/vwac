package com.ecannetwork.tech.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.db.dao.CommonDAO;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.dto.tech.TechTestActive;
import com.ecannetwork.dto.tech.TechTestActivePoint;
import com.ecannetwork.dto.tech.TechTestAnswer;
import com.ecannetwork.dto.tech.TechTestDb;
import com.ecannetwork.dto.tech.TechTestRecord;
import com.ecannetwork.dto.tech.TechTestUser;

@Service
public class TestActiveService
{
	@Autowired
	private CommonDAO commonDAO;

	/**
	 * 获取一个测评活动:::初始化有观察员和候选人的信息
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public TechTestActive get(String id)
	{
		TechTestActive active = (TechTestActive) this.commonDAO.get(id,
				TechTestActive.class);
		List<TechTestUser> users = this.commonDAO.list(
				"from TechTestUser t where t.testActiveId = ?", id);
		active.setUserids(users);

		return active;
	}

	/**
	 * 删除活动，及活动所有相关记录
	 * 
	 * @param id
	 */
	public void delActiveTX(String id)
	{
		// 候选人
		List<TechTestUser> users = this.commonDAO.list(
				"from TechTestUser t where t.testActiveId = ?", id);
		this.commonDAO.deleteAll(users);

		// 线下评测活动得分记录
		this.commonDAO.deleteAll(this.commonDAO.list(
				"from TechTestActivePoint t where t.testActiveId=?", id));

		// 试卷
		this.commonDAO.deleteAll(this.commonDAO.list(
				"from TechTest t where t.testActiveId=?", id));

		// 答题记录
		this.commonDAO.deleteAll(this.commonDAO.list(
				"from TechTestRecord t where t.testActiveId=?", id));

		// 主表
		TechTestActive active = (TechTestActive) this.commonDAO.get(id,
				TechTestActive.class);
		this.commonDAO.delete(active);
	}

	/**
	 * 保存或更新评分
	 * 
	 * @param id
	 * @param watcherId
	 * @param dimId
	 * @param point
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public AjaxResponse saveOrUpdatePointTX(String id, String watcherId,
			String dimId, Double point, String userid)
	{
		List<TechTestActivePoint> points = this.commonDAO
				.list("from TechTestActivePoint t where t.testActiveId=? and t.dimension=? and t.watchUserId=? and t.userId=?",
						id, dimId, watcherId, userid);

		TechTestActivePoint dto = null;
		if (points.size() > 0)
		{
			if (point == null)
			{
				this.commonDAO.deleteAll(points);
			} else
			{
				dto = points.get(0);
				dto.setPoints(point);
				this.commonDAO.update(dto);
			}
		} else
		{
			if (point != null)
			{
				dto = new TechTestActivePoint();

				dto.setTestActiveId(id);
				dto.setDimension(dimId);
				dto.setWatchUserId(watcherId);
				dto.setUserId(userid);
				dto.setPoints(point);
				this.commonDAO.save(dto);
			}
		}

		return new AjaxResponse();
	}

	/**
	 * 获取一个活动的试卷:::试卷中包含了答案列表
	 * 
	 * @param testActiveId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TechTestDb> listTestDB(String testActiveId)
	{
		List<TechTestDb> list = this.commonDAO
				.list("select a from TechTestDb a, TechTest b where a.id=b.testAcviceTitleId and b.testActiveId =?",
						testActiveId);

		if (list.size() > 0)
		{
			Map<String, TechTestDb> dbMap = new HashMap<String, TechTestDb>();

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < list.size(); i++)
			{
				TechTestDb db = list.get(i);
				sb.append("'").append(db.getId()).append("'");
				dbMap.put(db.getId(), db);
				if (i != list.size() - 1)
				{
					sb.append(",");
				}
			}

			List<TechTestAnswer> anss = this.commonDAO
					.list("from TechTestAnswer t where t.testingId in ("
							+ sb.toString() + ")");

			for (TechTestAnswer ans : anss)
			{
				dbMap.get(ans.getTestingId()).getAns().add(ans);
			}
		}

		return list;
	}

	/**
	 * 保存答题记录并计算得分
	 * 
	 * @param active
	 * @param id
	 * @param anss
	 */
	public void saveUserTestAnsTX(TechTestActive active, TechTestUser testUser,
			String[] anss)
	{
		// 删除已有的答题记录:::复核时用到
		List<TechTestRecord> records = this.commonDAO
				.list("from TechTestRecord t where t.testActiveId = ? and t.userId = ?",
						active.getId(), testUser.getUserId());
		this.commonDAO.deleteAll(records);

		// 将所有答案放置在set中：：：所有答案的编号均不一致uuid
		Set<String> ansSet = new HashSet<String>();

		if (anss != null)
		{
			for (String ans : anss)
			{// 保存答题记录
				String a[] = ans.split("_");
				// a[0]=提干编号　，a[1]=答案编号
				ansSet.add(a[1]);

				TechTestRecord record = new TechTestRecord(
						testUser.getUserId(), active.getId(), a[0], a[1], null);

				this.commonDAO.save(record);
			}
		}

		// 计分
		// 题目表
		List<TechTestDb> list = this.listTestDB(active.getId());

		Double totalCN = new Double(list.size());

		Double totalDE = new Double(0);
		Double pointCN = new Double(0);
		Double pointDE = new Double(0);

		/**
		 * <pre>
		 * 新的计算方式是针对Option选项(德国计分制)记分的。
		 * 
		 * 规则如下：
		 * 
		 * 1.     每道题有几个正确答案则该题就有几分，如4个选项有3个是正确的，则这道题的满分就是3分
		 * 
		 * 2.     选对一个正确答案得1分(+1)，选择一个错误答案扣1分(-1)，没有选择正确答案扣1分(-1)，没有选择错误答案不得分（0分）
		 * 
		 * 3.     每道题目最后得分如果小于0，则该题目的最后得分为0分。
		 * 
		 * 4.     最后计算百分比时，则是每道题的得分汇总后，除以每道题满分的汇总
		 * 
		 * 例如，一个题目有4个选项，3个正确答案
		 * 
		 * 情形1： 4个选项全选，最终得分是3 (选了3个正确答案)-1(选了一个错误答案)= 2分
		 * 
		 * 情形2： 选择了3个正确答案， 最终得分是3分
		 * 
		 * 情形3： 选择了2个正确答案，最终得分是2（2个正确答案）-1（有一个正确答案没有选）= 1分
		 * 
		 * 情形4： 选择了2个正确答案，1个错误答案， 最终得分是2（2个正确答案）-1（有一个正确答案没有选）-1（选了一个错误答案）= 0分
		 * 
		 * 情形5： 选择了1个正确答案，1个错误答案，最终得分是1（1个正确答案）-1（选了一个错误答案）-2（有2个正确答案没有选）= 0分（因为-2分小于0）
		 * </pre>
		 */
		for (TechTestDb db : list)
		{// 循环每一道题
			Double pointDeThis = new Double(0);

			boolean error = false;
			for (TechTestAnswer ans : db.getAns())
			{
				if (CoreConsts.YORN.YES.equals(ans.getIsRight()))
				{// 是正确答案
					totalDE++;// 德国积分制有多少个正确答案就有多少分，所以这里总分增加一分

					if (ansSet.contains(ans.getId()))
					{// 选择了正确答案
						pointDeThis++;
					} else
					{// 没有选择正确答案
						error = true;// 记录选择错误
						pointDeThis--;// 减分（德国 ）
					}
				} else
				{
					if (ansSet.contains(ans.getId()))
					{// 选择了错误答案
						error = true;// 记录选择错误
						pointDeThis--;// 减分（德国 ）
					} else
					{// 没有选择错误答案
						// pointDE++;//新的计分规则里面没有选择错误答案不得分
					}
				}
			}

			if (pointDeThis > 0)
			{
				pointDE += pointDeThis;
			}

			if (!error)
			{// 没有答错的选项
				pointCN++;
			}
		}

		System.out.println("pointDE:" + pointDE + "\ttotal:" + totalDE);
		System.out.println("pointCN:" + pointDE + "\ttotal:" + totalDE);

		// 中国100分制
		Double cn6 = Double.valueOf(dft.format(pointCN * 100 / totalCN));
		// 德国100分制
		Double de6 = Double.valueOf(dft.format(pointDE * 100 / totalDE));

		testUser.setPointCn(cn6);
		testUser.setPointDe(de6);
		this.commonDAO.update(testUser);
	}

	private DecimalFormat dft = new DecimalFormat("####.##");
}
