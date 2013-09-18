package swp;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class MySQLConnection {
 
    private static MySQLConnection instance = null;
    private Connection conn = null;
 
    // Hostname
    private static String dbHost = "localhost";
 
    // Port -- Standard: 3306
    private static String dbPort = "3306";
 
    // Datenbankname
    private static String database = "aufgaben_db";
 
    // Datenbankuser
    private static String dbUser = "root";
 
    // Datenbankpasswort
    private static String dbPassword = "a)t5qTU[";
 
    private MySQLConnection(){

        try {
 
            // Datenbanktreiber fuer ODBC Schnittstellen laden.
            // Fuer verschiedene ODBC-Datenbanken muss dieser Treiber
            // nur einmal geladen werden.
            Class.forName("com.mysql.jdbc.Driver");
 
            // Verbindung zur ODBC-Datenbank 'sakila' herstellen.
            // Es wird die JDBC-ODBC-Br�cke verwendet.
            conn = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":"
                    + dbPort + "/" + database + "?" + "user=" + dbUser + "&"
                    + "password=" + dbPassword);
        } catch (ClassNotFoundException e) {
            System.out.println("Treiber nicht gefunden");
        } catch (SQLException e) {
            System.out.println("Connect nicht moeglich");
        }
    }
 
    public static MySQLConnection getInstance()
    {
        if(instance == null)
            instance = new MySQLConnection();
        return instance;
    }
 
    /**
     * Liefert den Nachname, Vornamen
     */
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
 
                // Ergebniss�tze durchfahren.
                while (result.next()) {
                    String first_name = result.getString("bl_bez"); // Alternativ: result.getString(1);
                    //String last_name = result.getString("last_name"); // Alternativ: result.getString(2);
                    name += first_name + " ";
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