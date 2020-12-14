package sg.edu.iss.ims.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.ims.model.Category;
import sg.edu.iss.ims.model.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
	public Subcategory findSubcategoryByName(String subcategory);
	public List<Subcategory> findSubcategoriesByCategory(Category category);
}
