package sg.edu.iss.ims.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.iss.ims.user.UserRepository;

@SpringBootTest
public class UserRepositoryTest {
	
	@Autowired
	UserRepository urepo;
	
	@Test
	void findUserByUsername() {
		urepo.findUserByUsername("admin");
		urepo.findUserByUsername("mechanic");
	}

}
