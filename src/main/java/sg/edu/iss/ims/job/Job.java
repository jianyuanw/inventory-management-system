package sg.edu.iss.ims.job;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Job {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Description cannot be empty")
	@Pattern(regexp = "[A-Za-z0-9 ]+", message="Description can only consist of alphanumerical values and spaces")
	private String description;
	
	@NotEmpty(message = "Car license plate cannot be empty")
	@Pattern(regexp = "S[A-HJ-NP-Z]{2}[0-9]{1,4}[A-EGHJ-MPR-UX-Z]?", message="Invalid car license plate")
	private String carLicensePlate;
	
	@NotEmpty(message = "Customer name cannot be empty")
	@Pattern(regexp = "[A-Za-z ]+", message="Customer name can only consist of letters and spaces")
	private String customerName;
	
	@OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
	private List<JobTransaction> jobTransactions;
}
