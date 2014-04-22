package db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.http.HttpServletResponse;

import aufgaben_db.Global;


public class MysqlHelper {
	
	/* TODO: Make this handle multiple instances (e.g. MySQL, SQLite, UnQLite, ..) simultaneously
	 *       to allow for copying from one database (mostly MySQL) to another (e.g. SQLite) for 
	 *       better automatic synchronisation for the local downloadable version.
	 *       To download are:
	 *       1) Program container --> tomcat. Start via one of the $CATALINA_HOME/bin/* scripts.
	 *       2) Program. (jars, Java, wild mix).
	 *       3) Filesystem,
	 *       4) and database (for relations of exercises towards their sheets).
	*/
	private Connection connMySQL = null;
	private Connection connSQLite = null;
	private Connection conn = null; //<- the currently active one.

	
	private static String host = "localhost";//<--@Override by sub class.
	private final String port = "3306";//<--@Override by sub class. Default value: 3306 .
	private String engine = "MySQL";
	private String typeToTryFirst = "MySQL";//<-- higher precedence, may be changed on establishing connection.
	private String driver = "com.mysql.jdbc.Driver";//<--last used driver.
	//final String url = "jdbc:mysql://localhost:3306/";
	private final String db = "aufgaben_db";
	private final String user = "root";    /*annotations by JRIBW to LOCAL:*/
    private final String password = "a)t5qTU[";//cmd: mysqladmin -uroot -psample password "<secret>"
    //for localhost: SET PASSWORD FOR root@'localhost' = PASSWORD('a)t5qTU[');
    //for eclipse not conflicting with the usual os tomcat service. 
    //sudo /usr/local/tomcat/bin/shutdown.sh
    //#importing an sql db to a local db
    //eventually drop all filled tables prior to the import (as it results in doubles else)
    //mysql -u root -p aufgaben_db < aufgaben_db_11_june_13.sql
    
	public String sqlite_db_filelink;
    
    public MysqlHelper() {
    	
    }
    
    public Connection connectToMySQL() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	// Load database engine using the current class loader:
    	engine = "MySQL";
    	driver = "com.mysql.jdbc.Driver";
	    Class.forName(driver);//.newInstance();
	    
	    // create a database connection:
	    connMySQL = DriverManager.getConnection(
	    		"jdbc:" + engine.toLowerCase() + "://" + host + ":" + port + "/" /* + url */+ db
	    		, user, password
		);
	    
	    //out.println("Connected to the database.</br>");
	    return connMySQL;
    }
    public Connection connectToSQLite() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	// Load database engine using the current class loader:
    	engine = "SQLite";
    	driver = "org.sqlite.JDBC";
		Class.forName(driver);//.newInstance();

		// create a database connection:
		sqlite_db_filelink = Global.root + File.separator + Global.uploadTarget + db + ".sqlite";
		connSQLite = DriverManager.getConnection("jdbc:sqlite:" + sqlite_db_filelink /*.db*/);/*if absolute filelinks 
        		are used it has to be differentiated between the different operating 
        		systems, e.g. Windows uses ; and UNIX systems use : as delimiter!
        		*/
		
		//out.println("Connected to the database.</br>");
		
		
		// 1) IF NOT TABLE EXISTS => CREATE THOSE.
		//SQLite: SELECT name FROM sqlite_master WHERE type='table' AND name='table_name';
		String table = "sheetdraft";
		String sql = "CREATE TABLE IF NOT EXISTS `" + table + "` ("
//SQLite has ROWID				id            | int(11)      | NO   | PRI | NULL           | auto_increment |
				+ " filelink VARCHAR(200) UNIQUE NULL,"
				+ " type VARCHAR(18) NULL,"
				+ " course VARCHAR(50) NOT NULL,"
				+ " semester VARCHAR(18) NULL,"
				+ " lecturer_id INT(20) NOT NULL,"
				+ " description VARCHAR(100) NULL,"
				+ " author VARCHAR(35) NULL,"
				+ " whencreated INT(15) NULL,"
				+ " is_draft INT(1) NULL DEFAULT 0,"
				+ " headermixture TEXT,"
				+ "	whenchanged INT(15),"
				+ " plain_text TEXT"
				+ ")";
		
		java.sql.Statement sttmnt = connSQLite.createStatement();
		sttmnt.execute(sql);
		
		
		
		table = "exercise";
		sql = "CREATE TABLE IF NOT EXISTS `" + table + "` ("
				+ " sheetdraft_filelink VARCHAR(200) NOT NULL," 
				+ " filelink VARCHAR(250) UNIQUE NOT NULL ON CONFLICT FAIL,"//<-- continues on failures
				+ " originsheetdraft_filelink VARCHAR(200) NOT NULL,"
				+ " splitby VARCHAR(50) NOT NULL,"
				+ " header TEXT,"
				+ " footer TEXT,"
				+ " is_native_format INT(1) NULL,"
				+ " whencreated INT(15) NOT NULL,"
				+ " whenchanged INT(15) NULL"
				+ ")";
		//ADD UNIQUE INDEX IF NOT EXISTS filelink_splitby ON exercise(filelink, splitby);
		sttmnt.execute(sql);
		
		
		
		table = "lecturer";
		sql = "CREATE TABLE IF NOT EXISTS `" + table + "` ("
//ROWID				+ " id INT(20) UNIQUE NULL,"//ADD INDEX id
				+ " lecturer VARCHAR(50) NOT NULL,"
				+ " title VARCHAR(5) NULL,"
				+ " whencreated INT(15) NULL"
				+ ")";
	
		sttmnt.execute(sql);

		
	
		table = "draftexerciseassignment";
		sql = "CREATE TABLE IF NOT EXISTS `" + table + "` ("
				+ " sheetdraft_filelink VARCHAR(130) NOT NULL,"
				+ " position INT(2) NOT NULL DEFAULT 0,"
				+ " exercise_filelink VARCHAR(200) NOT NULL"
				+ ")";
	
		sttmnt.execute(sql);
		
//		sql = "CREATE UNIQUE INDEX IF NOT EXISTS sheetdraft_filelink__exercise_filelink ON draftexerciseassignment(sheetdraft_filelink, exercise_filelink)";
//		sttmnt.execute(sql);
		
		
		
		return connSQLite;
    }
    
    
	public Connection establishConnection(HttpServletResponse response) throws IOException {
		this.typeToTryFirst = "MySQL";
		return establishConnection(response, "MySQL");
	}	
	public Connection establishConnection (HttpServletResponse response, String typeToTryFirst) throws IOException {
		this.typeToTryFirst = typeToTryFirst;
		//PrintWriter out = response.getWriter();
		
		/* As the MySQL database engine is more suited for simultaneous access by multiple threads
    	   we start with a mysql database.
    	   If we are on a local system without a MySQL installation and configuration or any other
    	   error then we fall back to the SQLite database that ships with our application (AVSy).
    	 */
    	try {
	 		typeToTryFirst = "sqlite";
    		if (typeToTryFirst == null || typeToTryFirst.equalsIgnoreCase("mysql")) {
    			return connectToMySQL();
    		}
    		else {
    			return connectToSQLite();
    		}
			
		}
    	catch (Exception e) {
    		// error reporting:
	    	if (e instanceof ClassNotFoundException) {
	    		System.out.println("Treiber nicht gefunden");
	    	}
	    	else if (e instanceof SQLException) {
	    		System.out.println("Connect nicht moeglich");
				//out.println("no conn</br>");
				e.printStackTrace();
				if (connSQLite != null) {
					try {
						connSQLite.close();
					}
					catch (Exception sql_e) {
						e.printStackTrace();
					}
				}
				if (connMySQL != null) {
					try {
						connMySQL.close();
					}
					catch (Exception sql_e) {
						e.printStackTrace();
					}
				}
	    	}
			/* 	fallback to the other (by default: SQLite) database for local usage of this program (AVSy) as 
	    		requested by Prof. Frank Puppe:
	    	*/
        	try {
        		//ATTENTION: Here the order is switched if compared to the above (i.e. our first efforts)!
        		if (typeToTryFirst == null || typeToTryFirst.equalsIgnoreCase("mysql")) {
        			return connectToSQLite();
        		}
        		else {
        			return connectToMySQL();
        		}
        		
        	}
        	catch (ClassNotFoundException ex) {
        		// if the error message is "out of memory", 
        	    // it probably means no database file is found
        		System.err.println(ex.getMessage());
        	}
    		catch (SQLException ex) {
        		System.err.println(ex);
    		} catch (InstantiationException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
    		finally {
//        		try {
//            	  	if(conn != null)
//            	  		conn.close();
//              	}
//              	catch(SQLException ex) {
//            	  	// connection close failed.
//            	  	System.err.println(ex);
//              	}
            }
		}
    	// close the connection:
//    	finally {
//    		try {
//    			if(conn != null) {
//    				conn.close();
//    			}
//    		}
//			catch(SQLException e) {
//				// closing connection failed.
//			    System.err.println(e);
//			}
//        }
    	
    	// okay, there was no chance for any connection for now.
    	return null;
	}

 
	/**
	 * @param engine - the engine to choose. (if not supported => null)
	 * @return Connection - if no connection then null else first by default MySQL if unequal null,
	 * 		 otherwise SQLite-connection. (with parameter &lt;typeToTryFirst&gt; precedence may be reversed.) 
	 */
	public Connection getConnection() {
		if (this.typeToTryFirst == null || this.typeToTryFirst.equalsIgnoreCase("MySQL")) {
			return (connMySQL != null ? connMySQL : connSQLite);
		}
		return (connSQLite != null ? connSQLite : connMySQL); 
	}
	
    public Connection getConnection(String engine) {
    	if (engine == null) {
    		return null;
    	}
    	if (engine.toLowerCase().contains("sqlite")) {
    		return connSQLite;
    	}
    	else if (engine.toLowerCase().contains("mysql")) {
    		return connMySQL;
    	}
    	return null;
    }
    
}
