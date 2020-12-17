package sg.edu.iss.ims.supplier;

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
public class Supplier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Supplier name cannot be empty")
	@Pattern(regexp = "[A-Za-z ]+", message="Supplier name can only consist of spaces and alphabetical characters")
	private String name;

	public Supplier(String name) {
		super();
		this.name = name;
	}
}
