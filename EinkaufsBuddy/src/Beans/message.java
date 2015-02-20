package Beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.text.SimpleDateFormat;
//import java.util.Date;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


@ManagedBean(name = "message") 
@SessionScoped  

public class message {
	
	private int ms_id;
 //   private Date ms_time;
    private String ms_text;
    
/*	private int ms_receiverId;	
    private String ms_receiverFirstName;  
    private String ms_receiverLastName;  
    private byte[] ms_receiverPicture;
*/    
	    private int ms_senderId;	
	    /*   private String ms_senderFirstName;  
    private String ms_senderLastName;  
    private byte[] ms_senderPicture;    
*/
    
    DataSource ds;
    
    public message() {  
        try {  
            Context ctx = new InitialContext();  
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/database");  
        } catch (NamingException e) {  
            e.printStackTrace();  
        }  
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


	public void setMs_senderId(int ms_senderId) {
		this.ms_senderId = ms_senderId;
	}

	List<String> messagedetails = new ArrayList<String>();

	public List<String> getMessagedetails() {
		return messagedetails;
	}


	public void setMessagedetails(List<String> messagedetails) {
		this.messagedetails = messagedetails;
	}


	public String showMessage() {  
         
            PreparedStatement ps = null;  
            Connection con = null;  
            ResultSet rs = null;  
  
            if (ds != null) {  
                try {  
                    con = ds.getConnection();  
                    if (con != null) {  
                        String sql = "SELECT message.id, message.sender_id, message.text FROM message" ;  
                        ps = con.prepareStatement(sql);  
                        rs = ps.executeQuery();
                       
                        while (rs.next()) {  
                        	ms_id = rs.getInt("message.id");
                        	//messagedetails.add(rs.getString("message.text"));
                        	
	                        ms_senderId = rs.getInt("message.sender_id");  
	                        ms_text = rs.getString("message.text");
	                        /* firstName = rs.getString("name");
	                        lastName = rs.getString("last_name");  
	                        email = rs.getString("mail");
	                        password = rs.getString("password_hash");
	                        birthday = rs.getDate("birthdate");
	                        car = rs.getInt("car");
	                        abouttext = rs.getString("abouttext");
	                        street = rs.getString("street");
	                        plz = rs.getInt("plz");
	                        phone = rs.getString("phone");*/
	                        System.out.println (ms_id + " " + ms_senderId + " " + ms_text);
                        }
                    }  
                } catch (SQLException sqle) {  
                    sqle.printStackTrace();  
                }  
            } 
            
           
            return "messagedetail";
        }  
     
	
	public String writeMessage() {  
        int i = 0;
        
            PreparedStatement ps = null;  
            Connection con = null;  
            try {  
                if (ds != null) {  
                    con = ds.getConnection();  
                    if (con != null) { 
                    	//INSERT INTO `ad` (`advertiser_id`, `date`, `fk_time_id`, `limit`, `income`, `text`, `fk_category`, `status`, `fav_market`, `buyer_id`) VALUES(3, '2014-11-05', 3, 10, 1, 'Hallo, ich mag bitte eine Flasche Schnaps haben!', 1, 0, 'Aldi', NULL),
                        String sql = "INSERT INTO `message` (`sender_id`, `receiver_id`, `time_sent`, `ad_id`, `text`) VALUES(?,?,?,?,?)";  
                        ps = con.prepareStatement(sql);  
                        ps.setInt(1, 1);
                        ps.setInt(2, 2);  
                        ps.setString(3, "2015-01-06 15:00:00");
                        ps.setInt(4, 3);  
                        ps.setString(5, "Give me more");  
                       // System.out.println(ps.toString());
                        i = ps.executeUpdate();  
                        System.out.println("Message erfolgreich eingepflegt");  
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
            return "messagedetails";  
        } else  
            return "unsuccess";  
    }   
    
    

}
