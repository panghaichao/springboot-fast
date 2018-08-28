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
import com.cctc.fast.core.utils.Tree;
import com.cctc.fast.portal.core.convert.SysMenuToTree;
import com.cctc.fast.portal.system.entity.SysMenu;
import com.cctc.fast.portal.system.entity.SysRole;
import com.cctc.fast.portal.system.entity.SysRoleMenu;
import com.cctc.fast.portal.system.mapper.SysMenuMapper;
import com.cctc.fast.portal.system.mapper.SysRoleMapper;
import com.cctc.fast.portal.system.mapper.SysRoleMenuMapper;
import com.cctc.fast.portal.system.service.ISysMenuService;
import com.cctc.fast.portal.system.service.ISysRoleService;
@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService{

	@Autowired
	private ISysMenuService sysMenuService;
	@Autowired
	private SysMenuMapper sysMenuMapper;
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;
	
	
	/**
	 * 根据角色编号获取角色和功能菜单集合 
	 * @param roleId 角色编号
	 * @return List<SysRoleMenu>
	 */
	@Override
	public List<SysRoleMenu> getRoleMenuList(String roleId) {
		EntityWrapper<SysRoleMenu> wrapper  = new EntityWrapper<SysRoleMenu>();
		wrapper.eq("role_id", roleId);
		List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(wrapper);
		return sysRoleMenuList;
	}
	
	/**
	 * 组装角色和功能菜单树
	 * @param roleMenuList 角色和功能菜单集合 
	 * @return
	 */
	@Override
	public Map<String,List<Tree>> getRoleMenuTree(List<SysRoleMenu> roleMenuList) {
		
		EntityWrapper<SysMenu> wrapper  = new EntityWrapper<SysMenu>();
		wrapper.orderBy("menu_sort");
		List<SysMenu> allSysMenu = sysMenuMapper.selectList(wrapper);
		
		List<String> list = new ArrayList<String>(roleMenuList.size());
		
		if(roleMenuList.size()>0){
			for(SysRoleMenu sysRoleMenu : roleMenuList){
				list.add(sysRoleMenu.getMenuId());
			}
		}
		
		List<Tree> allTreeList = SysMenuToTree.convertSysMenuToTree(allSysMenu,list);
		
		
		List<SysMenu> returnList = new ArrayList<SysMenu>();
		returnList.addAll(sysMenuService.getMenuNotSuper());
		
		List<Tree> returnTreeList = SysMenuToTree.convertSysMenuToTree(returnList,list);
		
		Map<String,List<Tree>> model = new LinkedHashMap<String,List<Tree>>();
		
		//获取每个顶层元素的子数据集合  
		for(Tree tree : returnTreeList){
			List<Tree> tempList = new ArrayList<>();
			tree.setChildren(getChild1(tree.getValue(), allTreeList));
			tempList.add(tree);
			model.put(tree.getValue()+"_"+tree.getName(), tempList);
		}
		return model;
	}
	
	/**
	 * 递归查找子菜单
	 * @param id 当前菜单id
	 * @param allSysMenu 要查找的列表
	 * @return
	 */
	private List<Tree> getChild1(String id,List<Tree> allTreeList){
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
			tree.setChildren(getChild1(tree.getValue(), allTreeList));
		}
		
		//递归退出条件  
        if(childList.size()==0){  
            return null;  
        }
        return childList;
	}

	

}
