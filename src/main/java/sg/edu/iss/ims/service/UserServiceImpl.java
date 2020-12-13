package sg.edu.iss.ims.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.ims.model.User;
import sg.edu.iss.ims.repo.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
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
	public boolean authenticate(User user) {
		User dbuser = userRepo.findUserByUsername(user.getUsername());
		if (user.getUsername().equals(dbuser.getUsername()) && user.getPassword().equals(dbuser.getPassword())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

//	private String hash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
//		SecureRandom random = new SecureRandom();
//		byte[] salt = new byte[32];
//		random.nextBytes(salt);
//
//		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
//		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//
//		return factory.generateSecret(spec).getEncoded().toString();
//	}
}
