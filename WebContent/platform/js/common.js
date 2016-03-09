function loading()
{
	loadingHtml("Loadding ... ...");
}

/**
 * 替换target中的内容为等待视图
 * 
 * @param {}
 *            target
 */
function loadingHtml(html)
{
	$("#loading").css("top", "0px");
	$("#loading").css("left", "0px");
	$("#loading").height($(document.body).height());
	$("#loading").width($(document.body).width());
	$("#loading .text").html(html);
	$("#loading .text").css("margin-top",
			($("#loading").height() - 100) / 2 + "px")
	$("#loading").show();
}

function loadEnd()
{
	$("#loading").hide();
}

/**
 * focus效果设定
 * 
 * @param {}
 *            f 表单选择器或表单
 */
function initForm(f)
{
	$(f).find("input, textarea").focus(function()
			{
				$(this).addClass("focus");
			}).blur(function()
			{
				$(this).removeClass("focus");
				validEl(this);
			});/*.each(function(){
				var v = $(this).attr("v");
				if (v)
				{
					var vs = v.split(" ");
					for (var i = 0; i < vs.length; i++)
					{
						switch (vs[i])
						{
							case "num" :
								$(this).keyup(function(){
									$(this).val($(this).val().replace(/[^\d]/g,''));
								})
								break;
							default :
								break;
						}
					}
				}
			});*/
}

/**
 * 验证
 * 
 * @param {}
 *            f 表单选择器或表单
 */
function validFrom(f)
{
	var passed = true;
	$(f).find("input, textarea").each(function()
			{
				if (!validEl(this))
				{// 验证未通过
					passed = false;
				}
			});
	return passed;
}

/**
 * input,textarea控件是否验证通过
 * 
 * @param {}
 *            el
 */
function validEl(el)
{
	var v = $(el).attr("v");
	var passed = true;
	if (v)
	{
		var vs = v.split(" ");
		for (var i = 0; i < vs.length; i++)
		{
			switch (vs[i])
			{
				case "notEmpty" :
					if ($(el).val() == "")
						passed = false;
					break;
				case "num" :
					if (/\D/.test($(el).val()))
						passed = false;
					break;
				case "dob" :
				 var re=/^\d+(?=\.{0,1}\d+$|$)/;   
     			   if(!re.test($(el).val()))
     			   		passed= false;  
     			   	break; 
				default :
					break;
			}
			if (!passed)
			{// 验证未通过
				$(el).parent().addClass("error");
				break;
			} else
			{
				$(el).parent().removeClass("error");
			}

		}
	}
	return passed;
}

/**
 * ajax json 表单提交, 自动表单校验等, response必须为AjaxResponse
 * 
 * @param f
 * 		表单
 * @param succ
 * 		成功时的回调函数
 * @param err
 * 		失败时的回调函数
 */
function initJsonForm(f, succ, err)
{
	initForm(f);
	var options =
	{
		beforeSubmit : function(formData, jqForm, options)
		{
			if (validFrom(jqForm))
			{
				loading();
				return true;
			}
			return false;
		}, // pre-submit callback
		success : function(data)
		{
			loadEnd();
			if (data.success && succ)
			{
				succ(data);
			} else if (err)
			{
				err(data);
			}
		}, // post-submit callback
		type: 'post',
		dataType : 'json',
		timeout: 3000000
		// other available options:
		// url: url // override for form's 'action' attribute
		// dataType: null // 'xml', 'script', or 'json' (expected server
		// response type)
		// clearForm: true // clear all form fields after successful submit
		// resetForm: true // reset the form after successful submit
		// $.ajax options can be used here too, for example:
	};
	// bind form using 'ajaxForm'
	$(f).ajaxForm(options);
}

/**
 * json提交，需要页面有div.response
 * 
 * @param {}
 *            f
 * @param {}
 *            target
 */
function initMsgForm(f)
{
	initForm(f);
	var options =
	{
		beforeSubmit : function(formData, jqForm, options)
		{
			if (validFrom(jqForm))
			{
				loading();
				var m = $(jqForm).find(".response");
				m.removeClass("failure");
				m.removeClass("succ");
				return true;
			}
			return false;
		}, // pre-submit callback
		success : function(data)
		{
			loadEnd();
			var m = $(f).find(".response");
			if (data.success)
			{
				m.removeClass("failure");
				m.addClass("succ");
			} else
			{
				m.removeClass("succ");
				m.addClass("failure");
			}
			m.html(data.data);
		}, // post-submit callback
		type: 'post',
		dataType : 'json'
		// other available options:
		// url: url // override for form's 'action' attribute
		// type: type // 'get' or 'post', override for form's 'method' attribute
		// dataType: null // 'xml', 'script', or 'json' (expected server
		// response type)
		// clearForm: true // clear all form fields after successful submit
		// resetForm: true // reset the form after successful submit
		// $.ajax options can be used here too, for example:
		// timeout: 3000
	};
	// bind form using 'ajaxForm'
	$(f).ajaxForm(options);
}

/**
 * html 提交，局部刷新
 * 
 * @param {}
 *            f
 * @param {}
 *            target
 */
function initHtmlForm(f, target,callback)
{
	initForm(f);
	var options =
	{
		beforeSubmit : function(formData, jqForm, options)
		{
			if (validFrom(jqForm))
			{
				loading();
				return true;
			}
			return false;
		}, // pre-submit callback
		success : function(data)
		{
			loadEnd();
			if (callback)
			{
				callback(data);
			}
		}, // post-submit callback
		type: 'post',
		target: target
		// other available options:
		// url: url // override for form's 'action' attribute
		// type: type // 'get' or 'post', override for form's 'method' attribute
		// dataType: null // 'xml', 'script', or 'json' (expected server
		// response type)
		// clearForm: true // clear all form fields after successful submit
		// resetForm: true // reset the form after successful submit
		// $.ajax options can be used here too, for example:
		// timeout: 3000
	};
	// bind form using 'ajaxForm'
	$(f).ajaxForm(options);
}


/**
 * 加载某个url,get方式提交,自动显示记载图标
 * 
 * @param url
 * @param data
 * 			json格式的参数列表，例如: { name: "John", time: "2pm" }
 * @param callback
 * @param type
 * 			返回内容格式，xml, html, script, json, text, _default
 */
function ajaxGet(url,data,callback,type)
{
	loading();
	
	$.get(url,data,function(data){
		loadEnd();
		callback(data);
	},type);
}

/**
 * 加载某个url,post方式提交,自动显示记载图标
 * 
 * @param url
 * @param data
 * 			json格式的参数列表，例如: { name: "John", time: "2pm" }
 * @param callback
 * @param type
 * 			返回内容格式，xml, html, script, json, text, _default
 */
function ajaxPost(url,data,callback,type)
{
	loading();
	
	$.post(url,data,function(data){
		loadEnd();
		callback(data);
	},type);
}

/**
 * ajax加载某个url的内容到指定id中
 * 
 * @param url
 * @param id
 * @param param
 */
function ajaxLoad(url, id, param)
{
	loading();
	if(param)
	{
		$(id).load(url,param, function(data){
			loadEnd();
		});
	}else
	{
		$(id).load(url,function(data){
			loadEnd();
		});
	}
}

/**
 * 获取一组同名的checkbox的选中状态的值，逗号分割
 * @param name
 */
function checkedByName(name)
{
	var items = document.getElementsByName(name);
	
	var re = [];
	for(i = 0; i < items.length; i++)
	{
		if(items.item(i).checked)
		{
			re.push(items.item(i).value);
		}
	}
	
	if(re.length>0)
	{
		return re.join(",");
	}else
	{
		return "";
	}

}

function goUrl(url) {
    if (url != null && url != "") {
        location.href = url;
        return;
    }
    history.back();
}

function isStrictMode() {
    return document.compatMode != "BackCompat";
}
function getWidth() {
    return isStrictMode() ? Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth) : Math.max(document.body.scrollWidth, document.body.clientWidth);
}
function getHeight() {
    return isStrictMode() ? Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight) : Math.max(document.body.scrollHeight, document.body.clientHeight);
}

/*tr经过和移开样式*/
function mover(src) {
    src.bgColor = "#FFFF99";
}
function mout(src) {
    src.bgColor = "#ffffff";
}

/*文本框经过和移开样式*/
function ShowOver(obj, style) {
    obj.className = style;
}
function ShowOut(obj, style) {
    obj.className = style;
}

/*输入框必须填写数字*/
function isNum() {
    if (event.keyCode < 45 || event.keyCode > 57) {
        event.keyCode = 0;
    }
}

//获取参数
function getQueryString(queryName) {
    var reg = new RegExp("(^|&)" + queryName + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return
    null;
}
function funbtnClick(obj) {
    //$(obj).after("<img src='/images/loading.gif'/>");
    //$(obj).hide();
}
/*列表复选框*/
function checkAll(form) {
    var chkall = document.getElementById("cheList");

    var chkother = document.getElementById("tableData").getElementsByTagName("input");
    for (var i = 0; i < chkother.length; i++) {
        if (chkother[i].type == 'checkbox') {
            if (chkall.checked == true) {
                chkother[i].checked = true;
            }
            else {
                chkother[i].checked = false;
            }
        }
    }
}
//弹出框
function openDlg(id,title,url,w,h) {
	  $.layer({
		  type: 2,
		  border: [0],
		  title: title,
		  shadeClose: true,
		  iframe: {src : url},
		  area: [w, h]
	  });
	 
  }
