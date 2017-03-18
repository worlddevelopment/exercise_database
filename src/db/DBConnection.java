package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	//TODO make this handle multiple instances (e.g. MySQL, SQLite, UnQLite, ..) simultaneously:
	protected static DBConnection instance = null;
	protected Connection conn = null;

	protected static String dbHost = "localhost";//<--@Override by sub class.

	// Port -- Standard: 3306
	protected static String dbPort = "3306";//<--@Override by sub class.

	protected static String database = "core";//<--possibly @Override by sub class.

	protected static String dbUser = "root";

	protected static String dbPassword = "a)t5qTU[";


	private DBConnection() {

		/* As the MySQL database engine is more suited for simultaneous access by multiple threads
		   we start with a mysql database.
		   If we are on a local system without a MySQL installation and configuration or any other
		   error then we fall back to the SQLite database that ships with our application (AVSy).
		 */
		try {

			// Load database engine using the current class loader:
			// Datenbanktreiber fuer ODBC Schnittstellen laden.
			// Fuer verschiedene ODBC-Datenbanken muss dieser Treiber
			// nur einmal geladen werden.
			Class.forName("com.mysql.jdbc.Driver");

			// Es wird die JDBC-ODBC-Bruecke verwendet.
			conn = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":"
					+ dbPort + "/" + database + "?" + "user=" + dbUser + "&"
					+ "password=" + dbPassword);

		} catch (Exception e) {

			if (e instanceof ClassNotFoundException) {
				System.out.println("Treiber nicht gefunden");
			}
			else if (e instanceof SQLException) {
				System.out.println("Connect nicht moeglich");
			}

			// fallback to SQLite database for local usage of this program (AVSy) as req. by Prof. Frank Puppe:
			try {
				Class.forName("org.sqlite.JDBC");

				// create a database connection
				conn = DriverManager.getConnection("jdbc:sqlite:" + database + ".db");/*if absolute filelinks
						are used it has to be differentiated between the different operating
						systems, e.g. Windows uses ; and UNIX systems use : as delimiter!
						*/

			}
			catch (ClassNotFoundException ex) {
				// if the error message is "out of memory",
				// it probably means no database file is found
				System.err.println(ex.getMessage());
			}
			catch (SQLException ex) {
				System.err.println(ex);
			}
			finally {
				try {
					if(conn != null)
						conn.close();
				}
				catch(SQLException ex) {
					// connection close failed.
					System.err.println(ex);
				}
			}
		}
		finally {
			try {
				if(conn != null)
				conn.close();
			}
			catch(SQLException e) {
				// closing connection failed.
				System.err.println(e);
			}
		}
	}


	public static DBConnection getInstance()
	{
		if(instance == null)
			return new DBConnection();/*overriden subclass equivalent is more specific and may return new MySQLConnection()*/
		return instance;
	}

	public String getName()
	{
		String name = "";
		if(conn != null)
		{
			// Anfrage-Statement erzeugen.
			Statement query;
			try {
				query = conn.createStatement();
				// Ergebnistabelle erzeugen und abholen.
				String sql = "SELECT bl_bez " + "FROM blatt "
						+ "ORDER BY bl_bez";
				ResultSet result = query.executeQuery(sql);

				// Ergebnissaetze durchfahren.
				while (result.next()) {
					name = result.getString("bl_bez"); // Alternativ: result.getString(1);
					System.out.println(name);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return name;
	}

	public Connection getConnection() {
		return conn;
	}

//   public static void main(String[] args) {
//	   MySQLConnection.getInstance().getName();
//}
}
