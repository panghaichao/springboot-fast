package com.cctc.fast.core.tags.shiro;


/**
 * 
 * 判断是否不拥有这个角色
 * 
 * <@shiro.lacksRole name="admin">  
用户[<@shiro.principal/>]不拥有admin角色
</@shiro.lacksRole>   
 * @author Administrator
 *
 */
public class LacksRoleTag extends RoleTag {
	protected boolean showTagBody(String roleName) {
		boolean hasRole = (getSubject() != null) && (getSubject().hasRole(roleName));
		return !hasRole;
	}
}