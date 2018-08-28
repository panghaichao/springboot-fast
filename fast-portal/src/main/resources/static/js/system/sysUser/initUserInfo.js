layui.config({
    base : "/assets/plugins/"
}).use(['form','jquery',"address","upload"],function() {
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload,
        address = layui.address;
    
    
    /**
     * 初始化用户信息
     */
    $.get(contextPath+"/sysuser/my/"+userId, function (result) {
        if(result.status == 0){
        	var sysUser = result.data.sysUser;
        	$("#userId").val(sysUser.userId);
        	$("#userAccount").val(sysUser.userAccount);
        	$("#userName").val(sysUser.userName);
        	$("#userNickName").val(sysUser.userNickName);
        	$("#userMobile").val(sysUser.userMobile);
        	$("#userEmail").val(sysUser.userEmail);
        	$("#userDesc").val(sysUser.userDesc);
        	$("#userImage").val(sysUser.userImage);
        	$(".userSex input[value="+sysUser.userSex+"]").attr("checked","checked"); //性别
        	
        	if(sysUser.userImage){
        		$('#userFace').attr('src',"http://paalrbxg0.bkt.clouddn.com/"+sysUser.userImage);
//                $(".userAvatar").attr("src",$(".userAvatar").attr("src").split("images/")[0] + "images/" + window.sessionStorage.getItem('userFace').split("images/")[1]);
            }else{
                $("#userFace").attr("src",contextPath+"/assets/images/face.jpg");
            }
        	form.render();
        }
    });
    
    
    //上传头像
    upload.render({
        elem: '.userFaceBtn',
        url: contextPath+'/uploadAction',
        accept: 'images',
        acceptMime:'image/*',
        method : "post",  //此处是为了演示之用，实际使用中请将此删除，默认用post方式提交
        done: function(res, index, upload){
        	if(res.msg=="success"){
        		$("#userImage").val(res.data.key);
        		$('#userFace').attr('src',"http://paalrbxg0.bkt.clouddn.com/"+res.data.key);
            }else{
                layer.msg(res.msg, {icon: 5});
            }
        }
    });
    
    
    //提交个人资料
    form.on("submit(changeUser)",function(data){
        var index = parent.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            type:"POST",
            url:contextPath+"/sysuser/edit",
            dataType:"json",
            contentType:"application/json",
            data:JSON.stringify(data.field),
            success:function(res){
            	parent.layer.close(index);
                if(res.msg=="success"){
                    parent.layer.msg("用户更新成功!",{time:1000},function(){
                    });
                }else{
                	layer.msg(res.msg, {icon: 5});
                }
            }
        });
        return false;
    });
    
    
    
    
    
    
    
    
});   