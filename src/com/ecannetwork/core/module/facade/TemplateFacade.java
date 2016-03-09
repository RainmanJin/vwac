package com.ecannetwork.core.module.facade;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;

/**
 * process freemarker template
 * 
 * @author leo
 * 
 */
@Component("templateFacade")
public class TemplateFacade
{
	@Autowired
	private FreeMarkerConfigurer freemarkerConfig;

	/**
	 * process an retrun response as string
	 * 
	 * @param data
	 * @param templateName
	 * @return
	 */
	public String process(Map<String, Object> data, String templateName)
	{
		StringWriter sw = new StringWriter();
		this.process(data, templateName, sw);
		return sw.toString();
	}

	/**
	 * process and write to a file
	 * 
	 * @param data
	 * @param templateName
	 * @param fileName
	 */
	public void process(Map<String, Object> data, String templateName,
			String fileName)
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			this.process(data, templateName, writer);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * process template and write to a writer, close writer auto
	 * 
	 * @param data
	 * @param templateName
	 * @param writer
	 *            will be closed after method execute, what ever exception or
	 *            success
	 */
	public void process(Map<String, Object> data, String templateName,
			Writer writer)
	{
		try
		{
			Template template = freemarkerConfig.getConfiguration()
					.getTemplate(templateName);
			template.process(data, writer);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				writer.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
