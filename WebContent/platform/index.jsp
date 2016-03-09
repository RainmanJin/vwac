<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/platform/include/top.jsp"%>
<%@ taglib prefix="dd" uri="/WEB-INF/tld/ecan-domain.tld"%>

<div id="contentwrapper">
	<div id="content_right">
		<div class="box">
			<div class="title">
				<span>标题阿标题</span>
				<div class="box_tab" style="float: right; margin-right: 20px; margin-top: 5px; width: 400px;">
					<ul>
						<li class="left"></li>
						<li class="selected">已延误</li>
						<li t="#ajax" u="${ctxPath}/test.jsp">本周到期</li>
						<li>下周到期</li>
						<li>未开始</li>
						<li class="right"></li>
					</ul>
				</div>
			</div>
			<div class="content" id="ajax">
				<p>a你好</p>
				<p>
					<dd:checkBoxDomain domain="SEX" name="性别" value="0|1"/>
					<br />
					<dd:radioDomain domain="SEX" name="性别" value="1"/>
					<br />
					<dd:selectDomain domain="SEX" name="SEX" value="2"/>
					<br />
					<dd:viewDomain value="1" domain="SEX"/>
				</p>
				
				<hr />
				<p>
					<a t="#ajax" u="test.jsp" href="#nogo" class="event">ajax url</a><br />
					<a f="alert('hello')" href="#nogo" class="event">js function</a><br />
				</p>
			</div>
		</div>
		<br />
		<br />
		<hr />
		<br />
		<br />
		<div class="mid_tab">
			<ul>
				<li class="selected">项目计划</li>
				<li t="#ajax" u="${ctxPath}/test.jsp">干特图</li>
				<li>生命周期</li>
				<li>基线管理</li>
			</ul>
		</div>
		
		<br />
		<br />
		<div class="lbox">
			<div class="title">
				<h3>标题拉拉</h3>				
			</div>
			<div class="content">
				内容阿
				<div>好多内容阿</div>
			</div>
		</div>
		
		<br />
		<br />
		<div class="notice">
			<div class="t">
				<div class="lt"></div>
				<div class="rt"></div>
				<div class="ct"></div>
			</div>
			<div class="c">
				<div class="ml" style="height: 100px"></div>
				<div class="mr" style="height: 100px"></div>
				<div class="mc" style="height: 100px">
					<p>wo shi 内容阿<br /></p>
					<p>wo shi 内容阿<br /></p>
					<p>wo shi 内容阿<br /></p>
					<p>wo shi 内容阿<br /></p>
				</div>
			</div>
			<div class="b">
				<div class="lb"></div>
				<div class="rb"></div>
				<div class="cb"></div>
			</div>
		</div>
		
		<br />
		<br />
		<!-- 
		<div class="clearfix_l"></div>
		-->
		<br />
		
		<div class="tab">
			<ul>
				<li class="selected">
					<a href="#nogo">执行中的项目</a>
				</li>
				<li t="#ajax" u="${ctxPath}/test.jsp"><a href="#nogo">已终止的项目</a></li>
				<li><a href="#nogo">已完成的项目</a></li>
			</ul>
		</div>
		<div class="nav_line">
			<ul class="sub_tab" style="padding: 5px 0px 0px 20px;">
				<li class="selected">所有需求</li>
				<li t="#ajax" u="${ctxPath}/test.jsp">未分析</li>
				<li>已分析</li>
				<li>已设计</li>
				<li>已完成</li>
			</ul>
		</div>
		<br />
		<!-- 
		<div class="clearfix_l"></div>
		-->
		<br />
		
		<div class="mtab">
			<ul>
				<li class="selected">
					<a href="#nogo">2011-1-10 周一</a>
				</li>
				<li t="#ajax" u="${ctxPath}/test.jsp"><a href="#nogo">2011-1-11 周二</a></li>
				<li><a href="#nogo">2011-1-12 周三</a></li>
			</ul>
		</div>
	</div>
</div>
<div id="content_left">
		<div class="sidebar" style="height: 200px;">
			<h3>请选择下面的菜单</h3>
			<ul>
				<li class="selected"><a href="#nogo">系统基本参数</a></li>
				<li t="#ajax" u="${ctxPath}/test.jsp"><a href="#nogo">AJAX刷新</a></li>
				<li f="showWin()"><a href="#nogo">模态窗口</a></li>
				<li t="#ajax" u="${ctxPath}/grid.jsp"><a href="#nogo">AJAX更新JS表格</a></li>
				<li t="#ajax" u="${ctxPath}/form.jsp"><a href="#nogo">AJAX表单</a></li>
				<li t="#ajax" u="${ctxPath}/editForm.jsp"><a href="#nogo">Editable Grid</a></li>
				<li><a href="#nogo">系统基本参数</a></li>
				<li><a href="#nogo">系统基本参数</a></li>
				<li><a href="#nogo">系统基本参数</a></li>
			</ul>
		</div>
		
		<div class="sidetab">
			<h3>请选择下面的菜单</h3>
			<ul>
				<li class="selected"><a href="#nogo">系统基本参数</a></li>
				<li t="#ajax" u="${ctxPath}/test.jsp"><a href="#nogo">系统基本参数</a></li>
				<li><a href="#nogo">系统基本参数</a></li>
				<li><a href="#nogo">系统基本参数</a></li>
				<li><a href="#nogo">系统基本参数</a></li>
				<li><a href="#nogo">系统基本参数</a></li>
				<li><a href="#nogo">系统基本参数</a></li>
				<li><a href="#nogo">系统基本参数</a></li>
			</ul>
		</div>
	</div>
<%@ include file="/platform/include/footer.jsp" %>