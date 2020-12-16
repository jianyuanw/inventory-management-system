package sg.edu.iss.ims.service;

import sg.edu.iss.ims.model.ItemList;
import sg.edu.iss.ims.model.Job;

public interface JobService {
	public void createJob(Job job, ItemList itemList);
	
	public Job readJob(Long id);
	
	public void updateJob(Job job);
	
	public void deleteJob(Job job);
}
