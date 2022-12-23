package main;


import java.util.Properties;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;


public class MailSender {



	private static Properties properties = null;
	private static Session mailSession = null;
	protected final static String GMX_LOGIN_PROVIDER = "mail.gmx.net";

	public static void main(String[] args) {
		
		
		if(MailSender.PrepareEmailSessionWithProvider(
		"user@gmx.de", 
		"password", 
		MailSender.GMX_LOGIN_PROVIDER) 
				
		&&
				
		MailSender.sendMessage(
		"receiver@mailprovider.com", 
		"Methoden-Split 3", 
		"<html><body><h1>Test</h1></body></html>")) {
			
			
			System.out.println("Message has been sent");
		}else {
			System.out.println("False");
		}


	}



	protected static boolean PrepareEmailSessionWithProvider(String sender, String password, String provider) {
		try {
			if(MailSender.createProperties(sender, password, provider)) {
				//good
			}else {
				return false;
			}
			
			
			if(MailSender.createSession()) {
				//even better
			}else {
				return false;
			}
			
			
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}



	
	

	private static boolean createProperties(String mail_sender, String password, String login_provider) {
		try {
			if(MailSender.properties != null) {
				MailSender.properties = null;
				System.gc();
			}
			MailSender.properties = new Properties();
			MailSender.properties.put("mail.transport.protocol", "smtp");
			MailSender.properties.put("mail.smtp.host", login_provider);
			MailSender.properties.put("mail.smtp.port", "587");
			MailSender.properties.put("mail.smtp.auth", "true");
			MailSender.properties.put("mail.smtp.user", mail_sender);
			MailSender.properties.put("mail.smtp.password", password);
			MailSender.properties.put("mail.smtp.starttls.enable", "true");

			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private static boolean createSession() {
		if(MailSender.mailSession != null) {
			MailSender.mailSession = null;
			System.gc();
		}
		
		if(MailSender.properties == null) {
			return false;
		}
		
		
		try {
			MailSender.mailSession = Session.getInstance(MailSender.properties, new javax.mail.Authenticator()
			{
				@Override
				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(MailSender.properties.getProperty("mail.smtp.user"),
							MailSender.properties.getProperty("mail.smtp.password"));
				}
			});
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}


	}

	protected static boolean sendMessage(String mail_receiver, String mail_subject, String mail_html_message) {
		Message message = new MimeMessage(mailSession);
		String mail_sender = MailSender.properties.getProperty("mail.smtp.user");

		try {
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail_receiver));			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}



		try {
			message.setFrom(new InternetAddress(mail_sender));
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		try {
			message.setSubject(mail_subject);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		try {

			message.setContent(mail_html_message, "text/html; charset=utf-8");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		try {
			message.saveChanges();
			Transport.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	
	
	
	
}



