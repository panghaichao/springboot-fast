package com.cctc.fast.portal.core.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.cctc.fast.portal.basic.service.IDictService;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ApplicationStartedEventListener implements ApplicationListener<ContextRefreshedEvent>{
	private final static Logger logger = LoggerFactory.getLogger(ApplicationStartedEventListener.class);
	
	public static String REALPATH = null;
	
	@Autowired
	private IDictService dictService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){
			
			ApplicationContext applicationContext = event.getApplicationContext();
			WebApplicationContext webApplicationContext = (WebApplicationContext)applicationContext;
		    ServletContext servletContext = webApplicationContext.getServletContext();
		    REALPATH = servletContext.getRealPath("/");
			
			logger.info("初始化系统参数[生成web端JS]开始...");
			initParams();
			logger.info("初始化系统参数[生成web端JS]结束...");
        }  
	}
	
	private void initParams(){
		dictService.createGlobalParams();
	}

}
