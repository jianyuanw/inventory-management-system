package sg.edu.iss.ims.product;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	private ProductRepository productRepo;
	
	public ProductServiceImpl(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}
	
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
		return productRepo.findProductById(id);
		
	}

	@Override
	public void deleteProduct(Long id) {
		productRepo.delete(findProductById(id));
		
	}
	
	public List<Product> list(){
		return productRepo.findAll();
	}

}
