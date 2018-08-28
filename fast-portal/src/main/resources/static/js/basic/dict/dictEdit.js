layui.use(['form','layer'],function(){
	
	var form = layui.form,
    layer = layui.layer,
	$  = layui.jquery;

	var dictState = "on";
    form.on("submit(editDict)",function(data){
    	data.field.dictState = dictState;
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            type:"POST",
            url:contextPath+"/dict/edit",
            dataType:"json",
            contentType:"application/json",
            data:JSON.stringify(data.field),
            success:function(res){
            	top.layer.close(index);
                if(res.msg=="success"){
                    parent.layer.msg("菜单更新成功!",{time:1000,icon: 6},function(){
                    	layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    });
                }else{
                    layer.msg(res.msg, {icon: 5});
                }
            }
        });
        return false;
    })
    
    form.on('switch(dictState)', function(data){
    	if(!data.elem.checked){
    		dictState = "off";
    	}
	});
    
    
    form.on("submit(cancelDict)",function(data){
    	//先得到当前iframe层的索引
    	var index = parent.layer.getFrameIndex(window.name); 
    	parent.layer.close(index); //再执行关闭   
        return false;
    })
})