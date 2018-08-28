package com.cctc.fast.common.enums;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IEnum;

/**
 * 
 * 消息状态  枚举
 * @author Hejeo
 *
 */
public enum NoticeStatusEnum implements IEnum{
	UNREAD (0,"未读"),
	READ(1,"已读");
	
	private Integer code;
    private String msg;
    
    NoticeStatusEnum(Integer code,String msg){
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
