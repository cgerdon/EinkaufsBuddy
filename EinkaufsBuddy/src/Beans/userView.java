package Beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
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
	private int id;
	private String name;
	private String last_name;
	private double mittel;
	private double mittelstar;
	private Date birthdate;
	private int car;
	private String abouttext;
	private int sex;
	private String street;
	private int plz;
	private String phone;
	private boolean[][] daytimeavailable;
	private int anzahl;
	private  ArrayList<RatingResults> RatingList;
	
	//private Rating rating;
	
	

	public ArrayList<RatingResults> getRatingList() {
		return RatingList;
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

	public String showProfil(int id) {
		// Oh gott, bitte muss ich die NIE wieder anschauen.
	
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
			} finally {
				try {
					con.close();
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		showTimes(id);
		try {
			getRatings(id);
			
		} catch (SQLException e) {

			e.printStackTrace();
		}

		mittel();
		return "fremdprofil";

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
					String sql = "select rating.id, member.name, member.last_name, buyer_id, advertiser_id, rating, text, ad_id from rating left join member on member.id = rating.advertiser_id where buyer_id = "
							+ id +";";
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
