package com.cctc.fast.portal.business.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
import com.cctc.fast.common.utils.id.KeyUtil;
import com.cctc.fast.common.utils.string.StringUtil;
import com.cctc.fast.core.annotation.BussinessLog;
import com.cctc.fast.core.base.controller.BaseController;
import com.cctc.fast.core.utils.Result;
import com.cctc.fast.core.utils.ResultUtil;
import com.cctc.fast.core.utils.TableResult;
import com.cctc.fast.portal.business.member.entity.MemberLevel;
import com.cctc.fast.portal.business.member.service.IMemberLevelService;

/**
 * 会员等级控制层
 * @author Hejeo
 *
 */
@RestController
@RequestMapping("/memberLevel")
public class MemberLevelController extends BaseController{
	
	@Autowired
	private IMemberLevelService memberLevelService;

	/**
	 * 跳转会员等级管理界面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("memberLevel:index")
	public ModelAndView toIndex() {
		ModelAndView mv = new ModelAndView("/page/business/member/memberLevel/memberLevelList");
		return mv;
	}
	
	/**
	 * 获取会员等级管理列表数据
	 * @param current 当前页
	 * @param size	每页条数
	 * @param request
	 * @return TableResult
	 */
	@RequestMapping(value="/memberLevels", method=RequestMethod.GET)
	public TableResult<MemberLevel> getSysRoleList(
										@RequestParam(value = "page",defaultValue = "1")Integer current,
										@RequestParam(value = "limit",defaultValue = "10")Integer size,
										ServletRequest request){
		
		String memberLevelName = request.getParameter("memberLevelName");
		
		EntityWrapper<MemberLevel> memberLevelEntityWrapper = new EntityWrapper<MemberLevel>();
		if(StringUtil.notBlank(memberLevelName)){
			memberLevelEntityWrapper.like("member_level_name", memberLevelName);
		}
		
		Page<MemberLevel> page = memberLevelService.selectPage(new Page<MemberLevel>(current, size),memberLevelEntityWrapper);
		return new TableResult<MemberLevel>(page);
	}
	
	/**
	 * 跳转添加会员等级界面
	 * @return
	 */
	@RequestMapping(value = "/toadd", method = RequestMethod.GET)
	@RequiresPermissions("memberLevel:add")
	public ModelAndView toAddMemberLevelPage() {
		ModelAndView mv = new ModelAndView("/page/business/member/memberLevel/memberLevelAdd");
		return mv;
	} 
	
	
    /**
     * 添加会员等级
     * @param sysRole
     * @return
     */
    @RequestMapping(value="add",method=RequestMethod.POST)
    @BussinessLog(value="添加会员等级")
    @RequiresPermissions("memberLevel:add")
    public Result<Object> addMemberLevel(@Validated @RequestBody MemberLevel memberLevel,BindingResult br){
    	Result<Object> result= null;
    	if(br.hasErrors()){
    		result = ResultUtil.error(br.getErrorCount(), br.getFieldError().getDefaultMessage());
    	}else{
    		/** 添加会员等级信息  **/
        	memberLevel.setMemberLevelId(KeyUtil.genUniqueKey());
    		memberLevelService.insert(memberLevel);
    		/** 添加会员等级信息  **/
    		result = ResultUtil.success();
    	}
		return result;
    }
    
    
    /**
     * 跳转修改会员等级界面
     * @param roleId 会员等级编号
     * @return
     */
    @RequestMapping(value = "/{memberLevelId}", method = RequestMethod.GET)
    @RequiresPermissions("memberLevel:edit")
    public ModelAndView toEditSysRolePage(@NotBlank(message="会员等级编号不能为空") @PathVariable("memberLevelId") String memberLevelId){
    	Map<String,Object> model = new HashMap<String,Object>();  
    	MemberLevel memberLevel = memberLevelService.selectById(memberLevelId);
    	model.put("memberLevel",memberLevel);
        ModelAndView mv = new ModelAndView("/page/business/member/memberLevel/memberLevelEdit",model);
        return mv;
    }
    
    
    /**
     * 更新会员等级信息
     * @param sysRole
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @BussinessLog(value="更新会员等级")
    @RequiresPermissions("memberLevel:edit")
    public Result<Object> editSysRole(@Validated @RequestBody MemberLevel memberLevel,BindingResult br){
    	Result<Object> result= null;
    	if(br.hasErrors()){
    		result = ResultUtil.error(br.getErrorCount(), br.getFieldError().getDefaultMessage());
    	}else{
    		/** 更新会员等级信息  **/
        	memberLevelService.updateById(memberLevel);
    		/** 更新会员等级信息  **/
    		result = ResultUtil.success();
    	}
		return result;
    }
    
    /**
     * 删除会员等级
     * @param roleId 会员等级编号
     * @return
     */
    @RequestMapping(value="/deleteone/{memberLevelId}",method=RequestMethod.POST)
    @BussinessLog(value="删除会员等级")
    @RequiresPermissions("memberLevel:delete")
    public Result<Object> delMemberLevel(@PathVariable(value = "memberLevelId",required = true)String memberLevelId){
    	Result<Object> result = ResultUtil.success();
    	/*************删除会员等级******************/
    	memberLevelService.deleteById(memberLevelId);
		/*************删除会员等级******************/
		return result;
    }
    
    /**
     * 删除会员等级（多个）
     * @param roleIds 会员等级编号集合
     * @return
     */
    @RequestMapping(value="/deleteall",method=RequestMethod.POST)
    @BussinessLog(value="删除选中会员等级")
    @RequiresPermissions("memberLevel:deleteAll")
    public Result<Object> delSelectMemberLevel(@RequestParam(value = "memberLevelIds",required = true)String memberLevelIds){
    	Result<Object> result = ResultUtil.success();
    	List<String> list = new ArrayList<String>();  
        list = JSONObject.parseArray(memberLevelIds,String.class);  
        memberLevelService.deleteBatchIds(list);
		return result;
    }
    
    
    /**
     * 获取会员等级列表 下拉菜单用
     * @return
     */
    @RequestMapping(value = "/getAllMemberLevelListForSelect", method = RequestMethod.GET)
    public Result<Object> getAllMemberLevelListForSelect(){
    	EntityWrapper<MemberLevel> entityWrapper = new EntityWrapper<MemberLevel>();
    	entityWrapper.orderBy("member_level_sort");
    	List<MemberLevel> memberLevelList = memberLevelService.selectList(entityWrapper);
        return ResultUtil.success(memberLevelList);
    }
}
