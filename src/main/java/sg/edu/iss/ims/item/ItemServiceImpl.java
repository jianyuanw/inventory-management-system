package sg.edu.iss.ims.item;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import sg.edu.iss.ims.product.Product;

@Service
public class ItemServiceImpl implements ItemService {

	private ItemRepository itemRepo;
	
	public ItemServiceImpl(ItemRepository itemRepo) {
		this.itemRepo = itemRepo;
	}

	@Override
	public void createItem(Product product, Item item) {
		item.setProduct(product);
		if (item.getUnits() > item.getReorderAt()) {
			item.setState(ItemState.IN_STOCK);
		} else {
			item.setState(ItemState.BELOW_REORDER_LEVEL);
		}
		itemRepo.save(item);
	}
	
	@Override
	public void saveItem(Item item) {
		itemRepo.save(item);

	}

	public Item findId(Long id) {
		return itemRepo.findById(id).get();

	}

	@Override
	public void deleteItem(Long id) {
		itemRepo.delete(findId(id));

	}

	@Transactional
	public List<Item> list() {
		return itemRepo.findAll();
	}
	
	public List<Item> listAvailable() {
		return itemRepo.findAllByUnitsGreaterThan(0);
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

	@Override
	public List<Integer> checkInventory(List<Item> items) {
		List<Integer> errors = new ArrayList<Integer>();
		boolean issue = false;
		for (Item item: items) {
			Item dbItem = itemRepo.findItemById(item.getId());
			if (dbItem.getUnits() < item.getUnits()) {
				issue = true;
				errors.add(dbItem.getUnits());
			} else {
				errors.add(-1);
			}
		}
		
		if (!issue) {
			errors = null;
		}
		return errors;
	}

}
