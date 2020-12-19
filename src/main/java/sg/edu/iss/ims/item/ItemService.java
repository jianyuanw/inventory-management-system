package sg.edu.iss.ims.item;

import java.util.List;

import sg.edu.iss.ims.product.Product;

public interface ItemService {

	public void saveItem(Item item);

	public void deleteItem(Long Id);

	public List<Item> list();

	public Item findItemByProduct(Product product);

	public Item findItemById(Long id);

	public void update(Item item);
	
	public List<Integer> checkInventory(List<Item> items);

	public List<Item> listAvailable();

	public void createItem(Product product, Item item);
}
