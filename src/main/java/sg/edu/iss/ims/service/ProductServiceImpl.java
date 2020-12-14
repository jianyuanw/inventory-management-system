package sg.edu.iss.ims.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.ims.model.Product;
import sg.edu.iss.ims.repo.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepo;

	@Override
	public void saveProduct(Product product) {
		productRepo.save(product);
		
	}

	@Override
	public ArrayList<Product> findProductByName(String productName) {
		return productRepo.findByName(productName);
		
	}

	@Override
	public Product findProductById(Long id) {
		return productRepo.findById(id).get();
		
	}

	@Override
	public void deleteProduct(Long id) {
		productRepo.delete(findProductById(id));
		
	}
	
	@Transactional
	public List<Product> list(){
		return productRepo.findAll();
	}

}
