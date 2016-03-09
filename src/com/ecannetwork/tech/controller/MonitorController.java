package com.ecannetwork.tech.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.module.controller.AjaxResponse;

@Controller
@RequestMapping("monitor")
public class MonitorController
{
	/**
	 * 监控
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model)
	{
		Runtime r = Runtime.getRuntime();
		long total = r.totalMemory() / 1024/1024;
		long free = r.freeMemory() / 1024/1024;

		model.addAttribute("freeMemory", free);
		model.addAttribute("totalMemory", total);
		model.addAttribute("usedMemory", total - free);

		return "tech/monitor/index";
	}

	/**
	 * 强制调用gc
	 * 
	 * @return
	 */
	@RequestMapping("gc")
	public @ResponseBody
	AjaxResponse gc()
	{
		System.gc();

		return new AjaxResponse();
	}

	/**
	 * 输入系统堆栈信息
	 * 
	 * @return
	 */
	@RequestMapping("stack")
	public @ResponseBody
	String stack()
	{
		Map<Thread, StackTraceElement[]> stack = Thread.getAllStackTraces();

		StringBuilder sb = new StringBuilder();
		for (Thread thread : stack.keySet())
		{
			sb.append(
					thread.getId() + "&nbsp;&nbsp;" + thread.getName()
							+ "&nbsp; &nbsp;" + thread.getClass()
							+ "&nbsp; &nbsp;" + thread.getPriority()).append(
					"<br />");

			StackTraceElement[] els = stack.get(thread);
			for (int i = 0; i < els.length; i++)
			{
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;")
						.append(els[i].getClassName()).append(".")
						.append(els[i].getMethodName()).append("(")
						.append(els[i].getLineNumber()).append(")<br />");

			}

			sb.append("<br /><br /><hr />");
			sb.append("<br /><br />");
		}

		return sb.toString();
	}
}
