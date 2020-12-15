package sg.edu.iss.ims.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.ims.model.Category;
import sg.edu.iss.ims.model.Subcategory;
import sg.edu.iss.ims.repo.CategoryRepository;
import sg.edu.iss.ims.repo.SubcategoryRepository;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository catRepo;
	@Autowired
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
	
	@Override
	public List<Subcategory> getSubcategories() {
		return subcatRepo.findAll();
	}
	
	@Override
	public List<Category> getCategories(){
		return catRepo.findAll();
	}

}
