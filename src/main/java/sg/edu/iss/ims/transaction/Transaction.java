package sg.edu.iss.ims.transaction;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.ims.item.Item;
import sg.edu.iss.ims.job.Job;

@Entity
@Data
@NoArgsConstructor
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Item item;
	
	@NotNull(message = "Transaction Type cannot be null")
	private TransactionType transactionType;
	
	@ManyToOne
	private Job job;
	
	private Integer quantityChange;
	
	private LocalDateTime transactionTime;
	
	public Transaction(Item item, int quantityChange, TransactionType transactionType) {
		this.item = item;
		this.transactionType = transactionType;
		this.quantityChange = quantityChange;
		this.transactionTime = LocalDateTime.now();
	}
}
