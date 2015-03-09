package Beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "user")
// @RequestScoped von Mathias gelöscht und durch @SessionScoped ersetzt
@SessionScoped
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3257203081351623458L;
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String password2;
	private boolean updateImg;
	private Date birthday;
	private int car;
	private String abouttext;
	private byte[] picture;
	private String street;
	private int anzahl;
	private double mittel;
	private double mittelstar;
	private  ArrayList<RatingResults> RatingList;
	public int getAnzahl() {
		return anzahl;
	}





	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}





	public double getMittel() {
		return mittel;
	}





	public void setMittel(double mittel) {
		this.mittel = mittel;
	}





	public double getMittelstar() {
		return mittelstar;
	}





	public void setMittelstar(double mittelstar) {
		this.mittelstar = mittelstar;
	}





	public ArrayList<RatingResults> getRatingList() {
		return RatingList;
	}





	public void setRatingList(ArrayList<RatingResults> ratingList) {
		RatingList = ratingList;
	}

	private int plz;
	private Part file;
	private String phone;
	File outputFilePath;
	private String dbPassword;
	private String dbName;
	private StreamedContent dbImage;
	

	private String sender;
	private String titel;
	private String text;
	
	public String getSender() {
		return sender;
	}





	public void setSender(String sender) {
		this.sender = sender;
	}





	public String getTitel() {
		return titel;
	}





	public void setTitel(String titel) {
		this.titel = titel;
	}





	public String getText() {
		return text;
	}





	public void setText(String text) {
		this.text = text;
	}





	public String SendMail(){

	 final String username = "einkaufsbuddy@gmail.com";
     final String password = "buddydeseinkaufs";

     Properties props = new Properties();
     props.put("mail.smtp.starttls.enable", "true");
     props.put("mail.smtp.auth", "true");
     props.put("mail.smtp.host", "smtp.gmail.com");
     props.put("mail.smtp.port", "587");

     Session session = Session.getInstance(props,
       new javax.mail.Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
             return new PasswordAuthentication(username, password);
         }
       });

     try {

         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(sender));
         message.setRecipients(Message.RecipientType.TO,
             InternetAddress.parse("einkaufsbuddy@gmail.com"));
         message.setSubject(titel);
         message.setText(text);

         Transport.send(message);

         System.out.println("E-Mail erfolgreich versendet!");

     } catch (MessagingException e) {
         throw new RuntimeException(e);
     }

     
     return "home?faces-redirect=true";
 }

	public StreamedContent getDbImage() {
		return dbImage;
	}

	public void setDbImage(StreamedContent dbImage) {
		this.dbImage = dbImage;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public void uploadFile() throws IOException {

		String fileName = getFileName(file);

		String basePath = "C:" + File.separator + "temp" + File.separator;
		outputFilePath = new File(basePath + fileName);

		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = file.getInputStream();
			outputStream = new FileOutputStream(outputFilePath);

			int read = 0;
			final byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}

	}

	private boolean[][] daytimeavailable;
	DataSource ds;

	private String getFileName(Part part) {

		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {

				if (content.length() > 12) {
					updateImg = true;
				}
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}

	private void PicUpload() {
		Connection connection = null;
		PreparedStatement statement = null;
		FileInputStream inputStream = null;

		try {

			inputStream = new FileInputStream(outputFilePath);
			connection = ds.getConnection();
			statement = connection
					.prepareStatement("UPDATE member SET picture= ? where id= '"
							+ id + "'");
			statement.setBinaryStream(1, (InputStream) inputStream,
					(int) (outputFilePath.length()));

			statement.executeUpdate();

		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException: - " + e);
		} catch (SQLException e) {
			System.out.println("SQLException: - " + e);
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				System.out.println("SQLException Finally: - " + e);
			}
		}
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public void onDateSelect(SelectEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		facesContext.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected",
						format.format(event.getObject())));
	}

	public boolean[][] getDaytimeavailable() {
		return daytimeavailable;
	}

	public void setDaytimeavailable(boolean[][] daytimeavailable) {
		this.daytimeavailable = daytimeavailable;
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
					if (birthday == null) {
					}
					String sql = "UPDATE member set password_hash='"
							+ password
							+ "', name ='"
							+ firstName
							+ "', last_name='"
							+ lastName
							+ "' , car='"
							+ car
							+ "' , abouttext='"
							+ abouttext
							+ "' , street='"
							+ street
							+ "' , plz='"
							+ plz
							+ "' , phone='"
							+ phone
							+ "', birthdate='"
							+ new SimpleDateFormat("yyyy-MM-dd")
									.format(birthday) + "' where mail ='"
							+ email + "';";

					ps = con.prepareStatement(sql);
					i = ps.executeUpdate();

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
			return "home?faces-redirect=true";
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
						String sql = "select id, mail, picture, password_hash, name, last_name, birthdate, car, abouttext, fk_sex, street, plz, phone, picture from member where mail = '"
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
						// password = rs.getString("password_hash");
						birthday = rs.getDate("birthdate");
						car = rs.getInt("car");
						abouttext = rs.getString("abouttext");
						street = rs.getString("street");
						plz = rs.getInt("plz");
						phone = rs.getString("phone");
						dbImage = new DefaultStreamedContent(rs.getBinaryStream("picture"), "image");
					}
					showTimes(id);
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		}
	}

	public String changeData() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.email = getMailParam(fc);

		dbData(email);
		return "changedata";
	}

	public String getMailParam(FacesContext fc) {

		Map<String, String> params = fc.getExternalContext()
				.getRequestParameterMap();
		return params.get("email");

	}

	public String login() {
		dbData(email);
		if (email.equals(dbName) && password.equals(dbPassword)) {
			// Mathias hinzugefügt wegen LOGIN/LOGOUT Seiten
			HttpSession session = Util.getSession();
			session.setAttribute("username", email);
			showTimes(id);
			try {
				getRatings(id);
				
			} catch (SQLException e) {

				e.printStackTrace();
			}

			mittel();
			return "home?faces-redirect=true";

		} else
			logout();
		return "error";
	}
	
	private void mittel() {
		mittel = 0;
		for(RatingResults object: RatingList){
			mittel += object.getRating();
			}
	
		mittel = mittel / RatingList.size();
		
		if (RatingList.size() == 0) {mittel = 0; mittelstar = 0; anzahl = 0;}
		else {double f = 0.5;
		mittelstar = f * Math.round(mittel/f);
		anzahl = RatingList.size();}
		
	}



	public void getRatings(int id) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;

		if (ds != null) {
			try {
				con = ds.getConnection();
				if (con != null) {
					String sql = "select rating.id, member.name, member.last_name, buyer_id, advertiser_id, type, rating, text, ad_id from rating left join member on member.id = rating.advertiser_id where (type = 1 and buyer_id = "+ id +") or (type=2 and advertiser_id="+ id +")";
					System.out.println(sql);
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
				
					ArrayList<RatingResults> TempList = new ArrayList<RatingResults>();
					while (rs.next()) {
						
						RatingResults TempObj = new RatingResults();
						TempObj.setId(rs.getInt("rating.id"));
						TempObj.setBuyerid(rs.getInt("buyer_id"));
						TempObj.setAdvertiserid(rs.getInt("advertiser_id"));
						TempObj.setRating(rs.getInt("rating"));
						TempObj.setAdid(rs.getInt("ad_id"));
						TempObj.setText(rs.getString("text"));
						TempObj.setVorname(rs.getString("name"));
						TempObj.setName(rs.getString("last_name"));
						System.out.println(TempObj.toString());
						TempList.add(TempObj);
						
					
					}
					RatingList = TempList;
				}}finally {  
	                try {  
	                    con.close();  
	                    ps.close();  
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		
	}
		
		
		}

	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		FacesContext
				.getCurrentInstance()
				.getApplication()
				.getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null,
						"/login.xhtml");
	}

	// Mathias hinzugefügt: profilchange

	public void DeleteTimes(int id) {

		PreparedStatement ps = null;
		Connection con = null;

		if (ds != null) {
			try {
				con = ds.getConnection();
				if (con != null) {
					String sql = "DELETE from member_day_time_available where fk_member_id = "
							+ id + ";";
					ps = con.prepareStatement(sql);
					ps.executeUpdate();
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

	public void UpdateTimes(int id) {

		ArrayList<String> Querys = new ArrayList<String>();
		int rows = daytimeavailable.length;

		int cols = daytimeavailable[0].length;

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				// INSERT INTO `member_day_time_available` (`fk_member_id`,
				// `fk_day_id`, `fk_time_id`) VALUES (1, 3, 3);

				if (daytimeavailable[row][col] == true) {
					Querys.add("INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES ("
							+ id + "," + row + "," + col + ");");
				}
			}
		}

		for (String insertq : Querys) {
			PreparedStatement ps = null;
			Connection con = null;
			if (ds != null) {
				try {
					con = ds.getConnection();
					if (con != null) {
						String sql = insertq;
						ps = con.prepareStatement(sql);
						ps.executeUpdate();
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
	}

	public void showTimes(int id) {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		if (ds != null) {
			try {
				con = ds.getConnection();
				if (con != null) {
					String sql = "select fk_day_id, fk_time_id from member_day_time_available where fk_member_id = "
							+ id + ";";

					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					boolean[][] TempObj = new boolean[6][6];
					// hier die magie
					while (rs.next()) {
						TempObj[rs.getInt("fk_day_id")][rs.getInt("fk_time_id")] = true;
					}
					daytimeavailable = TempObj;
					// ende magie
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

	public String profilchange() throws IOException {
		// boolean TempObject[][] = new boolean[6][6];
		// daytimeavailable = TempObject;
		int i = 0;
		PreparedStatement ps = null;
		Connection con = null;
		try {
			if (ds != null) {
				con = ds.getConnection();
				if (con != null) {
					if (birthday == null) {
					}
					String sql = "UPDATE member set password_hash='"
							+ password
							+ "', name ='"
							+ firstName
							+ "', last_name='"
							+ lastName
							+ "' , car='"
							+ car
							+ "' , abouttext='"
							+ abouttext
							+ "' , street='"
							+ street
							+ "' , plz='"
							+ plz
							+ "' , phone='"
							+ phone
							+ "', birthdate='"
							+ new SimpleDateFormat("yyyy-MM-dd")
									.format(birthday) + "' where mail ='"
							+ email + "';";

					ps = con.prepareStatement(sql);
					i = ps.executeUpdate();

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

		DeleteTimes(id);

		UpdateTimes(id);

		updateImg = false;
		System.out.println("Ab hier..." + updateImg);
		uploadFile();
		if (updateImg == true) {
			
			PicUpload();
		}

		if (i > 0) {
			return "profil?faces-redirect=true";
		} else
			return "unsuccess?faces-redirect=true";
	}


	 
	 public long getFixDate() {
		 return System.currentTimeMillis();
	}

	private long fixDate;
	
}
