package com.cctc.fast.core.schedule.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.cctc.fast.core.schedule.zk.ZKManager.KEYS;


@ConfigurationProperties(prefix = "uncode.schedule",ignoreInvalidFields = true)
public class UncodeScheduleConfig{
	
	/**
	 * zookeeper 连接地址
	 */
	private String zkConnect;
	/**
	 * zookeeper 根节点路径
	 */
	private String rootPath = "/uncode/schedule";
	/**
	 * zookeeper连接超时时间   单位：毫秒
	 */
	private int zkSessionTimeout = 60000;
	/**
	 * zookeeper 用户名
	 */
	private String zkUsername;
	/**
	 * zookeeper 密码
	 */
	private String zkPassword;
	/**
	 * ip地址黑名单列表
	 */
	private List<String> ipBlackList;
	
	/**
	 * 是否启动调度任务 默认启动 true  不启动false
	 */
	private String isStartSchedule = "false";
	
	
	private List<String> targetBean;
	private List<String> targetMethod;
	private List<String> cronExpression;
	private List<String> startTime;
	private List<String> period;
	private List<String> delay;
	private List<String> params;
	private List<String> type;
	private List<String> extKeySuffix;
	private List<String> beforeMethod;
	private List<String> afterMethod;
	private List<String> threadNum;
	
	
	/**
	 * 获取配置文件
	 * @return
	 */
	public Map<String, String> getConfig(){
		Map<String, String> properties = new HashMap<String, String>();
		
		properties.put(KEYS.zkConnectString.key, zkConnect);
		if(StringUtils.isNotBlank(rootPath)){
			properties.put(KEYS.rootPath.key, rootPath);
		}
		if(zkSessionTimeout > 0){
			properties.put(KEYS.zkSessionTimeout.key, zkSessionTimeout+"");
		}
		if(StringUtils.isNotBlank(zkUsername)){
			properties.put(KEYS.userName.key, zkUsername);
		}
		if(StringUtils.isNotBlank(zkPassword)){
			properties.put(KEYS.password.key, zkPassword);
		}
		StringBuilder sb = new StringBuilder();
		if(ipBlackList != null && ipBlackList.size() > 0){
			for(String ip:ipBlackList){
				sb.append(ip).append(",");
			}
			ipBlackList.remove(sb.lastIndexOf(","));
		}
		
		if(StringUtils.isNotBlank(isStartSchedule)){
			properties.put(KEYS.isStartSchedule.key, isStartSchedule);
		}
		properties.put(KEYS.ipBlacklist.key, sb.toString());
		return properties;
	}
	
	
	public String getZkConnect() {
		return zkConnect;
	}
	public void setZkConnect(String zkConnect) {
		this.zkConnect = zkConnect;
	}
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	public int getZkSessionTimeout() {
		return zkSessionTimeout;
	}
	public void setZkSessionTimeout(int zkSessionTimeout) {
		this.zkSessionTimeout = zkSessionTimeout;
	}
	public String getZkUsername() {
		return zkUsername;
	}
	public void setZkUsername(String zkUsername) {
		this.zkUsername = zkUsername;
	}
	public String getZkPassword() {
		return zkPassword;
	}
	public void setZkPassword(String zkPassword) {
		this.zkPassword = zkPassword;
	}
	public List<String> getIpBlackList() {
		return ipBlackList;
	}
	public void setIpBlackList(List<String> ipBlackList) {
		this.ipBlackList = ipBlackList;
	}


	public List<String> getTargetBean() {
		return targetBean;
	}


	public void setTargetBean(List<String> targetBean) {
		this.targetBean = targetBean;
	}


	public List<String> getTargetMethod() {
		return targetMethod;
	}


	public void setTargetMethod(List<String> targetMethod) {
		this.targetMethod = targetMethod;
	}


	public List<String> getCronExpression() {
		return cronExpression;
	}


	public void setCronExpression(List<String> cronExpression) {
		this.cronExpression = cronExpression;
	}


	public List<String> getStartTime() {
		return startTime;
	}


	public void setStartTime(List<String> startTime) {
		this.startTime = startTime;
	}


	public List<String> getPeriod() {
		return period;
	}


	public void setPeriod(List<String> period) {
		this.period = period;
	}


	public List<String> getDelay() {
		return delay;
	}


	public void setDelay(List<String> delay) {
		this.delay = delay;
	}


	public List<String> getParams() {
		return params;
	}


	public void setParams(List<String> params) {
		this.params = params;
	}


	public List<String> getType() {
		return type;
	}


	public void setType(List<String> type) {
		this.type = type;
	}


	public List<String> getExtKeySuffix() {
		return extKeySuffix;
	}


	public void setExtKeySuffix(List<String> extKeySuffix) {
		this.extKeySuffix = extKeySuffix;
	}


	public List<String> getBeforeMethod() {
		return beforeMethod;
	}


	public void setBeforeMethod(List<String> beforeMethod) {
		this.beforeMethod = beforeMethod;
	}


	public List<String> getAfterMethod() {
		return afterMethod;
	}


	public void setAfterMethod(List<String> afterMethod) {
		this.afterMethod = afterMethod;
	}

	public List<String> getThreadNum() {
		return threadNum;
	}


	public void setThreadNum(List<String> threadNum) {
		this.threadNum = threadNum;
	}

	public String getIsStartSchedule() {
		return isStartSchedule;
	}

	public void setIsStartSchedule(String isStartSchedule) {
		this.isStartSchedule = isStartSchedule;
	}
	
	
	
	

}
