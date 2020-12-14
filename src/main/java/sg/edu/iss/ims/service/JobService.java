package sg.edu.iss.ims.service;

import sg.edu.iss.ims.model.Job;

public interface JobService {
	public void createJob(Job job);
	
	public Job readJob(String jobName);
	
	public void updateJob(Job job);
	
	public void deleteJob(Job job);
}
