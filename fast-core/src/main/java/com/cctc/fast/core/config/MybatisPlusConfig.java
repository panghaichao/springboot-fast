package com.cctc.fast.core.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.mapper.ISqlInjector;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.cctc.fast.core.handler.MyMetaObjectHandler;

/**
 * 
 * MybatisPlus配置类
 * 
 * @author Hejeo
 *
 */
@Configuration
@MapperScan({"com.cctc.fast.*.*.mapper*","com.cctc.fast.*.*.*.mapper*"})
public class MybatisPlusConfig {
	/**
	 * mybatis-plus SQL执行效率插件【生产环境可以关闭】
	 */
	@Bean
	public PerformanceInterceptor performanceInterceptor() {
		PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
		/* <!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 --> */
		performanceInterceptor.setMaxTime(1000);
		// /*<!--SQL是否格式化 默认false-->*/
		performanceInterceptor.setFormat(true);
		return performanceInterceptor;
	}

	@Bean
	public MetaObjectHandler metaObjectHandler() {
		return new MyMetaObjectHandler();
	}

	/**
	 * mybatis-plus分页插件<br>
	 * 文档：http://mp.baomidou.com<br>
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 开启 PageHelper 的支持
        paginationInterceptor.setLocalPage(true);
        return paginationInterceptor;
	}

	/**
	 * 注入主键生成器
	 */
	@Bean
	public IKeyGenerator keyGenerator() {
		return new H2KeyGenerator();
	}
	

	/**
	 * 注入sql注入器
	 */
	@Bean
	public ISqlInjector sqlInjector() {
		return new LogicSqlInjector();
	}

}