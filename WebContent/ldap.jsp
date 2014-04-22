<%@page import="java.io.PrintWriter"%>
<%@ page import="java.util.Properties,javax.naming.Context,
 javax.naming.NamingEnumeration,
 javax.naming.NamingException,
 javax.naming.directory.Attribute,
 javax.naming.directory.Attributes,
 javax.naming.directory.DirContext,
 javax.naming.directory.SearchControls,
 javax.naming.directory.SearchResult,
 javax.naming.ldap.InitialLdapContext,
 java.io.IOException"%>
 <%
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
     
     
     
    out.print("erg: " + getSearchResult(response,ctx, "cn=*", "ou=accounts,o=uni-wuerzburg"));
     //out.print("Erg: " + )
     //System.out.println(LdapConnection.getSearchResult(ctx, "objectClass=*","ou=accounts,o=uni-wuerzburg"));
     out.println("erfolg");
 }
 catch (Exception e) {
     out.println("misserfolg");
 }
 %>
<%!
	

public String getSearchResult(HttpServletResponse resp,final DirContext dirContext,final String searchFilter,final String searchBase) throws IOException, NamingException{
   
	String res = "";
	PrintWriter out = resp.getWriter();

	

    
    final SearchControls constraints = new SearchControls();
   // constraints.setSearchScope(LDAPConstants.SUBTREE_SCOPE);
    

    final NamingEnumeration<SearchResult> searchResults = dirContext.search(searchBase,searchFilter,constraints);
    
    if(searchResults != null && searchResults.hasMore()){

        // For Example , displayed attribute values
       
        final SearchResult searchResult = (SearchResult)searchResults.next();
        res = displayAttributes(resp,searchResult.getAttributes());
    }

    return res;
}

public String displayAttributes(HttpServletResponse resp,final Attributes attributes) throws IOException,NamingException{
	String erg ="";
	PrintWriter out = resp.getWriter();
    if (attributes == null) {
        
    } 
    else {
        for (NamingEnumeration<? extends Attribute> enums = attributes.getAll(); enums.hasMore();) {
            final Attribute attribute = (Attribute)enums.next();

          // out.println("Id :" + attribute.getID() + " " + attribute.get());
            if(attribute.getID().equals("fullName")){
            	//out.println("Deine name :"  + attribute.get().toString());
            	erg = attribute.get().toString();
            }
            //attribute.get();
//            for (NamingEnumeration<?> namingEnu = attribute.getAll();namingEnu.hasMore();)
//                System.out.println("\t        = " + namingEnu.next());
   }

    }
    return erg;
  
}

%>