package Beans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import javax.sql.DataSource;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

//import javax.faces.bean.RequestScoped;

@ManagedBean(name = "user")
// @RequestScoped von Mathias gelöscht und durch @SessionScoped ersetzt
@SessionScoped
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3257203081351623458L;
	private static int id;
	private String firstName;
	private String lastName;
	private static String email;
	private String password;
	private String password2;

	private Date birthday;
	private int car;
	private String abouttext;
	private byte[] picture;
	private String street;
	private static int anzahl;
	private static double mittel;
	private static double mittelstar;
	static boolean ratingsexist = false;
	private static ArrayList<RatingResults> RatingList;

	public int getAnzahl() {
		return anzahl;
	}

	public void setAnzahl(int anzahl) {
		User.anzahl = anzahl;
	}

	public double getMittel() {
		return mittel;
	}

	public void setMittel(double mittel) {
		User.mittel = mittel;
	}

	public double getMittelstar() {
		return mittelstar;
	}

	public void setMittelstar(double mittelstar) {
		User.mittelstar = mittelstar;
	}

	public ArrayList<RatingResults> getRatingList() {
		return RatingList;
	}

	public void setRatingList(ArrayList<RatingResults> ratingList) {
		RatingList = ratingList;
	}
	

	private int plz;
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

	public String SendMail() {

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
			message.setText(text + " Das war eine Mail von " + sender );

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		sender = "";
		text = "";
		titel = "";
		return "home?faces-redirect=true";
	}

	public StreamedContent getDbImage() {
		return dbImage;
	}

	public void setDbImage(StreamedContent dbImage) {
		this.dbImage = dbImage;
	}

	private static boolean[][] daytimeavailable;
	static DataSource ds;

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
		User.daytimeavailable = daytimeavailable;
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
		User.id = id;
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
		User.email = email;
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
						String sql = "INSERT INTO member(name, password_hash, last_name, mail) VALUES(?,?,?,?)";
						ps = con.prepareStatement(sql);
						ps.setString(1, firstName);
						ps.setString(2, password);
						ps.setString(3, lastName);
						ps.setString(4, email);
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
		firstName = "";
		password = "";
		lastName = "";
		email = "";
		if (i > 0) {
			return "home?faces-redirect=true";
		} else
			return "unsuccess";
	}

	public void dbData(String uName) {

	}

	public String changeData() {
		FacesContext fc = FacesContext.getCurrentInstance();
		User.email = getMailParam(fc);

		dbData(email);
		return "changedata";
	}

	public String getMailParam(FacesContext fc) {

		Map<String, String> params = fc.getExternalContext()
				.getRequestParameterMap();
		return params.get("email");

	}

	public String login() {

		if (email != null) {
			PreparedStatement ps = null;
			Connection con = null;
			ResultSet rs = null;

			if (ds != null) {
				try {
					con = ds.getConnection();
					if (con != null) {
						String sql = "select id, mail, password_hash, name, last_name, birthdate, car, abouttext, fk_sex, street, plz, phone  from member where mail = '"
								+ email + "'";
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
					}
					ps.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		}
		if (email.equals(dbName) && password.equals(dbPassword)) {
			// Mathias hinzugefügt wegen LOGIN/LOGOUT Seiten
			HttpSession session = Util.getSession();
			session.setAttribute("username", email);

			return "home?faces-redirect=true";

		} else
			logout();
		return "error";
	}

	public static void getRatings(int id) throws SQLException {
		ArrayList<RatingResults> Leer = new ArrayList<RatingResults>();
		RatingList = Leer;
		ratingsexist = false;
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		String TempVorname = null;
		String Tempnachname = null;
		int tempid = -1;

		if (ds != null) {
			try {
				con = ds.getConnection();
				if (con != null) {
					String sql = "select rating.id, member.name, member.last_name, buyer_id, advertiser_id, type, rating, text, ad_id from rating left join member on member.id = rating.advertiser_id where (type = "
							+ id
							+ " and buyer_id = "
							+ id
							+ ") or (type= "
							+ id + " and advertiser_id=" + id + ")";

					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					
					ArrayList<RatingResults> TempList = new ArrayList<RatingResults>();
					while (rs.next()) {

						if (rs.getInt("type") == rs.getInt("buyer_id")) {
							tempid = rs.getInt("advertiser_id");
						} else
							tempid = rs.getInt("buyer_id");
						PreparedStatement ps2 = null;
						Connection con2 = null;
						ResultSet rs2 = null;
						if (ds != null) {
							try {
								con2 = ds.getConnection();
								if (con2 != null) {

									String sql2 = "select name, last_name from member where id = "
											+ tempid;
									ps2 = con2.prepareStatement(sql2);
									rs2 = ps2.executeQuery();

									while (rs2.next()) {
										ratingsexist = true;
										TempVorname = rs2.getString("name");
										Tempnachname = rs2
												.getString("last_name");

									}

								}
							} finally {
								try {
									con2.close();
									ps2.close();
								} catch (SQLException sqle) {
									sqle.printStackTrace();
								}
							}

							RatingResults TempObj = new RatingResults();
							TempObj.setId(rs.getInt("rating.id"));
							TempObj.setBuyerid(rs.getInt("buyer_id"));
							TempObj.setAdvertiserid(rs.getInt("advertiser_id"));
							TempObj.setRating(rs.getInt("rating"));
							TempObj.setAdid(rs.getInt("ad_id"));
							TempObj.setText(rs.getString("text"));
							TempObj.setVorname(TempVorname);
							TempObj.setName(Tempnachname);
							TempList.add(TempObj);

						}
						RatingList = TempList;
					}
				}
			} finally {
				try {
					con.close();
					ps.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
			mittel = 0;
			mittelstar = 0;
			anzahl = 0;

			if (ratingsexist) {
				for (RatingResults object : RatingList) {
					mittel += object.getRating();
				}

				mittel = mittel / RatingList.size();
				double f = 0.5;
				mittelstar = f * Math.round(mittel / f);
				anzahl = RatingList.size();
			}
		}

	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		FacesContext
				.getCurrentInstance()
				.getApplication()
				.getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null,
						"/login.xhtml");
		id= 0; 
		return "login?faces-redirect=true";
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

	public static void showTimes(int id) {

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
					while (rs.next()) {
						TempObj[rs.getInt("fk_day_id")][rs.getInt("fk_time_id")] = true;
					}
					daytimeavailable = TempObj;

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

	public String profilchange() throws IOException, SQLException {
		
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

		loadProfil();

		if (i > 0) {
			return "profil?faces-redirect=true";
		} else
			return "unsuccess?faces-redirect=true";
	}

	public long getFixDate() {
		return System.currentTimeMillis();
	}

	public static void loadProfil() throws SQLException {

		showTimes(id);

		getRatings(id);

	}

	private static UploadedFile file;

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		User.file = file;
	}

	public static void upload(FileUploadEvent event) { 
		        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");  
		        FacesContext.getCurrentInstance().addMessage(null, msg);
		
			try {
				
				System.out.println(event.getFile().toString());

				InputStream fin2 = event.getFile().getInputstream();
				Connection con = ds.getConnection();

				PreparedStatement pre = con
						.prepareStatement("UPDATE member SET image_name = ? , picture = ? WHERE id = "
								+ id);
				pre.setString(1, event.getFile().getFileName().toString());
				pre.setBinaryStream(2, fin2, event.getFile().getSize());
				pre.executeUpdate();

				pre.close();

			} catch (Exception e) {
				System.out.println("Exception-File Upload." + e.getMessage());
			}
		 
		
	}

}
