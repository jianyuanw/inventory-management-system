package sg.edu.iss.ims.brand;

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
import sg.edu.iss.ims.product.Product;

@Entity
@Data
@NoArgsConstructor
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Brand name must not be empty")
	@Pattern(regexp = "[A-Za-z0-9 ]+", message="Brand name can only consist of spaces and alphanumerical characters")
	private String name;

	@OneToMany(mappedBy="brand", cascade=CascadeType.ALL)
	private List<Product> products;	
	
	public Brand(String name) {
		super();
		this.name = name;
	}

}
