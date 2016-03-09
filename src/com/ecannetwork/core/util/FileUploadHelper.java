package com.ecannetwork.core.util;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;

/**
 * 上传文件
 * 
 * @author leo
 * 
 */
public class FileUploadHelper
{
	public static final String EMPTY_FILE = "empty";

	/**
	 * 上传文件到指定目录:::允许无文件
	 * 
	 * @param request
	 * @param storeFileNameWithPath
	 *            上传文件存储路径， 带文件名称， 不带扩展名
	 * @param formFiledName
	 *            表单中的文件字段名称
	 * @param exts
	 *            允许的扩展名类型，扩展名列表， 小写
	 * @return 错误时间，response中包含错误消息，正常时，返回文件扩展名<br />
	 *         没有上传文件时，返回response为true,data为"empty"
	 */
	public static AjaxResponse upload(HttpServletRequest request,
			String storeFileNameWithPath, String formFiledName,
			List<String> exts)
	{
		return FileUploadHelper.upload(request, storeFileNameWithPath,
				formFiledName, exts, true, Long.MAX_VALUE);
	}

	/**
	 * 上传文件到指定目录
	 * 
	 * @param request
	 * @param storeFileNameWithPath
	 *            上传文件存储路径， 带文件名称， 不带扩展名
	 * @param formFiledName
	 *            表单中的文件字段名称
	 * @param exts
	 *            允许的扩展名类型，扩展名列表， 小写
	 * @return 错误时间，response中包含错误消息，正常时，返回文件扩展名
	 */
	public static AjaxResponse upload(HttpServletRequest request,
			String storeFileNameWithPath, String formFiledName,
			List<String> exts, boolean allowEmpty)
	{
		return upload(request, storeFileNameWithPath, formFiledName, exts,
				allowEmpty, Long.MAX_VALUE);
	}

	/**
	 * 上传文件到指定目录
	 * 
	 * @param request
	 * @param storeFileNameWithPath
	 *            上传文件存储路径， 带文件名称， 不带扩展名
	 * @param formFiledName
	 *            表单中的文件字段名称
	 * @param exts
	 *            允许的扩展名类型，扩展名列表， 小写
	 * @return 错误时间，response中包含错误消息，正常时，返回文件扩展名
	 */
	public static AjaxResponse upload(HttpServletRequest request,
			String storeFileNameWithPath, String formFiledName,
			List<String> exts, boolean allowEmpty, long maxBytes)
	{
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartHttpServletRequest
				.getFile(formFiledName);

		String fileName = multipartFile.getOriginalFilename();
		if (StringUtils.isBlank(fileName))
		{
			if (allowEmpty)
			{
				return new AjaxResponse(true, EMPTY_FILE);
			}
		}

		// 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名

		int idx = fileName.lastIndexOf(".");
		if (idx == -1)
		{
			return new AjaxResponse(false, I18N
					.parse("i18n.fileupload.errorType"));
		}

		if (multipartFile.getSize() > maxBytes)
		{
			return new AjaxResponse(false, I18N
					.parse("i18n.fileupload.errorSize"));
		}

		String ext = fileName.substring(idx + 1, fileName.length());
		// 对扩展名进行小写转换

		ext = ext.toLowerCase();

		System.out.println(multipartFile.getSize());

		File file = null;
		if (exts.contains(ext))
		{ // 如果扩展名属于允许上传的类型，则创建文件
			// file = this.creatFolder(typeName, brandName, fileName);
			file = new File(storeFileNameWithPath + "." + ext);

			try
			{
				multipartFile.transferTo(file); // 保存上传的文件
				return new AjaxResponse(true, ext);

			} catch (Exception e)
			{
				e.printStackTrace();
				//return new AjaxResponse(false, e.getMessage());
				return new AjaxResponse(false, I18N.parse("i18n.fileupload.error"));
			}
		} else
		{
			return new AjaxResponse(false, I18N
					.parse("i18n.fileupload.errorType"));
		}
	}
}
