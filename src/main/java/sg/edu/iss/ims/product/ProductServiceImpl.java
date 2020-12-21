package sg.edu.iss.ims.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	private ProductRepository productRepo;
	
	public ProductServiceImpl(ProductRepository productRepo) {
		this.productRepo = productRepo;
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
	
	public List<Product> list(){
		return productRepo.findAll();
	}

	@Override
	public HashMap<String, String> validate(String newSupplier, String newBrand, String newCategory, String newSubcategory) {
		HashMap<String, String> errors = new HashMap<String, String>();
		
		if (newSupplier == "") {
			errors.put("newSupplier", "Supplier name must not be empty");
		} else if (!Pattern.matches("[a-zA-Z0-9 ]+", newSupplier)) {
			errors.put("newSupplier", "Supplier name can only consist of spaces and alphanumerical characters");
		}
		
		if (newBrand == "") {
			errors.put("newBrand", "Brand name must not be empty");
		} else if (!Pattern.matches("[a-zA-Z0-9 ]+", newBrand)) {
			errors.put("newBrand", "Brand name can only consist of spaces and alphanumerical characters");
		}
		
		if (newCategory== "") {
			errors.put("newCategory", "Category name must not be empty");
		} else if (!Pattern.matches("[a-zA-Z0-9 ]+", newCategory)) {
			errors.put("newCategory", "Category name can only consist of spaces and alphanumerical characters");
		}
		
		if (newSubcategory == "") {
			errors.put("newSubcategory", "Subcategory name must not be empty");
		} else if (!Pattern.matches("[a-zA-Z0-9 ]+", newSubcategory)) {
			errors.put("newSubcategory", "Subcategory name can only consist of spaces and alphanumerical characters");
		}
		
		return errors;
		
	}

}
