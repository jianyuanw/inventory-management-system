package sg.edu.iss.ims.product;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;
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
	
	@NotEmpty(message = "Part number must not be empty")
	private String partNumber;
	
	@NotEmpty(message = "Product name must not be empty")
	@Pattern(regexp = "[A-Za-z0-9 ]+", message="Product name can only consist of spaces and alphanumerical characters")
	private String name;
	
	@NotEmpty(message = "Description must not be empty")
	@Pattern(regexp = "[A-Za-z0-9, ]+", message="Description can only consist of spaces, commas and alphanumerical characters")	
	private String description;
	
	@NotNull(message = "Price must not be null")	
	private Double originalPrice;
	
	@NotNull(message = "Price must not be null")
	private Double partnerPrice;
	
	@NotNull(message = "Price must not be null")
	private Double wholesalePrice;
	
	@NotNull(message = "Price must not be null")
	private Double retailPrice;
	
	@NotEmpty(message = "Color must not be empty")
	private String color;
	
	@NotEmpty(message = "Measurement must not be empty")
	private String measurement;
	
	@NotEmpty(message = "Measurement type must not be empty")
	private String measurementType;
	private String image;

	@OneToOne
	@Exclude
	private Category category;
	@OneToOne
	@Exclude
	private Supplier supplier;
	@OneToOne
	@Exclude
	private Brand brand;
	@OneToOne
	@Exclude
	private Subcategory subcategory;
	@OneToOne(mappedBy="product",cascade=CascadeType.ALL)
	@Exclude
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
