package sg.edu.iss.ims.category;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;
import sg.edu.iss.ims.product.Product;

@Entity
@Data
@NoArgsConstructor
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Category name must not be empty")
	@Pattern(regexp = "[A-Za-z0-9 ]+", message="Category name can only consist of spaces and alphanumerical characters")
	private String name;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	@Exclude
	private List<Subcategory> subcategories;
	
	@OneToMany(mappedBy="category", cascade=CascadeType.ALL)
	private List<Product> products;	

	public Category(String name) {
		super();
		this.name = name;
	}
}
