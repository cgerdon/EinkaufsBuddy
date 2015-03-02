package Beans;  

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
  
@ManagedBean(name = "userView") 

@SessionScoped  
public class userView{  
	
	private int id;
    DataSource ds;  
    
    public String showProfil(int id){
    	this.id = id;
		return "fremdprofil";
    	
    }
    
    
    public userView() {  
        try {  
            Context ctx = new InitialContext();  
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/database");  
        } catch (NamingException e) {  
            e.printStackTrace();  
        }  
    }  
    
      
    public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
  
    public void dbData(String uName) {  
        if (uName != null) {  
            PreparedStatement ps = null;  
            Connection con = null;  
            ResultSet rs = null;  
  
            if (ds != null) {  
                try {  
                    con = ds.getConnection();  
                    if (con != null) {  
                        String sql = "select id, mail, password_hash, name, last_name, birthdate, car, abouttext, fk_sex, street, plz, phone from member where mail = '"  
                                + uName + "'";  
                        ps = con.prepareStatement(sql);  
                        rs = ps.executeQuery();  
                        rs.next();  
//                        dbName = rs.getString("mail");  
//                        dbPassword = rs.getString("password_hash");  
//                        id = rs.getInt("id");
//                        firstName = rs.getString("name");
//                        lastName = rs.getString("last_name");  
//                        email = rs.getString("mail");
//                       // password = rs.getString("password_hash");
//                        birthday = rs.getDate("birthdate");
//                        car = rs.getInt("car");
//                        abouttext = rs.getString("abouttext");
//                        street = rs.getString("street");
//                        plz = rs.getInt("plz");
//                        phone = rs.getString("phone");
                    }  
                } catch (SQLException sqle) {  
                    sqle.printStackTrace();  
                }  
            }  
        }
    }  
	

    
}  
