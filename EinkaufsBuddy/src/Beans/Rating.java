package Beans;  

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
  
@ManagedBean(name = "rating") 

@SessionScoped  
public class Rating{  
	

    DataSource ds;  
    private int buyer;
    private int advertiser;
    private int ad;
    private String text;
    private int rating;
    private ArrayList<RatingResults> RatingList;
  
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



	public ArrayList<RatingResults> getRatingList() {
		return RatingList;
	}



	public void setRatingList(ArrayList<RatingResults> ratingList) {
		RatingList = ratingList;
	}



	public void addRating() {  
        int i = 0;  
       //INSERT INTO `rating` (`buyer_id`, `advertiser_id`, `rating`, `text`, `ad_id`) VALUES (2, 3, 5, 'Sie war Einsam. Und es gab einen JÃ¤germeister fÃ¼r mich!', 4);
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
                        i = ps.executeUpdate();  
                        System.out.println("Bewertung erfolgreich hinzugefügt");  
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
  
    public void getRatings(int id) {  
            PreparedStatement ps = null;  
            Connection con = null;  
            ResultSet rs = null;  
  
            if (ds != null) {  
                try {  
                    con = ds.getConnection();  
                    if (con != null) {  
                        String sql = "select id, buyer_id, advertiser_id, rating, text, ad_id from rating where buyer_id = '"  
                                + id + "' or advertiser_id = '" + id + ";";  
                        ps = con.prepareStatement(sql);  
                        rs = ps.executeQuery();  
                        while (rs.next()) {
                        	RatingResults TempObj = new RatingResults();
                            TempObj.setId(rs.getInt("id"));
                            TempObj.setBuyerid(rs.getInt(buyer));
                            TempObj.setAdvertiserid(rs.getInt("advertiser_id"));
                            TempObj.setRating(rs.getInt(rating));
                            TempObj.setAdid(rs.getInt(ad));
                            TempObj.setText(rs.getString("text"));
                        }
                    }  
                } catch (SQLException sqle) {  
                    sqle.printStackTrace();  
                }  
            }  
            
        }
	
   
    
//public String profilchange(String firstName, String lastName, Date birthday, int car, String phone, String email, String password, String street, int plz, String abouttext) {
//
//	int i = 0;  
//      PreparedStatement ps = null;  
//      Connection con = null;  
//      try {  
//                if (ds != null) {  
//                    con = ds.getConnection();  
//                    if (con != null) {  
//                    	if (birthday == null){
//                    	}
//                    	String sql = "UPDATE member set password_hash='" + password + "', name ='" + firstName + "', last_name='" + lastName + "' , car='" + car + "' , abouttext='" + abouttext + "' , street='" + street + "' , plz='" + plz + "' , phone='" + phone + "', birthdate='" + new SimpleDateFormat("yyyy-MM-dd").format(birthday) + "' where mail ='" + email + "';";
//
//                    	System.out.println(sql);
//                    	ps = con.prepareStatement(sql);  
//                        i = ps.executeUpdate();  
//                        System.out.println("Daten erfolgreich geändert");  
//                    }  
//                }  
//            } catch (Exception e) {  
//                System.out.println(e);  
//            } finally {  
//                try {  
//                    con.close();  
//                    ps.close();  
//                } catch (Exception e) {  
//                    e.printStackTrace();  
//                }  
//            }  
//
//        if (i > 0) {  
//           return "profil";  
//        } else  
//            return "unsuccess";  
//			}   
    
    
    
    
    
}  
