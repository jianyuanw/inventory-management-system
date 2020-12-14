package sg.edu.iss.ims.service;

import java.util.ArrayList;
import java.util.List;

import sg.edu.iss.ims.model.Product;

public interface ProductService {
	
	public void saveProduct(Product product);
	
	public ArrayList<Product> findProductByName(String productName);
	
	public Product findProductById(Long id);
	
	public void deleteProduct(Long id);
	
	public List<Product> list();

}
