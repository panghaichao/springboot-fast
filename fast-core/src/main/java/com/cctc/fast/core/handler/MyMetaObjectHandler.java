package com.cctc.fast.core.handler;

import java.util.Date;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.cctc.fast.common.enums.DeleteTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 添加修改 拦截器
 * @author Hejeo
 *
 */
public class MyMetaObjectHandler extends MetaObjectHandler {
	
	protected final static Logger logger = LoggerFactory.getLogger(MyMetaObjectHandler.class);

	@Override
	public void insertFill(MetaObject metaObject) {
		setFieldValByName("createTime", new Date().getTime(), metaObject);
		setFieldValByName("createUserId", getUserId(), metaObject);
		setFieldValByName("deleteFlag", DeleteTypeEnum.NOTDELETE.getValue(), metaObject);
		logger.info("新增的时候干点不可描述的事情");
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		setFieldValByName("updateTime", new Date().getTime(), metaObject);
		setFieldValByName("updateUserId", getUserId(), metaObject);
		logger.info("更新的时候干点不可描述的事情");
	}
	
	
	/**
	 * 获取用户编号
	 * @return
	 */
	private String getUserId(){
		Object obj = SecurityUtils.getSubject().getPrincipal();
		JSONObject json = (JSONObject) JSON.toJSON(obj);
		String userId = json.getString("userId");
		return userId;
	}

}
