package sg.edu.iss.ims.transaction;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.ims.item.Item;

@Entity
@Data
@NoArgsConstructor
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
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
