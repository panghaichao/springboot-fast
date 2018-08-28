package com.cctc.fast.portal.system.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cctc.fast.common.utils.string.StringUtil;
import com.cctc.fast.core.utils.MenuTree;
import com.cctc.fast.core.utils.Tree;
import com.cctc.fast.portal.core.convert.SysMenuToTree;
import com.cctc.fast.portal.system.entity.SysMenu;
import com.cctc.fast.portal.system.mapper.SysMenuMapper;
import com.cctc.fast.portal.system.service.ISysMenuService;
@Service
@Transactional
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

	@Autowired
	private SysMenuMapper sysMenuMapper;
	

	/**
	 * 获取菜单类型为模块的菜单集合
	 */
	@Override
	public List<SysMenu> getMenuNotSuper() {
		EntityWrapper<SysMenu> wrapper  = new EntityWrapper<SysMenu>();
		wrapper.eq("menu_type", "0");
		wrapper.orderBy("menu_sort");
		List<SysMenu> list = sysMenuMapper.selectList(wrapper);
		return list;
	}

	/**
	 * 获取功能菜单表格树数据
	 */
	@Override
	public List<SysMenu> getSysMenuTreeGrid() {
		
		// 查询出所有菜单
		EntityWrapper<SysMenu> wrapper  = new EntityWrapper<SysMenu>();
		wrapper.orderBy("menu_sort");
		List<SysMenu> allSysMenu = sysMenuMapper.selectList(wrapper);
		
		// 查询出菜单类型为模块的菜单集合
		List<SysMenu> returnList = new ArrayList<SysMenu>();
		returnList.addAll(this.getMenuNotSuper());
		
		
		// 获取每个顶层元素的子数据集合  
		for(SysMenu sysMenu : returnList){
			sysMenu.setChildren(getSysMenuTreeGridChild(sysMenu.getId(), allSysMenu));
		}
		
		return returnList;
	}

	/**
	 * 递归查找功能菜单表格树数据子节点
	 * @param id 当前菜单id
	 * @param allSysMenu 要查找的列表
	 * @return
	 */
	private List<SysMenu> getSysMenuTreeGridChild(String id,List<SysMenu> allSysMenu){
		List<SysMenu> childList=new ArrayList<SysMenu>(); 
		String parentId;
		
		//子集的直接子对象  
		for (SysMenu sysMenu : allSysMenu) {
	        // 遍历所有节点，将父菜单id与传过来的id比较
			parentId = sysMenu.getpMenuId();
	    	if(StringUtil.notBlank(parentId)){
	    		if(parentId.equals(id)){
	    			childList.add(sysMenu);
	    		}
	    	}
	    }
		
		//子集的间接子对象  
		for(SysMenu sysMenu : childList){
			sysMenu.setChildren(getSysMenuTreeGridChild(sysMenu.getId(), allSysMenu));
		}
		
		//递归退出条件  
        if(childList.size()==0){  
            return null;  
        }
        return childList;
	}

	/**
	 * 获取功能菜单树数据
	 */
	@Override
	public Map<String,List<Tree>> getSysMenuTreeList() {
		
		// 查询出所有菜单数据
		EntityWrapper<SysMenu> wrapper  = new EntityWrapper<SysMenu>();
		wrapper.orderBy("menu_sort");
		List<SysMenu> allSysMenu = sysMenuMapper.selectList(wrapper);
		
		// 把所有菜单转换成Tree对象
		List<Tree> allTreeList = SysMenuToTree.convertSysMenuToTree(allSysMenu);
		
		
		// 查询出菜单类型为模块的菜单集合
		List<SysMenu> returnList = new ArrayList<SysMenu>();
		returnList.addAll(this.getMenuNotSuper());
		
		// 把菜单类型为模块的菜单集合转换成Tree对象
		List<Tree> returnTreeList = SysMenuToTree.convertSysMenuToTree(returnList);
		
		Map<String,List<Tree>> model = new LinkedHashMap<String,List<Tree>>();
		
		//获取每个顶层元素的子数据集合  
		for(Tree tree : returnTreeList){
			List<Tree> list = new ArrayList<>();
			tree.setChildren(getSysMenuTreeChildList(tree.getValue(), allTreeList));
			list.add(tree);
			model.put(tree.getValue()+"_"+tree.getName(), list);
		}
		return model;
	}
	
	
	/**
	 * 递归查找功能菜单树数据子节点
	 * @param id 当前菜单id
	 * @param allSysMenu 要查找的列表
	 * @return
	 */
	private List<Tree> getSysMenuTreeChildList(String id,List<Tree> allTreeList){
		List<Tree> childList=new ArrayList<Tree>(); 
		String parentId;
		
		//子集的直接子对象  
		for (Tree tree : allTreeList) {
	        // 遍历所有节点，将父菜单id与传过来的id比较
			parentId = tree.getpId();
	    	if(StringUtil.notBlank(parentId)){
	    		if(parentId.equals(id)){
	    			childList.add(tree);
	    		}
	    	}
	    }
		
		//子集的间接子对象  
		for(Tree tree : childList){
			tree.setChildren(getSysMenuTreeChildList(tree.getValue(), allTreeList));
		}
		
		//递归退出条件  
        if(childList.size()==0){  
            return null;  
        }
        return childList;
	}

	
	/**
	 * 根据菜单编号集合获取子系统菜单集合信息
	 */
	@Override
	public List<SysMenu> getAllSubSystemByMenuIds(List<String> ids) {
		List<SysMenu> sysMenuList = new ArrayList<SysMenu>(ids.size());
		EntityWrapper<SysMenu> war =  new EntityWrapper<SysMenu>();
		war.in("menu_id", ids);
		war.eq("menu_type", "0");
		war.orderBy("menu_sort");
		sysMenuList = sysMenuMapper.selectList(war);
		return sysMenuList;
	}

	/**
	 * 根据菜单编号集合获取菜单集合信息
	 */
	@Override
	public List<SysMenu> getSysMenuListByMenuIds(List<String> ids) {
		if(ids.size()<1){
			return null;
		}
		List<SysMenu> sysMenuList = new ArrayList<SysMenu>(ids.size());
		EntityWrapper<SysMenu> war =  new EntityWrapper<SysMenu>();
		war.in("menu_id", ids);
		war.orderBy("menu_sort");
		sysMenuList = sysMenuMapper.selectList(war);
		return sysMenuList;
	}

	
	/**
	 * 获取功能菜单树数据  用于主页左侧功能菜单用
	 * @param menuList 当前用户所有有权限的功能菜单集合
	 * @return MenuTree
	 */
	@Override
	public List<MenuTree> getSysMenuTreeList(List<SysMenu> menuList,SysMenu sysMenu1) {
		List<SysMenu> notSuperMenuList = new ArrayList<SysMenu>();
		
		List<SysMenu> allMenuList = new ArrayList<SysMenu>();
		
		if(menuList==null || menuList.size()==0){
			return null;
		}
		
		for(SysMenu sysMenu:menuList){
			// 菜单类型  1 模块
			if("1".equals(sysMenu.getMenuType()) && sysMenu1.getId().equals(sysMenu.getpMenuId())){
				notSuperMenuList.add(sysMenu);
			}
			
			// 菜单类型  3 按钮
			if(!"3".equals(sysMenu.getMenuType())){
				allMenuList.add(sysMenu);
			}
		}
		
		if(notSuperMenuList.size()==0){
			return null;
		}
		
		if(allMenuList.size()==0){
			return null;
		}
		
		// 把所有菜单转换成MenuTree对象
		List<MenuTree> allMenuTreeList = SysMenuToTree.convertSysMenuToMenuTree(allMenuList);
		
		List<MenuTree> returnMenuTreeList = SysMenuToTree.convertSysMenuToMenuTree(notSuperMenuList);
		
		//获取每个顶层元素的子数据集合  
		for(MenuTree menuTree : returnMenuTreeList){
			menuTree.setChildren(getSysMenuTreeChildList1(menuTree.getId(), allMenuTreeList));
		}
		
		return returnMenuTreeList;
	}
	
	/**
	 * 递归查找功能菜单树数据子节点 用于主页左侧功能菜单
	 * @param id 当前菜单id
	 * @param allSysMenu 要查找的列表
	 * @return
	 */
	private List<MenuTree> getSysMenuTreeChildList1(String id,List<MenuTree> allMenuTreeList){
		List<MenuTree> childList=new ArrayList<MenuTree>(); 
		String parentId;
		
		//子集的直接子对象  
		for (MenuTree menuTree : allMenuTreeList) {
	        // 遍历所有节点，将父菜单id与传过来的id比较
			parentId = menuTree.getPid();
	    	if(StringUtil.notBlank(parentId)){
	    		if(parentId.equals(id)){
	    			childList.add(menuTree);
	    		}
	    	}
	    }
		
		//子集的间接子对象  
		for(MenuTree menuTree : childList){
			menuTree.setChildren(getSysMenuTreeChildList1(menuTree.getId(), allMenuTreeList));
		}
		
		//递归退出条件  
        if(childList.size()==0){  
            return null;  
        }
        return childList;
	}

}
