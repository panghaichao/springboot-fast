package com.cctc.fast.common.enums;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 删除枚举
 * @author Hejeo
 *
 */
public enum DeleteTypeEnum implements IEnum{
	DELETE(0, "已删除"),
    NOTDELETE(1, "未删除");
	
	
	private int value;
    private String desc;
    
    
    DeleteTypeEnum(final int value, final String desc) {
        this.value = value;
        this.desc = desc;
    }


    @Override
    public Serializable getValue() {
        return this.value;
    }

    @JsonValue
    public String getDesc(){
        return this.desc;
    }

}
