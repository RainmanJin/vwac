package com.ecannetwork.tech.facade;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecannetwork.core.app.domain.DomainFacade;
import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.tld.facade.CachedDtoViewFacade;
import com.ecannetwork.core.util.BeanContextUtil;
import com.ecannetwork.dto.core.EcanDomainvalueDTO;
import com.ecannetwork.dto.tech.TechTrainCourse;
import com.ecannetwork.dto.tech.TechTrainPlan;

@Component
public class TrainPlanReportGenerator
{
	@Autowired
	private DomainFacade domainFacade;

	/**
	 * 生成报表
	 * 
	 * @param monthWeeks
	 *            每个月对应的周
	 * @param proTypeCourse
	 *            每个领域对应的课程列表,课程列表中的课程包含了该课程的所有排期计划信息
	 * @throws Exception
	 */
	public void generator(Map<Integer, List<Integer>> monthWeeks,
			Map<String, List<TechTrainCourse>> proTypeCourse, Integer year,
			OutputStream out) throws Exception
	{
		CachedDtoViewFacade viewDtoFacade = (CachedDtoViewFacade) BeanContextUtil.applicationContext
				.getBean("cachedDtoViewFacade");

		HSSFWorkbook wb = new HSSFWorkbook();
		initStyles(wb);

		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet("Plan");
		Drawing drawing = sheet.createDrawingPatriarch();

		int rowCount = 2;// 表头占用两行
		for (List<TechTrainCourse> courses : proTypeCourse.values())
		{// 计算总共有多少课程：）把每个专业的加起来
			rowCount += courses.size();
		}

		for (short i = 0; i < rowCount; i++)
		{// 创建所有行
			Row row = sheet.createRow(i);
			row.setHeight((short) 500);

			for (short col = 0; col < 55; col++)
			{// 创建所有列
				Cell cell = row.createCell(col);
				if (i == 0 || i == 1)
				{
					cell.setCellStyle(titleCellStyle);
				} else
				{
					cell.setCellStyle(defaultCellStyle);
				}
			}
			// row.getCell(i).setCellStyle(weekFillCellStyle);
		}

		for (short col = 0; col < 55; col++)
		{// 设定列宽
			switch (col)
			{
			case 0:
				sheet.setColumnWidth(col, 1500);
				break;
			case 1:
				sheet.setColumnWidth(col, 7000);
				break;
			case 2:
				sheet.setColumnWidth(col, 15000);
				break;

			default:
				sheet.setColumnWidth(col, 1000);
				break;
			}
		}

		// 合并单元格、填充数据
		sheet.addMergedRegion(new CellRangeAddress(0, // first row (0-based)
				1, // last row (0-based)
				0, // first column (0-based)
				0 // last column (0-based)
		));
		sheet.getRow(0).getCell(0).setCellValue(I18N.parse("i18n.sequnce"));
		for (int i = 2; i < rowCount; i++)
		{
			sheet.getRow(i).getCell(0).setCellValue(i - 1);
			sheet.getRow(i).getCell(0).setCellStyle(this.titleCellStyle);
		}

		sheet.addMergedRegion(new CellRangeAddress(0, // first row (0-based)
				1, // last row (0-based)
				1, // first column (0-based)
				1 // last column (0-based)
		));
		sheet.getRow(0).getCell(1)
				.setCellValue(I18N.parse("i18n.trainplan.filed.proType"));

		sheet.addMergedRegion(new CellRangeAddress(0, // first row (0-based)
				1, // last row (0-based)
				2, // first column (0-based)
				2 // last column (0-based)
		));
		sheet.getRow(0).getCell(2)
				.setCellValue(I18N.parse("i18n.trainplan.filed.trainCourse"));

		int beginCol = 3;

		// 合并月份的表头
		for (Integer month : monthWeeks.keySet())
		{// 周
			List<Integer> weeks = monthWeeks.get(month);

			int endCol = beginCol + weeks.size() - 1;

			// 合并月份
			sheet.addMergedRegion(new CellRangeAddress(0, // first row (0-based)
					0, // last row (0-based)
					beginCol, // first column (0-based)
					endCol // last column (0-based)
			));
			Cell cell = sheet.getRow(0).getCell(beginCol);
			// cell.setCellType(Cell.CELL_TYPE_STRING);
			EcanDomainvalueDTO dv = domainFacade.getDomainMap().get("MONTH")
					.getByValue(String.valueOf(month));

			if (dv != null)
				cell.setCellValue(dv.getDomainlabel());
			else
				cell.setCellValue(String.valueOf(month));

			int idx = 0;
			for (int col = beginCol; col <= endCol; col++)
			{
				sheet.getRow(1).getCell(col).setCellValue(weeks.get(idx));
				idx++;
			}

			beginCol += weeks.size();
		}

		int baseRow = 2;
		// 生成数据
		for (String proType : proTypeCourse.keySet())
		{// 循环所有品牌
			List<TechTrainCourse> courses = proTypeCourse.get(proType);

			int endRow = baseRow + courses.size() - 1;
			if (endRow > baseRow)
			{
				// 合并品牌列
				sheet.addMergedRegion(new CellRangeAddress(baseRow, // first row
						// (0-based)
						endRow, // last row (0-based)
						1, // first column (0-based)
						1 // last column (0-based)
				));
			}

			sheet.getRow(baseRow).getCell(1)
					.setCellStyle(proTypeAndCourseCellStyle);
			EcanDomainvalueDTO dv = domainFacade.getDomainMap().get("PROTYPE")
					.getByValue(proType);
			if (dv != null)
			{
				sheet.getRow(baseRow).getCell(1)
						.setCellValue(dv.getDomainlabel());
			} else
			{
				sheet.getRow(baseRow).getCell(1).setCellValue(proType);
			}
			for (int i = 0; i < courses.size(); i++)
			{// 循环所有课程
				TechTrainCourse course = courses.get(i);

				Row row = sheet.getRow(baseRow);

				row.getCell(2).setCellStyle(proTypeAndCourseCellStyle);
				row.getCell(2).setCellValue(course.getName());

				for (int weekCol = 1; weekCol <= 52; weekCol++)
				{
					Cell cell = row.getCell(2 + weekCol);
					TechTrainPlan plan = course.getPlan(weekCol);
					if (plan != null)
					{// 有计划，判定状态
						if (TechTrainPlan.Status.fill.equals(plan.getStatus()))
						{// 待填写
							cell.setCellStyle(weekFillCellStyle);
						} else
						{
							if (TechTrainPlan.Status.plan.equals(plan
									.getStatus()))
							{
								cell.setCellStyle(weekPlanCellStyle);
							} else
							{
								if (TechTrainPlan.Status.confirm.equals(plan
										.getStatus()))
								{
									cell.setCellStyle(weekConfirmCellStyle);
								} else
								{
									if (TechTrainPlan.Status.cancel.equals(plan
											.getStatus()))
									{
										cell.setCellStyle(weekCancelCellStyle);
									}
								}
							}
						}

						String remark = course.getPlanRemarksForXls(weekCol);
						if (StringUtils.isNotBlank(remark))
						{
							// 增加注释
							// When the comment box is visible, have it show in
							// a
							// 1x3 space
							ClientAnchor anchor = createHelper
									.createClientAnchor();
							anchor.setCol1(cell.getColumnIndex());
							anchor.setCol2(cell.getColumnIndex() + 10);
							anchor.setRow1(row.getRowNum());
							anchor.setRow2(row.getRowNum() + 4);

							// Create the comment and set the text+author
							Comment comment = drawing.createCellComment(anchor);
							//
							// StringBuilder sb = new StringBuilder();
							// sb.append(I18N.parse("i18n.trainplan.filed.teacher"))
							// .append(": ");
							// for (String teacherid : plan.getTeacherIds())
							// {
							// EcanUser user = (EcanUser) viewDtoFacade.get(
							// teacherid, "EcanUser");
							// sb.append(user.getName()).append(", ");
							// }
							// sb.append("\n");
							// sb.append(I18N.parse("i18n.trainplan.filed.remark"))
							// .append(": ");
							// sb.append(plan.getRemark() == null ? "" : plan
							// .getRemark());
							RichTextString str = createHelper
									.createRichTextString(remark);
							comment.setString(str);
							// comment.setAuthor("Apache POI");

							// Assign the comment to the cell
							cell.setCellComment(comment);
						}
					} else
					{
						if (HolidayFacade.isHolidayWeek(year, weekCol))
						{// 假期
							row.getCell(2 + weekCol).setCellStyle(
									weekHolidayCellStyle);
						}
					}
				}

				baseRow++;
			}
		}

		// Write the output to a file
		// FileOutputStream fileOut = new FileOutputStream("/tmp/workbook.xls");
		wb.write(out);
		// fileOut.close();
	}

	private HSSFCellStyle defaultCellStyle = null;
	private HSSFCellStyle proTypeAndCourseCellStyle = null;
	private HSSFCellStyle titleCellStyle = null;
	private HSSFCellStyle weekFillCellStyle = null;
	private HSSFCellStyle weekPlanCellStyle = null;
	private HSSFCellStyle weekConfirmCellStyle = null;
	private HSSFCellStyle weekCancelCellStyle = null;
	private HSSFCellStyle weekHolidayCellStyle = null;

	private void initStyles(HSSFWorkbook wb)
	{
		defaultCellStyle = getWeekStyle(wb, (short) -1);

		titleCellStyle = getWeekStyle(wb, HSSFColor.GREY_25_PERCENT.index);

		titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		proTypeAndCourseCellStyle = getWeekStyle(wb, (short) -1);
		proTypeAndCourseCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		proTypeAndCourseCellStyle
				.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		weekFillCellStyle = getWeekStyle(wb, HSSFColor.DARK_TEAL.index);
		weekPlanCellStyle = getWeekStyle(wb, HSSFColor.DARK_TEAL.index);
		weekConfirmCellStyle = getWeekStyle(wb, HSSFColor.GREEN.index);
		weekCancelCellStyle = getWeekStyle(wb, HSSFColor.LIGHT_ORANGE.index);

		weekHolidayCellStyle = getWeekStyle(wb, HSSFColor.DARK_RED.index);
	}

	/**
	 * 获取一个样式
	 * 
	 * @param wb
	 * @param colorIndex
	 * @return
	 */
	private HSSFCellStyle getWeekStyle(HSSFWorkbook wb, short colorIndex)
	{
		HSSFCellStyle style = wb.createCellStyle();
		if (colorIndex != -1)
		{
			style.setFillForegroundColor(colorIndex);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		}

		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		return style;
	}
}