package sg.edu.iss.ims.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private Product product;

	private int units;

	private int reorderAt;

	private int reorderQuantity;

	private String shelfLocation;

	public Item(Product product, int units, int reorderAt, int reorderQuantity, String shelfLocation) {
		super();
		this.product = product;
		this.units = units;
		this.reorderAt = reorderAt;
		this.reorderQuantity = reorderQuantity;
		this.shelfLocation = shelfLocation;
	}

}
