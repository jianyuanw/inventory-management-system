package sg.edu.iss.ims.user;

import java.util.List;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

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
		return (newUser.getUsername().equals("") || newUser.getUsername().equals(currentUser.getUsername())) && 
			   (newUser.getPassword().equals("")) &&
			   (newUser.getRole().equals(null) || newUser.getRole().equals(currentUser.getRole())) &&
			   (newUser.getEmail().equals("") || newUser.getEmail().equals(currentUser.getEmail()));		   
	}
	
	@Override
	public void validateUser(User newUser, User currentUser, BindingResult results) {
		if (newUser.getUsername() != "") {
			if (!Pattern.matches("[A-Za-z0-9.]+", newUser.getUsername())) {
				results.rejectValue("username", "error.newUser", "Username must only contain alphanumeric characters and period");
				
			}
			if (userRepo.findUserByUsername(newUser.getUsername()) != null) {
				results.rejectValue("username", "error.newUser", "Username " + newUser.getUsername() + " already exists");
			}
		}
        
        if (newUser.getEmail() != "") {
        	if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", newUser.getEmail())) {
        		results.rejectValue("email", "error.newUser", "Email must be valid");
        	}
        }
        
        if (newUser.getRole() == null) {
        	results.rejectValue("role", "error.newUser", "Role cannot be null");
        }
	}
	
	@Override
	public void updateUser(User newUser, User currentUser) {
		this.invalidateSessions(currentUser.getUsername());
		
		if (newUser.getUsername() != "") {
			currentUser.setUsername(newUser.getUsername());
		}
		
        if (newUser.getPassword() != "") {
            currentUser.setPassword(this.encode(newUser.getPassword()));
        }
        
        if (newUser.getEmail() != "") {
        	currentUser.setEmail(newUser.getEmail());
        }
        
        if (newUser.getRole() != null) {
        	currentUser.setRole(newUser.getRole());
        }
        		
	}

	// Invalidate session if deleted user was logged in at the point of edit/deletion
	@Override
	public void invalidateSessions(String username) {
		List<Object> principals = sessionRegistry.getAllPrincipals();
		for (Object principal : principals) {
			if (principal instanceof MyUserDetails) {
				MyUserDetails loggedInUser = (MyUserDetails) principal;
				if (username.equals(loggedInUser.getUsername())) {
					List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false );
					for (SessionInformation sessionInfo : sessionsInfo) {
						sessionInfo.expireNow();
					}
				}
			}
		}
	}
}
