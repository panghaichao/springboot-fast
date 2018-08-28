package com.cctc.fast.core.tags.shiro;

/**
 * 判断是否没有这个权限
 * 
 * <@shiro.lacksPermission name="user:add">  
	用户[<@shiro.principal/>]不拥有user:add权限
</@shiro.lacksPermission>  
 * @author Administrator
 *
 */
public class LacksPermissionTag extends PermissionTag {
	protected boolean showTagBody(String p) {
		return !isPermitted(p);
	}
}