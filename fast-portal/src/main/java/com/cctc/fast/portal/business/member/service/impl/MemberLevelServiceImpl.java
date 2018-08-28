package com.cctc.fast.portal.business.member.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cctc.fast.portal.business.member.entity.MemberLevel;
import com.cctc.fast.portal.business.member.mapper.MemberLevelMapper;
import com.cctc.fast.portal.business.member.service.IMemberLevelService;

@Service
@Transactional
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevel> implements IMemberLevelService {

}
