package sg.edu.iss.ims.service;

public interface EmailService {
	public void sendSimpleMessage(String to, String subject, String text);
}
