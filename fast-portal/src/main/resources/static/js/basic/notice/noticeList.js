layui.use(['form','layer','table','laytpl','element'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table,
        element = layui.element;

    
    var noticeType = "";
    
    element.on('tab(docDemoTabBrief)', function(data){
    	var index = data.index;
    	if(index == 1){
    		noticeType = '2';
    	}else if(index == 2){
    		noticeType = '1';
    	}else if(index == 3){
    		noticeType = '0';
    	}else{
    		noticeType = ""
    	}
    	
    	reloadTable();
	});
    
    
    function reloadTable(){
//    	table.reload("noticeListTable",{
//            page: {
//                curr: 1 //重新从第 1 页开始
//            },
//            where: {
//            	noticeType: noticeType
//            }
//        })
    	tableIns.reload({
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
            	noticeType: noticeType
            }
        })
    }

    
    //操作日志列表
    var tableIns = table.render({
        elem: '#noticeList',
        url:contextPath+'/notice/notices',
        text: {
        	none: '暂无相关数据' //默认：无数据。
        },
        cellMinWidth : 95,
        page : true,
        height : "full-150",
        limits : [10,15,20,25],
        limit : 10,
        id : "noticeListTable",
        cols : [[
            {type: "checkbox", fixed:"left"},
            {field: 'noticeTitle', title: '标题', align:"center",templet:function(d){
        		return '<a class="layui-blue" title="查看消息内容" lay-event="getNoticeInfo">'+d.noticeTitle+'</a>';
            }},
            {field: 'noticeType', title: '消息类型',  align:'center',templet:function(d){
            	var value = dicCode.global["noticeType"][d.noticeType];
        		var color = dicCode.global["dicColor"][d.noticeType];
        		return '<div class="layui-table-cell"><span class="layui-badge '+color+' ">'+(value)+'</span></div>'; //列渲染
            }},
            {field: 'noticeStatus', title: '消息状态',  align:'center',templet:function(d){
            	var value = dicCode.global["noticeStatus"][d.noticeStatus];
        		var color = dicCode.global["dicColor"][d.noticeStatus];
        		return '<div class="layui-table-cell"><span class="layui-badge '+color+' ">'+(value)+'</span></div>'; //列渲染
            }},
            {field: 'readTime', title: '读取时间', align:'center',templet:function(d){
                return getSmpFormatDateByLong(d.readTime,true)
            }},
            {field: 'createTime', title: '创建时间', align:'center',templet:function(d){
                return getSmpFormatDateByLong(d.createTime,true)
            }}
        ]]
    });
    
    
    //列表操作
    table.on('tool(noticeList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'getNoticeInfo'){ //查看详情
        	console.log(data);
        	getNoticeInfo(data);
        }
    });
    
   /**
    * 查看详情
    */
    function getNoticeInfo(data){
    	var editIndex = layui.layer.open({
            title : "查看消息",
            type : 2,
            shade: 0.5,
            area : ['50%', '50%'],
            content : contextPath+"/notice/notices/"+data.noticeId,
            success: function(layero, index) {
            	layui.layer.iframeAuto(editIndex);
            },
            cancel: function(){
            	reloadTable();
            }
        });
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function(){
//        	layui.layer.full(editIndex);
        });
//        layui.layer.full(editIndex);
    }
    
    
    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('noticeListTable'),
            data = checkStatus.data,
            unReadCount = 0,
            noticeDetailIds = [];
        
        if(data.length > 0) {
            for (var i in data) {
            	if(data[i].noticeStatus == 0){
            		unReadCount++;
            	}
            	noticeDetailIds.push(data[i].noticeDetailId);
            }
            layer.confirm('确定删除选中的消息？', {icon: 3, title: '提示信息'}, function (index) {
            	$.post(contextPath+"/notice/deleteall",{"noticeDetailIds":JSON.stringify(noticeDetailIds),"unReadCount":JSON.stringify(unReadCount)},function (res){
                    if(res.msg=="success"){	
                        layer.msg("删除成功",{time: 1000,icon: 6},function(){
                        	reloadTable();
                        	layer.close(index);
                        });
                    }else{
                        layer.msg(res.msg, {icon: 5});
                    }
                });
            })
        }else{
            layer.msg("请选择需要删除的消息", {icon: 7});
        }
    })
})
