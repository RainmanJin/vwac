package com.ecannetwork.core.ajax.tree;

/**
 * 节点比较器
 * 
 * @author leo
 * 
 * @param <K>
 * @param <T>
 */
public interface ITreeNodeCompare<K, T>
{
	/**
	 * 获取id
	 * 
	 * @param o
	 * @return
	 */
	public K getKey(T o);

	/**
	 * 获取父节点:::为根节点时，返回空
	 * 
	 * @param node
	 * @return
	 */
	public K getParentKey(T node);

	/**
	 * 获取节点标题
	 * 
	 * @param node
	 * @return
	 */
	public String getNodeTitle(T node);
}
