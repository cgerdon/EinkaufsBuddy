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
    private int idvombesitzer;
    public int getIdvombesitzer() {
		return idvombesitzer;
	}

	public void setIdvombesitzer(int idvombesitzer) {
		this.idvombesitzer = idvombesitzer;
	}

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
	
	private String zeitpunkt;
	private Date datum;
	private String category;
	private int memberid;
	private int ad_status;
	private int ad_acc;
	private int ad_buyerID;
	private int ad_rate;
	//Mathias 
	private int ms_advertID;
	//Christoph
	int buyer_id_todb;
	
	public int getBuyer_id_todb() {
		return buyer_id_todb;
	}

	public void setBuyer_id_todb(int buyer_id_todb) {
		this.buyer_id_todb = buyer_id_todb;
	}

	public int getAdvertiser_id_todb() {
		return advertiser_id_todb;
	}

	public void setAdvertiser_id_todb(int advertiser_id_todb) {
		this.advertiser_id_todb = advertiser_id_todb;
	}

	public int getRating_todb() {
		return rating_todb;
	}

	public void setRating_todb(int rating_todb) {
		this.rating_todb = rating_todb;
	}

	public String getText_todb() {
		return Text_todb;
	}

	public void setText_todb(String text_todb) {
		Text_todb = text_todb;
	}

	public int getAdid_todb() {
		return adid_todb;
	}

	public void setAdid_todb(int adid_todb) {
		this.adid_todb = adid_todb;
	}

	public int getType_todb() {
		return type_todb;
	}

	public void setType_todb(int type_todb) {
		this.type_todb = type_todb;
	}

	int advertiser_id_todb;
	int rating_todb;
	String Text_todb;
	int adid_todb;
	int type_todb;
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
	
	public String startrating(int buyerid, int adid, int advid, int type){
	
		buyer_id_todb = buyerid;
		advertiser_id_todb = advid;
		adid_todb = adid;
		type_todb = type;
		System.out.println("Buyer ID ist " + buyer_id_todb + " und Advertiser ID ist " + advertiser_id_todb + " und adid ist "+ adid_todb + " der type ist " + type_todb);
		
		return "ratingadvert?faces-redirect=true";
	}

	public String showAd(int ad_id){
		FacesContext fc2 = FacesContext.getCurrentInstance();
		ms_advertID = getadvertid(fc2);
		FacesContext fc = FacesContext.getCurrentInstance();
		this.idvombesitzer = getresulttodetail(fc); 
		PreparedStatement ps = null;  
        Connection con = null;  
        ResultSet rs = null;
        
        if (ds != null) {  
            try {  
                con = ds.getConnection();  
                if (con != null) {
                	
                    String sql = "SELECT ad.id, ad.advertiser_id, ad.date, ad.fk_time_id, ad.limit, ad.income, ad.text, ad.fk_category, ad.status, ad.fav_market, ad.buyer_id, member.id, member.name, member.last_name, member.plz, times_available.time, member.street, category.category FROM ad LEFT JOIN member ON member.id=ad.advertiser_id LEFT JOIN times_available ON ad.fk_time_id = times_available.id LEFT JOIN category ON ad.fk_category = category.id WHERE ad.id = '" + ad_id + "'"; 
                    System.out.println(sql);
                    ps = con.prepareStatement(sql);  
                    rs = ps.executeQuery();  
               
                    while (rs.next()) {  
                         
                    	datum = rs.getDate("ad.date");
                    	ad_id = rs.getInt("ad.id");
                    	income = rs.getDouble("ad.income");
                    	limit = rs.getDouble("ad.limit");
                    	name = rs.getString("member.name");
                    	last_name = rs.getString("member.last_name");
                    	text = rs.getString("ad.text");
                    	zeitpunkt = rs.getString("times_available.time");
                    	category = rs.getString("category.category");
                    	//advertiser_id = rs.getInt("advertiser_id");
                    	//System.out.println("asdasdasdasdsa "+ advertiser_id);
                    	buyer_id = rs.getInt("buyer_id");
                    	
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
//		AdShow.setDatum(rs.getDate("ad.date"));
//		AdShow.setId(rs.getInt("ad.id"));
//		AdShow.setIncome(rs.getDouble("ad.income"));
//		AdShow.setKategorie(rs.getString("category.category"));
//		AdShow.setLimit(rs.getDouble("ad.limit"));
//		AdShow.setName(rs.getString("member.last_name"));
//		AdShow.setText(rs.getString("ad.text"));
//		AdShow.setVorname(rs.getString("member.name"));
//		AdShow.setZeitpunkt(rs.getString("times_available.time"));
		
        }
		return "advertdetail?faces-redirect=true";
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




	public int getAd_rate() {
		return ad_rate;
	}

	public void setAd_rate(int ad_rate) {
		this.ad_rate = ad_rate;
	}

	public int getAd_acc() {
		return ad_acc;
	}

	public void setAd_acc(int ad_acc) {
		this.ad_acc = ad_acc;
	}

	public int getAd_buyerID() {
		return ad_buyerID;
	}

	public void setAd_buyerID(int ad_buyerID) {
		this.ad_buyerID = ad_buyerID;
	}

	public int getAd_status() {
		return ad_status;
	}

	public void setAd_status(int ad_status) {
		this.ad_status = ad_status;
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
	
	ArrayList<Advert> alladverts = new ArrayList<Advert>();

	
	public ArrayList<Advert> getAlladverts() {
		return alladverts;
	}

	public void setAlladverts(ArrayList<Advert> alladverts) {
		this.alladverts = alladverts;
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
			int distance, String zeitpunkt, Date datum, String category, int memberid, int ad_status, int ad_buyerID, int ad_acc, int ad_rate){
	this.text = text;
	this.plz = plz;
	this.street = street;
	this.name = name;
	this.last_name = last_name;
	this.id = id;
	this.limit = limit;
	this.income = income;
	this.zeitpunkt = zeitpunkt;
	this.datum = datum;
	this.category = category;
	this.memberid = memberid;
	this.ad_status = ad_status; 
	this.ad_buyerID = ad_buyerID;
	this.ad_acc = ad_acc;
	this.ad_rate = ad_rate;
	
	
	
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
         
 
            return "viewadverts?faces-redirect=true";  
       
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
	    		  psmessage.setInt(2, idvombesitzer);  
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
				                   ps4.setInt(1, idvombesitzer);  
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
	
	public ArrayList<Advert> getAd_idfromSQL(int ad_id){
			
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
                    	
                        String sql = "SELECT member.name, member.last_name, ad.fk_category, ad.fk_time_id, ad.id, times_available.time, ad.accepted_id, ad.advertiser_id, ad.date, ad.`limit`, ad.income, ad.text, ad.`status`, ad.fav_market, category.category FROM ad  LEFT JOIN category ON ad.fk_category = category.id LEFT JOIN member on ad.advertiser_id = member.id LEFT JOIN times_available ON ad.fk_time_id = times_available.id WHERE ad.id = '" + ad_id + "'";  
                        ps = con.prepareStatement(sql);  
                        rs = ps.executeQuery();
 
                        while (rs.next()) {  
                        		//i++;
            
                        	
     //Mathias ergänzt			
	   							System.out.println("ab hier");	
	   							ad_id = rs.getInt("ad.id");
	   							date =	rs.getDate("ad.date"); 
                        		fk_time_id=	rs.getInt("ad.fk_time_id"); 
                        		limit=	rs.getDouble("ad.limit"); 
                        		income=	rs.getDouble("ad.income"); 
                        		text=	rs.getString("ad.text"); 
                        		fk_category=	rs.getInt("ad.fk_category"); 
                        		status=	rs.getBoolean("ad.status"); 
                        		fav_market=	rs.getString("ad.fav_market"); 
                        		//buyer_id=	rs.getInt("ad.buyer_id");
                        		
                        		
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

		getAd_idfromSQL(ms_advertID);

		return "advertdetail?faces-redirect=true";
	} 
	

	
	public String changeData() {  
		FacesContext fc = FacesContext.getCurrentInstance();
		this.ad_id = getad_id(fc);
		
		
		//Mathias abgeändert
		// ownadverts = getAd_idfromSQL(ad_id);
		getAd_idfromSQL(ad_id);

		return "changeadvert?faces-redirect=true";
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
	
	/*
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
                    								rs.getInt("ad.buyer_id"));
                    	
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
                    								rs.getInt("ad.buyer_id"));
                    	
                    	otheradverts.add(TempObj);
                 
                    	
                    }
                }  
            } catch (SQLException sqle) {  
                sqle.printStackTrace();  
            }    
        }
        return "viewotheradverts?faces-redirect=true";
    }
    	*/
    
    public void showAll(TabChangeEvent event){
    	
    	String name = event.getTab().getTitle();
        Connection con = null;  

        alladverts.clear();
    if (ds != null) {  
        try {  
            con = ds.getConnection();  
            if (con != null) {

            	if (name.equals("Eigene Inserate")) {
            		String sql = "SELECT IF(ad.buyer_id>0,1,0) AS buyerinter, ad.id, ad.accepted_id, ad.status, ad.advertiserrate, ad.advertiser_id, ad.date, ad.fk_time_id, ad.limit, ad.income, ad.text, ad.fk_category, ad.fav_market, ad.buyer_id, member.id, member.name, member.last_name, member.plz, times_available.time, member.street, category.category FROM ad LEFT JOIN member ON member.id=ad.advertiser_id LEFT JOIN times_available ON ad.fk_time_id = times_available.id LEFT JOIN category ON ad.fk_category = category.id WHERE ad.advertiser_id = '" + advertiser_id + "' ORDER BY status ASC,buyerinter ASC, accepted_id ASC, advertiserrate ASC, date ASC; "; 
            		PreparedStatement ps = null;  
            		ps = con.prepareStatement(sql); 
                    ResultSet rs = null;
                    rs = ps.executeQuery();  
                    
                    while (rs.next()) {  
                    	Advert TempObj = new Advert(
    							rs.getString("text"), rs.getInt("plz"),
    							rs.getString("street"), rs.getString("name"),
    							rs.getString("last_name"), rs.getInt("ad.id"),
    							rs.getDouble("limit"), rs.getDouble("income"),
    							0, rs.getString("time"), rs.getDate("date"),
    							rs.getString("category"), rs.getInt("member.id"),
    							rs.getInt("ad.status"),rs.getInt("ad.buyer_id"),
    							rs.getInt("ad.accepted_id"), rs.getInt("ad.advertiserrate") );
                    	alladverts.add(TempObj);
                    
                    }
                    ps.close();  

            	}
            	
            	if (name.equals("Fremde Inserate")) {
            		String sql = "SELECT ad.id, ad.accepted_id, ad.status, ad.buyerrate, ad.advertiser_id, ad.date, ad.fk_time_id, ad.limit, ad.income, ad.text, ad.fk_category, ad.fav_market, ad.buyer_id, member.id, member.name, member.last_name, member.plz, times_available.time, member.street, category.category FROM ad LEFT JOIN member ON ad.advertiser_id=member.id LEFT JOIN times_available ON ad.fk_time_id = times_available.id LEFT JOIN category ON ad.fk_category = category.id WHERE ad.buyer_id= '" + advertiser_id + "' ORDER BY accepted_id ASC, buyerrate ASC, date ASC;";
            		PreparedStatement ps = null;  
            		ps = con.prepareStatement(sql); 
                    ResultSet rs = null;
                    rs = ps.executeQuery();  
                    
                    while (rs.next()) {  
                    	Advert TempObj = new Advert(
    							rs.getString("text"), rs.getInt("plz"),
    							rs.getString("street"), rs.getString("name"),
    							rs.getString("last_name"), rs.getInt("ad.id"),
    							rs.getDouble("limit"), rs.getDouble("income"),
    							0, rs.getString("time"), rs.getDate("date"),
    							rs.getString("category"), rs.getInt("member.id"),
    							rs.getInt("ad.status"),rs.getInt("ad.buyer_id"),
    							rs.getInt("ad.accepted_id"), rs.getInt("ad.buyerrate") );
                    	alladverts.add(TempObj);
                    	
                    }
                    ps.close();  

            	}


            }  
        } catch (SQLException sqle) {  
            sqle.printStackTrace();  
        } finally {  
            try {  
                con.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }       
  
    }
	
}  
    
 public void showAllOhneEvent(){
    	
     PreparedStatement ps = null;  
        Connection con = null;  
        ResultSet rs = null;
        alladverts.clear();
    if (ds != null) {  
        try {  
            con = ds.getConnection();  
            if (con != null) {

            	String sql = "SELECT IF(ad.buyer_id>0,1,0) AS buyerinter, ad.id, ad.accepted_id, ad.status, ad.advertiserrate, ad.advertiser_id, ad.date, ad.fk_time_id, ad.limit, ad.income, ad.text, ad.fk_category, ad.fav_market, ad.buyer_id, member.id, member.name, member.last_name, member.plz, times_available.time, member.street, category.category FROM ad LEFT JOIN member ON member.id=ad.advertiser_id LEFT JOIN times_available ON ad.fk_time_id = times_available.id LEFT JOIN category ON ad.fk_category = category.id WHERE ad.advertiser_id = '" + advertiser_id + "' ORDER BY status ASC, buyerinter ASC, accepted_id ASC, advertiserrate ASC, date ASC; "; 
            	ps = con.prepareStatement(sql);  
                rs = ps.executeQuery();  
                
             
                while (rs.next()) {  
              
                	
                	Advert TempObj = new Advert(
							rs.getString("text"), rs.getInt("plz"),
							rs.getString("street"), rs.getString("name"),
							rs.getString("last_name"), rs.getInt("ad.id"),
							rs.getDouble("limit"), rs.getDouble("income"),
							0, rs.getString("time"), rs.getDate("date"),
							rs.getString("category"), rs.getInt("member.id"),
							rs.getInt("ad.status"), rs.getInt("ad.buyer_id"),
							rs.getInt("ad.accepted_id"), rs.getInt("ad.advertiserrate") );
                	
                	
                	alladverts.add(TempObj);
             
                	
                }
            }  
        } catch (SQLException sqle) {  
            sqle.printStackTrace();  
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
   
 
 public String adCancel(int advertID, int memberID){
	 
        Connection con = null;  
        PreparedStatement ps = null; 
        PreparedStatement ps2 = null; 
        alladverts.clear();
    if (ds != null) {  
        try {  
            con = ds.getConnection();  
            if (con != null) {
             
            	String sql = "UPDATE ad SET ad.buyer_id= null WHERE ad.id =" + advertID + ";";
            	ps = con.prepareStatement(sql);  
            	ps.executeUpdate();  

            	String sql2 = "UPDATE message SET message.advertanab=4 WHERE message.text =" + advertID + " AND message.receiver_id= " + memberID + " AND message.sender_id =" + advertiser_id + " AND message.advert = 2 ;" ;
            	ps2 = con.prepareStatement(sql2);  
            	ps2.executeUpdate();            
                	
                
            }  
        } catch (SQLException sqle) {  
            sqle.printStackTrace();  
        } finally {  
            try {  
                con.close();  
                ps.close(); 
                ps2.close(); 
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }      
  
    }
    
	return "viewadverts?faces-redirect=true";
}  
 
 

 public String buyerCancel(int advertID){

        Connection con = null;  
        PreparedStatement ps = null; 
        PreparedStatement ps2 = null; 
        alladverts.clear();
    if (ds != null) {  
        try {  
            con = ds.getConnection();  
            if (con != null) {
             
            	String sql = "UPDATE ad SET ad.buyer_id= null WHERE ad.id =" + advertID + ";";
            	ps = con.prepareStatement(sql);  
            	ps.executeUpdate();  

            	String sql2 = "UPDATE message SET message.advertanab=3 WHERE message.text =" + advertID + " AND message.receiver_id =" + advertiser_id + " AND message.advert = 2 ;" ;
            	ps2 = con.prepareStatement(sql2);  
            	ps2.executeUpdate();            
                	
                
            }  
        } catch (SQLException sqle) {  
            sqle.printStackTrace();  
        } finally {  
            try {  
                con.close();  
                ps.close(); 
                ps2.close(); 
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }      
  
    }
    
	return "viewadverts?faces-redirect=true";
}  
 
 public String addelete(int advertID){

     Connection con = null;  
     PreparedStatement ps = null; 

     alladverts.clear();
 if (ds != null) {  
     try {  
         con = ds.getConnection();  
         if (con != null) {
          
         	String sql = "UPDATE ad SET ad.buyer_id= null, ad.accepted_id= 9 WHERE ad.id =" + advertID + " ;";
         	ps = con.prepareStatement(sql);  
         	ps.executeUpdate();            
             	
             
         }  
     } catch (SQLException sqle) {  
         sqle.printStackTrace();  
     } finally {  
         try {  
             con.close();  
             ps.close(); 

         } catch (Exception e) {  
             e.printStackTrace();  
         }  
     }      

 }
 
	return "viewadverts?faces-redirect=true";
}  

 public String  adcanceldelete(int advertID){

     Connection con = null;  
     PreparedStatement ps = null; 
     PreparedStatement ps2 = null; 
     alladverts.clear();
 if (ds != null) {  
     try {  
         con = ds.getConnection();  
         if (con != null) {
          
          	String sql = "UPDATE ad SET ad.buyer_id= null, ad.accepted_id= 9 WHERE ad.id =" + advertID + ";";
         	ps = con.prepareStatement(sql);  
         	ps.executeUpdate();  

         	String sql2 = "UPDATE message SET message.advertanab=3 WHERE message.text =" + advertID + " AND message.receiver_id =" + advertiser_id + " AND message.advert = 2 ;" ;
         	ps2 = con.prepareStatement(sql2);  
         	ps2.executeUpdate();            
             	
             
         }  
     } catch (SQLException sqle) {  
         sqle.printStackTrace();  
     } finally {  
         try {  
             con.close();  
             ps.close(); 
             ps2.close(); 
         } catch (Exception e) {  
             e.printStackTrace();  
         }  
     }      

 }
 
	return "viewadverts?faces-redirect=true";
}  
 

 public String addeactive(int advertID){

     Connection con = null;  
     PreparedStatement ps = null; 

     alladverts.clear();
 if (ds != null) {  
     try {  
         con = ds.getConnection();  
         if (con != null) {
          
         	String sql = "UPDATE ad SET ad.status= 0 WHERE ad.id =" + advertID + ";";
         	ps = con.prepareStatement(sql);  
         	ps.executeUpdate();            
             	
             
         }  
     } catch (SQLException sqle) {  
         sqle.printStackTrace();  
     } finally {  
         try {  
             con.close();  
             ps.close(); 

         } catch (Exception e) {  
             e.printStackTrace();  
         }  
     }      

 }
 
	return "viewadverts?faces-redirect=true";
}  
 
 
}
