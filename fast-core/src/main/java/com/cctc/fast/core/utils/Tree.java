package com.cctc.fast.core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 树
 * @author Hejeo
 *
 */
public class Tree {
	/**
	 * 父节点
	 */
	private String pId;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 值
	 */
	private String value;
	/**
	 * 是否选中
	 */
	private boolean checked;
	
	/**
	 * 子节点
	 */
	private List<Tree> children = new ArrayList<Tree>();
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<Tree> getChildren() {
		return children;
	}
	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
}
