<%@ page import="java.io.IOException,
     java.io.PrintWriter,
     java.sql.Connection,
     java.sql.ResultSet,
     java.sql.SQLException,
     java.sql.Statement,
     java.util.HashMap,
     javax.servlet.ServletContext,
     javax.servlet.http.HttpServlet,
     javax.servlet.http.HttpServletRequest,
     javax.servlet.http.HttpServletResponse,db.*"%>

    <%!public class SQL_Methods{
    	private Connection conn = null;
    	
    	private String str;
    	
    	public String getStr() {
    		return str;
    	}

    	public void setStr(String str) {
    		this.str = str;
    	}
    	
    	public Connection getConn() {
    		return conn;
    	}

    	public void setConn(Connection conn) {
    		this.conn = conn;
    	}

    	/**
    	 * @throws SQLException 
    	 * 
    	 */
    	
    	public boolean is_duplicate(String table,String select,HashMap<String,String> column_value,HttpServletResponse response) throws IOException,SQLException {
    		// Anfrage-Statement erzeugen.
    		PrintWriter out = response.getWriter();
    		out.print(str);
    		if(this.conn == null) {
    			out.print("HAllo");
    			return true;
    		}
    		else {
            Statement query = getConn().createStatement();
            

            String where = "";
            String and = "";

            
            int count = 1;
            for (String key : column_value.keySet()) {
                //System.out.println("Key: " + key + ", Value: " + column_value.get(key));
            	if(count < column_value.size()){
            		and = " and ";
            	}
                where += key + "='" + column_value.get(key) + "' " + and;
                and = "";
            	count++;
            }

    		String sql = "select " + select + " from " + table + " where " + where + ";";
            ResultSet result = query.executeQuery(sql);
            result.last();
            int zeilen_anzahl = result.getRow();

            
            if(zeilen_anzahl == 0) {
            	return false;
            }
            else {
            	
            	return true;
            }
    		}
    	}
    	
    	public void exQuery(String query){

    		Statement st = null;
    			try {
    				st = conn.createStatement();

    				st.executeUpdate(query);
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			
    	
    	}
    	
    	public String printQuery(String query,String toprint){
    		String str = "";
    		Statement st = null;
    			try {
    				st = conn.createStatement();

    				ResultSet result = st.executeQuery(query);
    				
    				while (result.next()) {
                        String temp = result.getString(toprint); // Alternativ: result.getString(1);
                        //String last_name = result.getString("last_name"); // Alternativ: result.getString(2);
                        str += temp + "\n";
                        
                    }
    				
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			return str;
    	}
    	public String getId(String table,String where_column,String where_value) {
    		String id = "";
    		Statement st = null;
    		String query = "select distinct id from " + table + " where " + where_column + "='" + where_value + "';" ;
    		try {
    			st = conn.createStatement();
    			ResultSet rs = st.executeQuery(query);
    			rs.next();
    			id = rs.getString("id");

    			
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		return id;
    	}
    	
    	public String getIds(String table,HashMap<String,String> column_value) {
    		Statement query =null;
    		String id = "";
            
            String where = "";
            String and = "";

            try {
            	 int count = 1;
            	query = conn.createStatement();
                for (String key : column_value.keySet()) {
                    //System.out.println("Key: " + key + ", Value: " + column_value.get(key));
                	if(count < column_value.size()){
                		and = " and ";
                	}
                    where += key + "='" + column_value.get(key) + "' " + and;
                    and = "";
                	count++;
                }

        		String sql = "select id" + " from " + table + " where " + where + ";";
                ResultSet result = query.executeQuery(sql);
                result.next();

        		id = result.getString("id");
            	
            } catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            
            
    		return id;
    	}
    	

    	
    	public void create_sem_view(HttpServletRequest req,HttpServletResponse resp) throws IOException, SQLException {
    		
    		PrintWriter out =  resp.getWriter();
    		
    			Statement statement1 = null;
    			Statement statement2 = null;
    			Statement statement3 = null;
    			Statement statement4 = null;
    			Statement statement5 = null;
    			Connection con = DBConnection.getInstance().getConnection();

    			

    			
    			int i = 0;
    			try {
    				statement1 = con.createStatement();

    				String str_query1 = "SELECT sem_bez FROM semester order by sem_bez asc";
    		        ResultSet res1 = statement1.executeQuery(str_query1);
    				
    				out.print("<div style='float: left;width:200px;'>");
    				out.print ("<ul id='red' class='treeview'>");
    				//----------------Semester --------------------
    				while(res1.next()) {
    					statement2 = con.createStatement();
    					out.print("<li>");

    					
    						out.print("<span>" + res1.getString("sem_bez") + "</span>");
    						out.print("<ul>");
    						
    						
    					String var1 = res1.getString("sem_bez");
    					String str_query2 = "SELECT distinct ver_bez FROM blatt, semester, veranstaltung WHERE veranstaltung.id = blatt.ver_id and sem_bez = '"+ var1 + "' and semester.id = blatt.sem_id";

    					ResultSet res2 = statement2.executeQuery(str_query2);
    						//-------------------Veranstaltung ---------------
    						while (res2.next()) {
    							statement3 = con.createStatement();
    							out.print("<li>");

    								String var2 = res2.getString("ver_bez");
    								out.print("<span>" + var2 + "</span>");
    								out.print("<ul>");
    								
    								
    								String str_query3 = "SELECT distinct name FROM blatt, semester, veranstaltung,dozent WHERE veranstaltung.id = blatt.ver_id and sem_bez = '" + var1 + "' and ver_bez = '" + var2 + "' and blatt.doz_id = dozent.id and semester.id = blatt.sem_id";
    								ResultSet res3 = statement3.executeQuery(str_query3);
    								//--------------------Dozent -----------------------
    								while (res3.next()) {
    									statement4 = con.createStatement();
    									out.print("<li>");
    									String var3 = res3.getString("name");
    									out.print("<span>" + var3 +"</span>");
    									out.print("<ul>");
    									
    										String str_query4 = "SELECT distinct typ_bez FROM blatt, semester, veranstaltung,dozent,typ WHERE veranstaltung.id = blatt.ver_id and sem_bez = '" + var1 + "' and ver_bez = '" + 
    										var2 +"' and blatt.doz_id = dozent.id and semester.id = blatt.sem_id and typ.id = blatt.typ_id and name = '"+ var3 + "'";
    										ResultSet res4 = statement4.executeQuery(str_query4);
    										//--------------------TYP -----------------------
    										//res4.beforeFirst();
    										while (res4.next()) {
    											statement5 = con.createStatement();
    											out.print("<li>");
    											i++;
    											String var4 = res4.getString("typ_bez");
    												//echo "<a href = '#' id = 'ajaxloadlink' onclick = ret_str('$var4')>".$var4."</a>";
    												//echo "<a href = '#' id = 'ajaxlink' onmouseover = _get("."'$var2'".")>".$var4."</a>";
    												out.print ("<a href = '#' id = 'ajaxlink" + i + "' onmouseover = \"_get('" + var1 
    														+ "','" + var2 + "','" + var3 + "','" + var4 + "','" + i +"')\">" +var4 + "</a>");
    												
    												out.print("<ul>");

    												String str_query5 = "SELECT distinct bl_bez FROM aufgabe,blatt, semester, veranstaltung,dozent,typ WHERE veranstaltung.id = blatt.ver_id and sem_bez = '" + var1 + "' and ver_bez = '" + 
    														var2 +"' and blatt.doz_id = dozent.id and semester.id = blatt.sem_id and typ.id = blatt.typ_id and name = '"+ var3 + "' and typ_bez = '" + var4 + "';";
    														ResultSet res5 = statement5.executeQuery(str_query5);
    														int j = i;
    												while(res5.next()) {
    													j++;
    													out.print("<li>");
    													String var5 = res5.getString("bl_bez");
    													out.print ("<a href = '#' id = 'ajaxlink0" + j + "' onmouseover = \"get_parameter('" + var1 
    															+ "','" + var2 + "','" + var3 + "','" + var4 + "','" + var5 + "','" + j +"')\">" +var5 + "</a>");
    													
    													out.print("</li>");
    												}
    												out.print("</ul>");
    											out.print("</li>");
    										}
    										out.print("</ul>");



    									out.print("</li>");
    									
    								}
    								out.print("</ul>");


    							out.print("</li>");
    						}
    						out.print("</ul>");


    					out.print("</li>");
    				}
    				out.print("</ul>");
    				out.print("</div>");

    	}catch (SQLException e) {
    		        e.printStackTrace();
    		    }

    		
    	}
    	
    	
    public  void create_dozent_view(HttpServletRequest req,HttpServletResponse resp) throws IOException {
    	PrintWriter out =  resp.getWriter();
    	
    	Statement statement1 = null;
    	Statement statement2 = null;
    	Statement statement3 = null;
    	Statement statement4 = null;
    	Statement statement5 = null;
    	Connection con = DBConnection.getInstance().getConnection();

    	

    	
    	int i = 0;
    	try {
    		statement1 = con.createStatement();

    		String str_query1 = "SELECT name FROM dozent order by name asc";
            ResultSet res1 = statement1.executeQuery(str_query1);
    		
    		out.print("<div style='float: left;width:200px;'>");
    		out.print ("<ul id='red' class='treeview'>");
    		//----------------Dozent --------------------
    		while(res1.next()) {
    			statement2 = con.createStatement();
    			out.print("<li>");

    			
    				out.print("<span>" + res1.getString("name") + "</span>");
    				out.print("<ul>");
    				
    				
    			String var1 = res1.getString("name");
    			String str_query2 = "SELECT distinct ver_bez FROM blatt, veranstaltung,dozent WHERE veranstaltung.id = blatt.ver_id and name = '" + var1 + "' and dozent.id = blatt.doz_id order by ver_bez";

    			ResultSet res2 = statement2.executeQuery(str_query2);
    				//-------------------Veranstaltung ---------------
    				while (res2.next()) {
    					statement3 = con.createStatement();
    					out.print("<li>");

    						String var2 = res2.getString("ver_bez");
    						out.print("<span>" + var2 + "</span>");
    						out.print("<ul>");
    						
    						
    						String str_query3 = "SELECT distinct sem_bez FROM blatt, semester, veranstaltung,dozent WHERE veranstaltung.id = blatt.ver_id and name = '" + var1 + "' and ver_bez = '" + var2 + "' and blatt.doz_id = dozent.id and semester.id = blatt.sem_id";
    						ResultSet res3 = statement3.executeQuery(str_query3);
    						//--------------------Semester -----------------------
    						while (res3.next()) {
    							statement4 = con.createStatement();
    							out.print("<li>");
    							String var3 = res3.getString("sem_bez");
    							out.print("<span>" + var3 +"</span>");
    							out.print("<ul>");
    							
    								String str_query4 = "SELECT distinct typ_bez FROM blatt, semester, veranstaltung,dozent,typ WHERE veranstaltung.id = blatt.ver_id and name = '" + var1 + "' and ver_bez = '" + var2 + "' and blatt.doz_id = dozent.id and semester.id = blatt.sem_id and typ.id = blatt.typ_id and sem_bez = '" + var3 + "'";
    								ResultSet res4 = statement4.executeQuery(str_query4);
    								//--------------------TYP -----------------------
    								//res4.beforeFirst();
    								while (res4.next()) {
    									statement5 = con.createStatement();
    									out.print("<li>");
    									i++;
    									String var4 = res4.getString("typ_bez");
    										//echo "<a href = '#' id = 'ajaxloadlink' onclick = ret_str('$var4')>".$var4."</a>";
    										//echo "<a href = '#' id = 'ajaxlink' onmouseover = _get("."'$var2'".")>".$var4."</a>";
    										out.print ("<a href = '#' id = 'ajaxlink" + i + "' onmouseover = \"_get('" + var3 
    												+ "','" + var2 + "','" + var1 + "','" + var4 + "','" + i +"')\">" +var4 + "</a>");
    										
    										out.print("<ul>");

    										String str_query5 = "SELECT distinct bl_bez FROM aufgabe,blatt, semester, veranstaltung,dozent,typ WHERE veranstaltung.id = blatt.ver_id and sem_bez = '" + var3 + "' and ver_bez = '" + 
    												var2 +"' and blatt.doz_id = dozent.id and semester.id = blatt.sem_id and typ.id = blatt.typ_id and name = '"+ var1 + "' and typ_bez = '" + var4 + "';";
    												ResultSet res5 = statement5.executeQuery(str_query5);
    												int j = i;
    										while(res5.next()) {
    											j++;
    											out.print("<li>");
    											String var5 = res5.getString("bl_bez");
    											out.print ("<a href = '#' id = 'ajaxlink0" + j + "' onmouseover = \"get_parameter('" + var3 
    													+ "','" + var2 + "','" + var1 + "','" + var4 + "','" + var5 + "','" + j +"')\">" +var5 + "</a>");
    											
    											out.print("</li>");
    										}
    										out.print("</ul>");
    									out.print("</li>");
    								}
    								out.print("</ul>");



    							out.print("</li>");
    							
    						}
    						out.print("</ul>");


    					out.print("</li>");
    				}
    				out.print("</ul>");


    			out.print("</li>");
    		}
    		out.print("</ul>");
    		out.print("</div>");
    }catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void create_ver_view(HttpServletRequest req,HttpServletResponse resp) throws IOException {
    PrintWriter out =  resp.getWriter();
    	
    	Statement statement1 = null;
    	Statement statement2 = null;
    	Statement statement3 = null;
    	Statement statement4 = null;
    	Statement statement5 = null;
    	Connection con = DBConnection.getInstance().getConnection();

    	

    	
    	int i = 0;
    	try {
    		statement1 = con.createStatement();

    		String str_query1 = "SELECT ver_bez FROM veranstaltung order by ver_bez asc";
            ResultSet res1 = statement1.executeQuery(str_query1);
    		
    		out.print("<div style='float: left;width:200px;'>");
    		out.print ("<ul id='red' class='treeview'>");
    		//----------------Veranstaltung --------------------
    		while(res1.next()) {
    			statement2 = con.createStatement();
    			out.print("<li>");

    			
    				out.print("<span>" + res1.getString("ver_bez") + "</span>");
    				out.print("<ul>");
    				
    				
    			String var1 = res1.getString("ver_bez");
    			String str_query2 = "SELECT distinct name FROM blatt, veranstaltung,dozent WHERE veranstaltung.id = blatt.ver_id and ver_bez = '" + var1 + "' and dozent.id = blatt.doz_id order by name";

    			ResultSet res2 = statement2.executeQuery(str_query2);
    				//-------------------Dozent ---------------
    				while (res2.next()) {
    					statement3 = con.createStatement();
    					out.print("<li>");

    						String var2 = res2.getString("name");
    						out.print("<span>" + var2 + "</span>");
    						out.print("<ul>");
    						
    						
    						String str_query3 = "SELECT distinct sem_bez FROM blatt, semester, veranstaltung,dozent WHERE veranstaltung.id = blatt.ver_id and name = '" + var2 + "' and ver_bez = '" + var1 + "' and blatt.doz_id = dozent.id and semester.id = blatt.sem_id";
    						ResultSet res3 = statement3.executeQuery(str_query3);
    						//--------------------Semester -----------------------
    						while (res3.next()) {
    							statement4 = con.createStatement();
    							out.print("<li>");
    							String var3 = res3.getString("sem_bez");
    							out.print("<span>" + var3 +"</span>");
    							out.print("<ul>");
    							
    								String str_query4 = "SELECT distinct typ_bez FROM blatt, semester, veranstaltung,dozent,typ WHERE veranstaltung.id = blatt.ver_id and name = '" + var2 + "' and ver_bez = '" + var1 + "' and blatt.doz_id = dozent.id and semester.id = blatt.sem_id and typ.id = blatt.typ_id and sem_bez = '" + var3 + "'";
    								ResultSet res4 = statement4.executeQuery(str_query4);
    								//--------------------TYP -----------------------
    								//res4.beforeFirst();
    								while (res4.next()) {
    									statement5 = con.createStatement();
    									out.print("<li>");
    									i++;
    									String var4 = res4.getString("typ_bez");
    										//echo "<a href = '#' id = 'ajaxloadlink' onclick = ret_str('$var4')>".$var4."</a>";
    										//echo "<a href = '#' id = 'ajaxlink' onmouseover = _get("."'$var2'".")>".$var4."</a>";
    										out.print ("<a href = '#' id = 'ajaxlink" + i + "' onmouseover = \"_get('" + var3 
    												+ "','" + var1 + "','" + var2 + "','" + var4 + "','" + i +"')\">" +var4 + "</a>");
    										
    										out.print("<ul>");

    										String str_query5 = "SELECT distinct bl_bez FROM aufgabe,blatt, semester, veranstaltung,dozent,typ WHERE veranstaltung.id = blatt.ver_id and sem_bez = '" + var3 + "' and ver_bez = '" + 
    												var1 +"' and blatt.doz_id = dozent.id and semester.id = blatt.sem_id and typ.id = blatt.typ_id and name = '"+ var2 + "' and typ_bez = '" + var4 + "';";
    												ResultSet res5 = statement5.executeQuery(str_query5);
    												int j = i;
    										while(res5.next()) {
    											j++;
    											out.print("<li>");
    											String var5 = res5.getString("bl_bez");
    											out.print ("<a href = '#' id = 'ajaxlink0" + j + "' onmouseover = \"get_parameter('" + var3 
    													+ "','" + var1 + "','" + var2 + "','" + var4 + "','" + var5 + "','" + j +"')\">" +var5 + "</a>");
    											
    											out.print("</li>");
    										}
    										out.print("</ul>");
    									out.print("</li>");
    								}
    								out.print("</ul>");



    							out.print("</li>");
    							
    						}
    						out.print("</ul>");


    					out.print("</li>");
    				}
    				out.print("</ul>");


    			out.print("</li>");
    		}
    		out.print("</ul>");
    		out.print("</div>");
    }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    	
    public void create_sem_view_deb() throws IOException {
    		

    	Statement statement1 = null;
    	Statement statement2 = null;
    	Statement statement3 = null;
    	Statement statement4 = null;
    	Statement statement5 = null;
    			Connection con = DBConnection.getInstance().getConnection();
    			
    			
    			
    			
    			int i = 0;
    			try {
    				statement1 = con.createStatement();

    				String str_query1 = "SELECT sem_bez FROM semester order by sem_bez asc";
    				statement1.execute(str_query1);
    		        ResultSet res1 = statement1.getResultSet();
    				System.out.println("ist cl : "+ res1.getFetchSize());
    				System.out.print("<div style='float: left;width:200px;'>");
    				System.out.print ("<ul id='red' class='treeview'>");
    				//----------------Semester --------------------
    				while(res1.next()) {
    					statement2 = con.createStatement();
    					System.out.print("<li>");

    					
    					System.out.print("<span>" + res1.getString("sem_bez") + "</span>");
    					System.out.print("<ul>");
    						
    						
    					String var1 = res1.getString("sem_bez");
    					String str_query2 = "SELECT distinct ver_bez FROM blatt, semester, veranstaltung WHERE veranstaltung.id = blatt.ver_id and sem_bez = '"+ var1 + "' and semester.id = blatt.sem_id";

    					ResultSet res2 = statement2.executeQuery(str_query2);
    						//-------------------Veranstaltung ---------------
    						while (res2.next()) {
    							
    							statement3 = con.createStatement();
    							System.out.print("<li>");

    								String var2 = res2.getString("ver_bez");
    								System.out.print("<span>" + var2 + "</span>");
    								System.out.print("<ul>");
    								
    								
    								String str_query3 = "SELECT distinct name FROM blatt, semester, veranstaltung,dozent WHERE veranstaltung.id = blatt.ver_id and sem_bez = '" + var1 + "' and ver_bez = '" + var2 + "' and blatt.doz_id = dozent.id and semester.id = blatt.sem_id";
    								ResultSet res3 = statement3.executeQuery(str_query3);
    								res3 = statement3.getResultSet();
    								//--------------------Dozent -----------------------
    								while (res3.next()) {
    									
    									statement4 = con.createStatement();
    									System.out.print("<li>");
    									String var3 = res3.getString("name");
    									System.out.print("<span>" + var3 +"</span>");
    									System.out.print("<ul>");
    									
    										String str_query4 = "SELECT distinct typ_bez FROM blatt, semester, veranstaltung,dozent,typ WHERE veranstaltung.id = blatt.ver_id and sem_bez = '" + var1 + "' and ver_bez = '" + 
    										var2 +"' and blatt.doz_id = dozent.id and semester.id = blatt.sem_id and typ.id = blatt.typ_id and name = '"+ var3 + "'";
    										ResultSet res4 = statement4.executeQuery(str_query4);
    										//--------------------TYP -----------------------

    										while (res4.next()) {
    											statement5 = con.createStatement();
    											System.out.print("<li>");
    											i++;
    											String var4 = res4.getString("typ_bez");
    												//echo "<a href = '#' id = 'ajaxloadlink' onclick = ret_str('$var4')>".$var4."</a>";
    												//echo "<a href = '#' id = 'ajaxlink' onmouseover = _get("."'$var2'".")>".$var4."</a>";
    											System.out.print ("<a href = '#' id = 'ajaxlink" + i + "' onmouseover = \"_get('" + var1 
    														+ "','" + var2 + "','" + var3 + "','" + var4 + "','" + i +"')\">" +var4 + "</a>");
    												
    												System.out.print("<ul>");

    												String str_query5 = "SELECT distinct bl_bez FROM aufgabe,blatt, semester, veranstaltung,dozent,typ WHERE veranstaltung.id = blatt.ver_id and sem_bez = '" + var1 + "' and ver_bez = '" + 
    														var2 +"' and blatt.doz_id = dozent.id and semester.id = blatt.sem_id and typ.id = blatt.typ_id and name = '"+ var3 + "' and typ_bez = '" + var4 + "';";
    														ResultSet res5 = statement5.executeQuery(str_query5);
    														int j = i;
    												while(res5.next()) {

    													System.out.print("<li>");
    													String var5 = res5.getString("bl_bez");
    													System.out.print ("<a href = '#' id = 'ajaxlink0" + j + "' onmouseover = \"get_parameter('" + var1 
    															+ "','" + var2 + "','" + var3 + "','" + var4 + "','" + var5 + "','" + j +"')\">" +var5 + "</a>");
    													
    													System.out.print("</li>");
    													j++;
    												}
    												System.out.print("</ul>");
    												System.out.print("</li>");
    										}
    										System.out.print("</ul>");



    										System.out.print("</li>");

    								}
    								System.out.print("</ul>");


    								System.out.print("</li>");
    						}
    						System.out.print("</ul>");


    						System.out.print("</li>");
    				}
    				System.out.print("</ul>");
    				System.out.print("</div>");
    	}catch (SQLException e) {
    		        e.printStackTrace();
    		    }

    		
    	}
    	
/*     	public static void main(String[] args) throws SQLException {
    		MySQLConnection c = MySQLConnection.getInstance();
    		SQL_Methods sqlm = new SQL_Methods();
    		sqlm.setConn(c.getConnection());
//    		HashMap<String, String> column_value = new HashMap<String,String>();
//    		column_value.put("sem_bez", "SoSe 2010");
//    		//column_value.put("ver_id", "2");
//    		//sqlm.is_duplicate("blatt", column_value);
//    		System.out.println(sqlm.is_duplicate("semester","sem_bez",column_value));


    		System.out.println(sqlm.getId("veranstaltung", "ver_bez", "Mathe"));
    	} */
//    	public static void main(String[] args) throws IOException {
//    		//SQL_Methods.create_sem_view_deb();
////    		String st= "uploads/WiSe2012/Mathe/Weigel/Klausur/Uebung1.pdf";
////    		String [] temp = st.split("\\.");
////    		System.out.println(temp[1]);
//    	}

    }%>
    
    <%
        	MysqlHelper mh = new MysqlHelper();
                            Connection con = mh.establishConnection(response);
                        	if(con == null) {
                        		out.print("con");
                        	}
                        	//Initialisierung SQL_Methods
                        	SQL_Methods sqlm = new SQL_Methods();
                        	
                        	//sqlm.setStr("hop");
                        	//out.print(sqlm.getStr());
                        	sqlm.setConn(con);
                        	
                        	//out.print("connection established");
                        	HashMap<String, String> column_value = new HashMap<String, String>();
                        	//------------------Speichere Daten in DB---------------------------------
                        	//--------------Semester---------------
                        	column_value.put("sem_bez", "bla");

                        	//Speichere Daten,fall sie nict dupliziert werden
                        	if (!(sqlm.is_duplicate("semester", "sem_bez", column_value,response))) {
                        		String query = "INSERT INTO semester (id ,sem_bez)VALUES (NULL ,'"
                        		+ "bla" + "');";

                        		sqlm.exQuery(query);
                        		out.print("hura");
                        	}
                        	column_value.remove("sem_bez");
        %>