<%@page import="aufgaben_db.Global"%>
<%@page import="java.io.File"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="swp.MysqlHelper,aufgaben_db.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String [] values = request.getParameterValues("bl_id[]");
    String datei_pfad = "";
    String bild_pfad = "";
    String bl_bez = "";
    String ver_id = "";
    String doz_id = "";
    String sem_id = "";
    String ext = "";
    
    if(values == null) {
    	out.print("Kein Blatt wurde ausgewält");
    	
    }
    else {
    	String root = Global.root;//Global.root;//
    	
		for (int i = 0; i < values.length; i++) {
			MysqlHelper msqh = new MysqlHelper();
			msqh.establishConnection(response);
			Statement statement1 = null;
	
			
			Connection con = msqh.establishConnection(response);
			try {
				statement1 = con.createStatement();
				String str_query = "select * from blatt where id='" + values[i] + "';";
				
				ResultSet res = statement1.executeQuery(str_query);
				res.next();
				
				
				
				datei_pfad = res.getString("link");
				bild_pfad = res.getString("bild_link");
				bl_bez = res.getString("bl_bez");
				ext = res.getString("datei_typ");
				ver_id = res.getString("ver_id");
			    doz_id = res.getString("doz_id");
			    sem_id = res.getString("sem_id");
				statement1.close();
			    
			    
				//loesche blatt aus Der DB
				statement1 = con.createStatement();
				str_query = "delete from blatt where id='" + values[i] + "';";
				statement1.execute(str_query);
				statement1.close();
				
				//Loesche Vorschaubilder von Festplatte
				statement1 = con.createStatement();
				str_query = "select bild_link from aufgabe where bl_id ='" + values[i] + "';";
				ResultSet res0 = statement1.executeQuery(str_query);
				int j = 0;
				while(res0.next()) {
					
					File f = new File(root + res0.getString("bild_link"));
					boolean succsess = Global.deleteFile(f);
					if(succsess){
						out.print("<p>Das Vorschaubild von Aufgabe" + j + "  wurde gelöscht.</p>");
					}
					j++;
				}
				
				
				//loesche Aufgaben aus Der DB
				statement1 = con.createStatement();
				str_query = "delete from aufgabe where bl_id ='" + values[i] + "';";
				statement1.execute(str_query);
				statement1.close();
				
				statement1 = con.createStatement();
				str_query = "select count(*) as spalten_anzahl from blatt where sem_id='" + sem_id + "';";
				ResultSet res5 = statement1.executeQuery(str_query);
				res5.next();
				
				//loesche Antraege aus Semester-Tabelle

				if(res5.getString("spalten_anzahl").equals("0")) {
				statement1.close();
				statement1 = con.createStatement();
				str_query = "delete from semester where id='" + sem_id + "';";
				statement1.execute(str_query);
				statement1.close();
				}
				
				//loesche Antraege aus der Veranstaltung-Tabelle
				statement1 = con.createStatement();
				str_query = "select count(*) as spalten_anzahl from blatt where ver_id='" + ver_id + "';";
				ResultSet res6 = statement1.executeQuery(str_query);
				res6.next();
				if(res6.getString("spalten_anzahl").equals("0")){
				
				statement1.close();
				statement1 = con.createStatement();
				str_query = "delete from veranstaltung where id='" + ver_id + "';";
				statement1.execute(str_query);
				statement1.close();
				}
				
				//loesche Antraege aus der Dozent-Tabelle
				statement1 = con.createStatement();
				str_query = "select count(*) as spalten_anzahl from blatt where doz_id='" + doz_id + "';";
				ResultSet res7 = statement1.executeQuery(str_query);
				res7.next();
				if(res7.getString("spalten_anzahl").equals("0")){
				
				statement1.close();
				statement1 = con.createStatement();
				str_query = "delete from dozent where id='" + doz_id + "';";
				statement1.execute(str_query);
				}
				
				
				
				//loesche Blatt von der Festplatte
				
				
				File f=new File(root + datei_pfad);
				boolean succsess = Global.deleteFile(f);
				
				if(succsess){
				out.print("<p>Die Datei: <strong>" + bl_bez + "</strong> wurde von dem Server gelöscht.</p>");
				}
				else {
					out.print("<p>Die Datei: <strong>" + bl_bez +  "</strong> existiert nicht.</p>");
				}
				
				//loesche Pdf-Datei,die bei der Erstellung Vorschaubildes automatisch erstellt wurde 
					
				//ersetze datei_typ mit pdf
				String filename_reversed = new StringBuffer(
						datei_pfad).reverse().toString();
				int slash = filename_reversed.indexOf("/");
				String pfad_reversed = filename_reversed.substring(slash,
						filename_reversed.length());//file endung

				String pdf_file = new StringBuffer(pfad_reversed).reverse()
						.toString()  + Global.encodeUmlauts(bl_bez.replaceAll(" ", "")) + ".pdf";
						
				
				//loesche PDF-Datei - why? -okay, we don't need it anymore after having the image
			    //but we surely need the tex-, LibreXML-, docx-files (derivations of the original)
				if(ext != "pdf") {
					f = new File(root + pdf_file);
					succsess = Global.deleteFile(f);
					if(!succsess){
						out.print("<p>Die zu loeschende pdf-Datei:  existiert nicht.</p>");
					}
				} 
				
				//loesche Ordner mit der Aufgaben
				String aufgaben_dir =  new StringBuffer(pfad_reversed).reverse()
						.toString()  + Global.encodeUmlauts(bl_bez.replaceAll(" ", "_")) + "__" + ext;
				
				Global.deleteDir(new File(root + aufgaben_dir)); //loesche Ordner mit der Aufgaben
			    
				
				//loesche Vorschauild des Blattes von der Festplatte
				
				f = new File(root + bild_pfad);
				succsess = Global.deleteFile(f);
				if(succsess){
				out.print("<p>Das Vorschaubild wurde von dem Server gelöscht.</p>");
				}
				else {
					out.print("<p>Das Bild: existiert nicht.</p>");
				}
				
				
				
				
			}
			catch (SQLException e) {
	        e.printStackTrace();
	   		}
				//request.getParameterValues("aufg_id[]")[i];
				

		} 
    }
        
    %>
 
 <!-- Update Index von Suchmaschine  --> 
<%@include file="action.update_index.jsp"%>
    