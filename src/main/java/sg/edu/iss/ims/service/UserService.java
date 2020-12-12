package sg.edu.iss.ims.service;

import sg.edu.iss.ims.model.User;

public interface UserService {
	public void createUser(User user);

	public User readUser(String username);

	public void updateUser(User user);

	public void deleteUser(User user);
	
	public boolean authenticate(User user);
}
