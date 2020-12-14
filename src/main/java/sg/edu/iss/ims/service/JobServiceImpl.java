package sg.edu.iss.ims.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import sg.edu.iss.ims.model.Job;
import sg.edu.iss.ims.repo.JobRepository;

@Service
@Transactional
public class JobServiceImpl implements JobService {

	private final JobRepository jobRepo;
	
	public JobServiceImpl(JobRepository jobRepo) {
		this.jobRepo = jobRepo;
	}
	
	@Override
	public void createJob(Job job) {
		// TODO Auto-generated method stub
		jobRepo.save(job);
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
