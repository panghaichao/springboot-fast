layui.use(['form', 'tree', 'layer','laytpl'], function() {	
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl;

    $.get(contextPath+"/sysmenu/menus",function(data){
    	if(data.status==0){
    		var menuTree = layui.treeGird({
    	        elem: '#menuTree', //传入元素选择器
    	        spreadable: false, //设置是否全展开，默认不展开
    	        checkbox : false,
    	        nodes: data.data,
    	        layout: [
    	            {name: '菜单名称', treeNodes: true, headerClass: 'value_col', colClass: 'value_col', style: 'width: 20%' },
    	            {name: '菜单路径', field: 'url', headerClass: 'value_col', colClass: 'value_col', style: 'width: 20%'}, 
    	            {name: '类型',headerClass: 'value_col', colClass: 'value_col', style: 'width: 5%',
    	            	render: function(row) {
    	            		var value = dicCode.global["dicMenuType"][row.menuType];
    	            		var color = dicCode.global["dicColor"][row.menuType];
    	            		return '<div class="layui-table-cell"><span class="layui-badge '+color+' ">'+(value)+'</span></div>'; //列渲染
    	            	}
    	            },
    	            {name: '权限编码', field: 'menuCode', headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%'},
    	            {name: '图标', headerClass: 'value_col', colClass: 'value_col', style: 'width: 5%',
    	            	render: function(row) {
    	            		if(row.menuIcon.indexOf("layui")!=-1){
    	                		// 表示layui的图标
    	                		return '<div class="layui-table-cell laytable-cell-1-username"><i class="layui-icon '+(typeof(row.menuIcon)=="undefined"?'':row.menuIcon)+' "></i></div>'; //列渲染
    	                	}else{
    	                		// 表示自定义的图标
    	                		return '<div class="layui-table-cell laytable-cell-1-username"><i class="custom-icon '+(typeof(row.menuIcon)=="undefined"?'':row.menuIcon)+' "></i></div>'; //列渲染
    	                	}
    	            	}
    	            },
    	            {name: '排序', field: 'menuSort', headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%'},
    	            {
    	                name: '操作',
    	                headerClass: 'value_col',
    	                colClass: 'value_col',
    	                style: 'width: 30%',
    	                render: function(row) {
    	                	var chil_len=row.children.length;
    	                  
    	                	var html_con = '<div class="operating" data-id="'+ row.id + '" " >';
    	                		if(row.menuType!=3){
    	                			html_con += '<a class="layui-btn layui-btn-normal layui-btn-xs  addChild" ><i class="layui-icon layui-icon-add-1"></i>添加子菜单</a>';
    	                		}
  						 	 		html_con += '<a class="layui-btn layui-btn-xs  editMenu" ><i class="layui-icon layui-icon-edit"></i> 编辑</a>';
					 	 		if(chil_len==0){
  						 	 		html_con += '<a class="layui-btn layui-btn-danger layui-btn-xs  delMenu" ><i class="layui-icon layui-icon-delete"></i>删除</a>';
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
    		layer.msg(data.msg);
    	}
    });
    
    /**
     * 初始化操作按钮
     */
    function intiTool(){
    	// 添加子菜单
		$("#menuTree div.operating .addChild ").click(function(e){
			e.stopPropagation();
			var rowid = $(this).parent().attr("data-id");
			addMenu(rowid);
		});
		
		// 编辑菜单
		$("#menuTree div.operating .editMenu ").click(function(e){
			e.stopPropagation();
			var rowid = $(this).parent().attr("data-id");
			editMenu(rowid);
		});
		
		
		// 删除菜单
		$("#menuTree div.operating .delMenu ").click(function(e){
			e.stopPropagation();
			var rowid = $(this).parent().attr("data-id");
			delMenu(rowid);
		});
		
		
		$('.collapse').on('click', function() {
			layui.collapse(menuTree);
	    });

	    $('.expand').on('click', function() {
	    	layui.expand(menuTree);
	    });
		
    }
    
    
    function addMenu(rowid){
    	var index = layui.layer.open({
            title : "添加菜单",
            type : 2,
            content : contextPath+"/sysmenu/toadd/"+rowid,
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回菜单列表', '.layui-layer-setwin .layui-layer-close', {
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
    
    function editMenu(rowid){
    	var index = layui.layer.open({
            title : "编辑菜单",
            type : 2,
            content : contextPath+"/sysmenu/menus/"+rowid,
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回菜单列表', '.layui-layer-setwin .layui-layer-close', {
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
    
    
    function delMenu(rowid){
    	layer.confirm('确定删除此菜单？',{icon:3, title:'提示信息'},function(index){
        	var path = contextPath+"/sysmenu/menus/deleteone/"+rowid;
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
    
    
    $(".addSysMenu_btn").click(function(){
    	addMenu(0);
    })
    
})
