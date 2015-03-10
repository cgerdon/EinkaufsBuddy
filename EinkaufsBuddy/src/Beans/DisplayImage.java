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
            String strSql = "select picture from member where id='" + id + "' ";
            rs = stmt.executeQuery(strSql);
            if (rs.next()) {
                byte[] bytearray = new byte[1048576];
                int size = 0;
                sImage = rs.getBinaryStream(1);
                response.reset();
                response.setContentType("image/jpeg");
                while ((size = sImage.read(bytearray)) != -1) {
                    response.getOutputStream().
                            write(bytearray, 0, size);
                }
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
		} finally {
			
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
    }}
}