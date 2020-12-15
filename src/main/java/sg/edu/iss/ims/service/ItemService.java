package sg.edu.iss.ims.service;

import sg.edu.iss.ims.model.Item;
import sg.edu.iss.ims.model.Product;
import sg.edu.iss.ims.model.Supplier;

import java.util.List;

public interface ItemService {

    public void addItem(Item item);
    public void deleteItem(Long Id);

    public List<Item> list();

    public Item findItemByProduct(Product product);
    public Item findItemById(Long id);
}
