<%@ page import="swp.*,javax.naming.directory.DirContext,javax.naming.ldap.InitialLdapContext,java.util.Properties"%>

<%!


public class Log {
	private LDAP ldap;
	
	public Log(){
		ldap = new LDAP();
	}
	
	public LDAP getLdap() {
		return ldap;
	}


	public void setLdap(LDAP ldap) {
		this.ldap = ldap;
	}


	public boolean autanticate(String user,String password) {
		
		
		ldap.setProp(user, password);
		Properties env = ldap.getProp();
		 
		 
		 
		 try {
			  
		     DirContext ctx = new InitialLdapContext(env, null);

		     return true;
		 }
		 catch (Exception e) {
		    return false;
		 }
	}

}
%>

<%
User benutzer = new User();
Log l = new Log();
if(l.autanticate("s210507", "artjom84")) {
	LDAP ldap = l.getLdap();
	//out.print(benutzer.getName(ldap));
}
	%>
 