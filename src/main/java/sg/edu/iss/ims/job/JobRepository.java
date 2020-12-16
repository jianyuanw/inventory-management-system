package sg.edu.iss.ims.job;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
	public Job findJobById(Long id);
	
}
