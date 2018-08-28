package com.cctc.fast.portal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * 任务调度测试类
 * @author Hejeo
 *
 */
@Component
public class MyTastTest {
	private final static Logger LOGGER = LoggerFactory.getLogger(MyTastTest.class);
	public void sayHello(){
		LOGGER.error("hello");
		System.out.println("hello");
	}
}
