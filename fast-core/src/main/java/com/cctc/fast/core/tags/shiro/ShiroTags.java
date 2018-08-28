package com.cctc.fast.core.tags.shiro;

import freemarker.template.SimpleHash;

public class ShiroTags extends SimpleHash {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public ShiroTags() {
		put("authenticated", new AuthenticatedTag());
		put("guest", new GuestTag());
		put("hasAnyRoles", new HasAnyRolesTag());
		put("hasPermission", new HasPermissionTag());
		put("hasRole", new HasRoleTag());
		put("lacksPermission", new LacksPermissionTag());
		put("lacksRole", new LacksRoleTag());
		put("notAuthenticated", new NotAuthenticatedTag());
		put("principal", new PrincipalTag());
		put("user", new UserTag());
	}
}