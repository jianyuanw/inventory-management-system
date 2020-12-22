package sg.edu.iss.ims.job;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import sg.edu.iss.ims.item.Item;
import sg.edu.iss.ims.item.ItemList;
import sg.edu.iss.ims.item.ItemRepository;
import sg.edu.iss.ims.product.ProductRepository;
import sg.edu.iss.ims.transaction.Transaction;
import sg.edu.iss.ims.transaction.TransactionService;
import sg.edu.iss.ims.transaction.TransactionServiceImpl;
import sg.edu.iss.ims.transaction.TransactionType;

@Service
@Transactional
public class JobServiceImpl implements JobService {

	private final JobRepository jobRepo;
	private final ItemRepository itemRepo;
	private final TransactionService transactionService;
	
	public JobServiceImpl(JobRepository jobRepo, ProductRepository productRepo, ItemRepository itemRepo, TransactionServiceImpl transactionServiceImpl) {
		this.jobRepo = jobRepo;
		this.itemRepo = itemRepo;
		this.transactionService = transactionServiceImpl;
	}
	
	@Override
	public void createJob(Job job, ItemList itemList) {
		jobRepo.save(job);
		
		for (Item item : itemList.getList()) {
			Item dbItem = itemRepo.findItemById(item.getId());
			Transaction transaction = new Transaction(dbItem, -(item.getUnits()), TransactionType.SELL_STOCK);
			transaction.setJob(job);
			transactionService.changeStock(transaction, dbItem);
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
