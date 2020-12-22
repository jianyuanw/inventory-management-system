package sg.edu.iss.ims.product;

import java.util.List;

import sg.edu.iss.ims.item.Item;

public interface ViewProductService {

	public List<Product> list();
	public Product findProductById(Long id);
	public List<Item> findAllItems();

}
