package Beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.text.SimpleDateFormat;
//import java.util.Date;

import java.sql.ResultSet;

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
 //   private String ms_text;
    
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





	public int getMs_senderId() {
		return ms_senderId;
	}


	public void setMs_senderId(int ms_senderId) {
		this.ms_senderId = ms_senderId;
	}

	public String showMessage() { 
       
 /*        System.out.println(ms_time);
        System.out.println(ms_text);
      System.out.println(ms_receiverId);
        System.out.println(ms_receiverFirstName);
        System.out.println(ms_receiverLastName);
        System.out.println(ms_receiverPicture);
  */      
  /*       System.out.println(ms_senderFirstName);
        System.out.println(ms_senderLastName);
        System.out.println(ms_senderPicture);
 */       
            PreparedStatement ps = null;  
            Connection con = null;
    	//	ResultSet rs = null;
            try {  
                if (ds != null) {  
                    con = ds.getConnection();  
                    if (con != null) { 
                        String sql = "SELECT message.id, message.sender_id FROM message WHERE message.id=1;";
        				//????
                        ps = con.prepareStatement(sql);
                        /*rs = ps.executeQuery(); 
                        rs.setInt(1, ms_id);
                        rs.setInt(2, ms_senderId);
                   */
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
            
            System.out.println(ms_id);
            System.out.println(ms_senderId);
            return "message";
    }   
    
    

}
