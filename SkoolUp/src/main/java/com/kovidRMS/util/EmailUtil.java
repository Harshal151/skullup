package com.kovidRMS.util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.amazonaws.services.s3.AmazonS3;
import com.kovidRMS.daoImpl.LoginDAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Properties;

public class EmailUtil extends DAOConnection {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	String status = "error";
	
	
	public String sendReportCardMailToParents(int studentID, int organizationID, String realPath, 
			String pdfFIleName, String toEmailID, String fromEmailID, String fromEmailPassword, String studentName,
			String standardName, String divisionName, String termName, String academicYear, String organizationName) {

		final String fromEmail = fromEmailID; 

		final String toEmail = toEmailID; 

		final String fromEmailPass = fromEmailPassword;

		String emailBody = "";

		emailBody ="Dear "+studentName+",\n\nPlease find the attached "+standardName+"-"+divisionName+" report card for Academic Year "+academicYear+": "+termName+".\n\n" + 
				"Thanks & Regards,\n" + organizationName;
			
		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", "mail.sevasadanschool.in"); // SMTP Host
			props.put("mail.smtp.socketFactory.port", "587"); // SSL Port
			//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL
																							// Factory
																							// Class
			props.put("mail.smtp.auth", "true"); // Enabling SMTP
													// Authentication
			props.put("mail.smtp.port", "587"); // SMTP Port

			Authenticator auth = new Authenticator() {
				// override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, fromEmailPass);
				}
			};

			// Creating mail session with authentication and host,port
			// details
			Session session = Session.getInstance(props, auth);

			/*
			 * Creating message
			 */
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(fromEmail, studentName));

			msg.setSubject("Report Card | "+standardName+"-"+divisionName+" | Academic Year "+academicYear+": "+termName, "UTF-8");

			msg.setSentDate(new Date());

			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText(emailBody);

			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Second part is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = pdfFIleName;
			String filePath = realPath + "/" + pdfFIleName;
			DataSource source = new FileDataSource(filePath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			msg.setContent(multipart);

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			Transport.send(msg);

			status = "success";

			System.out.println("Report card pdf mail sent successfully.");

		} catch (Exception exception) {
			
			exception.printStackTrace();

			status = "error";
		}

		return status;

	}
	
}
