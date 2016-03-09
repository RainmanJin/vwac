package com.ecannetwork.core.reduce;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 任务后台执行工具
 * 
 * @author liulibin
 * 
 */
public class TaskReduceExecutor
{
	private Map<String, Long> taskCron = new HashMap<String, Long>();

	/**
	 * 初始化方法
	 */
	public void init()
	{
		if (taskCron != null && taskCron.size() > 0)
		{
			for (String key : taskCron.keySet())
			{
				final String k[] = key.trim().split(",");
				final Long ms = taskCron.get(key);

				Thread t = new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							for (int i = 0; i < k.length; i++)
							{
								if (StringUtils.isNotBlank(k[i]))
								{
									try
									{// 不因为某个task而导致整组调度的task异常
										ITask task = TaskReduce.instance().pop(
												k[i]);
										task.run();
									} catch (Exception e)
									{
										e.printStackTrace();
									}
								}
							}

							Thread.sleep(ms);
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				});
				t.setName("Cron --- " + key);
				t.start();
			}
		}
	}

}
