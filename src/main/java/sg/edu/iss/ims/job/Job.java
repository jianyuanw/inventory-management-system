package sg.edu.iss.ims.job;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Job {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String description;
	
	private String carLicensePlate;
	
	private String customerName;
	
	@OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
	private List<JobTransaction> jobTransactions;
}
