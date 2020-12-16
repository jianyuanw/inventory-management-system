package sg.edu.iss.ims.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.ims.product.Product;
import sg.edu.iss.ims.supplier.Supplier;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepo;

    @Override
    public void addItem(Item item)
    {
        itemRepo.save(item);

    }


    public Item findId (Long id) {
        return itemRepo.findById(id).get();

    }

    @Override
    public void deleteItem(Long id) {
        itemRepo.delete(findId(id));

    }


    @Transactional
    public List<Item> list()
    {
        return itemRepo.findAll();
    }

    @Override
    public Item findItemByProduct(Product product) {
        return itemRepo.findItemByProduct(product);
    }

    @Override
    public Item findItemById(Long id) {
        return itemRepo.findById(id).get();
    }

    @Override
    public void update(Item item) {
        itemRepo.save(item);
    }
}
