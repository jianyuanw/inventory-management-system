package sg.edu.iss.ims.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.ims.supplier.Supplier;

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
