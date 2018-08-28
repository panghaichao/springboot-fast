package com.cctc.fast.portal.core.config.shiro;

import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import com.cctc.fast.portal.system.entity.SysMenu;
import com.cctc.fast.portal.system.entity.SysRoleMenu;
import com.cctc.fast.portal.system.entity.SysUser;
import com.cctc.fast.portal.system.entity.SysUserRole;
import com.cctc.fast.portal.system.service.ISysMenuService;
import com.cctc.fast.portal.system.service.ISysRoleMenuService;
import com.cctc.fast.portal.system.service.ISysUserRoleService;
import com.cctc.fast.portal.system.service.ISysUserService;

/**
 * 实现AuthorizingRealm接口用户用户认证
 * @author Hejeo
 *
 */
public class MyShiroRealm extends AuthorizingRealm{
	
	@Autowired
	private ISysUserService sysUserService; 
	
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	
	@Autowired
	private ISysRoleMenuService sysRoleMenuService;
	
	@Autowired
	private ISysMenuService sysMenuService;

	/**
	 * 角色权限和对应权限添加
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		System.out.println("角色权限和对应权限添加");
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		
		SysUser sysUser = (SysUser) principalCollection.getPrimaryPrincipal();
		List<SysUserRole> userRoleList = sysUserRoleService.getSysUserRoleListByUserId(sysUser.getUserId());
		List<SysRoleMenu> roleMenuList = sysRoleMenuService.getSysRoleMenuList(userRoleList);
		
		List<String> ids = new ArrayList<String>(userRoleList.size());
		
		if(roleMenuList!=null && roleMenuList.size()>0){
			for(SysRoleMenu sysRoleMenu : roleMenuList){
				ids.add(sysRoleMenu.getMenuId());
			}
		}
		
		List<SysMenu> menuList = sysMenuService.getSysMenuListByMenuIds(ids);
		if(menuList!=null && menuList.size()>0){
			for(SysMenu sysMenu : menuList)
			authorizationInfo.addStringPermission(sysMenu.getMenuCode());
		}
		return authorizationInfo;
	}
	
	/**
	 * 用户认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		
		//获取用户的输入的账号.
        String username = (String) authenticationToken.getPrincipal();
        SysUser sysUser = sysUserService.getSysUserByUserAccount(username);
        if(sysUser == null){
        	throw new UnknownAccountException();//没找到帐号
        }
        
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		sysUser, //用户名
        		sysUser.getUserPassword(),
                ByteSource.Util.bytes(sysUser.getSalt()),//加密盐
                getName()  //realm name
        );
		
		return authenticationInfo;
	}
}
