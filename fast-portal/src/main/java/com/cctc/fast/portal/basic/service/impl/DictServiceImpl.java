package com.cctc.fast.portal.basic.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cctc.fast.common.utils.string.StringUtil;
import com.cctc.fast.portal.basic.entity.DictInfo;
import com.cctc.fast.portal.basic.mapper.DictMapper;
import com.cctc.fast.portal.basic.service.IDictService;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, DictInfo> implements IDictService {
	
	@Autowired
	private DictMapper dictMapper;
	
	@Override
	public List<DictInfo> getDictNotSuper(String searchVal) {
		EntityWrapper<DictInfo> wrapper  = new EntityWrapper<DictInfo>();
		if(StringUtil.notBlank(searchVal)){
			wrapper.like("dict_name", searchVal);
		}
		wrapper.eq("dict_type", "0");
		wrapper.orderBy("dict_sort");
		List<DictInfo> list = dictMapper.selectList(wrapper);
		return list;
	}

	@Override
	public List<DictInfo> getDictTreeGrid(String searchVal) {
		// 查询出所有菜单
		EntityWrapper<DictInfo> wrapper  = new EntityWrapper<DictInfo>();
		wrapper.orderBy("dict_sort");
		List<DictInfo> allDict = dictMapper.selectList(wrapper);
		
		// 查询出菜单类型为模块的菜单集合
		List<DictInfo> returnList = new ArrayList<DictInfo>();
		returnList.addAll(this.getDictNotSuper(searchVal));
		
		
		// 获取每个顶层元素的子数据集合  
		for(DictInfo dictInfo : returnList){
			dictInfo.setChildren(getDictTreeGridChild(dictInfo.getId(), allDict));
		}
		
		return returnList;
	}
	
	/**
	 * 递归查找功能菜单表格树数据子节点
	 * @param id 当前菜单id
	 * @param allDict 要查找的列表
	 * @return
	 */
	private List<DictInfo> getDictTreeGridChild(String id,List<DictInfo> allDict){
		List<DictInfo> childList=new ArrayList<DictInfo>(); 
		String parentId;
		
		//子集的直接子对象  
		for (DictInfo dictInfo : allDict) {
	        // 遍历所有节点，将父菜单id与传过来的id比较
			parentId = dictInfo.getpDictId();
	    	if(StringUtil.notBlank(parentId)){
	    		if(parentId.equals(id)){
	    			childList.add(dictInfo);
	    		}
	    	}
	    }
		
		//子集的间接子对象  
		for(DictInfo dictInfo : childList){
			dictInfo.setChildren(getDictTreeGridChild(dictInfo.getId(), allDict));
		}
		
		//递归退出条件  
        if(childList.size()==0){  
            return null;  
        }
        return childList;
	}

	/**
	 * 初始化数据字典到js文件中
	 */
	@Override
	public void createGlobalParams() {
		try {
			String path = this.getClass().getResource("/static/assets/js/").getPath();  
			
			File file = new File(path + "dicCode.js");
			if(!file.exists()){
				return;
			}
			
			OutputStream out = new FileOutputStream(file,false);
			String code = "var dicCode = {};";
			out.write(code.getBytes("utf-8"));
			
			List<DictInfo> dictInfoList = getDictTreeGrid("");
			if(dictInfoList!=null && dictInfoList.size()>0){
				code = "dicCode.global = {";
				out.write(code.getBytes("utf-8"));
				
				StringBuffer row = new StringBuffer();
				
				for(DictInfo dictInfo:dictInfoList){
					String dictCode = dictInfo.getDictCode();
					
					row.append("'" + dictCode + "':{");
					if(dictInfo.getChildren().size()>0){
						List<DictInfo> children = dictInfo.getChildren();
						for(DictInfo dict:children){
							String dictKey = dict.getDictKey();
							String dictVal = dict.getDictVal();
							row.append(" '"+dictKey+"' : '"+dictVal+"' ,");
						}
						row.deleteCharAt(row.length() - 1);
					}
					row.append("},");
				}
				row.deleteCharAt(row.length() - 1);
				
				byte ecode[] = (row.toString()+"};").getBytes("utf-8");
				out.write(ecode);
			}else{
				code = "dicCode.global = {};";
    			out.write(code.getBytes("utf-8"));
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
