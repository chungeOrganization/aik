function template(flag, data){
	var tpl = '';
	if(flag == 'i'){
		tpl += '<div id="'+data.id+'" role="dialog"  class="modal '+data.modalDialogSize+'">\
					<div class="modal-dialog '+data.dialogSize+'" style="'+data.modalDialogTop+'">\
						<div class="modal-content">\
							<div class="modal-header" style="'+data.showHeader+'">\
								<button aria-hidden="true" data-dismiss="modal" class="close" type="button" id="'+data.id+'_close">×</button>\
								<h4 id="myModalLabel" class="modal-title">'+data.title+'</h4>\
							</div>\
							<div class="modal-body">\
								<div class="row">\
									<div class="col-md-12">\
									'+data.content+'\
									</div>\
								</div>\
							</div>\
							<div class="modal-footer" style="'+data.showFooter+'">\
								<button data-dismiss="modal" class="btn btn-default" id="'+data.id+'_cancel" style="'+data.showCancelButton+'" type="button">'+data.cancelButtonText+'</button>\
								<input class="btn btn-primary" type="button" id="'+data.id+'_submit" style="'+data.showSubmitButton+'" value="'+data.submitButtonText+'">\
							</div>\
						</div>\
					</div>\
				</div>';
		/*tpl1 += '<div class="modal" id="'+data.id+'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="top:'+data.top+';left:'+data.left+';width:'+data.width+';bottom:'+data.bottom+';right:'+data.right+';margin-left:'+data.margin_left+'">\
			  <div class="modal-dialog" >\
			    <div class="modal-content" >\
			      <div class="modal-header modal-headerex" style="line-height:10px;padding:10px 15px;'+data.showHeader+'" id="'+data.id+'_header">\
			        <button type="button" style="'+data.showCloseButton+'" class="close" id="'+data.id+'_close"  aria-hidden="true">&times;</button>\
			        <h5 class="modal-title" id="myModalLabel" >'+data.title+'</h5>\
			      </div>\
			      <div class="modal-body modal-bodyex" style="max-height:'+data.max_height+'; overflow-y: auto;">'+data.content+'</div>\
			      <div class="modal-footer modal-footerex" style="padding-top: 10px; padding-bottom: 10px;'+data.showFooter+'" id="'+data.id+'_footer">\
			        <button type="button" class="btn btn-default btn-sm"  style="'+data.showCancelButton+'" id="'+data.id+'_cancel">'+data.cancelButtonText+'</button>\
			        <button type="button" class="btn btn-primary btn-sm" id="'+data.id+'_submit" style="'+data.showSubmitButton+'">'+data.submitButtonText+'</button>\
			      </div>\
			    </div>\
			  </div>\
			</div>';*/
	}else if(flag == 'c'){
		tpl +='<div class="msg_box">\
					<i class="msg_icon'+data.icon+'"></i>\
					<div class="msg_text">'+data.content+'</div>\
				</div>';
	}
	return tpl;
}

var d_id = 0;

/**
* 对话框的类
* @class Dialog
* @constructor
* @param {Object} options 对话框选项
**/
var Dialog = function(options) {
	/**
	* 对话框的id
	* @property id
	* @type String
	* @private
	**/
    this.id = 'dialog_' + (++d_id);

    /**
	* 对话框的dom结构
	* @property dom
	* @type Object
	* @private
	**/
    this.dom = null;

	/**
	* 遮罩
	* @property mask
	* @type Object
	* @private
	**/
    this.mask = null;

	/**
	* 控制对话框关闭的定时器
	* @property timer
	* @type Number
	* @private
	**/
    this.timer = null;
	/**
	* 对话框的选项
	* @property options
	* @type Object
	* @private
	**/
    this.options = $.extend({
        title: '提示',
        toolbar: '',
        footer: '',
        content: '',
        submitButtonText: '确 定',
        cancelButtonText: '取 消',
        dragable: true,
        destroyWhenClosed: true,
        autoClose: false,
        autoCloseDelay: 2000,
        useModal: true,
        closeAfterSubmit: true,
        showCloseButton: true,
        showSubmitButton: true,
        showCancelButton: true,
        showHeader: true,
        showFooter: true,
        showrightbottom: false,
        onCreated: function() {},
        onOpened: function() {},
        onClosed: function() {},
        onDestroyed: function() {},
        onSubmitted: function() {},
        onCanceled: function() {},
        isClocd: false,
        modal: 'modal-ex',
        width: '410px',
        height: 'auto',
        left: '50%',
        right: '',
        bottom:'',
        zIndex: 1001,
        mainStyle: 'popup_msg',
        modalDialogTop : '10%',
        modalDialogSize : 's',
      /*  template: Dialog.template,*/
        autoPosit: true,
        left: '25%',
        top: '20%',
        margin_left: '-180px',
        max_height: '350px',
        animation: null,
		needHide3d: true
    },options || {});
    Dialog.instances[this.id] = this;
}

/**
* 对话框类的原型对象
* @class Dialog_proto
* @static
**/
Dialog.prototype = {
	/**
	* 创建对话框
	* @method create
	**/
    create: function() {
    	var $self = this,
    		tpdata = {};
    	tpdata.id = this.id;
    	tpdata.showHeader = this.options.showHeader ? '' : 'display:none;';
    	tpdata.showFooter = this.options.showFooter ? '' : 'display:none;';
    	tpdata.showCancelButton = this.options.showCancelButton ? '' : 'display:none;';
    	tpdata.showSubmitButton = this.options.showSubmitButton ? '' : 'display:none;';
		tpdata.showCloseButton = this.options.showCloseButton ? '' : 'display:none;';
        tpdata.right= this.options.showrightbottom ? '1px' : '',
        tpdata.bottom= this.options.showrightbottom ? '1px' : '',
        tpdata.left= this.options.showrightbottom ? '' : '50%',
        tpdata.modal= this.options.showrightbottom ? 'modal-ex' : this.options.modal,
        tpdata.useModal = this.options.showrightbottom ? false : this.options.useModal,
		tpdata.footer = this.options.footer ? this.options.footer : '';
        tpdata.content = this.options.content.replace('<%=dialogId%>', this.id);
        tpdata.modalDialogTop = this.options.modalDialogTop == ''? '' : 'margin: '+this.options.modalDialogTop+' auto;';
        switch(this.options.modalDialogSize){
	    	case 'm':
	    		tpdata.modalDialogSize = '',
	    		tpdata.dialogSize = '';
	    		break;
	    	case 'b':
	    		tpdata.modalDialogSize = 'bs-example-modal-lg',
	    		tpdata.dialogSize = 'modal-lg';
	    		break;
	    	case 's':
	    		tpdata.modalDialogSize = 'bs-example-modal-sm',
	    		tpdata.dialogSize = 'modal-sm';
	    		break;
	    	default:
	    		tpdata.modalDialogSize = '',
	    	tpdata.dialogSize = '';
	    		break;
	    }
        tpdata = $.extend(this.options, tpdata);
        if (this.options.useModal) {
            this.mask = new Dialog.Mask({
                zIndex: this.options.zIndex - 1,
                target: this.options.maskTarget
            });
            this.mask.create();
        }
        
        var dialog = template('i', tpdata);
        $(document.body).append(dialog);
        if ($.support.leadingWhitespace && !window.XMLHttpRequest) {
            var divHeight = $('#' + this.id).height();
            $('#' + this.id).find(' > iframe').height(divHeight);
        }
        this.dom = $('#' + this.id);
        $('#' + this.id + '_close').click(function() {
            $self.close();
            return false;
        });
        $('#' + this.id + '_submit').click(function() {
            if ($self.options.onSubmitted.call($self) || $self.options.closeAfterSubmit) {
            	if($self.options.isClocd == false){
            		 $self.close('s');
            	}
            }
            return false;
        });
        $('#' + this.id + '_cancel').click(function() {
            $self.options.onCanceled.call($self);
            $self.close('s');
            return false;
        });            
        if (this.options && this.options.onCreated) {
            this.options.onCreated.call(this);
        }
    },

    /**
	* 打开对话框
	* @method open
	**/
    open: function() {
    	if (this.dom == null) {
        	this.create();
    	}
    	var animation = this.options.animation,
            showrightbottom = this.options.showrightbottom,
            classname = showrightbottom ? '' : 'inex',
    		$self = this;
        switch(animation){
        	case 'fade':
        		$(this.dom).addClass(classname).fadeIn();
        		break;
        	case 'slide':
        		$(this.dom).addClass(classname).slideDown();
        		break;
        	default:
        		$(this.dom).addClass(classname).show();
        		break;
        }
        if (this.mask) {
            this.mask.show();
        }
        if (this.options.autoClose) {
            if (this.timer) {
                clearTimeout(this.timer);
            }
            this.timer = setTimeout(function() {
                $self.close();
                clearTimeout(this.timer);
            },
            this.options.autoCloseDelay);
        }
        if (this.options && this.options.onOpened) {
            this.options.onOpened.call(this);
        }

    },

    /**
	* 关闭对话框
	* @method close
	**/
    close: function(opt) {
        var animation = this.options.animation,
            showrightbottom = this.options.showrightbottom,
            classname = showrightbottom ? '' : 'inex';
        switch(animation){
        	case 'fade':
        		$(this.dom).removeClass(classname).fadeOut();
        		break;
        	case 'slide':
        		$(this.dom).removeClass(classname).slideUp();
        		break;
        	default:
        		$(this.dom).removeClass(classname).hide();
        		break;
        }
        if (this.mask) {
            this.mask.hide();
        }
        if (this.timer) {
            clearTimeout(this.timer);
            this.timer = null;
        }
        if (opt != 's' && this.options && this.options.onClosed) {
            this.options.onClosed.call(this);
        }
        if (this.options && this.options.destroyWhenClosed) {
            var $self = this;
            setTimeout(function() {
                $self.destroy();
            },
            10);
        }
    },

    /**
	* 设置对话框位置
	* @method setPosition
	**/
	setPosition: function(){
		if(!this.options.autoPosit) {
            return;
        }
        var topPos = $(document).scrollTop() || 0,
			win = window;
		if (topPos < 0) {
			topPos = 0;
		}
		//console.log(topPos, $(window).height(), $(this.dom).height());
		$(this.dom).css({
			left: Math.floor(($(window).width() - $(this.dom).width()) / 2) + 'px',
			top: Math.floor(($(window).height() - $(this.dom).height()) / 2) + topPos + 'px'
		});
	},

	/**
	* 取消自动关闭
	* @method cancelAutoClose
	**/
	cancelAutoClose: function(){
		if(this.timer){
			clearTimeout(this.timer);
		}
	},

	/**
	* 销毁对话框的dom
	* @method destroy
	**/
    destroy: function() {
        try {
            $(this.dom).find('iframe').remove();
            $(this.dom).remove();
            if (this.mask) {
                this.mask.destroy();
            }
            this.options.onDestroyed.call(this);
            this.dom = null;
            this.options = null;
            Dialog.instances[this.id] = null;
            delete Dialog.instances[this.id];
        } catch(_) {}
    }
};
Dialog.template = '<div class="<%=modal%>" id="<%=id%>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="top:<%=top%>;left:<%=left%>;width:<%=width%>;bottom:<%=bottom%>;right:<%=right%>;margin-left:<%=margin_left%>">\
			  <div class="modal-dialog" >\
			    <div class="modal-content" >\
			      <div class="modal-header modal-headerex" style="height:20px;<%=showHeader%>" id="<%=id%>_header">\
			        <button type="button" style="<%=showCloseButton%>" class="close" id="<%=id%>_close"  aria-hidden="true">&times;</button>\
			        <h5 class="modal-title" id="myModalLabel" style="font-size: 14px;"><%=title%></h5>\
			      </div>\
			      <div class="modal-body modal-bodyex" style="max-height:<%=max_height%>">\
			        <%=content%>\
			      </div>\
			      <div class="modal-footer modal-footerex" style="<%=showFooter%>" id="<%=id%>_footer">\
			        <button type="button" class="btn btn-default"  style="<%=showCancelButton%>" id="<%=id%>_cancel"><%=cancelButtonText%></button>\
			        <button type="button" class="btn btn-info" id="<%=id%>_submit" style="<%=showSubmitButton%>"><%=submitButtonText%></button>\
			      </div>\
			    </div>\
			  </div>\
			</div>';
Dialog.tem = '<div id="<%=id%>" style="position: absolute; visibility: visible; display: block; z-index: 9999;  width:<%=width%>; height:<%=height%>; ">\
					    <div class="mgp_popup <%=mainStyle%>" style="width:<%=width%>;height:<%=height%>">\
					    	<div class="popup_hd" style="<%=showHeader%>" id="<%=id%>_header">\
					    		<div class="popup_title">\
					    			<i class="popup_icon"></i>\
					    			<h2><%=title%></h2>\
					    		</div>\
					    		<span class="popup_close" style="<%=showCloseButton%>">\
					    			<a href="#" rel="close" title="关闭" id="<%=id%>_close" onclick="return false;">关闭</a>\
					    		</span>\
					    	</div>\
					    	<div class="popup_bd">\
					    		<%=content%>\
					    		<div class="popup_op" style="<%=showFooter%>" id="<%=id%>_footer">\
					    			<%=footer%>\
					    			<a href="#" class="popup_btn" rel="sure" style="<%=showSubmitButton%>" onclick="return false;"><span id="<%=id%>_submit"><%=submitButtonText%></span></a><a href="#" rel="cancel" class="popup_btn" style="<%=showCancelButton%>" onclick="return false;"><span id="<%=id%>_cancel"><%=cancelButtonText%></span></a>\
					    		</div>\
					    	</div>\
					    </div>\
					  </div>';

Dialog.contentTemplate = '<div class="msg_box">\
			    			<i class="msg_icon <%=icon%>"></i>\
			    			<div class="msg_text"><%=content%></div>\
			    		</div>';

Dialog.contentIcons = {
	'warning': 'i_war',
	'success': 'i_suc',
	'error': 'i_err',
	'confirm': 'i_que',
	'tips': 'i_tip'
};

Dialog.instances = {};

Dialog.confirmText = function(options){
	var options = $.extend({modalDialogSize : 'm'}, options);
	options.content = template('c', {icon: Dialog.contentIcons.confirm, content: options.content || ''});
	var dialog = new Dialog(options);
	dialog.open();
	return dialog;
};

Dialog.confirm = function(options){
	var options = $.extend({modalDialogSize : 's'}, options);
	
	options.content = template('c', {icon: Dialog.contentIcons.confirm, content: options.content || ''});
	var dialog = new Dialog(options);
	dialog.open();
	return dialog;
};

/**
* Dialog类的静态函数，用于弹出通知对话框
* @method notice
**/
Dialog.notice = function(options){
	var options = $.extend({
		type: 'warning',
		modalDialogSize : 's',
		showCancelButton: false
	}, options);
	options.content = template('c', {icon: Dialog.contentIcons[options.type], content: options.content || ''});
	 
	var dialog = new Dialog(options);
	dialog.open();
	return dialog;
}

/**
* Dialog类的静态函数，用于关闭对话框
* @method close
**/
Dialog.close = function(id) {
    if (id) {
        Dialog.instances[id].close();
    } else {
        for (var ins in Dialog.instances) {
            Dialog.instances[ins].close();
        }
    }
}

/**
* Dialog类的静态函数，用于销毁对话框
* @method destroy 
**/
Dialog.destroy = function() {
    for (var ins in Dialog.instances) {
        try {
            Dialog.instances[ins].destroy();
        } catch(ex) {}
    }
    Dialog.instances = null;
}

    /**
* 遮罩的类
* @class Dialog.Mask
* @constructor
**/
/*Dialog.Mask = function(options) {
    this.options = $.extend({
        target: document.body,
        zIndex: 990,
        opacity: 0.5
    },
    options || {});
    this.dom = null;
}*/

/**
* 遮罩的类
* @class Dialog.Mask
* @constructor
**/
Dialog.Mask = function(options) {
    this.options = $.extend({
        target: document.body,
        zIndex: 990,
        opacity: 0.4
    },
    options || {});
    this.dom = null;
}

/**
* 遮罩类的原型对象
* @class Dialog.Mask.prototype
* @static
**/
Dialog.Mask.prototype = {

    /**
    * 创建遮罩
    * @method create
    **/
    create: function() {
        var width = $(this.options.target).get(0).scrollWidth,
            height = Math.max(document.documentElement.scrollHeight, document.body.scrollHeight),
            zIndex = this.options.zIndex,
            opacity = this.options.opacity;
        this.dom = document.createElement('div');
        document.body.appendChild(this.dom);
        $(this.dom).css({
            'position': 'absolute',
            'zIndex': zIndex,
            //'left': offset.left + 'px',
            //'top': offset.top + 'px',
            'left': 0,
            'top': 0,
            'width': width + 'px',
            'height': height + 'px',
            'display': 'none'
        });
        
        $(this.dom).append('<div style="position:absolute; left:0; top:0; width:' + width + 'px;height:' + height + 'px; background:#000000;z-index:' + zIndex + ';opacity:' + opacity + '; filter:alpha(opacity=' + (opacity * 100) + ');-moz-opacity:' + opacity + ';"></div>');
        if(this.options.showimg == true){
            $(this.dom).append('<div style="position:fixed;left: 50%; margin-left: -100px;top: 39%;z-index:1001;width: 250px;color:#FFFFFF"><img th:src="@{/static/assets/img/loading.gif}" >正在提交数据...</div>');
        }
        //if ($.browser.msie) {
          //  $(this.dom).append('<iframe style="opacity:0; filter:alpha(opacity=0);-moz-opacity:0;" scrolling="No" style="" border="0" frameborder="0" width="' + width + '" height="' + height + '"></iframe>');
       // } else if ($.browser.opera) {
         //   $(this.dom).append('<img onmousedown="return false;" galleryimg="no" style="z-index:' + zIndex + '" width="' + width + '" height="' + height + '"/>');
        //}
    },

    /**
    * 显示遮罩
    * @method show
    **/
    show: function() {
       if (!this.dom) {
            this.create();
        }
        $(this.dom).show();
    },

    /**
    * 窗口改变大小的事件处理函数
    * @method onWindowResized
    **/
    onWindowResized: function(){
        var width = $(this.options.target).get(0).scrollWidth,
            height = Math.max(document.documentElement.scrollHeight, document.body.scrollHeight);
        $(this.dom).css({
            'width': width + 'px',
            'height': height + 'px'
        });
        $(this.dom).find('div').css({
            'width': width + 'px',
            'height': height + 'px'
        });
        $(this.dom).find('iframe').css({
            'width': width + 'px',
            'height': height + 'px'
        });
        $(this.dom).find('img').css({
            'width': width + 'px',
            'height': height + 'px'
        });
    },

    /**
    * 隐藏遮罩
    * @method hide
    **/
    hide: function() {
        $(this.dom).hide();
    },

    /**
    * 销毁遮罩dom结构
    * @method destroy
    **/
    destroy: function() {
        $(this.dom).remove();
        this.dom = null;
        this.options = null;
    }
}

UiUtils = {
	initUI: function(parent){
		this.initSelectorUI($(".filterselector", window));
	},
	initSelectorUI : function(el){
		if(!el || el.length < 1) return;

		var multiples, width, action, $n, str, codeType;

		el.each(function(i, n){
			$n = $(n),
			codeType = $n.attr("data-codeType"),
			multiples = $n.attr("data-multiples"), 
			action = $n.attr("data-action");

			var v = '';
			if(!multiples)
				v += '<option value="">请选择</option>';
			if(multiples == "1")
				v += '<option value="">所有省</option>';
			if(multiples == "2")
				v += '<option value="">单位</option>';
			if(multiples == "3")
				v += '<option value="" >无</option>';
			if(action !== ""){
				$.ajax({
					url: action,
					data: {'codeType' : codeType},
					type: "post",
		            success: function(data){
		            	if(data.code == '0'){
		            		$.each(data.data, function(i, n){
		            			v += '<option value="'+i+'">'+n+'</option>';
		            		})
		            		$('#province').html(v);
		            	}
		            }
				 });
			}
		});
	},
	/**
	 * 页面非空判断
	 * @param id
	 * @returns {Boolean}
	 */
	check : function(id){
		var b = true;
		$('#'+id).find('.r').each(function(){
			if($(this).val() == "" ){
				b = false;
				return false;
			}
		});
		if(b && $('#'+id).find('.error').length > 0){
			b = false;
		}
		return b;
		
	}
}