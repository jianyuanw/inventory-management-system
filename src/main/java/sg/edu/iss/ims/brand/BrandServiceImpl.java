package sg.edu.iss.ims.brand;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	private BrandRepository brandRepo;

	@Override
	public Brand findBrandByName(String brandName) {
		return brandRepo.findByName(brandName);
	}

	@Override
	public Brand findBrandById(Long id) {
		return brandRepo.findById(id).get();
	}

	@Override
	public List<Brand> list() {
		return brandRepo.findAll();
	}
	
	@Override
	public Brand createBrand(String brandName) {
		Brand brand = new Brand(brandName);
		brandRepo.save(brand);
		return brand;
	}
	
	@Override
	public void deleteBrand(Brand brand) {
		brandRepo.delete(brand);
	}

}
