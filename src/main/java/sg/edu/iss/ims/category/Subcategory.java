package sg.edu.iss.ims.category;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

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
	@Pattern(regexp = "[A-Za-z ]+", message="Subcategory name can only consist of spaces and alphabetical characters")
	private String name;

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
