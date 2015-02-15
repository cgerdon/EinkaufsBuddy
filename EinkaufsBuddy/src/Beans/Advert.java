package Beans;  
  
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
//import java.io.Serializable;



import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.primefaces.event.SelectEvent;
  
@ManagedBean(name = "advert")  
@RequestScoped  
public class Advert {  
	
	private int ad_id;
    private int advertiser_id; 
    private Date date;
    //private String date;
    private int fk_time_id;
    private double income;
    private double limit;
    private int fk_category;
    private String text;
    private boolean status;
    private String fav_market;
    private int buyer_id;
    private String dbPassword;  
    private String dbName;  
    DataSource ds;  
  
    public Advert() {  
        try {  
            Context ctx = new InitialContext();  
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/database");  
        } catch (NamingException e) {  
            e.printStackTrace();  
        }  
    }  
    
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));  
    }
	
	public int getAd_id() {
		return ad_id;
	}




	public void setAd_id(int ad_id) {
		this.ad_id = ad_id;
		System.out.println("test1");  
	}




	public int getAdvertiser_id() {
		return advertiser_id;
	}




	public void setAdvertiser_id(int advertiser_id) {
		this.advertiser_id = advertiser_id;
	}


	/*public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}
*/
	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}
	



	public int getFk_time_id() {
		return fk_time_id;
	}




	public void setFk_time_id(int fk_time_id) {
		this.fk_time_id = fk_time_id;
	}




	public double getIncome() {
		return income;
	}




	public void setIncome(double income) {
		this.income = income;
	}



	public int getFk_category() {
		return fk_category;
	}

	public void setFk_category(int fk_category) {
		this.fk_category = fk_category;
	}

	public double getLimit() {
		return limit;
	}




	public void setLimit(double limit) {
		this.limit = limit;
	}




	public String getText() {
		return text;
	}




	public void setText(String text) {
		this.text = text;
	}






	public boolean isStatus() {
		return status;
	}




	public void setStatus(boolean status) {
		this.status = status;
	}




	public String getFav_market() {
		return fav_market;
	}




	public void setFav_market(String fav_market) {
		this.fav_market = fav_market;
	}




	public int getBuyer_id() {
		return buyer_id;
	}




	public void setBuyer_id(int buyer_id) {
		this.buyer_id = buyer_id;
	}




	public String getDbPassword() {
		return dbPassword;
	}




	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}




	public String getDbName() {
		return dbName;
	}




	public void setDbName(String dbName) {
		this.dbName = dbName;
	}




	public DataSource getDs() {
		return ds;
	}




	public void setDs(DataSource ds) {
		this.ds = ds;  
	}


/*Inserat muss man ändern können
	public String updateInfos() {
		//TODO:Christoph	sieht mistig aus, schön machen.
		  int i = 0;  
	      PreparedStatement ps = null;  
	      Connection con = null;  
	      try {  
	                if (ds != null) {  
	                    con = ds.getConnection();  
	                    if (con != null) {  
	                    	if (birthday == null){
	                    	}
	                    	String sql = "UPDATE ad set password_hash='" + password + "', name ='" + firstName + "', last_name='" + lastName + "' , car='" + car + "' , abouttext='" + abouttext + "' , street='" + street + "' , plz='" + plz + "' , phone='" + phone + "', birthdate='" + new SimpleDateFormat("yyyy-MM-dd").format(birthday) + "' where mail ='" + email + "';";

	                    	System.out.println(sql);
	                    	ps = con.prepareStatement(sql);  
	                        i = ps.executeUpdate();  
	                        System.out.println("Daten erfolgreich geändert");  
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

	        if (i > 0) {  
	           return "success";  
	        } else  
	            return "unsuccess";  
	}
*/
	public String add() {  
        int i = 0;
        System.out.println("test3");
        System.out.println(ad_id);
        System.out.println(advertiser_id);
        System.out.println(fk_time_id);
        System.out.println(limit);
        System.out.println(income);
        System.out.println(text);
        System.out.println(fk_category);
        System.out.println(status);
        System.out.println(fav_market);
        System.out.println(date);
        System.out.println(buyer_id);
        
             PreparedStatement ps = null;  
            Connection con = null;  
            try {  
                if (ds != null) {  
                    con = ds.getConnection();  
                    if (con != null) { 
                    	//new SimpleDateFormat("yyyy-MM-dd").format(date);
                        String sql = "INSERT INTO ad(id, advertiser_id, fk_time, limit, income, text, fk_category, status, fav_market, date, buyer_id) VALUES(?,?,?,?,?,?,?,?,?,?,?)";  
                        ps = con.prepareStatement(sql);  
                        ps.setInt(1, ad_id);
                        ps.setInt(2, advertiser_id); 
                        ps.setInt(3, fk_time_id);  
                        ps.setDouble(4, limit);  
                        ps.setDouble(5, income);  
                        ps.setString(6, text);
                        ps.setInt(7, fk_category);
                        ps.setBoolean(8, status); 
                        ps.setString(9, fav_market);
                        //ps.setString(10, "0000-00-00");
                        //ps.setDate(10, date);
                        //ps.setDate(10, new SimpleDateFormat("yyyy-MM-dd").format(date));
                        ps.setString(10, new SimpleDateFormat("yyyy-MM-dd").format(date));
                        ps.setInt(11,buyer_id);
                        i = ps.executeUpdate();  
                        System.out.println("Inserat erfolgreich angelegt");  
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
         
        if (i > 0) {  
            return "success";  
        } else  
            return "unsuccess";  
    }   
    
	
}  