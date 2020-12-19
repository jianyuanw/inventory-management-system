package sg.edu.iss.ims.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sg.edu.iss.ims.security.MyUserDetails;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private SessionRegistry sessionRegistry;

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

	// Invalidate session if deleted user was logged in at the point of edit/deletion
	@Override
	public void invalidateSessions(User user) {
		List<Object> principals = sessionRegistry.getAllPrincipals();
		for (Object principal : principals) {
			if (principal instanceof MyUserDetails) {
				MyUserDetails loggedInUser = (MyUserDetails) principal;
				if (user.getUsername().equals(loggedInUser.getUsername())) {
					List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false );
					for (SessionInformation sessionInfo : sessionsInfo) {
						sessionInfo.expireNow();
					}
				}
			}
		}
	}
}
