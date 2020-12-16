package sg.edu.iss.ims.service;

import sg.edu.iss.ims.model.Product;
import sg.edu.iss.ims.model.Supplier;

import java.util.List;

public interface ViewProductService {

	public List<Product> list();
	public Product findProductById(Long id);


}
