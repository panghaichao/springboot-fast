var mainsocket = new gcfp.websocket();


gcfp.mainWebSocket = mainsocket;

mainsocket.init(contextPath,currentUserId);


mainsocket.addOnOpenListener("a",function(){
	console.log("成功建立websocket连接...")
})

mainsocket.addOnCloseListener("main",function(){
	console.log("websocket连接断开...")
});


mainsocket.addOnMessageListener("main",function(json){
	console.log("收到websocket消息...");
	console.log(json);
	
	
	var noticeCount = JSON.parse(window.sessionStorage.getItem("noticeCount"));
	
	if(json.messageType == 1){
		// 减当前消息数量
		var messageContent = json.messageContent;
		noticeCount = noticeCount - messageContent ;
	}else if(json.messageType == 2){
		// 新消息
		noticeCount = noticeCount+1;
		
		$("#noticeIcon").addClass("layui-anim-scale layui-anim-loop");
	}
	
	window.sessionStorage.setItem("noticeCount",JSON.stringify(noticeCount));
	
	$("#noticeText").html(noticeCount);
});