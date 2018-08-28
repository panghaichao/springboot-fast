package com.cctc.fast.portal.basic.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cctc.fast.portal.basic.entity.NoticeInfo;

/**
 * 通知信息service接口层
 * @author Hejeo
 *
 */
public interface INoticeService extends IService<NoticeInfo> {
	/**
	 * 获取通知列表
	 * @param page
	 * @return
	 */
	Page<NoticeInfo> selectNoticeListPage(Integer current,Integer size,Map<String,Integer> searchMap);
}
