package sg.edu.iss.ims.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.iss.ims.user.User;
import sg.edu.iss.ims.user.UserRepository;

@SpringBootTest
public class UserRepositoryTest {
	
	@Autowired
	UserRepository urepo;
	
	@Test
	void findUserByUsername() {
		User a = urepo.findUserByUsername("admin");
		User m = urepo.findUserByUsername("mechanic");
	}

}
