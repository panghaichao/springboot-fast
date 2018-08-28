package com.cctc.fast.core.websocket;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import com.cctc.fast.common.utils.string.StringUtil;
import java.util.Map;


/**
 * 
 * 前端页面与后台通信握手拦截器, 可用于完善定向发送信息功能。
 * @author Hejeo
 *
 */
public class WebSocketInterceptor implements HandshakeInterceptor {
    
    private final static Logger logger = LoggerFactory.getLogger(WebSocketInterceptor.class);
    
    private final static String CURRENT_USER = "CURRENT_USER";
    
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    	logger.info("websocket 握手开始...");
    	
    	if(request.getHeaders().containsKey("Sec-WebSocket-Extensions")){
    		request.getHeaders().set("Sec-WebSocket-Extensions","permessage-deflate");
    	}
    	if(request instanceof ServletServerHttpRequest){
    		ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
    		HttpServletRequest hrequest = servletRequest.getServletRequest();
    		
    		String userId = hrequest.getParameter(WebsocketCode.USERID);
    		if(StringUtil.notBlank(userId)){
    			logger.info("websocket 握手[userId=" + userId + "]");
    			attributes.put(CURRENT_USER,userId);
    		}else{
    			logger.error("websocket 握手[userId is null]");
    		}
    	}
    	logger.info("websocket 握手完成...");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
        logger.info("afterHandshake完成");
    }
}
