package sg.edu.iss.ims.service;

import sg.edu.iss.ims.model.User;

import java.util.List;

public interface UserInterface {
	public void createUser(User user);

	public User readUser(String username);

	public void updateUser(User user);

	public void deleteUser(User user);

	public boolean authenticate(User user);

	public List<User> getAllUsers();
}