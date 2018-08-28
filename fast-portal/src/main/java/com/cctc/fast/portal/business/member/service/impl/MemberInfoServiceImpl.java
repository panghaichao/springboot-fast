package com.cctc.fast.portal.business.member.service.impl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import com.baomidou.dynamic.datasource.DS;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cctc.fast.portal.business.member.entity.MemberInfo;
import com.cctc.fast.portal.business.member.mapper.MemberInfoMapper;
import com.cctc.fast.portal.business.member.service.IMemberInfoService;
import com.cctc.fast.portal.business.member.vo.MemberInfoVo;

@Service
@Transactional
public class MemberInfoServiceImpl extends ServiceImpl<MemberInfoMapper, MemberInfo> implements IMemberInfoService {

	@Autowired
	private MemberInfoMapper memberInfoMapper;
	
	@Override
//	@DS("one")
	public Page<MemberInfoVo> selectMemberInfoListPage(Integer current, Integer size, Map<String, String> searchMap) {
		Page<MemberInfoVo> page = new Page<MemberInfoVo>(current, size);
		page.setRecords(memberInfoMapper.selectMemberInfoListPage(page,searchMap));
		return page;
	}

}
