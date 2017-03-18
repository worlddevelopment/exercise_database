package db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.http.HttpServletResponse;

import aufgaben_db.Global;


public class MysqlHelper {

	// ======= ATTRIBUTES
	/* TODO: Make this handle multiple instances (e.g. MySQL, SQLite,
	 * UnQLite, ..) simultaneously to allow for copying from one
	 * database (mostly MySQL) to another (e.g. SQLite) for better
	 * automatic synchronisation for the local downloadable version.
	 * To download are:
	 * 1) Program container: Tomcat. To start it see $CATALINA_HOME/bin/*
	 * 2) Program. (jars, Java, JSP, wild mix).
	 * 3) Filesystem,
	 * 4) and database (for relations of exercises towards their sheets).
	*/
	private Connection connMySQL = null;

	private Connection connSQLite = null;

	private Connection conn = null; //<- the currently active one.

	@Override
	private static String host = "localhost";

	@OVerride
	private final String port = "3306";

	private String engine = "MySQL";

	// Higher precedence, may be changed on establishing connection.
	private String typeToTryFirst = "MySQL";

	private String driver = "com.mysql.jdbc.Driver";//<--last used driver.

	//final String url = "jdbc:mysql://localhost:3306/";
	//
	private final String db = "aufgaben_db";

	private final String user = "root";

	private final String password = "a)t5qTU[";
	// LOCAL:
	//mysqladmin -uroot -psample password "<secret>"
	// For localhost:
	//SET PASSWORD FOR root@'localhost' = PASSWORD('a)t5qTU[');
	// For eclipse not conflicting with the usual os tomcat service:
	//sudo /usr/local/tomcat/bin/shutdown.sh
	// How to import an sql DB file to a local DB:
	// (May need to drop all tables prior to import to prevent doubles.)
	//mysql -u root -p aufgaben_db < aufgaben_db_11_june_13.sql

	public String sqlite_db_filelink;




	// ======= CONSTRUCTOR
	/**
	 * Constructor
	 */
	public MysqlHelper() {

	}




	// ======= METHODS
	/**
	 * Connect to a MYSQL database.
	 *
	 * @return the established connection or null.
	 */
	public Connection connectToMySQL() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		// Load database engine using the current class loader:
		engine = "MySQL";
		driver = "com.mysql.jdbc.Driver";
		Class.forName(driver);//.newInstance();

		// create a database connection:
		connMySQL = DriverManager.getConnection(
				"jdbc:" + engine.toLowerCase() + "://" + host + ":"
				+ port + "/" /* + url */+ db
				, user, password
		);

		//out.println("Connected to the database.</br>");
		return connMySQL;
	}



	/**
	 * Connect to an SQLite database.
	 *
	 * @return the established connection or null.
	 */
	public Connection connectToSQLite() throws InstantiationException
		, IllegalAccessException, ClassNotFoundException, SQLException {

		// Load database engine using the current class loader:
		engine = "SQLite";
		driver = "org.sqlite.JDBC";
		Class.forName(driver);//.newInstance();

		// Create a database connection:
		sqlite_db_filelink = Global.root + File.separator
			+ Global.uploadTarget + db + ".sqlite";
		connSQLite = DriverManager
			.getConnection("jdbc:sqlite:" + sqlite_db_filelink /*.db*/);
		// If absolute filelinks are used then it has to be
		// differentiated between the different operating systems.
		// Exempli gratia Windows systems use ; and UNIX : as delimiter!

		//out.println("Connected to the database.</br>");


		// 1) IF NOT TABLE EXISTS => CREATE THOSE.
		// SQLite:	SELECT name FROM sqlite_master
		//				WHERE type='table' AND name='table_name';
		String table = "sheetdraft";
		String sql = "CREATE TABLE IF NOT EXISTS `" + table + "` ("
//SQLite has ROWID	id	| int(11)	| NO | PRI | NULL | auto_increment |
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
				+ " whenchanged INT(15),"
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

//		sql = "CREATE UNIQUE INDEX IF NOT EXISTS"
//			+ " sheetdraft_filelink__exercise_filelink"
//			+ " ON draftexerciseassignment(sheetdraft_filelink"
//			+ ", exercise_filelink)";
//		sttmnt.execute(sql);


		return connSQLite;
	}



	/**
	 * Establish a connection.
	 *
	 * @param response An HTTP response.
	 * @return The connection handle or null
	 */
	public Connection establishConnection(HttpServletResponse response)
		throws IOException {

		this.typeToTryFirst = "MySQL";
		return establishConnection(response, "MySQL");
	}



	/**
	 * Establish a connection.
	 *
	 * @param response An HTTP response.
	 * @param typeToTryFirst A supported DB type like SQLite or MySQL
	 * @return The connection handle or null
	 */
	public Connection establishConnection (HttpServletResponse response
			, String typeToTryFirst) throws IOException {

		this.typeToTryFirst = typeToTryFirst;
		//PrintWriter out = response.getWriter();

		/*
		As the MySQL database engine is more suited for simultaneous
		access by multiple threads we start with a mysql database.
		If we are on a local system without a MySQL installation and
		configuration or any other error then we fall back to the
		SQLite database that ships with our application (AVSy).
		*/
		try {
			typeToTryFirst = "sqlite";
			if (typeToTryFirst == null
					|| typeToTryFirst.equalsIgnoreCase("mysql")) {
				return connectToMySQL();
			}
			else {
				return connectToSQLite();
			}

		}
		catch (Exception e) {
			// error reporting:
			if (e instanceof ClassNotFoundException) {
				System.out.println("Driver not found");
			}
			else if (e instanceof SQLException) {
				System.out.println("Connect not possible");
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
			/*
			Fallback to the other (by default SQLite) database for local
			usage of this program (EMSy) as requested by Prof. Puppe:
			*/
			try {
				// ATTENTION: Here the order is switched compared to
				// the above (i.e. our first efforts)!
				if (typeToTryFirst == null
						|| typeToTryFirst.equalsIgnoreCase("mysql")) {
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
//				try {
//					if(conn != null)
//						conn.close();
//				}
//				catch(SQLException ex) {
//					// connection close failed.
//					System.err.println(ex);
//				}
			}
		}
		// close the connection:
//		finally {
//			try {
//				if(conn != null) {
//					conn.close();
//				}
//			}
//			catch(SQLException e) {
//				// closing connection failed.
//				System.err.println(e);
//			}
//		}

		// okay, there was no chance for any connection for now.
		return null;
	}



	/**
	 * Get the currently active connection.
	 *
	 * @param engine The engine to choose. (if not supported => null)
	 * @return Connection or null: Tries MySQL before SQLite.
	 * With parameter &lt;typeToTryFirst&gt; precedence may be reversed.
	 */
	public Connection getConnection() {
		if (this.typeToTryFirst == null || this.typeToTryFirst.equalsIgnoreCase("MySQL")) {
			return (connMySQL != null ? connMySQL : connSQLite);
		}
		return (connSQLite != null ? connSQLite : connMySQL);
	}



	/**
	 * Get the currently active connection.
	 *
	 * @param engine The engine to choose. (if not supported => null)
	 * @return Connection or null: Tries MySQL before SQLite.
	 */
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



	/**
	 * Whether the currently active connection is SQLite.
	 *
	 * @return True if the current connection is of type SQLite.
	 */
	public boolean isConnSQLite() {
		return this.connSQLite != null;
	}

}
