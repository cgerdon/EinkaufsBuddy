package Beans;
 
import java.sql.*;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
 
@ManagedBean(name = "imageBean")
@SessionScoped
 
public class ImageBean {
	  public ImageBean() {  
	        try {  
	            Context ctx = new InitialContext();  
	            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/database");  
	        } catch (NamingException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	  DataSource ds;
    private String imageID;
    private String imageName;
 
    public String getImageName() {
        return imageName;
    }
 
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
 
    public String getImageLength() {
        return imageLength;
    }
 
    public void setImageLength(String imageLength) {
        this.imageLength = imageLength;
    }
    private String imageLength;
 
    public String getImageID() {
        return imageID;
    }
 
    public void setImageID(String imageID) {
        this.imageID = imageID;
    }
    Connection MySQLcon = null;
    Statement stmt = null;
    PreparedStatement ps;
    ResultSet rs;
 
    public List<ImageBean> getAllImage() throws SQLException {
        List<ImageBean> imageInfo = new ArrayList<ImageBean>();
        Connection con = ds.getConnection();
        try {
            stmt = con.createStatement();
            String strSql = "select id,Image_name from member order by id ";
             rs = stmt.executeQuery(strSql);
            while (rs.next()) {
            	ImageBean tbl = new ImageBean();
                tbl.setImageID(rs.getString("id"));
                tbl.setImageName(rs.getString("Image_name"));
                imageInfo.add(tbl);
            }
        } catch (SQLException e) {
            System.out.println("Exception in getAllImage::" + e.getMessage());
        }
        return imageInfo;
    }
}