package sg.edu.iss.ims.service;

import sg.edu.iss.ims.model.Category;

public interface CategoryService {
	public void createCategory(Category category);
	
	public void readCategory(Long categoryId);
	
	public void updateCategory(Category category);
	
	public void deleteCategory(Category category);
}
