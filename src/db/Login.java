package db;

import javax.naming.directory.DirContext;
import javax.naming.ldap.InitialLdapContext;
import db.LDAP;
import java.util.Properties;


/**
 * Log on an instance of the LDAP system.
 */
public class Login {

	private LDAP ldap;



	/**
	 * Constructor to create, store an LDAP instance.
	 */
	public Login() {
		this.ldap = new LDAP();
	}



	/**
	 * Get the currently used LDAP instance.
	 *
	 * @return the currently used LDAP instance
	 */
	public LDAP getLdap() {
		return ldap;
	}



	/**
	 * Set an LDAP system instance.
	 *
	 * @param ldap The ldap system instance to set.
	 */
	public void setLdap(LDAP ldap) {
		this.ldap = ldap;
	}



	/**
	 * Authenticate
	 *
	 * @param user User name
	 * @param password User password
	 */
	public boolean autanticate(String user,String password) {

		ldap.setProp(user, password);
		Properties env = ldap.getProp();

		try {

			DirContext ctx = new InitialLdapContext(env, null);
			ldap.getSearchResult(ctx, "cn=" + user, "ou=accounts,o=uni-wuerzburg");
			// Log in if user is assistant|helper or lecturer
			if (ldap.isHiwi() || ldap.getLehrstuhl() != null
					//.equals("Lehrstuhl für Mathematik V") || ldap.getLehrstuhl().equals("L.f. Didaktik d. Mathematik")
					) {
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
