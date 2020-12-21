package sg.edu.iss.ims.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findUserByUsername(String username);

	public List<User> findAllByRole(Role role);
}
