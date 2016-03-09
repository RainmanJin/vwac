package com.ecannetwork.core.logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecannetwork.core.module.db.dto.DtoSupport;
import com.ecannetwork.core.module.service.CommonService;

/**
 * 异步的日志队列::例如操作日志等
 * 
 * @author liulibin
 * 
 */
public class DBLoggerFacade implements Runnable
{
	private static Log log = LogFactory.getLog(DBLoggerFacade.class);

	private BlockingQueue<DtoSupport> blockQueue = null;

	/**
	 * 异步线程数量，默认为1
	 */
	private Integer threadCount;

	/**
	 * 最大缓冲区数量
	 */
	private Integer maxBufferSize;

	@Autowired
	private CommonService commonService;

	public BlockingQueue<DtoSupport> getBlockQueue()
	{
		return blockQueue;
	}

	public Integer getThreadCount()
	{
		return threadCount;
	}

	public void setThreadCount(Integer threadCount)
	{
		this.threadCount = threadCount;
	}

	public void init()
	{
		if (threadCount == null || threadCount == 0)
		{
			threadCount = 1;
		}

		if (maxBufferSize == null || maxBufferSize <= 0)
		{
			maxBufferSize = 10000;
		}
		this.blockQueue = new LinkedBlockingQueue<DtoSupport>(maxBufferSize);

		for (int i = 0; i < threadCount; i++)
		{
			new Thread(this).start();
		}
	}

	@Override
	public void run()
	{
		while (true)
		{
			try
			{

				List<DtoSupport> list = new ArrayList<DtoSupport>();

				DtoSupport dto = this.blockQueue.take();
				list.add(dto);
				while ((dto = this.blockQueue.poll()) != null)
				{
					list.add(dto);
				}

				this.commonService.saveTX(list);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	public void addDbLog(DtoSupport dto)
	{
		try
		{
			this.blockQueue.add(dto);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error(e);
		}
	}

	public void addDbLog(Collection<DtoSupport> dtos)
	{
		try
		{
			this.blockQueue.addAll(dtos);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error(e);
		}
	}
}
