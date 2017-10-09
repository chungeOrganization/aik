Ajax = window.Ajax || {
	routes: {},
	request: function(opt, ele, onclick, context){
		
		if (!opt || !opt.url) return;			
		var key = "_" + encodeURIComponent(opt.url).replace(/[\/\.%!?&=]/g, ""), 
			route = Ajax.routes[key];
		if (route && opt.url === route.url && route.request) return;
		Ajax.routes[key] = {url: opt.url, request: true};
		if(opt.url.indexOf('?') > -1){
			opt.url = opt.url + '&_t=' + new Date().getTime();
        }else{
        	opt.url = opt.url + '?_t=' + new Date().getTime();
        }			
						
		$.ajax({
			url: opt.url,
			cache: opt.cache === undefined ? false : opt.cache === true,
			type: opt.type || "get",
			data: opt.data || {},
			dataType: "json",
			timeout: opt.timeout || 60000,
			async: opt.async === undefined ? true : opt.async === true,
			global: opt.global === undefined ? false : opt.global === true,
			beforeSend: function(b) {
				$.isFunction(opt.beforeSend) && opt.beforeSend.call(this, b),				
				//加入后台约定请求头，方便后台处理相关逻辑
				b.setRequestHeader("X-XMLHttpRequest", "AJAX");
			},
			success: function(b) {
				if(b.code === "0"){
					$.isFunction(opt.success) && opt.success.call(this, b.data || b.rows);
				}else if(b.code === "-1"){ //未知系统错误
				}else if(b.code === "1004"){ //没有操作权限
				}else if(b.code === "1005"){ //系统超时
				}else{ //其它异常
					if(b.data){
						Dialog.notice({
							content : b.data.info || '数据异常，请刷新后重试'
						});	
					}else{
						Dialog.notice({
							content : '数据异常，请刷新后重试'
						});	
					};
				}								
			},
			error: function(b, c, d) {
				if($.isFunction(opt.error)){
					opt.error.call(this, b, c, d);	
				}else{
					Dialog.notice({
						content : '数据异常，请刷新后重试'
					});
				}
			},
			complete: function(b, c) {
				
				Ajax.routes && delete Ajax.routes[key];
				$.isFunction(opt.complete) && opt.complete.call(this, b);
			}
		});				
	},
	
	requestSubmit: function(options){
		if (!options || !options.url) return;			
		var key = "_" + encodeURIComponent(options.url).replace(/[\/\.%!?&=]/g, ""), 
			route = Ajax.routes[key],
			mask;
		if (route && options.url === route.url && route.request) return;
		Ajax.routes[key] = {url: options.url, request: true};
		if(options.url.indexOf('?') > -1){
			options.url = options.url + '&_t=' + new Date().getTime();
        }else{
        	options.url = options.url + '?_t=' + new Date().getTime();
        }			
		options.fm.ajaxSubmit( {
            url: options.url,
            type : options.type || "get",
            dataType : "json",
            resetForm : true,
            timeout: options.timeout || 60000,
            success : function(b) {
            	if(b.code === "0"){
					$.isFunction(options.success) && options.success.call(this, b.data || b.rows || b);
				}else if(b.code === "-1"){ //未知系统错误
				}else if(b.code === "1004"){ //没有操作权限
				}else if(b.code === "1005"){ //系统超时
				}else{ //其它异常
					if(b.data){
						Dialog.notice({
							content : b.data.info || '数据异常，请刷新后重试'
						});	
					}else{
						Dialog.notice({
							content : '数据异常，请刷新后重试'
						});	
					}
					
				}	
            },
            beforeSend: function(b) {
            	mask = new Dialog.Mask({
                    zIndex: 1000,
                    showimg: true
                });
                mask.create();
                mask.show();
                $.isFunction(options.beforeSend) && options.beforeSend.call(this, b);
            },
            error: function(b, c, d) {
				if($.isFunction(options.error)){
					options.error.call(this, b, c, d);	
				}else{
					Dialog.notice({
						content : '数据异常，请刷新后重试'
					});
				}
			},
			complete: function(b, c) {
				Ajax.routes && delete Ajax.routes[key];
				$.isFunction(options.complete) && options.complete.call(this, b);
				mask && mask.destroy();
			}
      	});	
	},

	load: function(options){
		if (!options.url) return;

		//不指定target时，默认显示在inbox-content中
		var op = {},
			target = options.target ? $(options.target) : $("#inbox-content");
			op.url = options.url,
			$load = $("#loadex");
		if($load && $load.length > 0){
			$load.show();
		}
		op.data = options.data ? options.data : options.data;
		//避免打开的不是.action时，可能产生缓存的问题
		if(op.url.indexOf(".htm") !== -1 || op.url.indexOf(".html") !== -1 || op.url.indexOf(".jsp") !== -1){
			if(op.url.indexOf("?") === -1){
				op.url += "?_t=" + new Date().getTime();
			}else{
				op.url += "&_t=" + new Date().getTime();
			}
		}
		if(options.iTab){
			var id = options.tab ? options.tab : '2',
				title = options.tabTitle ? options.tabTitle : '内容',
				tab = '<div class="tab-pane active" id="tab_'+id+'"></div>',
				li = '<li id="tabli_'+id+'" class="active"><a href="#tab_'+id+'" data-toggle="tab">'+title+'</a></li>',
				target = $("#tab_" + id);
			$("#nav-tabs").find("li").removeClass("active");
			$("#tab-content").find(".tab-pane").removeClass("active");
			if(!target || target.length <=0){
				$("#tab-content").append(tab);
				$("#nav-tabs").append(li);
				target = $("#tab_" + id);
			}else{
				target.addClass("active");
				$('#tabli_'+id).addClass("active").find("a").text(title);
			}
			
		}

		target.load(op.url, op.data, function(){
			$load.hide();
			UiUtils.initUI();
			$.isFunction(options.success) && options.success.call(this);
		});
	}
}