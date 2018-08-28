package com.cctc.fast.portal.business.member.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cctc.fast.portal.business.member.entity.MemberCard;
import com.cctc.fast.portal.business.member.mapper.MemberCardMapper;
import com.cctc.fast.portal.business.member.service.IMemberCardService;

@Service
@Transactional
public class MemberCardServiceImpl extends ServiceImpl<MemberCardMapper, MemberCard> implements IMemberCardService {
}
