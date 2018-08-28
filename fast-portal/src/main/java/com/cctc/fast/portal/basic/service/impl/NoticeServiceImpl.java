package com.cctc.fast.portal.basic.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cctc.fast.portal.basic.entity.NoticeInfo;
import com.cctc.fast.portal.basic.mapper.NoticeMapper;
import com.cctc.fast.portal.basic.service.INoticeService;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, NoticeInfo> implements INoticeService {

	@Override
	public Page<NoticeInfo> selectNoticeListPage(Integer current, Integer size,Map<String,Integer> searchMap) {
		Page<NoticeInfo> page = new Page<NoticeInfo>(current, size);
		page.setRecords(this.baseMapper.selectNoticeListPage(page,searchMap));
		return page;
	}

}
