package sg.edu.iss.ims.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sg.edu.iss.ims.item.Item;

public interface ProductService {
	
	public void saveProduct(Product product);
	
	public ArrayList<Product> findProductByName(String productName);
	
	public Product findProductById(Long id);
	
	public void deleteProduct(Long id);
	
	public List<Product> list();

	public HashMap<String, String> validate(String newSupplier, String newBrand, String newCategory,
			String newSubcategory);

	public void createDynamicProduct(Product product, Item item, String newSupplier, String newBrand,
			String newCategory, String newSubcategory);

}
