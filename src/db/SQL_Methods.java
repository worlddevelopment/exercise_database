package db;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import core.Global;



public class SQL_Methods{

	/**
	 * The connection handler.
	 */
	private Connection conn = null; /* Has to stay independent because of AJAX. */



	/**
	 * Get the connection handle.
	 *
	 * @return conn -- connection handle
	 */
	public Connection getConn() {
		return conn;
	}



	/**
	 * Set the connection handle.
	 *
	 * @param conn -- connection handle
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}



	/**
	 * Escape string to protect against SQL Injection
	 *
	 * Add a single quote ' around the data result of this function
	 * or a backtick ` around table and row identifiers.
	 * If this function returns null then the result should be changed
	 * to "NULL" without any quote or backtick.
	 *
	 * @param str any string
	 * @return a clean string
	 * @throws Exception
	 */
	public String mysql_real_escape_string(String str)
	{
		if (str == null) {
			return null;
		}

		if (str.replaceAll("[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/? ]","").length() < 1) {
			return str;
		}

		String clean_string = str;
		clean_string = clean_string.replaceAll("\\\\", "\\\\\\\\");
		clean_string = clean_string.replaceAll("\\n","\\\\n");
		clean_string = clean_string.replaceAll("\\r", "\\\\r");
		clean_string = clean_string.replaceAll("\\t", "\\\\t");
		clean_string = clean_string.replaceAll("\\00", "\\\\0");
		clean_string = clean_string.replaceAll("'", "\\\\'");
		clean_string = clean_string.replaceAll("\\\"", "\\\\\"");

		if (clean_string.replaceAll("[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/?\\\\\"' ]"
		  ,"").length() < 1)
		{
			return clean_string;
		}


		return clean_string;
	}



	/**
	 * Checks if an entry exists in the database.
	 *
	 * This method is for compatibility only as it requires columns twice, e.g.
	 * @param tables Tables to query from (FROM)
	 * @param columns_to_select Columns to select (SELECT)
	 * @param map_column_value_pairs Mapped column value pairs (WHERE)
	 * @return true or false
	 * @throws IOException
	 * @throws SQLException
	 */
	public boolean exist(String tables,String columns_to_select,HashMap<String,String> column_value)
			throws SQLException, IOException {
		String where = "";
		int count = 0;
		for (String key : column_value.keySet()) {
			//System.out.println("Key: " + key + ", Value: " + column_value.get(key));
			where += key + "='" + column_value.get(key) + "' ";
			if(count < column_value.size() - 1){
				where += " AND ";
			}
			count++;
		}

		// Call overloaded method with generated where statement
		return exist(tables, columns_to_select, where);
	}

	/**
	 * Checks if an entry exists in the database.
	 *
	 * @param tables Tables to query from (FROM)
	 * @param columns_to_select Columns to select (SELECT)
	 * @param where query conditions (WHERE)
	 * @return true or false
	 * @throws IOException
	 * @throws SQLException
	 */
	public boolean exist(String tables, String columns_to_select, String where)
			throws SQLException, IOException {
		// For ajax compatibility create a separate connection (instead of using Global.java)
		Statement query;
		//query = conn.createStatement();

		String sql = "SELECT " + columns_to_select
				+ " FROM " + tables + " WHERE " + where + ";";
		System.out.print("Exist: " + sql);
		ResultSet res = Global.query(sql);
		// Determine length
		int resL = 0;
		if (res.last() /*&& res.getType() == res.TYPE_SCROLL_SENSITIVE*/) {
			resL = res.getRow();
			res.beforeFirst();/*because afterwards follows a next()*/
		}
		// Tackle memory leaks by closing result set and its statement properly:
		Global.queryTidyUp(res);
		//conn.close();//as we don't need the result anymore

		if(resL > 0) {
			return true;
		}
		return false;
	}



	/**
	 * Execute query update type.
	 *
	 * @param query string
	 */
	public void executeUpdate(String query){

		Statement st = null;
		try {
			st = conn.createStatement();
			st.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {// <- as of v31.14
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}



	/**
	 * Execute query update type.
	 *
	 * @param query string
	 */
	public void execute(String query){

		Statement st = null;
		try {
			st = conn.createStatement();

			st.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {// <- as of v31.14
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}



	/**
	 * Print a query.
	 *
	 * @param query Query to execute.
	 * @param toprint Field|key|index to print.
	 */
	public String printQuery(String query, String toprint){
		String str = "";
		Statement st = null;
		ResultSet result = null;
		try {
			st = conn.createStatement();

			result = st.executeQuery(query);

			while (result.next()) {
				String temp = result.getString(toprint);
				//result.getString(1);
				str += temp + "\n";

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			// Tackle memory leaks by closing result set and its statement properly:
			if (st != null) {
				try {
					st.close();
					result.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return str;
	}



	/**
	 * Fetches the id of a database table where column equals value.
	 *
	 * @param table Table to select from.
	 * @param WHERE_column Condition field|column of where part.
	 * @param WHERE_value Condition value to check for equality.
	 * @return The id where column equals value in table.
	 */
	public int getId(String table, String WHERE_column, String WHERE_value) {
		int id = -1;
		Statement st = null;

		String apostrophe_or_not = "'";
		// MYSQL Integer or String required? -- parseInt can't parse if it's a string only!!
		if (WHERE_value.matches("[0-9]+")/*val.equals(Integer.valueOf(val) + "")*/
				&& WHERE_value.equals(Integer.parseInt(WHERE_value) + "")) {//needs regex match first
			//it's potentially a integer => no apostrophes in the query
			apostrophe_or_not = "";
		}

		//assemble
		String query = "SELECT DISTINCT id"
				+ " FROM " + table
				+ " WHERE " + WHERE_column + "=" + apostrophe_or_not + WHERE_value + apostrophe_or_not + ";";
		try {
			st = conn.createStatement();
			ResultSet res = st.executeQuery(query);
			if (res != null && res.next()) {
				id = res.getInt("id");
				// tackle memory leaks by closing result set and its statement properly:
				res.close(); //as of v31.13b
			}
			if (st != null) {
				st.close();
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}



	/**
	 * Fetches the id from table where all columns equal mapped values.
	 *
	 * @param table
	 * @param map_column_value_pairs WHERE_column <= WHERE_column_value
	 * @return The id where multiple columns equal its mapped value.
	 */
	public int getId(String table, HashMap<String,String> map_column_value_pairs) {
		Statement query = null;
		ResultSet result = null;

		int id = -1;

		String where = "";
		String and = "";

		try {
			int count = 1;
			query = conn.createStatement();
			for (String key : map_column_value_pairs.keySet()) {

				String val = map_column_value_pairs.get(key);

				String apostrophe_or_not = "'";
				// MYSQL Integer or String required? -- parseInt can't parse String only!!
				if (val.matches("[0-9]+") && val.equals(Integer.parseInt(val) + "")) {
					// it's potentially a integer => no apostrophes in the query
					apostrophe_or_not = "";
				}

				//System.out.println("Key: " + key + ", Value: " + column_value.get(key));
				if(count < map_column_value_pairs.size()){
					and = " AND ";
				}

				// assemble
				where += key + "=" + apostrophe_or_not + val + apostrophe_or_not + " " + and;

				// not to forget
				and = "";
				count++;
			}

			if (where == null || where.isEmpty()) {
				where = "1";// alternatively use TRUE
			}

			// Assemble the sql query
			String sql = "SELECT id" + " FROM " + table + " WHERE " + where + ";";
			result = query.executeQuery(sql);
			if (result.next()) {

				id = result.getInt("id");

			}
			else {

				Global.addMessage("Empty query in getIds(): " + sql, "warning");

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		finally {
			// Tackle memory leaks by closing result set and its statement properly:
			//Global.queryTidyUp(res);
			try {
				result.close();
				query.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return id;
	}



//	public static void create_sem_view(HttpServletRequest req,HttpServletResponse resp) throws IOException, SQLException {
//
//		PrintWriter out =  resp.getWriter();
//
//			Statement statement1 = null;
//			Statement statement2 = null;
//			Statement statement3 = null;
//			Statement statement4 = null;
//			Statement statement5 = null;
//			MysqlHelper mh = new MysqlHelper();
//			Connection con = mh.establishConnection(resp);
//
//
//
//
//			int i = 0;		//identifizierer fuer Blatt Link
//			int j = 0;		//identifizierer fuer aufagben Link
//			try {
//				statement1 = con.createStatement();
//
//				String str_query1 = "SELECT DISTINCT semester FROM sheetdraft ORDER BY semester ASC";
//				ResultSet res1 = statement1.executeQuery(str_query1);
//
//				out.print("<div style='float: left;width:200px;'>");
//				out.print ("<ul id='red' class='treeview'>");
//				//----------------Semester --------------------
//				while(res1.next()) {
//					statement2 = con.createStatement();
//					out.print("<li>");
//
//
//						out.print("<span>" + res1.getString("semester") + "</span>");
//						out.print("<ul>");
//
//
//					String varvalue1 = res1.getString("semester");
//					String str_query2 = "SELECT DISTINCT course FROM  sheetdraft, semester, course WHERE course.id =  sheetdraft.ver_id AND semester = '"+ varvalue1 + "' AND semester.id =  sheetdraft.sem_id";
//
//					ResultSet res2 = statement2.executeQuery(str_query2);
//						//-------------------Veranstaltung ---------------
//						while (res2.next()) {
//							statement3 = con.createStatement();
//							out.print("<li>");
//
//								String varvalue2 = res2.getString("course");
//								out.print("<span>" + varvalue2 + "</span>");
//								out.print("<ul>");
//
//
//								String str_query3 = "SELECT DISTINCT name FROM  sheetdraft, semester, course,lecturer WHERE course.id =  sheetdraft.ver_id AND semester = '" + varvalue1 + "' AND course = '" + varvalue2 + "' AND  sheetdraft.lecturer_id = lecturer.id AND semester.id =  sheetdraft.sem_id";
//								ResultSet res3 = statement3.executeQuery(str_query3);
//								//--------------------Dozent -----------------------
//								while (res3.next()) {
//									statement4 = con.createStatement();
//									out.print("<li>");
//									String varvalue3 = res3.getString("name");
//									out.print("<span>" + varvalue3 +"</span>");
//									out.print("<ul>");
//
//										String str_query4 = "SELECT DISTINCT type FROM  sheetdraft, semester, course,lecturer,typ WHERE course.id =  sheetdraft.ver_id AND semester = '" + varvalue1 + "' AND course = '" +
//										varvalue2 +"' AND  sheetdraft.lecturer_id = lecturer.id AND semester.id =  sheetdraft.sem_id AND typ.id =  sheetdraft.typ_id AND name = '"+ varvalue3 + "'";
//										ResultSet res4 = statement4.executeQuery(str_query4);
//										//--------------------TYP -----------------------
//										//res4.beforeFirst();
//										while (res4.next()) {
//											statement5 = con.createStatement();
//											out.print("<li>");
//											i++;
//											String varvalue4 = res4.getString("type");
//												//echo "<a href = '#' id = 'ajaxloadlink' onclick = ret_str('$varvalue4')>".$varvalue4."</a>";
//												//echo "<a href = '#' id = 'ajaxlink' onmouseover = _get("."'$varvalue2'".")>".$varvalue4."</a>";
//												out.print ("<a href = '#' id = 'ajaxlink" + i + "' onmouseover = \"_get('" + varvalue1
//														+ "','" + varvalue2 + "','" + varvalue3 + "','" + varvalue4 + "','" + i +"')\">" +varvalue4 + "</a>");
//
//												out.print("<ul>");
//
//												String str_query5 = "SELECT DISTINCT filelink FROM part, sheetdraft, semester, course,lecturer,typ WHERE course.id =  sheetdraft.ver_id AND semester = '" + varvalue1 + "' AND course = '" +
//														varvalue2 +"' AND  sheetdraft.lecturer_id = lecturer.id AND semester.id =  sheetdraft.sem_id AND typ.id =  sheetdraft.typ_id AND name = '"+ varvalue3 + "' AND type = '" + varvalue4 + "';";
//														ResultSet res5 = statement5.executeQuery(str_query5);
//														j = i;
//												while(res5.next()) {
//													j++;
//													out.print("<li>");
//													String varvalue5 = res5.getString("filelink");
//
//													out.print ("<a href = '#' id = 'ajaxlink0" + j + "' onmouseover = \"get_parameter('" + varvalue1
//															+ "','" + varvalue2 + "','" + varvalue3 + "','" + varvalue4 + "','" + varvalue5 + "','" + j +"')\">" +varvalue5 + "</a>");
//
//													out.print("</li>");
//													i=j;
//												}
//												out.print("</ul>");
//											out.print("</li>");
//										}
//										out.print("</ul>");
//
//
//
//									out.print("</li>");
//
//								}
//								out.print("</ul>");
//
//
//							out.print("</li>");
//						}
//						out.print("</ul>");
//
//
//					out.print("</li>");
//				}
//				out.print("</ul>");
//				out.print("</div>");
//
//	}catch (SQLException e) {
//				e.printStackTrace();
//			}
//
//
//	}
//
//
//
//
//
//	public static void create_lecturer_view(HttpServletRequest req,
//			HttpServletResponse resp) throws IOException {
//
//		PrintWriter out =  resp.getWriter();
//
//		Statement statement1 = null;
//		Statement statement2 = null;
//		Statement statement3 = null;
//		Statement statement4 = null;
//		Statement statement5 = null;
//
//		MysqlHelper mh = new MysqlHelper();
//		Connection con = mh.establishConnection(resp);
//
//
//
//		int i = 0;		//identifizierer fuer Blatt Link
//		int j = 0;		//identifizierer fuer aufagben Link
//		try {
//			statement1 = con.createStatement();
//
//			String str_query1 = "SELECT name FROM lecturer ORDER BY name ASC";
//			ResultSet res1 = statement1.executeQuery(str_query1);
//
//			out.print("<div style='float: left;width:200px;'>");
//			out.print ("<ul id='red' class='treeview'>");
//			//----------------Dozent --------------------
//			while(res1.next()) {
//				statement2 = con.createStatement();
//				out.print("<li>");
//
//
//					out.print("<span>" + res1.getString("name") + "</span>");
//					out.print("<ul>");
//
//
//				String varvalue1 = res1.getString("name");
//				String str_query2 = "SELECT DISTINCT course FROM  sheetdraft, course,lecturer WHERE course.id =  sheetdraft.ver_id AND name = '" + varvalue1 + "' AND lecturer.id =  sheetdraft.lecturer_id ORDER BY course";
//
//				ResultSet res2 = statement2.executeQuery(str_query2);
//					//-------------------Veranstaltung ---------------
//					while (res2.next()) {
//						statement3 = con.createStatement();
//						out.print("<li>");
//
//							String varvalue2 = res2.getString("course");
//							out.print("<span>" + varvalue2 + "</span>");
//							out.print("<ul>");
//
//
//							String str_query3 = "SELECT DISTINCT semester FROM  sheetdraft, semester, course,lecturer WHERE course.id =  sheetdraft.ver_id AND name = '" + varvalue1 + "' AND course = '" + varvalue2 + "' AND  sheetdraft.lecturer_id = lecturer.id AND semester.id =  sheetdraft.sem_id";
//							ResultSet res3 = statement3.executeQuery(str_query3);
//							//--------------------Semester -----------------------
//							while (res3.next()) {
//								statement4 = con.createStatement();
//								out.print("<li>");
//								String varvalue3 = res3.getString("semester");
//								out.print("<span>" + varvalue3 +"</span>");
//								out.print("<ul>");
//
//									String str_query4 = "SELECT DISTINCT type FROM  sheetdraft, semester, course,lecturer,typ WHERE course.id =  sheetdraft.ver_id AND name = '" + varvalue1 + "' AND course = '" + varvalue2 + "' AND  sheetdraft.lecturer_id = lecturer.id AND semester.id =  sheetdraft.sem_id AND typ.id =  sheetdraft.typ_id AND semester = '" + varvalue3 + "'";
//									ResultSet res4 = statement4.executeQuery(str_query4);
//									//--------------------TYP -----------------------
//									//res4.beforeFirst();
//									while (res4.next()) {
//										statement5 = con.createStatement();
//										out.print("<li>");
//										i++;
//										String varvalue4 = res4.getString("type");
//											//echo "<a href = '#' id = 'ajaxloadlink' onclick = ret_str('$varvalue4')>".$varvalue4."</a>";
//											//echo "<a href = '#' id = 'ajaxlink' onmouseover = _get("."'$varvalue2'".")>".$varvalue4."</a>";
//											out.print ("<a href = '#' id = 'ajaxlink" + i + "' onmouseover = \"_get('" + varvalue3
//													+ "','" + varvalue2 + "','" + varvalue1 + "','" + varvalue4 + "','" + i +"')\">" +varvalue4 + "</a>");
//
//											out.print("<ul>");
//
//											String str_query5 = "SELECT DISTINCT filelink FROM part, sheetdraft, semester, course,lecturer,typ WHERE course.id =  sheetdraft.ver_id AND semester = '" + varvalue3 + "' AND course = '" +
//													varvalue2 +"' AND  sheetdraft.lecturer_id = lecturer.id AND semester.id =  sheetdraft.sem_id AND typ.id =  sheetdraft.typ_id AND name = '"+ varvalue1 + "' AND type = '" + varvalue4 + "';";
//													ResultSet res5 = statement5.executeQuery(str_query5);
//													j = i;
//											while(res5.next()) {
//												j++;
//												out.print("<li>");
//												String varvalue5 = res5.getString("filelink");
//												out.print ("<a href = '#' id = 'ajaxlink0" + j + "' onmouseover = \"get_parameter('" + varvalue3
//														+ "','" + varvalue2 + "','" + varvalue1 + "','" + varvalue4 + "','" + varvalue5 + "','" + j +"')\">" +varvalue5 + "</a>");
//
//												out.print("</li>");
//												i=j;
//											}
//											out.print("</ul>");
//										out.print("</li>");
//									}
//									out.print("</ul>");
//
//
//
//								out.print("</li>");
//
//							}
//							out.print("</ul>");
//
//
//						out.print("</li>");
//					}
//					out.print("</ul>");
//
//
//				out.print("</li>");
//			}
//			out.print("</ul>");
//			out.print("</div>");
//	}catch (SQLException e) {
//		e.printStackTrace();
//	}
//
//
//}
//
//	public static void create_ver_view(HttpServletRequest req,HttpServletResponse resp) throws IOException {
//	PrintWriter out =  resp.getWriter();
//
//		Statement statement1 = null;
//		Statement statement2 = null;
//		Statement statement3 = null;
//		Statement statement4 = null;
//		Statement statement5 = null;
//		MysqlHelper mh = new MysqlHelper();
//		Connection con = mh.establishConnection(resp);
//
//
//
//
//		int i = 0;		//identifizierer fuer Blatt Link
//		int j = 0;		//identifizierer fuer aufagben Link
//		try {
//			statement1 = con.createStatement();
//
//			String str_query1 = "SELECT course FROM course ORDER BY course ASC";
//			ResultSet res1 = statement1.executeQuery(str_query1);
//
//			out.print("<div style='float: left;width:200px;'>");
//			out.print ("<ul id='red' class='treeview'>");
//			//----------------Veranstaltung --------------------
//			while(res1.next()) {
//				statement2 = con.createStatement();
//				out.print("<li>");
//
//
//					out.print("<span>" + res1.getString("course") + "</span>");
//					out.print("<ul>");
//
//
//				String varvalue1 = res1.getString("course");
//				String str_query2 = "SELECT DISTINCT name FROM  sheetdraft, course,lecturer WHERE course.id =  sheetdraft.ver_id AND course = '" + varvalue1 + "' AND lecturer.id =  sheetdraft.lecturer_id ORDER BY name";
//
//				ResultSet res2 = statement2.executeQuery(str_query2);
//					//-------------------Dozent ---------------
//					while (res2.next()) {
//						statement3 = con.createStatement();
//						out.print("<li>");
//
//							String varvalue2 = res2.getString("name");
//							out.print("<span>" + varvalue2 + "</span>");
//							out.print("<ul>");
//
//
//							String str_query3 = "SELECT DISTINCT semester FROM  sheetdraft, semester, course,lecturer WHERE course.id =  sheetdraft.ver_id AND name = '" + varvalue2 + "' AND course = '" + varvalue1 + "' AND  sheetdraft.lecturer_id = lecturer.id AND semester.id =  sheetdraft.sem_id";
//							ResultSet res3 = statement3.executeQuery(str_query3);
//							//--------------------Semester -----------------------
//							while (res3.next()) {
//								statement4 = con.createStatement();
//								out.print("<li>");
//								String varvalue3 = res3.getString("semester");
//								out.print("<span>" + varvalue3 +"</span>");
//								out.print("<ul>");
//
//									String str_query4 = "SELECT DISTINCT type FROM  sheetdraft, semester, course,lecturer,typ WHERE course.id =  sheetdraft.ver_id AND name = '" + varvalue2 + "' AND course = '" + varvalue1 + "' AND  sheetdraft.lecturer_id = lecturer.id AND semester.id =  sheetdraft.sem_id AND typ.id =  sheetdraft.typ_id AND semester = '" + varvalue3 + "'";
//									ResultSet res4 = statement4.executeQuery(str_query4);
//									//--------------------TYP -----------------------
//									//res4.beforeFirst();
//									while (res4.next()) {
//										statement5 = con.createStatement();
//										out.print("<li>");
//										i++;
//										String varvalue4 = res4.getString("type");
//											//echo "<a href = '#' id = 'ajaxloadlink' onclick = ret_str('$varvalue4')>".$varvalue4."</a>";
//											//echo "<a href = '#' id = 'ajaxlink' onmouseover = _get("."'$varvalue2'".")>".$varvalue4."</a>";
//											out.print ("<a href = '#' id = 'ajaxlink" + i + "' onmouseover = \"_get('" + varvalue3
//													+ "','" + varvalue1 + "','" + varvalue2 + "','" + varvalue4 + "','" + i +"')\">" +varvalue4 + "</a>");
//
//											out.print("<ul>");
//
//											String str_query5 = "SELECT DISTINCT filelink FROM part, sheetdraft, semester, course,lecturer,typ WHERE course.id =  sheetdraft.ver_id AND semester = '" + varvalue3 + "' AND course = '" +
//													varvalue1 +"' AND  sheetdraft.lecturer_id = lecturer.id AND semester.id =  sheetdraft.sem_id AND typ.id =  sheetdraft.typ_id AND name = '"+ varvalue2 + "' AND type = '" + varvalue4 + "';";
//													ResultSet res5 = statement5.executeQuery(str_query5);
//													j = i;
//											while(res5.next()) {
//												j++;
//												out.print("<li>");
//												String varvalue5 = res5.getString("filelink");
//												out.print ("<a href = '#' id = 'ajaxlink0" + j + "' onmouseover = \"get_parameter('" + varvalue3
//														+ "','" + varvalue1 + "','" + varvalue2 + "','" + varvalue4 + "','" + varvalue5 + "','" + j +"')\">" +varvalue5 + "</a>");
//
//												out.print("</li>");
//												i=j;
//											}
//											out.print("</ul>");
//										out.print("</li>");
//									}
//									out.print("</ul>");
//
//
//
//								out.print("</li>");
//
//							}
//							out.print("</ul>");
//
//
//						out.print("</li>");
//					}
//					out.print("</ul>");
//
//
//				out.print("</li>");
//			}
//			out.print("</ul>");
//			out.print("</div>");
//	}catch (SQLException e) {
//		e.printStackTrace();
//	}
//}

	/**
	 * A dynamic function that replaces all static tree view creators.
	 *
	 * @param request HTTP request with the correct parameters set.
	 * @param response HTTP response providing a writer.
	 * @throws IOException
	 */
	public static void createTreeView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// prep
		boolean debug =
		request.getParameter("debug") != null && request.getParameter("debug") != "";
		String varname_chosen = request.getParameter("ansicht");
		String treeviewnum = request.getParameter("treeviewnum");/*could be null=>null treeview default*/
//		if (resp.getBufferSize() < 1023) {
//			resp.setBufferSize();
			/*only determines when data buffer gets flush.
			a higher value then results in fewer flushes only */
//		}
		// Get context writer
		PrintWriter out = response.getWriter();
		// JavaScript dependencies may conflict with the one included in the HTML header.*/
//		out.print("<!-- Furthermore -->"
//				+"<script type='text/javascript' src='jquery/fairytale.js'></script>");

		// Statically called
		if (varname_chosen == null) {
			/*out.print("<div>No main column selected for building tree view.</div>"
					+ "<a href='?ansicht=course'>&rarr; Try this link for an example. &larr;</a>");
			out.print("<div class='info'>Now  checking whether method was called statically." +
					" If so, then an attribute varname_chosen must have been set.</div>");
			*/
			if (request.getAttribute("varname_chosen") != null) {
				varname_chosen = (String) request.getAttribute("varname_chosen");
			}
		}

		// Still not available?
		if (varname_chosen == null) {
			out.print("<div>No main column selected for building tree view.</div>"
					+ "<a href='?ansicht=course'>&rarr; Try this link for an example. &larr;</a>");
			out.print("<div class='nosuccess'>Aborting ...</div>");
			return ;
		}

		//PrintStream out = ((PrintStream)out_);

		String varname1; // = varname_chosen
		String varvalue1;
		String varname2;
		String varvalue2;
		String varname3;
		String varvalue3;
		String varname4;
		String varvalue4;
		String varname5;
		String varvalue5_sheetdraft_filelink;
		// Remap varvalue1...varvalue5 according to the chosenVarPair:
		if (varname_chosen.equals("semester")) {
			varname1 = varname_chosen;
			varname2 = "course";
			varname3 = "lecturer";
			varname4 = "type";
		}
		else if (varname_chosen.equals("course")) {
			varname1 = varname_chosen;
			varname2 = "lecturer";
			varname3 = "semester";
			varname4 = "type";
		}
		else if (varname_chosen.equals("lecturer")) {
			varname1 = varname_chosen;
			varname2 = "course";
			varname3 = "semester";
			varname4 = "type";
		}
		else {//TYPE
			varname1 = varname_chosen;
			varname2 = "course";
			varname3 = "lecturer";
			varname4 = "semester";
		}
		varname5 = "sheetdraft.filelink";


		Map<String, String> varvaluesMap = new HashMap<String, String>();

		// Several static method rewritten into one dynamic one.
		Statement statement1 = null;
		Statement statement2 = null;
		Statement statement3 = null;
		Statement statement4 = null;
		Statement statement5 = null;
		//Connection con = MySQLConnection.getInstance().getConnection();
		Connection con = new MysqlHelper().establishConnection(null);

		if (con == null) {
			// No effect if this file is accessed via ajax js request!
			Global.addMessage("SQL_Methods: Connection to Database failed.", "danger");
			return;
		}
		else {
			Global.addMessage("SQL_Methods: Connection to Database established successfully.", "success");
		}

		ResultSet res1 = null;
		int i = 0;
		try {
			String semester;
			String course;
			String lecturer;
			String type;

			// Get all values for varname_chosen from database:
			statement1 = con.createStatement();
			String query1 = "SELECT DISTINCT `" + varname_chosen + "`"
					+ " FROM `sheetdraft`, `lecturer`"
					+ " ORDER BY `" + varname_chosen + "` ASC";
			statement1.execute(query1
			);
			res1 = statement1.getResultSet();
			// Get length
			int res1L = 0;
			if (res1 != null && res1.last() /*&& res.getType() == res.TYPE_SCROLL_SENSITIVE*/) {
				res1L = res1.getRow();
				res1.beforeFirst();// because afterwards follows a next()
			}
			// Generate the option fields
			if (debug) {
				out.println("<br />Result size: "+ res1L + " rows.");
			}
			//----------------Semester --------------------
			if (res1L == 0) {
				//out.print("<li><ul><li><ul><li><ul><li><ul><li><ul><li></li></ul></li></ul></li></ul></li></ul></li>");
			} else
			{

			//out.print("<div style='float: left;width:200px;'>");
			out.print ("<ul id='becometreeview"+ treeviewnum +"' class='treeview'>");

			while(res1.next()) {
				statement2 = con.createStatement();


				varvalue1 = res1.getString(varname_chosen);
				//save it for dynamic use (to determine the value to one special key at all times)
				varvaluesMap.put(varname1, varvalue1);

				String query2 = "SELECT DISTINCT " + varname2
						+ " FROM  sheetdraft, lecturer" /*list here all to be possibly joined tables, always!*/
						+ " WHERE "+ varname_chosen +" = '"+ varvalue1 + "'"
						//cover here all join actions - dynamically
						+ ((varname_chosen.equals("lecturer") || varname2.equals("lecturer")) ? " AND sheetdraft.lecturer_id = lecturer.id" : "");
				if (debug) {
					out.println("<br /><br />Query 2: " + query2);
				}
				ResultSet res2 = statement2.executeQuery(
						query2
				);
				// get length
				int res2L = 0;
				if (res2 != null && res2.last() /*&& res.getType() == res.TYPE_SCROLL_SENSITIVE*/) {
					res2L = res2.getRow();
					res2.beforeFirst(); // because afterwards follows a next()
				}
				// ------- course -------
				if (res2L == 0) {
					out.print(//"empty");
					"<li><span>" + Global.decodeUmlauts(res1.getString(varname_chosen)) + "</span> (no lecture found)<ul><li><span>no semester found</span><ul><li><a href=\"#\" id=\"\" onmouseover=\"\">no sheet type found</a><ul><li><a href=\"#\" id=\"\" onmouseover=\"\">no filelink found</a></li></ul></li></ul></li></ul></li>");
					//out.print("<li><ul><li><ul><li><ul><li><ul><li></li></ul></li></ul></li></ul></li></ul></li>");
				} else
				{
				out.print("<li>");


				out.print("<span>" + Global.decodeUmlauts(res1.getString(varname_chosen)) + "</span> (" + res2L + (res2L == 1 ? " item" : " items") + ")");
				out.print("<ul>");
				while (res2.next()) {

					statement3 = con.createStatement();
					out.print("<li>");

					varvalue2 = res2.getString(varname2);
					// save it for dynamic use (to determine the value to one special key at all times)
					varvaluesMap.put(varname2, varvalue2);

					out.print("<span>" + Global.decodeUmlauts(varvalue2) + "</span>");
					out.print("<ul>");

					String query3 = " SELECT DISTINCT " + varname3
							+ " FROM sheetdraft, lecturer" /*list here all to be possibly joined tables, always!*/
							+ " WHERE " + varname_chosen +" = '" + varvalue1 + "'"
							+ " AND " + varname2 + " = '" + varvalue2 + "'"
							// Cover here all join actions dynamically.
							// Only if the previous one was to be joined
							// on column criteria, then uncomment this
							//+ (varname1 == "lecturer" ? " AND lecturer.lecturer = '"+ varvalue1 + "'" : "")
							// Join on lecturer
							+ (varname1.equals("lecturer") || varname2.equals("lecturer") || varname3.equals("lecturer") ? " AND sheetdraft.lecturer_id = lecturer.id" : "")
							;
					if (debug) {
						out.print("<br /><br />Query 3: " + query3);
					}
					ResultSet res3 = statement3.executeQuery(
							query3
					);
					res3 = statement3.getResultSet();
					// get length
					int res3L = 0;
					if (res3 != null && res3.last() /*&& res.getType() == res.TYPE_SCROLL_SENSITIVE*/) {
						res3L = res3.getRow();
						res3.beforeFirst(); // because afterwards follows a next()
					}
					// ------ Lecturer -------
					if (res3L == 0) {
						//out.print("<li><ul><li><ul><li></li></ul></li></ul></li>");
					} else
					while (res3.next()) {

						statement4 = con.createStatement();
						out.print("<li>");

						varvalue3 = res3.getString(varname3);
						// save it for dynamic use (to determine the value
						// to one special key at all times)
						varvaluesMap.put(varname3, varvalue3);

						out.print("<span>" + Global.decodeUmlauts(varvalue3) +"</span>");
						out.print("<ul>");

						String str_query4 = "SELECT DISTINCT " + varname4
								+" FROM  sheetdraft, lecturer" // list here all to be possibly joined tables, always!
								+" WHERE " + varname_chosen + " = '" + varvalue1 + "'"
								+" AND " + varname3 + " = '" + varvalue3 +"'"
								// Cover here all join actions dynamically.
								// Only if the previous one was the to be joined on column criteria uncomment this:
								//+ (varname1 == "lecturer" ? " AND lecturer.lecturer = '"+ varvalue1 + "'" : "")
								//+ (varname2 == "lecturer" ? " AND lecturer.lecturer = '"+ varvalue2 + "'" : "")
								// Join on lecturer
								+ (varname1 == "lecturer" || varname2 == "lecturer" || varname3 == "lecturer" || varname4 == "lecturer" ? " AND sheetdraft.lecturer_id = lecturer.id" : "")
								;
						if (debug) {
							out.println("<br /><br />Query 4: " + str_query4);
						}
						ResultSet res4 = statement4.executeQuery(str_query4);
						// get length
						int res4L = 0;
						if (res4 != null && res4.last() /*&& res.getType() == res.TYPE_SCROLL_SENSITIVE*/) {
							res4L = res4.getRow();
							res4.beforeFirst(); // because afterwards follows a next()
						}
						// ------- TYP -------
						if (res4L == 0) {
							//out.print("<li><ul><li></li></ul></li>");
						} else
						while (res4.next()) {
							statement5 = con.createStatement();
							out.print("<li>");
							i++;

							varvalue4 = res4.getString(varname4);
							// Save it for dynamic use (to determine the value to one special key at all times)
							varvaluesMap.put(varname4, varvalue4);

							//echo "<a href = '#' id = 'ajaxloadlink' onclick = ret_str('$varvalue4')>".$varvalue4."</a>";
							//echo "<a href = '#' id = 'ajaxlink' onmouseover = _get("."'$varvalue2'".")>".$varvalue4."</a>";
//							out.println("<a href='#' id='ajaxlink" + i + "'"
//									+ " onmouseover=\"_get('" + varvalue5_sheetdraft_filelink + "'"
//									+ ",'" + varvalue1	+ "'"
//									+ ",'" + varvalue2 + "','" + varvalue3 + "','" + varvalue4
//									+ "','" + i +"')\">"
//									+ varvalue4
//									 + "</a>");// --> NOW AFTER LAST UL WORKAROUND!
							// BECAUSE THE VALUE varvalue5_sheetdraft_filelink IS
							// REQUIRED AND ONLY AVAILABLE AFTER LAST UL-WORKAROUND.

							varvalue5_sheetdraft_filelink = "no sheetdraft filelink determined yet";


							/* LAST UL -BEGIN */
							String lastUL = "<ul>";

							// THIS IS FIXED - not dynamic! - part listing in table.ajax.jsp!
							// Here: sheetdraft listing of filelinks.
							String str_query5 = "SELECT DISTINCT sheetdraft.filelink AS filelink"
									+ " FROM /*part,*/ sheetdraft, lecturer" // List here all to be possibly joined tables, always!
									+ " WHERE " + varname_chosen + " = '" + varvalue1 + "'"
									+ " AND " + varname2 + " = '" + varvalue2 +"'"
									+ " AND " + varname3 + " = '"+ varvalue3 + "'"
									+ " AND " + varname4 + " = '" + varvalue4 + "'"
									// Cover here all join actions - dynamically.
									// Only if the previous one was the to be joined on column criteria add this
									//+ (varname3 == "lecturer" ? " AND lecturer.lecturer = '"+ varvalue3 + "'" : "")
									//+ (varname4 == "lecturer" ? " AND lecturer.lecturer = '"+ varvalue4 + "'" : "")
									// join on lecturer
									+ (varname2 == "lecturer" || varname3 == "lecturer" || varname4 == "lecturer" || varname5 == "lecturer" ? " AND sheetdraft.lecturer_id = lecturer.id" : "")
									;
							if (debug) {
								lastUL += ("<br /><br />Query 5: " + str_query5);
							}
							ResultSet res5 = statement5.executeQuery(str_query5);
							int j = i;
							if (res5 == null) {
								System.err.println("treeview: SQL_Methods: Query 5 failed: " + str_query5);
							}
							else
							while(res5.next()) {

								lastUL += ("<li>");

								varvalue5_sheetdraft_filelink = res5.getString("filelink");
								// Save it for dynamic use (to determine the value
								// to one special key at all times)
								varvaluesMap.put(varname5, varvalue5_sheetdraft_filelink);

								lastUL += ("<a href='#' id='ajaxlink0" + j + "'"
										+ " rel='" + Global.convertToImageLink(varvalue5_sheetdraft_filelink) + "'"
										+ " class='screenshot'"
										+ " onclick = \"ajax_get_listing_parts("
											+ "'" + varvalue5_sheetdraft_filelink + "'"
											+ ", '" + varvaluesMap.get("lecturer") + "'"
											//+ varvalue1 + "','" + varvalue2 + "','" + varvalue3
											//+ "','" + varvalue4 + "','" + varvalue5
											+ ", '" + j +"'"
											// Target object:
											+ ", (this.nextElementSibling ? this.nextElementSibling.nextElementSibling : this.nextSibling ? this.nextSibling.nextSibling : undefined)"
											+ ", this"
										+ ");"
										// Hide the sheetdraft listing (also loaded via ajax and cached as of v31.13):
										+ "var o = document.getElementById('ajaxlink" + i + "_listing'); if (!o) return false; o.style.display='none';"
										// Prevent jumping around with target. TODO remove if target is used in a sensible way.
										+ "return false;"
										+ "\">"
										+ "<i class='icon-eye-open'></i> "
										+ varvalue5_sheetdraft_filelink + "</a>");
								lastUL += ("</li>");
								j++;
							}
							if (statement5 != null) {
								statement5.close();
							}
							if (res5 != null) {
								res5.close();
							}
							lastUL += ("</ul>"); /* LAST UL -END */



							// Remap varvalue1...varvalue5 according to the chosenVarPair:
							if (varname_chosen.equals("semester")) {
								semester = varvalue1;
								course = varvalue2;
								lecturer = varvalue3;
								type = varvalue4;
							}
							else if (varname_chosen.equals("course")) {
								course = varvalue1;
								lecturer = varvalue2;
								semester = varvalue3;
								type = varvalue4;
							}
							else if (varname_chosen.equals("lecturer")) {
								lecturer = varvalue1;
								course = varvalue2;
								semester = varvalue3;
								type = varvalue4;
							}
							else {//TYPE
								type = varvalue1;
								course = varvalue2;
								lecturer = varvalue3;
								semester = varvalue4;
							}
							// Because only now the varvalue5_sheetdraft_filelink is available!
							out.print("<a href='#' id='ajaxlink" + i + "'"
//									+ " onclick=\""//this helps against server abuse.
//										+ "ajax_showCacheOrLoad("
//											// Target object:
//											+ "(this.nextElementSibling ? this.nextElementSibling.nextElementSibling : this.nextSibling.nextSibling)"
//										+ ");"
//									+ " \" "
									+ " onclick=\"ajax_get_listing_sheetdrafts('" + varvalue5_sheetdraft_filelink + "'"
										+ ",'" + semester	+ "'"
										+ ",'" + course + "','" + lecturer + "','" + type
										+ "','" + i +"'"
										// Target object:
										+ ", (this.nextElementSibling ? this.nextElementSibling.nextElementSibling : this.nextSibling ? this.nextSibling.nextSibling : undefined)"
										+ ", this"
										+ ");"
										// Hide all the possibly shown parts listings (also loaded via ajax and cached as of v31.13):
										// They start with the same index as their mothersheet (j) with a preceding 0.
										+ "for (var o, j_ = " + i + "; j_ < " + j + "; j_++) {"
											+ "o = document.getElementById('ajaxlink0' + j_ + '_listing'); if (!o) continue/*return would cancel everything.*/; o.style.display='none';"
										+ "}"
										+ "/*event-propagation to treeview to unfold it too:*/this.previousSibling.click();"
										// Prevent jumping around with target. TODO remove if target is used in a sensible way.
										+ "return false;"
									+ "\">"
									+ Global.decodeUmlauts(Global.display(varvalue4))/*--- -> /*/
									+ "</a>"

									+ "<script type='text/javascript'>//<!--"
									+ "addEvent('ajaxlink" + i + "', 'onclick', new )"
									+ "//--></script>");

							// Then print the stored lastUL element. ("last ul workaround")
							out.print(lastUL);



							out.print("</li>");

						}
						if (statement4 != null) {
							statement4.close();
						}
						if (res4 != null) {
							res4.close();
						}
						out.print("</ul>");



						out.print("</li>");
					}
					if (statement3 != null) {
						statement3.close();
					}
					if (res3 != null) {
						res3.close();
					}
					out.print("</ul>");


					out.print("</li>");
				}
				if (statement2 != null) {
					statement2.close();
				}
				if (res2 != null) {
					res2.close();
				}
				out.print("</ul>");


				out.print("</li>");
				}//else -END --required because of a javascript bug that refills treeview
				// Edit: not even that helps ... it is really opaque what treeview is doing here!
			}
			out.print("</ul>");
			//out.print("</div>");//WRAPPER END
			}//else res1 not required to be empty -END

		} catch (SQLException e) {
			e.printStackTrace();
			out.print("<br />\n\rSQL ERROR occurred: " + e.getMessage()
					+ "<br />\n\rSQLState: " + e.getSQLState()
					+ "<br />\n\rStackTrace: " + e.getStackTrace().toString()
					+ e.toString()
			);
		}
		finally {
			try {
				if (res1 != null) {
					res1.close();
				}
				if (statement1 != null) {
					statement1.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}










//	public static void main(String[] args) throws SQLException {
//		MySQLConnection c = MySQLConnection.getInstance();
//		SQL_Methods sqlm = new SQL_Methods();
//		sqlm.setConn(c.getConnection());
////		HashMap<String, String> column_value = new HashMap<String,String>();
////		column_value.put("semester", "SoSe 2010");
////		//column_value.put("ver_id", "2");
////		//sqlm.is_duplicate(" sheetdraft", column_value);
////		System.out.println(sqlm.is_duplicate("semester","semester",column_value));
//
//
//		System.out.println(sqlm.getId("course", "course", "Mathe"));
//	}
//	public static void main(String[] args) throws IOException {
//		//SQL_Methods.create_sem_view_deb();
////		String st= "uploads/WiSe2012/Mathe/Weigel/Klausur/Uebung1.pdf";
////		String [] temp = st.split("\\.");
////		System.out.println(temp[1]);
//	}


}
