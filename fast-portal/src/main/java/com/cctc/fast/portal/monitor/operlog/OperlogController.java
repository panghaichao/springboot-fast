package com.cctc.fast.portal.monitor.operlog;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cctc.fast.common.utils.string.StringUtil;
import com.cctc.fast.core.base.controller.BaseController;
import com.cctc.fast.core.base.entity.OperateLog;
import com.cctc.fast.core.base.service.IBussinessLogService;
import com.cctc.fast.core.utils.Result;
import com.cctc.fast.core.utils.ResultUtil;
import com.cctc.fast.core.utils.TableResult;

@RestController
@RequestMapping("/monitor/operlog")
public class OperlogController extends BaseController{
	@Autowired
	private IBussinessLogService bussinessLogService;
	
	/**
	 * 跳转用户管理界面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("operlog:index")
	public ModelAndView toIndex() {
		ModelAndView mv = new ModelAndView("/page/monitor/operlog/operlogList");
		return mv;
	}

	/**
	 * 获取日志列表数据
	 * @param current 当前页
	 * @param size	每页条数
	 * @param request
	 * @return TableResult
	 */
	@RequestMapping(value="/operlog", method=RequestMethod.GET)
	public TableResult<OperateLog> getOperlogList(@RequestParam(value = "page",defaultValue = "1")Integer current,
            						  @RequestParam(value = "limit",defaultValue = "10")Integer size,
            						  ServletRequest request){
		
		String searchVal = request.getParameter("searchVal");
		
		EntityWrapper<OperateLog> sysUserEntityWrapper = new EntityWrapper<OperateLog>();
		if(StringUtil.notBlank(searchVal)){
			sysUserEntityWrapper.like("log_name", searchVal).or().like("log_method_name", searchVal);
		}
		sysUserEntityWrapper.orderBy("create_time", false);
		
		Page<OperateLog> page = bussinessLogService.selectPage(new Page<OperateLog>(current, size),sysUserEntityWrapper);
		return new TableResult<OperateLog>(page);
	}
	
	
	/**
     * 删除操作日志
     * @param logId 操作日志编号
     * @return
     */
    @RequestMapping(value="/deleteone/{logId}",method=RequestMethod.POST)
//    @BussinessLog(value="删除操作日志")
    @RequiresPermissions("operlog:delete")
    public Result<Object> delSysUser(@PathVariable(value = "logId",required = true)String logId){
    	Result<Object> result = ResultUtil.success();
    	bussinessLogService.deleteById(logId);
		return result;
    }
    
    /**
     * 删除操作日志（多个）
     * @param logIds 操作日志编号集合
     * @return
     */
    @RequestMapping(value="/deleteall",method=RequestMethod.POST)
//    @BussinessLog(value="删除选中操作日志")
    @RequiresPermissions("operlog:deleteAll")
    public Result<Object> delSelectSysUser(@RequestParam(value = "logIds",required = true)String logIds){
    	Result<Object> result = ResultUtil.success();
    	List<String> list = new ArrayList<String>();  
        list = JSONObject.parseArray(logIds,String.class);  
		
        bussinessLogService.deleteBatchIds(list);
		return result;
    }
}
