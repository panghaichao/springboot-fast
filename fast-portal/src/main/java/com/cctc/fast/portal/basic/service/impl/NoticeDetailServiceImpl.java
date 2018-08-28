package com.cctc.fast.portal.basic.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cctc.fast.portal.basic.entity.NoticeDetail;
import com.cctc.fast.portal.basic.mapper.NoticeDetailMapper;
import com.cctc.fast.portal.basic.service.INoticeDetailService;

@Service
public class NoticeDetailServiceImpl extends ServiceImpl<NoticeDetailMapper, NoticeDetail> implements INoticeDetailService {
}
