package com.cctc.fast.core.handler;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.cctc.fast.common.enums.ExceptionEnum;
import com.cctc.fast.common.utils.http.HttpUtil;
import com.cctc.fast.core.exception.FastException;
import com.cctc.fast.core.utils.Result;
import com.cctc.fast.core.utils.ResultUtil;

/**
 * 
 * 异常拦截器
 * @author Hejeo
 *
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandle {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);
	
	/**
	 * 没有授权错误
	 * @param e
	 * @return
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public Object handleAuthorizationException(UnauthorizedException e){
		LOGGER.error("【权限异常】 {}",ExceptionEnum.NO_PERMISSION.getMsg());
		if(HttpUtil.isAjax()){
			return ResultUtil.error(ExceptionEnum.NO_PERMISSION);
		}else{
			ModelAndView mv = new ModelAndView("/page/error/unauthorized");
			return mv;
		}
	}
	
			
	/**
     * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result<Object> exceptionGet(Exception e){
        if(e instanceof FastException){
        	FastException myException = (FastException) e;
        	LOGGER.error("【业务异常】{}",myException.getMessage());
            return ResultUtil.error(myException.getCode(),myException.getMessage());
        }
        
        /**
         * 用户不存在
         */
        if(e instanceof UnknownAccountException){
        	LOGGER.error("【账户异常】{}",ExceptionEnum.USER_NOT_FIND.getMsg());
            return ResultUtil.error(ExceptionEnum.USER_NOT_FIND);
        }
        
        /**
         * 用户密码不正确
         */
        if(e instanceof IncorrectCredentialsException){
        	LOGGER.error("【账户异常】{}",ExceptionEnum.USER_NOT_PASS.getMsg());
            return ResultUtil.error(ExceptionEnum.USER_NOT_PASS);
        }
        
        LOGGER.error("【系统异常】{}",e.toString());
        e.printStackTrace();
        return ResultUtil.error(ExceptionEnum.UNKNOW_ERROR);
    }
}
