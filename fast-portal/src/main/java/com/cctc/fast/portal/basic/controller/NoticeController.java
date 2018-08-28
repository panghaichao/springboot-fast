package com.cctc.fast.portal.basic.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cctc.fast.common.enums.NoticeStatusEnum;
import com.cctc.fast.common.enums.WebSocketMessageTypeEnum;
import com.cctc.fast.common.utils.string.StringUtil;
import com.cctc.fast.core.annotation.BussinessLog;
import com.cctc.fast.core.base.controller.BaseController;
import com.cctc.fast.core.utils.Result;
import com.cctc.fast.core.utils.ResultUtil;
import com.cctc.fast.core.utils.TableResult;
import com.cctc.fast.portal.basic.entity.NoticeDetail;
import com.cctc.fast.portal.basic.entity.NoticeInfo;
import com.cctc.fast.portal.basic.service.INoticeDetailService;
import com.cctc.fast.portal.basic.service.INoticeService;
import com.cctc.fast.portal.system.entity.SysUser;
import io.swagger.annotations.ApiOperation;

/**
 * 通知信息控制器
 * @author Hejeo
 *
 */
@RestController
@RequestMapping("/notice")
@Transactional
public class NoticeController extends BaseController{
	
	@Autowired
	private INoticeService noticeService;
	
	@Autowired
	private INoticeDetailService noticeDetailService;
	
	
	/**
	 * 跳转通知信息管理界面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("notice:index")
	@ApiOperation(value = "跳转通知信息管理界面", notes = "跳转通知信息管理界面s")
	public ModelAndView toIndex() {
		ModelAndView mv = new ModelAndView("/page/basic/notice/noticeList");
		return mv;
	}
	
	/**
	 * 获取通知信息数据
	 * @param current 当前页
	 * @param size	每页条数
	 * @param request
	 * @return TableResult
	 */
	@ApiOperation(value = "获取通知信息数据", notes = "获取通知信息数据")
	@RequestMapping(value="/notices", method=RequestMethod.GET)
	public TableResult<NoticeInfo> getOperlogList(@RequestParam(value = "page",defaultValue = "1")Integer current,
            						  @RequestParam(value = "limit",defaultValue = "10")Integer size,
            						  ServletRequest request){
		
		String noticeType = request.getParameter("noticeType");
		
		Map<String,Integer> searchMap = new HashMap<>();
		if(StringUtil.notBlank(noticeType)){
			searchMap.put("noticeType", Integer.parseInt(noticeType));
		}
		
		Page<NoticeInfo> page = noticeService.selectNoticeListPage(current, size,searchMap);
		return new TableResult<NoticeInfo>(page);
	}
	
	
	/**
     * 跳转修改用户界面
     * @param noticeId 用户编号
     * @return
     */
    @RequestMapping(value = "/notices/{noticeId}", method = RequestMethod.GET)
    public ModelAndView toEditSysUserPage(@PathVariable("noticeId") String noticeId){
    	SysUser currentUser = (SysUser)getCurrentUser();
    	
    	Map<String,Object> model = new HashMap<String,Object>();  
    	NoticeInfo noticeInfo = noticeService.selectById(noticeId);
    	model.put("noticeInfo",noticeInfo);
    	
    	
    	EntityWrapper<NoticeDetail> wrapper = new EntityWrapper<NoticeDetail>();
		if(StringUtil.notBlank(noticeId)){
			wrapper.eq("notice_id", noticeId);
			wrapper.eq("user_id", currentUser.getUserId());
		}
		
		// 更改消息状态
		NoticeDetail noticeDetail = noticeDetailService.selectOne(wrapper);
		if(noticeDetail.getNoticeStatus() == NoticeStatusEnum.UNREAD.getCode()){
			noticeDetail.setNoticeStatus(NoticeStatusEnum.READ.getCode());
			noticeDetail.setReadTime(new Date().getTime());
	    	noticeDetailService.updateById(noticeDetail);
	    	
	    	
	    	JSONObject json = new JSONObject();
	    	json.put("messageType", WebSocketMessageTypeEnum.CUT_MESSAGE.getCode());
	    	json.put("messageContent", 1);
	        webSocketMessageHandler.sendMessage(currentUser.getUserId(), json.toJSONString());
	        
		}
		
    	
        ModelAndView mv = new ModelAndView("/page/basic/notice/noticeInfo",model);
        return mv;
    }
    
    
    /**
     * 获取我的未读消息总数
     * @return
     */
    @RequestMapping(value="/getMyNoticeCount",method=RequestMethod.GET)
    public Result<Object> getMyNoticeCount(){
    	SysUser currentUser = (SysUser)getCurrentUser();
    	
    	EntityWrapper<NoticeDetail> wrapper = new EntityWrapper<NoticeDetail>();
    	wrapper.eq("user_id", currentUser.getUserId());
    	wrapper.eq("notice_status", NoticeStatusEnum.UNREAD.getCode());
    	
    	Result<Object> result = ResultUtil.success(noticeDetailService.selectCount(wrapper));
    	return result;
    }
    
    
    @RequestMapping(value="/deleteall",method=RequestMethod.POST)
    @BussinessLog(value="删除选中消息")
    public Result<Object> delSelectSysUser(
    		@RequestParam(value = "noticeDetailIds",required = true)String noticeDetailIds,
    		@RequestParam(value = "unReadCount",required = true)Integer unReadCount
    		){
    	
    	SysUser currentUser = (SysUser)getCurrentUser();
    			
    	Result<Object> result = ResultUtil.success();
    	List<String> list = new ArrayList<String>();  
        list = JSONObject.parseArray(noticeDetailIds,String.class);  
		
        noticeDetailService.deleteBatchIds(list);
        
        
        JSONObject json = new JSONObject();
    	json.put("messageType", WebSocketMessageTypeEnum.CUT_MESSAGE.getCode());
    	json.put("messageContent", unReadCount);
        webSocketMessageHandler.sendMessage(currentUser.getUserId(), json.toJSONString());
        
		return result;
    }
}
