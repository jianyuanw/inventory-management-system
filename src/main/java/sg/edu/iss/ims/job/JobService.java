package sg.edu.iss.ims.job;

import sg.edu.iss.ims.item.ItemList;

public interface JobService {
	public void createJob(Job job, ItemList itemList);
	
	public Job readJob(Long id);
	
	public void updateJob(Job job);
	
	public void deleteJob(Job job);
}
