package sg.edu.iss.ims.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.ims.model.Product;
import sg.edu.iss.ims.repo.ProductRepository;

@Service
@Transactional
public class ProductIServicempl implements ProductService {
	
	@Autowired
	private ProductRepository productRepo;

	@Override
	public void createProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readProduct(String productName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

}
