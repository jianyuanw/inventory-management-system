package sg.edu.iss.ims.email;

public interface EmailService {
	public void sendSimpleMessage(String to, String subject, String text);
}
