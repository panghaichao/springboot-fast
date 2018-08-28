package com.cctc.fast.portal.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.cctc.fast.core.utils.Tree;
import com.cctc.fast.portal.system.entity.SysRole;
import com.cctc.fast.portal.system.entity.SysRoleMenu;

/**
 * 角色service接口层
 * @author Hejeo
 *
 */
public interface ISysRoleService extends IService<SysRole>{
	
	
	/**
	 * 根据角色编号获取角色和功能菜单集合 
	 * @param roleId 角色编号
	 * @return List<SysRoleMenu>
	 */
	public List<SysRoleMenu> getRoleMenuList(String roleId);
	
	/**
	 * 组装角色和功能菜单树
	 * @param roleMenuList 角色和功能菜单集合 
	 * @return
	 */
	public Map<String,List<Tree>> getRoleMenuTree(List<SysRoleMenu> roleMenuList);
}
