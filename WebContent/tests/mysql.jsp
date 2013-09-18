<%@page import="swp.MySQLConnection"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.PrintWriter"%>
<%@ page import="java.sql.Connection,java.sql.DriverManager,java.sql.ResultSet,java.sql.SQLException,
java.sql.Statement;"%>
<%!


public class MySQLConnection {
	 
    private MySQLConnection instance = null;
    private Connection conn = null;
 
    // Hostname
    private String dbHost = "localhost";
 
    // Port -- Standard: 3306
    private String dbPort = "3306";
 
    // Datenbankname
    private String database = "swp";
 
    // Datenbankuser
    private String dbUser = "root";
 
    // Datenbankpasswort
    private String dbPassword = "a)t5qTU";
 
    public  MySQLConnection(HttpServletResponse response) throws IOException {
    	PrintWriter out = response.getWriter();
        try {
 
            // Datenbanktreiber für ODBC Schnittstellen laden.
            // Für verschiedene ODBC-Datenbanken muss dieser Treiber
            // nur einmal geladen werden.
            Class.forName("com.mysql.jdbc.Driver");
 
            // Verbindung zur ODBC-Datenbank 'sakila' herstellen.
            // Es wird die JDBC-ODBC-Brücke verwendet.
            conn = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":"
                    + dbPort + "/" + database + "?" + "user=" + dbUser + "&"
                    + "password=" + dbPassword);
            
            out.print("erfolg");
        } catch (ClassNotFoundException e) {
            out.println("Treiber nicht gefunden");
        } catch (SQLException e) {
            out.println("Connect nicht moeglich");
        }
    }
 
    /* public  MySQLConnection getInstance()
    {
        if(instance == null)
            instance = new MySQLConnection();
        return instance;
    }
  */
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
 
                // Ergebnissätze durchfahren.
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
}
%>

<%
MySQLConnection m = new MySQLConnection(response);

%>