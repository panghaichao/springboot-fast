package com.cctc.fast.portal.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cctc.fast.core.base.entity.SuperEntity;

/**
 * 
 * 用户角色关联表
 * @author Hejeo
 *
 */
@SuppressWarnings("serial")
@TableName(value = "t_sys_user_role")
public class SysUserRole extends SuperEntity<SysUserRole>{
	@Override
	protected Serializable pkVal() {
		return this.userRoleId;
	}

	
	/**
	 * 主键
	 */
	@TableId(value = "user_role_id", type = IdType.INPUT)
	private String userRoleId; 
	
	/**
	 * 用户主键
	 */
	@TableField(value="user_id")
	private String userId;
	
	/**
	 * 角色主键
	 */
	@TableField(value="role_id")
	private String roleId;

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
