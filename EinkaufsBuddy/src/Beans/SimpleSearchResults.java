package Beans;

import java.sql.Date;

public class SimpleSearchResults {

	private String text;
	private int plz;
	private String street;
	private String name;
	private String last_name;
	private int id;
	private double limit;
	private double income;
	private int distance;
	private String zeitpunkt;
	private Date datum;

	
	

	@Override
	public String toString() {
		return "SimpleSearchResults [text=" + text + ", plz=" + plz
				+ ", street=" + street + ", name=" + name + ", last_name="
				+ last_name + ", id=" + id + ", limit=" + limit + ", income="
				+ income + ", distance=" + distance + ", zeitpunkt="
				+ zeitpunkt + ", datum=" + datum + "]";
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

	public SimpleSearchResults(String text, int plz, String street,
			String name, String last_name, int id, double limit, double income,
			int distance, String zeitpunkt, Date datum) {
		super();
		this.text = text;
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

}
