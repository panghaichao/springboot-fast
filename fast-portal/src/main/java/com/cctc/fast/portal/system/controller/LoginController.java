package com.cctc.fast.portal.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cctc.fast.common.enums.ExceptionEnum;
import com.cctc.fast.core.base.controller.BaseController;
import com.cctc.fast.core.exception.FastException;
import com.cctc.fast.core.utils.MenuTree;
import com.cctc.fast.core.utils.Result;
import com.cctc.fast.core.utils.ResultUtil;
import com.cctc.fast.portal.system.entity.SysMenu;
import com.cctc.fast.portal.system.entity.SysRoleMenu;
import com.cctc.fast.portal.system.entity.SysUser;
import com.cctc.fast.portal.system.entity.SysUserRole;
import com.cctc.fast.portal.system.service.ISysMenuService;
import com.cctc.fast.portal.system.service.ISysRoleMenuService;
import com.cctc.fast.portal.system.service.ISysUserRoleService;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 登录控制层
 * @author Hejeo
 *
 */
@RestController
@ApiIgnore
public class LoginController extends BaseController{
	
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	
	@Autowired
	private ISysRoleMenuService sysRoleMenuService;
	
	@Autowired
	private ISysMenuService sysMenuService;
	
	/**
	 * 跳转登录界面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/login")
	public Result<Object> login(HttpServletRequest request,@RequestBody Map<String,String> map){
		Result<Object> result = ResultUtil.success();
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;    
        HttpSession session = httpServletRequest.getSession();    
	    
        // 取出验证码    
        String validateCode = (String) session.getAttribute("validateCode");    
        // 取出页面的验证码    
        String randomcode=map.get("randomcode");
        if (!randomcode.equals(validateCode)) {   
        	throw new FastException(ExceptionEnum.CODE_ERROR);
        }   
		
		String account=map.get("userName");
        String password=map.get("password");
        UsernamePasswordToken token = new UsernamePasswordToken(account,password,false);  
        Subject currentUser = SecurityUtils.getSubject();  
        
        currentUser.login(token);
		return result;
	}
	
	
	/**
	 * 根据当前登录用户获取所有子系统
	 */
	@RequestMapping(value="/getAllSubSystem", method=RequestMethod.GET)
	public Result<Object> getAllSubSystem(){
		Result<Object> result = ResultUtil.success();
		
		SysUser sysUser = (SysUser) super.getCurrentUser();
		List<SysUserRole> userRoleList = sysUserRoleService.getSysUserRoleListByUserId(sysUser.getUserId());
		List<SysRoleMenu> roleMenuList = sysRoleMenuService.getSysRoleMenuList(userRoleList);
		
		List<String> ids = new ArrayList<String>(userRoleList.size());
		if(roleMenuList!=null && roleMenuList.size()>0){
			for(SysRoleMenu sysRoleMenu : roleMenuList){
				ids.add(sysRoleMenu.getMenuId());
			}
		}
		
		List<SysMenu> allSubSystem = new ArrayList<>();
		if(ids.size()>0){
			allSubSystem = sysMenuService.getAllSubSystemByMenuIds(ids);
		}
		
		result = ResultUtil.success(allSubSystem);
		return result;
	}
	
	/**
	 * 根据当前登录用户权限获取左侧菜单树
	 * @return
	 */
	@RequestMapping(value = "/getMenusTree", method=RequestMethod.GET)
	public Result<Object> getMenusTree(HttpServletRequest request) {
		Result<Object> result = ResultUtil.success();
		
		
		String subSystem  = request.getParameter("subSystem");
		EntityWrapper<SysMenu> war =  new EntityWrapper<SysMenu>();
		war.eq("menu_code", subSystem);
		SysMenu sysMenu = sysMenuService.selectOne(war);
		
		
		
		SysUser sysUser = (SysUser) super.getCurrentUser();
		
		List<SysUserRole> userRoleList = sysUserRoleService.getSysUserRoleListByUserId(sysUser.getUserId());
		List<SysRoleMenu> roleMenuList = sysRoleMenuService.getSysRoleMenuList(userRoleList);
		
		List<String> ids = new ArrayList<String>(userRoleList.size());
		
		if(roleMenuList!=null && roleMenuList.size()>0){
			for(SysRoleMenu sysRoleMenu : roleMenuList){
				ids.add(sysRoleMenu.getMenuId());
			}
		}
		
		List<MenuTree> menuTreeList = new ArrayList<>();
		
		if(ids.size()>0){
			List<SysMenu> menuList = sysMenuService.getSysMenuListByMenuIds(ids);
			menuTreeList = sysMenuService.getSysMenuTreeList(menuList,sysMenu);
		}
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("menus", menuTreeList);
		
		result = ResultUtil.success(map);
		
		return result;
	}
}
