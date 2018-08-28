package com.cctc.fast.portal.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cctc.fast.common.utils.id.KeyUtil;
import com.cctc.fast.common.utils.string.StringUtil;
import com.cctc.fast.core.annotation.BussinessLog;
import com.cctc.fast.core.base.controller.BaseController;
import com.cctc.fast.core.utils.Result;
import com.cctc.fast.core.utils.ResultUtil;
import com.cctc.fast.core.utils.TableResult;
import com.cctc.fast.core.utils.Tree;
import com.cctc.fast.portal.system.entity.SysRole;
import com.cctc.fast.portal.system.entity.SysRoleMenu;
import com.cctc.fast.portal.system.service.ISysRoleMenuService;
import com.cctc.fast.portal.system.service.ISysRoleService;

/**
 * 角色控制层
 * @author Hejeo
 *
 */
@RestController
@RequestMapping("/sysrole")
public class SysRoleController extends BaseController{
	@Autowired
	private ISysRoleService sysRoleService;
	
	@Autowired
	private ISysRoleMenuService sysRoleMenuService;

	/**
	 * 跳转角色管理界面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("sysrole:index")
	public ModelAndView toIndex() {
		ModelAndView mv = new ModelAndView("/page/system/sysRole/roleList");
		return mv;
	}
	
	/**
	 * 获取角色管理列表数据
	 * @param current 当前页
	 * @param size	每页条数
	 * @param request
	 * @return TableResult
	 */
	@RequestMapping(value="/roles", method=RequestMethod.GET)
	public TableResult<SysRole> getSysRoleList(@RequestParam(value = "page",defaultValue = "1")Integer current,
            						  @RequestParam(value = "limit",defaultValue = "10")Integer size,
            						  ServletRequest request){
		String roleName = request.getParameter("roleName");
		
		EntityWrapper<SysRole> sysRoleEntityWrapper = new EntityWrapper<SysRole>();
		if(StringUtil.notBlank(roleName)){
			sysRoleEntityWrapper.like("role_name", roleName);
		}
		
		Page<SysRole> page = sysRoleService.selectPage(new Page<SysRole>(current, size),sysRoleEntityWrapper);
		return new TableResult<SysRole>(page);
	}
	
	/**
	 * 跳转添加角色界面
	 * @return
	 */
	@RequestMapping(value = "/toadd", method = RequestMethod.GET)
	@RequiresPermissions("sysrole:add")
	public ModelAndView toAddSysRolePage() {
		ModelAndView mv = new ModelAndView("/page/system/sysRole/roleAdd");
		return mv;
	} 
	
	
    /**
     * 添加角色
     * @param sysRole
     * @return
     */
    @RequestMapping(value="add",method=RequestMethod.POST)
    @BussinessLog(value="添加角色")
    @RequiresPermissions("sysrole:add")
    public Result<Object> addSysRole(@RequestBody SysRole sysRole){
    	Result<Object> result = ResultUtil.success();
    	
    	/** 添加角色信息  **/
		sysRole.setRoleId(KeyUtil.genUniqueKey());
		sysRoleService.insert(sysRole);
		/** 添加角色信息  **/
		
		/** 添加角色对应菜单权限信息  **/
		List<SysRoleMenu> sysRoleMenuList = new ArrayList<>();
		if(sysRole.getMenus().length>0){
			String [] menus = sysRole.getMenus();
			
			for(String str : menus){
				SysRoleMenu sysRoleMenu = new SysRoleMenu();
				sysRoleMenu.setRoleMenuId(KeyUtil.genUniqueKey());
	    		sysRoleMenu.setRoleId(sysRole.getRoleId());
	    		sysRoleMenu.setMenuId(str);
	    		sysRoleMenuList.add(sysRoleMenu);
			}
			
			sysRoleMenuService.insertBatch(sysRoleMenuList);
		}
		/** 添加角色对应菜单权限信息  **/
		
		return result;
    }
    
    /**
     * 跳转修改角色界面
     * @param roleId 角色编号
     * @return
     */
    @RequestMapping(value = "/roles/{roleId}", method = RequestMethod.GET)
    @RequiresPermissions("sysrole:edit")
    public ModelAndView toEditSysRolePage(@PathVariable("roleId") String roleId){
    	Map<String,Object> model = new HashMap<String,Object>();  
    	SysRole sysRole = sysRoleService.selectById(roleId);
    	model.put("sysRole",sysRole);
        ModelAndView mv = new ModelAndView("/page/system/sysRole/roleEdit",model);
        return mv;
    }
    
    
    /**
     * 更新角色信息
     * @param sysRole
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @BussinessLog(value="更新角色")
    @RequiresPermissions("sysrole:edit")
    public Result<Object> editSysRole(@RequestBody SysRole sysRole){
    	Result<Object> result = ResultUtil.success();
    	/** 更新角色信息  **/
		sysRoleService.updateById(sysRole);
		/** 更新角色信息  **/
		
		/** 根据角色编号删除角色和菜单关系表  **/
		sysRoleMenuService.deleteRoleMenuByRoleId(sysRole.getRoleId());
		/** 根据角色编号删除角色和菜单关系表  **/
		
		/** 添加角色对应菜单权限信息  **/
		List<SysRoleMenu> sysRoleMenuList = new ArrayList<>();
		if(sysRole.getMenus().length>0){
			String [] menus = sysRole.getMenus();
			
			for(String str : menus){
				SysRoleMenu sysRoleMenu = new SysRoleMenu();
				sysRoleMenu.setRoleMenuId(KeyUtil.genUniqueKey());
	    		sysRoleMenu.setRoleId(sysRole.getRoleId());
	    		sysRoleMenu.setMenuId(str);
	    		sysRoleMenuList.add(sysRoleMenu);
			}
			
			sysRoleMenuService.insertBatch(sysRoleMenuList);
		}
		/** 添加角色对应菜单权限信息  **/
		
		
		reloadShiroUser(getCurrentUser());
		return result;
    }
    
    /**
     * 删除角色
     * @param roleId 角色编号
     * @return
     */
    @RequestMapping(value="/roles/deleteone/{roleId}",method=RequestMethod.POST)
    @BussinessLog(value="删除角色")
    @RequiresPermissions("sysrole:delete")
    public Result<Object> delSysRole(@PathVariable(value = "roleId",required = true)String roleId){
    	Result<Object> result = ResultUtil.success();
    	/*************删除角色******************/
		sysRoleService.deleteById(roleId);
		/*************删除角色******************/
		
		/*************根据角色编号删除角色和菜单关系表 ******************/
		sysRoleMenuService.deleteRoleMenuByRoleId(roleId);
		/*************根据角色编号删除角色和菜单关系表 ******************/
		return result;
    }
    
    /**
     * 删除角色（多个）
     * @param roleIds 角色编号集合
     * @return
     */
    @RequestMapping(value="/roles/deleteall",method=RequestMethod.POST)
    @BussinessLog(value="删除选中角色")
    @RequiresPermissions("sysrole:deleteAll")
    public Result<Object> delSelectSysRole(@RequestParam(value = "roleIds",required = true)String roleIds){
    	Result<Object> result = ResultUtil.success();
    	List<String> list = new ArrayList<String>();  
        list = JSONObject.parseArray(roleIds,String.class);  
		sysRoleService.deleteBatchIds(list);
		return result;
    }
    
    
    /**
     * 根据角色编号获取角色菜单树
     * @param roleId 角色编号
     * @return
     */
    @RequestMapping(value = "/roles/getRoleMenuTree/{roleId}", method = RequestMethod.GET)
    public Result<Object> getRoleMenuTree(@PathVariable(value = "roleId",required = true)String roleId){
    	Result<Object> result = ResultUtil.success();
    	/** 根据角色编号获取角色和功能菜单集合  **/
		List<SysRoleMenu> roleMenuList = sysRoleService.getRoleMenuList(roleId);
		/** 根据角色编号获取角色和功能菜单集合 **/
		
		/** 组装角色和功能菜单树 **/
		Map<String,List<Tree>> treeList = sysRoleService.getRoleMenuTree(roleMenuList);
		result = ResultUtil.success(treeList);
		return result;
    }
}
