package Beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.primefaces.model.DefaultStreamedContent;

@ManagedBean(name = "simpleSearch")
// ge�ndert von Mathias: @RequestScoped ersetzt durch @SessionScoped
@SessionScoped
public class simpleSearch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7367996151453435801L;
	private Integer plzInput;
	private Date fromDate;
	private Date toDate;
	private int sliderDistance;
	private int sliderLimit;
	private String text;
	private int summeAds;
	private int plzDB;
	private String streetDB;
	private boolean validplz;
	
	//joel f�r advertdetail
	private String fav_market;
	private int plz;
	private String street;
	private String name;
	private String last_name;
	private int ad_id;
	private double limit;
	private double income;
	private String zeitpunkt;
	private Date datum;
	private String category;
	private int memberid;
	
	ArrayList<SimpleSearchResults> AdvertList = new ArrayList<SimpleSearchResults>();

	DataSource ds;

	public simpleSearch() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/database");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public ArrayList<SimpleSearchResults> getAdvertList() {
		return AdvertList;
	}

	public void setAdvertList(ArrayList<SimpleSearchResults> advertList) {
		AdvertList = advertList;
	}

	public DataSource getDs() {
		return ds;
	}

	public void setDs(DataSource ds) {
		this.ds = ds;
	}

	public Integer getPlzInput() {
		return plzInput;
	}

	public void setPlzInput(Integer plzInput) {
		this.plzInput = plzInput;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getPlzDB() {
		return plzDB;
	}

	public void setPlzDB(int plzDB) {
		this.plzDB = plzDB;
	}

	public String getStreetDB() {
		return streetDB;
	}

	public void setStreetDB(String streetDB) {
		this.streetDB = streetDB;
	}

	public void summe() {
		setSummeAds(AdvertList.size());
	}

	//getter and setter
	public String getFav_market() {
		return fav_market;
	}

	public void setFav_market(String fav_market) {
		this.fav_market = fav_market;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
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

	public int getAd_id() {
		return ad_id;
	}

	public void setAd_id(int ad_id) {
		this.ad_id = ad_id;
	}

	public double getLimit() {
		return limit;
	}

	public void setLimit(double limit) {
		this.limit = limit;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public String getZeitpunkt() {
		return zeitpunkt;
	}

	public void setZeitpunkt(String zeitpunkt) {
		this.zeitpunkt = zeitpunkt;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getMemberid() {
		return memberid;
	}

	public void setMemberid(int memberid) {
		this.memberid = memberid;
	}
	
	public void checkplz(int plz) throws SQLException {

		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		if (ds != null) {
			try {
				con = ds.getConnection();
				if (con != null) {
					String sql = "select distinct plz from plz where plz = "
							+ plzInput + ";";
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();

					if (!rs.isBeforeFirst()) {
						validplz = false;

					} else {
						validplz = true;

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
		}
	}
	
	public void nearSearch(){
		
	}

	

	public String searchSimple() throws IOException, JSONException,
			URISyntaxException, SQLException {
		checkplz(plzInput);

		PreparedStatement ps = null;
		List<String> Adressen = new ArrayList<String>();

		StringBuffer buffer = new StringBuffer();
		Connection con = null;
		ResultSet rs = null;

		// Mathias hinzugef�gt: wegen Session
		AdvertList.clear();

		if (ds != null) {
			try {
				con = ds.getConnection();
				if (con != null) {

					String sql = "SELECT member.id, member.picture, member.name, ad.fav_market, member.last_name, ad.text, ad.accepted_id, ad.date, member.plz, times_available.time, member.street, ad.id, ad.limit, ad.income, category.category from ad LEFT JOIN member ON ad.advertiser_id=member.id LEFT JOIN times_available ON ad.fk_time_id = times_available.id LEFT JOIN category ON ad.fk_category = category.id where (ad.buyer_id is null and ad.status > 0) and  ad.accepted_id != 9;";
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					int i = 0;
					String NoStreet = "";
					Integer NoPLZ = 0;
					while (rs.next()) {
						String RealStreet = "";
						Integer RealPLZ = 0;
						if (rs.getString("street") == null){
							RealStreet = NoStreet;
							RealPLZ = NoPLZ;
						}else{
							RealStreet = rs.getString("street");
							RealPLZ = rs.getInt("plz");
						
						}
						//rs.getString("fav_market"),
						DefaultStreamedContent dbImage = new DefaultStreamedContent();
						if (rs.getBinaryStream("picture") == null){
							//dbImage
						}
						else {
						dbImage = new DefaultStreamedContent(rs.getBinaryStream("picture"), "image");}
						String Text = "";
						if (rs.getString("text").length()>20) {
							Text = rs.getString("text").substring(0,20) + "...";
						}
						else{Text = rs.getString("text");}
						SimpleSearchResults TempObj = new SimpleSearchResults(rs.getString("fav_market"),
								Text, RealPLZ,
								RealStreet, rs.getString("name"),
								rs.getString("last_name"), rs.getInt("ad.id"),
								rs.getDouble("limit"), rs.getDouble("income"),
								0, rs.getString("time"), rs.getDate("date"),
								rs.getString("category"),
								rs.getInt("member.id"), dbImage);
						
						//joel f�r advertdetail
						text=rs.getString("text");
						plz=RealPLZ;
						street=RealStreet;
						last_name=rs.getString("last_name");
						ad_id=rs.getInt("ad.id");
						limit=rs.getDouble("limit");
						income=rs.getDouble("income");
						zeitpunkt=rs.getString("time");
						datum=rs.getDate("date");
						category=rs.getString("category");
						memberid=rs.getInt("member.id");
						
						
						
						// AdvertList.add(i, TempObj);
						AdvertList.add(TempObj);
						i = i + 1;
						Adressen.add(RealStreet.replaceAll("\\s",
								"+")
								+ "+"
								+ RealPLZ.toString());
					}

					BufferedReader reader = null;
					if (validplz == true) {

						try {
							String tempurl = "/maps/api/distancematrix/json?origins="
									+ plzInput + "+DE&destinations=";
							for (String adr : Adressen) {
								tempurl += adr + "+DE|";
							}
							tempurl += "&mode=car&language=de-DE&sensor=false";
							URL url = new URL("https", "maps.googleapis.com",
									tempurl);

							reader = new BufferedReader(new InputStreamReader(
									url.openStream()));
							
							int read;
							char[] chars = new char[1024];
							while ((read = reader.read(chars)) != -1)
								buffer.append(chars, 0, read);

						} finally {
							if (reader != null)
								reader.close();
						}
					}
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		if (validplz == true) {
			
			JSONObject jsonGoogleMaps = new JSONObject(buffer.toString());
			JSONArray rows = jsonGoogleMaps.getJSONArray("rows");

			for (int i = 0; i < rows.length(); i++) {
				JSONObject obj = rows.getJSONObject(i);
				JSONArray elements = obj.getJSONArray("elements");
				
				for (int j = 0; j < elements.length(); j++) {
					JSONObject elem = elements.getJSONObject(j);
					SimpleSearchResults asdf = AdvertList.get(j);

					if (checkStreet(elem) == null){
						asdf.setDistance(0);
					}
					else{
						JSONObject distance = elem.getJSONObject("distance");
					
						asdf.setDistance(Integer.parseInt(distance
								.getString("value"))/1000);
					}
					
					AdvertList.set(j, asdf);
				}
			}
		}

		try {
			con.close();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		try {
			ps.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		summe();
		return "simpleSearchResult";
	}
	
	private JSONObject checkStreet(JSONObject json) throws JSONException
	{   
	    try
	    {
	        return json.getJSONObject("distance");}
	    catch (JSONException e)
	    {
	        return null;
	    }    
	}

	public String sortByDistance() {

		Collections.sort(AdvertList, SimpleSearchResults.COMPARE_BY_DISTANCE);
		return "simpleSearchResult?faces-redirect=true";
	}
	
	public String reset() throws IOException, JSONException, URISyntaxException, SQLException{
		
		fromDate = null;
		toDate = null;
		sliderDistance = 0;
		sliderLimit = 0;
		searchSimple();
		return "simpleSearchResult?faces-redirect=true";
		
	}


	public String sortByDate() {
		Collections.sort(AdvertList, SimpleSearchResults.COMPARE_BY_DATE);

		return "simpleSearchResult?faces-redirect=true";
	}

	public String filterAds() {
		for (int j= 0;j<=10;j++){
		if (fromDate == null || toDate == null) {
		} else {
			for (int i = 0; i < AdvertList.size(); i++) {
				SimpleSearchResults TempObj = AdvertList.get(i);
				if (TempObj.getDatum().before(toDate)
						&& TempObj.getDatum().after(fromDate)) {

				} else {
					AdvertList.remove(i);
				}
			}
		}}
		for (int j= 0;j<=10;j++){
		if (sliderDistance > 0) {
		
			for (int i = 0; i < AdvertList.size(); i++) {
				SimpleSearchResults TempObj = AdvertList.get(i);
				
				if ((TempObj.getDistance()) > sliderDistance) {
					AdvertList.remove(i);
				
				}
			}

		}}
		for (int j= 0;j<=10;j++){
		if (sliderLimit > 0) {
			
			for (int i = 0; i < AdvertList.size(); i++) {
				SimpleSearchResults TempObj = AdvertList.get(i);
				
				if ((int)TempObj.getLimit() > (int)sliderLimit) {
					AdvertList.remove(i);
				
				}
			}

		}}
		summe();
		return "simpleSearchResult?faces-redirect=true";
	}

	public int getSummeAds() {
		return summeAds;
	}

	public int getSliderDistance() {
		return sliderDistance;
	}

	public void setSliderDistance(int sliderDistance) {
		this.sliderDistance = sliderDistance;
	}

	public int getSliderLimit() {
		return sliderLimit;
	}

	public void setSliderLimit(int sliderLimit) {
		this.sliderLimit = sliderLimit;
	}

	public void setSummeAds(int summeAds) {
		this.summeAds = summeAds;
	}

}