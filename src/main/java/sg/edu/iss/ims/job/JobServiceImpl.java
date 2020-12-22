package sg.edu.iss.ims.job;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import sg.edu.iss.ims.item.Item;
import sg.edu.iss.ims.item.ItemList;
import sg.edu.iss.ims.item.ItemRepository;
import sg.edu.iss.ims.product.ProductRepository;
import sg.edu.iss.ims.transaction.Transaction;
import sg.edu.iss.ims.transaction.TransactionRepository;
import sg.edu.iss.ims.transaction.TransactionService;
import sg.edu.iss.ims.transaction.TransactionServiceImpl;
import sg.edu.iss.ims.transaction.TransactionType;

@Service
@Transactional
public class JobServiceImpl implements JobService {

	private final JobRepository jobRepo;
	private final TransactionRepository transactionRepo;
	private final JobTransactionRepository jobTransactionRepo;
	private final ItemRepository itemRepo;
	private final TransactionService transactionService;
	
	public JobServiceImpl(JobRepository jobRepo, TransactionRepository transactionRepo, ProductRepository productRepo,
			              JobTransactionRepository jobTransactionRepo, ItemRepository itemRepo, TransactionServiceImpl transactionServiceImpl) {
		this.jobRepo = jobRepo;
		this.transactionRepo = transactionRepo;
		this.jobTransactionRepo = jobTransactionRepo;
		this.itemRepo = itemRepo;
		this.transactionService = transactionServiceImpl;
	}
	
	@Override
	public void createJob(Job job, ItemList itemList) {
		jobRepo.save(job);
		
		for (Item item : itemList.getList()) {
			Item dbItem = itemRepo.findItemById(item.getId());
			Transaction transaction = new Transaction(dbItem, -(item.getUnits()), TransactionType.SELL_STOCK);
			transactionService.changeStock(transaction, dbItem);
			JobTransaction jobTransaction = new JobTransaction();
			jobTransaction.setJob(job);
			jobTransaction.setTransaction(transaction);
			jobTransactionRepo.save(jobTransaction);
		}
		
	}

	@Override
	public Job readJob(Long id) {
		return jobRepo.findJobById(id);
	}

	@Override
	public void updateJob(Job job) {
		jobRepo.save(job);
		
	}

	@Override
	public void deleteJob(Job job) {
		jobRepo.delete(job);
		
	}

}
