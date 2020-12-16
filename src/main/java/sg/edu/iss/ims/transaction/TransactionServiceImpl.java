package sg.edu.iss.ims.transaction;

import java.util.List;

import javax.transaction.Transactional;

@Transactional
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepo;
	
	public TransactionServiceImpl(TransactionRepository transactionRepo) {
		this.transactionRepo = transactionRepo;
	}
	
	@Override
	public Transaction findById(Long id) {
		return transactionRepo.findById(id).get();
	}

	@Override
	public List<Transaction> findByItem_Id(Long itemId) {
		return transactionRepo.findByItem_Id(itemId);
	}
	
}
