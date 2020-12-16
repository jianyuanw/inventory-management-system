package sg.edu.iss.ims.job;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.ims.transaction.Transaction;

@Entity
@Data
@NoArgsConstructor
public class JobTransaction {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private Transaction transaction;
	
	@ManyToOne
	private Job job;
}
