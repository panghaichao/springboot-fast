package com.cctc.fast.portal.basic.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.cctc.fast.core.base.mapper.SuperMapper;
import com.cctc.fast.portal.basic.entity.NoticeInfo;

public interface NoticeMapper extends SuperMapper<NoticeInfo>{
	
	List<NoticeInfo> selectNoticeListPage(Pagination page,Map<String,Integer> searchMap);
}
