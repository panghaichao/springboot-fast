package com.cctc.fast.core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 系统左侧功能菜单树
 * @author Hejeo
 *
 */
public class MenuTree {
	
	private String id;
	
	private String pid;
	
	/**
	 * 菜单名称
	 */
	private String title;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 对应的页面链接
	 */
	private String href;
	/**
	 * 子菜单是否展开
	 */
	private boolean spread = false;
	
	/**
	 * 控制对应页面链接的打开方式
	 */
	private String target;
	
	/**
	 * 子菜单数据
	 */
	private List<MenuTree> children = new ArrayList<MenuTree>();
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean isSpread() {
		return spread;
	}

	public void setSpread(boolean spread) {
		this.spread = spread;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<MenuTree> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}
}
