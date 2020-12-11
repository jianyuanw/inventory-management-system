package sg.edu.iss.ims.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.ims.repo.ProductRepository;

@Service
@Transactional
public class ProductImplementation {
	
	@Autowired
	private ProductRepository productRepo;

}
