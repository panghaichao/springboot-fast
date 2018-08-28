layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //角色列表
    var tableIns = table.render({
        elem: '#roleList',
        url:contextPath+'/sysrole/roles',
        text: {
        	none: '暂无相关数据' //默认：无数据。
        },
        cellMinWidth : 95,
        page : true,
        height : "full-110",
        limits : [10,15,20,25],
        limit : 20,
        id : "roleListTable",
        cols : [[
            {type: "checkbox", fixed:"left"},
            {field: 'roleName', title: '角色名称', align:"center"},
            {field: 'createTime', title: '创建时间', align:'center',templet:function(d){
                return getSmpFormatDateByLong(d.createTime,true)
            }},
            {title: '操作', templet:'#roleListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
        	var loadIndex = layer.load(2, {
                shade: [0.3, '#333']
            });
        	
//        	table.reload("roleListTable",{
        	tableIns.reload({
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    roleName: $(".searchVal").val()  //搜索的关键字
                }
            })
            layer.close(loadIndex);
        }else{
            layer.msg("请输入搜索的内容");
        }
    });

    //添加角色
    function addRole(){
        var index = layui.layer.open({
            title : "添加角色",
            type : 2,
            content : contextPath+"/sysrole/toadd",
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回角色列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })
    }
    $(".addSysRole_btn").click(function(){
        addRole();
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('roleListTable'),
            data = checkStatus.data,
            roleIds = [];
        
        if(data.length > 0) {
            for (var i in data) {
            	roleIds.push(data[i].roleId);
            }
            layer.confirm('确定删除选中的角色？', {icon: 3, title: '提示信息'}, function (index) {
            	$.post(contextPath+"/sysrole/roles/deleteall",{"roleIds":JSON.stringify(roleIds)},function (res){
                    if(res.msg=="success"){	
                        layer.msg("删除成功",{time: 1000,icon: 6},function(){
                        	 tableIns.reload();
                        	 layer.close(index);
                        });
                    }else{
                        layer.msg(res.msg, {icon: 5});
                    }
                });
            })
        }else{
            layer.msg("请选择需要删除的角色", {icon: 7});
        }
    })

    //列表操作
    table.on('tool(roleList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'toEditSysRole'){ //编辑
        	toEditSysRole(data);
        }else if(layEvent === 'delSysRole'){ //删除
            layer.confirm('确定删除此角色？',{icon:3, title:'提示信息'},function(index){
            	var path = contextPath+"/sysrole/roles/deleteone/"+data.roleId;
            	$.post(path,function (res){
                    if(res.msg=="success"){	
                        layer.msg("删除成功",{time: 1000,icon: 6},function(){
                        	 tableIns.reload();
                        	 layer.close(index);
                        });
                    }else{
                        layer.msg(res.msg, {icon: 5});
                    }
                });
            });
        }
    });
    
    
    function toEditSysRole(data){
    	var editIndex = layui.layer.open({
            title : "编辑角色",
            type : 2,
            content : contextPath+"/sysrole/roles/"+data.roleId,
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回角色列表', '.layui-layer-setwin .layui-layer-close', {	
                        tips: 3
                    });
                },500);
            }
        });
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function(){
        	layui.layer.full(editIndex);
        });
        layui.layer.full(editIndex);
    }

})
