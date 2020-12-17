package sg.edu.iss.ims.job;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import sg.edu.iss.ims.item.Item;
import sg.edu.iss.ims.item.ItemList;
import sg.edu.iss.ims.item.ItemRepository;
import sg.edu.iss.ims.product.ProductRepository;
import sg.edu.iss.ims.transaction.Transaction;
import sg.edu.iss.ims.transaction.TransactionRepository;
import sg.edu.iss.ims.transaction.TransactionType;

@Service
@Transactional
public class JobServiceImpl implements JobService {

	private final JobRepository jobRepo;
	private final TransactionRepository transactionRepo;
	private final JobTransactionRepository jobTransactionRepo;
	private final ItemRepository itemRepo;
	
	public JobServiceImpl(JobRepository jobRepo, TransactionRepository transactionRepo, ProductRepository productRepo, JobTransactionRepository jobTransactionRepo, ItemRepository itemRepo) {
		this.jobRepo = jobRepo;
		this.transactionRepo = transactionRepo;
		this.jobTransactionRepo = jobTransactionRepo;
		this.itemRepo = itemRepo;
	}
	
	@Override
	public void createJob(Job job, ItemList itemList) {
		jobRepo.save(job);
		
		for (Item item : itemList.getList()) {
			Item dbItem = itemRepo.findItemById(item.getId());
			dbItem.setUnits(dbItem.getUnits() - item.getUnits());
			Transaction transaction = new Transaction(dbItem, -(item.getUnits()), TransactionType.SELL_STOCK);
			transactionRepo.save(transaction);
			JobTransaction jobTransaction = new JobTransaction();
			jobTransaction.setJob(job);
			jobTransaction.setTransaction(transaction);
			jobTransactionRepo.save(jobTransaction);
		}
		
	}

	@Override
	public Job readJob(Long id) {
		// TODO Auto-generated method stub
		return jobRepo.findJobById(id);
	}

	@Override
	public void updateJob(Job job) {
		// TODO Auto-generated method stub
		jobRepo.save(job);
		
	}

	@Override
	public void deleteJob(Job job) {
		// TODO Auto-generated method stub
		jobRepo.delete(job);
		
	}

}
