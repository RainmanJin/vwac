package com.ecannetwork.tech.notification.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecannetwork.core.module.facade.TemplateFacade;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.core.util.MailUtil;
import com.ecannetwork.dto.core.EcanUser;

@Component
public class MailSender
{
	private static ExecutorService exe = Executors.newFixedThreadPool(1);

	@Autowired
	private TemplateFacade templateFacade;

	/**
	 * 
	 * @param toUsers
	 * @param params
	 * @param ftl
	 *            :::WEB-INF/ftl/{notification/mail.userCreate.ftl}
	 * @param title
	 */
	public void send(List<EcanUser> toUsers, Map<String, Object> params,
			String subjectFtl, String contentFtl)
	{
		for (EcanUser u : toUsers)
		{
			this.send(u, params, subjectFtl, contentFtl);
		}
	}

	/**
	 * 
	 * @param toUsers
	 * @param params
	 * @param ftl
	 *            :::WEB-INF/ftl/{notification/mail.userCreate.ftl}
	 * @param title
	 */
	public void send(final EcanUser u, Map<String, Object> params,
			final String subjectFtl, final String contentFtl)
	{
		if (StringUtils.equalsIgnoreCase("1",
				Configs.get("notification.enable")))
		{
			if (u == null || StringUtils.isBlank(u.getEmail()))
			{
				return;
			}
			final Map<String, Object> datas = new HashMap<String, Object>();
			datas.putAll(params);
			datas.put("user", u);
			exe.submit(new Runnable()
			{
				public void run()
				{

					String content = templateFacade.process(datas, contentFtl);
					String subject = templateFacade.process(datas, subjectFtl);

					try
					{
						new MailUtil(Configs.get("notification.mail.smptAddr"),
								Configs.get("notification.mail.smptUsername"),
								Configs.get("notification.mail.smptPassword"))
								.connect()
								.sendEmail(
										Configs.get("notification.mail.smptFrom"),
										u.getEmail(),
										subject,
										content,
										Configs.get("notification.mail.smptEncoding"))
								.disConnect();
					} catch (MessagingException e)
					{
						e.printStackTrace();
					}
				}
			});
		}
	}

	public static void main(String[] args)
	{
		try
		{
			System.out.println(Configs.get("notification.mail.smptFrom"));

			MailUtil m = new MailUtil(
					Configs.get("notification.mail.smptAddr"),
					Configs.get("notification.mail.smptUsername"),
					Configs.get("notification.mail.smptPassword"));
			m.connect()
					.sendEmail(Configs.get("notification.mail.smptFrom"),
							"skoy.ms@qq.com", "标题，主题subject", "content 内容",
							Configs.get("notification.mail.smptEncoding"))
					.disConnect();
		} catch (MessagingException e)
		{
			e.printStackTrace();
		}
	}
}
