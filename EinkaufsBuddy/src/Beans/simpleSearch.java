package Beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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
	private List<String> nearAdsList;
	
	DataSource ds;

	public simpleSearch() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/database");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getNearAdsList() {
		return nearAdsList;
	}

	public void setNearAdsList(List<String> nearAdsList) {
		this.nearAdsList = nearAdsList;
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

	public String searchSimple() throws IOException, JSONException {
		//System.out.println(plzInput);
		 PreparedStatement ps = null;  
		 List<String> nearAds = new ArrayList<String>();
		 List<String> Adressen = new ArrayList<String>();
		 
		 StringBuffer buffer = new StringBuffer();
         Connection con = null;  
         ResultSet rs = null;  

         if (ds != null) {  
             try {  
                 con = ds.getConnection();  
                 if (con != null) {  
                     String sql = "SELECT ad.text, member.plz, member.street from ad LEFT JOIN member ON ad.advertiser_id=member.id;";
                     ps = con.prepareStatement(sql);  
                     rs = ps.executeQuery();  
                    
                     while(rs.next()) {
                    	 //TODO:Christoph	hier das hier https://developers.google.com/maps/documentation/distancematrix/?hl=de
                    	 //http://maps.googleapis.com/maps/api/distancematrix/json?origins=76137%20DE&destinations=76829%20DE|76131%20DE&mode=car&language=de-DE&sensor=false
//                    	 Beispiel JSON mit 2 adressen
                    	 
                         nearAds.add(rs.getString("text"));
                         Adressen.add(rs.getString("street") + " " + rs.getString("plz"));
                         }
                     BufferedReader reader = null;
                     try {
                         URL url = new URL("http://maps.googleapis.com/maps/api/distancematrix/json?origins=76137%20DE&destinations=76829%20DE|76131%20DE&mode=car&language=de-DE&sensor=false");
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
         //System.out.println(buffer.toString());
         nearAdsList = nearAds;
		return "simpleSearchResult";
	}

}
