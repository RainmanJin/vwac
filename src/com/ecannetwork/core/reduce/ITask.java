package com.ecannetwork.core.reduce;

/**
 * 任务
 * 
 * @author liulibin
 * 
 */
public interface ITask extends Runnable
{
	/**
	 * 合并两个任务:::合并后的任务将在TaskReduceExecutor中被执行
	 * 
	 * @param task
	 */
	public void reduce(ITask task);

	/**
	 * 分组任务的标示符
	 * 
	 * @return
	 */
	public String getGroup();
}
