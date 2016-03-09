package com.ecannetwork.core.reduce;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务合并:::异步任务合并， 早高并发环境下可以用到
 * 
 * @author liulibin
 * 
 */
public class TaskReduce
{
	private Map<String, ITask> taskMap = new HashMap<String, ITask>();

	/**
	 * 加入一个任务
	 * 
	 * @param task
	 */
	@SuppressWarnings("null")
	public synchronized void addTask(ITask task)
	{
		ITask t = taskMap.get(task.getGroup());
		if (task != null)
		{
			t.reduce(task);
		} else
		{
			taskMap.put(task.getGroup(), task);
		}
	}

	/**
	 * pop一个任务
	 * 
	 * @param group
	 * @return
	 */
	public synchronized ITask pop(String group)
	{
		return taskMap.remove(group);
	}

	private TaskReduce()
	{
	}

	private static TaskReduce _intance = new TaskReduce();

	public static TaskReduce instance()
	{
		return _intance;
	}
}
