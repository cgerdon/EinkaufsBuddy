package Beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

@ManagedBean(name = "simpleSearch")
@RequestScoped
public class simpleSearch {
	
	private int plzInput;
	private String text;
	private int plzDB;
	private String streetDB;
	private String name;
	private String vorname;
	private int id;
	private int limit;
	private int income;
	private List<SimpleSearchResults> AdvertList;
	
	
	DataSource ds;

	public simpleSearch() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/database");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public List<SimpleSearchResults> getAdvertList() {
		return AdvertList;
	}



	public void setAdvertList(List<SimpleSearchResults> advertList) {
		AdvertList = advertList;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getVorname() {
		return vorname;
	}


	public void setId(int id) {
		this.id = id;
	}


	public void setVorname(String vorname) {
		this.vorname = vorname;
	}



	public int getLimit() {
		return limit;
	}



	public void setLimit(int limit) {
		this.limit = limit;
	}



	public int getIncome() {
		return income;
	}



	public void setIncome(int income) {
		this.income = income;
	}



	public int getId() {
		return id;
	}


	public int getPlzInput() {
		return plzInput;
	}

	public void setPlzInput(int plzInput) {
		this.plzInput = plzInput;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getPlzDB() {
		return plzDB;
	}

	public void setPlzDB(int plzDB) {
		this.plzDB = plzDB;
	}

	public String getStreetDB() {
		return streetDB;
	}

	public void setStreetDB(String streetDB) {
		this.streetDB = streetDB;
	}

	public String searchSimple() throws IOException, JSONException, URISyntaxException {
		
		 PreparedStatement ps = null;  
		 List<String> Adressen = new ArrayList<String>();
		 
		 StringBuffer buffer = new StringBuffer();
         Connection con = null;  
         ResultSet rs = null;  

         if (ds != null) {  
             try {  
                 con = ds.getConnection();  
                 if (con != null) {  
                     String sql = "SELECT member.name, member.last_name, ad.text, member.plz, member.street, ad.id, ad.limit, ad.income from ad LEFT JOIN member ON ad.advertiser_id=member.id;";
                     ps = con.prepareStatement(sql);  
                     rs = ps.executeQuery();  
                    
                     while(rs.next()) {
                    	 //TODO:Christoph
//                    	System.out.println(rs.getString("street"));
//                    	System.out.println(rs.getString("name"));
//                    	System.out.println(rs.getString("last_name"));
//                    	System.out.println(rs.getString("text"));
//                    	System.out.println(rs.getInt("plz"));
//                    	System.out.println(rs.getInt("id"));
//                    	System.out.println(rs.getDouble("limit"));
//                    	System.out.println(rs.getDouble("income"));
                    	SimpleSearchResults TempObj = new SimpleSearchResults(rs.getString("text"), rs.getInt("plz"), rs.getString("street"), rs.getString("name"), rs.getString("last_name"), rs.getInt("id"), rs.getDouble("limit"), rs.getDouble("income"), 0);
                    	//AdvertList.add(TempObj);
                    	AdvertList.add(TempObj);
                    	Adressen.add(rs.getString("street").replaceAll("\\s","+") + "+" + rs.getString("plz").replaceAll("\\s","+"));
                         }
                     BufferedReader reader = null;
                     try {
                         String tempurl = "/maps/api/distancematrix/json?origins=" + plzInput + "+DE&destinations=";
                         for (String adr : Adressen) {
                        	 tempurl +=  adr + "+DE|";}
                        tempurl += "&mode=car&language=de-DE&sensor=false";
						URL url = new URL("https","maps.googleapis.com",tempurl);
						

                         System.out.println(url);

                         reader = new BufferedReader(new InputStreamReader(url.openStream()));
                         
                         int read;
                         char[] chars = new char[1024];
                         while ((read = reader.read(chars)) != -1)
                             buffer.append(chars, 0, read); 

                          
                     } finally {
                         if (reader != null)
                             reader.close();
                     }
                     
                 }  
             } catch (SQLException sqle) {  
                 sqle.printStackTrace();  
             }  
         }  
         JSONObject jsonGoogleMaps = new JSONObject(buffer.toString());
         JSONArray rows  = jsonGoogleMaps.getJSONArray("rows");
     
         for (int i = 0; i < rows.length(); i++) {
             JSONObject obj=rows.getJSONObject(i);
             JSONArray elements=obj.getJSONArray("elements");
             for (int j = 0; j < elements.length(); j++) {
                 JSONObject elem=elements.getJSONObject(j);
                 JSONObject distance = elem.getJSONObject("distance");
                 System.out.println(distance.getString("value"));
             }}
         System.out.println(AdvertList);
         System.out.println(buffer.toString());
		return "simpleSearchResult";
	}

}
