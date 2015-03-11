package Beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.Comparator;

import org.primefaces.model.DefaultStreamedContent;

public class SimpleSearchResults implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5911721057620266881L;
	private String text;
	private String fav_market;
	private int plz;
	private String street;
	private String name;
	private String last_name;
	private int id;
	private double limit;
	private double income;
	private Integer distance;
	private String zeitpunkt;
	private Date datum;
	private String category;
	private int memberid;
	private DefaultStreamedContent dbImage;
	
	

	public DefaultStreamedContent getDbImage() {
		return dbImage;
	}

	public void setDbImage(DefaultStreamedContent dbImage) {
		this.dbImage = dbImage;
	}

	public int getMemberid() {
		return memberid;
	}

	public void setMemberid(int memberid) {
		this.memberid = memberid;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
	
	public String getFav_market() {
		return fav_market;
	}

	public void setFav_market(String fav_market) {
		this.fav_market = fav_market;
	}
	//String fav_market,
	public SimpleSearchResults(String favMarket, String text, int plz, String street,
			String name, String last_name, int id, double limit, double income,
			int distance, String zeitpunkt, Date datum, String category, int memberid, DefaultStreamedContent dbImage) {
		super();
		this.text = text;
		this.fav_market = favMarket;
		this.plz = plz;
		this.street = street;
		this.name = name;
		this.last_name = last_name;
		this.id = id;
		this.limit = limit;
		this.income = income;
		this.distance = distance;
		this.zeitpunkt = zeitpunkt;
		this.datum = datum;
		this.category = category;
		this.memberid = memberid;
		this.dbImage = dbImage;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public static Comparator<SimpleSearchResults> COMPARE_BY_DISTANCE = new Comparator<SimpleSearchResults>() {
        public int compare(SimpleSearchResults one, SimpleSearchResults other) {
            return one.distance.compareTo(other.distance);
        }
    };

    public static Comparator<SimpleSearchResults> COMPARE_BY_DATE = new Comparator<SimpleSearchResults>() {
        public int compare(SimpleSearchResults one, SimpleSearchResults other) {
            return one.datum.compareTo(other.datum);
        }
    };


}
