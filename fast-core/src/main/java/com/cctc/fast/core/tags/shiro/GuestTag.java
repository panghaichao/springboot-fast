package com.cctc.fast.core.tags.shiro;

import freemarker.core.Environment;
import freemarker.log.Logger;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.Map;

/**
 * 
 * 游客
 * <@shiro.guest>  
您当前是游客，<a href="javascript:void(0);" class="dropdown-toggle qqlogin" >登录</a>
</@shiro.guest> 
 * @author Administrator
 *
 */
public class GuestTag extends SecureTag {
	private static final Logger log = Logger.getLogger("AuthenticatedTag");

	public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
		if ((getSubject() == null) || (getSubject().getPrincipal() == null)) {
			if (log.isDebugEnabled()) {
				log.debug(
						"Subject does not exist or does not have a known identity (aka 'principal').  Tag body will be evaluated.");
			}

			renderBody(env, body);
		} else if (log.isDebugEnabled()) {
			log.debug("Subject exists or has a known identity (aka 'principal').  Tag body will not be evaluated.");
		}
	}
}