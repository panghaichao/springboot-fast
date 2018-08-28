package com.cctc.fast.portal.basic.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cctc.fast.portal.basic.entity.DictInfo;
/**
 * 数据字典service接口层
 * @author Hejeo
 *
 */
public interface IDictService extends IService<DictInfo>{
	/**
	 * 获取数据字典为类型的字典集合
	 * @return List<DictInfo>
	 */
	public List<DictInfo> getDictNotSuper(String searchVal);
	
	
	/**
	 * 获取数据字典表格树数据
	 * @return
	 */
	public List<DictInfo> getDictTreeGrid(String searchVal);
	
	
	/**
	 * 初始化数据字典到js文件中
	 */
	public void createGlobalParams();
}
