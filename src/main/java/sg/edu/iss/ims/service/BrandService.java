package sg.edu.iss.ims.service;

import java.util.List;

import sg.edu.iss.ims.model.Brand;

public interface BrandService {
		
	public Brand findBrandByName(String BrandName);
	
	public Brand findBrandById(Long id);
		
	public List<Brand> list();

}
