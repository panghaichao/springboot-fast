package com.cctc.fast.portal.system.controller;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.cctc.fast.common.enums.ExceptionEnum;
import com.cctc.fast.common.utils.id.KeyUtil;
import com.cctc.fast.common.utils.string.StringUtil;
import com.cctc.fast.core.annotation.BussinessLog;
import com.cctc.fast.core.base.controller.BaseController;
import com.cctc.fast.core.exception.FastException;
import com.cctc.fast.core.utils.Result;
import com.cctc.fast.core.utils.ResultUtil;
import com.cctc.fast.core.utils.Tree;
import com.cctc.fast.portal.system.entity.SysMenu;
import com.cctc.fast.portal.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 功能菜单 控制层
 * @author Hejeo
 *
 */
@RestController
@RequestMapping("/sysmenu")
@Api("功能菜单控制层相关的api")
public class SysMenuController extends BaseController{
	@Autowired
	private ISysMenuService sysMenuService;

	/**
	 * 跳转功能菜单管理界面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("sysmenu:index")
	@ApiOperation(value = "跳转功能菜单管理界面", notes = "跳转功能菜单管理界面")
	public ModelAndView toIndex() {
		ModelAndView mv = new ModelAndView("/page/system/sysMenu/menuList");
		return mv;
	}
	
	/**
	 * 获取功能菜单表格树数据
	 */
	@ApiOperation(value = "获取功能菜单表格树数据", notes = "获取功能菜单表格树数据")
	@RequestMapping(value="/menus", method=RequestMethod.GET)
	public Result<Object> menus(ServletRequest request){
		Result<Object> result = ResultUtil.success();
		List<SysMenu> sysMenuList = sysMenuService.getSysMenuTreeGrid();
		result = ResultUtil.success(sysMenuList);
		return result;
	}
	
	/**
	 * 跳转添加功能菜单界面
	 * @param menuId 上级节点编号
	 * @return
	 */
	@ApiOperation(value = "跳转添加功能菜单界面", notes = "跳转添加功能菜单界面")
	@ApiImplicitParam(name = "menuId", value = "菜单编号", paramType = "path", required = true, dataType = "String")
	@RequestMapping(value = "/toadd/{menuId}", method = RequestMethod.GET)
	@RequiresPermissions("sysmenu:add")
    public ModelAndView toAddSysMenuPage(@PathVariable("menuId") String menuId){
    	Map<String,Object> model = new HashMap<String,Object>(); 
    	SysMenu sysMenu = sysMenuService.selectById(menuId);
    	
    	if("0".equals(menuId)){
    		sysMenu = new SysMenu();
    		sysMenu.setId(menuId);
    		sysMenu.setName("");
    		model.put("menuType","0");
    	}else{
    		model.put("menuType",Integer.parseInt(sysMenu.getMenuType())+1);
    	}
    	model.put("sysMenu",sysMenu);
        ModelAndView mv = new ModelAndView("/page/system/sysMenu/menuAdd",model);
        return mv;
    }
	
	/**
     * 添加菜单
     * @param sysMenu
     * @return
     */
    @RequestMapping(value="add",method=RequestMethod.POST)
    @BussinessLog(value="添加菜单")
    @ApiOperation(value = "添加菜单", notes = "添加菜单")
    @ApiImplicitParam(name = "sysMenu", value = "功能菜单实体", required = true, dataType = "SysMenu")
    @RequiresPermissions("sysmenu:add")
    public Result<Object> addSysMenu(@RequestBody SysMenu sysMenu){
    	Result<Object> result = ResultUtil.success();
    	sysMenu.setId(KeyUtil.genUniqueKey());
		sysMenuService.insert(sysMenu);
		return result;
    }
    
    
    /**
     * 跳转修改菜单界面
     * @param roleId 角色编号
     * @return
     */
    @RequestMapping(value = "/menus/{menuId}", method = RequestMethod.GET)
    @ApiOperation(value = "跳转修改菜单界面", notes = "跳转修改菜单界面")
    @ApiImplicitParam(name = "menuId", value = "菜单编号", paramType = "path", required = true, dataType = "String")
    @RequiresPermissions("sysmenu:edit")
    public ModelAndView toEditSysMenuPage(@PathVariable("menuId") String menuId){
    	Map<String,Object> model = new HashMap<String,Object>();  
    	SysMenu sysMenu = sysMenuService.selectById(menuId);
    	model.put("sysMenu",sysMenu);
        ModelAndView mv = new ModelAndView("/page/system/sysMenu/menuEdit",model);
        return mv;
    }
    
    
    /**
     * 更新菜单信息
     * @param sysMenu
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @BussinessLog(value="更新菜单")
    @ApiOperation(value = "更新菜单", notes = "更新菜单")
    @ApiImplicitParam(name = "sysMenu", value = "功能菜单实体", required = true, dataType = "SysMenu")
    @RequiresPermissions("sysmenu:edit")
    public Result<Object> editSysMenu(@RequestBody SysMenu sysMenu){
    	Result<Object> result = ResultUtil.success();
    	if(StringUtil.isBlank(sysMenu.getId())){
    		throw new FastException(ExceptionEnum.NO_ID);
		}
		sysMenuService.updateById(sysMenu);
		return result;
    }
    
    
    /**
     * 删除菜单
     * @param menuId 菜单编号
     * @return
     */
    @RequestMapping(value="/menus/deleteone/{menuId}",method=RequestMethod.POST)
    @BussinessLog(value="删除菜单")
    @ApiOperation(value = "删除菜单", notes = "根据url的菜单编号来指定删除菜单")
	@ApiImplicitParam(name = "menuId", value = "菜单编号", required = true, dataType = "String", paramType = "path")
    @RequiresPermissions("sysmenu:delete")
    public Result<Object> delSysMenu(@PathVariable(value = "menuId",required = true)String menuId){
    	Result<Object> result = ResultUtil.success();
    	sysMenuService.deleteById(menuId);
		return result;
    }
    
    
    /**
	 * 获取功能菜单树数据
	 */
    @ApiIgnore // 使用该注解忽略这个API
	@RequestMapping(value="/getSysMenuList", method=RequestMethod.GET)
	public Result<Object> getSysMenuList(){
		Result<Object> result = ResultUtil.success();
		Map<String,List<Tree>> treeList = sysMenuService.getSysMenuTreeList();
		result = ResultUtil.success(treeList);
		return result;
	}
	
}
