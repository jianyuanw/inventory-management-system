package sg.edu.iss.ims.brand;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Brand name must not be empty")
	@Pattern(regexp = "[A-Za-z0-9 ]+", message="Brand name can only consist of spaces and alphanumerical characters")
	private String name;

	public Brand(String name) {
		super();
		this.name = name;
	}

}
