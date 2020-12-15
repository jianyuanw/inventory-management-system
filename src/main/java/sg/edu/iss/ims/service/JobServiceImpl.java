package sg.edu.iss.ims.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import sg.edu.iss.ims.model.Item;
import sg.edu.iss.ims.model.ItemList;
import sg.edu.iss.ims.model.Job;
import sg.edu.iss.ims.model.JobTransaction;
import sg.edu.iss.ims.model.Transaction;
import sg.edu.iss.ims.model.TransactionType;
import sg.edu.iss.ims.repo.ItemRepository;
import sg.edu.iss.ims.repo.JobRepository;
import sg.edu.iss.ims.repo.JobTransactionRepository;
import sg.edu.iss.ims.repo.ProductRepository;
import sg.edu.iss.ims.repo.TransactionRepository;

@Service
@Transactional
public class JobServiceImpl implements JobService {

	private final JobRepository jobRepo;
	private final TransactionRepository transactionRepo;
	private final ProductRepository productRepo;
	private final JobTransactionRepository jobTransactionRepo;
	private final ItemRepository itemRepo;
	
	public JobServiceImpl(JobRepository jobRepo, TransactionRepository transactionRepo, ProductRepository productRepo, JobTransactionRepository jobTransactionRepo, ItemRepository itemRepo) {
		this.jobRepo = jobRepo;
		this.transactionRepo = transactionRepo;
		this.productRepo = productRepo;
		this.jobTransactionRepo = jobTransactionRepo;
		this.itemRepo = itemRepo;
	}
	
	@Override
	public void createJob(Job job, ItemList itemList) {
		jobRepo.save(job);
		
		for (Item item : itemList.getList()) {
			Transaction transaction = new Transaction(productRepo.findProductById(item.getId()), -(item.getUnits()), TransactionType.SELL_STOCK);
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
