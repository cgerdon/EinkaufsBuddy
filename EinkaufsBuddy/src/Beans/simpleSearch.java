package Beans;

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

	public String searchSimple() {
		//System.out.println(plzInput);
		 PreparedStatement ps = null;  
		 List<String> nearAds = new ArrayList<String>();
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
                         nearAds.add(rs.getString("text"));
                     }
                 }  
             } catch (SQLException sqle) {  
                 sqle.printStackTrace();  
             }  
         }  
  
         nearAdsList = nearAds;
		return "simpleSearchResult";
	}

}
