package com.cctc.fast.core.base.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.cctc.fast.core.handler.ExceptionHandle;
import com.cctc.fast.core.websocket.WebSocketMessageHandler;

public class BaseController {
	
	protected static String SUCCESS = "SUCCESS";
	protected static String ERROR = "ERROR";
	protected static String REDIRECT = "redirect:";
    protected static String FORWARD = "forward:";
    
    @Autowired
	private ExceptionHandle exceptionHandle;
    
    
    @Autowired
    public WebSocketMessageHandler webSocketMessageHandler;
    
    public ExceptionHandle getExceptionHandle() {
		return exceptionHandle;
	}

	public void setExceptionHandle(ExceptionHandle exceptionHandle) {
		this.exceptionHandle = exceptionHandle;
	}

	/**
     * 获取当前登录用户
     * @return
     */
    public Object getCurrentUser(){
    	return SecurityUtils.getSubject().getPrincipal();
    }
    
    
    /**
     * 重新加载Shiro用户信息
     * @param user 用户对象
     */
    public static void reloadShiroUser(Object user){
    	Subject subject = SecurityUtils.getSubject();
    	PrincipalCollection principalCollection = subject.getPrincipals();
    	String realmName = principalCollection.getRealmNames().iterator().next();
    	PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
    	subject.runAs(newPrincipalCollection);
    }
}
