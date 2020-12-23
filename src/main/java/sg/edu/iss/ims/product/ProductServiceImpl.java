package sg.edu.iss.ims.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import sg.edu.iss.ims.brand.Brand;
import sg.edu.iss.ims.brand.BrandRepository;
import sg.edu.iss.ims.category.Category;
import sg.edu.iss.ims.category.CategoryRepository;
import sg.edu.iss.ims.category.Subcategory;
import sg.edu.iss.ims.category.SubcategoryRepository;
import sg.edu.iss.ims.item.Item;
import sg.edu.iss.ims.item.ItemRepository;
import sg.edu.iss.ims.item.ItemState;
import sg.edu.iss.ims.supplier.Supplier;
import sg.edu.iss.ims.supplier.SupplierRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepo;
	private final SupplierRepository supplierRepo;
	private final BrandRepository brandRepo;
	private final CategoryRepository categoryRepo;
	private final SubcategoryRepository subcategoryRepo;
	private final ItemRepository itemRepo;

	public ProductServiceImpl(ProductRepository productRepo, SupplierRepository supplierRepo, BrandRepository brandRepo,
			CategoryRepository categoryRepo, SubcategoryRepository subcategoryRepo, ItemRepository itemRepo) {
		this.productRepo = productRepo;
		this.supplierRepo = supplierRepo;
		this.brandRepo = brandRepo;
		this.categoryRepo = categoryRepo;
		this.subcategoryRepo = subcategoryRepo;
		this.itemRepo = itemRepo;
	}

	@Override
	public void saveProduct(Product product) {
		productRepo.save(product);

	}

	@Override
	public ArrayList<Product> findProductByName(String productName) {
		return productRepo.findByName(productName);

	}

	@Override
	public Product findProductById(Long id) {
		return productRepo.findProductById(id);

	}

	@Override
	public void deleteProduct(Long id) {
		productRepo.delete(findProductById(id));

	}

	public List<Product> list() {
		return productRepo.findAll();
	}

	@Override
	public HashMap<String, String> validate(String newSupplier, String newBrand, String newCategory,
			String newSubcategory) {
		HashMap<String, String> errors = new HashMap<String, String>();

		if (newSupplier == "") {
			errors.put("newSupplier", "Supplier name must not be empty");
		} else {
			String error = "";
			if (!Pattern.matches("[a-zA-Z0-9 ]+", newSupplier)) {
				error += "Supplier name can only consist of spaces and alphanumerical characters\n";
			}
			if (supplierRepo.findSupplierByName(newSupplier) != null) {
				error += "Supplier name " + newSupplier + " already exists\n";
			}
			
			if (error != "") {
				errors.put("newSupplier", error);
			}
		}

		if (newBrand == "") {
			errors.put("newBrand", "Brand name must not be empty");
		} else {
			String error = "";
			if (!Pattern.matches("[a-zA-Z0-9 ]+", newBrand)) {
				error += "Brand name can only consist of spaces and alphanumerical characters\n";
			}
			if (brandRepo.findByName(newBrand) != null) {
				error += "Brand name " + newBrand + " already exists\n";
			}
			
			if (error != "") {
				errors.put("newBrand", error);
			}
		}

		if (newCategory == "") {
			errors.put("newCategory", "Category name must not be empty");
		} else {
			String error = "";
			if (!Pattern.matches("[a-zA-Z0-9 ]+", newCategory)) {
				error += "Category name can only consist of spaces and alphanumerical characters\n";
			}
			if (categoryRepo.findByName(newCategory) != null) {
				error += "Category name " + newCategory + " already exists\n";
			}
			
			if (error != "") {
				errors.put("newCategory",  error);
			}
		}

		if (newSubcategory == "") {
			errors.put("newSubcategory", "Subcategory name must not be empty");
		} else {
			String error = "";
			if (!Pattern.matches("[a-zA-Z0-9 ]+", newSubcategory)) {
				error += "Subcategory name can only consist of spaces and alphanumerical characters\n";
			}
			if (subcategoryRepo.findSubcategoryByName(newSubcategory) != null) {
				error += "Subcategory name " + newSubcategory + " already exists\n";
			}
			if (error != "") {
				errors.put("newSubcategory", error);
			}
			
		}

		return errors;

	}

	@Override
	public void createDynamicProduct(Product product, Item item, String newSupplier, String newBrand,
			String newCategory, String newSubcategory) {
		
		if (product.getSupplier() == null) {
			Supplier supplier = new Supplier(newSupplier);
			supplierRepo.save(supplier);
			product.setSupplier(supplier);
		}
		if (product.getBrand() == null) {
			Brand brand = new Brand(newBrand);
			brandRepo.save(brand);
			product.setBrand(brand);
		}
		if (product.getCategory() == null) {
			Category category = new Category(newCategory);
			categoryRepo.save(category);
			product.setCategory(category);
		}
		if (product.getSubcategory() == null) {
			Subcategory subcategory = new Subcategory(newSubcategory);
			subcategory.setCategory(product.getCategory());
			subcategoryRepo.save(subcategory);
			product.setSubcategory(subcategory);
		}
		
		productRepo.save(product);
		
		item.setProduct(product);
		if (item.getUnits() >= item.getReorderAt()) {
			item.setState(ItemState.IN_STOCK);
		} else {
			item.setState(ItemState.BELOW_REORDER_LEVEL);
		}
		
		itemRepo.save(item);

	}

}
