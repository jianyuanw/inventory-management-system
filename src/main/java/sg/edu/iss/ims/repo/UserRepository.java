package sg.edu.iss.ims.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.ims.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findUserByUsername(String username);
}
