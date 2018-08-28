package com.cctc.fast.common.utils;

import com.baomidou.mybatisplus.enums.IEnum;

/**
 */
public class EnumUtil {

    public static <T extends IEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getValue())) {
                return each;
            }
        }
        return null;
    }
}
