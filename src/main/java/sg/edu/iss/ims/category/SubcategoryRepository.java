package sg.edu.iss.ims.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
	public Subcategory findSubcategoryByName(String subcategory);
	public List<Subcategory> findSubcategoriesByCategory(Category category);
	public Subcategory findByNameAndCategory_Id(String subcategoryName, Long categoryId);
}
