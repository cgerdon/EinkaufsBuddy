package Beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.text.SimpleDateFormat;
//import java.util.Date;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


@ManagedBean(name = "message") 
@SessionScoped  

public class message implements Serializable {
	
	private static final long serialVersionUID = 2788720637477494048L;
	
	private Date date;
	private int ms_id;
	private String ms_time;
    private String ms_text;

  //Muss mal überprüfen, ob das auch das richtige ist
    @ManagedProperty(value="#{user.id}")
	private int ms_receiverId;	
	@ManagedProperty(value="#{user.firstName}")
    private String ms_receiverFirstName; 
	@ManagedProperty(value="#{user.lastName}")	
	private String ms_receiverLastName;  
	
//    private byte[] ms_receiverPicture;
   
	private int ms_senderId;	
	private String ms_senderFirstName;  
    private String ms_senderLastName;
    
    
    String messagetext;
    
    /*   private byte[] ms_senderPicture;    
*/
	public message(int ms_id, String ms_time, int ms_senderId, String ms_receiverFirstName, String ms_receiverLastName, String ms_text) {
		super();
		this.ms_id = ms_id;
		this.ms_time=ms_time;
		this.ms_senderId = ms_senderId;
		this.ms_receiverFirstName =ms_receiverFirstName;
		this.ms_receiverLastName=ms_receiverLastName;
		this.ms_text = ms_text;
	}

    DataSource ds;
    
    public message() {  
        try {  
            Context ctx = new InitialContext();  
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/database");  
        } catch (NamingException e) {  
            e.printStackTrace();  
        }  
    }  
    
 

	public String getMessagetext() {
		return messagetext;
	}

	public void setMessagetext(String messagetext) {
		this.messagetext = messagetext;
	}



	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMs_time() {
		return ms_time;
	}

	public void setMs_time(String ms_time) {
		this.ms_time = ms_time;
	}

	public int getMs_id() {
		return ms_id;
	}

	public void setMs_id(int ms_id) {
		this.ms_id = ms_id;
	}

	public String getMs_text() {
		return ms_text;
	}

	public void setMs_text(String ms_text) {
		this.ms_text = ms_text;
	}

	public int getMs_senderId() {
		return ms_senderId;
	}

	public String getMs_senderFirstName() {
		return ms_senderFirstName;
	}

	public void setMs_senderFirstName(String ms_senderFirstName) {
		this.ms_senderFirstName = ms_senderFirstName;
	}

	public String getMs_senderLastName() {
		return ms_senderLastName;
	}

	public void setMs_senderLastName(String ms_senderLastName) {
		this.ms_senderLastName = ms_senderLastName;
	}

	public int getMs_receiverId() {
		return ms_receiverId;
	}

	public void setMs_receiverId(int ms_receiverId) {
		this.ms_receiverId = ms_receiverId;
	}

	public String getMs_receiverFirstName() {
		return ms_receiverFirstName;
	}

	public void setMs_receiverFirstName(String ms_receiverFirstName) {
		this.ms_receiverFirstName = ms_receiverFirstName;
	}

	public String getMs_receiverLastName() {
		return ms_receiverLastName;
	}


	public void setMs_receiverLastName(String ms_receiverLastName) {
		this.ms_receiverLastName = ms_receiverLastName;
	}


	public void setMs_senderId(int ms_senderId) {
		this.ms_senderId = ms_senderId;
	}

	ArrayList<message> messagedetails = new ArrayList<message>();
	ArrayList<message> messageoverview = new ArrayList<message>();
	

	public ArrayList<message> getMessagedetails() {
		return messagedetails;
	}


	public void setMessagedetails(ArrayList<message> messagedetails) {
		this.messagedetails = messagedetails;
	}

	
	public ArrayList<message> getMessageoverview() {
		return messageoverview;
	}


	public void setMessageoverview(ArrayList<message> messageoverview) {
		this.messageoverview = messageoverview;
	}
	

	public String showAllMessage() {
		PreparedStatement ps = null;  
        Connection con = null;  
        ResultSet rs = null;  
        
        if (ds != null) {  
            try {  
                con = ds.getConnection();  
                if (con != null) {  
                    String sql = "SELECT message.receiver_id, member.name, member.last_name FROM message JOIN member ON message.receiver_id=member.id WHERE message.receiver_id= " + this.ms_receiverId + " OR message.sender_id= " + this.ms_receiverId + " GROUP BY member.name, member.last_name;" ;  
                    ps = con.prepareStatement(sql);  
                    rs = ps.executeQuery();
                    messageoverview.clear();
                    while (rs.next()) {  

                    	message TempObj = new message(0, null, rs.getInt("message.receiver_id"), rs.getString("member.name"), rs.getString("member.last_name"), null);
                    	messageoverview.add(TempObj);
                    	
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
        
        	return "messageoverview";   
    }      
	
	
   public String DateConString(String dateString){
	   String jahr;
	   String monat;
	   String tag;
	   String uhrzeit; 
	   
	   jahr = dateString.substring(0, dateString.length()-17);
	   monat = dateString.substring(5, dateString.length()-14);
	   tag = dateString.substring(8, dateString.length()-11);
	   uhrzeit= dateString.substring(11, dateString.length()-5);
	   
	   return tag + "." + monat + "." + jahr + " - " + uhrzeit ;
   }

   public void giveSenderName(){
	   
	   PreparedStatement ps = null;  
       Connection con = null;  
       ResultSet rs = null;  

       if (ds != null) {  
           try {  
               con = ds.getConnection();  
               if (con != null) {  
               	   String sql = "SELECT member.name, member.last_name FROM member WHERE member.id=" + this.ms_senderId + ";" ;  
                      ps = con.prepareStatement(sql);  
                      rs = ps.executeQuery();
                      
                      ms_senderFirstName = rs.getString("member.name"); 
                      ms_senderLastName = rs.getString("member.last_name"); 
                   }
               }  
            catch (SQLException sqle) {  
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
   
	public ArrayList <message> giveMessagedetailfromSQL(int ms_senderId){
		this.ms_senderId = ms_senderId;
		
		//giveSenderName();
		
        PreparedStatement ps = null;  
        Connection con = null;  
        ResultSet rs = null;  

        if (ds != null) {  
            try {  
                con = ds.getConnection();  
                if (con != null) {  
                	   String sql = "SELECT message.id, message.time_sent, message.sender_id, member.name, member.last_name, message.text FROM message JOIN member ON message.sender_id=member.id WHERE (message.receiver_id=" + this.ms_senderId + " AND message.sender_id=" + this.ms_receiverId + ") OR (message.receiver_id=" + this.ms_receiverId + " AND message.sender_id=" + this.ms_senderId + ") ORDER BY message.time_sent DESC" ;  
                       ps = con.prepareStatement(sql);  
                       rs = ps.executeQuery();
                       
                       messagedetails.clear();
                       while (rs.next()) {  
                       	
                    	   ms_time = DateConString(rs.getString("message.time_sent"));
                        	
                    	   message TempObj = new message(rs.getInt("message.id"), ms_time, rs.getInt("message.sender_id"), rs.getString("member.name"), rs.getString("member.last_name"), rs.getString("message.text"));
                    	   messagedetails.add(TempObj);         
						
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
		
        return messagedetails;
	}

	public int getnamevonmessage(FacesContext fc){
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		String namevonmessage = params.get("namevonmessage");
		int value = Integer.valueOf(namevonmessage);
		return value;
 	}
	
	public String showMessage() { 
			FacesContext fc = FacesContext.getCurrentInstance();
			this.ms_senderId = getnamevonmessage(fc);
			 
			messagedetails = giveMessagedetailfromSQL(ms_senderId);
            
            return "messagedetail";
        }
	
/*	public int getempfaenger(FacesContext fc){
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		String empfaenger = params.get("empfaenger");
		int value = Integer.valueOf(empfaenger);
		return value;
 	}
*/
	//public void writeMessage(String ms_text) { 
			public void writeMessage() { 	
			
	//	FacesContext fc = FacesContext.getCurrentInstance();
	//	this.ms_senderId = getempfaenger(fc);
		
		Timestamp tstamp = new Timestamp(System.currentTimeMillis());		
		String datumConverter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tstamp);

            PreparedStatement ps = null;  
            Connection con = null;  
            
            try {  
                if (ds != null) {  
                    con = ds.getConnection();  
                    if (con != null) { 
                        String sql = "INSERT INTO message (sender_id, receiver_id, time_sent, ad_id, text) VALUES(?,?,?,?,?)";  
                        ps = con.prepareStatement(sql);  
                        	ps.setInt(1, ms_receiverId);
	                        ps.setInt(2, ms_senderId);  
	                        ps.setString(3, datumConverter);
	                        ps.setInt(4, 3);  
	                        ps.setString(5, ms_text); 
                        ps.executeUpdate();    
                        
                       PreparedStatement ps2 = null;  
                       ResultSet rs = null;  
                 	   String sql2 = "SELECT message.id, message.time_sent, message.sender_id, member.name, member.last_name, message.text FROM message JOIN member ON message.sender_id=member.id WHERE (message.receiver_id=" + this.ms_senderId + " AND message.sender_id=" + this.ms_receiverId + ") OR (message.receiver_id=" + this.ms_receiverId + " AND message.sender_id=" + this.ms_senderId + ") ORDER BY message.time_sent DESC" ;  
                       ps2 = con.prepareStatement(sql2);  
                       rs = ps2.executeQuery();
                       
                       messagedetails.clear();
                       while (rs.next()) {  
                       	
                    	   ms_time = DateConString(rs.getString("message.time_sent"));
                        	
                    	   message TempObj = new message(rs.getInt("message.id"), ms_time, rs.getInt("message.sender_id"), rs.getString("member.name"), rs.getString("member.last_name"), rs.getString("message.text"));
                    	   messagedetails.add(TempObj);  
                        
                       }
                        
                        
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
            
       //  showMessage(ms_senderId);
    }   

	public String showMessage(int ms_senderId) {  
		this.ms_senderId = ms_senderId;
		
		messagedetails = giveMessagedetailfromSQL(ms_senderId);
		
		return "messagedetail";
	}  
	
	
	public int getstartmessage(FacesContext fc){
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		String startmessage = params.get("startmessage");
		int value = Integer.valueOf(startmessage);
		return value;
 	}

	public String startMessage() {  
		FacesContext fc = FacesContext.getCurrentInstance();
		this.ms_senderId = getstartmessage(fc);
		
		messagedetails = giveMessagedetailfromSQL(ms_senderId);
		
        return "messagedetailvonsuche";    
    }   

	
		public void reloadMessage() {  
				
		giveMessagedetailfromSQL(ms_senderId);
            
        }  

}
