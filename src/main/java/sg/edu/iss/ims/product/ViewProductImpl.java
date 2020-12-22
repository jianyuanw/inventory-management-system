package sg.edu.iss.ims.product;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import sg.edu.iss.ims.item.Item;
import sg.edu.iss.ims.item.ItemRepository;

@Service
public class ViewProductImpl implements ViewProductService {

	private ProductRepository prepo;
	private ItemRepository itemRepo;

	public ViewProductImpl(ProductRepository prepo, ItemRepository itemRepo) {
		this.prepo = prepo;
		this.itemRepo = itemRepo;
	}

	@Override
	public Product findProductById(Long id) {
		return prepo.findById(id).get();
	}

	@Transactional
	public List<Product> list() {
		return prepo.findAll();
	}

	public List<Item> findAllItems() {
		return itemRepo.findAll();
	}
}
