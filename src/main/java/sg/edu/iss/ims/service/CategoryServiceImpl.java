package sg.edu.iss.ims.service;

import java.util.List;

import sg.edu.iss.ims.model.Category;
import sg.edu.iss.ims.model.Subcategory;
import sg.edu.iss.ims.repo.CategoryRepository;
import sg.edu.iss.ims.repo.SubcategoryRepository;

public class CategoryServiceImpl implements CategoryService {
	private CategoryRepository catRepo;
	private SubcategoryRepository subcatRepo;
	
	public CategoryServiceImpl(CategoryRepository catRepo, SubcategoryRepository subcatRepo) {
		this.catRepo = catRepo;
		this.subcatRepo = subcatRepo;
	}
	
	@Override
	public void createCategory(Category category) {
		catRepo.save(category);
	}

	@Override
	public void readCategory(Long categoryId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCategory(Category category) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCategory(Category category) {
		// TODO Auto-generated method stub

	}
	
	public void createSubcategory(Subcategory subcategory) {
		subcatRepo.save(subcategory);
	}
	
	public List<Subcategory> getSubcategories() {
		return subcatRepo.findAll();
	}

}
