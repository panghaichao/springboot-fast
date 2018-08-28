package com.cctc.fast.core.utils;

import com.cctc.fast.common.enums.ExceptionEnum;

/**
 * 
 * 返回工具类
 * @author Hejeo
 *
 */
public class ResultUtil {
	/**
     * 返回成功，传入返回体具体出參
     * @param object
     * @return
     */
    public static Result<Object> success(Object object){
    	Result<Object> result = new Result<Object>();
        result.setStatus(0);
        result.setMsg("success");
        result.setData(object);
        return result;
    }
    
    /**
     * 提供给部分不需要出參的接口
     * @return
     */
    public static Result<Object> success(){
        return success(null);
    }
    
    /**
     * 自定义错误信息
     * @param code
     * @param msg
     * @return
     */
    public static Result<Object> error(Integer code,String msg){
        Result<Object> result = new Result<Object>();
        result.setStatus(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
    
    /**
     * 返回异常信息，在已知的范围内
     * @param exceptionEnum
     * @return
     */
    public static Result<Object> error(ExceptionEnum exceptionEnum){
    	Result<Object> result = new Result<Object>();
        result.setStatus(exceptionEnum.getCode());
        result.setMsg(exceptionEnum.getMsg());
        result.setData(null);
        return result;
    }
}
