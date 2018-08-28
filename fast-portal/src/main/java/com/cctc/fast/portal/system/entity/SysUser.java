package com.cctc.fast.portal.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cctc.fast.core.base.entity.SuperEntity;

/**
 * 
 * 系统用户表
 * @author Hejeo
 *
 */
@SuppressWarnings("serial")
@TableName(value = "t_sys_user")
public class SysUser extends SuperEntity<SysUser>{
	
	@Override
	protected Serializable pkVal() {
		return this.userId;
	}
	
	/**
	 * 用户主键
	 */
	@TableId(value = "user_id", type = IdType.INPUT)
	private String userId; 
	
	/**
	 * 用户帐号
	 */
	@TableField(value="user_account")
	private String userAccount;
	
	
	/**
	 * 用户密码
	 */
	@TableField(value="user_password")
	private String userPassword;
	
	/**
	 * 加密盐
	 */
	@TableField(value="salt")
	private String salt;
	
	
	/**
	 * 用户姓名
	 */
	@TableField(value="user_name")
	private String userName;
	
	/**
	 * 用户昵称
	 */
	@TableField(value="user_nick_name")
	private String userNickName;
	
	
	/**
	 * 手机号
	 */
	@TableField(value="user_mobile")
	private String userMobile;
	
	/**
	 * 电子邮箱
	 */
	@TableField(value="user_email")
	private String userEmail;
	
	
	/**
	 * 用户性别 0男 1女
	 */
	@TableField(value="user_sex")
	private String userSex;
	
	/**
	 * 用户头像
	 */
	@TableField(value="user_image")
	private String userImage;
	
	/**
	 * 用户描述
	 */
	@TableField(value="user_desc")
	private String userDesc;
	
	
	@TableField(exist=false)
	private String[] roleList;

	
	
	public String[] getRoleList() {
		return roleList;
	}

	public void setRoleList(String[] roleList) {
		this.roleList = roleList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	
}
