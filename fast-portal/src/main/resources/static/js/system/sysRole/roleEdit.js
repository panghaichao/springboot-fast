layui.config({
	base: '/assets/plugins/tree/',
}).extend({
	authtree: 'authtree',
});

layui.use(['jquery', 'authtree', 'form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        authtree = layui.authtree,
        $ = layui.jquery;

    form.on("submit(editRole)",function(data){
    	var id_array=new Array();  
    	$('input[name="authids"]:checked').each(function(){  
    	    id_array.push($(this).val());//向数组中添加元素  
    	});  
//    	var idstr=id_array.join(',');//将数组元素连接起来以构建一个字符串  
    	
    	data.field.menus=id_array;
    	
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            type:"POST",
            url:contextPath+"/sysrole/edit",
            dataType:"json",
            contentType:"application/json",
            data:JSON.stringify(data.field),
            success:function(res){
            	top.layer.close(index);
                if(res.msg=="success"){
                    parent.layer.msg("角色更新成功!",{time:1000,icon: 6},function(){
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
    
    
    form.on("submit(cancelRole)",function(data){
    	//先得到当前iframe层的索引
    	var index = parent.layer.getFrameIndex(window.name); 
    	parent.layer.close(index); //再执行关闭   
        return false;
    })
    
    $.ajax({
    	type:"GET",
		url: contextPath+'/sysrole/roles/getRoleMenuTree/'+roleId,
		dataType: 'json',
		success: function(data){
			$.each(data.data,function(key,values){
				var keyArr = key.split('_');
				var id = keyArr[0];
				var name = keyArr[1];
				var html = "";
				html+=" <div class='layui-inline cctc'> ";
				html+=" 	<fieldset class='layui-elem-field '>";
				html+=" 	<legend>"+name+"</legend>";
				html+=" 	<div class='layui-field-box'>";
				html+=" 	 	<div id='menuTree"+id+"'></div>";
				html+=" 	</div>";
				html+=" 	</fieldset>";
				html+=" </div>";
				$("#roleMenuTree").append(html);
				// 渲染时传入渲染目标ID，树形结构数据（具体结构看样例，checked表示默认选中），以及input表单的名字
				authtree.render('#menuTree'+id+'', data.data[key], {inputname: 'authids', layfilter: 'lay-check-auth', openall: true});
			});
		}
	})

})