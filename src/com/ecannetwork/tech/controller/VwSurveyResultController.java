package com.ecannetwork.tech.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.dto.tech.MwVotecourse;
import com.ecannetwork.dto.tech.MwVotekey;
import com.ecannetwork.dto.tech.MwVoteresult;
import com.ecannetwork.dto.tech.MwVotesubject;
import com.ecannetwork.dto.tech.MwVotesystem;
import com.ecannetwork.dto.tech.SubjectUnit;
import com.ecannetwork.dto.tech.TechTrainCourse;
import com.ecannetwork.tech.service.TestDbService;

@Controller
@RequestMapping("/vwsurveyresult")
public class VwSurveyResultController
{
	@Autowired
	private CommonService commonService;
	@Autowired
	private TestDbService dbService;

	@RequestMapping("index")
	public String index(Model model)
	{
		@SuppressWarnings("unchecked")
		List<MwVotecourse> list = commonService.list("from MwVotecourse t order by id desc");// 列表
		model.addAttribute("dblist", list);

		return "tech/vwsurveyresult/index";
	}

	// 调查分析和图形分析只是两种不同的展示方式。一次性将投票数和比例计算出来

	@RequestMapping("statresult")
	public String statresult(Model model, @RequestParam(value = "id", required = true) final String id, @RequestParam(value = "NSysId", required = true) final String NSysId)
	{
		MwVotecourse mwVotecourse = (MwVotecourse) commonService.get(id, MwVotecourse.class);

		// 原C#部分，statresulttmp和statresult的是从不同的表取的，mw_voteresult、mw_voteresulttmp
		// 这里合并成一张表，用字段IsTemp区分。IsTemp=0，原表mw_voteresult；IsTemp=1，临时表mw_voteresulttmp
		// 如果开始时间比当前时间小，则从原表取数据，即before=true，IsTemp=0；否则，取临时表，IsTemp=1
		if (mwVotecourse.getDtStartDate().before(new Date()))// IsTemp=0
		{
			model.addAttribute("userfullCount", getUsefullSurvey(id, NSysId, 0));// 当前调查问卷有效样本数
			model.addAttribute("SubDatalist", CreateTB(id, NSysId, 0));// list数据
		}
		else
		{
			model.addAttribute("userfullCount", getUsefullSurvey(id, NSysId, 1));// 当前调查问卷有效样本数
			model.addAttribute("SubDatalist", CreateTB(id, NSysId, 1));// list数据
		}

		return "tech/vwsurveyresult/statresult";// 转向页面
	}

	@RequestMapping("statreport")
	public String statreport(Model model, @RequestParam(value = "id", required = true) final String id, @RequestParam(value = "NSysId", required = true) final String NSysId)
	{
		MwVotecourse mwVotecourse = (MwVotecourse) commonService.get(id, MwVotecourse.class);

		// 原C#部分，statresulttmp和statresult的是从不同的表取的，mw_voteresult、mw_voteresulttmp
		// 这里合并成一张表，用字段IsTemp区分。IsTemp=0，原表mw_voteresult；IsTemp=1，临时表mw_voteresulttmp
		// 如果开始时间比当前时间小，则从原表取数据，即before=true，IsTemp=0；否则，取临时表，IsTemp=1
		if (mwVotecourse.getDtStartDate().before(new Date()))// IsTemp=0
		{
			model.addAttribute("userfullCount", getUsefullSurvey(id, NSysId, 0));// 当前调查问卷有效样本数
			model.addAttribute("SubDatalist", CreateTB(id, NSysId, 0));// list数据
		}
		else
		{
			model.addAttribute("userfullCount", getUsefullSurvey(id, NSysId, 1));// 当前调查问卷有效样本数
			model.addAttribute("SubDatalist", CreateTB(id, NSysId, 1));// list数据
		}

		return "tech/vwsurveyresult/statreport";// 转向页面
	}

	// 存放调查问卷及结果
	public class SubData
	{
		private String id;
		private String sub;// 标题
		private Integer type;// 类型
		private String tp;// 投票数
		private String rate;// 所占比例

		public String getId()
		{
			return id;
		}

		public void setId(String id)
		{
			this.id = id;
		}

		public String getSub()
		{
			return sub;
		}

		public void setSub(String sub)
		{
			this.sub = sub;
		}

		public Integer getType()
		{
			return type;
		}

		public void setType(Integer type)
		{
			this.type = type;
		}

		public String getTp()
		{
			return tp;
		}

		public void setTp(String tp)
		{
			this.tp = tp;
		}

		public String getRate()
		{
			return rate;
		}

		public void setRate(String rate)
		{
			this.rate = rate;
		}
	}

	// 获取当前调查问卷有效样本数
	private String getUsefullSurvey(final String id, final String NSysId, final Integer IsTemp)
	{
		// 当前调查问卷有效样本数
		String userfullCount = (String) this.commonService.executeCallbackTX(new HibernateCallback<String>()
		{
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException
			{
				String re = null;
				try
				{
					Connection conn = session.connection();
					PreparedStatement pst = conn.prepareStatement("select count(1) FROM mw_voteresult where N_VoteId=" + Integer.valueOf(id) + " and N_KeyId=0 and C_Reuslt = '" + NSysId
							+ "' and IsTemp=" + IsTemp);
					ResultSet rs = pst.executeQuery();
					if (rs.next())
					{
						re = rs.getString(1);
					}
					pst.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return re;
			}
		});

		return userfullCount;
	}

	// 获取全部
	private int getAllSurvey(final Integer id, final Integer SubId, final Integer NSysId, final Integer IsTemp)
	{
		// 当前调查问卷有效样本数
		String userfullCount = (String) this.commonService.executeCallbackTX(new HibernateCallback<String>()
		{
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException
			{
				String re = null;
				try
				{
					Connection conn = session.connection();
					PreparedStatement pst = conn.prepareStatement("select count(1) FROM mw_voteresult where N_VoteId=" + id + " and N_SubId=" + SubId + " and N_SysId = " + NSysId + " and IsTemp="
							+ IsTemp);
					ResultSet rs = pst.executeQuery();
					if (rs.next())
					{
						re = rs.getString(1);
					}
					pst.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return re;
			}
		});

		return userfullCount == null ? 0 : Integer.valueOf(userfullCount);
	}

	private List<SubData> CreateTB(String id, String NSysId, Integer IsTemp)
	{
		List lstsbDatas = new ArrayList();

		int xh = 1;
		// 获取父级
		@SuppressWarnings("unchecked")
		List<MwVotesubject> list = commonService.list("from MwVotesubject where parentid=0 and NSysId=?", Integer.valueOf(NSysId));// 问卷数据
		if (!list.isEmpty())
		{
			for (MwVotesubject mwVotesubject : list)
			{
				// 父级。1.。。。
				SubData sbdata = new SubData();
				sbdata.setId(UUID.randomUUID());
				sbdata.setSub(xh + ". " + mwVotesubject.getCSubTitle());
				sbdata.setType(0);
				sbdata.setTp(null);

				lstsbDatas.add(sbdata);

				int jj = 0;

				List<SubjectUnit> subdata = GetSubToKey(Integer.valueOf(NSysId), Integer.valueOf(mwVotesubject.getId()), "");
				if (!subdata.isEmpty())
				{
					for (SubjectUnit subjectUnit : subdata)
					{
						jj++;
						lstsbDatas.addAll(GetTable(xh, jj, Integer.valueOf(id), subjectUnit, IsTemp));
					}
					xh++;
				}
			}
		}
		else
		{
			List<SubjectUnit> subdata2 = GetSubToKey(Integer.valueOf(NSysId), 0, "");
			if (!subdata2.isEmpty())
			{
				for (SubjectUnit subjectUnit : subdata2)
				{
					lstsbDatas.addAll(GetTable(xh, 0, Integer.valueOf(id), subjectUnit, IsTemp));
					xh++;
				}
			}
		}

		return lstsbDatas;
	}

	private List<SubData> GetTable(int xh, int hh, int id, SubjectUnit subjectUnit, Integer IsTemp)
	{
		List lstsbs = new ArrayList();

		// 第二级。1-1.。。
		SubData sbdata = new SubData();
		sbdata.setId(UUID.randomUUID());
		sbdata.setSub("<span class=\"splace\">&nbsp;</span>" + xh + ((hh > 0) ? "-" + hh : "") + ". " + subjectUnit.getC_SubTitle());
		sbdata.setType(null);
		sbdata.setTp(null);

		lstsbs.add(sbdata);

		int allSurvey = getAllSurvey(id, subjectUnit.getSubid(), subjectUnit.getN_SysId(), IsTemp);// 总数

		@SuppressWarnings("unchecked")
		List<MwVotekey> list = commonService.list("from MwVotekey where NSubId=? order by NOrderId,id", subjectUnit.getN_SubId());
		if (!list.isEmpty())
		{
			for (MwVotekey mwVotekey : list)
			{
				String str2 = "";
				int num3 = 0;

				SubData sbdata2 = new SubData();
				sbdata2.setId(UUID.randomUUID());
				sbdata2.setType(mwVotekey.getNType());

				switch (mwVotekey.getNType())
				{
				case 1:
				case 5:
					str2 = "<span class=\"splace2\">&nbsp;&nbsp;</span>" + mwVotekey.getCKeyTitle() + "<br/>";

					@SuppressWarnings("unchecked")
					List<MwVoteresult> mwVoteresults = commonService.list("from MwVoteresult where NVoteId=? and NKeyId=? and NSubId=? and IsTemp=?", id, Integer.valueOf(mwVotekey.getId()),
							subjectUnit.getSubid(), IsTemp);

					for (MwVoteresult mwVoteresult : mwVoteresults)
					{
						str2 = str2 + mwVoteresult.getCReuslt() + "<br/>";
						num3++;
					}

					sbdata2.setSub(str2);
					sbdata2.setTp(String.valueOf(num3));
					lstsbs.add(sbdata2);
					break;
				case 2:
				case 3:
				case 4:
					// 票数
					final String strSql = "select count(1) FROM mw_voteresult where N_VoteId=" + id + " and N_KeyId=" + Integer.valueOf(mwVotekey.getId()) + " and N_SubId = '"
							+ subjectUnit.getSubid() + "' and IsTemp=" + IsTemp;

					String userfullNum = (String) this.commonService.executeCallbackTX(new HibernateCallback<String>()
					{
						@Override
						public String doInHibernate(Session session) throws HibernateException, SQLException
						{
							String re = null;
							try
							{
								Connection conn = session.connection();
								PreparedStatement pst = conn.prepareStatement(strSql);
								ResultSet rs = pst.executeQuery();
								if (rs.next())
								{
									re = rs.getString(1);
								}
								pst.close();
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
							return re;
						}
					});

					num3 = userfullNum == null ? 0 : Integer.valueOf(userfullNum);

					sbdata2.setSub("<span class=\"splace2\">&nbsp;&nbsp;</span>" + mwVotekey.getCKeyTitle());
					sbdata2.setTp(String.valueOf(num3));

					double tempRate = (num3 * 1.0 / allSurvey * 1.0) * 100;
					sbdata2.setRate(String.format("%.2f", tempRate));// 保留两位小数

					lstsbs.add(sbdata2);
					break;
				}
			}
		}

		return lstsbs;
	}

	public List<SubjectUnit> GetSubToKey(final Integer sysid, final Integer parentid, final String where)
	{
		final List list = new ArrayList();

		// 用此种方法来执行sql语句
		this.commonService.executeCallbackTX(new HibernateCallback()
		{
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				try
				{
					Connection conn = session.connection();

					String cmdtext = "SELECT u.N_SubId,s.N_SysId,s.N_SubId as subid,s.C_SubTitle,s.Parentid,s.N_Need FROM mw_voteunit AS u INNER JOIN mw_votesubject AS s ON u.N_SubId = s.N_Type where 1=1 ";
					if (sysid > 0)
					{
						cmdtext += " and u.N_SysId =" + sysid;
					}
					if (parentid > 0)
					{
						cmdtext += " and s.Parentid =" + parentid;
					}
					cmdtext += where + " order by s.N_OrderId,s.N_SubId";

					PreparedStatement pst = conn.prepareStatement(cmdtext);
					ResultSet rs = pst.executeQuery();
					while (rs.next())
					{
						SubjectUnit unit = new SubjectUnit();
						unit.setN_SubId(rs.getInt("N_SubId"));
						unit.setN_SysId(rs.getInt("N_SysId"));
						unit.setSubid(rs.getInt("subid"));
						unit.setC_SubTitle(rs.getString("C_SubTitle"));
						unit.setParentid(rs.getInt("Parentid"));
						unit.setN_Need(rs.getInt("N_Need"));

						list.add(unit);
					}
					pst.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				return null;
			}
		});

		return list;
	}

	StringBuffer excelallreportSb;// 导出总表到Excel的html数据
	String excelallreportCode = "";
	String excelallreportStartDate = "";

	// 导出总表到Excel
	@RequestMapping("excelallreport")
	public String excelallreport(Model model, @RequestParam(value = "id", required = true) final String id, @RequestParam(value = "NSysId", required = true) final String NSysId,
			HttpServletRequest request)
	{
		excelallreportSb = new StringBuffer();// 每次都创建，避免上次的数据未释放，导致导出的数据重复
		MwVotecourse mwVotecourse = (MwVotecourse) commonService.get(id, MwVotecourse.class);

		if (mwVotecourse.getDtStartDate().before(new Date()))// IsTemp=0
		{
			model.addAttribute("dataString", tableOne(id, NSysId, 0));// 生成excel数据，html格式
		}
		else
		// IsTemp=1
		{
			model.addAttribute("dataString", tableOne(id, NSysId, 1));// 生成excel数据，html格式
		}

		String fileName = "vwac_survey_" + id + "_" + excelallreportStartDate + "_" + excelallreportCode + ".xls";// 文件名称
		HttpSession session = request.getSession();// 将文件名存储到session中
		session.setAttribute("fileName", fileName);

		return "tech/vwsurveyresult/excelallreport";
	}

	private String tableOne(final String id, final String NSysId, final Integer IsTemp)
	{
		final List listVotekey = new ArrayList();

		// 用此种方法来执行sql语句
		this.commonService.executeCallbackTX(new HibernateCallback()
		{
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				try
				{
					Connection conn = session.connection();

					String cmdtext = "SELECT k.C_KeyTitle,k.N_Score,k.N_KeyId FROM mw_voteunit AS u INNER JOIN mw_votekey AS k ON u.N_SubId = k.N_SubId WHERE k.N_Type = 2  and u.N_SysId=" + NSysId
							+ " ";

					PreparedStatement pst = conn.prepareStatement(cmdtext);
					ResultSet rs = pst.executeQuery();
					while (rs.next())
					{
						String unit = rs.getString("N_Score") + "\n" + rs.getString("C_KeyTitle");

						listVotekey.add(unit);
					}
					pst.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				return null;
			}
		});

		if (listVotekey.size() == 0)
		{
			return "";
		}
		else
		{
			int columns = 3 + listVotekey.size(); // 总列数
			columns = columns < 8 ? 8 : columns;// 总列数小于8 默认8列
			int xh = 1;

			int yl = Integer.valueOf(getUsefullSurvey(id, NSysId, IsTemp));// 当前调查问卷有效样本数

			MwVotecourse mwVotecourse = (MwVotecourse) commonService.get(id, MwVotecourse.class);
			excelallreportCode = mwVotecourse.getCCode();
			DateFormat format2 = new SimpleDateFormat("yyyyMMdd");
			if (mwVotecourse.getDtStartDate() != null)
			{
				excelallreportStartDate = format2.format(mwVotecourse.getDtStartDate());
			}

			String CourseStart = "";
			String CourseEnd = "";
			DateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
			if (mwVotecourse.getCourseStart() != null)
			{
				CourseStart = format3.format(mwVotecourse.getCourseStart());
			}
			if (mwVotecourse.getCourseEnd() != null)
			{
				CourseEnd = format3.format(mwVotecourse.getCourseEnd());
			}

			excelallreportSb
					.append("<table style=\"border:thin solid #333333;\"><tr style=\"height:60px;\"><td style=\"border:thin solid #333333;background-color:#003C65;text-align:center;font-size:16px;color:#FFF\" colspan=\""
							+ columns
							+ "\";>"
							+ mwVotecourse.getCTitle()
							+ "</td></tr>"
							+ "<tr style=\"height:40px;\"><td style=\"border:thin solid #333333;background-color:#8DB6CD\" colspan=\"2\">培训名称:"
							+ mwVotecourse.getCCourse()
							+ "</td><td style=\"border:thin solid #333333;background-color:#8DB6CD\" colspan=\"2\">培训师:"
							+ mwVotecourse.getCTearcher()
							+ "</td><td style=\"border:thin solid #333333;background-color:#8DB6CD\" colspan=\"2\">培训时间:"
							+ CourseStart
							+ "—"
							+ CourseEnd
							+ "</td><td style=\"border:thin solid #333333;background-color:#8DB6CD\">培训人数:"
							+ mwVotecourse.getPxnum()
							+ "</td><td style=\"border:thin solid #333333;background-color:#8DB6CD\" colspan=\""
							+ (columns - 7)
							+ "\">参加人数:"
							+ yl
							+ "</td></tr>"
							+ "<tr><td style=\"border:thin solid #333333;background-color:#C9C9C9;text-align:center;\" rowspan=\"2\">序列</td><td style=\"border:thin solid #333333;background-color:#C9C9C9;text-align:center\" rowspan=\"2\">调查项目</td><td style=\"border:thin solid #333333;text-align:center;background-color:#C9C9C9\" rowspan=\"2\">平均分</td><td style=\"border:thin solid #333333;text-align:center;background-color:#C9C9C9;height:30px\";colspan=\""
							+ (columns - 3) + "\">选项票数/内容</td></tr>");

			String header = "<tr style=\"height:30px\">";

			for (Object object : listVotekey)
			{
				header += "<td style=\"border:thin solid #333333;background-color:#C9C9C9;text-align:center;\">" + object.toString().replace("\r", "") + "</td>";
			}
			header = header + "</tr>";
			excelallreportSb.append(header);

			List<MwVotesubject> list = commonService.list("from MwVotesubject where parentid=0 and NSysId=?", Integer.valueOf(NSysId));// 问卷数据
			if (!list.isEmpty())
			{
				// 如果分级，按分级数据显示
				Map<String, String> zdb = new HashMap<String, String>();

				String itemtmp = "<tr><td style=\"border:thin solid #333333;\" align=\"center\">{0}</td><td style=\"border:thin solid #333333;\">{1}</td><td style=\"border:thin solid #333333;\">{2}</td><td colspan="
						+ listVotekey.size() + " style=\"border:thin solid #333333;\"></td></tr>";

				for (final MwVotesubject mwVotesubject : list)
				{
					String b = xh + " ";
					String c = mwVotesubject.getCSubTitle();
					String d = "";

					// 获取分数
					String soucreStr = (String) this.commonService.executeCallbackTX(new HibernateCallback<String>()
					{
						@Override
						public String doInHibernate(Session session) throws HibernateException, SQLException
						{
							String re = null;
							try
							{
								Connection conn = session.connection();
								PreparedStatement pst = conn
										.prepareStatement("SELECT SUM(k.N_Score) score  FROM mw_voteresult AS r  INNER JOIN mw_votesubject AS s ON r.N_SubId = s.N_SubId  INNER JOIN mw_votekey AS k ON r.N_KeyId = k.N_KeyId   where r.N_VoteId='"
												+ id
												+ "' and s.N_SubId in( select  n_subid FROM mw_votesubject  where  instr(colpath,',"
												+ mwVotesubject.getId()
												+ ",')>0"
												+ " union select 0)  and r.IsTemp=" + IsTemp);
								ResultSet rs = pst.executeQuery();
								if (rs.next())
								{
									re = rs.getString(1);
								}
								pst.close();
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
							return re;
						}
					});

					int soucre = soucreStr == null ? 0 : Integer.valueOf(soucreStr);

					double p = yl == 0 ? 0 : (soucre * 1.0 / yl * 1.0);

					String lenStr = (String) this.commonService.executeCallbackTX(new HibernateCallback<String>()
					{
						@Override
						public String doInHibernate(Session session) throws HibernateException, SQLException
						{
							String re = null;
							try
							{
								Connection conn = session.connection();
								PreparedStatement pst = conn
										.prepareStatement("SELECT count(u.N_SubId) FROM mw_voteunit AS u INNER JOIN mw_votesubject AS s ON u.N_SubId = s.N_Type where 1=1  and s.Parentid="
												+ mwVotesubject.getId() + " and (u.N_Type!=1 and u.N_Type!=5)");
								ResultSet rs = pst.executeQuery();
								if (rs.next())
								{
									re = rs.getString(1);
								}
								pst.close();
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
							return re;
						}
					});

					int len = lenStr == null ? 0 : Integer.valueOf(lenStr);
					if (len > 0)
					{
						p = (p * 1.0 / len * 1.0);
					}
					d = String.format("%.2f", p);
					zdb.put(c, d);

					excelallreportSb.append(itemtmp.replace("{0}", b).replace("{1}", c).replace("{2}", d));

					int jj = 0;
					List<SubjectUnit> subdata = GetSubToKey(Integer.valueOf(NSysId), Integer.valueOf(mwVotesubject.getId()), "");
					for (SubjectUnit subjectUnit : subdata)
					{
						jj++;
						dataOne(xh, jj, yl, Integer.valueOf(id), subjectUnit, listVotekey, IsTemp);
					}
					xh++;
				}

				excelallreportSb.append(" <tr><td colspan=\"" + columns + "\"></td></tr><tr><td rowspan=" + (zdb.size() + 1)
						+ " style=\"border:thin solid #333333;\">单项汇总</td><td style=\"border:thin solid #333333;\" colspan=\"2\">项目</td><td style=\"border:thin solid #333333;\" colspan="
						+ (columns - 3) + ">分值</td></tr>");

				for (Map.Entry<String, String> pair : zdb.entrySet())
				{
					if (pair.getValue() != "0.00" && pair.getValue() != "0")
					{
						excelallreportSb.append("<tr ><td style=\"border:thin solid #333333;\" colspan=\"2\">" + pair.getKey()
								+ "</td><td align=\"left\" style=\"border:thin solid #333333;\" colspan=" + (columns - 3) + ">" + pair.getValue() + "</td></tr>");
					}
				}
			}
			excelallreportSb.append("</table>");

			return excelallreportSb.toString();
		}
	}

	private void dataOne(int xh, int hh, int yb, final int id, final SubjectUnit subjectUnit, List listVotekey, final Integer IsTemp)
	{
		int i = 0;
		excelallreportSb.append("<tr><td {rowspan} align=\"center\" style=\"border:thin solid #333333;\">" + xh + ((hh > 0) ? "." + hh : "") + " </td>");
		excelallreportSb.append("<td {rowspan} style=\"border:thin solid #333333;\">" + subjectUnit.getC_SubTitle() + "</td>");

		StringBuilder sbx = new StringBuilder();

		List<MwVotekey> list = commonService.list("from MwVotekey where NSubId=? order by NOrderId,id", subjectUnit.getN_SubId());
		int jj = 0;

		for (MwVotekey mwVotekey : list)
		{
			int num3 = 0;
			switch (mwVotekey.getNType())
			{
			case 1:
			case 5:
				@SuppressWarnings("unchecked")
				List<MwVoteresult> mwVoteresults = commonService.list("from MwVoteresult where NVoteId=? and NKeyId=? and NSubId=? and IsTemp=?", id, Integer.valueOf(mwVotekey.getId()),
						subjectUnit.getSubid(), IsTemp);

				if (mwVoteresults.size() > 0)
				{
					for (MwVoteresult mwVoteresult : mwVoteresults)
					{
						if (i == 0)
						{
							excelallreportSb.append("<td {rowspan} style=\"border:thin solid #333333;\"></td>");
							excelallreportSb.append("<td colspan=" + listVotekey.size() + " style=\"border:thin solid #333333;\">" + mwVoteresult.getCReuslt() + "</td>");
							excelallreportSb.append("</tr>");
						}
						else
						{
							sbx.append("<tr><td colspan=" + listVotekey.size() + " style=\"border:thin solid #333333;\" align=left>" + mwVoteresult.getCReuslt() + "</td></tr>");
						}
						num3++;
						i++;
					}
				}
				else
				{
					excelallreportSb.append("<td {rowspan} style=\"border:thin solid #333333;\"></td>");
					excelallreportSb.append("<td colspan=" + listVotekey.size() + " style=\"border:thin solid #333333;\"></td>");
				}
				break;
			case 2:
			case 4:
				if (jj == 0)
				{
					// 获取分数
					String soucreStr = (String) this.commonService.executeCallbackTX(new HibernateCallback<String>()
					{
						@Override
						public String doInHibernate(Session session) throws HibernateException, SQLException
						{
							String re = null;
							try
							{
								Connection conn = session.connection();
								PreparedStatement pst = conn
										.prepareStatement("SELECT SUM(k.N_Score) score FROM mw_voteresult AS r INNER JOIN mw_votesubject AS s ON r.N_SubId = s.N_SubId INNER JOIN mw_votekey AS k ON r.N_KeyId = k.N_KeyId where  r.N_VoteId='"
												+ id + "' and s.N_SubId =" + subjectUnit.getSubid() + " and r.IsTemp=" + IsTemp);
								ResultSet rs = pst.executeQuery();
								if (rs.next())
								{
									re = rs.getString(1);
								}
								pst.close();
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
							return re;
						}
					});

					int soucre = soucreStr == null ? 0 : Integer.valueOf(soucreStr);

					double p = yb == 0 ? 0 : (soucre * 1.0 / yb * 1.0);
					excelallreportSb.append("<td style=\"border:thin solid #333333;\">" + String.format("%.2f", p) + "</td>");

				}
				final String strSql = "select count(1) FROM mw_voteresult where N_VoteId=" + id + " and N_KeyId=" + Integer.valueOf(mwVotekey.getId()) + " and N_SubId = '" + subjectUnit.getSubid()
						+ "' and IsTemp=" + IsTemp;

				String userfullNum = (String) this.commonService.executeCallbackTX(new HibernateCallback<String>()
				{
					@Override
					public String doInHibernate(Session session) throws HibernateException, SQLException
					{
						String re = null;
						try
						{
							Connection conn = session.connection();
							PreparedStatement pst = conn.prepareStatement(strSql);
							ResultSet rs = pst.executeQuery();
							if (rs.next())
							{
								re = rs.getString(1);
							}
							pst.close();
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						return re;
					}
				});
				num3 = userfullNum == null ? 0 : Integer.valueOf(userfullNum);
				excelallreportSb.append("<td style=\"border:thin solid #333333;\">" + num3 + "</td>");
				jj++;
				break;
			case 3:
				int colspan = listVotekey.size() > 1 ? listVotekey.size() - 1 : 0;
				if (jj == 0)
				{
					excelallreportSb.append("<td {rowspan} style=\"border:thin solid #333333;\">0</td>");
					excelallreportSb.append("<td align=left style=\"border:thin solid #333333;\">" + mwVotekey.getCKeyTitle() + "</td>");
					excelallreportSb.append("<td colspan=\"" + colspan + "\" style=\"border:thin solid #333333;\">" + num3 + "</td>");
					excelallreportSb.append("</tr>");
				}
				else
				{
					final String strSql2 = "select count(1) FROM mw_voteresult where N_VoteId=" + id + " and N_KeyId=" + Integer.valueOf(mwVotekey.getId()) + " and N_SubId = '"
							+ subjectUnit.getSubid() + "' and IsTemp=" + IsTemp;

					String userfullNum2 = (String) this.commonService.executeCallbackTX(new HibernateCallback<String>()
					{
						@Override
						public String doInHibernate(Session session) throws HibernateException, SQLException
						{
							String re = null;
							try
							{
								Connection conn = session.connection();
								PreparedStatement pst = conn.prepareStatement(strSql2);
								ResultSet rs = pst.executeQuery();
								if (rs.next())
								{
									re = rs.getString(1);
								}
								pst.close();
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
							return re;
						}
					});
					num3 = userfullNum2 == null ? 0 : Integer.valueOf(userfullNum2);
					excelallreportSb.append(" <tr><td align=left style=\"border:thin solid #333333;\">" + mwVotekey.getCKeyTitle() + "</td><td colspan=\"" + colspan
							+ "\" style=\"border:thin solid #333333;\">" + num3 + "</td> </tr>");
				}
				jj++;
				i++;
				break;
			}
		}
		if (i > 0)
		{
			excelallreportSb.append(sbx.toString());
			String repSb = excelallreportSb.toString().replace("{rowspan}", "rowspan=\"" + i + "\"");
			excelallreportSb.setLength(0);// 清空，避免替换后的字符串没有加入
			excelallreportSb.append(repSb);
		}
		else
		{
			excelallreportSb.append("</tr>");
			String repSb = excelallreportSb.toString().replace("{rowspan}", "");
			excelallreportSb.setLength(0);// 清空
			excelallreportSb.append(repSb);
		}
	}

	StringBuffer excelallreportSbsingle;// 按单人导出到Excel的html数据
	String excelallreportCodesingle = "";
	String excelallreportStartDatesingle = "";

	// 按单人导出到Excel
	@RequestMapping("excelsinglereport")
	public String excelsinglereport(Model model, @RequestParam(value = "id", required = true) final String id, @RequestParam(value = "NSysId", required = true) final String NSysId,
			HttpServletRequest request)
	{
		excelallreportSbsingle = new StringBuffer();// 每次都创建，避免上次的数据未释放，导致导出的数据重复

		MwVotecourse mwVotecourse = (MwVotecourse) commonService.get(id, MwVotecourse.class);
		if (mwVotecourse.getDtStartDate().before(new Date()))// IsTemp=0
		{
			model.addAttribute("tableExcelString", tableOneSingle(id, NSysId, 0));// 生成excel数据，html格式
		}
		else
		// IsTemp=1
		{
			model.addAttribute("tableExcelString", tableOneSingle(id, NSysId, 1));// 生成excel数据，html格式
		}

		String fileName = "vwac_survey_detail_" + id + "_" + excelallreportStartDatesingle + "_" + excelallreportCodesingle + ".xls";// 文件名称
		HttpSession session = request.getSession();// 将文件名存储到session中
		session.setAttribute("fileName", fileName);

		return "tech/vwsurveyresult/excelsinglereport";
	}

	private String tableOneSingle(final String id, final String NSysId, Integer IsTemp)
	{
		final List listVotekey = new ArrayList();

		// 用此种方法来执行sql语句
		this.commonService.executeCallbackTX(new HibernateCallback()
		{
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				try
				{
					Connection conn = session.connection();

					String cmdtext = "SELECT k.C_KeyTitle,k.N_Score,k.N_KeyId FROM mw_voteunit AS u INNER JOIN mw_votekey AS k ON u.N_SubId = k.N_SubId WHERE k.N_Type = 2  and u.N_SysId=" + NSysId
							+ " ";

					PreparedStatement pst = conn.prepareStatement(cmdtext);
					ResultSet rs = pst.executeQuery();
					while (rs.next())
					{
						String unit = rs.getString("N_Score") + "\n" + rs.getString("C_KeyTitle");

						listVotekey.add(unit);
					}
					pst.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				return null;
			}
		});

		if (listVotekey.size() == 0)
		{
			return "";
		}

		int columns = 3 + listVotekey.size(); // 总列数
		columns = columns < 8 ? 8 : columns;// 总列数小于8 默认8列

		int yl = Integer.valueOf(getUsefullSurvey(id, NSysId, IsTemp));// 当前调查问卷有效样本数

		MwVotecourse mwVotecourse = (MwVotecourse) commonService.get(id, MwVotecourse.class);
		excelallreportCodesingle = mwVotecourse.getCCode();
		DateFormat format2 = new SimpleDateFormat("yyyyMMdd");
		if (mwVotecourse.getDtStartDate() != null)
		{
			excelallreportStartDatesingle = format2.format(mwVotecourse.getDtStartDate());
		}

		String CourseStart = "";
		String CourseEnd = "";
		DateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
		if (mwVotecourse.getCourseStart() != null)
		{
			CourseStart = format3.format(mwVotecourse.getCourseStart());
		}
		if (mwVotecourse.getCourseEnd() != null)
		{
			CourseEnd = format3.format(mwVotecourse.getCourseEnd());
		}

		excelallreportSbsingle
				.append("<table style=\"border:1px solid #F0F0F0;\"><tr style=\"height:60px;\"><td style=\"border:1px solid #F0F0F0;background-color:#003C65;text-align:center;font-size:16px;color:#FFF\" colspan=\""
						+ columns
						+ "\";>"
						+ mwVotecourse.getCTitle()
						+ "</td></tr>"
						+ "<tr style=\"height:40px;\"><td style=\"border:1px solid #F0F0F0;background-color:#8DB6CD\" colspan=\"2\">培训名称:"
						+ mwVotecourse.getCCourse()
						+ "</td><td style=\"border:1px solid #F0F0F0;background-color:#8DB6CD\" colspan=\"2\">培训师:"
						+ mwVotecourse.getCTearcher()
						+ "</td><td style=\"border:1px solid #F0F0F0;background-color:#8DB6CD\" colspan=\"2\">培训时间:"
						+ CourseStart
						+ "-"
						+ CourseEnd
						+ "</td><td style=\"border:1px solid #F0F0F0;background-color:#8DB6CD\">培训人数:"
						+ mwVotecourse.getPxnum()
						+ "</td><td style=\"border:1px solid #F0F0F0;background-color:#8DB6CD\" colspan=\""
						+ (columns - 7)
						+ "\">参加人数:"
						+ yl
						+ "</td></tr>"
						+ "<tr><td style=\"border:1px solid #F0F0F0;background-color:#C9C9C9;text-align:center;\" rowspan=\"2\">序列</td><td style=\"border:1px solid #F0F0F0;background-color:#C9C9C9;text-align:center\" rowspan=\"2\" colspan=\"2\">调查项目</td><td style=\"border:1px solid #F0F0F0;text-align:center;background-color:#C9C9C9;height:30px\";colspan=\""
						+ (columns - 3) + "\">选项票数/内容</td></tr>");

		String header = "";
		header = "<tr style=\"height:30px\">";
		for (Object object : listVotekey)
		{
			header += "<td style=\"border:1px solid #F0F0F0;background-color:#C9C9C9;\">" + object.toString().replace("\r", "").replace(" ", "") + "</td>";
		}
		header = header + "</tr>";
		excelallreportSbsingle.append(header);

		// 找到所有参与投票的人
		@SuppressWarnings("unchecked")
		List<MwVoteresult> dtresult = commonService.list("from MwVoteresult where NVoteId=? and NSysId=? and IsTemp=?", Integer.valueOf(id), Integer.valueOf(NSysId), IsTemp);

		// 获取单个人节点集合
		List<MwVoteresult> resultrows = new ArrayList();
		for (MwVoteresult mwVoteresult : dtresult)
		{
			if (mwVoteresult.getNSubId().equals(0))
			{
				resultrows.add(mwVoteresult);
			}
		}

		// 有多少人调查，就循环几轮
		if (yl > 0)
		{
			for (int i = 0; i < yl; i++)
			{
				String dt = resultrows.get(i).getDtDate() == null ? "" : resultrows.get(i).getDtDate().toString();
				String ip = resultrows.get(i).getCIp() == null ? "" : resultrows.get(i).getCIp().toString();
				excelallreportSbsingle.append("<tr><td colspan=\"" + columns + "\" style=\"background-color:#003C65;height:30px;text-align:center;color:#ffffff\">问卷：" + (i + 1) + " 时间：" + dt + " ip:"
						+ ip + "</td></tr>");
				// 获取该人下所有投票项目
				List<MwVoteresult> result = new ArrayList();
				for (MwVoteresult eVoteresult : dtresult)
				{
					if (eVoteresult.getNTestId().equals(Integer.valueOf(resultrows.get(i).getId())))
					{
						result.add(eVoteresult);
					}
				}

				String itemtmp = "<tr><td style=\"border:1px solid #F0F0F0;height:40px;\">{0}</td style=\"border:1px solid #F0F0F0;\"><td style=\"border:1px solid #F0F0F0;\" colspan=2>{1}</td><td colspan="
						+ listVotekey.size() + " style=\"border:1px solid #F0F0F0;\"></td></tr>";

				List<MwVotesubject> list = commonService.list("from MwVotesubject where parentid=0 and NSysId=?", Integer.valueOf(NSysId));// 问卷数据
				if (!list.isEmpty())
				{
					// 如果分级，按分级数据显示
					int xh = 1;
					for (final MwVotesubject mwVotesubject : list)
					{
						String b = (i + 1) + "." + xh;
						String c = mwVotesubject.getCSubTitle();

						excelallreportSbsingle.append(itemtmp.replace("{0}", b + " ").replace("{1}", c));
						int jj = 0;
						List<SubjectUnit> subdata = GetSubToKey(Integer.valueOf(NSysId), Integer.valueOf(mwVotesubject.getId()), "");
						for (SubjectUnit subjectUnit : subdata)
						{
							jj++;
							dataOneSingle(b, jj, Integer.valueOf(id), subjectUnit, listVotekey, result);
						}
						xh++;
					}
				}
			}
		}

		excelallreportSbsingle.append("</table>");
		return excelallreportSbsingle.toString();
	}

	private void dataOneSingle(String xh, int hh, final int id, final SubjectUnit subjectUnit, List listVotekey, List<MwVoteresult> rowresult)
	{
		excelallreportSbsingle.append("<tr><td style=\"border:1px solid #F0F0F0;height:30px;\">" + xh + ((hh > 0) ? "." + hh : "") + " </td>");
		excelallreportSbsingle.append("<td style=\"border:1px solid #F0F0F0;\" colspan=\"2\">" + subjectUnit.getC_SubTitle() + "</td>");

		@SuppressWarnings("unchecked")
		List<MwVotekey> list = commonService.list("from MwVotekey where NSubId=? order by NOrderId,id", subjectUnit.getN_SubId());
		int xmnum = 0;

		for (MwVotekey mwVotekey : list)
		{
			String str2 = "";
			int num3 = 0;
			switch (mwVotekey.getNType())
			{
			case 1:
			case 5:
				MwVoteresult resultFirst = null;
				for (MwVoteresult eVoteresult : rowresult)
				{
					if (eVoteresult.getNKeyId().equals(Integer.valueOf(mwVotekey.getId())) && eVoteresult.getNSubId().equals(subjectUnit.getSubid()))
					{
						resultFirst = eVoteresult;
						break;
					}
				}
				if (resultFirst != null)
				{
					str2 = resultFirst.getCReuslt();
				}

				excelallreportSbsingle.append("<td colspan=" + listVotekey.size() + " style=\"border:1px solid #F0F0F0;\">" + str2 + "</td>");
				break;
			case 2:
			case 4:
				MwVoteresult resultFirst4 = null;
				for (MwVoteresult eVoteresult : rowresult)
				{
					if (eVoteresult.getId().equals(mwVotekey.getId()) && eVoteresult.getNSubId().equals(subjectUnit.getSubid()))
					{
						resultFirst4 = eVoteresult;
						break;
					}
				}
				if (resultFirst4 != null)
				{
					num3 = 1;
				}

				excelallreportSbsingle.append("<td style=\"border:1px solid #F0F0F0;\">" + num3 + "</td>");
				break;
			case 3:
				MwVoteresult resultFirst3 = null;
				for (MwVoteresult eVoteresult : rowresult)
				{
					if (eVoteresult.getId().equals(mwVotekey.getId()) && eVoteresult.getNSubId().equals(subjectUnit.getSubid()))
					{
						resultFirst3 = eVoteresult;
						break;
					}
				}
				if (resultFirst3 != null)
				{
					str2 = mwVotekey.getCKeyTitle();
				}
				if (xmnum == 0)
				{
					excelallreportSbsingle.append("<td colspan=" + listVotekey.size() + " style=\"border:1px solid #F0F0F0;\">" + str2 + "");
				}
				else if (xmnum == list.size() - 1)
				{
					excelallreportSbsingle.append(" " + str2 + "</td>");
				}
				else
				{
					excelallreportSbsingle.append(" " + str2 + "");
				}
				xmnum++;
				break;
			}
		}

		excelallreportSbsingle.append("</tr>");
	}
}
