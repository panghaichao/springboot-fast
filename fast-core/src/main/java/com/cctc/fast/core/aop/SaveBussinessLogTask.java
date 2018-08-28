package com.cctc.fast.core.aop;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import com.alibaba.fastjson.JSON;
import com.cctc.fast.common.utils.http.HttpUtil;
import com.cctc.fast.common.utils.id.KeyUtil;
import com.cctc.fast.core.annotation.BussinessLog;
import com.cctc.fast.core.base.entity.OperateLog;
import com.cctc.fast.core.base.service.IBussinessLogService;

/**
 * 保存业务请求日志
 * @author Hejeo
 *
 */
public class SaveBussinessLogTask implements Runnable{
	private JoinPoint  joinPoint;
    private long time;
    private String ip;
    private Exception ex;
    private IBussinessLogService bussinessLogService;
    
    public SaveBussinessLogTask(JoinPoint  point, long time,Exception e,IBussinessLogService bussinessLogService) {
        this.joinPoint = point;
        this.time = time;
        //获取请求的ip
        this.ip = HttpUtil.getIp();
        this.ex = e;
        this.bussinessLogService = bussinessLogService;
    }
    
	@Override
	public void run() {
		saveLog(joinPoint, time, ip,ex);
	}

	private void saveLog(JoinPoint  joinPoint, long time, String ip,final Exception e) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		BussinessLog annotation = method.getAnnotation(BussinessLog.class);
		
		// 业务名称
		String bussinessName = annotation.value();
		
		// 请求的类
        String className = joinPoint.getTarget().getClass().getName();
        // 请求方法
        String methodName = signature.getName();
        
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        String params ="";
        if(args.length>0){
        	params = JSON.toJSONString(args[0]);
        }
        
        OperateLog operateLog = new OperateLog();
        operateLog.setLogId(KeyUtil.genUniqueKey());
        operateLog.setLogName(bussinessName);
        operateLog.setLogClassName(className);
        operateLog.setLogMethodName(methodName);
        operateLog.setLogMethodParams(params);
        operateLog.setLogIp(ip);
        operateLog.setLogTime(time);
        operateLog.setLogResult("0");
        if(e!=null){
        	operateLog.setLogResult("1");
        	operateLog.setLogMessage(e.getMessage());
        }
        bussinessLogService.insert(operateLog);
	}
}
