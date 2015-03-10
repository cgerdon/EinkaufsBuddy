package Beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@ManagedBean(name = "userView")
@SessionScoped
public class userView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5820720967637889546L;
	//Mathias
	@ManagedProperty(value="#{user.id}")
    private int aduser_id; 
	
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
	private boolean[][] daytimeavailable;
	private int anzahl;
	private double mittel;
	private double mittelstar;
	boolean ratingsexist = false;
	private  ArrayList<RatingResults> RatingList;
	
	//private Rating rating;
	
	

	public ArrayList<RatingResults> getRatingList() {
		return RatingList;
	}

	
	
	public int getAduser_id() {
		return aduser_id;
	}



	public void setAduser_id(int aduser_id) {
		this.aduser_id = aduser_id;
	}



	public int getAnzahl() {
		return anzahl;
	}



	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}



	public double getMittelstar() {
		return mittelstar;
	}



	public void setMittelstar(double mittelstar) {
		this.mittelstar = mittelstar;
	}



	public double getMittel() {
		return mittel;
	}



	public void setMittel(double mittel) {
		this.mittel = mittel;
	}



	public void setRatingList(ArrayList<RatingResults> ratingList) {
		RatingList = ratingList;
	}

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

	public boolean[][] getDaytimeavailable() {
		return daytimeavailable;
	}

	public void setDaytimeavailable(boolean[][] daytimeavailable) {
		this.daytimeavailable = daytimeavailable;
	}

	DataSource ds;

	public String showProfil(int ide) throws SQLException {
		System.out.println(id);
		System.out.println("Lade Profil");
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		System.out.println("gleich die query");
		if (ds != null) {
			try {
				con = ds.getConnection();
				if (con != null) {
					String sql = "select id, name, last_name, birthdate, car, abouttext, fk_sex, street, plz, phone from member where id = '"
							+ ide + "'";
						ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					while(rs.next()){
					id = rs.getInt("id");
					name = rs.getString("name");
					System.out.println(name);
					last_name = rs.getString("last_name");
					System.out.println(last_name);
					birthdate = rs.getDate("birthdate");
					System.out.println(birthdate);
					car = rs.getInt("car");
					System.out.println(birthdate);
					abouttext = rs.getString("abouttext");
					System.out.println(abouttext);
					street = rs.getString("street");
					System.out.println(street);
					plz = rs.getInt("plz");
					System.out.println(plz);
					phone = rs.getString("phone");
					System.out.println(phone);}
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
		System.out.println("Profil fertig!");
		System.out.println("Lade Zeiten");
		showTimes(id);
		System.out.println("Ende Lade Zeiten");
		System.out.println("Lade Ratings");
		getRatings(id);
		System.out.println("Ende Ratings");
		
		
		return "fremdprofil?faces-redirect=true";
		
	}

	private void mittel() {
		mittel = 0;
		anzahl = 0;
		mittelstar = 0;
		
		System.out.println("mittel gestartet");
		//System.out.println(RatingList.size());
		System.out.println(ratingsexist);
		if (ratingsexist){
			
		
		for(RatingResults object: RatingList){
			mittel += object.getRating();
			}
	
		mittel = mittel / RatingList.size();
		double f = 0.5;
		mittelstar = f * Math.round(mittel/f);
		anzahl = RatingList.size();
		}

		
	}



	public void getRatings(int id) throws SQLException {
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
					String sql = "select rating.id, member.name, member.last_name, buyer_id, advertiser_id, type, rating, text, ad_id from rating left join member on member.id = rating.advertiser_id where (type = "+ id +" and buyer_id = "+ id +") or (type= "+ id +" and advertiser_id="+ id +")";
					
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					
					ArrayList<RatingResults> TempList = new ArrayList<RatingResults>();
					while (rs.next()) {
					
						if (rs.getInt("type") == rs.getInt("buyer_id")){
							tempid = rs.getInt("advertiser_id");
						} else tempid = rs.getInt("buyer_id");
						PreparedStatement ps2 = null;
						Connection con2 = null;
						ResultSet rs2 = null;
						if (ds != null) {
							try {
								con2 = ds.getConnection();
								if (con2 != null) {
			
									String sql2 = "select name, last_name from member where id = " + tempid;
									ps2 = con2.prepareStatement(sql2);
									rs2 = ps2.executeQuery();
			

									while (rs2.next()) {
										ratingsexist = true;
										TempVorname = rs2.getString("name");
										Tempnachname = rs2.getString("last_name");
									
									}
									
								}}finally {  
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
				}}}finally {  	
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
			
			
			if (ratingsexist){
			for(RatingResults object: RatingList){
				mittel += object.getRating();
				}
		
			mittel = mittel / RatingList.size();
			double f = 0.5;
			mittelstar = f * Math.round(mittel/f);
			anzahl = RatingList.size();}
		}
		
		}

	private void showTimes(int id) {
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
