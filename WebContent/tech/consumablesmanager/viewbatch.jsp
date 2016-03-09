<%@include file="/common/context.jsp" %>
<script type="text/javascript">
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});
</script>
<ec:table tableId="ec_ajax" action="viewbatch?id=${consumablesId}" method="post" items="batchList" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
					<ec:row>
						<ec:column title="${i18n.consumables.batch}" property="batch" width="5%"></ec:column>
						<ec:column title="${i18n.consumables.totlesum}" property="intSum" width="6%"></ec:column>
						<ec:column title="${i18n.consumables.price}" property="conPrice" width="5%"></ec:column>
						<ec:column title="${i18n.course.filed.createtime}" property="conDate" cell="date" format="yyyy-MM-dd HH:mm:ss" width="18%"></ec:column>
						<ec:column title="${i18n.course.filed.creator}" property="userName" width="8%"></ec:column>
						<ec:column title="${i18n.consumables.remark}" property="conRemark" width="15%">
							<ecan:SubString content="${var.conRemark}" end="14" fix="..."/>
						</ec:column>
					</ec:row>
			</ec:table>
