package swp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.http.HttpServletResponse;


public class MysqlHelper {
	
	
	private Connection conn = null;
	final String url = "jdbc:mysql://localhost:3306/";
    final String dbName = "aufgaben_db";
    final String driver = "com.mysql.jdbc.Driver";
    final String userName = "root";    /*annotations by JRIBW to LOCAL:*/
    final String password = "a)t5qTU[";//cmd: mysqladmin -uroot -psample password "<secret>"
    //for localhost: SET PASSWORD FOR root@'localhost' = PASSWORD('a)t5qTU[');
    //for eclipse not conflicting with the usual os tomcat service. 
    //sudo /usr/local/tomcat/bin/shutdown.sh
    //#importing an sql db to a local db
    //eventually drop all filled tables prior to the import (as it results in doubles else)
    //mysql -u root -p aufgaben_db < aufgaben_db_11_june_13.sql
	public Connection establishConnection (HttpServletResponse response) throws IOException {
		//PrintWriter out = response.getWriter();
		
		try {
			
		    Class.forName(driver).newInstance();
		    this.conn = DriverManager.getConnection(url+dbName,userName,password);
		    //out.println("Connected to the database.</br>");
		    return this.conn;
			
		} 
			catch (Exception e) {
				//out.println("no con</br>");
		    e.printStackTrace();
		    return null;
	    }
	}

}
