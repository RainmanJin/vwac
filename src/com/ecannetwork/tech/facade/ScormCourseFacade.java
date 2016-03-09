package com.ecannetwork.tech.facade;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.core.util.ZIP;
import com.ecannetwork.dto.tech.TechCourse;
import com.ecannetwork.dto.tech.TechScormPkg;
import com.ecannetwork.tech.scorm.Item;
import com.ecannetwork.tech.scorm.Organization;
import com.ecannetwork.tech.scorm.Organizations;
import com.ecannetwork.tech.scorm.Resource;
import com.ecannetwork.tech.service.ScormCorseService;

@Component
public class ScormCourseFacade
{
	private String imsmanifest = "imsmanifest.xml";

	@Autowired
	private ScormCorseService scormService;

	@Autowired
	private CommonService commonService;

	/**
	 * 解压课件包，并读取课件包的信息，正确则返回的response中有Organizations对象， <br />
	 * 错误则为异常消息
	 * 
	 * @param scormFilePath
	 *            scorm包的文件路径
	 * @param uuid
	 *            scorm包的uuid,将作为课件主键存储
	 * @return
	 */
	public AjaxResponse validateScorm(String scormFilePath, String uuid)
	{
		File zipFile = new File(scormFilePath);

		// scorm包与scorm包解压文件夹名称一致
		String path = zipFile.getParent() + File.separator + uuid;
		File coursePathFile = new File(path);
		FileReader imsmanifestReader = null;

		try
		{
			// 解压文件先
			ZIP.unzip(zipFile, coursePathFile);

			imsmanifestReader = new FileReader(path + File.separator
					+ imsmanifest);
			// 解析文件
			Organizations orgs = parseImsmanifest(imsmanifestReader);
			// 保存到数据库
			return new AjaxResponse(true, orgs);
		} catch (Exception e)
		{
			e.printStackTrace();
			return new AjaxResponse(false, I18N
					.parse("i18n.scorm.packageFormatError"));
		} finally
		{
			if (imsmanifestReader != null)
				IOUtils.closeQuietly(imsmanifestReader);

			// 完成后删除课件目录
			if (coursePathFile != null && coursePathFile.exists())
			{// 
				FileUtils.deleteQuietly(coursePathFile);
			}
		}
	}

	/**
	 * 导入课件
	 * 
	 * @param scormFilePath
	 *            scorm文件路径
	 * @return 正常时response中包含课件信息TechCourse
	 * @throws IOException
	 */
	public AjaxResponse publishScorm(String scromid)
	{
		TechScormPkg scorm = (TechScormPkg) this.commonService.get(scromid,
				TechScormPkg.class);

		String scormFilePath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ scorm.getUrl();
		String uuid = UUID.randomUUID();

		File zipFile = new File(scormFilePath);

		// 课件名称与课件ID一致
		String path = zipFile.getParent() + File.separator + uuid;

		FileReader imsmanifestReader = null;

		try
		{
			// 解压文件先
			ZIP.unzip(scormFilePath, path);

			// 解压文件先
			imsmanifestReader = new FileReader(path + File.separator
					+ imsmanifest);
			// 解析文件
			Organizations orgs = parseImsmanifest(imsmanifestReader);

			// 保存到数据库
			TechCourse c = scormService.saveToDatabaseTX(orgs, uuid, scorm);

			return new AjaxResponse(true, c);
		} catch (Exception e)
		{
			e.printStackTrace();
			return new AjaxResponse(false, I18N
					.parse("i18n.scorm.packageFormatError"));
		} finally
		{
			if (imsmanifestReader != null)
				IOUtils.closeQuietly(imsmanifestReader);
		}
	}

	/**
	 * 解析scorm课件
	 * 
	 * @param imsmanifestReader
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	private Organizations parseImsmanifest(FileReader imsmanifestReader)
			throws IOException, DocumentException
	{
		Organizations re = new Organizations();

		String content = IOUtils.toString(imsmanifestReader);
		Document doc = DocumentHelper.parseText(content);

		Map<String, Resource> resourceMap = new HashMap<String, Resource>();

		// 将所有的resouce初始化为一个map
		List resources = doc.getRootElement().elements("resources");
		for (int i = 0; i < resources.size(); i++)
		{
			Element res = (Element) resources.get(i);
			List rs = res.elements("resource");

			for (int j = 0; j < rs.size(); j++)
			{
				Element r = (Element) rs.get(j);
				Resource rsrc = new Resource(r);
				resourceMap.put(rsrc.getIdentifier(), rsrc);
			}
		}
		// 解析结果

		Element organizationsElement = doc.getRootElement().element(
				"organizations");
		String defaultorganization = organizationsElement
				.attributeValue("default");

		List organizations = organizationsElement.elements("organization");

		for (int i = 0; i < organizations.size(); i++)
		{
			Element organization = (Element) organizations.get(i);
			Organization org = new Organization();
			org.setIdentifier(organization.attributeValue("identifier"));
			if (org.getIdentifier().equals(defaultorganization))
			{
				re.setDefaultOrgnization(org);
			}
			List els = organization.elements();
			for (int j = 0; j < els.size(); j++)
			{
				Element e = (Element) els.get(j);
				if (e.getName().equals("title"))
				{
					org.setTitle(e.getTextTrim());
				} else if (e.getName().equals("item"))
				{
					org.getItems().add(new Item(e, resourceMap));
				}
			}
			re.getOrgnizations().add(org);
		}
		return re;
	}

}
