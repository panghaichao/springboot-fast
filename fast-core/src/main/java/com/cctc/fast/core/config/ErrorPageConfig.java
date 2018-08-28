package com.cctc.fast.core.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorPageConfig implements EmbeddedServletContainerCustomizer{

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.addErrorPages(
                new ErrorPage(HttpStatus.BAD_REQUEST, "/400.html"),
                new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html"),
                new ErrorPage(HttpStatus.NOT_FOUND, "/404/"),
                new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html")
        );
	}
}
