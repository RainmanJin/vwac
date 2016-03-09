package com.ecannetwork.core.ajax.tree;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ecannetwork.core.util.JsonFactory;

public class TreeBuilder<K, T>
{
	private ITreeNodeCompare<K, T> compare;
	private Map<K, TreeNode<K, T>> map = new HashMap<K, TreeNode<K, T>>();
	private List<TreeNode<K, T>> roots = new LinkedList<TreeNode<K, T>>();

	public TreeBuilder(ITreeNodeCompare<K, T> compare)
	{
		this.compare = compare;
	}

	/**
	 * 增加一个新节点:::节点增加顺序需要按照先父后子,考虑到性能，暂不提供任意顺序增加
	 * 
	 * @param node
	 */
	public void add(T node)
	{
		K parentKey = compare.getParentKey(node);
		K key = compare.getKey(node);
		TreeNode<K, T> thisNode = new TreeNode<K, T>(node, compare);

		if (parentKey == null)
		{// 没有找到父节点
			map.put(key, thisNode);
			roots.add(thisNode);
		} else
		{
			// 获取到父节点
			TreeNode<K, T> parentNode = map.get(parentKey);
			if (parentNode != null)
			{// 将节点加入到父节点的子节点序列
				parentNode.addChild(thisNode);
				map.put(key, thisNode);
			}
		}
	}

	public void addAll(Collection<T> nodes)
	{
		for (T node : nodes)
		{
			this.add(node);
		}
	}

	/**
	 * 转化为json
	 */
	public String toJson()
	{
		return JsonFactory.toJson(this.roots);
	}

	public String toHtmlOptions(K selectedid)
	{
		return toHtmlOptions("", roots, selectedid);
	}

	private static final String HTML_SELECT_TREE_PREFIX = "----";

	private String toHtmlOptions(String prefix, List<TreeNode<K, T>> list,
			K selectedid)
	{
		StringBuilder sb = new StringBuilder();

		for (TreeNode<K, T> node : list)
		{
			sb.append("<option value=\"")
					.append(compare.getKey(node.getAttr())).append("\"");
			if (selectedid != null
					&& selectedid.equals(compare.getKey(node.getAttr())))
			{
				sb.append(" selected=\"selected\"");
			}
			sb.append(">");
			sb.append(prefix).append(compare.getNodeTitle(node.getAttr()));
			sb.append("</option>\n");

			if (node.getChildren().size() > 0)
			{// 子节点大于0
				sb.append(toHtmlOptions(prefix + HTML_SELECT_TREE_PREFIX,
						node.getChildren(), selectedid));
			}
		}

		return sb.toString();
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TreeBuilder<String, TComDeptDTO> builder = new TreeBuilder<String,
		// TComDeptDTO>(
		// new ITreeNodeCompare<String, TComDeptDTO>()
		// {
		//
		// @Override
		// public String getKey(TComDeptDTO o)
		// {
		// return o.getLevelCode();
		// }
		//
		// @Override
		// public String getParentKey(TComDeptDTO node)
		// {
		// String levelCode = node.getLevelCode();
		// if (levelCode.length() > 4)
		// {
		// return levelCode.substring(0,
		// levelCode.length() - 4);
		// }
		// return null;
		// }
		//
		// @Override
		// public String getNodeTitle(TComDeptDTO node)
		// {
		// return node.getName();
		// }
		// });
		//
		// Random rand = new Random();
		// for (int i = 0; i < 3; i++)
		// {
		// TComDeptDTO dto = new TComDeptDTO();
		// dto.setLevelCode("000" + i);
		// dto.setName("部门_" + i);
		//
		// builder.add(dto);
		//
		// int sub = rand.nextInt(5);
		// for (int j = 0; j < sub; j++)
		// {
		// TComDeptDTO child = new TComDeptDTO();
		// child.setLevelCode("000" + i + "000" + j);
		// child.setName("部门_" + i + "_" + j);
		// builder.add(child);
		// }
		// }
		//
		// System.out.println(builder.toJson());
		// System.out.println(builder.toHtmlOptions("1"));

	}
}
