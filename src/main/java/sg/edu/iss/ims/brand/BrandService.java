package sg.edu.iss.ims.brand;

import java.util.List;

public interface BrandService {
		
	public Brand findBrandByName(String BrandName);
	
	public Brand findBrandById(Long id);
		
	public List<Brand> list();

	public Brand createBrand(String brandName);
	
	public void deleteBrand(Brand brand);

}
