package sg.edu.iss.ims.email;

import sg.edu.iss.ims.item.Item;
import sg.edu.iss.ims.transaction.Transaction;

public interface EmailService {
	public void sendSimpleMessage(String[] to, String subject, String text);

	public void sendStockNotification(Item dbItem, Transaction transaction);
}
