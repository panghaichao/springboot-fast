layui.use(['form', 'tree', 'layer','laytpl'], function() {	
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl;
    
    init();
    
    var param ={};
    
    function init(){
    	$("#dictTree").empty();
    	$.get(contextPath+"/dict/dicts",param,function(data){
        	if(data.status==0){
        		var dictTree = layui.treeGird({
        	        elem: '#dictTree', //传入元素选择器
        	        spreadable: false, //设置是否全展开，默认不展开
        	        checkbox : false,
        	        nodes: data.data,
        	        layout: [
        	            {name: '字典名称', treeNodes: true, headerClass: 'value_col', colClass: 'value_col', style: 'width: 20%' },
        	            {name: '字典编码', field: 'dictCode', headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%'}, 
        	            {name: '字典类型',headerClass: 'value_col', colClass: 'value_col', style: 'width: 5%',
        	            	render: function(row) {
        	            		var name = "类型"
        	            		if(row.dictType=="1"){
        	            			name = "明细"
    	            				return '<div class="layui-table-cell"><span class="layui-badge layui-bg-orange">'+(name)+'</span></div>'; //列渲染
        	            		}
        	            		return '<div class="layui-table-cell"><span class="layui-badge">'+(name)+'</span></div>'; //列渲染
        	            	}
        	            },
        	            {name: '字典Key', field: 'dictKey', headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%'},
        	            {name: '字典Val', field: 'dictVal', headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%'},
        	            {name: '字典状态', headerClass: 'value_col', colClass: 'value_col', style: 'width: 8%',
        	            	render: function(row) {
        	            		if(row.dictState == 'on'){
        	            			return '<input type="checkbox" name="dictState" lay-filter="dictState" lay-skin="switch" lay-text="启用|禁用" checked>';
        	            		}else{
        	            			return '<input type="checkbox" name="dictState" lay-filter="dictState" lay-skin="switch" lay-text="启用|禁用">';
        	            		}
        	            		
        	            	}
        	            },
        	            {name: '排序', field: 'dictSort', headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%'},
        	            {
        	                name: '操作',
        	                headerClass: 'value_col',
        	                colClass: 'value_col',
        	                style: 'width: 30%',
        	                render: function(row) {
        	                	var chil_len=row.children.length;
        	                  
        	                	var html_con = '<div class="operating" data-id="'+ row.id + '" " >';
    	    	                	if(row.dictType == '0'){
    	    	                		html_con += '<a class="layui-btn layui-btn-normal layui-btn-xs  addChild" ><i class="layui-icon layui-icon-add-1"></i>添加明细</a>';
    	    	            		}
      						 	 		html_con += '<a class="layui-btn layui-btn-xs  editDict" ><i class="layui-icon layui-icon-edit"></i> 编辑</a>';
    					 	 		if(chil_len==0){
      						 	 		html_con += '<a class="layui-btn layui-btn-danger layui-btn-xs  delDict" ><i class="layui-icon layui-icon-delete"></i>删除</a>';
    					 	 		}
    					 	 		html_con += '</div>';
      						 	 		
    		   					return html_con;
        	                }
        	              }
        	        ]
        	    });
        		form.render();
        		
        		intiTool();
        	}else{
        		layer.msg(data.msg, {icon: 5});
        	}
        });
    }
    
    
    /**
     * 初始化操作按钮
     */
    function intiTool(){
    	// 添加子菜单
		$("#dictTree div.operating .addChild ").click(function(e){
			e.stopPropagation();
			var rowid = $(this).parent().attr("data-id");
			addDict(rowid);
		});
		
		// 编辑菜单
		$("#dictTree div.operating .editDict ").click(function(e){
			e.stopPropagation();
			var rowid = $(this).parent().attr("data-id");
			editDict(rowid);
		});
		
		
		// 删除菜单
		$("#dictTree div.operating .delDict ").click(function(e){
			e.stopPropagation();
			var rowid = $(this).parent().attr("data-id");
			delDict(rowid);
		});
		
		
		$('.collapse').on('click', function() {
			layui.collapse(dictTree);
	    });

	    $('.expand').on('click', function() {
	    	layui.expand(dictTree);
	    });
		
    }
    
    
    function addDict(rowid){
    	var index = layui.layer.open({
            title : "添加数据字典",
            type : 2,
            content : contextPath+"/dict/toadd/"+rowid,
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回数据字典列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
        	layui.layer.full(index);
        })
    }
    
    function editDict(rowid){
    	var index = layui.layer.open({
            title : "编辑数据字典",
            type : 2,
            content : contextPath+"/dict/dicts/"+rowid,
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回数据字典列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
        	layui.layer.full(index);
        })
    }
    
    
    function delDict(rowid){
    	layer.confirm('确定删除此数据字典？',{icon:3, title:'提示信息'},function(index){
        	var path = contextPath+"/dict/dicts/deleteone/"+rowid;
        	$.post(path,function (res){
                if(res.msg=="success"){	
                    layer.msg("删除成功",{time: 1000,icon: 6},function(){
                    	location.reload();
                    	layer.close(index);
                    });
                }else{
                    layer.msg(res.msg, {icon: 5});
                }
            });
        });
    }
    
    
    $(".addDict_btn").click(function(){
    	addDict(0);
    })
    
    //搜索
    $(".search_btn").on("click",function(){
    	var searchVal = $(".searchVal").val();
        if( searchVal != ''){
        	var loadIndex = layer.load(2, {
                shade: [0.3, '#333']
            });
        	
        	param = {searchVal:searchVal};
        	init();
        	layui.expand(dictTree);
        	
            layer.close(loadIndex);
        }else{
            layer.msg("请输入搜索的内容");
        }
    });
    
})
