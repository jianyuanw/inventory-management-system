package sg.edu.iss.ims.category;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	
	private CategoryRepository catRepo;
	private SubcategoryRepository subcatRepo;
	
	public CategoryServiceImpl(CategoryRepository catRepo, SubcategoryRepository subcatRepo) {
		this.catRepo = catRepo;
		this.subcatRepo = subcatRepo;
	}
	
	@Override
	public Category createCategory(String name) {
		Category category = new Category(name);
		catRepo.save(category);
		return category;
	}
	
	@Override
	public void saveCategory(Category category) {
		catRepo.save(category);
	}

	@Override
	public Category readCategory(Long categoryId) {
		return catRepo.getOne(categoryId);

	}

	@Override
	public void updateCategory(Category category) {
		catRepo.save(category);

	}

	@Override
	public void deleteCategory(Category category) {
		catRepo.delete(category);

	}
	
	@Override
	public void saveSubcategory(Subcategory subcategory) {
		subcatRepo.save(subcategory);
	}
	
	@Override
	public Subcategory createSubcategory(Category category, String name) {
		Subcategory subcategory = new Subcategory(category, name);
		subcatRepo.save(subcategory);
		return subcategory;
	}
	
	@Override
	public List<Subcategory> getSubcategories() {
		return subcatRepo.findAll();
	}
	
	@Override
	public List<Category> getCategories(){
		return catRepo.findAll();
	}
	
	@Override
	public Category findCategoryByName(String category) {
		return catRepo.findByName(category);
	}
	
	@Override 
	public Subcategory findSubcategoryByName(String subcategory) {
		return subcatRepo.findSubcategoryByName(subcategory);
	}

	@Override
	public Category findCategoryById(Long id) {
		return catRepo.findById(id).get();
	}
	
	@Override
	public Subcategory findSubcategoryById(Long id) {
		return subcatRepo.findById(id).get();
	}
	
	@Override
	public void deleteSubcategory(Subcategory subcategory) {
		subcatRepo.delete(subcategory);
	}
	
	@Override
	public Subcategory findSubcategoryByNameAndCategory_Id(String subcategoryName, Long categoryId) {
		return subcatRepo.findByNameAndCategory_Id(subcategoryName, categoryId);
	}
}
