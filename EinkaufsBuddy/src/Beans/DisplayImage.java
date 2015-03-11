package Beans;
 
import java.sql.*;
import java.io.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
 
public class DisplayImage extends HttpServlet {
    private static final long serialVersionUID = 4593558495041379082L;
    
    public DisplayImage() {  
        try {  
            Context ctx = new InitialContext();  
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/database");  
        } catch (NamingException e) {  
            e.printStackTrace();  
        }  
    }  
  DataSource ds;
 
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        Statement stmt = null;
        ResultSet rs;
        Connection con;
        InputStream sImage;
        try {
 
            String id = request.getParameter("Image_id");
            System.out.println("inside servlet–>" + id);
 
            con = ds.getConnection();
            stmt = con.createStatement();
            String strSql = "select picture, image_name from member where id='" + id + "' ";
            rs = stmt.executeQuery(strSql);
            System.out.println(rs.toString());
            
            if (rs.next()) {
            	if (rs.getString("image_name") == null){
            		System.out.println("andere methode wird geladen");
                	loaddefault(request, response);
                	con.close();
                    stmt.close();
            	}
            	else{
                byte[] bytearray = new byte[1048576];
                int size = 0;
                sImage = rs.getBinaryStream(1);
                response.reset();
                response.setContentType("image/jpeg");
                con.close();
                stmt.close();
                while ((size = sImage.read(bytearray)) != -1) {
                    response.getOutputStream().
                            write(bytearray, 0, size);
                }

            	}
            } 

            
        } catch (Exception e) {
            e.printStackTrace();
	
			}
    }

	private void loaddefault(HttpServletRequest request,
            HttpServletResponse response) {
		System.out.println("andere methode wurde geladen");
		 Statement stmt2 = null;
	        ResultSet rs2;
	        Connection con2;
	        InputStream sImage2;
	        try {
	 
	            String id = "39";
	            System.out.println("inside servlet–>" + id);
	 
	            con2 = ds.getConnection();
	            stmt2 = con2.createStatement();
	            String strSql2 = "select picture from member where id='" + id + "' ";
	            rs2 = stmt2.executeQuery(strSql2);
	            if (rs2.next()) {
	                byte[] bytearray = new byte[1048576];
	                int size = 0;
	                sImage2 = rs2.getBinaryStream(1);
	                response.reset();
	                response.setContentType("image/jpeg");
	                while ((size = sImage2.read(bytearray)) != -1) {
	                    response.getOutputStream().
	                            write(bytearray, 0, size);
	                }
	                con2.close();
	                stmt2.close();
	            } 
	            
	        } catch (Exception e) {
	            e.printStackTrace();
			} finally {
				
				try {
					stmt2.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
	    }
		
	}
}