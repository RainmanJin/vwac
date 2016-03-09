function g_auto_ajax(element)
{	
	var target = element.attr("t");
	var url = element.attr("u");
	var func = element.attr("f");
	
	if (func != undefined)
	{
		eval(func);
	} else if (target != undefined && url != undefined)
	{
		if (url.indexOf("?") != -1)
		{
			url += "&r_=" + Math.random();
		} else
		{
			url += "?r_=" + Math.random();
		}
		
		loading(target);
		
		$(target).load(url,loadEnd);
	} else
	{
		//do nth
		alert("BUG？彩蛋蛋");
	}
}
$(document).ready(function(){
	_init_body("body");
});

function _init_body(parent)
{
	$(parent).find(".event").click(function(){
		g_auto_ajax($(this));
	});
	/*
	//top menu
	$(parent).find("#menu li,#sub_menu li").hover(function(){
		$(this).addClass("hover");
	},function(){
		$(this).removeClass("hover");
	});
	
	var tabs = $(parent).find(".sidebar ul, .sidetab ul, .tab ul, .mtab ul, .mid_tab ul, .box_tab ul, ul.sub_tab")
	tabs.each(function(){
		var lis = $(this).children("li");
		lis.click(function(){
			g_auto_ajax($(this));
			lis.removeClass("selected");
			$(this).addClass("selected");
		});
	});
	*/
}