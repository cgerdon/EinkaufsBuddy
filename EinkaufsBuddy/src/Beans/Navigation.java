package Beans;

import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@RequestScoped
@ManagedBean(name = "navigation") 
public class Navigation {

	public String NaviProfil() throws SQLException{
		User.loadProfil();
		return "profil?faces-redirect=true";
	}
	
	public String NaviInserate(){
		return "viewadverts?faces-redirect=true";
	}		
	
	public String advertsadd(){
		return "createadvert?faces-redirect=true";
	}	
	
	public String advertssearch(){
		return "home?faces-redirect=true";
	}	
	
	public String zursimplesearch(){
		return "simpleSearchResult?faces-redirect=true";
	}
	
	public String zurviewadvert(){
		return "viewadverts?faces-redirect=true";
	}

	
	public String zurmessagedetail(){
		return "messagedetail?faces-redirect=true";
	}
	
}
