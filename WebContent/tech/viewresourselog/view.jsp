<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	
	<style>
		.rowAns {
			height: 32px;
			display: block;
		}
		
		.rowAns span {
			display: inline-block;
			border: 1px solid #dfdfdf;
			float: left;
			height: 30px;
			line-height: 30px;
			text-align: center;
		}
		
		.rowAns a{
			text-decoration:underline;
			color: blue;
			height: 25px; line-height: 25px;
		}
		
		#AnsDIV{
			display: inline-block;
		}

	</style>
	
	<script type="text/javascript">
	initJsonForm("#myForm",function(d){
		location.href="index";
	},function(d){
		alert(d.data)
	});
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">
				${i18n.goods.title}
			</div>
			<form id="myForm" action="#" method="post" class="form">
				<input type="hidden" name="id" value="${dto.id}" />
					<ul>
						<li>
							<label>${i18n.goods.name}:</label>
							<c:choose>
								<c:when test="${dto.resType eq '1'}">
									<input type="text" name="name" readonly="readonly" value="<ecan:viewDto dtoName="TechConsumablesManager" id="${dto.resId}" property="conName"/>" v="notEmpty" maxlength="100" />
								</c:when>
								<c:when test="${dto.resType eq '0'}">
									<input type="text" name="name" readonly="readonly" value="<ecan:viewDto dtoName="TechResourseManager" id="${dto.resId}" property="resName"/>" v="notEmpty" maxlength="100" />
								</c:when>
							</c:choose>
						</li>
						<li>
						<label>${i18n.goods.sum}:</label>
							<input type="text" class="gray" name="num" value="${dto.intResSum}" readonly="readonly"  v="notEmpty" maxlength="100" />
						</li>
						<li>
						<label>${i18n.user.filed.name}:</label>
							<input type="text" class="gray" name="operName" value="${dto.operName}" readonly="readonly"  v="notEmpty" maxlength="100" />
						</li>
						<li>
						<label>${i18n.resourselog.date}:</label>
							<input type="text" class="gray" name="insertDate"   value="<ecan:dateFormart value="${dto.insertDate}" formart="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly"  v="notEmpty" maxlength="100" />
						</li>
						<li>
						<label>${i18n.goods.type}:</label>
							<d:viewDomain domain="RESOURSETYPE" value="${dto.resType}"/>
						</li>
						<li><label>${i18n.goods.remark}:</label>
							<textarea rows="4" cols="20" readonly="readonly">${dto.logInfo}</textarea>
						</li>
					</ul>
					</form>
				<div class="btns" style="margin-left: 160px;">
					<span class="btn">
						<a href="javascript:history.back()">${i18n.button.back}</a>
					</span>		
				</div>
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>