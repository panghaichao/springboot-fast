package com.cctc.fast.core.websocket;

import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;
import com.cctc.fast.common.utils.string.StringUtil;

@Component
public class WebSocketMessageHandler implements WebSocketHandler{

	private static final Logger logger = LoggerFactory.getLogger(WebSocketMessageHandler.class);
	
	//在线用户列表
	private static ConcurrentHashMap<String,WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<String,WebSocketSession>();
	
	public synchronized static ConcurrentHashMap<String, WebSocketSession> getWebSocketSessionMap() {
		return webSocketSessionMap;
	}
	
    /**
     * 在WebSocket协商成功后调用，并且打开WebSocket连接准备使用
     * @param webSocketSession
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession)throws Exception {
        // 用户编号
        String userId = getUserId(webSocketSession);
        if (StringUtil.notBlank(userId)) {
        	logger.info("websocket 建立连接[" + webSocketSession.getId() + "," + userId + "]");
        	
        	if(webSocketSessionMap.containsKey(userId)){
        		logger.error("websocket 建立连接" + userId + "已存在");
        		WebSocketSession osession = webSocketSessionMap.get(userId);
        		if(osession != null){
					CloseStatus closeStatus = new CloseStatus(4000);
					osession.close(closeStatus);
				}
        	}
        	webSocketSessionMap.put(userId,webSocketSession);
        	
        	
        	JSONObject json = new JSONObject();
        	json.put("messageContent", "成功建立socket连接");
            webSocketSession.sendMessage(new TextMessage(json.toJSONString()));
        }else{
        	logger.error("websocket 建立连接[userId is null]");
        }
    }

    /**
     * 处理来自底层WebSocket消息传输的错误
     * @param webSocketSession
     * @param throwable 错误
     * @throws Exception 异常
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
    	// 用户编号
        String userId = getUserId(webSocketSession);
        if (StringUtil.notBlank(userId)) {
        	WebSocketSession msession = webSocketSessionMap.get(userId);
        	if(webSocketSession != null && webSocketSession.getId().equals(msession.getId())){
        		logger.info("websocket 消息异常断开连接[" + webSocketSession.getId() + "," + userId + "]");
        		webSocketSessionMap.remove(userId);
        	}
        }else{
        	logger.error("websocket 消息异常断开连接[userId is null]");
        }
    	
    	if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
    }

    /**
     * 在网络套接字连接关闭后或在传输错误发生后调用。
     * 尽管从技术上讲，会话可能仍然是开放的，但取决于底层实现，在这一点上发送消息是不鼓励的，而且很可能不会成功。
     * @param webSocketSession webSocketSession
     * @param closeStatus closeStatus
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
    	if(closeStatus.getCode() == 4000){
			return;
		}
    	
    	// 用户编号
        String userId = getUserId(webSocketSession);
        if (StringUtil.notBlank(userId)) {
        	WebSocketSession msession = webSocketSessionMap.get(userId);
        	if(webSocketSession != null && webSocketSession.getId().equals(msession.getId())){
        		logger.info("websocket 断开连接[" + webSocketSession.getId() + "," + userId + "," + closeStatus + "]");
        		webSocketSessionMap.remove(userId);
        	}
        }else{
        	logger.error("websocket 断开连接[userId is null]");
        }
        
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        System.out.println("安全退出了系统");
    }

    /**
     * WebSocketHandler是否处理部分消息
     * @return 标志
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    
    
    /**
     * 处理收到的webSocketMessage
     *
     * @param webSocketSession
     * @param webSocketMessage
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) {
        if(!"".equals(webSocketMessage.getPayload())){
        	String json = webSocketMessage.getPayload().toString();
        	logger.info("websocket 收到消息[" + json + "]");
        }
        logger.info(webSocketSession+":"+webSocketMessage.getPayload());
    }
    
    
    /**
     * 发消息
     * @param userId 用户编号
     * @param message 消息内容
     * @return
     */
    public  boolean sendMessage(String userId,String message){
    	try {
    		WebSocketSession wssession = webSocketSessionMap.get(userId);
    		if(wssession != null && wssession.isOpen()){
    			wssession.sendMessage(new TextMessage(message));
    			return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
    }
    

    /**
     * 广播信息
     * @param message
     * @return
     */
    public  boolean sendMessageToAllUsers(String message) {
        boolean allSendSuccess = true;
        try {
        	for(WebSocketSession wssession : webSocketSessionMap.values()){
    			wssession.sendMessage(new TextMessage(message));
    			try{
    				Thread.sleep(1);
    			}catch(Exception e){
    			}
    		}
		} catch (Exception e) {
			allSendSuccess = false;
		}
        return allSendSuccess;
    }

    /**
     * 获取用户id
     * @param session
     * @return
     */
    private String getUserId(WebSocketSession session) {
        try {
            return (String) session.getAttributes().get("CURRENT_USER");
        } catch (Exception e) {
            return null;
        }
    }

}
