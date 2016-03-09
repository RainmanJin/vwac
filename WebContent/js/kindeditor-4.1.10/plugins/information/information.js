/*
KindEditor.plugin('information', function(K) {
        var editor = this, name = 'information';
        // 点击图标时执行
        editor.clickToolbar(name, function() {
                //editor.insertHtml('你好');
        	selectBrno(this);
        });
});
*/
var tabs;
var ctxpath;
var self;

KindEditor.plugin('information', function(K) {
	self = this, name = 'information',path = "path",
	allowFileManager = K.undef(self.allowFileManager, false),
		lang = self.lang(name + '.');
	ctxpath = self.lang('path');
	self.plugin.inforDialog = function(options) {
		var showRemote = K.undef(options.showRemote, true),
			showLocal = K.undef(options.showLocal, true),
			clickFn = options.clickFn;
		var html = [
            '<div id="ckeditor_plugins_tabs" class="pageContent">'+
            	'<div id="showSelectInfo"><br/>&nbsp;&nbsp;<input id="showSelectNameid" type="hidden">所选信息：<input id="showSelectName" type="text"><br/><br/></div>'+
	        	'<div class="tabs" currentIndex="0" eventType="click">'+
		        	'<div class="tabsHeader">'+
		        		'<div class="tabsHeaderContent">'+
			        		'<ul>'+
				        		'<li><a href="sysadmin/informationGroupForEditor" class="j-ajax"><span>综合信息分组</span></a></li>'+
								'<li><a href="sysadmin/editor_infogTree" class="j-ajax"><span>综合信息详情</span></a></li>'+
							'</ul>'+
		        		'</div>'+
		        	'</div>'+
		        	'<div class="tabsContent" style="height:400px;background:#ffffff;">'+
		        		'<div id="informationGroupForEditor"></div>'+
						'<div></div>'+
		        	'</div>'+
	            '</div>'+
            '</div>'
		].join('');
		var dialogWidth = showLocal || allowFileManager ? 650 : 400,
			dialogHeight = showLocal && showRemote ? 500 : 250;
		var dialog = self.createDialog({
			id:'informationid',
			name : name,
			width : dialogWidth,
			height : dialogHeight,
			title : self.lang(name),
			body : html,
			yesBtn : {
				name : self.lang('yes'),
				click : function(e) {
					if (dialog.isLoading) {
						return;
					}
					clickFn.call(self);
				}
			}
		}),
		div = dialog.div;
		var defauleinfo = self.cmd.selection().sel;
		var info_a_a = self.plugin.getSelectedLink();
		if (info_a_a) {
			defauleinfo = info_a_a.text();
			if(info_a_a.attr("infotype") == "group"){
				//K('#showgroupid', div).val(info_a_a.attr("id"));
				//K('#tab1infoid', div).show();
			}else if(info_a_a.attr("infotype") == "detail"){
				//K('#showinforid', div).val(info_a_a.attr("id"));
				//K('#tab2infoid', div).show();
			}
			
		}
		refreshCkeditorTabs();
		return dialog;
	};
	self.plugin.information = {
		edit : function() {
			var infoDialog = self.plugin.inforDialog.div;
			var infoID = "";
			var infoValue = "";
			var objtext = self.cmd.selection();
			var seltext = objtext.sel;
			var info_a_b = self.plugin.getSelectedLink();
			self.plugin.inforDialog({
				clickFn : function(url, title, width, height, border, align) {
					var selectedIndex=-1;
					K(".tabsHeaderContent ul li", infoDialog).each(function(index,element){
						if($(element).hasClass("selected")){
							selectedIndex=index;
						}
					});
					var infogroupid = K('#showSelectInfo #showSelectNameid', infoDialog).val();
					var infogroupvalue = K('#showSelectInfo #showSelectName', infoDialog).val();
					if(infogroupid=='' || infogroupvalue=='' ){
						alert("请选择一条信息");
						//alertMsg.warn('请选择一条分组信息！');
						return;
					}
					if(selectedIndex == 0){
						self.insertHtml('<a href="javascript:adv.gotoGroup('+infogroupid+')" id='+infogroupid+' infotype="group">'+infogroupvalue+'</a>');
					}else if(selectedIndex == 1){
						self.insertHtml('<a href="javascript:adv.gotoInfo('+infogroupid+')" infotype="detail" id='+infogroupid+'>'+infogroupvalue+'</a>');
					}
					setTimeout(function() {
						self.hideDialog().focus();
					}, 0);
				}
			});
			
		}
	};
	self.clickToolbar(name, self.plugin.information.edit);
});

function refreshCkeditorTabs(){
	$("#ckeditor_plugins_tabs").initUI();
	var url = $('#ckeditor_plugins_tabs ul li:first a').attr("href");
	$("#informationGroupForEditor").ajaxUrl({type:"get",url:url,callback:function(){
		$(this).initUI();
	}});
}
function refreshKinderInfoTabs(url){
	$(this).ajaxUrl({type:"get",url:url,callback:function(json){
		$("#index1").html(json);
		$("#index1").initUI();
	}});
}
function loadContent(target,url,gotoWindowEnd) {
	
	$.get(url, function(data) {
		if (typeof(target) =="string") {
			target=$("#" + target);
		}
		target.empty().html(data);
		if (gotoWindowEnd!=null && $.isFunction(gotoWindowEnd)) {
			gotoWindowEnd();
		}

	}, 'html');
}

function showDetails(){
	//$("#infolistid").val(id);
	self.loadPlugin('infordetails', function() {
		self.plugin.infordetailsDialog({});
	});
}

function showInfoSelectTree(id,value){
	//alert(1);
	self.loadPlugin('infoTree', function() {
		self.plugin.infoTreeDialog({treeid:id,treeText:value});
	});
}

function gotoList(id,name) {
	showDetails(id);
}