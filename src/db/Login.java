package db;

import javax.naming.directory.DirContext;

import javax.naming.ldap.InitialLdapContext;

import db.LDAP;

import java.util.Properties;

public class Login {
	private LDAP ldap;
	
	
	public LDAP getLdap() {
		return ldap;
	}
	
	public Login() {
		this.ldap = new LDAP();
	}

	public void setLdap(LDAP ldap) {
		this.ldap = ldap;
	}


	public boolean autanticate(String user,String password) {
		
		
		ldap.setProp(user, password);
		Properties env = ldap.getProp();
		 
		 
		 
		 try {
			  
		     DirContext ctx = new InitialLdapContext(env, null);
		     ldap.getSearchResult(ctx, "cn=" + user, "ou=accounts,o=uni-wuerzburg");
		     //einlogen falls Benutzer ist hiwi oder Dozent der Lehrstuhl der Didaktik <- now more general.
		     if(ldap.isHiwi() || ldap.getLehrstuhl() != null /*.equals("Lehrstuhl für Mathematik V") || ldap.getLehrstuhl().equals("L.f. Didaktik d. Mathematik")*/) {
		    	 return true;
		     }
		     else  {
		    	 return false;
		     }

		     
		 }
		 catch (Exception e) {
			 System.out.println("Error");
			 e.printStackTrace();
		    return false;
		 }
	}

}
