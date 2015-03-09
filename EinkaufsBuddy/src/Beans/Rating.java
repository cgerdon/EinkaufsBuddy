package Beans;  

import java.io.Serializable;
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
public class Rating implements Serializable{  
	

     /**
	 * 
	 */
	private static final long serialVersionUID = -3764405341697498243L;
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
	
	public void ChangeAdFields(int adid, int spalte){
		//UPDATE `einkaufsbuddy`.`ad` SET `advertiserrate`=1 WHERE  `id`=52;
		 PreparedStatement ps = null;  
         Connection con = null;  
         String sql = "";
         if (spalte == 2){
        	 sql = "UPDATE `einkaufsbuddy`.`ad` SET `advertiserrate`= 1 WHERE  `id`="+adid+";";
        	 
         }
         else {
        	 sql = "UPDATE `einkaufsbuddy`.`ad` SET `buyerrate`= 1 WHERE  `id`="+adid+";";
         }
         System.out.println(sql);
         try {  
             if (ds != null) {  
                 con = ds.getConnection();  
                 if (con != null) {  
                 	 ps = con.prepareStatement(sql);  
                     
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


	public String addRating(int buyerid, int adid, int advid, int type){  
      
            PreparedStatement ps = null;  
            Connection con = null;  
            try {  
                if (ds != null) {  
                    con = ds.getConnection();  
                    if (con != null) {  
                    	System.out.println(buyerid);
                    	System.out.println(adid);
                    	System.out.println(rating);
                    	System.out.println("Hier sollte der Text stehen: " + text);
                    	System.out.println(advid);
                    	System.out.println(type);
                        String sql = "INSERT INTO `rating` (`buyer_id`, `advertiser_id`, `rating`, `text`, `ad_id`, type) VALUES (?, ?, ?, ?, ?, ?);";  
                        ps = con.prepareStatement(sql);  
                        ps.setInt(1, buyerid);  
                        ps.setInt(2, advid);  
                        ps.setInt(3, rating);  
                        ps.setString(4, text);  
                        ps.setInt(5, adid);
                        System.out.println("In type steht " + type + "und in advid steht " + advid + " und in buyerid steht "+ buyerid);
                        ps.setInt(6, advid);
                        System.out.println(ps.toString());
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
            if (type == 1) {ChangeAdFields(adid, 1);}
            else {ChangeAdFields(adid, 2);}
            return "home?faces-redirect=true";
    }  
  
	
   
   
}  
