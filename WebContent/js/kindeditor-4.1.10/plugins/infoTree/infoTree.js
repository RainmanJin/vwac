KindEditor.plugin('infoTree', function(K) {
	var self = this, name = 'infoTree',
		lang = self.lang(name + '.');
	self.plugin.infoTreeDialog = function(options) {
		var width = K.undef(options.width, 750),
			height = K.undef(options.height, 470),
			treeid = options.treeid,
			treeText = options.treeText,
			clickFn = options.clickFn;
			
		var html = [
			'<div id="showinforList" class="ke-plugin-filemanager-body"></div>',
			'</div>'

		].join('');
		var dialog = self.createDialog({
			name : name,
			width : width,
			height : height,
			title : "信息列表",
			body : html,
			yesBtn : {
				name : self.lang('yes'),
				click : function(e) {
					if (dialog.isLoading) {
						return;
					}
					/*$("table .selectInfoName").each(function(i,obj){
						
					}*/
					$("input:checked[type='radio']").each(function(i,obj){
						var inforId = obj.value;
						var inforDname = $(obj).attr("hiddenText");
						$("#showSelectInfo #showSelectNameid").val(inforId);
						$("#showSelectInfo #showSelectName").val(inforDname);
						
						if(inforId == "" || inforId == null){
							alert("请至少选择一条数据");
						}else{
							self.hideDialog();
						}
					 }); 
				}
			},
		}),
		div = dialog.div,
		bodyDiv = K('.ke-plugin-filemanager-body', div);
		loadInformationByTreeid(treeid, treeText);
		return dialog;
	};

});

function loadInformationByTreeid(id,value){
	//$("#ckeditor_plugins_tabs").initUI();
	//var url = $('#ckeditor_plugins_tabs ul li:first a').attr("href");
	//$("#showinforList").load("sysadmin/informationForEditor/1");
	$("#showinforList").ajaxUrl({type:"post",url:"sysadmin/informationForEditor/"+id,callback:function(html){
		$("#showinforList").html(html);
		$(this).initUI();
	}});
}


