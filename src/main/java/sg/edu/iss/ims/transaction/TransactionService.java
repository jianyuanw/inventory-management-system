package sg.edu.iss.ims.transaction;

import java.util.List;

public interface TransactionService {
	public Transaction findById(Long id);
	public List<Transaction> findByItem_Id(Long itemId);
}