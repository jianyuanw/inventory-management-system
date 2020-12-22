package sg.edu.iss.ims.transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import sg.edu.iss.ims.email.EmailService;
import sg.edu.iss.ims.item.Item;
import sg.edu.iss.ims.item.ItemRepository;
import sg.edu.iss.ims.item.ItemState;
import sg.edu.iss.ims.job.JobTransaction;
import sg.edu.iss.ims.job.JobTransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepo;
	private final EmailService emailService;
	private final ItemRepository itemRepo;
	private final JobTransactionRepository jobTransactionRepo;
	
	public TransactionServiceImpl(TransactionRepository transactionRepo, ItemRepository itemRepo, 
								  EmailService emailService, JobTransactionRepository jobTransactionRepo) {
		this.transactionRepo = transactionRepo;
		this.emailService = emailService;
		this.itemRepo = itemRepo;
		this.jobTransactionRepo = jobTransactionRepo;
	}
	
	@Override
	public Transaction findById(Long id) {
		return transactionRepo.findById(id).get();
	}

	@Override
	public List<Transaction> findByItem_Id(Long itemId) {
		return transactionRepo.findAllByItem_Id(itemId);
	}
	
	@Override
	public List<Transaction> parseUsageReportQuery(Long itemId, String dateStart, String dateEnd) {
		LocalDateTime ldtStart = dateStart != "" ? LocalDate.parse(dateStart, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay() : null;
		LocalDateTime ldtEnd = dateEnd != "" ? LocalDate.parse(dateEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(23, 59, 59) : null;		
		
		List<Transaction> results = null;
		
		if (ldtStart == null && ldtEnd == null) {
			results = transactionRepo.findAllByItem_Id(itemId);
		} else if (ldtStart == null) {
			results = transactionRepo.findAllByItem_IdAndTransactionTimeLessThanEqual(itemId, ldtEnd);
		} else if (ldtEnd == null) {
			results = transactionRepo.findAllByItem_IdAndTransactionTimeGreaterThanEqual(itemId, ldtStart);
		} else {
			results = transactionRepo.findAllByItem_IdAndTransactionTimeGreaterThanEqualAndTransactionTimeLessThanEqual(itemId, ldtStart, ldtEnd);
		}
		
		return results;

	}

	@Override
	public boolean changeStock(Transaction transaction, Item dbItem) {
		if (transaction.getTransactionType() == TransactionType.RECEIVE_STOCK) {
			if ((dbItem.getUnits() + transaction.getQuantityChange()) >= dbItem.getReorderAt()) {
				dbItem.setState(ItemState.IN_STOCK);
			} else {
				dbItem.setState(ItemState.BELOW_REORDER_LEVEL);
			}
		} else {
			if ((dbItem.getUnits() + transaction.getQuantityChange()) < dbItem.getReorderAt()) {
				dbItem.setState(ItemState.BELOW_REORDER_LEVEL);
				emailService.sendStockNotification(dbItem, transaction);
			}
		}
		dbItem.setUnits(dbItem.getUnits() + transaction.getQuantityChange());
		this.save(transaction);
		
		return true;
	}
	
	@Override
	public void save(Transaction transaction) {
		transactionRepo.save(transaction);
	}

	@Override
	public List<Transaction> findAll() {
		return transactionRepo.findAll();		
	}

	@Override
	public void reverse(Long transactionId) {
		Transaction transaction = transactionRepo.findById(transactionId).get();
		JobTransaction jobTransaction = jobTransactionRepo.findByTransaction_Id(transactionId);
		if (jobTransaction != null) {
			jobTransactionRepo.delete(jobTransaction);			
		}		
		
		Item item = transaction.getItem();
		item.setUnits(item.getUnits() + -(transaction.getQuantityChange()));		
		
		transactionRepo.delete(transaction);
		
	}
}
