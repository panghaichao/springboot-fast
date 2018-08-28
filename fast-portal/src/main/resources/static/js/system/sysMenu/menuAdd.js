layui.use(['form','layer'],function(){
	
	var form = layui.form,
    layer = layui.layer,
	$  = layui.jquery;
	
//    var form = layui.form
//        layer = parent.layer === undefined ? layui.layer : top.layer,
//        $ = layui.jquery;

	
	form.val("addMenuForm", {
		"menuType": menuType
	})
	
    form.on("submit(addMenu)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            type:"POST",
            url:contextPath+"/sysmenu/add",
            dataType:"json",
            contentType:"application/json",
            data:JSON.stringify(data.field),
            success:function(res){
            	top.layer.close(index);
                if(res.msg=="success"){
                    parent.layer.msg("菜单添加成功!",{time:1000,icon: 6},function(){
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
    
    
    //选择图标
    $("#selectIcon").on("click",function () {
    	iconShow =layer.open({
            type: 2,
            title: '选择图标',
            shadeClose: true,
            content: contextPath+'/icon',
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回添加菜单界面', '.layui-layer-setwin .layui-layer-close', {	
                        tips: 3
                    });
                },500);
            }
        });
        layer.full(iconShow);
    });


})