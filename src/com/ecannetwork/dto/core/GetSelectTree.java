package com.ecannetwork.dto.core;

import java.util.ArrayList;
import java.util.List;

import com.ecannetwork.dto.tech.MwTestdriverfield;

//生成父级树形选择下拉列表
public class GetSelectTree
{
	private static List<MwTestdriverfield> resultList = new ArrayList<MwTestdriverfield>();// 最终的结果集

	/**
	 * 返回树形下拉列表，所有级别
	 * 
	 * @param mwTestdriverfieldOld所有的数据
	 * @param pidvalue第一级父id
	 * @return
	 */
	public static List<MwTestdriverfield> getParentLevel(List<MwTestdriverfield> mwTestdriverfieldOld, Integer pidvalue)
	{
		List<MwTestdriverfield> firstList = new ArrayList<MwTestdriverfield>();// 第一级

		for (MwTestdriverfield field : mwTestdriverfieldOld)
		{
			if (field.getParentId().equals(pidvalue))
			{
				firstList.add(field);
			}
		}

		for (MwTestdriverfield mwTestdriverfield : firstList)
		{
			mwTestdriverfield.setCName("├&nbsp;&nbsp;" + mwTestdriverfield.getCName());

			resultList.add(mwTestdriverfield);

			Recursion_GetClassTree(mwTestdriverfieldOld, mwTestdriverfield.getId(), "&nbsp;&nbsp;");
		}

		return resultList;
	}

	/**
	 * 生成子树形
	 * 
	 * @param mwTestdriverfieldOld数据
	 * @param ID上一级id
	 * @param fontstring前置字符串
	 */
	private static void Recursion_GetClassTree(List<MwTestdriverfield> mwTestdriverfieldOld, String ID, String fontstring)
	{
		List<MwTestdriverfield> childList = new ArrayList<MwTestdriverfield>();// 子级

		for (MwTestdriverfield child : mwTestdriverfieldOld)
		{
			if (child.getParentId().equals(Integer.valueOf(ID)))
			{
				childList.add(child);
			}
		}

		for (MwTestdriverfield child : childList)
		{
			String Gname = fontstring + child.getCName();
			String G1 = null;
			String G2 = null;

			G1 = Gname.substring(0, Gname.lastIndexOf("&nbsp;") + 6);
			G2 = Gname.substring(Gname.lastIndexOf("&nbsp;") + 6, Gname.length());

			G1 = G1 + "├" + "&nbsp;&nbsp;";
			String Name_Child = G1 + G2;

			child.setCName(Name_Child);
			resultList.add(child);
			String FontString = fontstring + "&nbsp;&nbsp;";

			Recursion_GetClassTree(mwTestdriverfieldOld, child.getId(), FontString);// 生成下一级
		}
	}

	/**
	 * 返回指定级别的树形
	 * 
	 * @param level树形级别
	 *            。&nbsp;&nbsp;长度为12。12为一个单位,13，则两级，25，则三级
	 * @param mwTestdriverfieldOld
	 * @param pidvalue
	 * @return
	 */
	public static List<MwTestdriverfield> getParentLevel(Integer level, List<MwTestdriverfield> mwTestdriverfieldOld, Integer pidvalue)
	{
		List<MwTestdriverfield> firstList = new ArrayList<MwTestdriverfield>();// 第一级

		for (MwTestdriverfield field : mwTestdriverfieldOld)
		{
			if (field.getParentId().equals(pidvalue))
			{
				firstList.add(field);
			}
		}

		for (MwTestdriverfield mwTestdriverfield : firstList)
		{
			mwTestdriverfield.setCName("├&nbsp;&nbsp;" + mwTestdriverfield.getCName());

			resultList.add(mwTestdriverfield);

			Recursion_GetClassTree(level, mwTestdriverfieldOld, mwTestdriverfield.getId(), "&nbsp;&nbsp;");
		}

		return resultList;
	}

	/**
	 * 生成子级
	 * 
	 * @param level控制循环次数
	 * @param mwTestdriverfieldOld
	 * @param ID
	 * @param fontstring
	 */
	private static void Recursion_GetClassTree(Integer level, List<MwTestdriverfield> mwTestdriverfieldOld, String ID, String fontstring)
	{
		List<MwTestdriverfield> childList = new ArrayList<MwTestdriverfield>();// 子级

		for (MwTestdriverfield child : mwTestdriverfieldOld)
		{
			if (child.getParentId().equals(Integer.valueOf(ID)))
			{
				childList.add(child);
			}
		}

		for (MwTestdriverfield child : childList)
		{
			String Gname = fontstring + child.getCName();
			String G1 = null;
			String G2 = null;

			G1 = Gname.substring(0, Gname.lastIndexOf("&nbsp;") + 6);
			G2 = Gname.substring(Gname.lastIndexOf("&nbsp;") + 6, Gname.length());

			G1 = G1 + "├" + "&nbsp;&nbsp;";
			String Name_Child = G1 + G2;

			child.setCName(Name_Child);
			resultList.add(child);
			String FontString = fontstring + "&nbsp;&nbsp;";

			// 生成指定级别的树形
			if (fontstring.length() < level)
			{
				Recursion_GetClassTree(level, mwTestdriverfieldOld, child.getId(), FontString);// 生成下一级
			}
		}
	}

}
