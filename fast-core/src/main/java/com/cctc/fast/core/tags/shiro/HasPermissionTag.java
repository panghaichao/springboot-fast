package com.cctc.fast.core.tags.shiro;

/**
 * 
 * 判断是否有拥有这个权限
 * 
 * <@shiro.hasPermission name="user:add">  
	用户[<@shiro.principal/>]拥有user:add权限
</@shiro.hasPermission>
 * @author Administrator
 *
 */
public class HasPermissionTag extends PermissionTag {
	protected boolean showTagBody(String p) {
		return isPermitted(p);
	}
}