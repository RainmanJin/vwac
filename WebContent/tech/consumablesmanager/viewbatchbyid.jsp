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
				${i18n.consumables.title}
			</div>
			<form id="myForm" action="add" method="post" class="form">
				<input type="hidden" name="id" value="${batch.id}" />
					<ul>
						<li>
							<label>${i18n.consumables.batch}</label>
							<input type="text" name="name" value="${batch.batch}" readonly="readonly" maxlength="3" />
						</li>
						
						<li>
							<label>${i18n.consumables.totlesum}</label>
							<input type="text" name="conSum" value="${batch.intSum}" readonly="readonly" maxlength="5" />
						</li>
						
						
						<li>
							<label>${i18n.consumables.price}</label>
							<input type="text" name="conPrice" value="${batch.conPrice}" readonly="readonly" maxlength="8" />
						</li>
						
						<li>
							<label>${i18n.course.filed.createtime}</label>
							<input type="text" name="conDate" value="${batch.conDate}" readonly="readonly" maxlength="18" />
						</li>
						
						<li>
							<label>${i18n.user.filed.name}</label>
							<input type="text" name="userName" value="${batch.userName}" readonly="readonly" maxlength="15" />
						</li>
						<li>
							<label>${i18n.consumables.surplus}</label>
							<input type="text" name="surplusSum" value="${batch.surplusSum}" readonly="readonly" maxlength="5" />
						</li>
						<li>
							<label>${i18n.consumables.remark}</label>
							<p>
								${batch.conRemark}
							</p>
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