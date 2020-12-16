package sg.edu.iss.ims.product;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.ims.brand.Brand;
import sg.edu.iss.ims.category.Category;
import sg.edu.iss.ims.category.Subcategory;
import sg.edu.iss.ims.item.Item;
import sg.edu.iss.ims.supplier.Supplier;

@Entity
@Data
@NoArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String partNumber;
	private String name;
	private String description;
	private Double originalPrice;
	private Double wholesalePrice;
	private Double retailPrice;
	private Double partnerPrice;
	private String color;
	private String measurement;
	private String measurementType;
	private String image;

	@OneToOne
	private Category category;
	@OneToOne
	private Supplier supplier;
	@OneToOne
	private Brand brand;
	@OneToOne
	private Subcategory subcategory;
	@OneToOne(mappedBy="product",cascade=CascadeType.ALL)
	private Item item;

	
	public Product(String partNumber, String name, String description, Double originalPrice,
			Double wholesalePrice, Double retailPrice, Double partnerPrice, String color, String measurement,
			String measurementType, Category category, Subcategory subcategory, 
			Supplier supplier, Brand brand, String image) {
		super();
		this.partNumber = partNumber;
		this.name = name;
		this.description = description;
		this.originalPrice = originalPrice;
		this.wholesalePrice = wholesalePrice;
		this.retailPrice = retailPrice;
		this.partnerPrice = partnerPrice;
		this.color = color;
		this.measurement = measurement;
		this.measurementType = measurementType;
		this.category = category;
		this.subcategory = subcategory;
		this.supplier = supplier;
		this.brand = brand;
		this.image = image;
	}
	

}