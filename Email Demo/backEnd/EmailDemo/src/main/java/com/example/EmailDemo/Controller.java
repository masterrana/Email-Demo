package com.example.EmailDemo;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@Autowired
	private JavaMailSender sender;

	@RequestMapping(path="/Mail",method = RequestMethod.POST)
	
	public ApiResponse sendMail(@RequestBody Email model) 
	{
		String email,data,cc;
		email=model.getEmail();
		data=model.getData();
		cc=model.getCc();

		try 
		{
			InternetAddress from = new InternetAddress("m2312@csci.co.in", "Vivek Rana");
			InternetAddress[] to = new InternetAddress[] { new InternetAddress(email, model.getTo()) };
			InternetAddress [] c= new InternetAddress[] {new InternetAddress(cc)};

			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setText(data, true);
			helper.setCc(c);
			//helper.setFileTypeMap(file1);
			helper.setSubject("Example of the Email");
			sender.send(message);
			ApiResponse response = new ApiResponse();
			response.setMessage("Send Successfully.....!!!:)");
			return response;
		
		} catch (MailException | UnsupportedEncodingException | MessagingException e) 
		{
			e.printStackTrace();
		}
		ApiResponse response = new ApiResponse();
		response.setMessage("Not Send Successfully...!!!");
		return response;
	}

	class ApiResponse 
	{
		private String message;

		public String getMessage() 
		{
			return message;
		}

		public void setMessage(String message) 
		{
			this.message = message;
		}
	}
}