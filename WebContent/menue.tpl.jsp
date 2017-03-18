<%@page import="core.Global" %>
<hr />
<div style="float: left;">
	<form action="index.jsp" method="get">

		<!--
		<button class="btn btn-primary <%= Global.getIfActive(session, "start") %>" name="id" value="start"><%=Global.display("start")%></button>
		<button class="btn btn-primary <%= Global.getIfActive(session, "upload_sheet") %>" name="id" value="upload_sheet">
		<%=Global.display("add a sheet")%></button>
		<button class="btn btn-primary <%= Global.getIfActive(session, "drafts") %>" name="id" value="drafts">
		<%=Global.display("drafts")%></button>
		<button class="btn btn-primary <%= Global.getIfActive(session, "statistik") %>" name="id" value="statistik">
		<%=Global.display("statistics")%></button>
		-->
		<a class="btn btn-primary <%= Global.getIfActive(session, "start") %>" href="?id=start">
			<i class="icon-home icon-white"></i>
			<%=Global.display("start")%></a>
		<a onclick="$('#overlay').show();$('#upload_sheet').show();return false;" class="btn btn-primary <%= Global.getIfActive(session, "upload_sheet") %>" href="?id=upload_sheet">
			<i class="icon-plus icon-white"></i><i class="icon-leaf icon-white"></i>
			<%=Global.display("add a sheet")%></a>
		<a class="btn btn-primary <%= Global.getIfActive(session, "drafts") %>" href="?id=drafts">
			<i class="icon-book icon-white"></i>
			<%=Global.display("drafts")%>
		</a>
		<a class="btn btn-primary <%= Global.getIfActive(session, "statistik") %>" href="?id=statistik">
			<i class="icon-fire icon-white"></i>
			<%=Global.display("statistics")%>
		</a>
		<%
		if (Global.isLoggedIn(session)) {
		%>
			<button class="btn btn-danger" name="q" value="logout">
				Logout<i class="icon-magnet"></i></button>

		<%
		}
		%>
		<!--
		<button class="menue" name="id" value="usermanagement">Verwaltung</button>
		-->
		<!--
		<button class="btn btn-secondary" style="color:rgb(75,150,200);font-size:auto !important;" name="id" value="settings">
			 <%=Global.display("settings")%> <i class="icon-wrench"></i></button>
		 -->
		<a class="btn btn-success <%= Global.getIfActive(session, "download") %>" href="?id=download">
			Download!
			<i class="icon-arrow-down"></i>
		</a>

	</form>
</div>
<!-- For Firefox 2px, for Chrome without top margin -->

<div style="float: right;">
	<form action="index.jsp" method="get">
	<div>
		<input id="search_string" type="text" name="search_string" placeholder="<%=Global.display("search")  %> | Filter" value="" />
		<button id="search_btn" name="id" value="search_result_lucene" style="height: 27px;" >
			<i class="icon-search"></i></button>
		<a href="?id=ext_search" style="margin-top: 10px;"
			><%=Global.display("extended search")%></a>
			</div>
	</form>
</div>


<!-- OVERLAY -->
<div id="overlay" class="overlay" style="background-color:rgba(255,255,255,.7);" onclick="$('#overlay').hide();$('#upload_sheet').hide();"></div>
<div id="upload_sheet" class="centered fixed"
 style="border:3px solid rgb(155,155,255); display: none; background-color:rgba(255,255,255, .9);"
 ondblclick="$('#overlay').hide();$('#upload_sheet').hide();">
	<jsp:include page="upload_sheet.in.jsp" />
</div>

<div style="clear: both;"></div><!-- clear both (i.e. clear floating layout in this DOM level) -->
