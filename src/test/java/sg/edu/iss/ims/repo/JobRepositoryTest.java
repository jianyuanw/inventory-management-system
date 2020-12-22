package sg.edu.iss.ims.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.iss.ims.job.JobRepository;

@SpringBootTest
public class JobRepositoryTest {
	
	@Autowired
	JobRepository jrepo;
	
	@Test
	void findJobById() {
		jrepo.findAll();
	}

}
