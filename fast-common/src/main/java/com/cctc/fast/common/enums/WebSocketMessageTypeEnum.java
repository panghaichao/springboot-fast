package com.cctc.fast.common.enums;

import java.io.Serializable;
import com.baomidou.mybatisplus.enums.IEnum;

/**
 * 
 * websocket 消息类型枚举
 * @author Hejeo
 *
 */
public enum WebSocketMessageTypeEnum implements IEnum{
	/**
	 * 减当前用户消息数量
	 */
	CUT_MESSAGE(1,"减当前消息数量"),
	NEW_MESSAGE(2,"新消息");
	
	

	private Integer code;
    private String msg;
    
    WebSocketMessageTypeEnum(Integer code,String msg){
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
