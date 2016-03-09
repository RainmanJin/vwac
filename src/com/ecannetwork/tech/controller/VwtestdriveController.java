package com.ecannetwork.tech.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.dto.core.EcanDomainvalueDTO;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.core.GetCheckBoxHtml;
import com.ecannetwork.dto.core.GetSelectTree;
import com.ecannetwork.dto.tech.MwTestdriver;
import com.ecannetwork.dto.tech.MwTestdriverfield;
import com.ecannetwork.dto.tech.MwTestdriverfieldParent;
import com.ecannetwork.dto.tech.TechTrainCourse;

@Controller
@RequestMapping("/vwtestdrive")
public class VwtestdriveController
{
	@Autowired
	private CommonService commonService;

	@RequestMapping("index")
	public String index(Model model)
	{
		List<MwTestdriver> list = commonService.list("from MwTestdriver t order by id desc");// 列表
		model.addAttribute("dblist", list);

		return "tech/vwtestdrive/index";
	}

	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse del(Model model, @RequestParam(value = "id", required = true) String id)
	{
		MwTestdriver mwTestdriver = (MwTestdriver) commonService.get(id, MwTestdriver.class);
		if (mwTestdriver != null)
		{
			commonService.deleteTX(MwTestdriver.class, id);
			return new AjaxResponse(true, "i18n.commit.del.success");
		}
		return new AjaxResponse(true, "i18n.commit.del.success");
	}

	@RequestMapping("add")
	public String add(Model model) throws SQLException
	{
		// 绑定培训课程下拉列表数据
		List<TechTrainCourse> CCourselist = this.commonService.list("from com.ecannetwork.dto.tech.TechTrainCourse t where t.status=?", CoreConsts.YORN.YES);
		model.addAttribute("CCourselist", CCourselist);

		// 获取新的识别码，表中C_Code最大值+1
		String MaxCCode = (String) this.commonService.executeCallbackTX(new HibernateCallback<String>()
		{
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException
			{
				String re = null;
				try
				{
					Connection conn = session.connection();
					PreparedStatement pst = conn.prepareStatement("select max(c_code) from mw_testdriver");
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

				if (re != null && re != "")
				{
					return re;
				}
				else
				{
					return "10001";
				}
			}
		});

		Integer newCCode = Integer.valueOf(MaxCCode) + 1;
		model.addAttribute("NewCCode", String.valueOf(newCCode));

		return "tech/vwtestdrive/save";
	}

	// 讲师
	@RequestMapping("listteachers")
	public String listteachers(Model model)
	{
		List<EcanUser> teachers = this.commonService.list("from EcanUser e where e.roleId='teacher' and e.status = '1'");

		model.addAttribute("list", teachers);
		return "tech/message/selectteachers";
	}

	// 学员
	@RequestMapping("liststudents")
	public String liststudents(Model model)
	{
		List<EcanUser> students = this.commonService.list("from EcanUser e where e.roleId='student' and e.status = '1'");

		model.addAttribute("list", students);
		return "tech/message/selectstudents";
	}

	// 车型
	@RequestMapping("listchexi")
	public String listchexi(Model model)
	{
		List<EcanDomainvalueDTO> domainvaluechexi = this.commonService.list("from EcanDomainvalueDTO e where e.domainId='CX' and (isDelete='0' or isDelete is null)");

		model.addAttribute("list", domainvaluechexi);
		return "tech/message/selectchexi";
	}

	@RequestMapping("edit")
	public String edit(Model model, @RequestParam(value = "id", required = true) String id)
	{
		MwTestdriver mwTestdriver = (MwTestdriver) commonService.get(id, MwTestdriver.class);
		model.addAttribute("dto", mwTestdriver);

		// 绑定培训课程下拉列表数据
		List<TechTrainCourse> CCourselist = this.commonService.list("from com.ecannetwork.dto.tech.TechTrainCourse t where t.status=?", CoreConsts.YORN.YES);
		model.addAttribute("CCourselist", CCourselist);

		return "tech/vwtestdrive/save";
	}

	@RequestMapping("save")
	public @ResponseBody
	AjaxResponse save(Model model, @Valid MwTestdriver mwTestdriver, BindingResult result, HttpServletRequest request)
	{
		// 获取是否验证checkbox的值。当选中时，值为on，否则为null
		String gonglu = request.getParameter("gonglu");
		String yundong = request.getParameter("yundong");
		String yueye = request.getParameter("yueye");

		String changdi = "";
		changdi = gonglu == null ? "" : "0";
		changdi += yundong == null ? "," : ",1";
		changdi += yueye == null ? "," : ",2";

		mwTestdriver.setChangdi(changdi);
		
		if (mwTestdriver.getId() == null || mwTestdriver.getId() == "")
		{
			mwTestdriver.setPgfield("||");//新建，配置字段为空
			
			// 记录创建时间
			mwTestdriver.setCreateTime(new Date());// new Date()为获取当前系统时间

			commonService.saveTX(mwTestdriver);// 保存
			return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
		}
		else
		{
			commonService.updateTX(mwTestdriver);// 更新
			return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
		}
	}

	@RequestMapping("filed")
	public String filed(Model model) throws SQLException
	{
		final List list = new ArrayList();

		// 用此种方法来执行sql语句
		// MwTestdriverfield增加了字段，不能直接使用Hibernate映射
		this.commonService.executeCallbackTX(new HibernateCallback()
		{
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				try
				{
					Connection conn = session.connection();
					PreparedStatement pst = conn.prepareStatement("select b.*,a.c_name parentname from Mw_Testdriverfield a inner join Mw_Testdriverfield b on a.id=b.parentId  order by b.id desc");
					ResultSet rs = pst.executeQuery();
					while (rs.next())
					{
						MwTestdriverfieldParent testfield = new MwTestdriverfieldParent();
						testfield.setId(rs.getString("Id"));
						testfield.setCName(rs.getString("C_Name"));
						testfield.setParentId(rs.getInt("ParentId"));
						testfield.setColPath(rs.getString("ColPath"));
						testfield.setOrderby(rs.getInt("Orderby"));
						testfield.setIsUse(rs.getInt("isUse"));
						testfield.setRemark(rs.getString("remark"));
						testfield.setParentName(rs.getString("parentname"));

						list.add(testfield);
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

		model.addAttribute("dblist", list);
		return "tech/vwtestdrive/fieldindex";
	}

	@RequestMapping("delfield")
	public @ResponseBody
	AjaxResponse delfield(Model model, @RequestParam(value = "id", required = true) String id)
	{
		MwTestdriverfield mwTestdriverfield = (MwTestdriverfield) commonService.get(id, MwTestdriverfield.class);
		if (mwTestdriverfield != null)
		{
			commonService.deleteTX(MwTestdriverfield.class, id);
			return new AjaxResponse(true, "i18n.commit.del.success");
		}
		return new AjaxResponse(true, "i18n.commit.del.success");
	}

	@RequestMapping("addfield")
	public String addfield(Model model)
	{
		// 绑定父级下拉列表数据
		List<MwTestdriverfield> mwTestdriverfieldOld = this.commonService.list(MwTestdriverfield.class);// 原始数据

		// 转换数据成树形
		List<MwTestdriverfield> parentlevellist = new ArrayList<MwTestdriverfield>();
		parentlevellist = GetSelectTree.getParentLevel(mwTestdriverfieldOld, 0);

		model.addAttribute("parentlevellist", parentlevellist);

		return "tech/vwtestdrive/savefield";
	}

	@RequestMapping("editfield")
	public String editfield(Model model, @RequestParam(value = "id", required = true) String id)
	{
		MwTestdriverfield mwTestdriverfield = (MwTestdriverfield) commonService.get(id, MwTestdriverfield.class);
		model.addAttribute("dto", mwTestdriverfield);

		// 绑定父级下拉列表数据
		List<MwTestdriverfield> mwTestdriverfieldOld = this.commonService.list(MwTestdriverfield.class);// 原始数据

		// 转换数据成树形
		List<MwTestdriverfield> parentlevellist = new ArrayList<MwTestdriverfield>();
		parentlevellist = GetSelectTree.getParentLevel(mwTestdriverfieldOld, 0);

		model.addAttribute("parentlevellist", parentlevellist);

		return "tech/vwtestdrive/savefield";
	}

	@RequestMapping("savefield")
	public @ResponseBody
	AjaxResponse savefield(Model model, @Valid MwTestdriverfield mwTestdriverfield, BindingResult result, HttpServletRequest request)
	{
		if (mwTestdriverfield.getId() == null || mwTestdriverfield.getId() == "")
		{
			commonService.saveTX(mwTestdriverfield);// 保存
			return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
		}
		else
		{
			commonService.updateTX(mwTestdriverfield);// 更新
			return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
		}
	}

	// 生成checkbox的数据，并设置已经选择的项，界面上直接复制显示
	private String setCheckBox(String selectFieldf)
	{
		// checkbox数据
		List<MwTestdriverfield> mwTestdriverfieldOld = this.commonService.list(MwTestdriverfield.class);// 原始数据

		GetCheckBoxHtml getCheckBoxHtml = new GetCheckBoxHtml();
		String result = getCheckBoxHtml.getParentLevel(mwTestdriverfieldOld, 0, selectFieldf);
		return result;
	}

	@RequestMapping("changdidetail")
	public String changdidetail(Model model, @RequestParam(value = "id", required = true) String id, @RequestParam(value = "type", required = true) String type)
	{
		MwTestdriver mwTestdriver = (MwTestdriver) commonService.get(id, MwTestdriver.class);
		model.addAttribute("dto", mwTestdriver);
		model.addAttribute("type", type);// 类型

		// 读取配置字段
		String pgField = mwTestdriver.getPgfield();
		if (pgField != null)
		{
			String filed[] = mwTestdriver.getPgfield().split("\\|", 3);// 分隔符为“.”(无输出),“|”(不能得到正确结果)转义字符时,“*”,“+”时出错抛出异常,都必须在前面加必须得加"\\",如split(\\|);

			model.addAttribute("pgfield", filed[Integer.valueOf(type)]);// 存放历史配置字段
			// 传入历史配置字段，界面上则选中状态
			model.addAttribute("setchexkbox", setCheckBox(filed[Integer.valueOf(type)]));
		}
		else
		{
			model.addAttribute("setchexkbox", setCheckBox(""));// 无选中状态
		}

		return "tech/vwtestdrive/changdidetail";
	}

	@RequestMapping("changdisave")
	public @ResponseBody
	AjaxResponse changdisave(Model model, @Valid MwTestdriver mwTestdrivertemp, BindingResult result, HttpServletRequest request)
	{
		MwTestdriver mwTestdriver = (MwTestdriver) commonService.get(mwTestdrivertemp.getId(), MwTestdriver.class);

		String pgField = "";// 配置字段

		String type = request.getParameter("selectType");// 类型
		String hidf = request.getParameter("allpgfield").substring(0, request.getParameter("allpgfield").lastIndexOf(","));// 获取所有的选中的项

		if (mwTestdriver.getPgfield() != null)
		{
			String san[] = mwTestdriver.getPgfield().split("\\|", 3);
			if (type.equals("0"))
			{
				pgField = hidf + "|" + san[1] + "|" + san[2];
			}
			else if (type.equals("1"))
			{
				pgField = san[0] + "|" + hidf + "|" + san[2];
			}
			else if (type.equals("2"))
			{
				pgField = san[0] + "|" + san[1] + "|" + hidf;
			}
		}
		else
		{
			if (type.equals("0"))
			{
				pgField = hidf + "|" + "" + "|" + "";
			}
			else if (type.equals("1"))
			{
				pgField = "" + "|" + hidf + "|" + "";
			}
			else if (type.equals("2"))
			{
				pgField = "" + "|" + "" + "|" + hidf;
			}
		}

		mwTestdriver.setPgfield(pgField);
		commonService.updateTX(mwTestdriver);// 更新

		return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
	}

}
