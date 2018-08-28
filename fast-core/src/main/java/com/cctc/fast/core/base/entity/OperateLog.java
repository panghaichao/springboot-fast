package com.cctc.fast.core.base.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cctc.fast.core.base.entity.SuperEntity;

/**
 * 
 * 功能菜单表
 * @author Hejeo
 *
 */
@SuppressWarnings("serial")
@TableName(value = "t_operate_log")
public class OperateLog extends SuperEntity<OperateLog>{
	
	@Override
	protected Serializable pkVal() {
		return this.logId;
	}

	
	/**
	 * 主键
	 */
	@TableId(value = "log_id", type = IdType.INPUT)
	private String logId; 
	
	/**
	 * 业务名称
	 */
	@TableField(value="log_name")
	private String logName;
	
	/**
	 * 请求类名
	 */
	@TableField(value="log_class_name")
	private String logClassName;
	
	/**
	 * 请求方法名称
	 */
	@TableField(value="log_method_name")
	private String logMethodName;

	/**
	 * 请求参数
	 */
	@TableField(value="log_method_params")
	private String logMethodParams;
	
	/**
	 * 访问者ip
	 */
	@TableField(value="log_ip")
	private String logIp;
	
	/**
	 * 执行时间
	 */
	@TableField(value="log_time")
	private Long logTime;
	
	/**
	 * 请求结果 0成功1失败
	 */
	@TableField(value="log_result")
	private String logResult;
	
	/**
	 * 异常信息
	 */
	@TableField(value="log_message")
	private String logMessage;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public String getLogClassName() {
		return logClassName;
	}

	public void setLogClassName(String logClassName) {
		this.logClassName = logClassName;
	}

	public String getLogMethodName() {
		return logMethodName;
	}

	public void setLogMethodName(String logMethodName) {
		this.logMethodName = logMethodName;
	}

	public String getLogMethodParams() {
		return logMethodParams;
	}

	public void setLogMethodParams(String logMethodParams) {
		this.logMethodParams = logMethodParams;
	}

	public String getLogIp() {
		return logIp;
	}

	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}

	public Long getLogTime() {
		return logTime;
	}

	public void setLogTime(Long logTime) {
		this.logTime = logTime;
	}

	public String getLogResult() {
		return logResult;
	}

	public void setLogResult(String logResult) {
		this.logResult = logResult;
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}
	
	
}
