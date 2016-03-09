package com.ecannetwork.tech.util;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

public class PoiExcel
{
	public static void main(String[] args)
	{

		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("BT");

		// 创建字体样式
		HSSFFont font = wb.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);
		font.setColor(HSSFColor.PALE_BLUE.index);

		HSSFCellStyle style = wb.createCellStyle();
		// 设置表格样式
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

		// style.setFont(font);// 设置字体

		// 创建Excel的sheet的五行
		HSSFRow row_2 = sheet.createRow(1);
		HSSFCell cell_2_1 = row_2.createCell(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));
		cell_2_1.setCellValue("TMD weekly report");

		HSSFRow row_3 = sheet.createRow(2);
		HSSFCell cell_3_1 = row_3.createCell(1);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));
		cell_3_1.setCellValue("Report Date");

		// 设置导出日期
		HSSFCell cell_3_3 = row_3.createCell(3);
		cell_3_3.setCellValue(sf.format(new Date()));
		style.setFont(font);
		cell_3_3.setCellStyle(style);

		HSSFCell cell_3_5 = row_3.createCell(5);
		cell_3_5.setCellValue("Reporter");
		cell_3_5.setCellStyle(style);

		HSSFCell cell_3_6 = row_3.createCell(6);
		cell_3_6.setCellValue("Zhu Bin");
		cell_3_6.setCellStyle(style);

		// 设置表格内容
		HSSFRow row_4 = sheet.createRow(4);
		HSSFCell cell = row_4.createCell(1);
		HSSFCell cell_4_2 = row_4.createCell(2);
		cell_4_2.setCellStyle(style);
		cell.setCellStyle(style);
		// 合并单元格
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 3, 6));

		HSSFCell cell3 = row_4.createCell(3);
		HSSFCellStyle stylePlanned = wb.createCellStyle();
		stylePlanned.setFillForegroundColor(IndexedColors.GREY_25_PERCENT
		        .getIndex());
		stylePlanned.setFillPattern(CellStyle.SOLID_FOREGROUND);
		stylePlanned.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		stylePlanned.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		stylePlanned.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		stylePlanned.setBorderRight(HSSFCellStyle.BORDER_THIN);
		stylePlanned.setBorderTop(HSSFCellStyle.BORDER_THIN);
		stylePlanned.setWrapText(true);
		font.setColor(HSSFColor.INDIGO.index);
		stylePlanned.setFont(font);
		cell3.setCellValue("Planned");
		cell3.setCellStyle(stylePlanned);

		for (int i = 4; i <= 6; i++)
		{
			cell3 = row_4.createCell(i);
			cell3.setCellStyle(stylePlanned);
		}

		HSSFCell cell7_10 = row_4.createCell(7);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 7, 10));
		font = wb.createFont();
		font.setColor(HSSFColor.INDIGO.index);
		stylePlanned.setFont(font);
		cell7_10.setCellValue("Planned Accumulated");
		cell7_10.setCellStyle(stylePlanned);
		for (int i = 8; i <= 10; i++)
		{
			cell7_10 = row_4.createCell(i);
			cell7_10.setCellStyle(stylePlanned);
		}

		HSSFCell cell11_14 = row_4.createCell(11);
		HSSFCellStyle actualStyle = wb.createCellStyle();
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 11, 14));
		actualStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell11_14.setCellValue("Actual");
		actualStyle.setFillForegroundColor(IndexedColors.TAN.getIndex());
		actualStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		actualStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		actualStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		actualStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		actualStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		actualStyle.setWrapText(true);
		font.setColor(HSSFColor.INDIGO.index);
		actualStyle.setFont(font);
		cell11_14.setCellStyle(actualStyle);
		for (int i = 12; i <= 14; i++)
		{
			cell11_14 = row_4.createCell(i);
			cell11_14.setCellStyle(actualStyle);
		}

		HSSFCell cell15_18 = row_4.createCell(15);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 15, 18));
		cell15_18.setCellValue("Actual Accumulated");
		cell15_18.setCellStyle(actualStyle);
		for (int i = 16; i <= 18; i++)
		{
			cell15_18 = row_4.createCell(i);
			cell15_18.setCellStyle(actualStyle);
		}

		HSSFCell cell_19_24 = row_4.createCell(19);
		HSSFCellStyle trainerStyle = wb.createCellStyle();
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 19, 24));
		trainerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell_19_24.setCellValue("Trainer");
		trainerStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		trainerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		trainerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		trainerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		trainerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		trainerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		trainerStyle.setWrapText(true);
		font.setColor(HSSFColor.INDIGO.index);
		trainerStyle.setFont(font);
		cell_19_24.setCellStyle(trainerStyle);

		// DATE
		HSSFRow row_5 = sheet.createRow(5);
		HSSFCell cell_5_1 = row_5.createCell(1);
		HSSFCell cell_5_2 = row_5.createCell(2);
		cell_5_1.setCellValue("");
		cell_5_2.setCellValue("DATE");
		cell_5_1.setCellStyle(style);
		cell_5_2.setCellStyle(style);

		// Planned
		HSSFCell cell_5_3 = row_5.createCell(3);
		HSSFCell cell_5_4 = row_5.createCell(4);
		HSSFCell cell_5_5 = row_5.createCell(5);
		HSSFCell cell_5_6 = row_5.createCell(6);

		cell_5_3.setCellValue("TMD Plan");
		cell_5_4.setCellValue("Number of course");
		cell_5_5.setCellValue("Participants");
		cell_5_6.setCellValue("Day");
		cell_5_3.setCellStyle(stylePlanned);
		cell_5_4.setCellStyle(stylePlanned);
		cell_5_5.setCellStyle(stylePlanned);
		cell_5_6.setCellStyle(stylePlanned);

		// Planned Accumulated（累计到本周为止）
		HSSFCell cell_5_7 = row_5.createCell(7);
		HSSFCell cell_5_8 = row_5.createCell(8);
		HSSFCell cell_5_9 = row_5.createCell(9);
		HSSFCell cell_5_10 = row_5.createCell(10);
		cell_5_7.setCellValue("TMD Plan");
		cell_5_8.setCellValue("Number of course");
		cell_5_9.setCellValue("Participants");
		cell_5_10.setCellValue("Day");
		cell_5_7.setCellStyle(stylePlanned);
		cell_5_8.setCellStyle(stylePlanned);
		cell_5_9.setCellStyle(stylePlanned);
		cell_5_10.setCellStyle(stylePlanned);

		// Actual
		HSSFCell cell_5_11 = row_5.createCell(11);
		HSSFCell cell_5_12 = row_5.createCell(12);
		HSSFCell cell_5_13 = row_5.createCell(13);
		HSSFCell cell_5_14 = row_5.createCell(14);
		cell_5_11.setCellValue("TMD Plan");
		cell_5_12.setCellValue("Number of course");
		cell_5_13.setCellValue("Participants");
		cell_5_14.setCellValue("Day");
		cell_5_11.setCellStyle(actualStyle);
		cell_5_12.setCellStyle(actualStyle);
		cell_5_13.setCellStyle(actualStyle);
		cell_5_14.setCellStyle(actualStyle);

		// Actual Accumulated
		HSSFCell cell_5_15 = row_5.createCell(15);
		HSSFCell cell_5_16 = row_5.createCell(16);
		HSSFCell cell_5_17 = row_5.createCell(17);
		HSSFCell cell_5_18 = row_5.createCell(18);
		cell_5_15.setCellValue("TMD Plan");
		cell_5_16.setCellValue("Number of course");
		cell_5_17.setCellValue("Participants");
		cell_5_18.setCellValue("Day");
		cell_5_15.setCellStyle(actualStyle);
		cell_5_16.setCellStyle(actualStyle);
		cell_5_17.setCellStyle(actualStyle);
		cell_5_18.setCellStyle(actualStyle);

		// Trainer
		int k = 1;
		for (int i = 19; i < 25; i++)
		{
			HSSFCell cell_5_19 = row_5.createCell(i);
			cell_5_19.setCellValue("Trainer " + k);
			cell_5_19.setCellStyle(trainerStyle);
			k++;
		}

		// 设置W1 行内容
		int t = 1;
		for (int j = 6; j < 58; j++)
		{//星期
			HSSFRow row_6 = sheet.createRow(j);
			HSSFCell cell_6_1 = row_6.createCell(1);
			cell_6_1.setCellValue("W" + t);
			cell_6_1.setCellStyle(style);
			t++;
			for (int i = 2; i < 25; i++)
			{//列
				
				cell_6_1 = row_6.createCell(i);
				cell_6_1.setCellValue("");
				cell_6_1.setCellStyle(style);
			}
		}

		// 设置totle
		HSSFRow row_58 = sheet.createRow(58);
		HSSFCell cell_58_1 = row_58.createCell(1);
		cell_58_1.setCellValue("Total");
		cell_58_1.setCellStyle(style);
		for (int i = 2; i < 25; i++)
		{//合计
			cell_58_1 = row_58.createCell(i);
			cell_58_1.setCellValue("");
			cell_58_1.setCellStyle(style);
		}

		FileOutputStream os;
		try
		{
			String filename = "VWAC Internal Reporting_"
			        + sf.format(new Date()) + ".xls";
			os = new FileOutputStream("/tmp/" + filename);
			wb.write(os);
			os.close();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
