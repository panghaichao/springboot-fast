layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //操作日志列表
    var tableIns = table.render({
        elem: '#operlogList',
        url:contextPath+'/monitor/operlog/operlog',
        text: {
        	none: '暂无相关数据' //默认：无数据。
        },
        cellMinWidth : 95,
        page : true,
        height : "full-110",
        limits : [10,15,20,25],
        limit : 20,
        id : "operlogListTable",
        cols : [[
            {type: "checkbox", fixed:"left"},
            {field: 'logName', title: '业务名称', align:"center"},
            {field: 'logClassName', title: '类名', align:"center"},
            {field: 'logMethodName', title: '方法名称', align:"center"},
            {field: 'logMethodParams', title: '参数', align:"center"},
            {field: 'logIp', title: 'ip地址', align:'center',templet:function(d){
                return '<a class="layui-blue">'+d.logIp+'</a>';
            }},
            {field: 'logResult', title: '请求结果',  align:'center',templet:function(d){
            	if(d.logResult == "0"){
                    return '<span class="layui-btn layui-btn-green layui-btn-xs">成功</span>'
                }else{
                    return '<span class="layui-btn layui-btn-danger layui-btn-xs">失败</span>'
                }
            }},
            {field: 'createTime', title: '创建时间', align:'center',templet:function(d){
                return getSmpFormatDateByLong(d.createTime,true)
            }},
            {title: '操作', templet:'#operlogListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
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
        }else{
            layer.msg("请输入搜索的内容");
        }
    });

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('operlogListTable'),
            data = checkStatus.data,
            logIds = [];
        
        if(data.length > 0) {
            for (var i in data) {
            	logIds.push(data[i].logId);
            }
            layer.confirm('确定删除选中的操作日志？', {icon: 3, title: '提示信息'}, function (index) {
            	$.post(contextPath+"/monitor/operlog/deleteall",{"logIds":JSON.stringify(logIds)},function (res){
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
            layer.msg("请选择需要删除的操作日志");
        }
    })

    //列表操作
    table.on('tool(operlogList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'deloperlog'){ //删除
            layer.confirm('确定删除此操作日志？',{icon:3, title:'提示信息'},function(index){
            	var path = contextPath+"/monitor/operlog/deleteone/"+data.logId;
            	$.post(path,function (res){
                    if(res.msg=="success"){	
                        layer.msg("删除成功",{time: 1000},function(){
                        	 tableIns.reload();
                        	 layer.close(index);
                        });
                    }else{
                        layer.msg(res.msg);
                    }
                });
            });
        }
    });

})
