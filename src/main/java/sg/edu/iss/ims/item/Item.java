package sg.edu.iss.ims.item;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.ims.product.Product;
import sg.edu.iss.ims.transaction.Transaction;

@Entity
@Data
@NoArgsConstructor
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private Product product;

	@NotNull(message = "Units must not be null")
	@Min(value = 0, message = "Units must not be negative")
	private int units;

	@NotNull(message = "Units must not be null")
	@Min(value = 1, message = "Reorder at value must be more than 0")
	private int reorderAt;

	@NotNull(message = "Units must not be null")
	@Min(value = 1, message = "Minimum reorder quantity must be more than 0")
	private int reorderQuantity;

	@NotEmpty(message = "Shelf location must not be empty")
	private String shelfLocation;
	
	@OneToMany(mappedBy="item", cascade=CascadeType.ALL)
	private List<Transaction> transactions;

	private ItemState state;
	@OneToMany(mappedBy="item", cascade=CascadeType.ALL)
	private List<Reorder> reorders;
	

	public Item(Product product, int units, int reorderAt, int reorderQuantity, String shelfLocation,
				ItemState state) {
		super();
		this.product = product;
		this.units = units;
		this.reorderAt = reorderAt;
		this.reorderQuantity = reorderQuantity;
		this.shelfLocation = shelfLocation;
		this.state = state;
	}
	
	public Item(Product product, ItemState state) {
		super();
		this.product = product;
		this.state = state;
	}

}
