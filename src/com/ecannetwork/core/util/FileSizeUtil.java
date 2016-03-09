package com.ecannetwork.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

public class FileSizeUtil
{
	public static long getFileSizes(File f)
	{// 取得文件大小
		long s = 0;
		if (f.exists())
		{
			FileInputStream fis = null;
			try
			{
				fis = new FileInputStream(f);
				s = fis.available();
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			} finally
			{
				if (fis != null)
					try
					{
						fis.close();
					} catch (IOException e)
					{
						e.printStackTrace();
					}
			}
		}

		return s;
	}

	// 递归:取得文件夹大小
	public static long getDicSize(File f) 
	{
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++)
		{
			if (flist[i].isDirectory())
			{
				size = size + getDicSize(flist[i]);
			} else
			{
				size = size + flist[i].length();
			}
		}
		return size;
	}

	public static String getFormatFileSize(File file)
	{
		try
		{
			return formetFileSize(getFileSizes(file));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public static String formetFileSize(long fileBytesCount)
	{// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.0");
		String fileSizeString = "";
		if (fileBytesCount < 1024)
		{
			fileSizeString = df.format((double) fileBytesCount) + "B";
		} else if (fileBytesCount < 1048576)
		{
			fileSizeString = df.format((double) fileBytesCount / 1024) + "K";
		} else if (fileBytesCount < 1073741824)
		{
			fileSizeString = df.format((double) fileBytesCount / 1048576) + "M";
		} else
		{
			fileSizeString = df.format((double) fileBytesCount / 1073741824)
					+ "G";
		}
		return fileSizeString;
	}

	public static long getlist(File f)
	{// 递归求取目录文件个数
		long size = 0;
		File flist[] = f.listFiles();
		size = flist.length;
		for (int i = 0; i < flist.length; i++)
		{
			if (flist[i].isDirectory())
			{
				size = size + getlist(flist[i]);
				size--;
			}
		}
		return size;
	}

	public static void main(String[] args)
	{
		System.out.println(getFormatFileSize(new File("/Users/leo/a.apk")));
	}
}
