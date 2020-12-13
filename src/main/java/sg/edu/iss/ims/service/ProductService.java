package sg.edu.iss.ims.service;

import sg.edu.iss.ims.model.Product;

public interface ProductService {
	
	public void createProduct(Product product);
	
	//To change this to Product when we start implementing
	public void readProduct(String productName);
	
	public void updateProduct(Product product);
	
	public void deleteProduct(Product product);

}
