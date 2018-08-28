package com.cctc.fast.portal.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cctc.fast.portal.system.entity.SysRoleMenu;
import com.cctc.fast.portal.system.entity.SysUserRole;
import com.cctc.fast.portal.system.mapper.SysRoleMenuMapper;
import com.cctc.fast.portal.system.service.ISysRoleMenuService;
@Service
@Transactional
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

	@Autowired SysRoleMenuMapper sysRoleMenuMapper;
	
	/**
	 * 根据角色编号删除角色和菜单关系表 
	 * @param roleId 角色编号
	 */
	@Override
	public void deleteRoleMenuByRoleId(String roleId) {
		sysRoleMenuMapper.deleteRoleMenuByRoleId(roleId);
	}

	@Override
	public List<SysRoleMenu> getSysRoleMenuList(List<SysUserRole> userRoleList) {
		List<SysRoleMenu> sysRoleMenuList = new ArrayList<SysRoleMenu>();
		
		if(userRoleList==null || userRoleList.size()==0){
			return sysRoleMenuList;
		}
		
		List<String> ids = new ArrayList<String>(userRoleList.size());
		
		for(SysUserRole sysUserRole : userRoleList){
			ids.add(sysUserRole.getRoleId());
		}
		
		EntityWrapper<SysRoleMenu> war =  new EntityWrapper<SysRoleMenu>();
		war.in("role_id", ids);
		
		sysRoleMenuList = sysRoleMenuMapper.selectList(war);
		
		return sysRoleMenuList;
	}

}
