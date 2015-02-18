package Beans;


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
	
	
	
	
	public SimpleSearchResults(String text, int plz, String street,
			String name, String last_name, int id, double limit, double income,
			int distance) {
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
