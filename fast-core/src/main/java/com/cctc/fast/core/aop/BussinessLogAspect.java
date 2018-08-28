package com.cctc.fast.core.aop;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cctc.fast.core.base.service.IBussinessLogService;

/**
 * 业务日志切面
 * 
 * @author Hejeo
 *
 */
@Aspect
@Component
public class BussinessLogAspect {
	
	@Autowired
    private IBussinessLogService bussinessLogService;
	
	ThreadPoolExecutor pool = new ThreadPoolExecutor(10,30,30L,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());

	/**
	 * 切入点
	 */
	@Pointcut("@annotation(com.cctc.fast.core.annotation.BussinessLog)")
	public void logPointCut() {
		
	}
	
	@Around("logPointCut()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
		// 当前时间
		long beginTime = System.currentTimeMillis();
		
        //先执行业务
        Object result = point.proceed();
        
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        
        // 保存日志到数据库
        pool.execute(new SaveBussinessLogTask(point, time,null,bussinessLogService));

        return result;
    }
	
	/**
     * 拦截异常操作
     * 
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfter(JoinPoint point, Exception e)
    {
        // 保存日志到数据库
        pool.execute(new SaveBussinessLogTask(point, 0,e,bussinessLogService));
    }
	
}
