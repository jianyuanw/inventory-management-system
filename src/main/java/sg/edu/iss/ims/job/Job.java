package sg.edu.iss.ims.job;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.ims.transaction.Transaction;

@Entity
@Data
@NoArgsConstructor
public class Job {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Car license plate must not be empty")
	@Pattern(regexp = "S[A-HJ-NP-Z]{2}[0-9]{1,4}[A-EGHJ-MPR-UX-Z]?", message="Invalid car license plate")
	private String carLicensePlate;
	
	@NotEmpty(message = "Customer name must not be empty")
	@Pattern(regexp = "[A-Za-z ]+", message="Customer name can only consist of spaces and alphabetical characters")
	private String customerName;
	
	@NotEmpty(message = "Description must not be empty")
	@Pattern(regexp = "[A-Za-z0-9, ]+", message="Description can only consist of spaces, commas and alphanumerical characters")
	private String description;	
	
	@OneToMany(mappedBy = "job", fetch = FetchType.EAGER)
	private List<Transaction> transactions;
}
