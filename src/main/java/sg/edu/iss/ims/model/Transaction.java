package sg.edu.iss.ims.model;

import java.time.Instant;

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
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany
	private Item item;
	
	private TransactionType transactionType;
	
	private Integer quantityChange;
	
	private Instant transactionTime;
	
	public Transaction(Item item, int quantityChange, TransactionType transactionType) {
		this.item = item;
		this.transactionType = transactionType;
		this.quantityChange = quantityChange;
		this.transactionTime = Instant.now();
	}
}
