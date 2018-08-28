package com.cctc.fast.common.enums;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IEnum;

/**
 * 
 * 通知类型 枚举
 * @author Hejeo
 *
 */
public enum NoticeTypeEnum implements IEnum{
	NOTICE(0,"告警"),
	MESSAGE(1,"私信"),
	WARN(2,"通知");
	
	private Integer code;
    private String msg;
    
    NoticeTypeEnum(Integer code,String msg){
    	this.code = code;
    	this.msg = msg;
    }

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public Serializable getValue() {
		return this.code;
	}
    
}
