package com.ecannetwork.tech.facade;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecannetwork.core.app.domain.DomainFacade;
import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.tld.facade.CachedDtoViewFacade;
import com.ecannetwork.core.util.BeanContextUtil;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechTestActive;
import com.ecannetwork.tech.controller.bean.testactive.StatRow;
import com.ecannetwork.tech.util.TechConsts;

@Component
public class TestActiveReportGenerator
{
	@Autowired
	private DomainFacade domainFacade;

	/**
	 * 生成个人的总表
	 * 
	 * @param out
	 * @param userid
	 *            用户编号
	 * @param active
	 *            测评活动对象
	 * @param rows
	 *            行信息,List<Row(包含)>
	 * @param stats
	 * @throws Exception
	 */
	public void generator(OutputStream out, String userid,
			TechTestActive active,
			List<com.ecannetwork.tech.controller.bean.testactive.Row> rows,
			List<StatRow> srows) throws Exception
	{
		CachedDtoViewFacade viewDtoFacade = (CachedDtoViewFacade) BeanContextUtil.applicationContext
				.getBean("cachedDtoViewFacade");

		HSSFWorkbook wb = new HSSFWorkbook();
		initStyles(wb);

		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet("Stats");
		Drawing drawing = sheet.createDrawingPatriarch();

		// 生成所有单元格
		int watchMensCount = active.getWatchMenIds().size();
		int columnCount = watchMensCount * 4 + 1 + 2 + 4;
		int rowCount = srows.size() + 3;

		for (short i = 0; i < rowCount; i++)
		{// 创建所有行
			Row row = sheet.createRow(i);
			if (i == 0)
			{
				row.setHeight((short) 1000);
			} else
			{
				row.setHeight((short) 500);
			}
			for (short col = 0; col < columnCount; col++)
			{// 创建所有列
				Cell cell = row.createCell(col);
				switch (i)
				{
				case 0:
					cell.setCellStyle(titleCellStyle);
					break;
				case 1:
					cell.setCellStyle(subtitleCellStyle);
					break;
				case 2:
					if (col == 0)
					{
						cell.setCellStyle(wacherMenTitleCellStyle);
					} else
					{
						cell.setCellStyle(headCellStyle);
					}
					break;
				default:
					if (col == 0)
					{
						cell.setCellStyle(dimTitleCellStyle);
					} else
					{
						cell.setCellStyle(defaultCellStyle);
					}
					break;
				}
			}
		}

		// 第一列宽度
		sheet.setColumnWidth(0, 10000);

		// 合并单元格
		// 首行title
		sheet.addMergedRegion(new CellRangeAddress(0, // first row (0-based)
				0, // last row (0-based)
				0, // first column (0-based)
				columnCount - 1 // last column (0-based)
				));

		Cell titleCell = sheet.getRow(0).getCell(0);
		EcanUser user = (EcanUser) viewDtoFacade.get(userid, "EcanUser");
		titleCell.setCellValue(I18N
				.parse("i18n.testactive.viewStat.reportBlTo")
				+ ":   " + user.getName());

		// 循环合并阶段
		sheet.getRow(1).getCell(0).setCellValue(
				I18N.parse("i18n.testactive.viewStat.jobReq")
						+ ":"
						+ domainFacade.getDomainMap().get("PROTYPE")
								.getByValue(active.getProType())
								.getDomainlabel());
		sheet.getRow(1).getCell(columnCount - 4).setCellValue(
				I18N.parse("i18n.testactive.viewStat.student"));
		sheet.getRow(1).getCell(columnCount - 3).setCellValue(
				I18N.parse("i18n.testactive.viewStat.index"));
		sheet.getRow(1).getCell(columnCount - 2).setCellValue(
				I18N.parse("i18n.testactive.viewStat.absSpaceing"));
		sheet.getRow(1).getCell(columnCount - 1).setCellValue(
				I18N.parse("i18n.testactive.viewStat.watchMenSpaceing2"));

		int baseCol = 1;
		for (com.ecannetwork.tech.controller.bean.testactive.Row r : rows)
		{
			int endCol = active.getWatchMenIds().size() + baseCol - 1;

			if (r.getDimensionId().equals(TechConsts.PTP))
				endCol = baseCol + 2 - 1;

			// 合并单元格
			sheet.addMergedRegion(new CellRangeAddress(1, // first row (0-based)
					1, // last row (0-based)
					baseCol, // first column (0-based)
					endCol // last column (0-based)
					));

			sheet.getRow(1).getCell(baseCol).setCellValue(
					domainFacade.getDomainMap().get("TESTSTEP").getByValue(
							r.getDimensionId()).getI18nLabel());

			baseCol = endCol + 1;
		}

		// 观察员部分
		Row row = sheet.getRow(2);
		row.getCell(0).setCellValue(
				I18N.parse("i18n.testactive.viewStat.watchmen") + ": ");
		int col = 1;
		for (com.ecannetwork.tech.controller.bean.testactive.Row r : rows)
		{
			if (r.getDimensionId().equals(TechConsts.PTP))
			{
				row.getCell(col++).setCellValue(
						I18N.parse("i18n.testactive.viewStat.ptp100"));
				row.getCell(col++).setCellValue(
						I18N.parse("i18n.testactive.viewStat.ptp6"));
			} else
			{
				for (String id : active.getWatchMenIds())
				{
					EcanUser u = (EcanUser) viewDtoFacade.get(id, "EcanUser");
					row.getCell(col++).setCellValue(u.getName());
				}
			}
		}

		DecimalFormat dft = new DecimalFormat("####.##");

		// 循环显示维度信息与得分
		for (int i = 0; i < srows.size(); i++)
		{
			int rowIdx = i + 3;
			StatRow srow = (StatRow) srows.get(i);
			row = sheet.getRow(rowIdx);
			row.getCell(0).setCellValue(srow.getDimName());

			col = 1;
			for (int k = 0; k < rows.size(); k++)
			{// 循环每个一级维度
				com.ecannetwork.tech.controller.bean.testactive.Row r = rows
						.get(k);
				if (r.getDimensionId().equals(TechConsts.PTP))
				{// PTP
					if (srow.getPtpPoint() != null)
					{
						Cell temp = row.getCell(col++);
						temp.setCellStyle(pointCellStyle);
						temp.setCellType(Cell.CELL_TYPE_NUMERIC);
						temp.setCellValue(dft.format(srow.getPtpPoint()));

						temp = row.getCell(col++);
						temp.setCellStyle(pointCellStyle);
						temp.setCellType(Cell.CELL_TYPE_NUMERIC);
						temp.setCellValue(dft
								.format(srow.getPtpPoint() / 20 + 1));
					} else
					{
						col += 2;
					}
				} else
				{// 观察员
					Map pp = (Map) srow.getPoints().get(r.getDimensionId());

					if (pp != null)
					{
						for (Iterator it = active.getWatchMenIds().iterator(); it
								.hasNext();)
						{// 循环每个观察员
							String watchMen = (String) it.next();
							Double p = (Double) pp.get(watchMen);
							if (p != null)
							{
								Cell temp = row.getCell(col++);
								temp.setCellStyle(pointCellStyle);
								temp.setCellType(Cell.CELL_TYPE_NUMERIC);
								temp.setCellValue(dft.format(p));
							} else
							{
								col++;
							}
						}
					} else
					{
						// 合并单元格
						sheet.addMergedRegion(new CellRangeAddress(rowIdx, // first
								// row
								// (0-based)
								rowIdx, // last row (0-based)
								col, // first column (0-based)
								col + active.getWatchMenIds().size() - 1 // last
						// column
								// (0-based)
								));
						col += active.getWatchMenIds().size();
					}
				}
			}

			Double _avg = srow.getAvg28();
			if (_avg != null)
			{
				Cell temp = row.getCell(columnCount - 4);
				temp.setCellType(Cell.CELL_TYPE_NUMERIC);
				temp.setCellStyle(pointCellStyle);
				temp.setCellValue(dft.format(_avg));
			}

			Double pointIndex = srow.getPointIndex();
			Double absToIndex = srow.getAbsToIndex();
			if (pointIndex != null)
			{
				Cell temp = row.getCell(columnCount - 3);
				temp.setCellType(Cell.CELL_TYPE_NUMERIC);
				temp.setCellStyle(pointCellStyle);
				temp.setCellValue(pointIndex);
			}

			if (absToIndex != null)
			{
				Cell temp = row.getCell(columnCount - 2);
				temp.setCellStyle(pointCellStyle);
				temp.setCellValue(Double.valueOf(dft.format(absToIndex)));
				temp.setCellType(Cell.CELL_TYPE_NUMERIC);
			}

			if (srow.isWatchMenStep2())
			{
				Cell temp = row.getCell(columnCount - 1);
				temp.setCellStyle(pointCellStyle);
				temp.setCellValue("**");
			}
		}

		// Write the output to a file
		// FileOutputStream fileOut = new FileOutputStream("/tmp/workbook.xls");
		wb.write(out);
		// fileOut.close();
	}

	/**
	 * 默认样式：浅灰色,剧中
	 */
	private HSSFCellStyle defaultCellStyle = null;

	/**
	 * 浅灰色，黑色，大字体
	 */
	private HSSFCellStyle titleCellStyle = null;
	/**
	 * 大维度，蓝色，白色字体
	 */
	private HSSFCellStyle subtitleCellStyle = null;

	/**
	 * 观察员栏目title
	 */
	private HSSFCellStyle headCellStyle = null;

	/**
	 * 观察员
	 */
	private HSSFCellStyle wacherMenTitleCellStyle = null;

	/**
	 * 维度名称， 居左
	 */
	private HSSFCellStyle dimTitleCellStyle = null;
	/**
	 * 得分信息
	 */
	private HSSFCellStyle pointCellStyle = null;

	private void initStyles(HSSFWorkbook wb)
	{
		String gray = "#F0F0F0";
		int[] colorGray = new int[3];
		colorGray[0] = Integer.parseInt(gray.substring(1, 3), 16);
		colorGray[1] = Integer.parseInt(gray.substring(3, 5), 16);
		colorGray[2] = Integer.parseInt(gray.substring(5, 7), 16);

		String blue = "#2274AC";
		int[] colorBlue = new int[3];
		colorBlue[0] = Integer.parseInt(blue.substring(1, 3), 16);
		colorBlue[1] = Integer.parseInt(blue.substring(3, 5), 16);
		colorBlue[2] = Integer.parseInt(blue.substring(5, 7), 16);

		// 自定义颜色
		HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.GREY_25_PERCENT.index,
				(byte) colorGray[0], (byte) colorGray[1], (byte) colorGray[2]);
		palette.setColorAtIndex(HSSFColor.BLUE.index, (byte) colorBlue[0],
				(byte) colorBlue[1], (byte) colorBlue[2]);

		defaultCellStyle = getWeekStyle(wb, HSSFColor.GREY_25_PERCENT.index);
		defaultCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		defaultCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		titleCellStyle = getWeekStyle(wb, (short) -1);
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 20);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleCellStyle.setFont(font);
		titleCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

		subtitleCellStyle = getWeekStyle(wb, HSSFColor.BLUE.index);
		font = wb.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		subtitleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		subtitleCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		subtitleCellStyle.setFont(font);

		headCellStyle = getWeekStyle(wb, HSSFColor.GREY_25_PERCENT.index);
		headCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		font = wb.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		headCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		headCellStyle.setFont(font);

		wacherMenTitleCellStyle = getWeekStyle(wb,
				HSSFColor.GREY_25_PERCENT.index);
		wacherMenTitleCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		wacherMenTitleCellStyle
				.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		font = wb.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		wacherMenTitleCellStyle.setFont(font);

		dimTitleCellStyle = getWeekStyle(wb, HSSFColor.GREY_25_PERCENT.index);
		dimTitleCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		dimTitleCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		font = wb.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		dimTitleCellStyle.setFont(font);

		pointCellStyle = getWeekStyle(wb, HSSFColor.WHITE.index);
		pointCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		pointCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		font = wb.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		pointCellStyle.setFont(font);
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

		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		return style;
	}
}