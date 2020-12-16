package sg.edu.iss.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.iss.ims.model.Product;
import sg.edu.iss.ims.model.Supplier;
import sg.edu.iss.ims.repo.ProductRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ViewProductImpl implements ViewProductService {

	@Autowired
	ProductRepository prepo;

	@Override
	public Product findProductById(Long id) {
		return prepo.findById(id).get();

	}


	@Transactional
	public List<Product> list() {
		return prepo.findAll();
	}

}
