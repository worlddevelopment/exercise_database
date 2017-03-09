package db;

import java.io.IOException;
import java.util.Properties;

import javax.naming.Context;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
//import javax.smartcardio.ATR;


public class LDAP {

	final String SERVER  = "ldaps://auth.uni-wuerzburg.de";
	final String PORT = "636";

	private Properties prop;
	private String lehrstuhl;
	private boolean hiwi = false;



	/**
	 * Get the LDAP properties.
	 *
	 * @return The LDAP properties.
	 */
	public Properties getProp() {
		return prop;
	}



	/**
	 * Set LDAP properties.
	 *
	 * @param user Security principal user
	 * @param password Security credentials password.
	 */
	public void setProp(String user, String password) {

		Properties env = new Properties();

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

		env.put(Context.PROVIDER_URL,SERVER + ":" + PORT + "/");

		env.put(Context.SECURITY_PROTOCOL, "ssl");
		//env.put(Context.REFERRAL, "follow");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "cn=" + user + ",ou=accounts,o=uni-wuerzburg");

		env.put(Context.SECURITY_CREDENTIALS, password);
		//setProp(env);
		this.prop = env;
	}



	/**
	 * Return the search result.
	 *
	 * @param dirContext
	 * @param searchFilter
	 * @param searchBase
	 * @return The search result.
	 */
	public String getSearchResult(final DirContext dirContext,
			final String searchFilter, final String searchBase)
			throws IOException, NamingException{

		String res = "";

		final SearchControls constraints = new SearchControls();
		final NamingEnumeration<SearchResult> searchResults = dirContext.search(searchBase,searchFilter,constraints);

		if (searchResults != null && searchResults.hasMore()){
			// For example displayed attribute values
			final SearchResult searchResult = (SearchResult)searchResults.next();
			res = displayAttributes(searchResult.getAttributes());
		}

		return res;
	}



	/**
	 * Display attribute values.
	 *
	 * @param attributes The attribute whose values to display.
	 * @return The values to display.
	 */
	public String displayAttributes(final Attributes attributes)
			throws IOException, NamingException{
		String erg ="";

		if (attributes == null) {
			System.out.println("LDAP: displayAttributes: attributes was null: " + attributes);
		}
		else {
			int i = 0;
			for (NamingEnumeration<? extends Attribute> enums = attributes.getAll(); enums.hasMore();) {

				i++;
				final Attribute attribute = (Attribute)enums.next();
				//System.out.println(i + " : "  + attribute.toString());


				if (attribute.getID().equals("fullName")){
					//out.println("Deine name :"  + attribute.get().toString());
					erg = attribute.get().toString();
					System.out.println("Name: " + erg);
				}
				if (attribute.getID().equals("eduPersonAffiliation") ){
					if (attribute.size()>1) {
						this.hiwi = attribute.get(1).equals("employee"); // zugriff nur für Hiwis
						System.out.println("Rolle:" + attribute.get(1).toString());
					}


				}
//				if (attribute.getID().equals("groupMembership")){
//					//out.println("Deine name :"  + attribute.get().toString());
//					System.out.println("groupMembership: " + attribute.get(0).toString());
//				}
				if (attribute.getID().equals("ou")){
					this.lehrstuhl = attribute.get(0).toString();
					System.out.println("ou:"  + attribute.get(0));
				}

	   }

		}
		return erg;

	}



	/**
	 * Return the current chair|school.
	 *
	 * @return The current chair|school.
	 */
	public String getLehrstuhl() {
		return lehrstuhl;
	}



	/**
	 * Return if the user is scientific assistant|helper.
	 *
	 * @return true if it is a scientific assistant|helper.
	 */
	public boolean isHiwi() {
		return hiwi;
	}


}
