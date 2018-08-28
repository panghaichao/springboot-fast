package com.cctc.fast.portal.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cctc.fast.core.base.controller.BaseController;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 文档帮助控制器
 * @author Hejeo
 *
 */
@Controller
@RequestMapping("/doc")
@ApiIgnore
public class DocController extends BaseController{
	private final String BASE_URL = "/page/doc/";
	/**
	 * 跳转bodyTabDoc
	 * @return
	 */
	@RequestMapping(value = "/bodyTabDoc", method = RequestMethod.GET)
	@RequiresPermissions("doc:bodyTabDoc")
	public String bodyTabDoc() {
		return BASE_URL+"bodyTabDoc";
	}
	
	/**
	 * 跳转bodyTabDoc
	 * @return
	 */
	@RequestMapping(value = "/addressDoc", method = RequestMethod.GET)
	@RequiresPermissions("doc:addressDoc")
	public String addressDoc() {
		return BASE_URL+"addressDoc";
	}
	
	
	/**
	 * 跳转addTabRow动态添加行
	 * @return
	 */
	@RequestMapping(value = "/addTabRow", method = RequestMethod.GET)
	@RequiresPermissions("doc:addTabRow")
	public String addTabRow() {
		return BASE_URL+"addTabRow";
	}
	
	
	/**
	 * 跳转formSelects
	 * @return
	 */
	@RequestMapping(value = "/formSelects", method = RequestMethod.GET)
	@RequiresPermissions("doc:formSelects")
	public String formSelects() {
		return BASE_URL+"formSelects";
	}
}
