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
import javax.faces.bean.ManagedProperty;
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
	
	@ManagedProperty(value="#{user.id}")
    private int advertiser_id; 
    private Date date;
    private int fk_time_id;
    private double income;
    private double limit;
    private int fk_category;
    private String text;
    private boolean status;
    private String fav_market;
    private int buyer_id;
    private int ad_count;
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
  
	}




	public int getAdvertiser_id() {
		return advertiser_id;
	}




	public void setAdvertiser_id(int advertiser_id) {
		this.advertiser_id = advertiser_id;
	}


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


	public DataSource getDs() {
		return ds;
	}



	public void setDs(DataSource ds) {
		this.ds = ds;  
	}

	ArrayList<Advert> ownadverts = new ArrayList<Advert>();
	//ArrayList<message> messageoverview = new ArrayList<message>();
	

	public ArrayList<Advert> getOwnadverts() {
		return ownadverts;
	}


	public void setOwnadverts(ArrayList<Advert> ownadverts) {
		this.ownadverts = ownadverts;
	}

	
	/*public ArrayList<message> getMessageoverview() {
		return messageoverview;
	}


	public void setMessageoverview(ArrayList<message> messageoverview) {
		this.messageoverview = messageoverview;
	}*/
	
	ArrayList<Advert> otheradverts = new ArrayList<Advert>();

	
	public ArrayList<Advert> getOtheradverts() {
		return otheradverts;
	}


	public void setOtheradverts(ArrayList<Advert> otheradverts) {
		this.otheradverts = otheradverts;
	}
	
	
	public Advert(int ad_count, int ad_id, int advertiser_id, Date date, int fk_time_id, double limit, double income, String text, int fk_category, boolean status, String fav_market, int buyer_id) { //int buyer_id
		super();
		this.ad_count=ad_count;
		this.ad_id = ad_id;
		this.advertiser_id = advertiser_id;
		this.date = date;
		this.fk_time_id =fk_time_id;
		this.limit=limit;
		this.income = income;
		this.text = text;
		this.fk_category = fk_category;
		this.status = status;
		this.fav_market = fav_market;
		this.buyer_id = buyer_id;
	}
	

	//Inserat muss man ändern können
	public String updateInfos() {
		  int i = 0;  
	      PreparedStatement ps = null;  
	      Connection con = null;  
	      try {  
	                if (ds != null) {  
	                    con = ds.getConnection();  
	                    if (con != null) {  
	                    	
	                    	String sql = "UPDATE ad set date='" + new SimpleDateFormat("yyyy-MM-dd").format(date) + "', fk_timt_id ='" + fk_time_id + "', limit'" + limit + "' , text='" + text + "' , fk_category='" + fk_category + "' , status='" + status + "' , fav_market='" + fav_market + "' where id ='" + ad_id + "';";

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
	           return "successad";  
	        } else  
	            return "unsuccess";  
	}

	public String add() {  
        int i = 0;
        System.out.println(advertiser_id);
        System.out.println(date);
        System.out.println(fk_time_id);
        System.out.println(limit);
        System.out.println(income);
        System.out.println(text);
        System.out.println(fk_category);
        System.out.println(status);
        System.out.println(fav_market);
        System.out.println(buyer_id);
        
            PreparedStatement ps = null;  
            Connection con = null;
            try {  
                if (ds != null) {  
                    con = ds.getConnection();  
                    if (con != null) { 
                    	//INSERT INTO `ad` (`advertiser_id`, `date`, `fk_time_id`, `limit`, `income`, `text`, `fk_category`, `status`, `fav_market`, `buyer_id`) VALUES(3, '2014-11-05', 3, 10, 1, 'Hallo, ich mag bitte eine Flasche Schnaps haben!', 1, 0, 'Aldi', NULL),
                        String sql = "INSERT INTO `ad` (`advertiser_id`, `date`, `fk_time_id`, `limit`, `income`, `text`, `fk_category`, `status`, `fav_market`, `buyer_id`) VALUES(?,?,?,?,?,?,?,?,?,?)";  
                        ps = con.prepareStatement(sql);    
                        ps.setInt(1, advertiser_id);
                        ps.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(date));
                        ps.setInt(3, fk_time_id);  
                        ps.setDouble(4, limit);  
                        ps.setDouble(5, income);  
                        ps.setString(6, text);
                        ps.setInt(7,fk_category);
                        ps.setBoolean(8, status); 
                        ps.setString(9, fav_market);
                        ps.setString(10, null);
                        System.out.println(ps.toString());
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
            return "successad";  
        } else  
            return "unsuccess";  
    }   
	
	public ArrayList <Advert> getAd_idfromSQL(int ad_id){
			this.ad_id = ad_id; 
			
            PreparedStatement ps = null;  
            Connection con = null;  
            ResultSet rs = null;  
  
            if (ds != null) {  
                try {  
                    con = ds.getConnection();  
                    if (con != null) {  
                        String sql = "select id, advertiser_id, date, fk_time_id, limit, income, text, fk_category, status, fav_market, buyer_id from ad where id = '"  
                                + ad_id + "'";  
                        ps = con.prepareStatement(sql);  
                        rs = ps.executeQuery();
                        
                        int i=0;
                        ownadverts.clear();
                        	while (rs.next()) {  
                        		i++;
            
                     	   
                        		Advert TempObj = new Advert(i, 
	   								rs.getInt("ad.id"), 
	   								rs.getInt("ad.advertiser_id"), 
	   								rs.getDate("ad.date"), 
	   								rs.getInt("ad.fk_time_id"), 
	   								rs.getDouble("ad.limit"), 
	   								rs.getDouble("ad.income"), 
	   								rs.getString("ad.text"), 
	   								rs.getInt("ad.fk_category"), 
	   								rs.getBoolean("ad.status"), 
	   								rs.getString("ad.fav_market"), 
	   								rs.getInt("ad.buyer_id"));
  	
                        		ownadverts.add(TempObj);

                        	}
                       
                    	}  
                } catch (SQLException sqle) {  
                    sqle.printStackTrace();  
                }    
        }
			return ownadverts;    
    }  
    
	public String changeData() {  
		FacesContext fc = FacesContext.getCurrentInstance();
		this.ad_id = getad_id(fc);
		
		ownadverts = getAd_idfromSQL(ad_id);
		System.out.println("test ad id " + ad_id); 
		return "changeadvert";
	} 
		
	
	public int getad_id(FacesContext fc){
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String ad_id = params.get("ad_id");
		int value = Integer.valueOf(ad_id);
		return value;
 
	}
	    

    public String showown(){
    	PreparedStatement ps = null;  
        Connection con = null;  
        ResultSet rs = null;
        
        if (ds != null) {  
            try {  
                con = ds.getConnection();  
                if (con != null) {
                	System.out.println(advertiser_id); 
                	// String sql = "select id, advertiser_id, date, fk_time_id, limit, income, text, fk_category, status, fav_market, buyer_id
                    String sql = "SELECT ad.id, ad.advertiser_id, ad.date, ad.fk_time_id, ad.limit, ad.income, ad.text, ad.fk_category, ad.status, ad.fav_market, ad.buyer_id FROM ad WHERE ad.advertiser_id = '" + advertiser_id + "'";  //buyer_id
                    ps = con.prepareStatement(sql);  
                    rs = ps.executeQuery();  
                    ownadverts.clear();
                    int i =0;
                    while (rs.next()) {  
                    	i++;	
                    	Advert TempObj = new Advert(i,
                    								rs.getInt("ad.id"), 
                    								rs.getInt("ad.advertiser_id"), 
                    								rs.getDate("ad.date"), 
                    								rs.getInt("ad.fk_time_id"), 
                    								rs.getDouble("ad.limit"), 
                    								rs.getDouble("ad.income"), 
                    								rs.getString("ad.text"), 
                    								rs.getInt("ad.fk_category"), 
                    								rs.getBoolean("ad.status"), 
                    								rs.getString("ad.fav_market"), 
                    								rs.getInt("ad.buyer_id"));
                    	
                    	ownadverts.add(TempObj);
                    	//System.out.println(ownadverts);
                 
                    	
                    }
                }  
            } catch (SQLException sqle) {  
                sqle.printStackTrace();  
            }    
        }
        return "viewownadverts";
    }
    public String showothers(){
	      	PreparedStatement ps = null;  
	        Connection con = null;  
	        ResultSet rs = null;
        
        if (ds != null) {  
            try {  
                con = ds.getConnection();  
                if (con != null) {
                	//System.out.println(advertiser_id); 
                	// String sql = "select id, advertiser_id, date, fk_time_id, limit, income, text, fk_category, status, fav_market, buyer_id
                    String sql = "SELECT ad.id, ad.advertiser_id, ad.date, ad.fk_time_id, ad.limit, ad.income, ad.text, ad.fk_category, ad.status, ad.fav_market, ad.buyer_id FROM ad WHERE ad.advertiser_id = '" + buyer_id + "'";  //buyer_id
                    ps = con.prepareStatement(sql);  
                    rs = ps.executeQuery();  
                    otheradverts.clear();
                    int i=0;
                    while (rs.next()) {  
                    	i++;	
                    	Advert TempObj = new Advert(i,
                    								rs.getInt("ad.id"), 
                    								rs.getInt("ad.advertiser_id"), 
                    								rs.getDate("ad.date"), 
                    								rs.getInt("ad.fk_time_id"), 
                    								rs.getDouble("ad.limit"), 
                    								rs.getDouble("ad.income"), 
                    								rs.getString("ad.text"), 
                    								rs.getInt("ad.fk_category"), 
                    								rs.getBoolean("ad.status"), 
                    								rs.getString("ad.fav_market"), 
                    								rs.getInt("ad.buyer_id"));
                    	
                    	otheradverts.add(TempObj);
                    	//System.out.println(otheradverts);
                 
                    	
                    }
                }  
            } catch (SQLException sqle) {  
                sqle.printStackTrace();  
            }    
        }
        return "viewotheradverts";
    }
    	
	
}  