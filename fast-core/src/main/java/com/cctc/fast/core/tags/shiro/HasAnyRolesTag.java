package com.cctc.fast.core.tags.shiro;

import org.apache.shiro.subject.Subject;

/**
 * 
 * 判断是否拥有这些角色的其中一个
 * 
 * <@shiro.hasAnyRoles name="admin,user,member">  
用户[<@shiro.principal/>]拥有角色admin或user或member<br/>  
</@shiro.hasAnyRoles>   
 * @author Administrator
 *
 */
public class HasAnyRolesTag extends RoleTag {
	private static final String ROLE_NAMES_DELIMETER = ",";

	protected boolean showTagBody(String roleNames) {
		boolean hasAnyRole = false;
		Subject subject = getSubject();

		if (subject != null) {
			for (String role : roleNames.split(ROLE_NAMES_DELIMETER)) {
				if (subject.hasRole(role.trim())) {
					hasAnyRole = true;
					break;
				}
			}
		}

		return hasAnyRole;
	}
}