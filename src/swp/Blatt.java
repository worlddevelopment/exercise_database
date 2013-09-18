package swp;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Blatt {
	
	private String datei_name;
	private String semester;
	private String dozent;
	private String veranstaltung;
	private String typ;
	private String dateiFormat;
	private String beschreibung;
	private String bl_link;
	private String bild_link;
	
	private String bl_id;
	private String semester_id;
	private String dozent_id;
	private String veranstaltung_id;
	private String typ_id;
	
	ArrayList<Aufgabe> aufgaben;
	
	



	/**
	 * Es wird ein Blatt mit DB id instantiiert.
	 * dabei werden alle daten aus der Db geholt.
	 * @param id
	 * @throws IOException 
	 */
	public Blatt(String id) throws IOException {
		aufgaben = new ArrayList<Aufgabe>();
		MysqlHelper msqh = new MysqlHelper();
		Connection con = msqh.establishConnection(null);
		Statement statement = null;


		
		
		try {
			statement = con.createStatement();
			String str_query = "select * from blatt b,veranstaltung v,semester s,typ t,dozent d where b.id='" 
			+ id + "' and b.doz_id = d.id and b.sem_id = s.id and b.ver_id = v.id and b.typ_id = t.id;";
			
			ResultSet res = statement.executeQuery(str_query);
			res.next();
			
			this.datei_name = res.getString("bl_bez");
			this.veranstaltung_id = res.getString("ver_id");
		    this.dozent_id = res.getString("doz_id");
		    this.semester_id = res.getString("sem_id");
		    this.typ_id = res.getString("typ_id");
		    this.bl_id = res.getString("id");
		    
		    this.semester = res.getString("sem_bez");
		    this.veranstaltung = res.getString("ver_bez");
		    this.dozent = res.getString("name");
		    this.typ = res.getString("typ_bez");
		    this.dateiFormat = res.getString("datei_typ");
		    this.beschreibung = res.getString("beschr");
		    this.bl_link = res.getString("link");
		    this.bild_link = res.getString("bild_link");
			
		   
			
			
			statement = con.createStatement();
			str_query = "select * from aufgabe where bl_id='" + this.bl_id + "' group by aufg_bez asc;";
			res = statement.executeQuery(str_query);
			while(res.next()) {
				Aufgabe a = new Aufgabe();
				a.setId(res.getString("id"));
				a.setBez(res.getString("aufg_bez"));
				a.setLink(res.getString("link"));
				a.setBild_link(res.getString("bild_link"));
				aufgaben.add(a);
			}
			res.close();
			con.close();
			
		}
		catch (SQLException e) {
		    e.printStackTrace();
			}
	}
	
	

	public String getDateiName() {
		return datei_name;
	}

	public String getSemester() {
		return semester;
	}

	public String getDozent() {
		return dozent;
	}

	public String getVeranstaltung() {
		return veranstaltung;
	}
	public String getTyp() {
		return typ;
	}



	public String getSemester_id() {
		return semester_id;
	}



	public String getDozent_id() {
		return dozent_id;
	}



	public String getVeranstaltung_id() {
		return veranstaltung_id;
	}
	
	public String getTyp_id() {
		return typ_id;
	}



	public String getDateiFormat() {
		return dateiFormat;
	}



	public String getBeschreibung() {
		return beschreibung;
	}



	public String getBl_link() {
		return bl_link;
	}



	public String getBild_link() {
		return bild_link;
	}



	public ArrayList<Aufgabe> getAufgaben() {
		return aufgaben;
	}

}
