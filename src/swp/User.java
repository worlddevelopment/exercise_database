package swp;
import java.util.Properties;

import javax.naming.directory.DirContext;
import javax.naming.ldap.InitialLdapContext;

public class User {
	
	public String getName(LDAP ldap,String benutzername) {
		
		
		Properties prop = ldap.getProp();
		
		try {
			  
		     DirContext ctx = new InitialLdapContext(prop, null);

		     return ldap.getSearchResult(ctx, "cn=" + benutzername, "ou=accounts,o=uni-wuerzburg");
		 }
		 catch (Exception e) {
		    return "";
		 }
	}
}
