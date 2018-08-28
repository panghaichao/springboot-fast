package com.cctc.fast.core.config;

import java.io.IOException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import com.cctc.fast.core.tags.shiro.ShiroTags;
import freemarker.template.TemplateException;
@Configuration
public class FreeMarkerConfigExtend extends FreeMarkerConfigurer{
	@Override  
	public void afterPropertiesSet() throws IOException, TemplateException{  
        super.afterPropertiesSet();
        freemarker.template.Configuration  cfg = this.getConfiguration();
        cfg.setSharedVariable("shiro", new ShiroTags());//shiro标签
        cfg.setClassForTemplateLoading(this.getClass(), "/templates/");
        cfg.setDefaultEncoding("UTF-8");
    } 
}
