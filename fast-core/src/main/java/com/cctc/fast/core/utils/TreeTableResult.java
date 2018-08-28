package com.cctc.fast.core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 表格树
 * @author Hejeo
 *
 */
public class TreeTableResult {
	/**
	 * 值
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 子节点
	 */
	private List<TreeTableResult> children = new ArrayList<TreeTableResult>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TreeTableResult> getChildren() {
		return children;
	}
	public void setChildren(List<TreeTableResult> children) {
		this.children = children;
	} 
	
	
}
