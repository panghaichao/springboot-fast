package com.cctc.fast.core.utils;

/**
 * 复选框
 * @author Hejeo
 *
 */
public class Checkbox {
	
	/**
	 * 复选框值
	 */
	private String id;
	/**
	 * 复选框名称
	 */
	private String name;
	/**
	 * 复选框默认未选中
	 */
	private boolean check=false;
	
	
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
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	
	
}
