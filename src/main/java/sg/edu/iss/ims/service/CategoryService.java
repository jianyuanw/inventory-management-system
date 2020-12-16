package sg.edu.iss.ims.service;

import java.util.List;

import sg.edu.iss.ims.model.Category;
import sg.edu.iss.ims.model.Subcategory;

public interface CategoryService {
	public void createCategory(Category category);
	
	public void readCategory(Long categoryId);
	
	public void updateCategory(Category category);
	
	public void deleteCategory(Category category);
	
	public List<Category> getCategories();
	
	public List<Subcategory> getSubcategories();
}
