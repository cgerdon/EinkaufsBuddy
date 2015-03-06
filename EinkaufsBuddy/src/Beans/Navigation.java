package Beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@RequestScoped
@ManagedBean(name = "navigation") 
public class Navigation {

	public String NaviProfil(){
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
	
}
