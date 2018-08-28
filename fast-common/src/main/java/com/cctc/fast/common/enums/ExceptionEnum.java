package com.cctc.fast.common.enums;

/**
 * 异常枚举
 * @author Hejeo
 *
 */
public enum ExceptionEnum {
	 UNKNOW_ERROR(-1,"未知错误"),
	 USER_ADD_FAILD(-101,"添加用户失败"),
	 USER_NOT_FIND(-101,"用户不存在"),
	 USER_NOT_PASS(-102,"用户密码不正确"),
	 NO_PERMISSION(-103,"用户没有权限"),
	 CODE_ERROR(-104,"验证码不正确"),
	 NO_ID(-105,"编号不存在");
	
	private Integer code;

    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

	public Integer getCode() {
		return code;
	}
	
	public String getMsg() {
		return msg;
	}
    
    
}
