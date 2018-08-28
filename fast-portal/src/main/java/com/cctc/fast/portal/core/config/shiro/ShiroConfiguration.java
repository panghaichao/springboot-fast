package com.cctc.fast.portal.core.config.shiro;

import java.util.LinkedHashMap;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfiguration{
	
	/**
     * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * 所以我们需要修改下doGetAuthenticationInfo中的代码; @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);// 散列的次数，比如散列两次，相当于md5(md5(""));
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);//表示是否存储散列后的密码为16进制，需要和生成密码时的一样，默认是base64；
        return hashedCredentialsMatcher;
    }
	
	//将自己的验证方式加入容器
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }
    
    
    /**************************redis cache 和 redis session   ************************************/
//    /**
//	 * 配置shiro redisManager
//	 * 
//	 * @return
//	 */
//	public RedisManager redisManager() {
//		RedisManager redisManager = new RedisManager();
//		redisManager.setHost("127.0.0.1");
//		redisManager.setPort(6379);
//		redisManager.setExpire(1800);// 配置过期时间
//		// redisManager.setTimeout(timeout);
//		// redisManager.setPassword(password);
//		return redisManager;
//	}
//    
//    public RedisCacheManager redisCacheManager() {
//    	RedisCacheManager redisCacheManager = new RedisCacheManager();
//    	redisCacheManager.setRedisManager(redisManager());
//    	return redisCacheManager;
//    }
//    
//    
//    /**
//	 * RedisSessionDAO shiro sessionDao层的实现 通过redis
//	 */
//	public RedisSessionDAO redisSessionDAO() {
//		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//		redisSessionDAO.setRedisManager(redisManager());
//		return redisSessionDAO;
//	}
//	
//    /**
//	 * shiro session的管理
//	 */
//	public DefaultWebSessionManager SessionManager() {
//		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//		sessionManager.setSessionDAO(redisSessionDAO());
//		return sessionManager;
//	}
	/**************************redis cache 和 redis session   ************************************/
    
    /**
     * encache
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager() {
    	EhCacheManager cacheManager = new EhCacheManager(); 
    	cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
    	//配置文件路径
    	return cacheManager; 
    }
    
    /**
     * SessionManager
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
    	DefaultWebSessionManager sessionManager = new DefaultWebSessionManager(); 
    	/**
    	 * 设置连接超时时间  单位毫秒 30分钟
    	 */
    	sessionManager.setGlobalSessionTimeout(30*60000);
    	//配置文件路径
    	return sessionManager; 
    }
    
    
    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager());
        securityManager.setRealm(myShiroRealm());
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }
    
    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/favicon.ico", "anon");//网站图标
        filterChainDefinitionMap.put("/static/**","anon");
        filterChainDefinitionMap.put("/assets/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/validatecodeServlet", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        //登出
        filterChainDefinitionMap.put("/logout","logout");
        
        filterChainDefinitionMap.put("/**", "authc");
        
        //如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面  
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功后要跳转的链接  
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面;  
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        
        return shiroFilterFactoryBean;
    }
    
    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
