layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    $(window).resize(function(){
    });
    
    //用户列表
    var tableIns = table.render({
        elem: '#memberList',
        url:contextPath+'/memberInfo/members',
        text: {
        	none: '暂无相关数据' //默认：无数据。
        },
//        even: true, //开启隔行背景
        cellMinWidth : 95,
        page : true,
        height : "full-110",
        limits : [10,15,20,25],
        limit : 20,
        loading: true,
        id : "memberListTable",
        cols : [[
            {type: "checkbox", fixed:"left"},
            {field: 'memberCardCode', title: '会员卡号', align:"center",templet:function(d){
                return '<a class="layui-blue">'+d.memberCardCode+'</a>';
            }},
            {field: 'memberName', title: '会员姓名', align:"center"},
            {field: 'memberSex', title: '会员性别',  align:'center',width:100,templet:function(d){
            	var value = dicCode.global["dicSex"][d.memberSex];
        		var color = dicCode.global["dicColor"][d.memberSex];
        		return '<div class="layui-table-cell"><span class="layui-badge '+color+' ">'+(value)+'</span></div>'; //列渲染
            }},
            {field: 'memberPhone', title: '手机号码', align:"center",width:130},
            {field: 'memberLevelName', title: '会员等级', align:"center",width:130},
            {field: 'memberCardBalance', title: '账户余额', align:'center',sort: true,templet:function(d){
                return '<a class="layui-blue">'+getMoney(d.memberCardBalance)+'</a>';
            }},
            {field: 'totalConsumption', title: '总消费金额', align:'center',sort: true,templet:function(d){
                return '<a class="layui-blue">'+getMoney(d.totalConsumption)+'</a>';
            }},
            {field: 'totalPoint', title: '总积分', align:'center',sort: true,templet:function(d){
                return '<a class="layui-blue">'+d.totalPoint+'</a>';
            }},
            {field: 'usablePoint', title: '可用积分', align:'center',sort: true,templet:function(d){
                return '<a class="layui-blue">'+d.usablePoint+'</a>';
            }},
            {field: 'createTime', title: '创建时间', align:'center',templet:function(d){
                return getSmpFormatDateByLong(d.createTime,true)
            }},
            {title: '操作', templet:'#memberListBar',fixed:"right",align:"center",width:300}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
    	if($(this).hasClass("refreshThis")){
    		$(this).removeClass("refreshThis");
			var loadIndex = layer.load(2, {
                shade: [0.3, '#333']
            });
        	
			tableIns.reload({
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                	searchVal: $(".searchVal").val()  //搜索的关键字
                }
            })
            layer.close(loadIndex);
            
			setTimeout(function(){
				$(".search_btn").addClass("refreshThis");
			},2000)
		}else{
			layer.msg("您点击的速度超过了服务器的响应速度，还是等两秒再刷新吧！");
		}
    	
    });

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('memberListTable'),
            data = checkStatus.data,
            userIds = [];
        
        if(data.length > 0) {
            for (var i in data) {
            	userIds.push(data[i].userId);
            }
            layer.confirm('确定删除选中的用户？', {icon: 3, title: '提示信息'}, function (index) {
            	$.post(contextPath+"/sysuser/users/deleteall",{"userIds":JSON.stringify(userIds)},function (res){
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
            layer.msg("请选择需要删除的用户", {icon: 7});
        }
    })

    //列表操作
    table.on('tool(memberList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'toEditSysUser'){ //编辑
        	toEditSysUser(data);
        }else if(layEvent === 'delSysUser'){ //删除
            layer.confirm('确定删除此用户？',{icon:3, title:'提示信息'},function(index){
            	var path = contextPath+"/sysuser/users/deleteone/"+data.userId;
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
        }else if(layEvent === 'resetPassword'){ // 重置密码
        	layer.confirm('确定重置此用户密码吗？',{icon:3, title:'提示信息'},function(index){
            	var path = contextPath+"/sysuser/resetPassword/"+data.userId;
            	$.post(path,function (res){
                    if(res.msg=="success"){	
                        layer.msg("重置密码成功!",{time: 1000,icon: 6},function(){
                        	 layer.close(index);
                        });
                    }else{
                    	layer.msg(res.msg, {icon: 5}); 
                    }
                });
            });
        }
    });
    
    
    function toEditSysUser(data){
    	var editIndex = layui.layer.open({
            title : "编辑用户",
            type : 2,
            content : contextPath+"/sysuser/users/"+data.userId,
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {	
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
