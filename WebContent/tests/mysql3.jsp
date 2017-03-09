<%@ page import="java.io.IOException,
 java.io.PrintWriter,
 java.sql.Connection,
 java.sql.DriverManager,
 java.sql.ResultSet,
 java.sql.SQLException,
 java.sql.Statement"%>


<%
    out.println("MySQL Connect Example.");
    Connection conn = null;
    String url = "jdbc:mysql://localhost:3306/";
    String dbName = "swp";
    String driver = "com.mysql.jdbc.Driver";
    String userName = "root";
    String password = "a)t5qTU[";
    try {
    Class.forName(driver).newInstance();
    conn = DriverManager.getConnection(url+dbName,userName,password);
    out.println("Connected to the database</br>");




    Statement query = conn.createStatement();
    String sql = "INSERT INTO semester (id ,sem_bez)VALUES (NULL,'blaaa');";
    query.executeUpdate(sql);



    conn.close();
    out.println("Disconnected from database");
    } catch (Exception e) {
    e.printStackTrace();
    }
%>
