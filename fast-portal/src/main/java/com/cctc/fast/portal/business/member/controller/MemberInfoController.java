package com.cctc.fast.portal.business.member.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.baomidou.mybatisplus.plugins.Page;
import com.cctc.fast.common.utils.EntityUtils;
import com.cctc.fast.common.utils.id.KeyUtil;
import com.cctc.fast.common.utils.string.StringUtil;
import com.cctc.fast.core.annotation.BussinessLog;
import com.cctc.fast.core.base.controller.BaseController;
import com.cctc.fast.core.utils.Result;
import com.cctc.fast.core.utils.ResultUtil;
import com.cctc.fast.core.utils.TableResult;
import com.cctc.fast.portal.business.member.entity.MemberCard;
import com.cctc.fast.portal.business.member.entity.MemberInfo;
import com.cctc.fast.portal.business.member.service.IMemberCardService;
import com.cctc.fast.portal.business.member.service.IMemberInfoService;
import com.cctc.fast.portal.business.member.vo.MemberInfoVo;

/**
 * 会员信息表控制器
 * @author Hejeo
 *
 */
@RestController
@RequestMapping("/memberInfo")
public class MemberInfoController extends BaseController {
	
	@Autowired
	private IMemberInfoService memberInfoService;
	
	@Autowired
	private IMemberCardService memberCardService;
	
	/**
	 * 跳转用户管理界面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("memberInfo:index")
	public ModelAndView toIndex() {
		ModelAndView mv = new ModelAndView("/page/business/member/member/memberList");
		return mv;
	}
	
	
	/**
	 * 获取会员管理列表数据
	 * @param current 当前页
	 * @param size	每页条数
	 * @param request
	 * @return TableResult
	 */
	@RequestMapping(value="/members", method=RequestMethod.GET)
	public TableResult<MemberInfoVo> getSysUserList(@RequestParam(value = "page",defaultValue = "1")Integer current,
            						  @RequestParam(value = "limit",defaultValue = "10")Integer size,
            						  ServletRequest request){
		
		String searchVal = request.getParameter("searchVal");
		
		Map<String,String> searchMap = new HashMap<>();
		if(StringUtil.notBlank(searchVal)){
			searchMap.put("searchVal", searchVal);
		}
		
		Page<MemberInfoVo> page = memberInfoService.selectMemberInfoListPage(current, size, searchMap);
		return new TableResult<MemberInfoVo>(page);
	}
	
	
	/**
	 * 跳转添加会员界面
	 * @return
	 */
	@RequestMapping(value = "/toadd", method = RequestMethod.GET)
	@RequiresPermissions("memberInfo:add")
	public ModelAndView toAddSysRolePage() {
		ModelAndView mv = new ModelAndView("/page/business/member/member/memberAdd");
		return mv;
	}
	
	
	@RequestMapping(value="add",method=RequestMethod.POST)
    @BussinessLog(value="添加会员")
    @RequiresPermissions("memberInfo:add")
    public Result<Object> addMemberInfo(@RequestBody MemberInfoVo memberInfoVo){
		MemberInfo memberInfo = EntityUtils.copyData(memberInfoVo, MemberInfo.class);
		memberInfo.setMemberId(KeyUtil.genUniqueKey());
		memberInfoService.insert(memberInfo);
		
		MemberCard memberCard = EntityUtils.copyData(memberInfoVo, MemberCard.class);
		memberCard.setMemberCardId(KeyUtil.genUniqueKey());
		memberCardService.insert(memberCard);
		
		Result<Object> result = ResultUtil.success();
		return result;
	}
}
