package sg.edu.iss.ims.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "imsuser")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Username must not be empty")
	@Pattern(regexp = "[A-Za-z0-9.]+", message = "Username must only contain alphanumeric characters and .")
	private String username;
	
	@NotEmpty(message = "Password must not be empty")
	private String password;
	
	@NotNull(message = "Role must not be null")
	private Role role;

	public User(String username, String password, Role role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}

}
