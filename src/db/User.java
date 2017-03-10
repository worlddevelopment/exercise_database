package db;


import java.util.Properties;

import javax.naming.directory.DirContext;
import javax.naming.ldap.InitialLdapContext;


public class User {

	/**
	 * Get the name of a user as stored in directory accessed via LDAP.
	 *
	 * @param ldap An active LDAP wrapper reference.
	 * @param username The username to retrieve the real name of.
	 * @return The real name of the user or an empty string.
	 */
	public String getName(LDAP ldap, String username) {

		Properties prop = ldap.getProp();
		try {
			DirContext ctx = new InitialLdapContext(prop, null);
			return ldap.getSearchResult(ctx, "cn=" + username
					, "ou=accounts,o=uni-wuerzburg");
		}
		catch (Exception e) {
			return "";
		}
	}
}
