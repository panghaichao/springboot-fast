package com.cctc.fast.portal.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.cctc.fast.core.utils.MenuTree;
import com.cctc.fast.core.utils.Tree;
import com.cctc.fast.portal.system.entity.SysMenu;
/**
 * 功能菜单service接口层
 * @author Hejeo
 *
 */
public interface ISysMenuService extends IService<SysMenu>{
	/**
	 * 获取菜单类型为模块的菜单集合
	 * @return List<SysMenu>
	 */
	public List<SysMenu> getMenuNotSuper();
	
	/**
	 * 获取功能菜单表格树数据
	 * @return
	 */
	public List<SysMenu> getSysMenuTreeGrid();
	
	/**
	 * 获取功能菜单树数据
	 * @return
	 */
	public Map<String,List<Tree>> getSysMenuTreeList();
	
	/**
	 * 根据菜单编号集合获取子系统菜单集合信息
	 */
	public List<SysMenu> getAllSubSystemByMenuIds(List<String> ids);
	
	/**
	 * 根据菜单编号集合获取菜单集合信息
	 * @param ids 菜单编号集合
	 * @return
	 */
	public List<SysMenu> getSysMenuListByMenuIds(List<String> ids);
	
	
	/**
	 * 获取功能菜单树数据  用于主页左侧功能菜单用
	 * @param menuList 当前用户所有有权限的功能菜单集合
	 * @return MenuTree
	 */
	public List<MenuTree> getSysMenuTreeList(List<SysMenu> menuList,SysMenu sysMenu);
}
