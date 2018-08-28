package com.cctc.fast.portal.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.cctc.fast.portal.system.entity.SysUser;

/**
 * 用户service接口层
 * @author Hejeo
 *
 */
public interface ISysUserService extends IService<SysUser>{
	/**
	 * 根据账户获取用户信息
	 * @param userAccount 用户登陆账户
	 * @return
	 */
	public SysUser getSysUserByUserAccount(String userAccount);
}
