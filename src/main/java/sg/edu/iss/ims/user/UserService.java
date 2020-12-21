package sg.edu.iss.ims.user;

import java.util.List;

import org.springframework.validation.BindingResult;

public interface UserService {

	void createUser(User user);

	User readUser(String username);

	User readUser(Long id);

	void updateUser(User user);

	void deleteUser(User user);

	List<User> getAllUsers();

	String encode(String rawPassword);

	boolean noChange(User newUser, User currentUser);

	void invalidateSessions(String username);

	void updateUser(User newUser, User currentUser);

	void validateUser(User newUser, User currentUser, BindingResult results);
}