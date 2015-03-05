package Beans;  

import java.sql.Connection;
import java.sql.PreparedStatement;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
  
@ManagedBean(name = "rating") 

@SessionScoped  
public class Rating{  
	

     DataSource ds;  
    private int buyer;
    private int advertiser;
    private int ad;
    private String text;
    private int rating;
    
  
    public Rating() {  
        try {  
            Context ctx = new InitialContext();  
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/database");  
        } catch (NamingException e) {  
            e.printStackTrace();  
        }  
    }  
    
    
	public int getBuyer() {
		return buyer;
	}



	public void setBuyer(int buyer) {
		this.buyer = buyer;
	}



	public int getAdvertiser() {
		return advertiser;
	}



	public void setAdvertiser(int advertiser) {
		this.advertiser = advertiser;
	}



	public int getAd() {
		return ad;
	}



	public void setAd(int ad) {
		this.ad = ad;
	}



	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}



	public int getRating() {
		return rating;
	}



	public void setRating(int rating) {
		this.rating = rating;
	}


	public void addRating() {  
      
       //INSERT INTO `rating` (`buyer_id`, `advertiser_id`, `rating`, `text`, `ad_id`) VALUES (2, 3, 5, 'Sie war Einsam. Und es gab einen Jägermeister für mich!', 4);
            PreparedStatement ps = null;  
            Connection con = null;  
            try {  
                if (ds != null) {  
                    con = ds.getConnection();  
                    if (con != null) {  
                        String sql = "INSERT INTO `rating` (`buyer_id`, `advertiser_id`, `rating`, `text`, `ad_id`) VALUES (?, ?, ?, ?, ?);";  
                        ps = con.prepareStatement(sql);  
                        ps.setInt(1, buyer);  
                        ps.setInt(2, advertiser);  
                        ps.setInt(3, rating);  
                        ps.setString(4, text);  
                        ps.setInt(5, ad);
                        ps.executeUpdate();  
                    }  
                }
            } catch (Exception e) {  
                System.out.println(e);  
            } finally {  
                try {  
                    con.close();  
                    ps.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
    }  
  
	
   
   
}  
