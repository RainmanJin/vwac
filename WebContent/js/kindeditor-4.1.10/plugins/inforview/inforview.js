KindEditor.plugin('infoDetails', function(K) {
	var self = this, name = 'infoDetails',
		lang = self.lang(name + '.');
	self.plugin.infoDetailsDialog = function(options) {
		var width = K.undef(options.width, 750),
			height = K.undef(options.height, 470),
			infoid = options.infoid,
			clickFn = options.clickFn;
			
		var html = [
			'<div id="showinfoDetails" class="ke-plugin-filemanager-body"></div>',
			'</div>'

		].join('');
		var dialog = self.createDialog({
			name : name,
			width : width,
			height : height,
			title : "信息详情",
			body : html,
		}),
		div = dialog.div,
		bodyDiv = K('.ke-plugin-filemanager-body', div);
		loadInformationByTreeid(treeid, treeText);
		return dialog;
	};

});

function loadinfoDetailsDialogByid(id){
	$("#showinfoDetails").ajaxUrl({type:"post",url:"sysadmin/informationForEditor/"+id,callback:function(html){
		$("#showinfoDetails").html(html);
		$(this).initUI();
	}});
}


