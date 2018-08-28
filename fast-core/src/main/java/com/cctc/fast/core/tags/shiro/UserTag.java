package com.cctc.fast.core.tags.shiro;

import freemarker.core.Environment;
import freemarker.log.Logger;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.Map;

/**
 * 
 * 已经登录，或者记住我登录
 * 
 * <@shiro.user>  
	欢迎[<@shiro.principal/>]登录，<a href="/logout.shtml">退出</a>  
	</@shiro.user>  
 * @author Administrator
 *
 */
public class UserTag extends SecureTag {
	static final Logger log = Logger.getLogger("UserTag");

	public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
		if ((getSubject() != null) && (getSubject().getPrincipal() != null)) {
			log.debug("Subject has known identity (aka 'principal'). Tag body will be evaluated.");
			renderBody(env, body);
		} else {
			log.debug(
					"Subject does not exist or have a known identity (aka 'principal'). Tag body will not be evaluated.");
		}
	}
}