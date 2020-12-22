package sg.edu.iss.ims.category;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class Subcategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@Exclude
	private Category category;
	
	@NotEmpty(message = "Subcategory name must not be empty")
	@Pattern(regexp = "[A-Za-z0-9 ]+", message="Subcategory name can only consist of spaces and alphanumerical characters")
	private String name;

	@OneToMany(mappedBy="subcategory", cascade=CascadeType.ALL)
	private List<Product> products;	
	
	public Subcategory(Category category, String name) {
		super();
		this.category = category;
		this.name = name;
	}
	
	public Subcategory(String name) {
		super();
		this.name = name;
	}

}
