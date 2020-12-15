package sg.edu.iss.ims.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.ims.model.Brand;
import sg.edu.iss.ims.repo.BrandRepository;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	private BrandRepository brandRepo;

	@Override
	public Brand findBrandByName(String BrandName) {
		return brandRepo.findBrandByName(BrandName);
	}

	@Override
	public Brand findBrandById(Long id) {
		return brandRepo.findById(id).get();
	}

	@Override
	public List<Brand> list() {
		return brandRepo.findAll();
	}

}
