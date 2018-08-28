package com.cctc.fast.portal.system.mapper;

import com.cctc.fast.core.base.mapper.SuperMapper;
import com.cctc.fast.portal.system.entity.SysRoleMenu;

public interface SysRoleMenuMapper extends SuperMapper<SysRoleMenu>{
	/**
	 * 根据角色编号删除角色和菜单关系表 
	 * @param roleId 角色编号
	 */
	public void deleteRoleMenuByRoleId(String roleId);
}
