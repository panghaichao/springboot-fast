package com.cctc.fast.portal.system.mapper;

import com.cctc.fast.core.base.mapper.SuperMapper;
import com.cctc.fast.portal.system.entity.SysUserRole;

public interface SysUserRoleMapper extends SuperMapper<SysUserRole>{
	/**
	 * 根据用户编号删除用户和角色关系表
	 * @param userId 用户编号
	 */
	public void deleteByUserId(String userId);
}
