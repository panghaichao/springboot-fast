layui.use(['form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    form.on("submit(addUser)",function(data){
    	
    	var selectRole = [];
        $('input[name="roles"]:checked').each(function(){
            selectRole.push($(this).val());
        });
        data.field.roleList = selectRole;
    	
    	
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            type:"POST",
            url:contextPath+"/sysuser/add",
            dataType:"json",
            contentType:"application/json",
            data:JSON.stringify(data.field),
            success:function(res){
            	top.layer.close(index);
                if(res.msg=="success"){
                    parent.layer.msg("用户添加成功!",{time:1000,icon: 6},function(){
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
    
    
    /**
     * 初始化性別
     */
    var dicSex = dicCode.global["dicSex"];
    var sexHtml="";
    $.each(dicSex,function(index,value){
    	sexHtml+= "<input type='radio' name='userSex' value='"+index+"' title='"+value+"' checked>"
	});
    $(".userSex").html(sexHtml)
    form.render('radio');


})