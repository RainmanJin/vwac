package com.ecannetwork.core.ajax.tree;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TreeNode<K, T> implements Serializable
{
	public static final String STATE_OPEN = "open";
	public static final String STATE_CLODE = "closed";

	private static final long serialVersionUID = 1L;
	private String data;
	private String state = STATE_OPEN;
	private T attr;
	private List<TreeNode<K, T>> children = new LinkedList<TreeNode<K, T>>();

	public TreeNode(T attr, ITreeNodeCompare<K, T> compare)
	{
		this.data = compare.getNodeTitle(attr);
		this.attr = attr;
		this.state = STATE_OPEN;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public T getAttr()
	{
		return attr;
	}

	public void setAttr(T attr)
	{
		this.attr = attr;
	}

	public List<TreeNode<K, T>> getChildren()
	{
		return children;
	}

	public void setChildren(List<TreeNode<K, T>> children)
	{
		this.children = children;
	}

	public void addChild(TreeNode<K, T> c)
	{
		this.children.add(c);
		this.state = STATE_OPEN;
	}
}
