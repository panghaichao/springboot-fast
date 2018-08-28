package com.cctc.fast.portal.system.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;
/**
 * 
 * 界面跳转控制器
 * @author Hejeo
 *
 */
@Controller
@ApiIgnore
public class HomeController {
	/**
	 * 跳转登录界面
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping({"/","/index" })
	public String index() {
		return "/index";
	}
	
	@RequestMapping({ "/toMain",})
	public String toMain() {
		return "/page/main";
	}
	

	@RequestMapping("/403")
	public String unauthorizedRole() {
		System.out.println("------没有权限-------");
		return "403";
	}
	
	@RequestMapping("/404")
	public String notFound() {
		System.out.println("------界面找不到404-------");
		return "/page/error/404";
	}
	
	@RequestMapping("/icon")
	public String icon() {
		return "/page/common/icon";
	}
	
	
	@RequestMapping("/tree")
	public String tree() {
		return "/page/common/index";
	}
	
}
