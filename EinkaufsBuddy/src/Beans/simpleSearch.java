package Beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

@ManagedBean(name = "simpleSearch")
@RequestScoped
public class simpleSearch {

	private Integer plzInput;
	private String text;
	private int plzDB;
	private String streetDB;
	ArrayList<SimpleSearchResults> eineListe = new ArrayList<SimpleSearchResults>();
	SimpleSearchResults[] AdvertList = new SimpleSearchResults[999];
	DataSource ds;

	public simpleSearch() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/database");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public SimpleSearchResults[] getAdvertList() {
		return AdvertList;
	}

	public void setAdvertList(SimpleSearchResults[] advertList) {
		AdvertList = advertList;
	}


	
	public ArrayList<SimpleSearchResults> getEineListe() {
		return eineListe;
	}

	public void setEineListe(ArrayList<SimpleSearchResults> eineListe) {
		this.eineListe = eineListe;
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

	public String searchSimple() throws IOException, JSONException,
			URISyntaxException {

		PreparedStatement ps = null;
		List<String> Adressen = new ArrayList<String>();

		StringBuffer buffer = new StringBuffer();
		Connection con = null;
		ResultSet rs = null;

		if (ds != null) {
			try {
				con = ds.getConnection();
				if (con != null) {
					String sql = "SELECT member.name, member.last_name, ad.text, member.plz, member.street, ad.id, ad.limit, ad.income from ad LEFT JOIN member ON ad.advertiser_id=member.id;";
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					int i = 0;
					while (rs.next()) {
						SimpleSearchResults TempObj = new SimpleSearchResults(
								rs.getString("text"), rs.getInt("plz"),
								rs.getString("street"), rs.getString("name"),
								rs.getString("last_name"), rs.getInt("id"),
								rs.getDouble("limit"), rs.getDouble("income"),
								0);
						// AdvertList.add(i, TempObj);
						AdvertList[i] = TempObj;
						eineListe.add(TempObj);
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

						System.out.println(url);

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
				System.out.println(distance.getString("value"));
				AdvertList[j].setDistance(Integer.parseInt(distance
						.getString("value")));
				SimpleSearchResults asdf = eineListe.get(j);
				asdf.setDistance(Integer.parseInt(distance
						.getString("value")));
				eineListe.set(j, asdf);
			}
		}
		System.out.println("*****Hier starten die Inserate*****");
		System.out.println(AdvertList[0].toString());
		System.out.println(AdvertList[1].toString());
		System.out.println(AdvertList[2].toString());
		return "simpleSearchResult";
	}

}
