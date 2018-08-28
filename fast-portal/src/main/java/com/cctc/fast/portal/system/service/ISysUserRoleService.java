package com.cctc.fast.portal.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cctc.fast.core.utils.Checkbox;
import com.cctc.fast.portal.system.entity.SysUserRole;


/**
 * 
 * 用户角色关联 service接口
 * @author Hejeo
 *
 */
public interface ISysUserRoleService extends IService<SysUserRole>{
	/**
	 * 根据用户编号获取用户角色列表
	 * @param userId 用户编号
	 * @return
	 */
	public List<Checkbox> getUserRoleListByUserId(String userId);
	
	/**
	 * 根据用户编号删除用户和角色关系表
	 * @param userId 用户编号
	 */
	public void deleteByUserId(String userId);
	
	
	/**
	 * 根据用户编号获取用户角色列表
	 * @param userId 用户编号
	 * @return
	 */
	public List<SysUserRole> getSysUserRoleListByUserId(String userId);
}
