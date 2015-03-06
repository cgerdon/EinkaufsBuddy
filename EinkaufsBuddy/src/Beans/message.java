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
    private String ms_senderName;
    private String ms_senderNamekurz;

    private int ms_anzahl;
    private int ms_gesamtanzahl;
    private int ms_advertId;
    private int currentdelete;
    private String messagetext;
        
    /*   private byte[] ms_senderPicture;    
*/
	public message(int ms_anzahl, int ms_id, String ms_time, int ms_senderId, String ms_senderNamekurz, String ms_receiverFirstName, String ms_receiverLastName, String ms_text, int ms_advertId) {
		super();
		this.ms_anzahl=ms_anzahl;
		this.ms_id = ms_id;
		this.ms_time=ms_time;
		this.ms_senderId = ms_senderId;
		this.ms_senderNamekurz = ms_senderNamekurz;
		this.ms_receiverFirstName =ms_receiverFirstName;
		this.ms_receiverLastName=ms_receiverLastName;
		this.ms_text = ms_text;
		this.ms_advertId = ms_advertId;
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
    
    

    
	public int getCurrentdelete() {
		return currentdelete;
	}

	public void setCurrentdelete(int currentdelete) {
		this.currentdelete = currentdelete;
	}

	public int getMs_advertId() {
		return ms_advertId;
	}

	public void setMs_advertId(int ms_advertId) {
		this.ms_advertId = ms_advertId;
	}

	public int getMs_gesamtanzahl() {
		return ms_gesamtanzahl;
	}

	public void setMs_gesamtanzahl(int ms_gesamtanzahl) {
		this.ms_gesamtanzahl = ms_gesamtanzahl;
	}

	public String getMs_senderNamekurz() {
		return ms_senderNamekurz;
	}

	public void setMs_senderNamekurz(String ms_senderNamekurz) {
		this.ms_senderNamekurz = ms_senderNamekurz;
	}


	public String getMs_senderName() {
		return ms_senderName;
	}

	public void setMs_senderName(String ms_senderName) {
		this.ms_senderName = ms_senderName;
	}

	public int getMs_anzahl() {
		return ms_anzahl;
	}

	public void setMs_anzahl(int ms_anzahl) {
		this.ms_anzahl = ms_anzahl;
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
		Connection con = null;
		PreparedStatement ps = null;  
        ResultSet rs = null;
    	
        if (ds != null) {  
            try {  
                con = ds.getConnection();  
                if (con != null) { 
                	String sql = "SELECT member.id, member.name, member.last_name, table1.helpmaxt, table2.ungelesen, table1.helpid FROM member, (SELECT tablezeit.maxtid AS helpid, tablezeit.sender_id AS helpsender, tablezeit.receiver_id AS helpreceiver, tablezeit.maxthelp AS helpmaxt FROM (SELECT MAX(message.id) AS maxtid, sender_id, receiver_id, MAX(time_sent) AS maxthelp FROM message WHERE receiver_id= " + ms_receiverId + " OR sender_id= " + ms_receiverId + " GROUP BY sender_id,receiver_id) AS tablezeit JOIN (SELECT sender_id, receiver_id, MAX(time_sent) AS maxthelp2 FROM message WHERE receiver_id= " + ms_receiverId + " OR sender_id= " + ms_receiverId + " GROUP BY sender_id,receiver_id) AS tablezeit2 ON tablezeit2.sender_id = tablezeit.receiver_id AND tablezeit2.receiver_id = tablezeit.sender_id LEFT JOIN message ON maxtid= message.id WHERE tablezeit.maxthelp > tablezeit2.maxthelp2 AND NOT ((tablezeit.sender_id= " + ms_receiverId + " AND del_sender= 2 ) OR (tablezeit.receiver_id= " + ms_receiverId + " AND del_receiver = 2 )) GROUP BY helpsender,helpreceiver) AS table1 LEFT JOIN (SELECT receiver_id, sender_id, SUM(message.`read`) AS ungelesen FROM message WHERE receiver_id= " + ms_receiverId + " AND message.`read`=1 GROUP BY sender_id,receiver_id) AS table2 ON table1.helpreceiver = table2.receiver_id AND table1.helpsender = table2.sender_id WHERE (member.id =  table1.helpreceiver OR member.id =  table1.helpsender) AND member.id <> " + ms_receiverId + " ORDER BY table1.helpmaxt DESC; " ;
                	
                	ps = con.prepareStatement(sql);  
                    rs = ps.executeQuery();
                    messageoverview.clear();

                    while (rs.next()) {

                    	ms_senderNamekurz= rs.getString("member.last_name");
                    	ms_senderNamekurz= ms_senderNamekurz.substring(0, ms_senderNamekurz.length()-(ms_senderNamekurz.length()-1)) + ".";
		                
                    	 ms_time = DateConString(rs.getString("table1.helpmaxt"));
                    	
                    	message TempObj = new message(rs.getInt("table2.ungelesen"), 0, ms_time, rs.getInt("member.id"), ms_senderNamekurz, rs.getString("member.name"), rs.getString("member.last_name"), null, 0);
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
        
        return "message?faces-redirect=true";   
    }      
	
	
	public void showAllMessageReload() {
		Connection con = null;
		PreparedStatement ps = null;  
        ResultSet rs = null;
    	
        if (ds != null) {  
            try {  
                con = ds.getConnection();  
                if (con != null) { 
                	String sql = "SELECT member.id, member.name, member.last_name, table1.helpmaxt, table2.ungelesen, table1.helpid FROM member, (SELECT tablezeit.maxtid AS helpid, tablezeit.sender_id AS helpsender, tablezeit.receiver_id AS helpreceiver, tablezeit.maxthelp AS helpmaxt FROM (SELECT MAX(message.id) AS maxtid, sender_id, receiver_id, MAX(time_sent) AS maxthelp FROM message WHERE receiver_id= " + ms_receiverId + " OR sender_id= " + ms_receiverId + " GROUP BY sender_id,receiver_id) AS tablezeit JOIN (SELECT sender_id, receiver_id, MAX(time_sent) AS maxthelp2 FROM message WHERE receiver_id= " + ms_receiverId + " OR sender_id= " + ms_receiverId + " GROUP BY sender_id,receiver_id) AS tablezeit2 ON tablezeit2.sender_id = tablezeit.receiver_id AND tablezeit2.receiver_id = tablezeit.sender_id LEFT JOIN message ON maxtid= message.id WHERE tablezeit.maxthelp > tablezeit2.maxthelp2 AND NOT ((tablezeit.sender_id= " + ms_receiverId + " AND del_sender= 2 ) OR (tablezeit.receiver_id= " + ms_receiverId + " AND del_receiver = 2 )) GROUP BY helpsender,helpreceiver) AS table1 LEFT JOIN (SELECT receiver_id, sender_id, SUM(message.`read`) AS ungelesen FROM message WHERE receiver_id= " + ms_receiverId + " AND message.`read`=1 GROUP BY sender_id,receiver_id) AS table2 ON table1.helpreceiver = table2.receiver_id AND table1.helpsender = table2.sender_id WHERE (member.id =  table1.helpreceiver OR member.id =  table1.helpsender) AND member.id <> " + ms_receiverId + " ORDER BY table1.helpmaxt DESC; " ;

                	ps = con.prepareStatement(sql);  
                    rs = ps.executeQuery();
                    messageoverview.clear();

                    while (rs.next()) {

                    	ms_senderNamekurz= rs.getString("member.last_name");
                    	ms_senderNamekurz= ms_senderNamekurz.substring(0, ms_senderNamekurz.length()-(ms_senderNamekurz.length()-1)) + ".";
                    	
                    	ms_time = DateConString(rs.getString("table1.helpmaxt"));
                     	
                     	message TempObj = new message(rs.getInt("table2.ungelesen"), 0, ms_time, rs.getInt("member.id"), ms_senderNamekurz, rs.getString("member.name"), rs.getString("member.last_name"), null, 0);
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
    }  
	
	public void showGesamtMessage() {
		Connection con = null;
		PreparedStatement ps = null;  
        ResultSet rs = null;
    	        
        if (ds != null) {  
            try {  
                con = ds.getConnection();  
                if (con != null) { 
                	
                	String sql = "SELECT COUNT(message.`read`) FROM message WHERE message.receiver_id=" + ms_receiverId + " AND message.`read`= 1;" ;  

                	ps = con.prepareStatement(sql);  
                    rs = ps.executeQuery();

                    while (rs.next()) {
                    	ms_gesamtanzahl = rs.getInt("COUNT(message.`read`)");	 
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
	
	public void showGesamtMessageOhne() {
		Connection con = null;
		PreparedStatement ps = null;  
        ResultSet rs = null;
    	
        if (ds != null) {  
            try {  
                con = ds.getConnection();  
                if (con != null) { 
                	String sql = "SELECT COUNT(message.`read`) FROM message WHERE message.receiver_id=" + ms_receiverId + " AND NOT message.sender_id=" + ms_senderId + " AND message.`read`=1 ;" ;  

                	ps = con.prepareStatement(sql);  
                    rs = ps.executeQuery();

                    while (rs.next()) {
                    	ms_gesamtanzahl = rs.getInt("COUNT(message.`read`)");	 
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

   public String giveSenderName(int ms_senderIdhelp){
	   int  ms_senderIdhelp2; 
	   
	   ms_senderIdhelp2 = ms_senderIdhelp;
	   
	   PreparedStatement pshelp = null;  
       Connection conhelp = null;  
       ResultSet rshelp = null;  

       if (ds != null) {  
           try {  
               conhelp = ds.getConnection();  
               if (conhelp != null) {  
               	   String sqlhelp = "SELECT member.name, member.last_name FROM member WHERE member.id=" + ms_senderIdhelp2 + ";" ;  
               	     pshelp = conhelp.prepareStatement(sqlhelp);  
                     rshelp = pshelp.executeQuery();
                     
                     while (rshelp.next()) {
                      ms_senderFirstName = rshelp.getString("member.name"); 
                      ms_senderLastName= rshelp.getString("member.last_name"); 
                     }
                 }
               }  
            catch (SQLException sqle) {  
               sqle.printStackTrace();
           } finally {  
	            try {  
	                conhelp.close();  
	                pshelp.close();  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
           } 
       }     
       
	   return ms_senderFirstName + " " + ms_senderLastName;
   }
   
	public ArrayList <message> giveMessagedetailfromSQL(int ms_senderId){
		this.ms_senderId = ms_senderId;
		
        PreparedStatement ps = null;  
        Connection con = null;  
        ResultSet rs = null;  

        if (ds != null) {  
            try {  
                con = ds.getConnection();  
                if (con != null) {  
                	   String sql = "SELECT message.id, message.time_sent, message.sender_id, member.name, member.last_name, message.text, message.advert FROM message JOIN member ON message.sender_id=member.id WHERE ((message.receiver_id=" + ms_senderId + " AND message.sender_id=" + ms_receiverId + ") OR (message.receiver_id=" + ms_receiverId + " AND message.sender_id=" + ms_senderId + ")) AND ((message.receiver_id=" + ms_receiverId + " AND message.del_receiver=1) OR (message.sender_id=" + ms_receiverId + " AND message.del_sender=1)) ORDER BY message.time_sent DESC;" ;  
                       ps = con.prepareStatement(sql);  
                       rs = ps.executeQuery();
                      
                       int i = 0; 
                
                       messagedetails.clear();
                       while (rs.next()) {  
                    	   i++;
           
                    	   
                    	   ms_time = DateConString(rs.getString("message.time_sent"));
                    	   
                    	  message TempObj = new message(i, rs.getInt("message.id"), ms_time, rs.getInt("message.sender_id"), null, rs.getString("member.name"), rs.getString("member.last_name"), rs.getString("message.text"), rs.getInt("message.advert") );
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
	
	
	public void updatemessagegelesen() {
		
		byte b = 2; 
	      PreparedStatement ps = null;  
	      Connection con = null;  
	      try {  
	                if (ds != null) {  
	                    con = ds.getConnection();  
	                    if (con != null) {  
	                    	
	                    	String sql = "UPDATE message SET message.read= " + b + " WHERE message.sender_id= "+ ms_senderId +" AND message.receiver_id= " + ms_receiverId + ";";

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
			ms_senderName = giveSenderName(ms_senderId);
			updatemessagegelesen();
			showGesamtMessage();
            return "messagedetail";
        }
	

	public String showMessage(int ms_senderId) {  
		this.ms_senderId = ms_senderId;
		
		messagedetails = giveMessagedetailfromSQL(ms_senderId);
		ms_senderName = giveSenderName(ms_senderId);
		
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
		
		ms_senderName = giveSenderName(ms_senderId);
		
        return "messagedetailvonsuche";    
    }   

	
		public void reloadMessage() {  
				
		giveMessagedetailfromSQL(ms_senderId);
		updatemessagegelesen();    
        }  
		
		
		public void writeMessage() { 	
		
		if (ms_text.length() != 0){
				byte b = 1;
				
				Timestamp tstamp = new Timestamp(System.currentTimeMillis());		
				String datumConverter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tstamp);
		 
		            PreparedStatement ps = null;  
		            Connection con = null;  
		            
		            try {  
		                if (ds != null) {  
		                    con = ds.getConnection();  
		                    if (con != null) { 
		                        String sql = "INSERT INTO `message` (`sender_id`, `receiver_id`, `time_sent`, `read`, `text`, `del_sender`, `del_receiver`) VALUES (?, ?, ?, ?, ?, ?, ?);";  
		                        ps = con.prepareStatement(sql);  
		                        ps.setInt(1, ms_receiverId);  
		                        ps.setInt(2, ms_senderId);  
		                        ps.setString(3, datumConverter); 
		                        ps.setByte(4, b);  
		                        ps.setString(5, ms_text); 
		                        ps.setByte(6, b);  
		                        ps.setByte(7, b);  
		
		                       ps.executeUpdate();   
		                        
		                       ms_text = null; 
		                       
		                       
		                       
		                       PreparedStatement ps2 = null;  
		                       ResultSet rs = null;  
		                	   String sql2 = "SELECT message.id, message.time_sent, message.sender_id, member.name, member.last_name, message.text, message.advert FROM message JOIN member ON message.sender_id=member.id WHERE ((message.receiver_id=" + ms_senderId + " AND message.sender_id=" + ms_receiverId + ") OR (message.receiver_id=" + ms_receiverId + " AND message.sender_id=" + ms_senderId + ")) AND ((message.receiver_id=" + ms_receiverId + " AND message.del_receiver=1) OR (message.sender_id=" + ms_receiverId + " AND message.del_sender=1)) ORDER BY message.time_sent DESC;" ;  
		                       ps2 = con.prepareStatement(sql2);  
		                       rs = ps2.executeQuery();
		              
		                       messagedetails.clear();
		                       int i = 0; 
		                       while (rs.next()) {  
		                       		i++;
		                    	   ms_time = DateConString(rs.getString("message.time_sent"));
		                        	
		                     	  message TempObj = new message(i, rs.getInt("message.id"), ms_time, rs.getInt("message.sender_id"), null, rs.getString("member.name"), rs.getString("member.last_name"), rs.getString("message.text"), rs.getInt("message.advert") );
		                    	   messagedetails.add(TempObj);  
		                       }
		                       
		                       
		                       
				                       if (messagedetails.size()==1){
				                    	   
				                    	   int hvar = 0; 
				                           PreparedStatement ps3 = null;
				                           ResultSet rs2 = null;  
				                     	   String sql3 = "SELECT COUNT(message.id) FROM message WHERE (message.receiver_id=" + ms_senderId + " AND message.sender_id=" + ms_receiverId + ") OR (message.receiver_id=" + ms_receiverId + " AND message.sender_id=" + ms_senderId + ");" ;
				                           ps3 = con.prepareStatement(sql3);  
				                           rs2 = ps3.executeQuery();
				                           
				                           
				                           while (rs2.next()) {
				                        	   hvar =  rs2.getInt("COUNT(message.id)");	 
				                            }	
				                           
				                           if (hvar==1){
				                        	   
				                        	   Timestamp tstamp2 = new Timestamp(System.currentTimeMillis()-1000);		
				                        	   String datumConverter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tstamp2);
				                        	   byte c=2; 
				                        	   
				                        	   PreparedStatement ps4 = null;  
				                        	   String sql4 = "INSERT INTO `message` (`sender_id`, `receiver_id`, `time_sent`, `read`, `text`, `del_sender`, `del_receiver`) VALUES (?, ?, ?, ?, ?, ?, ?);";  
				                               ps4 = con.prepareStatement(sql4);  
				                               ps4.setInt(1, ms_senderId);  
				                               ps4.setInt(2, ms_receiverId);  
				                               ps4.setString(3, datumConverter2); 
				                               ps4.setByte(4, c);  
				                               ps4.setString(5, "SystemFirstMessage"); 
				                               ps4.setByte(6, c);  
				                               ps4.setByte(7, c);  
				
				                              ps4.executeUpdate();   
		
				                           }
				                           
		
				                    	   sql2 = "SELECT message.id, message.time_sent, message.sender_id, member.name, member.last_name, message.text, message.advert FROM message JOIN member ON message.sender_id=member.id WHERE ((message.receiver_id=" + ms_senderId + " AND message.sender_id=" + ms_receiverId + ") OR (message.receiver_id=" + ms_receiverId + " AND message.sender_id=" + ms_senderId + ")) AND ((message.receiver_id=" + ms_receiverId + " AND message.del_receiver=1) OR (message.sender_id=" + ms_receiverId + " AND message.del_sender=1)) ORDER BY message.time_sent DESC;" ;  
				                           ps2 = con.prepareStatement(sql2);  
				                           rs = ps2.executeQuery();
				                  
				                           messagedetails.clear();
				                           i = 0; 
				                           while (rs.next()) {  
				                           		i++;
				                        	   ms_time = DateConString(rs.getString("message.time_sent"));
				                            	
				                         	  message TempObj = new message(i, rs.getInt("message.id"), ms_time, rs.getInt("message.sender_id"), null, rs.getString("member.name"), rs.getString("member.last_name"), rs.getString("message.text"), rs.getInt("message.advert") );
				                        	   messagedetails.add(TempObj);  
				                           }
				                           
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
		}
    }   
	
		
		public int getnamevonmessagedelete(FacesContext fc){
			Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
			String namevonmessagedelete = params.get("namevonmessagedelete");
			int value = Integer.valueOf(namevonmessagedelete);
			return value;
	 	}
		
		
		
		public String deleteAllMessage() { 	
			FacesContext fc = FacesContext.getCurrentInstance();
			this.ms_senderId = getnamevonmessagedelete(fc);
			
			currentdelete = this.ms_senderId;
			
            PreparedStatement ps = null; 
            PreparedStatement ps2 = null;  
            Connection con = null;  
            
            try {  
                if (ds != null) {  
                    con = ds.getConnection();  
                    if (con != null) { 
                        String sql = "UPDATE message SET message.del_sender=2 WHERE ((message.receiver_id=" + ms_senderId + " AND message.sender_id=" + ms_receiverId + ") OR (message.receiver_id=" + ms_receiverId + " AND message.sender_id=" + ms_senderId + ")) AND (message.sender_id=" + ms_receiverId + " AND message.del_sender=1);";  
                        ps = con.prepareStatement(sql);  
                        ps.executeUpdate();   

                 	   String sql2 = "UPDATE message SET message.del_receiver=2, message.`read`=2  WHERE ((message.receiver_id=" + ms_senderId + " AND message.sender_id=" + ms_receiverId + ") OR (message.receiver_id=" + ms_receiverId + " AND message.sender_id=" + ms_senderId + ")) AND (message.receiver_id=" + ms_receiverId + " AND message.del_receiver=1);" ;  
                       ps2 = con.prepareStatement(sql2);  
                       ps2.executeUpdate();
 
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
            
            showGesamtMessage();
            showAllMessage();
            return "message?faces-redirect=true";
    }   		
		
		
		
}
