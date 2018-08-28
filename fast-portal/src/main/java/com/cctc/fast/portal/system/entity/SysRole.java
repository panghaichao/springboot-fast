package com.cctc.fast.portal.system.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cctc.fast.core.base.entity.SuperEntity;

/**
 * 
 * 角色表
 * @author Hejeo
 *
 */
@SuppressWarnings("serial")
@TableName(value = "t_sys_role")
public class SysRole extends SuperEntity<SysRole>{
	
	@Override
	protected Serializable pkVal() {
		return this.roleId;
	}

	
	/**
	 * 主键
	 */
	@TableId(value = "role_id", type = IdType.INPUT)
	private String roleId; 
	
	/**
	 * 角色名称
	 */
	@TableField(value="role_name")
	private String roleName;
	
	/**
	 * 角色名称
	 */
	@TableField(value="role_desc")
	private String roleDesc;
	
	@TableField(exist=false)
	private String[] menus;
	
	public String[] getMenus() {
		return menus;
	}

	public void setMenus(String[] menus) {
		this.menus = menus;
	}
	

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
}
