package sg.edu.iss.ims.email;

import java.util.List;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import sg.edu.iss.ims.item.Item;
import sg.edu.iss.ims.transaction.Transaction;
import sg.edu.iss.ims.user.Role;
import sg.edu.iss.ims.user.User;
import sg.edu.iss.ims.user.UserRepository;

@Service("EmailService")
public class EmailServiceImpl implements EmailService{
	private static final String ADDRESS = "inventorymanagementsystem@gmail.com";
	
	private final JavaMailSender emailSender;
	private final UserRepository userRepo;
	
	public EmailServiceImpl(JavaMailSender emailSender, UserRepository userRepo) {
		this.emailSender = emailSender;
		this.userRepo = userRepo;
	}
	
	public void sendSimpleMessage(String[] to, String subject, String text) {
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

	@Override
	public void sendStockNotification(Item dbItem, Transaction transaction) {
		String subject = "Low stock notification : " + dbItem.getProduct().getPartNumber() + " - " + dbItem.getProduct().getName();
		
		StringBuilder email = new StringBuilder("Dear admin clerks,\n\n");
		
		email.append("After a " + transaction.getTransactionType() + ": the following item has dropped below the reorder unit level of " + dbItem.getReorderAt() + "\n");
		email.append(dbItem.getProduct().getPartNumber() + " - " + dbItem.getProduct().getName() + "\n\n");
		email.append("Kindly take note of this situation.\n\n");
		
		email.append("AUTOMATED: PLEASE DO NOT RESPOND\n\n");
		email.append("Best regards,\n");
		email.append("Automated Inventory Notification\n");
		
		List<User> admins = userRepo.findAllByRole(Role.ADMIN_CLERK);
		String[] recipients = new String[admins.size()];
		
		for (int i = 0; i < admins.size(); i++) {
			recipients[i] = admins.get(i).getEmail();
		}
		
		this.sendSimpleMessage(recipients, subject, email.toString());
		
	}
}
