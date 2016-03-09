package com.ecannetwork.tech.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecannetwork.core.app.domain.DomainFacade;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.dto.core.EcanDomainDTO;
import com.ecannetwork.dto.core.EcanDomainvalueDTO;

@Component("exportExcel")
public class ExportExcel
{
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private DomainFacade domainFacade;
	// 称谓
	private static String[] caller =
	{ "Mr.", "Ma.", "Dr." };
	// 性别
	private static String[] sex =
	{ "男", "女" };

	// 所属（公司名）

	// private String[] Company = { "大众汽车学院", "SCREEN", "SVW", "FAW-VW", "信息化部",
	// "Puxin" };
	private String[] Company;
	// 部门

	// private String[] Dept = { "计划战略部", "高级讲师", "销售讲师", "售后技术讲师", "售后非技术讲师" };
	private String[] Dept;

	// 角色

	// private String[] Role = { "管理员", "讲师", "操作员", "学员" };
	private String[] Role;

	public ExportExcel()
	{
	}

	public String[] getDoaminValues(String key)
	{
		Map<String, EcanDomainDTO> domainMap = domainFacade.getDomainMap();
		if (domainMap.containsKey(key))
		{
			EcanDomainDTO ecanDomain = (EcanDomainDTO) domainMap.get(key);
			List<EcanDomainvalueDTO> domainValuesList = ecanDomain.getValues();
			List<String> domainvalues = new ArrayList<String>();
			for (EcanDomainvalueDTO ecanDomainvalueDTO : domainValuesList)
			{
				domainvalues.add(ecanDomainvalueDTO.getDomainlabel());
			}

			return (String[]) domainvalues.toArray(new String[domainvalues
					.size()]);
		}
		return null;
	}

	public static void main(String[] args)
	{

		new ExportExcel().ExExcel();
	}

	public String ExExcel()
	{
		String fileName = "";
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("用户表格规范");
		HSSFCellStyle cellTxtStyle = wb.createCellStyle();
		HSSFCellStyle mustTxtStyle = wb.createCellStyle();
		
		cellTxtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		mustTxtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		HSSFFont font = wb.createFont();
		font.setColor(HSSFColor.RED.index);
		mustTxtStyle.setFont(font);
		
		HSSFRow row = sheet.createRow(0);
		// 真实姓名
		HSSFCell cell0 = row.createCell(0);
		cell0.setCellValue("真实姓名");
		cell0.setCellStyle(mustTxtStyle);

		// 登录名
		HSSFCell cell1 = row.createCell(1);
		cell1.setCellValue("登录名");
		cell1.setCellStyle(mustTxtStyle);
		// 密码
		HSSFCell cell2 = row.createCell(2);
		cell2.setCellValue("密码");
		cell2.setCellStyle(mustTxtStyle);
		// 昵称
		HSSFCell cell3 = row.createCell(3);
		cell3.setCellValue("昵称");
		cell3.setCellStyle(cellTxtStyle);
		// 生日
		HSSFCell cell4 = row.createCell(4);
		cell4.setCellValue("生日");
		cell4.setCellStyle(cellTxtStyle);
		// 身份证号码
		HSSFCell cell5 = row.createCell(5);
		cell5.setCellValue("身份证号码");
		cell5.setCellStyle(cellTxtStyle);
		// 称谓
		HSSFCell cell6 = row.createCell(6);
		cell6.setCellValue("称谓");
		cell6.setCellStyle(cellTxtStyle);
		// 性别
		HSSFCell cell7 = row.createCell(7);
		cell7.setCellValue("性别");
		cell7.setCellStyle(cellTxtStyle);
		// 所属（公司名）
		HSSFCell cell8 = row.createCell(8);
		cell8.setCellValue("所属（公司名）");
		cell8.setCellStyle(mustTxtStyle);
		// 部门
		HSSFCell cell9 = row.createCell(9);
		cell9.setCellValue("部门");
		cell9.setCellStyle(cellTxtStyle);
		// 角色
		HSSFCell cell10 = row.createCell(10);
		cell10.setCellValue("角色");
		cell10.setCellStyle(mustTxtStyle);


		// 手机
		HSSFCell cell12 = row.createCell(11);
		cell12.setCellValue("手机");
		cell12.setCellStyle(cellTxtStyle);
		// 办公电话
		HSSFCell cell13 = row.createCell(12);
		cell13.setCellValue("办公电话");
		cell13.setCellStyle(cellTxtStyle);
		// 邮箱
		HSSFCell cell14 = row.createCell(13);
		cell14.setCellValue("邮箱");
		cell14.setCellStyle(mustTxtStyle);
		for (int i = 1; i <= 3000; i++)
		{
			HSSFRow row_i = sheet.createRow(i);
			for (int k = 0; k < 14; k++)
			{
				// 6,7 8,9,10,11
				if (k == 6)
				{
					createListBox(caller, i, k, sheet);
				} else if (k == 7)
				{
					createListBox(sex, i, k, sheet);
				} else if (k == 8)
				{
					createListBox(domainvalues("COMPANY"), i, k, sheet);
				} else if (k == 9)
				{
					createListBox(domainvalues("POSITION"), i, k, sheet);

				} else if (k == 10)
				{
					createListBox(domainvalues("ROLE"), i, k, sheet);
				} else
				{
					HSSFCell cell_i = row_i.createCell(k);
					cell_i.setCellValue("");
					cell_i.setCellStyle(cellTxtStyle);
				}

			}

		}
		// 创建流
		FileOutputStream fileOut;

		try
		{
			fileOut = new FileOutputStream(CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ TechConsts.USER_TEMPLATE_PATH + File.separator
					+ sf.format(new Date()) + "-USERTEMPLATE" + ".xls");
			fileName = CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ TechConsts.USER_TEMPLATE_PATH + File.separator
					+ sf.format(new Date()) + "-USERTEMPLATE" + ".xls";
			wb.write(fileOut);
			fileOut.close();
			return fileName;
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return fileName;
	}

	private static void createListBox(String[] list, int rownum, int cellnum,
			HSSFSheet sheet)
	{
		// HSSFWorkbook book = new HSSFWorkbook();
		// HSSFSheet sheet = book.createSheet("test");
		// HSSFRow row = sheet.createRow(0);
		// HSSFCell cell = row.createCell(0);
		// cell.setCellValue("请选择");
		CellRangeAddressList regions = new CellRangeAddressList(rownum, 50000,
				cellnum, cellnum);

		DVConstraint constraint = DVConstraint
				.createExplicitListConstraint(list);

		HSSFDataValidation vaildation = new HSSFDataValidation(regions,
				constraint);
		sheet.addValidationData(vaildation);

	}

	private String[] domainvalues(String key)
	{
		return getDoaminValues(key);
	}

	public String[] getCompany()
	{
		return Company;
	}

	public void setCompany(String[] company)
	{
		Company = company;
		// Company = getDoaminValues("COMPANY");
	}

	public String[] getDept()
	{
		return Dept;
	}

	public void setDept(String[] dept)
	{
		this.Dept = dept;
		// this.Dept = getDoaminValues(COMPANY"POSITION"ROLE);
	}

	public String[] getRole()
	{
		return Role;
	}

	public void setRole(String[] role)
	{
		this.Role = role;
		// this.Role = getDoaminValues("ROLE");
	}

}
