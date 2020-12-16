package sg.edu.iss.ims.product;

import java.util.ArrayList;
import java.util.List;

public interface ProductService {
	
	public void saveProduct(Product product);
	
	public ArrayList<Product> findProductByName(String productName);
	
	public Product findProductById(Long id);
	
	public void deleteProduct(Long id);
	
	public List<Product> list();

}
