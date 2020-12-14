package sg.edu.iss.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.iss.ims.model.Item;
import sg.edu.iss.ims.model.Supplier;
import sg.edu.iss.ims.repo.ItemRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemrepo;

    @Override
    public void addItem(Item item)
    {
        itemrepo.save(item);

    }


    public Item findId (Long id) {
        return itemrepo.findById(id).get();

    }

    @Override
    public void deleteItem(Long id) {
        itemrepo.delete(findId(id));

    }


    @Transactional
    public List<Item> list()
    {
        return itemrepo.findAllByUnits();

    }


}
