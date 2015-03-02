package Beans;  

import java.sql.Connection;
import java.sql.Date;
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
	private String name;
	private String last_name;
	private Date birthdate;
	private int car;
	private String abouttext;
	private int sex;
	private String street;
	private int plz;
	private String phone;
	private int[][] daytimeavailable;
	
	
	
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


	public Date getBirthdate() {
		return birthdate;
	}


	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}


	public int getCar() {
		return car;
	}


	public void setCar(int car) {
		this.car = car;
	}


	public String getAbouttext() {
		return abouttext;
	}


	public void setAbouttext(String abouttext) {
		this.abouttext = abouttext;
	}


	public int getSex() {
		return sex;
	}


	public void setSex(int sex) {
		this.sex = sex;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public int getPlz() {
		return plz;
	}


	public void setPlz(int plz) {
		this.plz = plz;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public int[][] getDaytimeavailable() {
		return daytimeavailable;
	}


	public void setDaytimeavailable(int[][] daytimeavailable) {
		this.daytimeavailable = daytimeavailable;
	}


	DataSource ds;  
    
    public String showProfil(int id){
    	//Oh gott, bitte muss ich die NIE wieder anschauen. 
    	this.id = id;
    	 PreparedStatement ps = null;  
         Connection con = null;  
         ResultSet rs = null;  
         if (ds != null) {  
             try {  
                 con = ds.getConnection();  
                 if (con != null) {  
                     String sql = "select id, name, last_name, birthdate, car, abouttext, fk_sex, street, plz, phone from member where id = '"  
                             + id + "'";  
                     ps = con.prepareStatement(sql);  
                     rs = ps.executeQuery();  
                     rs.next();  
                     name = rs.getString("name");
                     last_name = rs.getString("last_name");  
                     birthdate = rs.getDate("birthdate");
                     car = rs.getInt("car");
                     abouttext = rs.getString("abouttext");
                     street = rs.getString("street");
                     plz = rs.getInt("plz");
                     phone = rs.getString("phone");
                 }  
             } catch (SQLException sqle) {  
                 sqle.printStackTrace();  
             }  
         }  
     
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
	
    
}  
