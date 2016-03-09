package com.ecannetwork.tech.util;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddressList;

public class TestPoiExcelList
{
	public static void main(String[] args)
	{
		// String[] list = { "东软", "华信", "SAP", "海辉" };
		List<String> list1 = new ArrayList<String>();
		list1.add("东软");
		list1.add("华信");
		list1.add("SAP");
		list1.add("海辉");
		String[] list = list1.toArray(new String[list1.size()]);
		new TestPoiExcelList().createListBox(list);
	}

	public void createListBox(String[] list)
	{
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet("test");
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("请选择");
		CellRangeAddressList regions = new CellRangeAddressList(0, 10000, 0, 0);

		DVConstraint constraint = DVConstraint
		        .createExplicitListConstraint(list);

		HSSFDataValidation vaildation = new HSSFDataValidation(regions,
		        constraint);

		sheet.addValidationData(vaildation);
		FileOutputStream fileOut;

		try
		{
			fileOut = new FileOutputStream("c://test.xls");
			book.write(fileOut);
			fileOut.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
