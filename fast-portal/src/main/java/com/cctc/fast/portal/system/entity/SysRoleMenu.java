package com.cctc.fast.portal.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cctc.fast.core.base.entity.SuperEntity;

/**
 * 
 * 角色和功能菜单关联表
 * @author Hejeo
 *
 */
@SuppressWarnings("serial")
@TableName(value = "t_sys_role_menu")
public class SysRoleMenu extends SuperEntity<SysMenu>{
	@Override
	protected Serializable pkVal() {
		return this.roleMenuId;
	}

	
	/**
	 * 主键
	 */
	@TableId(value = "role_menu_id", type = IdType.INPUT)
	private String roleMenuId; 
	
	/**
	 * 角色主键
	 */
	@TableField(value="role_id")
	private String roleId;
	
	/**
	 * 菜单主键
	 */
	@TableField(value="menu_id")
	private String menuId;

	public String getRoleMenuId() {
		return roleMenuId;
	}

	public void setRoleMenuId(String roleMenuId) {
		this.roleMenuId = roleMenuId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
