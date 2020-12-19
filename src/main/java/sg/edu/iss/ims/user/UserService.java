package sg.edu.iss.ims.user;

import java.util.List;

public interface UserService {

	void createUser(User user);

	User readUser(String username);

	User readUser(Long id);

	void updateUser(User user);

	void deleteUser(User user);
	
//	boolean authenticate(User user);

	List<User> getAllUsers();

	String encode(String rawPassword);

	boolean noChange(User newUser, User currentUser);

	void invalidateSessions(User user);
}