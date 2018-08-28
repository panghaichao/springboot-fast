layui.config({
    base : contextPath+"/assets/plugins/"
}).extend({
    "formSelects" : "formSelects-v4"
})

layui.use(['form','layer','formSelects','laydate'],function(){
    var form = layui.form
        layer = layui.layer,
        $ = layui.jquery,
        formSelects = layui.formSelects,
        laydate = layui.laydate;
    
    
    //渲染开卡日期  默认当前日期
    laydate.render({
    	elem: '#cardDate', 
    	value: new Date(),
    	max: 0
    });
    
    //渲染出生日期
    laydate.render({
    	elem: '#birthDate',
    	max: 0
    });
    
    
    // 会员编号图标 tips提示
    $('#createMemberCardCode').on('mouseover', function(){
    	layer.tips('点我生成会员编号', '#createMemberCardCode',{
			time: 1000
		});
    });
    
    // 会员编号图标 点击事件
    $('#createMemberCardCode').on('click', function(){
    	 $("#memberCardCode").val(randomNum(10))
    });
    
    /**
     * 生成随机编号
     */
    function randomNum(n){
    	var t='';
    	for(var i=0;i<n;i++){
    		t+=Math.floor(Math.random()*10);
    	}
    	return t;
	}
   
    
    /**
     * 根据输入的身份证号码计算出生日期和年龄  开始
     */
    $("#identityCard").blur(function(){
    	var idCard = this.value;
    	var birthday = "";  
        if(idCard != null && idCard != ""){  
            if(idCard.length == 15){  
                birthday = "19"+idCard.substr(6,6);  
            } else if(idCard.length == 18){  
                birthday = idCard.substr(6,8);  
            }  
          
            birthday = birthday.replace(/(.{4})(.{2})/,"$1-$2-");  
        }  
        
        $("#birthDate").val(birthday)
        
        //时间字符串里，必须是“/”
	    var birthDate = new Date(birthday);
	    var nowDateTime = new Date();
	    var age = nowDateTime.getFullYear() - birthDate.getFullYear();
	    //再考虑月、天的因素;.getMonth()获取的是从0开始的，这里进行比较，不需要加1
	    if (nowDateTime.getMonth() < birthDate.getMonth() || (nowDateTime.getMonth() == birthDate.getMonth() && nowDateTime.getDate() < birthDate.getDate())) {
	        age--;
	    }
	    if(!isNaN(age)){ 
	    	$("#memberAge").val(age)
	    }else{
	    	$("#memberAge").val("")
	    }
    }); 
    /**
     * 根据输入的身份证号码计算出生日期和年龄  完毕
     */
    
    
    
    /**
     * 自定义表单校验规则
     */
	form.verify({
		  username: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
		      return '用户名不能有特殊字符';
		    }
		    if(/(^\_)|(\__)|(\_+$)/.test(value)){
		      return '用户名首尾不能出现下划线\'_\'';
		    }
		    if(/^\d+\d+\d$/.test(value)){
		      return '用户名不能全为数字';
		    }
		  }
		  
		  //我们既支持上述函数式的方式，也支持下述数组的形式
		  //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
		  ,pass: [
		    /^[\S]{6,12}$/
		    ,'密码必须6到12位，且不能出现空格'
		  ] 
	});      	
    
	
	/**
     * 初始化性別
     */
    var dicSex = dicCode.global["dicSex"];
    var sexHtml="";
    $.each(dicSex,function(index,value){
    	sexHtml+= "<input type='radio' name='memberSex' value='"+index+"' title='"+value+"' checked>"
	});
    $(".memberSex").html(sexHtml)
    form.render('radio');
	
    
    
    
    
    /**
     * 初始化会员等级
     */
    $.get(contextPath+"/memberLevel/getAllMemberLevelListForSelect", function (result) {
        if(result.status == 0){
        	var sexHtml="";
            $.each(result.data,function(index,value){
            	sexHtml+= "<option value='"+value.memberLevelId+"'>"+value.memberLevelName+"</option>"
        	});
            $("#memberLevelId").append(sexHtml)
        }
    	form.render('select');
    });
    
    
    
    
//    form.render('radio');
    
    
	
    /**
     * 初始化地区数据
     */
    formSelects.data('area', 'server', {
        url: contextPath+"/assets/json/area_address.json",
        linkage: true,
        linkageWidth: 130
    });
    
    
    
    
    form.on("submit(addMember)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            type:"POST",
            url:contextPath+"/memberInfo/add",
            dataType:"json",
            contentType:"application/json",
            data:JSON.stringify(data.field),
            success:function(res){
            	top.layer.close(index);
                if(res.msg=="success"){
                    parent.layer.msg("会员添加成功!",{time:1000,icon: 6},function(){
                    	layer.closeAll("iframe");
                    	$('#memberForm')[0].reset();
                    	
                    	formSelects.value('area', []);
                        //刷新父页面
//                        parent.location.reload();
                    });
                }else{
                    layer.msg(res.msg, {icon: 5});
                }
            }
        });
        return false;
    })

})