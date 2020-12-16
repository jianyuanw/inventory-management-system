package sg.edu.iss.ims.category;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.ToString.Exclude;

@Entity
@Data
public class Subcategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@Exclude
	private Category category;
	private String name;

	public Subcategory() {
		super();
	}
	
	public Subcategory(String name) {
		super();
		this.name = name;
	}

}
