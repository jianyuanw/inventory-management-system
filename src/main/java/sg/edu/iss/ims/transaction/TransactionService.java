package sg.edu.iss.ims.transaction;

import java.util.List;

import sg.edu.iss.ims.item.Item;

public interface TransactionService {
	public Transaction findById(Long id);

	public List<Transaction> findByItem_Id(Long itemId);

	public List<Transaction> parseUsageReportQuery(Long itemId, String dateStart, String dateEnd);

	public void save(Transaction transaction);

	boolean changeStock(Transaction transaction, Item dbItem);

	public List<Transaction> findAll();

	public void reverse(Long transactionId);
}
