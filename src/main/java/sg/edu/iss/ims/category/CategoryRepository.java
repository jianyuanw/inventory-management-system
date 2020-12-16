package sg.edu.iss.ims.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	public Category findCategoryByName(String category);
	
	@Query("SELECT cat FROM Category cat JOIN FETCH cat.subcategories WHERE cat.name = (:name)")
	public Category findCategoryByNameEager(@Param("name") String category);
	
}
