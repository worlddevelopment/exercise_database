package swp;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;



public class LdapConnection{
    final String searchFilter = "cn=mail";
	  final String searchBase = "ou=people,o=uni-wuerzburg";

	    

	/**
	 * This method is used to search an entry in LDAP using Novell API
	 * @param dirContext
	 * @param searchFilter
	 * @param searchBase
	 * @return {@link Boolean}
	 * @throws NamingException
	 */
	private static final boolean getSearchResult(final DirContext dirContext, 
	        final String searchFilter, 
	        final String searchBase) throws NamingException{
	    
	    boolean retVal = false;
	    
	    final SearchControls constraints = new SearchControls();
	   // constraints.setSearchScope(LDAPConstants.SUBTREE_SCOPE);
	    
	    System.out.println("** Search Starts : "  + System.currentTimeMillis());
	    final NamingEnumeration<SearchResult> searchResults = dirContext.search(searchBase,searchFilter,constraints);
	    System.out.println("** Search Ends : "  + System.currentTimeMillis());
	    
	    if(searchResults != null && searchResults.hasMore()){
	        retVal= true;
	        // For Example , displayed attribute values
	       
	        final SearchResult searchResult = (SearchResult)searchResults.next();
	        displayAttributes(searchResult.getAttributes());
	    }

	    return retVal;
	}
	/**
	 * This method used to display the Attribute Values
	 * @param attributes
	 * @throws NamingException
	 */
	private static void displayAttributes(final Attributes attributes) throws NamingException{
	    
	    if (attributes == null) {
	        System.out.println("*** No attributes ***");
	    } 
	    else {
	        for (NamingEnumeration<? extends Attribute> enums = attributes.getAll(); enums.hasMore();) {
	            final Attribute attribute = (Attribute)enums.next();
	            System.out.println();
	           System.out.println("Id :" + attribute.getID() + " " + attribute.get());
	            if(attribute.getID().equals("fullName")){
	            	System.out.println("Deine name :"  + attribute.get());
	            }
	            //attribute.get();
//	            for (NamingEnumeration<?> namingEnu = attribute.getAll();namingEnu.hasMore();)
//	                System.out.println("\t        = " + namingEnu.next());
       }

	    }
	  
}
	public static void main(String[] args) {
//		Properties env = new Properties();
//	      env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//	      env.put(Context.PROVIDER_URL, "ldap://adressbuch.uni-wuerzburg.de:389");//ldap://adressbuch.uni-wuerzburg.de:389/ou=people,o=uni-wuerzburg??one?(objectClass=*)
//	      //env.put(Context.REFERRAL, "folow");
//	      env.put(Context.SECURITY_AUTHENTICATION, "none");
//	      //env.put(Context.SECURITY_PRINCIPAL, "ou=people,o=uni-wuerzburg");
//	      //env.put(Context.SECURITY_CREDENTIALS, "artjom84");
//	      try {
//	    	  
//	          DirContext ctx = new InitialLdapContext(env, null);
//	          //System.out.println( ctx.createSubcontext("ou=people,o=uni-wuerzburg"));
//	          //ctx.getAttributes("ou=people,o=uni-wuerzburg").size();
//	          //ctx.lookup("ou=people,o=uni-wuerzburg");
//	          //ctx.close();
//	          LdapConnection.getSearchResult(ctx, "objectClass=*", "ou=people,o=uni-wuerzburg");
//	          System.out.println(LdapConnection.getSearchResult(ctx, "objectClass=*", "ou=people,o=uni-wuerzburg"));
//	          System.out.println("erfolg");
//	      }
//	      catch (Exception e) {
//	          System.out.println("misserfolg");
//	      }
		
		
		
		Properties env = new Properties();
	      env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

	      env.put(Context.PROVIDER_URL, "ldaps://auth.uni-wuerzburg.de:636/");//ldap://adressbuch.uni-wuerzburg.de:389/ou=people,o=uni-wuerzburg??one?(objectClass=*)
	      env.put(Context.SECURITY_PROTOCOL, "ssl");
	      //env.put(Context.REFERRAL, "follow");
	      env.put(Context.SECURITY_AUTHENTICATION, "simple");
	      env.put(Context.SECURITY_PRINCIPAL, "cn=s210507,ou=accounts,o=uni-wuerzburg");
	      env.put(Context.SECURITY_CREDENTIALS, "artjom84");
	      try {
	    	  
	          DirContext ctx = new InitialLdapContext(env, null);
	          //System.out.println( ctx.createSubcontext("ou=people,o=uni-wuerzburg"));
	          //ctx.getAttributes("ou=people,o=uni-wuerzburg").size();
	          //ctx.lookup("ou=people,o=uni-wuerzburg");
	          //ctx.close();
	          LdapConnection.getSearchResult(ctx, "cn=*", "ou=accounts,o=uni-wuerzburg");
	          //System.out.println(LdapConnection.getSearchResult(ctx, "objectClass=*","ou=accounts,o=uni-wuerzburg"));
	          System.out.println("erfolg");
	      }
	      catch (Exception e) {
	          System.out.println("misserfolg");
	      }
	      



	}
}
