package com.ecannetwork.tech.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.controller.DateBindController;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.core.util.QRCodeUtil;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.MwVotecourse;
import com.ecannetwork.dto.tech.MwVotekey;
import com.ecannetwork.dto.tech.MwVoteresult;
import com.ecannetwork.dto.tech.MwVotesubject;
import com.ecannetwork.dto.tech.MwVotesystem;
import com.ecannetwork.dto.tech.SubjectUnit;
import com.ecannetwork.dto.tech.TechTest;
import com.ecannetwork.dto.tech.TechTestAnswer;
import com.ecannetwork.dto.tech.TechTestDb;
import com.ecannetwork.dto.tech.TechTrainCourse;
import com.ecannetwork.dto.tech.TechUserMessage;
import com.ecannetwork.tech.service.TestDbService;
import com.ecannetwork.tech.service.TrainPlanService;
import com.ecannetwork.tech.util.FileUtils;

@Controller
@RequestMapping("/vwsurvey")
public class VwsurveyController extends DateBindController
{
	@Autowired
	private CommonService commonService;
	@Autowired
	private TestDbService dbService;
	
	private String tmpPath="http://123.57.17.189:8084/";

	@RequestMapping("index")
	public String index(Model model)
	{
		@SuppressWarnings("unchecked")
		List<MwVotecourse> list = commonService.list("from MwVotecourse t order by id desc");// 列表
		model.addAttribute("dblist", list);

		return "tech/vwsurvey/index";
	}

	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse del(Model model, @RequestParam(value = "id", required = true) String id)
	{
		MwVotecourse mwVotecourse = (MwVotecourse) commonService.get(id, MwVotecourse.class);
		if (mwVotecourse != null)
		{
			commonService.deleteTX(MwVotecourse.class, id);
			return new AjaxResponse(true, "i18n.commit.del.success");
		}
		return new AjaxResponse(true, "i18n.commit.del.success");
	}

	@RequestMapping("copy")
	public @ResponseBody
	AjaxResponse copy(Model model, @RequestParam(value = "id", required = true) String id)
	{
		// 先查出当前记录
		MwVotecourse mwVotecourse = (MwVotecourse) commonService.get(id, MwVotecourse.class);
		if (mwVotecourse != null)
		{
			mwVotecourse.setId(null);// id是数据库自动生成的
			mwVotecourse.setCTitle(mwVotecourse.getCTitle() + "_复制");// title是原来的title+"_复制"

			commonService.saveTX(mwVotecourse);// 作为新的记录保存
			return new AjaxResponse(true, "复制成功");
		}
		return new AjaxResponse(true, "复制成功");
	}

	@RequestMapping("build")
	public @ResponseBody
	AjaxResponse build(Model model, @RequestParam(value = "id", required = true) String id, @RequestParam(value = "NSysId", required = true) String NSysId)
	{
		// 先查出当前记录
		MwVotecourse mwVotecourse = (MwVotecourse) commonService.get(id, MwVotecourse.class);
		if (mwVotecourse != null)
		{
			// 读votetemplate文件
			String votetemp = "";
			String idTemplateFile = ExecuteContext.realPath() + "mw/onlinesurvey/votetemplate_" + id + ".htm";// 对应id的模板
			String defaultTemplateFile = ExecuteContext.realPath() + "mw/onlinesurvey/votetemplate.htm";// 默认的模板

			if (FileUtils.fileExists(idTemplateFile))
			{
				votetemp = FileUtils.readFile(idTemplateFile) + "\n";
			}
			else
			{
				votetemp = FileUtils.readFile(defaultTemplateFile) + "\n";
			}

			votetemp = votetemp.replace("{id}", mwVotecourse.getId()).replace("{path}", ExecuteContext.realPath()).replace("{title}", mwVotecourse.getCTitle())
					.replace("{course}", mwVotecourse.getCCourse()).replace("{teacher}", mwVotecourse.getCTearcher()).replace("{address}", mwVotecourse.getCAdrees())
					.replace("{starttime}", mwVotecourse.getDtStartDate().toString()).replace("{endtime}", mwVotecourse.getDtOverDate().toString())
					.replace("{coursestarttime}", mwVotecourse.getCourseStart().toString()).replace("{courseendtime}", mwVotecourse.getCourseEnd().toString());

			// 生成内容
			votetemp = votetemp.replace("{Content}", pubBuildHtml(mwVotecourse).replace("{Images}", ExecuteContext.realPath() + "/mw/onlinesurvey/SysImages"));

			// 生成的新的文件，以id命名
			String newFile = ExecuteContext.realPath() + "mw/onlinesurvey/" + id + ".html";
			String desFilePath = ExecuteContext.realPath() + "/mw/onlinesurvey/qr";

			// 生成当前问卷的二维码
			if (votetemp.contains("{QR}"))
			{
				try
				{
					QRCodeUtil.encode(newFile, "", ExecuteContext.realPath() + "/mw/onlinesurvey/qr/", true, id);// 生成二维码

					// 替换二维码位置
					votetemp = votetemp.replace("{QR}", "<img src=\"" + ExecuteContext.realPath() + "/mw/onlinesurvey/qr/" + id + ".jpg\" width=\"100\" height=\"100\" />");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			File file = new File(newFile);
			if (file.exists())
			{
				file.delete();
			}

			FileUtils.writeFile(newFile, votetemp);
			System.out.println("build create file : "+newFile);

			return new AjaxResponse(true, "生成投票问卷成功");
		}
		return new AjaxResponse(false, "生成投票问卷失败");
	}

	@RequestMapping("add")
	public String add(Model model)
	{
		// 绑定调查模板下拉列表数据
		List<MwVotesystem> mwVotesystem = this.commonService.list("from MwVotesystem order by id DESC");
		model.addAttribute("list", mwVotesystem);

		// 绑定培训课程下拉列表数据
		List<TechTrainCourse> CCourselist = this.commonService.list("from com.ecannetwork.dto.tech.TechTrainCourse t where t.status=?", CoreConsts.YORN.YES);
		model.addAttribute("CCourselist", CCourselist);

		return "tech/vwsurvey/save";
	}

	@RequestMapping("edit")
	public String edit(Model model, @RequestParam(value = "id", required = true) String id)
	{
		MwVotecourse mwVotecourse = (MwVotecourse) commonService.get(id, MwVotecourse.class);
		model.addAttribute("dto", mwVotecourse);

		// 绑定调查模板下拉列表数据
		List<MwVotesystem> mwVotesystem = this.commonService.list("from MwVotesystem order by id DESC");
		model.addAttribute("list", mwVotesystem);

		// 绑定培训课程下拉列表数据
		List<TechTrainCourse> CCourselist = this.commonService.list("from com.ecannetwork.dto.tech.TechTrainCourse t where t.status=?", CoreConsts.YORN.YES);
		model.addAttribute("CCourselist", CCourselist);

		return "tech/vwsurvey/save";
	}

	// //选择老师
	@RequestMapping("listteachers")
	public String listteachers(Model model)
	{
		List<EcanUser> teachers = this.commonService.list("from EcanUser e where e.roleId='teacher' and e.status = '1'");

		model.addAttribute("list", teachers);
		return "tech/message/selectteachers";
	}

	@RequestMapping("save")
	public @ResponseBody
	AjaxResponse save(Model model, @Valid MwVotecourse mwVotecourse, BindingResult result, HttpServletRequest request)
	{
		// 获取是否验证checkbox的值。当选中时，值为on，否则为null
		String checkNcodeString = request.getParameter("NCodeSurvey");
		if (checkNcodeString == null)
		{
			mwVotecourse.setNCodeSurvey(0);
		}
		else
		{
			mwVotecourse.setNCodeSurvey(1);
		}

		if (mwVotecourse.getId() == null || mwVotecourse.getId() == "")
		{
			commonService.saveTX(mwVotecourse);// 保存
			return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
		}
		else
		{
			commonService.updateTX(mwVotecourse);// 更新
			return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
		}
	}

	// 生成调查问卷内容
	public String pubBuildHtml(MwVotecourse mwVotecourse)
	{
		StringBuffer strResult = new StringBuffer();// 最终的结果

		strResult.append("<form name=\"VWVoteForm\" id=\"VWVoteForm\" method=\"post\" action=\"/Portal2012/techc/vwsurvey/getresult\" >");
		strResult.append("<input name=\"VoteId\" type=\"hidden\" value=\"" + mwVotecourse.getId() + "\" /><input name=\"SubjectId\" type=\"hidden\" value=\"" + mwVotecourse.getNSysId() + "\" />");
		strResult.append("<div class=\"survey\">");

		Integer xh = 1;
		@SuppressWarnings("unchecked")
		List<MwVotesubject> list = commonService.list("from MwVotesubject where parentid=0 and NSysId=? order by id desc", mwVotecourse.getNSysId());// 问卷数据
		if (!list.isEmpty())
		{
			for (MwVotesubject mwVotesubject : list)
			{
				Integer jj = 0;
				List<SubjectUnit> subdata = GetSubToKey(mwVotecourse.getNSysId(), mwVotesubject.getParentid(), "");
				if (!subdata.isEmpty())
				{
					for (SubjectUnit subjectUnit : subdata)
					{
						jj++;
						strResult.append("<div class=\"qa" + ((xh * jj > 1) ? " nodisplay" : "") + " \">" + "<h2>" + subjectUnit.getC_SubTitle().replace("\n", "<br/>") + "</h2>" + "<p>"
								+ String.valueOf(xh) + ((jj > 0) ? "-" + jj : "") + ". " + subjectUnit.getC_SubTitle().replace("\n", "<br/>") + "</p>");
						strResult.append(GetTable(subjectUnit));
						strResult.append("</div>");
					}
					xh++;
				}
			}
		}
		// 增加验证码功能
		// if (mwVotecourse.getNCodeSurvey().equals(1))
		// {
		// strResult.append("<div class=\"qa nodisplay\"><p>" +
		// String.valueOf(xh++) + ". 请输入图片中文字</p>");
		// strResult.append("<input type=\"text\" name=\"Check\" style=\"width:100px\" />  <img id=\"imgObj\" alt=\"验证码\" src=\"${ctxPath}/techc/vwsurvey/code\" /><br/>");
		// strResult.append("</div>");
		// }

		strResult.append("</div><div class=\"btn\"> <a id=\"btnpre\">上一页<br />Previous</a> <span></span> <a id=\"btnnext\">下一页<br />Next</a>");
		strResult.append(" </div>");
		//strResult.append("<br><br><input type=\"submit\" name=\"Submit\" value=\"提 交\" style=\"width:100px\"></form>");
		
		strResult.append("</form>");

		return strResult.toString();
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

	private String GetTable(SubjectUnit subjectUnit)
	{
		List<MwVotekey> list = commonService.list("from MwVotekey where NSubId=? order by NOrderId,id desc", subjectUnit.getN_SubId());
		StringBuffer strUnitBuffer = new StringBuffer();// 返回的字符串
		int num2 = 0;
		if (!list.isEmpty())
		{
			for (MwVotekey mwVotekey : list)
			{
				num2++;
				Integer ntype = mwVotekey.getNType();
				switch (ntype)
				{
				case 1:
					if (subjectUnit.getN_Need().equals(1))
					{
						strUnitBuffer.append(mwVotekey.getCKeyTitle() + "  <input type=\"text\" name=\"" + String.valueOf(subjectUnit.getSubid()) + "\" check=\"^\\S+$\" warning=\""
								+ mwVotekey.getCKeyTitle() + "不能为空\" >");
					}
					else
					{
						if (!mwVotekey.getCRule().isEmpty() && !mwVotekey.getCRule().startsWith("不限制"))
						{
							String strArray17[] = mwVotekey.getCRule().split("`");
							if (strArray17[1] != "*")
							{
								strUnitBuffer.append(mwVotekey.getCKeyTitle() + "" + "<input type=\"text\" name=\"" + String.valueOf(subjectUnit.getSubid()) + "\" check=\"" + strArray17[1]
										+ "\" warning=\"" + mwVotekey.getCKeyTitle() + "格式不正确,应填 " + strArray17[0] + "\" >");
							}
							else
							{
								strUnitBuffer.append(mwVotekey.getCKeyTitle() + "" + "<input type=\"text\" name=\"" + String.valueOf(subjectUnit.getSubid()) + "\">");
							}
						}
						else
						{
							strUnitBuffer.append(mwVotekey.getCKeyTitle() + " <input type=\"text\" name=\" " + String.valueOf(subjectUnit.getSubid()) + "\">");
						}
					}
					break;
				case 2:
					if (num2 == 1)
					{
						strUnitBuffer.append("<ul class=\"option\">");
					}
					if (subjectUnit.getN_Need().equals(1))
					{
						if (num2 != 1)
						{
							strUnitBuffer.append("<li><label><input type=\"radio\" name=\"" + String.valueOf(subjectUnit.getSubid()) + "\" value=\"" + String.valueOf(mwVotekey.getId()) + "\">"
									+ mwVotekey.getCKeyTitle() + "</label></li>");
						}
						else
						{
							strUnitBuffer.append("<li><label><input type=\"radio\" name=\"" + String.valueOf(subjectUnit.getSubid()) + "\" value=\"" + String.valueOf(mwVotekey.getId())
									+ "\"   check=\"^0$\" warning=\"" + subjectUnit.getC_SubTitle() + "最少选一项\" >" + mwVotekey.getCKeyTitle() + "</label></li>");
						}
					}
					else
					{
						strUnitBuffer.append("<li><label><input type=\"radio\" name=\"" + String.valueOf(subjectUnit.getSubid()) + "\" value=\"" + String.valueOf(mwVotekey.getId()) + "\">"
								+ mwVotekey.getCKeyTitle() + "</label></li>");

					}
					if (num2 == list.size())
					{
						strUnitBuffer.append("</ul>");
					}
					break;
				case 3:
					if (num2 == 1)
					{
						strUnitBuffer.append("<ul class=\"option\">");
					}
					if (subjectUnit.getN_Need().equals(1))
					{
						if (num2 == 1)
						{
							strUnitBuffer.append("<li><label><input type=\"checkbox\" name=\"" + String.valueOf(subjectUnit.getSubid()) + "\" value=\"" + String.valueOf(mwVotekey.getId())
									+ "\"  check=\"^0{1,}$\" warning=\"" + subjectUnit.getC_SubTitle() + "最少选一项或以上\">" + mwVotekey.getCKeyTitle() + "</label></li>");
						}
						else
						{
							strUnitBuffer.append("<li><label><input type=\"checkbox\" name=\"" + String.valueOf(subjectUnit.getSubid()) + "\" value=\"" + String.valueOf(mwVotekey.getId()) + "\">"
									+ mwVotekey.getCKeyTitle() + "</label></li>");
						}
					}
					else
					{
						strUnitBuffer.append("<li><label><input type=\"checkbox\" name=\"" + String.valueOf(subjectUnit.getSubid()) + "\" value=\"" + String.valueOf(mwVotekey.getId()) + "\">"
								+ mwVotekey.getCKeyTitle() + "</label></li>");
					}
					if (num2 == list.size())
					{
						strUnitBuffer.append("</ul>");
					}
					break;
				case 4:
					if (num2 == 1)
					{
						strUnitBuffer.append("<select name=\"" + String.valueOf(subjectUnit.getSubid()) + "\"><option value=\"" + String.valueOf(mwVotekey.getId()) + "\"  selected=\"selected\">"
								+ mwVotekey.getCKeyTitle() + "</option>");
					}
					else if (num2 == list.size())
					{
						strUnitBuffer.append("<option value=\"" + String.valueOf(subjectUnit.getSubid()) + "\" >" + mwVotekey.getCKeyTitle() + "</option></select>");
					}
					else
					{
						strUnitBuffer.append("<option value=\"" + String.valueOf(subjectUnit.getSubid()) + "\" >" + mwVotekey.getCKeyTitle() + "</option>");
					}
					break;
				case 5:
					if (subjectUnit.getN_Need().equals(1))
					{
						strUnitBuffer.append("<p>" + mwVotekey.getCKeyTitle() + "<textarea name=\"" + String.valueOf(subjectUnit.getSubid()) + "\" rows=\"5\"  check=\"^[\\s|\\S]{2,}$\" warning=\""
								+ mwVotekey.getCKeyTitle() + "不能为空,且不能少于3个字\"></textarea></p>");
						break;
					}
					else
					{
						if (!mwVotekey.getCRule().isEmpty() && !mwVotekey.getCRule().startsWith("不限制"))
						{
							String strArray3[] = mwVotekey.getCRule().split("`");
							strUnitBuffer.append("<p>" + mwVotekey.getCKeyTitle() + "<textarea type=\"text\" name=\"" + String.valueOf(subjectUnit.getSubid()) + "\" check=\"" + strArray3[1]
									+ "\" warning=\"" + mwVotekey.getCKeyTitle() + "格式不正确,应为 " + strArray3[0] + "\" ></textarea></p>");
						}
						else
						{
							strUnitBuffer.append("<p>" + mwVotekey.getCKeyTitle() + "<textarea name=\"" + String.valueOf(subjectUnit.getSubid()) + "\" rows=\"5\" ></textarea></p>");
						}
					}
					break;
				}
			}
		}
		return strUnitBuffer.toString();
	}

	@RequestMapping("pushreport")
	public String pushreport(Model model, @RequestParam(value = "id", required = true) String id)
	{
		
		System.out.println("pushreport : " + id);
		// 先查出当前记录
		MwVotecourse mwVotecourse = (MwVotecourse) commonService.get(id, MwVotecourse.class);
		if (mwVotecourse != null)
		{
			//TODO:temp
			String webpath = this.tmpPath + "mw/onlinesurvey/" + id + ".html";// 路径
			try
			{
				QRCodeUtil.encode(webpath, "", ExecuteContext.realPath() + "/mw/onlinesurvey/qr/", true, id);// 生成二维码
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			// String qrPath = ExecuteContext.realPath() +
			// "mw/onlinesurvey/qr/" + id + ".jpg";

			String qrPath = ExecuteContext.contextPath() + "/mw/onlinesurvey/qr/" + id + ".jpg";
			String content = pubBuildHtml(mwVotecourse);// 代码内容

			model.addAttribute("webpath", webpath);
			model.addAttribute("qrPath", qrPath);
			model.addAttribute("content", content);
		}

		return "tech/vwsurvey/pushreport";
	}

	String hostIP = "";// 当前页面客户端的IP

	@RequestMapping("getresult")
	public @ResponseBody
	void getresult(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		hostIP = request.getRemoteAddr();
		Integer id = Integer.valueOf(request.getParameter("VoteId"));
		Integer sysId = Integer.valueOf(request.getParameter("SubjectId"));

		if (id == null && sysId == null)
		{
			// return new AjaxResponse(true, "参数错误！");

			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=utf-8");

			out.print("<script>alert('参数错误！')</script>");
			out.print("<script>location.href='/Portal2012/mw/onlinesurvey/" + id + ".html'</script>");
			out.close();
		}
		int num2;

		MwVotecourse mwVotecourse = (MwVotecourse) commonService.get(String.valueOf(id), MwVotecourse.class);
		if (mwVotecourse.getDtStartDate().after(new Date()))// 调查的时间开始时间在当前时间后
		{
			// return new AjaxResponse(true, "调查已经过期！");
			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=utf-8");

			out.print("<script>alert('调查已经过期！')</script>");
			out.print("<script>location.href='/Portal2012/mw/onlinesurvey/" + id + ".html'</script>");
			out.close();
		}

		// 是否是正式开始
		boolean isdovote = mwVotecourse.getDtStartDate().before(new Date());
		int num4 = 0;
		num2 = AddRes(isdovote, 0, 0, String.valueOf(sysId), 0, sysId, num4, id);

		List<SubjectUnit> subdata = GetSubToKey(Integer.valueOf(sysId), 0, "");
		for (SubjectUnit subjectUnit : subdata)
		{
			int num8 = 0;
			int num5 = 0;
			int num7 = 0;

			List<MwVotekey> list = commonService.list("from MwVotekey where NSubId=? order by NOrderId,id desc", subjectUnit.getN_SubId());
			for (MwVotekey mwVotekey : list)
			{
				String[] strArray2;
				switch (mwVotekey.getNType())
				{
				case 1:
					if (!String.valueOf(subjectUnit.getSubid()).isEmpty())
					{
						AddRes(isdovote, Integer.valueOf(mwVotekey.getId()), subjectUnit.getSubid(), String.valueOf(subjectUnit.getSubid()), num2, sysId, num4, id);
					}
					break;
				case 2:
					if ((subjectUnit.getSubid() != null) && (num8 == 0))
					{
						AddRes(isdovote, subjectUnit.getSubid(), subjectUnit.getSubid(), "2", num2, sysId, num4, id);
						num8 = 1;
					}
					break;
				case 3:
					if ((subjectUnit.getSubid() != null) || (num7 != 0))
					{
						break;
					}
					// row["N_SubId"].ToString();
					strArray2 = String.valueOf(subjectUnit.getSubid()).split(",");
					if (strArray2.length != 0)
					{
						for (String string2 : strArray2)
						{
							AddRes(isdovote, Integer.valueOf(string2), subjectUnit.getSubid(), "3", num2, sysId, num4, id);
						}
					}
					num7 = 1;
					break;
				case 4:
					if ((subjectUnit.getSubid() != null) && (num5 == 0))
					{
						AddRes(isdovote, subjectUnit.getSubid(), subjectUnit.getSubid(), "4", num2, sysId, num4, id);
						num5 = 1;
					}
					break;
				case 5:
					if (subjectUnit.getSubid() != null)
					{
						AddRes(isdovote, Integer.valueOf(mwVotekey.getId()), subjectUnit.getSubid(), String.valueOf(subjectUnit.getSubid()), num2, sysId, num4, id);
					}
					break;
				}
			}
		}

		method_3(id, sysId);

		// return new AjaxResponse(true, "投票成功！");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=utf-8");

		out.print("<script>alert('投票成功！')</script>");
		out.print("<script>location.href='/Portal2012/mw/onlinesurvey/" + id + ".html'</script>");
		out.close();
	}

	private int AddRes(Boolean istrue, int KeyId, int SubId, String Reuslt, int TestID, int SysID, int N_LogicPageId, int VoteID)
	{
		int i = 0;
		if (istrue)
		{
			MwVoteresult mwVoteresult = new MwVoteresult();

			mwVoteresult.setNKeyId(KeyId);
			mwVoteresult.setNSubId(SubId);
			mwVoteresult.setCReuslt(Reuslt);
			mwVoteresult.setNTestId(TestID);
			mwVoteresult.setNSysId(SysID);
			mwVoteresult.setNLogicPageId(N_LogicPageId);
			mwVoteresult.setNVoteId(VoteID);
			mwVoteresult.setDtDate(new Date());
			mwVoteresult.setCIp(hostIP);

			commonService.saveTX(mwVoteresult);// 保存

			List<MwVoteresult> list = commonService.list("from MwVoteresult Order By id Desc limit 1");
			i = Integer.valueOf(list.get(0).getId());
		}
		else
		{
			// mw_voteresulttmp表
			// i = B_VoteresultTmp.AddRes(KeyId, SubId, Reuslt, TestID, SysID,
			// N_LogicPageId, VoteID);
		}
		return i;
	}

	private void method_3(int id, int sysid)
	{
		MwVotecourse mwVotecourse = (MwVotecourse) commonService.get(String.valueOf(id), MwVotecourse.class);
		String strA = mwVotecourse.getCReturnUrl();

		String resulttemp = "";
		String idresultFile = ExecuteContext.realPath() + "mw/onlinesurvey/result_" + id + ".htm";// 对应id的结果
		String defaultresultlateFile = ExecuteContext.realPath() + "mw/onlinesurvey/result.htm";// 默认的结果
		if (strA.isEmpty())
		{
			if (FileUtils.fileExists(idresultFile))
			{
				resulttemp = FileUtils.readFile(idresultFile);
			}
			else
			{
				resulttemp = FileUtils.readFile(defaultresultlateFile);
			}
			resulttemp = resulttemp.replace("{voteid}", String.valueOf(id));
			// base.Response.Write(html);
		}
		else
		{
			// base.Response.Redirect(strA);
		}
	}
}
