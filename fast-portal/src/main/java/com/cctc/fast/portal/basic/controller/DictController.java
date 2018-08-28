package com.cctc.fast.portal.basic.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.cctc.fast.common.enums.ExceptionEnum;
import com.cctc.fast.common.utils.id.KeyUtil;
import com.cctc.fast.common.utils.string.StringUtil;
import com.cctc.fast.core.annotation.BussinessLog;
import com.cctc.fast.core.base.controller.BaseController;
import com.cctc.fast.core.exception.FastException;
import com.cctc.fast.core.utils.Result;
import com.cctc.fast.core.utils.ResultUtil;
import com.cctc.fast.portal.basic.entity.DictInfo;
import com.cctc.fast.portal.basic.service.IDictService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 数据字典控制器
 * @author Hejeo
 *
 */
@RestController
@RequestMapping("/dict")
@Transactional
public class DictController extends BaseController{
	@Autowired
	private IDictService dictService;
	
	/**
	 * 跳转数据字典管理界面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("dict:index")
	@ApiOperation(value = "跳转功能菜单管理界面", notes = "跳转数据字典管理界面")
	public ModelAndView toIndex() {
		ModelAndView mv = new ModelAndView("/page/basic/dict/dictList");
		return mv;
	}
	
	/**
	 * 获取数据字典表格树数据
	 */
	@ApiOperation(value = "获取数据字典表格树数据", notes = "获取数据字典表格树数据")
	@RequestMapping(value="/dicts", method=RequestMethod.GET)
	public Result<Object> getDictList(ServletRequest request){
		
		String searchVal = request.getParameter("searchVal");
		
		Result<Object> result = ResultUtil.success();
		List<DictInfo> dictList = dictService.getDictTreeGrid(searchVal);
		result = ResultUtil.success(dictList);
		return result;
	}
	
	/**
	 * 跳转添加数据字典界面
	 * @param dictId 上级编号
	 * @return
	 */
	@ApiOperation(value = "跳转添加数据字典界面", notes = "跳转添加数据字典界面")
	@ApiImplicitParam(name = "dictId", value = "上级编号", paramType = "path", required = true, dataType = "String")
	@RequestMapping(value = "/toadd/{dictId}", method = RequestMethod.GET)
	@RequiresPermissions("dict:add")
    public ModelAndView toAddSysMenuPage(@PathVariable("dictId") String dictId){
    	Map<String,Object> model = new HashMap<String,Object>(); 
    	DictInfo dictInfo = dictService.selectById(dictId);
    	
    	if("0".equals(dictId)){
    		dictInfo = new DictInfo();
    		dictInfo.setId(dictId);
    		dictInfo.setName("");
    	}
    	model.put("dictInfo",dictInfo);
        ModelAndView mv = new ModelAndView("/page/basic/dict/dictAdd",model);
        return mv;
    }
	
	/**
     * 添加数据字典
     * @param sysMenu
     * @return
     */
    @RequestMapping(value="add",method=RequestMethod.POST)
    @BussinessLog(value="添加数据字典")
    @ApiOperation(value = "添加数据字典", notes = "添加数据字典")
    @ApiImplicitParam(name = "dictInfo", value = "数据字典实体", required = true, dataType = "DictInfo")
    @RequiresPermissions("dict:add")
    public Result<Object> addDict(@RequestBody DictInfo dictInfo){
    	Result<Object> result = ResultUtil.success();
    	dictInfo.setId(KeyUtil.genUniqueKey());
    	dictService.insert(dictInfo);
		return result;
    }
    
    /**
     * 跳转修改数据字典界面
     * @param dictId 数据字典编号
     * @return
     */
    @RequestMapping(value = "/dicts/{dictId}", method = RequestMethod.GET)
    @ApiOperation(value = "跳转修改数据字典界面", notes = "跳转修改数据字典界面")
    @ApiImplicitParam(name = "dictId", value = "数据字典编号", paramType = "path", required = true, dataType = "String")
    @RequiresPermissions("dict:edit")
    public ModelAndView toEditSysMenuPage(@PathVariable("dictId") String dictId){
    	Map<String,Object> model = new HashMap<String,Object>();  
    	DictInfo dictInfo = dictService.selectById(dictId);
    	model.put("dictInfo",dictInfo);
        ModelAndView mv = new ModelAndView("/page/basic/dict/dictEdit",model);
        return mv;
    }
    
    /**
     * 更新数据字典信息
     * @param dictInfo
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @BussinessLog(value="更新数据字典")
    @ApiOperation(value = "更新数据字典", notes = "更新数据字典")
    @ApiImplicitParam(name = "dictInfo", value = "数据字典实体", required = true, dataType = "DictInfo")
    @RequiresPermissions("dict:edit")
    public Result<Object> editDictInfo(@RequestBody DictInfo dictInfo){
    	Result<Object> result = ResultUtil.success();
    	if(StringUtil.isBlank(dictInfo.getId())){
			throw new FastException(ExceptionEnum.NO_ID);
		}
    	dictService.updateById(dictInfo);
		return result;
    }
    
    /**
     * 删除数据字典信息
     * @param dictId 数据字典编号
     * @return
     */
    @RequestMapping(value="/dicts/deleteone/{dictId}",method=RequestMethod.POST)
    @BussinessLog(value="删除数据字典信息")
    @ApiOperation(value = "删除数据字典信息", notes = "根据url的菜单编号来指定删除数据字典信息")
	@ApiImplicitParam(name = "dictId", value = "数据字典编号", required = true, dataType = "String", paramType = "path")
    @RequiresPermissions("dict:delete")
    public Result<Object> delSysMenu(@PathVariable(value = "dictId",required = true)String dictId){
    	Result<Object> result = ResultUtil.success();
    	dictService.deleteById(dictId);
		return result;
    }
}
