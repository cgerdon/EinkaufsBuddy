package Beans;  
  
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
//import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
 
@ManagedBean(name = "advert")  

// @RequestScoped  
@SessionScoped

public class Advert implements Serializable{  

	/**
	 * 
	 */
	private static final long serialVersionUID = 7029800421689345132L;
	
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
    
    //für die Anzeige der Inserate
    private int plz;
	private String street;
	private String name;
	private String last_name;
	private int id;
	private Integer distance;
	private String zeitpunkt;
	private Date datum;
	private String category;
	private int memberid;
    
	//Mathias 
	private int ms_advertID;
	
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




	public int getMs_advertID() {
		return ms_advertID;
	}

	public void setMs_advertID(int ms_advertID) {
		this.ms_advertID = ms_advertID;
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
	
	public int getAd_count() {
		return ad_count;
	}

	public void setAd_count(int ad_count) {
		this.ad_count = ad_count;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public String getZeitpunkt() {
		return zeitpunkt;
	}

	public void setZeitpunkt(String zeitpunkt) {
		this.zeitpunkt = zeitpunkt;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getMemberid() {
		return memberid;
	}

	public void setMemberid(int memberid) {
		this.memberid = memberid;
	}

	public Advert(String text, int plz, String street,
			String name, String last_name, int id, double limit, double income,
			int distance, String zeitpunkt, Date datum, String category, int memberid){
	this.text = text;
	this.plz = plz;
	this.street = street;
	this.name = name;
	this.last_name = last_name;
	this.id = id;
	this.limit = limit;
	this.income = income;
	this.distance = distance;
	this.zeitpunkt = zeitpunkt;
	this.datum = datum;
	this.category = category;
	this.memberid = memberid;
	
	/*(int ad_count, int ad_id, int advertiser_id, Date date, int fk_time_id, double limit, double income, String text, int fk_category, boolean status, String fav_market, int buyer_id) { //int buyer_id
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
		this.buyer_id = buyer_id;*/
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
	                    	int statustest = 0;
	                    	if (status=true) statustest=1;
	                    	if (!(status=true)) statustest=0;
	                    	String sql = "UPDATE ad SET ad.date='" + new SimpleDateFormat("yyyy-MM-dd").format(date) + "', ad.fk_time_id ='" + fk_time_id + "', ad.limit='" + limit + "' , ad.income='" + income + "' , ad.text='" + text + "' , ad.fk_category='" + fk_category + "' , ad.status='" + statustest + "' , ad.fav_market='" + fav_market + "' WHERE ad.id ='" + ad_id + "'";


	                    	ps = con.prepareStatement(sql);  
	                        i = ps.executeUpdate();  
	                       
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

                        i = ps.executeUpdate();

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
	
	

	
	
	
	public String executeJob() {

	      PreparedStatement ps = null;  
	      Connection con = null;  
	      try {  
	                if (ds != null) {  
	                    con = ds.getConnection();  
	                    if (con != null) {  
	                    	//Mathias: musste die SQL ändern, da das nicht das macht, was es machen soll!
	      		          	// String sql = "UPDATE ad SET ad.buyer_id='" + buyer_id + "' WHERE ad.id ='" + ad_id + "'";
	                    	// Funktioniert im Mom nicht, da die Detailseite immer noch nicht funktioniert
	                    	String sql = "UPDATE ad SET ad.buyer_id='" + advertiser_id + "' WHERE ad.id ='" + ms_advertID + "'";

	                    	ps = con.prepareStatement(sql);  
	                       ps.executeUpdate();  
	          
	                                      
	                      System.out.println(advertiser_id);  
	                      System.out.println(ms_advertID); 
	  /* ***Mathias braucht den Platz für die Nachrichtenübermittlung **** */ 
	              
	      			Timestamp tstamp = new Timestamp(System.currentTimeMillis());		
	      			String datumConverter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tstamp);
	      			byte b = 1; 
	      		
	    		  String sqlmessage = "INSERT INTO `message` (`sender_id`, `receiver_id`, `time_sent`, `read`, `text`, `del_sender`, `del_receiver`, `advert`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";  
	    		  PreparedStatement psmessage = null; 
	    		  psmessage = con.prepareStatement(sqlmessage);   
	    		  
	    		  psmessage.setInt(1, advertiser_id);  
	    		  psmessage.setInt(2, ad_id);  
	    		  psmessage.setString(3, datumConverter); 
	    		  psmessage.setByte(4, b);  
	    		  psmessage.setInt(5, ms_advertID); 
	    		  psmessage.setByte(6, b);  
                  psmessage.setByte(7, b);  
                  psmessage.setByte(8, (byte)2); 
                  psmessage.executeUpdate();  
	              
				           	   int hvar = 0; 
				               PreparedStatement ps3 = null;
				               ResultSet rs2 = null;  
				         	   String sql3 = "SELECT COUNT(message.id) FROM message WHERE (message.receiver_id=" + ad_id + " AND message.sender_id=" + advertiser_id + ") OR (message.receiver_id=" + advertiser_id + " AND message.sender_id=" + ad_id + ");" ;
				               ps3 = con.prepareStatement(sql3);  
				               rs2 = ps3.executeQuery();
				               
				               
				               while (rs2.next()) {
				            	   hvar =  rs2.getInt("COUNT(message.id)");	 
				                }	
				               
				               if (hvar==1){
				            	   
				            	   Timestamp tstamp2 = new Timestamp(System.currentTimeMillis()-10000);		
				            	   String datumConverter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tstamp2);
				            	   byte c=2; 
				            	   
				            	   PreparedStatement ps4 = null;  
				            	   String sql4 = "INSERT INTO `message` (`sender_id`, `receiver_id`, `time_sent`, `read`, `text`, `del_sender`, `del_receiver`) VALUES (?, ?, ?, ?, ?, ?, ?);";  
				                   ps4 = con.prepareStatement(sql4);  
				                   ps4.setInt(1, ad_id);  
				                   ps4.setInt(2, advertiser_id);  
				                   ps4.setString(3, datumConverter2); 
				                   ps4.setByte(4, c);  
				                   ps4.setString(5, "SystemFirstMessage"); 
				                   ps4.setByte(6, c);  
				                   ps4.setByte(7, c);  
				
				                  ps4.executeUpdate();   
				
				               }
	              
	                        
	              
	                        
	                        
	                        
	                        
	                        
	                        
	/* ***Mathias ENDE **** */                       
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

 		return "advertdetail?faces-redirect=true";    
	}
	
	public ArrayList <Advert> getAd_idfromSQL(int ad_id){
			this.ad_id = ad_id; 
			System.out.println(ad_id);
			
            PreparedStatement ps = null;  
            Connection con = null;  
            ResultSet rs = null;  
  
            ownadverts.clear();
            
            if (ds != null) {  
                try {  
                    con = ds.getConnection();  
                    if (con != null) {  
                    	
                        String sql = "SELECT * FROM ad WHERE ad.id = '" + ad_id + "'";  
                        ps = con.prepareStatement(sql);  
                        rs = ps.executeQuery();
                        
                        //int i=0;
                        
                        while (rs.next()) {  
                        		//i++;
            
                        	
     //Mathias ergänzt	
	   								
                        		date =	rs.getDate("ad.date"); 
                        		fk_time_id=	rs.getInt("ad.fk_time_id"); 
                        		limit=	rs.getDouble("ad.limit"); 
                        		income=	rs.getDouble("ad.income"); 
                        		text=	rs.getString("ad.text"); 
                        		fk_category=	rs.getInt("ad.fk_category"); 
                        		status=	rs.getBoolean("ad.status"); 
                        		fav_market=	rs.getString("ad.fav_market"); 
                        		buyer_id=	rs.getInt("ad.buyer_id");
                        		
                        		
                        	/*	Advert TempObj = new Advert(i, 
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
                        	*/	
                        	}
                       
                    	}  
                } catch (SQLException sqle) {  
                    sqle.printStackTrace();  
                }    
        }
			return ownadverts;    
    }  
    
	
	public int getresulttodetail(FacesContext fc){
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String resulttodetail = params.get("resulttodetail");
		int value = Integer.valueOf(resulttodetail);
		return value;
	}
	
	public int getadvertid(FacesContext fc){
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String advertid = params.get("advertid");
		int value = Integer.valueOf(advertid);
		return value;
	}
	
	
	public String doJob() {  
		FacesContext fc = FacesContext.getCurrentInstance();
		this.ad_id = getresulttodetail(fc); 
		FacesContext fc2 = FacesContext.getCurrentInstance();
        ms_advertID = getadvertid(fc2);

		getAd_idfromSQL(ad_id);

		return "advertdetail?faces-redirect=true";
	} 
	

	
	public String changeData() {  
		FacesContext fc = FacesContext.getCurrentInstance();
		this.ad_id = getad_id(fc);
		
		
		//Mathias abgeändert
		// ownadverts = getAd_idfromSQL(ad_id);
		getAd_idfromSQL(ad_id);

		return "changeadvert";
	} 
		
	
	public int getad_id(FacesContext fc){
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String ad_id = params.get("ad_id");
		int value = Integer.valueOf(ad_id);
		return value;
 
	}
	
	
	public String deleteAd(){
      	PreparedStatement ps = null;  
        Connection con = null;  
    
        otheradverts.clear();
    if (ds != null) {  
        try {  
            con = ds.getConnection();  
            if (con != null) {
            	// String sql = "select id, advertiser_id, date, fk_time_id, limit, income, text, fk_category, status, fav_market, buyer_id
                String sql = "DELETE FROM ad WHERE ad.id= '" + ad_id + "'";  //buyer_id
                ps = con.prepareStatement(sql);  
                ps.executeUpdate();  
                
            }  
        } catch (SQLException sqle) {  
            sqle.printStackTrace();  
        }    
     }
     return "viewadverts";
	}
	
	/*public String showall(){
      	PreparedStatement ps = null;  
        Connection con = null;  
        ResultSet rs = null;
    
    if (ds != null) {  
        try {  
            con = ds.getConnection();  
            if (con != null) {
            	// String sql = "select id, advertiser_id, date, fk_time_id, limit, income, text, fk_category, status, fav_market, buyer_id
                String sql = "SELECT ad.id, ad.advertiser_id, ad.date, ad.fk_time_id, ad.limit, ad.income, ad.text, ad.fk_category, ad.status, ad.fav_market, ad.buyer_id FROM ad WHERE ad.status = '1' AND ad.buyer_id = 'null'";  //buyer_id
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
             
                	
                }
            }  
        } catch (SQLException sqle) {  
            sqle.printStackTrace();  
        }    
     }
     return "advert";
	}*/
	
/*	

			String sql = "SELECT member.id, member.name, member.last_name, ad.text, ad.date, member.plz, times_available.time, member.street, ad.id, ad.limit, ad.income, category.category from ad LEFT JOIN member ON ad.advertiser_id=member.id LEFT JOIN times_available ON ad.fk_time_id = times_available.id LEFT JOIN category ON ad.fk_category = category.id;";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			int i = 0;

			while (rs.next()) {
				SimpleSearchResults TempObj = new SimpleSearchResults(
						rs.getString("text"), rs.getInt("plz"),
						rs.getString("street"), rs.getString("name"),
						rs.getString("last_name"), rs.getInt("ad.id"),
						rs.getDouble("limit"), rs.getDouble("income"),
						0, rs.getString("time"), rs.getDate("date"),
						rs.getString("category"), rs.getInt("member.id"));
				// AdvertList.add(i, TempObj);
				AdvertList.add(TempObj);
				i = i + 1;
				Adressen.add(rs.getString("street").replaceAll("\\s",
						"+")
						+ "+"
						+ rs.getString("plz").replaceAll("\\s", "+"));
			}
			
*/
	
	
    public String showown(){
    	PreparedStatement ps = null;  
        Connection con = null;  
        ResultSet rs = null;
        ownadverts.clear();
        if (ds != null) {  
            try {  
                con = ds.getConnection();  
                if (con != null) {
                	System.out.println(advertiser_id);
                	// String sql = "SELECT ad.id, ad.advertiser_id, ad.date, ad.fk_time_id, ad.limit, ad.income, ad.text, ad.fk_category, ad.status, ad.fav_market, ad.buyer_id FROM ad WHERE ad.advertiser_id = '" + advertiser_id + "'";
                    String sql = "SELECT ad.id, ad.advertiser_id, ad.date, ad.fk_time_id, ad.limit, ad.income, ad.text, ad.fk_category, ad.status, ad.fav_market, ad.buyer_id, member.id, member.name, member.last_name, member.plz, times_available.time, member.street, category.category FROM ad LEFT JOIN member ON member.id=ad.advertiser_id LEFT JOIN times_available ON ad.fk_time_id = times_available.id LEFT JOIN category ON ad.fk_category = category.id WHERE ad.advertiser_id = '" + advertiser_id + "'"; 
                    ps = con.prepareStatement(sql);  
                    rs = ps.executeQuery();  
               
                    while (rs.next()) {  
                   
                    	Advert TempObj = new Advert(
								rs.getString("text"), rs.getInt("plz"),
								rs.getString("street"), rs.getString("name"),
								rs.getString("last_name"), rs.getInt("ad.id"),
								rs.getDouble("limit"), rs.getDouble("income"),
								0, rs.getString("time"), rs.getDate("date"),
								rs.getString("category"), rs.getInt("member.id"));
                    	
                    	/*Advert TempObj = new Advert(i,
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
                    								rs.getInt("ad.buyer_id"));*/
                    	
                    	ownadverts.add(TempObj);
                    	 
                    	
                    }
                }  
            } catch (SQLException sqle) {  
                sqle.printStackTrace();  
            }    
        }
        return "viewownadverts?faces-redirect=true";
    }
    public String showothers(){
	      	PreparedStatement ps = null;  
	        Connection con = null;  
	        ResultSet rs = null;
	        otheradverts.clear();
        if (ds != null) {  
            try {  
                con = ds.getConnection();  
                if (con != null) {

                	// String sql = "SELECT ad.id, ad.advertiser_id, ad.date, ad.fk_time_id, ad.limit, ad.income, ad.text, ad.fk_category, ad.status, ad.fav_market, ad.buyer_id FROM ad WHERE ad.advertiser_id = '" + buyer_id + "'";
                	String sql = "SELECT ad.id, ad.advertiser_id, ad.date, ad.fk_time_id, ad.limit, ad.income, ad.text, ad.fk_category, ad.status, ad.fav_market, ad.buyer_id, member.id, member.name, member.last_name, member.plz, times_available.time, member.street, category.category FROM ad LEFT JOIN member ON ad.advertiser_id=member.id LEFT JOIN times_available ON ad.fk_time_id = times_available.id LEFT JOIN category ON ad.fk_category = category.id WHERE ad.buyer_id= '" + advertiser_id + "'";
                    ps = con.prepareStatement(sql);  
                    rs = ps.executeQuery();  
                    
                 
                    while (rs.next()) {  
                  
                    	
                    	Advert TempObj = new Advert(
								rs.getString("text"), rs.getInt("plz"),
								rs.getString("street"), rs.getString("name"),
								rs.getString("last_name"), rs.getInt("ad.id"),
								rs.getDouble("limit"), rs.getDouble("income"),
								0, rs.getString("time"), rs.getDate("date"),
								rs.getString("category"), rs.getInt("member.id"));
                    	
                    	/*Advert TempObj = new Advert(i,
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
                    								rs.getInt("ad.buyer_id"));*/
                    	
                    	otheradverts.add(TempObj);
                 
                    	
                    }
                }  
            } catch (SQLException sqle) {  
                sqle.printStackTrace();  
            }    
        }
        return "viewotheradverts?faces-redirect=true";
    }
    	
    
    public void showAll(TabChangeEvent event){
    	
        FacesMessage msg = new FacesMessage(event.getTab().getTitle());
        
     //   String jo = FacesContext.getCurrentInstance().addMessage(null, msg);
      //  String jo = FacesContext.getCurrentInstance(msg);
        
          System.out.println(msg);
    	   
    /*	   FacesContext.getCurrentInstance().addMessage(null, msg);
    	
    	
           FacesContext fc = FacesContext.getCurrentInstance();
   		this.ms_senderId = getstartmessage(fc);
    	
      	PreparedStatement ps = null;  
        Connection con = null;  
        ResultSet rs = null;
        otheradverts.clear();
    if (ds != null) {  
        try {  
            con = ds.getConnection();  
            if (con != null) {

            	// String sql = "SELECT ad.id, ad.advertiser_id, ad.date, ad.fk_time_id, ad.limit, ad.income, ad.text, ad.fk_category, ad.status, ad.fav_market, ad.buyer_id FROM ad WHERE ad.advertiser_id = '" + buyer_id + "'";
            	String sql = "SELECT ad.id, ad.advertiser_id, ad.date, ad.fk_time_id, ad.limit, ad.income, ad.text, ad.fk_category, ad.status, ad.fav_market, ad.buyer_id, member.id, member.name, member.last_name, member.plz, times_available.time, member.street, category.category FROM ad LEFT JOIN member ON ad.advertiser_id=member.id LEFT JOIN times_available ON ad.fk_time_id = times_available.id LEFT JOIN category ON ad.fk_category = category.id WHERE ad.buyer_id= '" + advertiser_id + "'";
                ps = con.prepareStatement(sql);  
                rs = ps.executeQuery();  
                
             
                while (rs.next()) {  
              
                	
                	Advert TempObj = new Advert(
							rs.getString("text"), rs.getInt("plz"),
							rs.getString("street"), rs.getString("name"),
							rs.getString("last_name"), rs.getInt("ad.id"),
							rs.getDouble("limit"), rs.getDouble("income"),
							0, rs.getString("time"), rs.getDate("date"),
							rs.getString("category"), rs.getInt("member.id"));
                	
                	
                	otheradverts.add(TempObj);
             
                	
                }
            }  
        } catch (SQLException sqle) {  
            sqle.printStackTrace();  
        }    
    }*/
}
	
}  
