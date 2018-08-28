package com.cctc.fast.portal.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cctc.fast.common.enums.ExceptionEnum;
import com.cctc.fast.common.utils.id.KeyUtil;
import com.cctc.fast.common.utils.string.Md5Util;
import com.cctc.fast.common.utils.string.StringUtil;
import com.cctc.fast.core.annotation.BussinessLog;
import com.cctc.fast.core.base.controller.BaseController;
import com.cctc.fast.core.utils.Checkbox;
import com.cctc.fast.core.utils.Result;
import com.cctc.fast.core.utils.ResultUtil;
import com.cctc.fast.core.utils.TableResult;
import com.cctc.fast.portal.system.entity.SysRole;
import com.cctc.fast.portal.system.entity.SysUser;
import com.cctc.fast.portal.system.entity.SysUserRole;
import com.cctc.fast.portal.system.service.ISysRoleService;
import com.cctc.fast.portal.system.service.ISysUserRoleService;
import com.cctc.fast.portal.system.service.ISysUserService;

/**
 * 用户控制层
 * @author Hejeo
 *
 */
@RestController
@RequestMapping("/sysuser")
public class SysUserController extends BaseController{

	@Autowired
	private ISysUserService sysUserService;
	
	@Autowired
	private ISysRoleService sysRoleService;
	
	@Autowired
	private ISysUserRoleService sysUserRoleService;

	/**
	 * 跳转用户管理界面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("sysuser:index")
	public ModelAndView toIndex() {
		ModelAndView mv = new ModelAndView("/page/system/sysUser/userList");
		return mv;
	}

	/**
	 * 获取用户管理列表数据
	 * @param current 当前页
	 * @param size	每页条数
	 * @param request
	 * @return TableResult
	 */
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public TableResult<SysUser> getSysUserList(@RequestParam(value = "page",defaultValue = "1")Integer current,
            						  @RequestParam(value = "limit",defaultValue = "10")Integer size,
            						  ServletRequest request){
		
		String userName = request.getParameter("userName");
		
		EntityWrapper<SysUser> sysUserEntityWrapper = new EntityWrapper<SysUser>();
		if(StringUtil.notBlank(userName)){
			sysUserEntityWrapper.like("user_account", userName).or().like("user_name", userName);
		}
		
		Page<SysUser> page = sysUserService.selectPage(new Page<SysUser>(current, size),sysUserEntityWrapper);
		return new TableResult<SysUser>(page);
	}
	
	
	/**
	 * 跳转添加用户界面
	 * @return
	 */
	@RequestMapping(value = "/toadd", method = RequestMethod.GET)
	@RequiresPermissions("sysuser:add")
	public ModelAndView toAddSysUserPage() {
		Map<String,Object> model = new HashMap<String,Object>();  
		model.put("sysRoleList", sysRoleService.selectList(new EntityWrapper<SysRole>()));
		ModelAndView mv = new ModelAndView("/page/system/sysUser/userAdd",model);
		return mv;
	} 
	
	
    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    @RequestMapping(value="add",method=RequestMethod.POST)
    @BussinessLog(value="添加用户")
    @RequiresPermissions("sysuser:add")
    public Result<Object> addSysUser(@RequestBody SysUser sysUser){
    	Result<Object> result = ResultUtil.success();
    	sysUser.setUserId(KeyUtil.genUniqueKey());
    	
    	/**
    	 * 对密码进行MD5加密盐加密
    	 */
    	String userPass = sysUser.getUserPassword();
    	String salt = Md5Util.getRandomSalt(3);
    	String userPassword = Md5Util.md5(userPass, salt);
    			
		sysUser.setSalt(salt);		
		sysUser.setUserPassword(userPassword);
		sysUserService.insert(sysUser);
		
		
		/********* 添加用户角色 **************/
		if(sysUser.getRoleList().length>0){
			List<SysUserRole> sysUserRoleList = new ArrayList<>(sysUser.getRoleList().length);
			for(String roleId : sysUser.getRoleList()){
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setUserRoleId(KeyUtil.genUniqueKey());
				sysUserRole.setRoleId(roleId);
				sysUserRole.setUserId(sysUser.getUserId());
				sysUserRoleList.add(sysUserRole);
			}
			sysUserRoleService.insertBatch(sysUserRoleList);
		}
		/********* 添加用户角色 完毕**************/
		return result;
    }
    
    /**
     * 跳转修改用户界面
     * @param userId 用户编号
     * @return
     */
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    @RequiresPermissions("sysuser:edit")
    public ModelAndView toEditSysUserPage(@PathVariable("userId") String userId){
    	Map<String,Object> model = new HashMap<String,Object>();  
    	SysUser sysUser = sysUserService.selectById(userId);
    	List<Checkbox> checkboxList = sysUserRoleService.getUserRoleListByUserId(userId);
    	model.put("sysUser",sysUser);
    	model.put("checkboxList",checkboxList);
        ModelAndView mv = new ModelAndView("/page/system/sysUser/userEdit",model);
        return mv;
    }
    
    
    /**
     * 更新用户信息
     * @param sysUser
     * @return
     */
    @RequestMapping(value="edit",method=RequestMethod.POST)
    @BussinessLog(value="更新用户")
    @RequiresPermissions("sysuser:edit")
    public Result<Object> editSysUser(@RequestBody SysUser sysUser){
    	Result<Object> result = ResultUtil.success();
    	sysUserService.updateById(sysUser);
    	
    	reloadShiroUser(sysUser);
		
		/********* 添加用户角色 **************/
		if(sysUser.getRoleList()!=null && sysUser.getRoleList().length>0){
			
			sysUserRoleService.deleteByUserId(sysUser.getUserId());
			
			List<SysUserRole> sysUserRoleList = new ArrayList<>(sysUser.getRoleList().length);
			for(String roleId : sysUser.getRoleList()){
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setUserRoleId(KeyUtil.genUniqueKey());
				sysUserRole.setRoleId(roleId);
				sysUserRole.setUserId(sysUser.getUserId());
				sysUserRoleList.add(sysUserRole);
			}
			sysUserRoleService.insertBatch(sysUserRoleList);
		}
		/********* 添加用户角色 完毕**************/
		return result;
    }
    
    /**
     * 删除用户
     * @param userId 用户编号
     * @return
     */
    @RequestMapping(value="/users/deleteone/{userId}",method=RequestMethod.POST)
    @BussinessLog(value="删除用户")
    @RequiresPermissions("sysuser:delete")
    public Result<Object> delSysUser(@PathVariable(value = "userId",required = true)String userId){
    	Result<Object> result = ResultUtil.success();
    	sysUserService.deleteById(userId);
		return result;
    }
    
    /**
     * 删除用户（多个）
     * @param userIds 用户编号集合
     * @return
     */
    @RequestMapping(value="/users/deleteall",method=RequestMethod.POST)
    @BussinessLog(value="删除选中用户")
    @RequiresPermissions("sysuser:deleteAll")
    public Result<Object> delSelectSysUser(@RequestParam(value = "userIds",required = true)String userIds){
    	Result<Object> result = ResultUtil.success();
    	List<String> list = new ArrayList<String>();  
        list = JSONObject.parseArray(userIds,String.class);  
		
		sysUserService.deleteBatchIds(list);
		return result;
    }
    
    /**
     * 获取用户总数
     * @return
     */
    @RequestMapping(value="/getUserCount",method=RequestMethod.GET)
    public Result<Object> getUserCount(){
    	Result<Object> result = ResultUtil.success(sysUserService.selectCount(new EntityWrapper<SysUser>()));
    	return result;
    }
    
    /**
     * 重置密码
     */
    @RequestMapping(value = "/resetPassword/{userId}", method = RequestMethod.POST)
    @BussinessLog(value="重置密码")
    @RequiresPermissions("sysuser:resetPassword")
    public Result<Object> resetPassword(@PathVariable("userId") String userId){
    	SysUser sysUser = sysUserService.selectById(userId);
    	
    	/**
    	 * 对密码进行MD5加密盐加密
    	 */
    	String userPass = "123456";
    	String salt = Md5Util.getRandomSalt(3);
    	String userPassword = Md5Util.md5(userPass, salt);
    	sysUser.setSalt(salt);
    	sysUser.setUserPassword(userPassword);
    	Result<Object> result = ResultUtil.success(sysUserService.updateById(sysUser));
    	return result;
    }
    
    
    
    /**
     * 跳转个人资料界面
     * @param userId 用户编号
     * @return
     */
    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public ModelAndView toMyViewPage(){
    	Map<String,Object> model = new HashMap<String,Object>();  
        ModelAndView mv = new ModelAndView("/page/system/sysUser/userInfo",model);
        return mv;
    }
    
    
    @RequestMapping(value = "/my/{userId}", method = RequestMethod.GET)
    public Result<Object> myinfo(@PathVariable("userId") String userId){
    	SysUser sysUser = sysUserService.selectById(userId);
    	Map<String,Object> returnMap = new HashMap<>();
    	returnMap.put("sysUser", sysUser);
        return ResultUtil.success(returnMap); 
    }
    
    
    

	/**
	 * 分页 PAGE
	 */
	@RequestMapping("/test")
	public Result<Object> test() {
		Result<Object> result = ResultUtil.success();
		try {
			// if (name.equals("zzp")){
			// result = ResultUtil.success(new UserInfo());
			// }else if (name.equals("pzz")){
			result = ResultUtil.error(ExceptionEnum.USER_NOT_FIND);
			// }else{
			//
			// }
			int i = 1 / 0;
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultUtil.error(ExceptionEnum.UNKNOW_ERROR);
//			result = getExceptionHandle().exceptionGet(e);
		}
		return result;
	}

	@GetMapping("/test1")
	public void test1() {
	}

	@GetMapping("/getRouteList")
	public Object getRouteList(HttpServletRequest request, HttpServletResponse response) {
		return sysUserService.selectPage(new Page<SysUser>());
	}

	/**
	 * 插入 OR 修改
	 */
	@GetMapping("/test3")
	public SysUser test3() {
		SysUser user = new SysUser();
		user.setUserId("123");
		user.setUserAccount("cctc");
		user.setUserPassword("123456");
		user.setUserName("庞海超");
		System.out.println("插入前：" + user.toString());
		sysUserService.insertOrUpdate(user);
		user = sysUserService.selectById(1L);
		System.out.println("更新后：" + user.toString());
		return user;
	}

	//
	@GetMapping("/add")
	@BussinessLog(value = "添加用户")
	public Object addUser() {
		SysUser user = new SysUser();
		user.setUserId("123");
		user.setUserAccount("cctc");
		user.setUserPassword("123456");
		user.setUserName("庞海超");

		JSONObject result = new JSONObject();
		result.put("result", sysUserService.insert(user));
		return result;
	}
	//
	//
	// /**
	// * 7、分页 size 一页显示数量 current 当前页码
	// * 方式一：http://localhost:8080/user/page?size=1&current=1<br>
	// * 方式二：http://localhost:8080/user/pagehelper?size=1&current=1<br>
	// */
	//
	// // 参数模式分页
	// @GetMapping("/page")
	// public Object page(Page page) {
	// return sysUserService.selectPage(page);
	// }
	//
	// // ThreadLocal 模式分页
	// @GetMapping("/pagehelper")
	// public Object pagehelper(Page page) {
	// PageHelper.setPagination(page);
	// page.setRecords(sysUserService.selectList(null));
	// page.setTotal(PageHelper.freeTotal());//获取总数并释放资源 也可以
	// PageHelper.getTotal()
	// return page;
	// }
	//
}
