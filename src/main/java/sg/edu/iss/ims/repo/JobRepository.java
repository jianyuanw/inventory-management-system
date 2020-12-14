package sg.edu.iss.ims.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.ims.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
	public Job findJobByName(String jobName);
	
}
