<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<%--<script>
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
     <select id="selectL" name="selectL" multiple="multiple">
         <c:forEach var="ls" items="${list}" varStatus="s">
         <option value="${ls.id}">${ls.name}</option>
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
  
</div>--%>
<script>
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});
</script>
			<ec:table tableId="ec_ajax" action="listUsers?id=${param.id}" method="post" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
					<ec:row>
						<c:if test="${!(param.type eq 'hoster')}">
							<ec:column headerCell="selectAll" property="oper" alias="selIds" width="2%" filterable="false">
								<input type="checkbox" name="selIds" value="${var.id}" />
							</ec:column>
						</c:if>
						<ec:column title="${i18n.user.filed.name}" property="name" width="10%">
						<c:if test="${param.type eq 'hoster'}">
							<a href="#nogo" onclick="chgHoster('${var.id}')">${var.name}</a>
						</c:if>
						</ec:column>
						<ec:column title="${i18n.user.filed.company}" property="company" width="20%" filterCell="droplist" filterOptions="DOMAIN_COMPANY">
							<d:viewDomain value="${var.company}" domain="COMPANY" />
						</ec:column>
						
						<%--  DELETE PROTYPE
						<ec:column title="${i18n.user.filed.proType}" property="proType" width="10%" filterCell="droplist" filterOptions="DOMAIN_PROTYPE">
							<d:viewDomain value="${var.proType}" domain="PROTYPE" />
						</ec:column>
						 --%>
						<ec:column title="${i18n.oper.text}" property="oper" width="10%" filterable="false">
							<c:if test="${param.type eq 'hoster'}">
									<a href="#nogo" onclick="chgHoster('${var.id}')">${i18n.oper.select}</a>
							</c:if>
							<a target="_blank" href="${ctxPath}/techc/usermanager/profile?id=${var.id}">${i18n.oper.profile}</a>
						</ec:column>
					</ec:row>
			</ec:table>