package com.cctc.fast.portal.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cctc.fast.core.utils.Checkbox;
import com.cctc.fast.portal.system.entity.SysRole;
import com.cctc.fast.portal.system.entity.SysUserRole;
import com.cctc.fast.portal.system.mapper.SysRoleMapper;
import com.cctc.fast.portal.system.mapper.SysUserRoleMapper;
import com.cctc.fast.portal.system.service.ISysUserRoleService;
@Service
@Transactional
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	/**
	 * 根据用户编号获取用户角色列表
	 * @param userId 用户编号
	 * @return
	 */
	@Override
	public List<Checkbox> getUserRoleListByUserId(String userId) {
		EntityWrapper<SysUserRole> sysUserRoleEntityWrapper = new EntityWrapper<SysUserRole>();
		sysUserRoleEntityWrapper.eq("user_id", userId);
		List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectList(sysUserRoleEntityWrapper);
		
		List<SysRole> sysRoleList = sysRoleMapper.selectList(new EntityWrapper<SysRole>());
		
		List<Checkbox> checkboxList=new ArrayList<>();
		Checkbox checkbox=null;
		
		for(SysRole sysRole:sysRoleList){
			checkbox=new Checkbox();
			checkbox.setId(sysRole.getRoleId());
			checkbox.setName(sysRole.getRoleName());
			
			for(SysUserRole sysUserRole :sysUserRoleList){
				if(sysUserRole.getRoleId().equals(sysRole.getRoleId())){
					checkbox.setCheck(true);
				}
			}
			
			checkboxList.add(checkbox);
	    }
		return checkboxList;
	}

	/**
	 * 根据用户编号删除用户和角色关系表
	 * @param userId 用户编号
	 */
	@Override
	public void deleteByUserId(String userId) {
		sysUserRoleMapper.deleteByUserId(userId);
	}

	@Override
	public List<SysUserRole> getSysUserRoleListByUserId(String userId) {
		EntityWrapper<SysUserRole> sysUserRoleEntityWrapper = new EntityWrapper<SysUserRole>();
		sysUserRoleEntityWrapper.eq("user_id", userId);
		List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectList(sysUserRoleEntityWrapper);
		return sysUserRoleList;
	}
}
