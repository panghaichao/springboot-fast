package com.cctc.fast.core.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.cctc.fast.common.utils.http.HttpUtil;
import com.cctc.fast.core.handler.ExceptionHandle;

/**
 * 
 * 请求异常切面
 * @author Hejeo
 *
 */
@Aspect
@Component
public class HttpAspect {
	private final static Logger LOGGER = LoggerFactory.getLogger(HttpAspect.class);

	@Autowired
	private ExceptionHandle exceptionHandle;

	@Pointcut("execution(public * com.cctc.fast.portal.*.controller.*.*(..))")
	public void log() {

	}

	@Before("log()")
	public void doBefore(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// url
		LOGGER.info("url={}", request.getRequestURL());
		// method
		LOGGER.info("method={}", request.getMethod());
		// ip
		LOGGER.info("ip={}", HttpUtil.getIp());
		// class_method
		LOGGER.info("class_method={}",
				joinPoint.getSignature().getDeclaringTypeName() + "," + joinPoint.getSignature().getName());
		// args[]
		LOGGER.info("args={}", joinPoint.getArgs());
	}

	@Around("log()")
	public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		try {
		} catch (Exception e) {
			return exceptionHandle.exceptionGet(e);
		}
		return proceedingJoinPoint.proceed();
	}

	@AfterReturning(pointcut = "log()", returning = "object") // 打印输出结果
	public void doAfterReturing(Object object) {
		LOGGER.info("response={}", object.toString());
	}
}
