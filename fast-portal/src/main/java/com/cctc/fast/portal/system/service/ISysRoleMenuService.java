package com.cctc.fast.portal.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cctc.fast.portal.system.entity.SysRoleMenu;
import com.cctc.fast.portal.system.entity.SysUserRole;
/**
 * 角色功能菜单service接口层
 * @author Hejeo
 *
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu>{
	/**
	 * 根据角色编号删除角色和菜单关系表 
	 * @param roleId 角色编号
	 */
	public void deleteRoleMenuByRoleId(String roleId);
	
	/**
	 * 根据用户角色列表获取角色对应的菜单列表
	 * @param userRoleList 用户角色列表
	 * @return
	 */
	public List<SysRoleMenu> getSysRoleMenuList(List<SysUserRole> userRoleList);

}
