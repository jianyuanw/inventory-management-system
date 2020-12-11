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
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String partNumber;
	private String name;
	private String description;
	private String type;
	private Double originalPrice;
	private Double wholesalePrice;
	private Double retailPrice;
	private Double partnerPrice;
	private String color;
	private String dimension;
	
	@OneToOne
	private Manufacturer manufacturer;
	
	private int reorderAt;
	
	private int reorderQuantity;
	
	private String shelfLocation;

	public Product(String partNumber, String name, String description, String type, Double originalPrice,
			Double wholesalePrice, Double retailPrice, Double partnerPrice, String color, String dimension,
			int reorderAt, int reorderQuantity, String shelfLocation) {
		super();
		this.partNumber = partNumber;
		this.name = name;
		this.description = description;
		this.type = type;
		this.originalPrice = originalPrice;
		this.wholesalePrice = wholesalePrice;
		this.retailPrice = retailPrice;
		this.partnerPrice = partnerPrice;
		this.color = color;
		this.dimension = dimension;
		this.reorderAt = reorderAt;
		this.reorderQuantity = reorderQuantity;
		this.shelfLocation = shelfLocation;
	}


}
