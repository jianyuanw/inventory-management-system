package sg.edu.iss.ims.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findUserByUsername(String username);
}
