package com.cctc.fast.portal.core.convert;

import java.util.ArrayList;
import java.util.List;

import com.cctc.fast.common.utils.string.StringUtil;
import com.cctc.fast.core.utils.MenuTree;
import com.cctc.fast.core.utils.Tree;
import com.cctc.fast.portal.system.entity.SysMenu;
/**
 * 功能菜单对象转换树对象工具类
 * @author Hejeo
 *
 */
public class SysMenuToTree {
	
	
	/**
	 * 把功能菜单集合转换成Tree集合
	 * @param sysMenuList
	 * @return
	 */
	public static List<Tree> convertSysMenuToTree(List<SysMenu> sysMenuList){
		List<Tree> treeList = new ArrayList<Tree>();
		if(sysMenuList.size()>0){
			for(SysMenu sysMenu : sysMenuList){
				Tree tree = new Tree();
				tree.setName(sysMenu.getName());
				tree.setValue(sysMenu.getId());
				tree.setpId(sysMenu.getpMenuId());
				tree.setChecked(false);
				treeList.add(tree);
			}
		}
		return treeList;
	}
	
	
	/** 把功能菜单集合转换成Tree集合
	 * @param sysMenuList 所有树
	 * @param menusList 要选中的树
	 * @return
	 */
	public static List<Tree> convertSysMenuToTree(List<SysMenu> sysMenuList,List<String> menusList){
		List<Tree> treeList = new ArrayList<Tree>();
		if(sysMenuList.size()>0){
			for(SysMenu sysMenu : sysMenuList){
				Tree tree = new Tree();
				tree.setName(sysMenu.getName());
				tree.setValue(sysMenu.getId());
				tree.setpId(sysMenu.getpMenuId());
				tree.setChecked(false);
				if(menusList.contains(sysMenu.getId())){
					tree.setChecked(true);
				}
				treeList.add(tree);
			}
		}
		return treeList;
	}
	
	
	
	public static List<MenuTree> convertSysMenuToMenuTree(List<SysMenu> menuList){
		List<MenuTree> menuTreeList = new ArrayList<MenuTree>();
		MenuTree menuTree = null;
		for(SysMenu sysMenu : menuList){
			menuTree = new MenuTree();
			menuTree.setId(sysMenu.getId());
			menuTree.setPid(sysMenu.getpMenuId());
			menuTree.setTitle(sysMenu.getName());
			menuTree.setIcon(sysMenu.getMenuIcon());
			menuTree.setHref(sysMenu.getUrl());
			menuTree.setSpread(false);
			if(StringUtil.notBlank(sysMenu.getMenuState())){
				menuTree.setSpread(true);
			}
			if(StringUtil.notBlank(sysMenu.getMenuOpenStyle())){
				menuTree.setTarget("_blank");
			}
			menuTreeList.add(menuTree);
		}
		return menuTreeList;
	}
}
