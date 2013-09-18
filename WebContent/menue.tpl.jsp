<%@page import="aufgaben_db.Global" %>

<hr />
<div style="float: left;">
    <form action="index.jsp" method="get">

        <button class="btn btn-primary <%= Global.getIfActive(session, "start") %>" name="id" value="start">Start</button>
        <button class="btn btn-primary <%= Global.getIfActive(session, "upload_sheet") %>" name="id" value="upload_sheet">Blatt
            hinzuf&uuml;gen</button>
        <button class="btn btn-primary <%= Global.getIfActive(session, "drafts") %>" name="id" value="drafts">Entwürfe</button>
        <button class="btn btn-primary <%= Global.getIfActive(session, "statistik") %>" name="id" value="statistik">Statistik</button>
        <%
        if (Global.angemeldet) {
        %>
            <button class="btn btn-danger" name="q" value="logout">Logout</button>
            
        <%
        }
        %>
        <!--  <button class="menue" name="id" value="usermanagement">Nutzerverwaltung</button>-->
        <button class="btn btn-secondary" style="color:rgb(75,150,200);font-size:auto !important;" name="id" value="profile">Profil</button>
    </form>
</div>
<!-- Fuer Firefox 2px,fuer Chrome ohne top margin -->

<div style="float: right;">
    <form action="index.jsp" method="get">
    <div>
        <input type="text" name="search_string" placeholder="Suche"/>
        <button name="id" value="search_result_lucene" style="height: 27px;" ><i class="icon-search"></i></button>
        <a href="index.jsp?id=ext_search" style="margin-top: 10px;"
            >Erweiterte Suche</a>
            </div>
    </form>
</div>


<div style="clear: both;"></div><!-- clear both (i.e. clear floating layout in this DOM level) -->