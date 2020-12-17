package sg.edu.iss.ims.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.ims.item.Item;
import sg.edu.iss.ims.item.ItemRepository;
import sg.edu.iss.ims.supplier.Supplier;

import javax.transaction.Transactional;
import java.util.List;

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
