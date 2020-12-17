package sg.edu.iss.ims.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public void createUser(User user) {
		userRepo.save(user);
	}

	@Override
	public User readUser(String username) {
		return userRepo.findUserByUsername(username);
	}

	public User readUser(Long id) {
		return userRepo.findById(id).get();
	}

	@Override
	public void updateUser(User user) {
		userRepo.save(user);
	}

	@Override
	public void deleteUser(User user) {
		userRepo.delete(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public String encode(String rawPassword) {
		return encoder.encode(rawPassword);
	}

	@Override
	public boolean noChange(User newUser, User currentUser) {
		return newUser.getUsername() == "" && newUser.getPassword() == "" &&
				newUser.getRole() == currentUser.getRole();
	}

}
