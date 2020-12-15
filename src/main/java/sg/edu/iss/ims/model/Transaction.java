package sg.edu.iss.ims.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private Product product;
	
	private TransactionType transactionType;
	
	private Integer quantityChange;
	
	private Instant transactionTime;
	
	public Transaction(Product product, int quantityChange, TransactionType transactionType) {
		this.product = product;
		this.transactionType = transactionType;
		this.quantityChange = quantityChange;
		this.transactionTime = Instant.now();
	}
}
