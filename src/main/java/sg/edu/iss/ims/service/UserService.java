package sg.edu.iss.ims.service;

import sg.edu.iss.ims.model.User;

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
}