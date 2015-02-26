package Beans;  

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
  
@ManagedBean(name = "user") 
//@RequestScoped von Mathias gelöscht und durch @SessionScoped ersetzt
@SessionScoped  
public class User{  
	
	private int id;
    private String firstName;  
    private String lastName;  
    private String email;  
    private String password;
    private Date birthday;
    private int car;
    private String abouttext;
    private byte[] picture;
    private String street;
    private int plz;
    private String phone;
    private String dbPassword;  
    private String dbName;  
    DataSource ds;  
    
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
     
    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
         
        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }
  
    public User() {  
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



	public String getDbPassword() {  
        return dbPassword;  
    }  
  
    public String getDbName() {  
        return dbName;  
    }  
  
    public String getFirstName() {  
        return firstName;  
    }  
  
    public void setFirstName(String name) {  
        this.firstName = name;  
    }  
  
    public String getLastName() {  
        return lastName;  
    }  
  
    public void setLastName(String lastName) {  
        this.lastName = lastName;  
    }  
  
    public String getEmail() {  
        return email;  
    }  
  
    public void setEmail(String email) {  
        this.email = email;  
    }  
  
    public String getPassword() {  
        return password;  
    }  
  
    public void setPassword(String password) {  
        this.password = password;  
    }  
  
    public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
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
	
	public String updateInfos() {
		  int i = 0;  
	      PreparedStatement ps = null;  
	      Connection con = null;  
	      try {  
	                if (ds != null) {  
	                    con = ds.getConnection();  
	                    if (con != null) {  
	                    	if (birthday == null){
	                    	}
	                    	String sql = "UPDATE member set password_hash='" + password + "', name ='" + firstName + "', last_name='" + lastName + "' , car='" + car + "' , abouttext='" + abouttext + "' , street='" + street + "' , plz='" + plz + "' , phone='" + phone + "', birthdate='" + new SimpleDateFormat("yyyy-MM-dd").format(birthday) + "' where mail ='" + email + "';";

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

	public String add() {  
        int i = 0;  
        if (email != null) {  
            PreparedStatement ps = null;  
            Connection con = null;  
            try {  
                if (ds != null) {  
                    con = ds.getConnection();  
                    if (con != null) {  
                        String sql = "INSERT INTO member(name, password_hash, last_name, mail, birthdate) VALUES(?,?,?,?,?)";  
                        ps = con.prepareStatement(sql);  
                        ps.setString(1, firstName);  
                        ps.setString(2, password);  
                        ps.setString(3, lastName);  
                        ps.setString(4, email);  
                        ps.setString(5, "0000-00-00");
                        i = ps.executeUpdate();  
                        System.out.println("Benutzer erfolgreich hinzugefügt");  
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
        if (i > 0) {  
            return "success";  
        } else  
            return "unsuccess";  
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
                        dbName = rs.getString("mail");  
                        dbPassword = rs.getString("password_hash");  
                        id = rs.getInt("id");
                        firstName = rs.getString("name");
                        lastName = rs.getString("last_name");  
                        email = rs.getString("mail");
                        password = rs.getString("password_hash");
                        birthday = rs.getDate("birthdate");
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
        }
    }  
	
    public String changeData(){
    	FacesContext fc = FacesContext.getCurrentInstance();
		this.email = getMailParam(fc);
 
    	dbData(email);
    	return "changedata";
    }
    
	public String getMailParam(FacesContext fc){
		 
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("email");
 
	}
    
	
    public String login(){  
        dbData(email);  
        if (email.equals(dbName) && password.equals(dbPassword)) { 
        	System.out.println(id + " " + firstName + " " + lastName + " " + email + " " + password + " " + birthday + " " + car + " " + abouttext + " " + street + " " + plz + " " + phone);
 
        	//Mathias hinzugefügt wegen LOGIN/LOGOUT Seiten
        	HttpSession session = Util.getSession();
            session.setAttribute("username", email);
            
            return "home";  

        } else  
            return "invalid";  
    }  
  
    public void logout() {  
        FacesContext.getCurrentInstance().getExternalContext()  
                .invalidateSession();  
        FacesContext.getCurrentInstance()  
                .getApplication().getNavigationHandler()  
                .handleNavigation(FacesContext.getCurrentInstance(), null, "/login.xhtml");  
    }  
    
    
// Mathias hinzugefügt: profilchange
    
public String profilchange(String firstName, String lastName, Date birthday, int car, String phone, String email, String password, String street, int plz, String abouttext) {

	int i = 0;  
      PreparedStatement ps = null;  
      Connection con = null;  
      try {  
                if (ds != null) {  
                    con = ds.getConnection();  
                    if (con != null) {  
                    	if (birthday == null){
                    	}
                    	String sql = "UPDATE member set password_hash='" + password + "', name ='" + firstName + "', last_name='" + lastName + "' , car='" + car + "' , abouttext='" + abouttext + "' , street='" + street + "' , plz='" + plz + "' , phone='" + phone + "', birthdate='" + new SimpleDateFormat("yyyy-MM-dd").format(birthday) + "' where mail ='" + email + "';";

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
           return "profil";  
        } else  
            return "unsuccess";  
}   
    
    
    
    
    
}  
