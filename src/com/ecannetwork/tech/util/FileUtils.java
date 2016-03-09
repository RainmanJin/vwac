package com.ecannetwork.tech.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

public class FileUtils
{

	/**
	 * path1 为源文件 path2 为目标文件，本方法的功能是 复制f1文件 为f2
	 * 
	 * @param path1
	 * @param path2
	 */
	public static void headCreate(String path1, String path2)
	{
		File f1 = new File(path1);
		File f2 = new File(path2);
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try
		{
			fi = new FileInputStream(f1);
			fo = new FileOutputStream(f2);
			in = fi.getChannel();// 得到f1 的文件通道
			out = fo.getChannel();// 得到f12的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				fi.close();
				in.close();
				fo.close();
				out.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void copy(String path, String copyPath) throws IOException
	{
		File filePath = new File(path);
		DataInputStream read;
		DataOutputStream write;
		if (filePath.isDirectory())
		{
			File[] list = filePath.listFiles();
			for (int i = 0; i < list.length; i++)
			{
				String newPath = path + File.separator + list[i].getName();
				String newCopyPath = copyPath + File.separator + list[i].getName();
				File copyFileDir = new File(copyPath);
				if (!copyFileDir.exists())
				{
					copyFileDir.mkdirs();
				}
				copy(newPath, newCopyPath);
			}
		}
		else if (filePath.isFile())
		{
			if (!filePath.exists())
			{
				filePath.createNewFile();
			}
			read = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
			write = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(copyPath)));
			byte[] buf = new byte[1024 * 512];
			while (read.read(buf) != -1)
			{
				write.write(buf);
			}
			read.close();
			write.close();
		}
		else
		{
			System.out.println("请输入正确的文件名或路径名");
		}
	}

	// add by yufei,2015-01-24
	/**
	 * 测试给定位置的文件是否存在
	 * 
	 * @param fileFullPath
	 *            文件完整路径名称
	 * @return
	 */
	public static boolean fileExists(String fileFullPath)
	{
		File file = new File(fileFullPath);
		return file.exists();
	}

	/**
	 * 写文件 如果给定路径处有文件，则删除，重建。再写入文件
	 * 
	 * @param fileFullPath
	 *            文件完整路径名称
	 * @param content
	 *            要写入的内容
	 * @return
	 */
	public static boolean writeFile(String fileFullPath, String content)
	{
		boolean isSuccess = false;
		File file = new File(fileFullPath);
		if (file.exists())
		{
			file.delete();
		}
		try
		{
			file.createNewFile();

			FileOutputStream out = new FileOutputStream(file, false); // 如果追加方式用true
			out.write(content.getBytes("utf-8"));// 注意需要转换对应的字符集
			out.close();
			isSuccess = true;
		}
		catch (IOException e)
		{
			isSuccess = false;
			e.printStackTrace();
		}

		return isSuccess;
	}

	/**
	 * 读文件
	 * 
	 * @param fileFullPath文件完整路径
	 * @return
	 */
	public static String readFile(String fileFullPath)
	{
		StringBuffer sb = new StringBuffer();
		String tempstr = null;
		try
		{
			File file = new File(fileFullPath);
			if (!file.exists())
			{
				throw new FileNotFoundException();
			}

			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));// 注意需要转换对应的字符集
			while ((tempstr = br.readLine()) != null)
			{
				sb.append(tempstr);
			}
			br.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return sb.toString();
	}

}
