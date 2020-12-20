package sg.edu.iss.ims.transaction;

import java.util.List;

public interface TransactionService {
	public Transaction findById(Long id);
	public List<Transaction> findByItem_Id(Long itemId);
	public List<Transaction> parseUsageReportQuery(Long itemId, String dateStart, String dateEnd);
	public void save(Transaction transaction);
	public boolean changeStock(Transaction transaction, Long itemId);
}
