package com.cctc.fast.portal.business.member.mapper;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.cctc.fast.core.base.mapper.SuperMapper;
import com.cctc.fast.portal.business.member.entity.MemberInfo;
import com.cctc.fast.portal.business.member.vo.MemberInfoVo;

public interface MemberInfoMapper extends SuperMapper<MemberInfo> {
	
	/**
	 * 获取会员列表
	 * @param page
	 * @param searchMap
	 * @return
	 */
	List<MemberInfoVo> selectMemberInfoListPage(Pagination page,Map<String,String> searchMap);
}
