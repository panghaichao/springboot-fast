package com.cctc.fast.portal.system.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cctc.fast.core.base.entity.SuperEntity;

/**
 * 
 * 功能菜单表
 * @author Hejeo
 *
 */
@SuppressWarnings("serial")
@TableName(value = "t_sys_menu")
public class SysMenu extends SuperEntity<SysMenu>{
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	
	/**
	 * 主键
	 */
	@TableId(value = "menu_id", type = IdType.INPUT)
	private String id; 
	
	/**
	 * 上级菜单编号
	 */
	@TableField(value="p_menu_id")
	private String pMenuId;
	
	/**
	 * 菜单名称
	 */
	@TableField(value="menu_name")
	private String name;
	
	/**
	 * 菜单权限编码
	 */
	@TableField(value="menu_code")
	private String menuCode;

	/**
	 * 菜单类型  0子系统  1模块 2菜单 3按钮
	 */
	@TableField(value="menu_type")
	private String menuType;
	
	/**
	 * 菜单展开状态
	 */
	@TableField(value="menu_state")
	private String menuState;
	
	/**
	 * 菜单路径
	 */
	@TableField(value="menu_url")
	private String url;
	
	/**
	 * 菜单图标
	 */
	@TableField(value="menu_icon")
	private String menuIcon;
	
	/**
	 * 菜单打开方式  不设置的情况下以窗口形式打开，可选参数：_blank。
	 */
	@TableField(value="menu_open_style")
	private String menuOpenStyle;
	
	/**
	 * 排序
	 */
	@TableField(value="menu_sort")
	private Integer menuSort;

	@TableField(exist=false)
	private List<SysMenu> children=new ArrayList<SysMenu>();


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getpMenuId() {
		return pMenuId;
	}


	public void setpMenuId(String pMenuId) {
		this.pMenuId = pMenuId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMenuCode() {
		return menuCode;
	}


	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}


	public String getMenuType() {
		return menuType;
	}


	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}


	public String getMenuState() {
		return menuState;
	}


	public void setMenuState(String menuState) {
		this.menuState = menuState;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getMenuIcon() {
		return menuIcon;
	}


	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}


	public Integer getMenuSort() {
		return menuSort;
	}


	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}


	public List<SysMenu> getChildren() {
		return children;
	}


	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}


	public String getMenuOpenStyle() {
		return menuOpenStyle;
	}


	public void setMenuOpenStyle(String menuOpenStyle) {
		this.menuOpenStyle = menuOpenStyle;
	}
	
	
}
