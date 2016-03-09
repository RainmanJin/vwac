package com.ecannetwork.tech.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecannetwork.core.app.domain.DomainFacade;
import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.dto.core.EcanDomainvalueDTO;
import com.ecannetwork.dto.tech.TechTest;
import com.ecannetwork.dto.tech.TechTestActive;
import com.ecannetwork.dto.tech.TechTestActivePoint;
import com.ecannetwork.dto.tech.TechTestActivePointIndex;
import com.ecannetwork.dto.tech.TechTestAnswer;
import com.ecannetwork.dto.tech.TechTestDb;
import com.ecannetwork.dto.tech.TechTestRecord;
import com.ecannetwork.tech.controller.bean.testactive.Points;
import com.ecannetwork.tech.controller.bean.testactive.Row;
import com.ecannetwork.tech.controller.bean.testactive.StatRow;
import com.ecannetwork.tech.service.TestActiveService;
import com.ecannetwork.tech.util.TechConsts;

@Component
public class TestActiveFacade
{
	@Autowired
	private DomainFacade domainFacade;

	@Autowired
	private CommonService commonService;

	@Autowired
	private TestActiveService activeService;

	/**
	 * 将已打分的记录和维度信息构建成层级的row
	 * 
	 * @param points
	 *            观察员编号、维度编号做为key
	 * @return
	 */
	public List<Row> parseDimensionRows(
			Map<String, List<TechTestActivePoint>> points)
	{
		List<Row> rows = new ArrayList<Row>();

		// key 为维度级别编号
		Map<String, Row> temp = new HashMap<String, Row>();

		List<EcanDomainvalueDTO> dvs = this.domainFacade.getDomainMap().get(
				"TESTSTEP").getValuesAsTree();

		for (EcanDomainvalueDTO dv : dvs)
		{
			Row row = new Row();
			row.setDimensionId(dv.getDomainvalue());
			List<TechTestActivePoint> ps = points.get(dv.getDomainvalue());
			if (ps != null)
			{
				row.addPoints(ps);
			}

			if (dv.getLevel() != 1)
			{// 加入到父中
				Row parent = temp.get(dv.getParentLevelCode());
				parent.getChilds().add(row);
			} else
			{
				rows.add(row);
			}

			// 暂存
			temp.put(dv.getDomainLevel(), row);
		}

		return rows;
	}

	/**
	 * 统计视图
	 * 
	 * @param points
	 * @return
	 */
	public List<StatRow> parseDimensionStat(TechTestActive active,
			Map<String, List<TechTestActivePoint>> points)
	{

		List<Row> rows = this.parseDimensionRows(points);

		List<StatRow> srows = new ArrayList<StatRow>();

		// 存储维度索引
		Map<String, StatRow> temp = new HashMap<String, StatRow>();

		for (Row row : rows)
		{// 一级row
			for (Row row2 : row.getChilds())
			{// 二级指标
				EcanDomainvalueDTO dv = this.domainFacade.getDomainMap().get(
						"TESTSTEP").getByValue(row2.getDimensionId());
				String dimName = dv.getI18nLabel();

				StatRow srow = temp.get(dimName);
				if (srow == null)
				{
					srow = new StatRow();
					srows.add(srow);
					temp.put(dimName, srow);
				}

				if (row.getDimensionId().equals(TechConsts.PTP))
				{
					TechTestActivePoint point = row2.getPoints().get("100");
					if (point != null)
					{
						srow.addPoints(row.getDimensionId(), "100", point
								.getPoints());
					}
				} else
				{
					for (String watcher : active.getWatchMenIds())
					{// 循环所有观察员
						// 观察员的多个三级维度的平均得分
						Double d = row2.getWatchMenAvgPoint(watcher);
						if (d != null)
						{
							srow.addPoints(row.getDimensionId(), watcher, d);
						}
					}
				}
				srow.setDimName(dimName);
				srow.setIdx(dv.getIndexsequnce().intValue());
				
				// 标准指标
				srow.setPointIndex(this.getTestActivePointIndex(active
						.getProType(), row2.getDimensionId()));

			}
		}

		Collections.sort(srows, new Comparator<StatRow>()
		{
			@Override
			public int compare(StatRow o1, StatRow o2)
			{
				return o1.getIdx().compareTo(o2.getIdx());
			}
		});

		return srows;
	}

	/**
	 * 生成试卷:::算法:::平均分配到每个课程，然后再按照课程下的模块平均分配
	 * 
	 * @param active
	 */
	@SuppressWarnings("unchecked")
	public AjaxResponse generatorTest(TechTestActive active)
	{// 生成试卷
		// 查询出领域下的所有的测试题目

		List<TechTestDb> list = this.commonService
				.list(
						"select t from TechTestDb t, TechTrainCourse tc where t.proType=? and t.status = ? and t.trainCourseId = tc.id and tc.status=?",
						active.getProType(), CoreConsts.YORN.YES,
						CoreConsts.YORN.YES);

		List<TechTestDb> result = null;
		if (list.size() < active.getTestCount())
		{
			return new AjaxResponse(false, I18N.parse(
					"i18n.testactive.msg.testDBEnough", list.size()));
		} else
		{
			if (list.size() == active.getTestCount())
			{
				result = list;
			} else
			{// 循环每个类别，取出平均数量，不够时在下一个类别补全

				result = new LinkedList<TechTestDb>();

				// 按课程构建成map
				Map<String, Map<String, List<TechTestDb>>> map = new HashMap<String, Map<String, List<TechTestDb>>>();

				for (TechTestDb db : list)
				{// 构建成map，好分组抽取
					Map<String, List<TechTestDb>> courseDbs = map.get(db
							.getTrainCourseId());
					if (courseDbs == null)
					{
						courseDbs = new HashMap<String, List<TechTestDb>>();

						map.put(db.getTrainCourseId(), courseDbs);
					}
					String module = db.getModuleId();
					if (StringUtils.isBlank(module))
					{
						module = "common";
					}

					List<TechTestDb> dbs = courseDbs.get(module);
					if (dbs == null)
					{
						dbs = new ArrayList<TechTestDb>();
						courseDbs.put(module, dbs);
					}

					// 设定随机数，排序后取特定数量就可以确认取出的是随机的了
					db.setRandomIdx(random.nextInt(Integer.MAX_VALUE));
					dbs.add(db);
				}

				int perProTypeCount = active.getTestCount() % map.size() == 0 ? active
						.getTestCount()
						/ map.size()
						: active.getTestCount() / map.size() + 1;

				// 之前的课程中少了几个试题
				int left = 0;

				// 总共还差几个
				int totalLeft = active.getTestCount();

				for (String key : map.keySet())
				{
					// 课程下所有模块的试题
					Map<String, List<TechTestDb>> moduleMap = map.get(key);

					int count = left + perProTypeCount;

					count = count > totalLeft ? totalLeft : count;

					left = this
							.getTestDbFormModuleMap(count, moduleMap, result);

					// 总共剩余多少个
					totalLeft = active.getTestCount() - result.size();
					if (totalLeft <= 0)
					{
						break;
					}
				}
			}
		}

		List<TechTest> tests = new LinkedList<TechTest>();
		for (TechTestDb db : result)
		{// 生成试卷
			TechTest test = new TechTest(active.getId(), db.getId());
			tests.add(test);
		}

		this.commonService.saveTX(tests);

		return new AjaxResponse();
	}

	/**
	 * 某个课程中的习题平均分配
	 * 
	 * @param count
	 * @param moduleMap
	 * @param results
	 * @return
	 */
	private int getTestDbFormModuleMap(int count,
			Map<String, List<TechTestDb>> moduleMap, List<TechTestDb> results)
	{
		int avg = count % moduleMap.size() == 0 ? count / moduleMap.size()
				: count / moduleMap.size() + 1;

		for (List<TechTestDb> dbs : moduleMap.values())
		{
			Collections.sort(dbs, new Comparator<TechTestDb>()
			{// 按随机数排序，确保取到的是随机的
						public int compare(TechTestDb o1, TechTestDb o2)
						{
							return o1.getRandomIdx().compareTo(
									o2.getRandomIdx());
						}
					});

			if (dbs.size() <= avg)
			{// 模块下的试题数量小于平均每个模块需要的试题数量
				if (count > avg)
				{// 总剩余需要获取的试题数量大于该模块下的试题数量
					results.addAll(dbs);
					count -= avg;
				} else
				{// 总剩余要获取的试题数量小于该模块下的试题数量，直接截取模块下的部分试题，返回
					results.addAll(dbs.subList(0, count));
					return 0;
				}
			} else
			{
				if (count > avg)
				{
					results.addAll(dbs.subList(0, avg));
					count -= avg;
				} else
				{
					results.addAll(dbs.subList(0, count));
					return 0;
				}
			}
		}

		return count;
	}

	private static Random random = new Random();

	/**
	 * 获取某个领域的某个维度的标准指标
	 * 
	 * @param proType
	 *            领域编号
	 * @param dim
	 *            指标编码
	 * @return
	 */
	public Double getTestActivePointIndex(String proType, String dim)
	{
		return this.testActivePointIndex.get(proType + "_" + dim);
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init()
	{
		List<TechTestActivePointIndex> idxs = this.commonService
				.list(TechTestActivePointIndex.class);
		for (TechTestActivePointIndex idx : idxs)
		{
			testActivePointIndex.put(idx.getProType() + "_"
					+ idx.getDimension(), idx.getPoint());
		}
	}

	/**
	 * 测评活动标准指标Map<专业编号_维度编号, 指标数值>
	 */
	private Map<String, Double> testActivePointIndex = new HashMap<String, Double>();

	/**
	 * 查看测评结果:::Map<课程，Map<模块,得分>
	 * 
	 * @param testUser
	 * @param active
	 */
	public Map<String, Map<String, Points>> viewTestResult(String userid,
			String testActiveId)
	{
		List<TechTestDb> dbs = this.activeService.listTestDB(testActiveId);

		List<TechTestRecord> records = this.commonService.list(
				"from TechTestRecord t where t.testActiveId=? and t.userId=?",
				testActiveId, userid);

		// 所有的答案编号
		Set<String> ansSet = new HashSet<String>();
		for (TechTestRecord r : records)
		{
			ansSet.add(r.getAnswerId());
		}

		// Map<课程，Map<模块,得分>
		Map<String, Map<String, Points>> map = new HashMap<String, Map<String, Points>>();

		// Map<题干编号,得分>
		for (TechTestDb db : dbs)
		{// 循环每道题
			Map<String, Points> modules = map.get(db.getTrainCourseId());
			if (modules == null)
			{
				modules = new HashMap<String, Points>();
				map.put(db.getTrainCourseId(), modules);
			}

			String moduleId = db.getModuleId();
			if (StringUtils.isBlank(moduleId))
			{
				moduleId = "common";
			}

			// 模块得分
			Points modulePoint = modules.get(moduleId);
			if (modulePoint == null)
			{
				modulePoint = new Points();
				modules.put(moduleId, modulePoint);
			}

			Double pointDe = new Double(0);

			boolean hasError = false;
			for (TechTestAnswer ans : db.getAns())
			{
				if (CoreConsts.YORN.YES.equals(ans.getIsRight()))
				{// 是正确答案
					if (ansSet.contains(ans.getId()))
					{
						pointDe += 1;
					} else
					{
						pointDe -= 1;
						hasError = true;
					}
				} else
				{
					if (ansSet.contains(ans.getId()))
					{
						pointDe -= 1;
						hasError = true;
					} else
					{
//						pointDe += 1;
					}
				}
			}

			modulePoint.setPointDe(modulePoint.getPointDe() + pointDe);
			modulePoint.setPointTotalDe(modulePoint.getPointTotalDe()
					+ db.getAns().size());

			modulePoint.setPointTotalCn(modulePoint.getPointTotalCn() + 1);
			if (!hasError)
			{
				modulePoint.setPointCn(modulePoint.getPointCn() + 1);
			}
		}

		return map;
	}
}
