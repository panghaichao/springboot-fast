package com.cctc.fast.core.exception;

import com.cctc.fast.common.enums.ExceptionEnum;

/**
 * 
 * 统一异常
 * @author Hejeo
 *
 */
public class FastException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private Integer code;
	
	/**
     * 继承exception，加入错误状态值
     * @param exceptionEnum
     */
    public FastException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }
    
    /**
     * 自定义错误信息
     * @param message
     * @param code
     */
    public FastException(String message, Integer code) {
        super(message);
        this.code = code;
    }

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
