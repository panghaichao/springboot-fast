var gcfp = {};

gcfp.mainWebSocket = null

gcfp.websocket = function(){
	this.webSocketObj = null;
	this.isConnect = false;
	this.__listeners = {};
	this.__listeners.onOpen = {};
    this.__listeners.onMessage = {};
    this.__listeners.onClose = {};
    this.__listeners.onError = {};
};

gcfp.websocket.prototype.init = function(apppath,userid,channel){
	var that = this;
	channel = channel?channel:"0";
	if('WebSocket' in window){
		this.webSocketObj = new WebSocket('ws://' + window.location.host + '/' + apppath + '/websocketApp?userId=' + userid + '&channel=' + channel);  
    }else if('MozWebSocket' in window){
    	this.webSocketObj = new MozWebSocket('ws://' + window.location.host + '/' + apppath + '/websocketApp?userId=' + userid + '&channel=' + channel);
    }else{
        return;
    }
	this.webSocketObj.onopen = function(){
		that.isConnect = true;
		that._onOpen();
	}
	this.webSocketObj.onmessage = function(e){
		var data = e.data;
		var json = null;
		if(data && data != ''){
			json = $.evalJSON(data);
		}
		if(json){
			that._onMessage(json);
		}
	}
	this.webSocketObj.onclose = function(){
		that.isConnect = false;
		that._onClose();
	}
	
	this.webSocketObj.onerror = function(e){
		that._onError(e);
	}
	
};

gcfp.websocket.prototype._onOpen = function(){
	for(var key in this.__listeners.onOpen){
		try{
			this.__listeners.onOpen[key]();
		}catch(e){
			delete this.__listeners.onOpen[key];
			continue;
		}
	}
}

gcfp.websocket.prototype._onMessage = function(json){
	for(var key in this.__listeners.onMessage){
		try{
			this.__listeners.onMessage[key](json);
		}catch (e){
			delete this.__listeners.onMessage[key];
			continue;
		}
	}
}

gcfp.websocket.prototype._onClose = function(){
	for(var key in this.__listeners.onClose){
		try{
			this.__listeners.onClose[key]();
		}catch (e){
			delete this.__listeners.onClose[key];
			continue;
		}
	}
}

gcfp.websocket.prototype._onError = function(e){
	for(var key in this.__listeners.onError){
		try{
			this.__listeners.onError[key](e);
		}catch (e){
			delete this.__listeners.onError[key];
			continue;
		}
	}
}

/**
 * 增加侦听函数
 * type 类型
 * name 名称
 * func 函数
 * */
gcfp.websocket.prototype.addListener = function(type,name,func){
	if("onOpen" == type){
		this.addOnOpenListener(name,func);
	}else if("onClose" == type){
		this.addOnCloseListener(name,func);
	}else if("onMessage" == type){
		this.addOnMessageListener(name,func);
	}else if("onError" == type){
		this.addOnErrorListener(name,func);
	}
}

/**
 * 移除侦听函数
 * 
 * */
gcfp.websocket.prototype.removeListener = function(type,name){
	if("onOpen" == type){
		this.removeOnOpenListener(name);
	}else if("onClose" == type){
		this.removeOnCloseListener(name);
	}else if("onMessage" == type){
		this.removeOnMessageListener(name);
	}else if("onError" == type){
		this.removeOnErrorListener(name);
	}
}

/**
 * 增加连接成功侦听
 * */
gcfp.websocket.prototype.addOnOpenListener = function(name,func){
	this.__listeners.onOpen[name] = func;
}

/**
 * 移除连接成功侦听
 * */
gcfp.websocket.prototype.removeOnOpenListener = function(name){
	delete this.__listeners.onOpen[name];
}

/**
 * 增加接受消息侦听
 * */
gcfp.websocket.prototype.addOnMessageListener = function(name,func){
	this.__listeners.onMessage[name] = func;
}

/**
 * 移除接受消息侦听
 * */
gcfp.websocket.prototype.removeOnMessageListener = function(name){
	delete this.__listeners.onMessage[name];
}

/**
 * 增加关闭侦听
 * */
gcfp.websocket.prototype.addOnCloseListener = function(name,func){
	this.__listeners.onClose[name] = func;
}

/**
 * 移除关闭侦听
 * */
gcfp.websocket.prototype.removeOnCloseListener = function(name){
	delete this.__listeners.onClose[name];
}

/**
 * 增加出错侦听
 * */
gcfp.websocket.prototype.addOnErrorListener = function(name,func){
	this.__listeners.onError[name] = func;
}

/**
 * 移除出错侦听
 * */
gcfp.websocket.prototype.removeOnErrorListener = function(name){
	delete this.__listeners.onError[name];
}

/**
 * 移除所有侦听
 * */
gcfp.websocket.prototype.removeAllListener = function(){
	this.__listeners = {};
    this.__listeners.onOpen = {};
    this.__listeners.onMessage = {};
    this.__listeners.onClose = {};
    this.__listeners.onError = {};
}

/**
 * 发送消息
 * */
gcfp.websocket.prototype.sendMessage = function(message){
	if(this.webSocketObj != null && this.isConnect){
		this.webSocketObj.send(message);
	}
}

/**
 * 关闭
 * */
gcfp.websocket.prototype.closeWebSocket = function(){
	this.isConnect = false;
	this.webSocketObj.close();
}