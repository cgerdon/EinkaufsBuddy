package Beans;

import java.io.Serializable;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@ManagedBean(name = "mailer")  

//@RequestScoped  
@SessionScoped

public class Mailer implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8753368215083610703L;
	private String sender;
	private String titel;
	private String text;
	
	
	


	public String getSender() {
		return sender;
	}





	public void setSender(String sender) {
		this.sender = sender;
	}





	public String getTitel() {
		return titel;
	}





	public void setTitel(String titel) {
		this.titel = titel;
	}





	public String getText() {
		return text;
	}





	public void setText(String text) {
		this.text = text;
	}





	public void SendMail(){

	 final String username = "einkaufsbuddy@gmail.com";
     final String password = "buddydeseinkaufs";

     Properties props = new Properties();
     props.put("mail.smtp.starttls.enable", "true");
     props.put("mail.smtp.auth", "true");
     props.put("mail.smtp.host", "smtp.gmail.com");
     props.put("mail.smtp.port", "587");

     Session session = Session.getInstance(props,
       new javax.mail.Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
             return new PasswordAuthentication(username, password);
         }
       });

     try {

         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress("einkaufsbuddy@gmail.com"));
         message.setRecipients(Message.RecipientType.TO,
             InternetAddress.parse("cgerdon@gmail.com"));
         message.setSubject("Testing Subject");
         message.setText("Dear Mail Crawler,"
             + "\n\n No spam to my email, please!");

         Transport.send(message);

         System.out.println("Done");

     } catch (MessagingException e) {
         throw new RuntimeException(e);
     }
 }

}
