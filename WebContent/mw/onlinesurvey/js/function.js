//var cookie="cookie1";
$(function(){
	$("#btnpre").on("click",function(){prePage()});
    $("#btnnext").on("click",function(){nextPage()});
	$("#btnsecond").on("click",function(){
		$("#first").hide();
		$("#second").show();
	});
    if($.cookie(cookie))
	{
		$("#btnsecond").addClass("diswork");
		$("#btnsecond").html("您已经投票了<br />You have voted already");
		$("#btnsecond").unbind("click");
	}
});
//扩展 检查某个元素在数组中的索引值
Array.prototype.indexOf = function(el){
  for (var i=0,n=this.length; i<n; i++){
   if (this[i] == el){
    return i;
   }
  }
  return -1;
}

function showpage(n,type)
{
	/*$('.survey div').each(function(index){
		$(this).hide();
	});*/
	$('.survey div').hide();
	//alert(type+"--"+currentpage);
	//当前问答DIV
	if(type==0){
		var tmp= $.cookie('p_'+n); 
		//alert(n+"--"+tmp);
		setval(n,tmp);
		$('.survey div').eq(n).show();
	}
	else
	{
	  getval(n);
	}
}
function getval(n)
{
    var obj = $('.survey div').eq(n - 1);
    var _input = obj.find("input,textarea");
    if (_input) {
        var type = _input.attr("type");
	    var check = _input.attr("check");
        if (type == "radio") {
			var sVal = obj.find("input[type=radio]:checked").val();
			//alert(check+"_"+sVal);
			if (check) {
				var sReg = check;
				var sTemp = sVal != undefined ? "0" : "";
				var reg = new RegExp(sReg, "i");
				if (!reg.test(sTemp)) {
					lhgdialog.alert(_input.attr("warning"));
					obj.show();
					currentpage--;
					return false;
				}
			}
			$.cookie('p_' + (n - 1), sVal);
			if ($('.survey div').eq(n).find("input[type=radio]")) {
				var tmp = $.cookie('p_' + n);
				if (tmp) $('.survey div').eq(n).find("input[value='+tmp+']").attr('checked', 'checked');  
			}
			$('.survey div').eq(n).show();
			return true;
        }else if (type == "checkbox") {
			var sCheck=obj.find("input[type=checkbox]");
			var sVal="";
			var sTemp="";
			for(i=0;i<sCheck.length;i++){  
			   if(sCheck[i].checked) {
				   sVal+=","+sCheck[i].value; 
				   sTemp+="0"; 
			   }
			}
			//0{1,}
			//alert(check+"_"+sVal+"_"+sTemp);
			if (check) {
				var sReg = check;
				var reg = new RegExp(sReg, "i");
				if (!reg.test(sTemp)) {
					lhgdialog.alert(_input.attr("warning"));
					obj.show();
					currentpage--;
					return false;
				}
			}
			$.cookie('p_' + (n - 1), sVal);
			
			if ($('.survey div').eq(n).find("input[type=checkbox]")) {
				var tmp = $.cookie('p_' + n);
				if (tmp){
					var sarr=tmp.split(',');
					var sNcheck=$('.survey div').eq(n).find("input[type=checkbox]");
					for(i=0;i<sNcheck.length;i++){ 
					   if(sarr.indexOf(sNcheck[i].value)>-1) sNcheck[i].checked=true;
					}
				}
			}
			$('.survey div').eq(n).show();
			return true;
        }
        else {
            var sVal = _input.attr("value");
            if (check) {
                var sReg = check;
                var reg = new RegExp(sReg, "i");
                if (sVal == "" || !reg.test(sVal)) {
                    lhgdialog.alert(_input.attr("warning"));
                    obj.show();
                    currentpage--;
                    return false;
                }
            }
            $.cookie('p_' + (n - 1), sVal);
            if ($('.survey div').eq(n).find("textarea")) {
                var tmp = $.cookie('p_' + n);
                if (tmp) $('.survey div').eq(n).find("textarea").attr('value', tmp);
            }
            $('.survey div').eq(n).show();
            return true;
        }
        
    }
}
function setval(n,val)
{
	var obj=$('.survey div').eq(n);
	var _input=obj.find("input");
	//console.log($(_input).html(),_input.attr("type"));
	if(_input!=undefined)
	{
		var type = _input.attr("type");
		if(type=="radio"){
		  obj.find("input[value='+val+']").attr('checked','checked');
		}else if (type == "checkbox") {
			var sarr=val.split(',');
			for(i=0;i<_input.length;i++){ 
				if(sarr.indexOf(_input[i].value)>-1) _input[i].checked=true;
			}
		}
		return;
	}
	_input=obj.find("textarea");
	if(_input!=undefined)
	{
		obj.find("textarea").attr('value',val);
	}
}
//上一页
function prePage()
{
	var n=parseInt(currentpage);
	if(n>0){
	   setBtn("btnpre","",0);	
	   currentpage--;
	   showpage(n-1,0);
	   saveProgress();
	}
	else
	{
		//首页
		window.location.href=homeurl;
		setBtn("btnpre","",1);
	}
}
//下一页
function nextPage()
{
	var len=$('.survey div').length;
	var n=parseInt(currentpage)+1;
	if(n>len-1)
	{
		if($.cookie(cookie))
		{
			window.location.href=homeurl;
			return;
		}
		$(".loading").show();
		$.cookie(cookie,"1");
		//数据保存
		//alert(path+"/vote/GetResult.aspx?t="+ (new Date()).getTime());
		//$("#VWVoteForm").attr("action",path+"/vote/GetResult.aspx?t="+ (new Date()).getTime());
		$("#VWVoteForm").submit();
		setBtn("btnnext","",1);
	}
	else{
	  setBtn("btnnext","",0);
	  currentpage++;
	  showpage(n,1);
	  saveProgress();
	}
}

function setBtn(id,css,status)
{     
	$('#'+id).toggleClass(css);
}
//完成进度
function saveProgress()
{
	//$("#showproess").html((currentpage+1)+"/"+totalpage);
}