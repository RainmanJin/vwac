<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<script>
$(function(){
    var leftSel = $("#selectL");
	var rightSel = $("#selectR");
	$("#toright").bind("click",function(){		
		leftSel.find("option:selected").each(function(){
			$(this).remove().appendTo(rightSel);
		});
	});
	$("#toleft").bind("click",function(){		
		rightSel.find("option:selected").each(function(){
			$(this).remove().appendTo(leftSel);
		});
	});
	leftSel.dblclick(function(){
		$(this).find("option:selected").each(function(){
			$(this).remove().appendTo(rightSel);
		});
	});
	rightSel.dblclick(function(){
		$(this).find("option:selected").each(function(){
			$(this).remove().appendTo(leftSel);
		});
	});
	
});
</script>
<style type="text/css">
.demo{width:450px; margin:100px auto}
.select_side{float:left; width:200px}
.demo select{width:180px; height:200px}
.select_opt{float:left; width:40px; height:100%; margin-top:36px}
.select_opt p{width:26px; height:26px; margin-top:6px; background:url(../../images/arr.gif) no-repeat; cursor:pointer; text-indent:-999em}
.select_opt p#toright{background-position:2px 0}
.select_opt p#toleft{background-position:2px -22px}
.sub_btn{clear:both; height:42px; line-height:42px; padding-top:10px; text-align:center}
</style>
<div id="main">
  <div class="demo">
     <div class="select_side">
     <p>待选区</p>
  <select id="selectL" size="30" name="selectL"  multiple="multiple">  
	   <c:forEach var="main" items="${list}" varStatus="s">
	         <optgroup label="${main.tagName}">  
	               <c:forEach var="child" items="${main.tct}" varStatus="s">
	                   <option value="${child.ctagName}">${child.ctagName}</option>  
	               </c:forEach>
	         </optgroup> 
	   </c:forEach>
 </select>  
     </div>
     <div class="select_opt">
        <p id="toright" title="添加">&gt;</p>
        <p id="toleft" title="移除">&lt;</p>
     </div>
     <div class="select_side">
     <p>已选区</p>
     <select id="selectR" name="selectR" multiple="multiple">
     </select>
     </div>
     <div class="sub_btn"></div>
  </div>
  
</div>