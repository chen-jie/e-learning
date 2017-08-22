$(function(){
  // Initialize the tree inside the <div>element.
  // The tree structure is read from the contained <ul> tag.
  $("#tree").fancytree({
  	extensions: ["table"],
  	source: {url: "/admin/perm/list", debugDelay: 500},
    renderColumns: function(event, data) {
	        var node = data.node;
	        	tdList = $(node.tr).find(">td");
		        // (index #0 is rendered by fancytree by adding the checkbox)
		        //tdList.eq(0).text(node.getIndexHier()).addClass("alignRight");
		    	// (index #1 is rendered by fancytree)
		        //tdList.eq(1).text(node.data.resourceename);
		    	tdList.eq(1).text(node.data.id);
		    	tdList.eq(2).text(node.data.parentId);
		        tdList.eq(3).text(node.data.url);
		        tdList.eq(4).text(node.data.type);
	  }
  });
  
   $("#tree").contextmenu({
      delegate: "span.fancytree-title",
//      menu: "#options",
      menu: [
          {title: "添加子资源", cmd: "add", uiIcon: "ui-icon-plus"},
          {title: "----"},
          {title: "编辑资源", cmd: "edit", uiIcon: "ui-icon-pencil"},
          {title: "----"},
          {title: "删除资源", cmd: "delete", uiIcon: "ui-icon-trash"}],
      beforeOpen: function(event, ui) {
        var node = $.ui.fancytree.getNode(ui.target);
//        alert(node.data.id+":"+node.data.url);
        // Modify menu entries depending on node status
        $("#tree").contextmenu("enableEntry", "paste", node.isFolder());
        node.setActive();
      },
      select: function(event, ui) {
        var node = $.ui.fancytree.getNode(ui.target);
        var cmd = ui.cmd;
        proccess(cmd,node.data.id);
      }
    });
});
function proccess(cmd,id){
	if("add" == cmd){
		 layer.open({
		        type: 1
		        ,title: '新增资源' //不显示标题栏
		        ,closeBtn: true
		        ,area: '600px'
		        ,shade: 0.8
		        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
		        ,btnAlign: 'c'
		        ,btn: ['确定', '取消']
		 		,yes:function(){
		 			var param = $('#form_').serialize();
		 			if(id != undefined){
		 				param+='&parentId='+id;
		 			}
		 			$.ajax({
		 				url : '/admin/perm/add',
		 				type: 'post',
		 				data: param,
		 				dataType:'json',
		 				success: function(res){
		 	                if(res.status === 0) {
		 	                    layer.alert(res.msg,function(){
		 	                    	layer.closeAll();
		 	                    	window.location.reload();
		 	                    });
		 	                } else {
		 	                    layer.alert(res.msg);
		 	                }
		 	            }, error: function(e){
		 	            	layer.alert('请求异常，请重试');
		 	            }
		 			});
		 		}
		        ,moveType: 1 //拖拽模式，0或者1
		        ,content: '<div class="main layui-clear"><form class="layui-form" id="form_"><div class="layui-form-item"><label class="layui-form-label">资源名称</label><div class="layui-input-block"><input name="name" lay-verify="name" required autocomplete="off" class="layui-input" type="text"></div></div><div class="layui-form-item"><label class="layui-form-label">资源类型</label><div class="layui-input-block"><input name="type" lay-verify="type" required autocomplete="off" class="layui-input" type="text"></div></div><div class="layui-form-item"><label class="layui-form-label">资源url</label><div class="layui-input-block"><input name="url" lay-verify="url" required autocomplete="off" class="layui-input" type="text"></div></div></form></div>'
		      }); 
	}else if("edit" == cmd){
		$.post('/admin/perm/list', {id:id}, function(data){
			url = data.url;
			name = data.name;
			type = data.type;
			parentId = data.parentId;
			code=data.code;
			layer.open({
		        type: 1
		        ,title: '编辑资源' //不显示标题栏
		        ,closeBtn: true
		        ,area: '600px;'
		        ,shade: 0.8
		        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
		        ,btnAlign: 'c'
		        ,btn: ['确定', '取消']
		 		,yes:function(){
		 			var param = $('#form_').serialize();
		 			param+="&id="+id+"&code="+code;
		 			if(parentId != undefined){
		 				param+='&parentId='+parentId;
		 			}
		 			$.ajax({
		 				url : '/admin/perm/edit',
		 				type: 'post',
		 				data: param,
		 				dataType:'json',
		 				success: function(res){
		 	                if(res.status === 0) {
		 	                    layer.alert(res.msg,function(){
		 	                    	layer.closeAll();
		 	                    	window.location.reload();
		 	                    });
		 	                } else {
		 	                    layer.alert(res.msg);
		 	                }
		 	            }, error: function(e){
		 	            	layer.alert('请求异常，请重试');
		 	            }
		 			});
		 		}
		        ,moveType: 1 //拖拽模式，0或者1
		        ,content: '<div class="main layui-clear"><form class="layui-form" id="form_"><div class="layui-form-item"><label class="layui-form-label">资源名称</label><div class="layui-input-block"><input name="name" lay-verify="name" required autocomplete="off" value="'+name+'" class="layui-input" type="text"></div></div><div class="layui-form-item"><label class="layui-form-label">资源类型</label><div class="layui-input-block"><input name="type" lay-verify="type" value="'+type+'" required autocomplete="off" class="layui-input" type="text"></div></div><div class="layui-form-item"><label class="layui-form-label">资源url</label><div class="layui-input-block"><input name="url" lay-verify="url" required value="'+url+'" autocomplete="off" class="layui-input" type="text"></div></div></form></div>'
		      }); 
		},'json');
	}else if("delete" == cmd){
		layer.confirm('确定要删除吗？该资源下的子资源也将被一并删除！！',{icon: 5},function(){
			$.post('/admin/perm/delete',{id:id},function(data){
				layer.alert(data.msg,function(){
					layer.closeAll();
					window.location.reload();
				});
			},'json')
		});
	}
}