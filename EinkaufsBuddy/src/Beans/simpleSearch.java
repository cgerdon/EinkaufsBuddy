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

@ManagedBean(name = "simpleSearch")
// geändert von Mathias: @RequestScoped ersetzt durch @SessionScoped
@SessionScoped
public class simpleSearch implements Serializable{

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

	public String searchSimple() throws IOException, JSONException,
			URISyntaxException {

		PreparedStatement ps = null;
		List<String> Adressen = new ArrayList<String>();

		StringBuffer buffer = new StringBuffer();
		Connection con = null;
		ResultSet rs = null;

		// Mathias hinzugefügt: wegen Session
		AdvertList.clear();

		if (ds != null) {
			try {
				con = ds.getConnection();
				if (con != null) {
					String sql = "SELECT member.id, member.name, member.last_name, ad.text, ad.date, member.plz, times_available.time, member.street, ad.id, ad.limit, ad.income, category.category from ad LEFT JOIN member ON ad.advertiser_id=member.id LEFT JOIN times_available ON ad.fk_time_id = times_available.id LEFT JOIN category ON ad.fk_category = category.id;";
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					int i = 0;

					while (rs.next()) {
						SimpleSearchResults TempObj = new SimpleSearchResults(
								rs.getString("text"), rs.getInt("plz"),
								rs.getString("street"), rs.getString("name"),
								rs.getString("last_name"), rs.getInt("ad.id"),
								rs.getDouble("limit"), rs.getDouble("income"),
								0, rs.getString("time"), rs.getDate("date"),
								rs.getString("category"), rs.getInt("member.id"));
						// AdvertList.add(i, TempObj);
						AdvertList.add(TempObj);
						i = i + 1;
						Adressen.add(rs.getString("street").replaceAll("\\s",
								"+")
								+ "+"
								+ rs.getString("plz").replaceAll("\\s", "+"));
					}
					BufferedReader reader = null;
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
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		JSONObject jsonGoogleMaps = new JSONObject(buffer.toString());
		JSONArray rows = jsonGoogleMaps.getJSONArray("rows");

		for (int i = 0; i < rows.length(); i++) {
			JSONObject obj = rows.getJSONObject(i);
			JSONArray elements = obj.getJSONArray("elements");
			for (int j = 0; j < elements.length(); j++) {
				JSONObject elem = elements.getJSONObject(j);
				JSONObject distance = elem.getJSONObject("distance");
				SimpleSearchResults asdf = AdvertList.get(j);
				asdf.setDistance(Integer.parseInt(distance.getString("value")));
				AdvertList.set(j, asdf);
			}
		}
		summe();
		return "simpleSearchResult";
	}

	public String sortByDistance() {

		Collections.sort(AdvertList, SimpleSearchResults.COMPARE_BY_DISTANCE);
		return "simpleSearchResult";
	}

	public String sortByDate() {
		Collections.sort(AdvertList, SimpleSearchResults.COMPARE_BY_DATE);

		return "simpleSearchResult";
	}

	public String filterAds() {
		// TODO: Christoph: Kleiner Bug, was weg is is weg. Lösung (schlecht),
		// einfach nochmal Google Api anfetzen.Bessere Lösung, ne TempListe
		// einführen

		if (fromDate == null || toDate == null) {
		} else {
			for (int i = 0; i < AdvertList.size(); i++) {
				SimpleSearchResults TempObj = AdvertList.get(i);
				if (TempObj.getDatum().before(toDate)
						&& TempObj.getDatum().after(fromDate)) {
					System.out.println("liegt drin");
				} else {
					AdvertList.remove(i);
				}
			}
		}
		if (sliderDistance > 0) {
			for (int i = 0; i < AdvertList.size(); i++) {
				SimpleSearchResults TempObj = AdvertList.get(i);
				if ((TempObj.getDistance() / 1000) < sliderDistance) {
					AdvertList.remove(i);
				}
			}

		}

		if (sliderLimit > 0) {
			for (int i = 0; i < AdvertList.size(); i++) {
				SimpleSearchResults TempObj = AdvertList.get(i);
				if (TempObj.getLimit() < sliderLimit) {
					AdvertList.remove(i);
				}
			}

		}
		summe();
		return "simpleSearchResult";
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
