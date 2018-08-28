package com.cctc.fast;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.cctc.fast.core.schedule.ConsoleManager;
import com.cctc.fast.core.schedule.core.TaskDefine;

//@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)  使用多数据源的话用这个
@SpringBootApplication
@ServletComponentScan
@EnableScheduling
public class FastApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FastApplication.class, args);
		
		/**
		 * 添加定时任务
		 */
//		TaskDefine taskDefine = new TaskDefine();
//		taskDefine.setTargetBean("myTastTest");
//		taskDefine.setTargetMethod("sayHello");
//		taskDefine.setPeriod(1000);
//		ConsoleManager.addScheduleTask(taskDefine);
	}
}
