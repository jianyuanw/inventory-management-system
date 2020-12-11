package sg.edu.iss.ims.service;

import sg.edu.iss.ims.model.Product;

public interface ProductInterface {
	
	public void createProduct(Product product);
	
	public void readProduct(String productName);
	
	public void updateProduct(Product product);
	
	public void deleteProduct(Product product);

}
