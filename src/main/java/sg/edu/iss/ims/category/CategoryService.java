package sg.edu.iss.ims.category;

import java.util.List;

public interface CategoryService {
	public void saveCategory(Category category);
	
	public Category readCategory(Long categoryId);
	
	public void updateCategory(Category category);
	
	public void deleteCategory(Category category);
	
	public List<Category> getCategories();
	
	public List<Subcategory> getSubcategories();

	public Category createCategory(String name);

	public void saveSubcategory(Subcategory subcategory);

	public Subcategory createSubcategory(Category category, String name);

	public Category findCategoryByName(String newCategory);

	public Subcategory findSubcategoryByName(String newCategory);
	
	public Category findCategoryById(Long id);
	
	public Subcategory findSubcategoryById(Long id);
	
	public void deleteSubcategory(Subcategory subcategory);

	public Subcategory findSubcategoryByNameAndCategory_Id(String subcategoryName, Long categoryId);
}
