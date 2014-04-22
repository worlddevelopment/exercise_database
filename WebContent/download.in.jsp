<%@page import="db.UnixComandosThread"%>
<%@page import="db.MysqlHelper"%>
<%@page import="aufgaben_db.DocType"%>
<%@page import="java.util.ArrayList,java.util.List, java.util.Date"%>
<%@page import="java.io.File"%>
<%@page import="aufgaben_db.Global" %>
<%


// Preconditions
// IMPORTANT NOTE: SQLite3 command has to be installed.
/* if (Global.session == null) {
    Global.addMessage("Session was null in page 'download'!", "danger");
    return ;
}
 */
String action = null;
if (request != null && request.getParameter("q") != null) {
        action = request.getParameter("q");
/*      if (action.equalsIgnoreCase("download_regenerate")) {//<-- force regeneration of zip files?
        }
        else if (action.equalsIgnoreCase("download")) {
        } */
} 
 
/* ======= LOCAL ATTRIBUTES ======= */
String[] downloads = { "all", "apache_tomcat", "program", "data" };
String archive_type = "zip";//7z;
String[] downloadsExpanded = new String[downloads.length];
//expand:
int i = -1;
while (++i < downloads.length) {
    downloadsExpanded[i] = "AVSy--" + downloads[i] + "." + (downloads[i].contains("program") ? "war" : archive_type);
}



/* ======= EVERY TIME THE SITE IS LOADED ======= */
// 0) Determine changes, only if changed rezip and resynchronise the db. ("a cache")
boolean allFilesStillThere = true; // TODO check if up to date (i.e. if older than one (?) hour).
boolean allFilesStillUpToDate = true;
/* Check if at least one of the above two conditions no longer is valid (turns into false). If so, recreate.*/
for (String downloadExpanded : downloadsExpanded) {
    File file = new File(downloadExpanded);
    // 1. check if zip file already exists.
    if (!file.exists()) {
        allFilesStillThere = false;
        break;
    }
    if (new Date().getTime() -  file.lastModified() > 1 * 60 * 60 * 1000 /*s == 1h*/ ) {//<- compiler will calculate the constant.
        allFilesStillUpToDate = false;
    }
}

if (!allFilesStillThere || !allFilesStillUpToDate 
        // Force a rezip?
        || (action != null && action.equalsIgnoreCase("download_force_regeneration"))) {
    
    // 1 synchronise database from MySQL to SQLite:
    // a) Remove sqlite db file:
    if (Global.msqh.sqlite_db_filelink != null && new File(Global.msqh.sqlite_db_filelink).exists()) { 
        org.apache.commons.io.FileDeleteStrategy.FORCE.delete(new File(Global.msqh.sqlite_db_filelink));// no deletion required, we simply could overwrite. though it's easier for handling as this prevents the danger of accidentally appending if it's forgotten to clear/truncate/empty the whole database before synching.
    }
    // b) Make Global query function operate on the correct connection. (currently used for consistency only, just to be safe)
    java.sql.Connection prevConnection = Global.conn;
    // connection already opened?
    if (Global.msqh.getConnection("sqlite") == null) {
        // ensure sqlite connection is opened.
        Global.msqh.connectToSQLite();
    }
    Global.conn = Global.msqh.getConnection("sqlite");
    // c) Export MySQL content and Run the SQLite import script: https://gist.github.com/esperlu/943776/ (executes mysqldump itself! data)
//     String sql_filelink = Global.root + File.separator + Global.uploadTarget + "aufgaben_db.sql";
//     String mysql2sqlite_conversion_filelink = Global.root + File.separator + "mysql2sqlite.sql";
    command.Command[] commands = {
            //command.Command.constructInstance(Global.root + System.getProperty("file.separator")),/*Needs sqlite3 to be installed on the UNIX server.*/
            command.Command.constructInstance(Global.root + "convert2sqlite/mysql2sqlite.sh " 
            /* + " --no-data " + */ + " -u root -p 'a)t5qTU[' aufgaben_db | sqlite3 " + Global.msqh.sqlite_db_filelink)
            //TODO if password changes, then change this here too or use the MySQLHelper.password.
    };
    try {
        new UnixComandosThread("", "").d_o(commands);
    }
    catch(Exception e) {
        e.printStackTrace();
        System.out.println(
                Global.addMessage("It's not supported to Download from a non-UNIX machine currently."
                        , "success")
        );
    }
    
    // c)alternative: parse a sql dump file and import the result into SQLite after conversion:
    //TODO convert mysql2sqlite conversion shell script from above to pure Java.  
    //Global.runScript(sql_filelink, Global.conn);//<- we could pass sqlite connection directly too here.

    // d) restore initial state:
    Global.conn = prevConnection;
    
    
    
    
    // 2 create 7z/zip compressed file of all files of uploads/:
    // a)
    File dir = new File(Global.root);
    if (!dir.getAbsolutePath().endsWith(Global.uploadTarget) && !dir.getAbsolutePath().endsWith(Global.uploadTarget + File.separator)) {
        dir = new File(
            Global.root + File.separator + Global.uploadTarget + File.separator
        );
    }
    // b) zip
    String target_filelink = Global.root + File.separator + "AVSy--data" + "." + archive_type;
    if (converter.Zip.zip(dir,  target_filelink)) {//TODO zip multiple directories. e.g. index_data, uploads,..
        Global.addMessage("Zip file " + target_filelink + " was created successfully.", "success");
    }
    else {
        Global.addMessage("Zip file " + target_filelink + " failed. Please check in the filesystem if has really not been created if possible.", "nosuccess");
    }
    
    
    
    // 3 & 4  i.e. the Tomcat container and the Program itself are not being generated on the fly.
    
    
}
    



/* ======= ON REQUEST ======= */



%>
<form method="get" action="">
    <button type="submit" name="q" value="download_force_regeneration">
        <%= Global.display("force refresh") %>
    </button>
</form>

<hr />
<%
for (i = 0; i < downloads.length; i++) {
%>
<div style="text-align:center; margin: 2%; vertical-align: middle;">
    <span style=""><%= (i + 1) %></span>
    <a class="btn btn-success" style="display:inline-block; width:90%; padding:1%;" 
            href="<%=downloadsExpanded[i] %>">
        <%= (downloads[i].equalsIgnoreCase("all") ? 
                "Download All (Tomcat + Aufgaben-Verwaltungs-System + Dateien)" 
                : Global.display(downloads[i]) ) %>
    </a>
    <!-- 
    <form method="get" action="" style="inline-block">
        <input type="hidden" name="q" value="download"></input>
        <button type="submit" name="download" value="<%= downloads[i] %>"></button>
    </form>
    -->
</div>
<%
}
%>
