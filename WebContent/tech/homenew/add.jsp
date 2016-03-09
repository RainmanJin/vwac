<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>


<%@include file="/common/head.jsp"%>
<style>
.lineSeperater {
	border-top: 1px dotted #dfdfdf;
	padding: 20px 0px;
}

.form li label {
	width: 140px;
}

.rowTitle {
	border: 1px solid #dfdfdf;
	background: #f0f0f0;
	color: #2274AC;
	font-size: 14px;
	font-weight: bold;
	height: 25px;
	line-height: 25px;
}

.eXtremeTable .tableRegion {
	margin: 0px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		initJsonForm("#msForm", function(d) {
			location.href = "index";
		}, function(d) {
			alert(d.data);
		});

		initJsonForm("#imgForm", function(d) {
		   
			$("#prevImg").attr("src","${ctxPath}"+d.data);
			$("#filePath").val(d.data);
		}, function(d) {
			alert(d)
		});
		
		initJsonForm("#attachForm", function(d) {
			//alert(d.data); 
			$("#fj2,#fj").val(d.data);
		}, function(d) {
			alert(d)
		});

	});

</script>
</head>
<body>
	<%@include file="/common/header.jsp"%>

	<div class="centerbody centerbox">
		<div id="contentwrapper">
			<div id="content_right">
				<div class="tt">${i18n.homenew.info}</div>
				<form id="msForm" action="save" method="post">
					<input type="hidden" name="id" value="${dto.id}" />
					<input type="hidden" id="fj" name="fj" value="${dto.fj}" />
					<input type="hidden" id="filePath" name="filePath" value="${dto.filePath}" /> 
					

					<ul class="form">
						<li><label id="testtitle">${i18n.homenew.title}</label> <input
							type="text" id="title" name="title" value="${dto.title}"
							v="notEmpty" maxlength="450" style="width: 450px" /> <span
							class="msg">${i18n.valid.notEmpty}</span></li>
						<li><label id="testtitle">${i18n.homenew.link}</label> <input
							type="text" id="shareUsers" name="shareUsers"
							value="${dto.shareUsers}" maxlength="450"
							style="width: 450px" /> 
						</li>
					

						<li><label id="testtitle">${i18n.homenew.order}</label> <input
							type="text" id="sort" name="sort" value="${dto.sort}"
							 maxlength="450" style="width: 450px" /></li>

						<li style="height: 310px;"><label>${i18n.homenew.contents}</label>
							<textarea id="contents" name="contents" 
								style="width: 450px; height: 300px;">${dto.contents}</textarea>
						</li>
						<li><label>${i18n.homenew.pubtime}</label>
							 <input
							type="text" id="createTimeDesc" name="createTimeDesc" value="${dto.createTimeDesc}"
							 maxlength="450" style="width: 450px" /></li>
						</li>


					</ul>
				</form>

				
				<form id="imgForm" action="img" method="post" class="form"
					enctype="multipart/form-data">
					<input type="hidden" name="id" value="${dto.id}" />

					<li style="height: 136px;"><label>${i18n.homenew.pic}</label> <input type="file" id="img" name="img"
						onchange="$('#imgForm').submit();" />
						
						<div style="display: block;">
							<img src="${ctxPath}${dto.filePath}" id="prevImg" width="162" height="116"
								style="display: block;" />
						</div></li>
				</form>
				
				
				<form id="attachForm" action="attach" method="post" class="form"
					enctype="multipart/form-data">
					<input type="hidden" name="id" value="${dto.id}" />

					<li><label>${i18n.homenew.attach}</label> <input type="file" id="attach" name="attach"
						onchange="$('#attachForm').submit();" /></li>
					<li><label></label> <input type="text" id="fj2" value="${dto.fj}" style="width: 450px" readonly="readonly"/></li>	
						
				</form>



				<div class="btns" style="padding-left: 160px;">
					<span class="btn"> <a href="javascript:void(0)"
						onclick="$('#msForm').submit()">${i18n.button.save}</a>
					</span> <span class="btn"> <a href="index">${i18n.button.back}</a>
					</span>
				</div>
			</div>
		</div>

		<%@include file="/common/leftmenu.jsp"%>
		<div class="clearfix_b"></div>
	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>