package com.ecannetwork.dto.core;

import java.util.ArrayList;
import java.util.List;

import com.ecannetwork.dto.tech.MwTestdriverfield;

/*
 * 生成字段管理需要的checkbox群
 */
public class GetCheckBoxHtml
{
	private List<MwTestdriverfield> resultList = new ArrayList<MwTestdriverfield>();// 最终的结果集

	/**
	 * 返回树形下拉列表，所有级别
	 * 
	 * @param mwTestdriverfieldOld所有的数据
	 * @param pidvalue第一级父id
	 * @return
	 */
	public String getParentLevel(List<MwTestdriverfield> mwTestdriverfieldOld, Integer pidvalue, String selectFieldf)
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
			mwTestdriverfield.setCName("├&nbsp;" + mwTestdriverfield.getCName());

			resultList.add(mwTestdriverfield);

			Recursion_GetClassTree(mwTestdriverfieldOld, mwTestdriverfield.getId(), "&nbsp;&nbsp;&nbsp;&nbsp;");
		}

		String selfiled[] = selectFieldf.split(",");
		StringBuffer checkhtmlBuffer = new StringBuffer();// 最终的返回结果
		checkhtmlBuffer.append("<ul style='width: 700px'>");
		for (MwTestdriverfield field : resultList)
		{
			checkhtmlBuffer.append("<li><label>");
			checkhtmlBuffer.append(field.getCName());
			checkhtmlBuffer.append("</label>");

			checkhtmlBuffer.append("<input title='");
			checkhtmlBuffer.append(field.getId());
			checkhtmlBuffer.append("' type='checkbox' name='key'");
			checkhtmlBuffer.append(" value='");
			checkhtmlBuffer.append(field.getId());
			checkhtmlBuffer.append("'");

			if (selfiled.length != 0)
			{
				for (int i = 0; i < selfiled.length; i++)
				{
					if (selfiled[i].equals(field.getId()))
					{
						checkhtmlBuffer.append(" checked='checked' ");
					}
				}
			}
			checkhtmlBuffer.append("/></li>");
		}
		checkhtmlBuffer.append("</ul>");

		return checkhtmlBuffer.toString();
	}

	/**
	 * 生成子树形
	 * 
	 * @param mwTestdriverfieldOld数据
	 * @param ID上一级id
	 * @param fontstring前置字符串
	 */
	private void Recursion_GetClassTree(List<MwTestdriverfield> mwTestdriverfieldOld, String ID, String fontstring)
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

			G1 = G1 + "├" + "&nbsp;";
			String Name_Child = G1 + G2;

			child.setCName(Name_Child);
			resultList.add(child);

			String FontString = fontstring + "&nbsp;&nbsp;&nbsp;&nbsp;";
			Recursion_GetClassTree(mwTestdriverfieldOld, child.getId(), FontString);// 生成下一级
		}
	}

}
