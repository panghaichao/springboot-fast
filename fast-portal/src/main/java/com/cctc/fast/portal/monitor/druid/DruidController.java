package com.cctc.fast.portal.monitor.druid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cctc.fast.core.base.controller.BaseController;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/monitor/druid")
@ApiIgnore
public class DruidController extends BaseController{
	
	private String prefix = "/druid";
	
	@RequiresPermissions("druid:index")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return REDIRECT + prefix + "/index";
    }
}
