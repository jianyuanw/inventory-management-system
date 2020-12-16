package sg.edu.iss.ims.item;

import sg.edu.iss.ims.product.Product;
import sg.edu.iss.ims.supplier.Supplier;

import java.util.List;

public interface ItemService {

    public void addItem(Item item);
    public void deleteItem(Long Id);

    public List<Item> list();

    public Item findItemByProduct(Product product);
    public Item findItemById(Long id);
    public void update(Item item);
}
