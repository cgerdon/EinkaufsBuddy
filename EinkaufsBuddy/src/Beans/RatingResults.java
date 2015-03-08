package Beans;


public class RatingResults {

	private int id;
	private int buyerid;
	private int advertiserid;
	private int rating;
	private String text;
	private int adid;
	private String name;
	private String vorname;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

	@Override
	public String toString() {
		return "RatingResults [id=" + id + ", buyerid=" + buyerid
				+ ", advertiserid=" + advertiserid + ", rating=" + rating
				+ ", text=" + text + ", adid=" + adid + ", name=" + name
				+ ", vorname=" + vorname + "]";
	}
	public int getBuyerid() {
		return buyerid;
	}
	public void setBuyerid(int buyerid) {
		this.buyerid = buyerid;
	}
	public int getAdvertiserid() {
		return advertiserid;
	}
	public void setAdvertiserid(int advertiserid) {
		this.advertiserid = advertiserid;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getAdid() {
		return adid;
	}
	public void setAdid(int adid) {
		this.adid = adid;
	}
	
	

}
