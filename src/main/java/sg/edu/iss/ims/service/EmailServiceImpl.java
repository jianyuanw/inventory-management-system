package sg.edu.iss.ims.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("EmailService")
public class EmailServiceImpl implements EmailService{
	private static final String ADDRESS = "inventorymanagementsystem@gmail.com";
	
	private final JavaMailSender emailSender;
	
	public EmailServiceImpl(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}
	
	public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }
}
