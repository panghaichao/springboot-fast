package com.cctc.fast.portal.business.member.service;

import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cctc.fast.portal.business.member.entity.MemberInfo;
import com.cctc.fast.portal.business.member.vo.MemberInfoVo;

public interface IMemberInfoService extends IService<MemberInfo>{
	
	/**
	 * 获取会员列表
	 * @param current 当前页
	 * @param size 每页条数
	 * @param searchMap 查询条件
	 * @return
	 */
	Page<MemberInfoVo> selectMemberInfoListPage(Integer current,Integer size,Map<String,String> searchMap);
}
