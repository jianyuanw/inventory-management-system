package sg.edu.iss.ims.service;

import java.util.List;

import sg.edu.iss.ims.model.Transaction;

public interface TransactionService {
	public Transaction findById(Long id);
	public List<Transaction> findByItem_Id(Long itemId);
}
