package sg.edu.iss.ims.category;

import java.util.List;

public interface CategoryService {
	public void createCategory(Category category);
	
	public void readCategory(Long categoryId);
	
	public void updateCategory(Category category);
	
	public void deleteCategory(Category category);
	
	public List<Category> getCategories();
	
	public List<Subcategory> getSubcategories();
}
