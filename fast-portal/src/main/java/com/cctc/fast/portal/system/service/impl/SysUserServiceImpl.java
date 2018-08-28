package com.cctc.fast.portal.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cctc.fast.portal.system.entity.SysUser;
import com.cctc.fast.portal.system.mapper.SysUserMapper;
import com.cctc.fast.portal.system.service.ISysUserService;
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	@Override
	public SysUser getSysUserByUserAccount(String userAccount) {
		EntityWrapper<SysUser> wrapper  = new EntityWrapper<SysUser>();
		wrapper.eq("user_account", userAccount);
		return super.selectOne(wrapper);
	}
}
