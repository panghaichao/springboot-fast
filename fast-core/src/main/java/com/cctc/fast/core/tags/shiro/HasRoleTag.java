package com.cctc.fast.core.tags.shiro;

/**
 * 
 * 判断是否拥有这个角色
 * 
 * <@shiro.hasRole name="admin">  
	用户[<@shiro.principal/>]拥有角色admin<br/>  
</@shiro.hasRole>   
 * @author Administrator
 *
 */
public class HasRoleTag extends RoleTag {
	protected boolean showTagBody(String roleName) {
		return (getSubject() != null) && (getSubject().hasRole(roleName));
	}
}