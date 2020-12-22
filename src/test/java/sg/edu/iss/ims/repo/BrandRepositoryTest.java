package sg.edu.iss.ims.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.iss.ims.brand.Brand;
import sg.edu.iss.ims.brand.BrandRepository;

@SpringBootTest
public class BrandRepositoryTest {
	
	@Autowired
	BrandRepository brepo;
	
	@Test
	void findBrandByName() {
		String[] brands = {"Volkswagen", "Toyota", "Renault", "Nissan", "Mitsubishi"};
		for(String b: brands) {
			Brand brand = brepo.findByName(b);
			System.out.println(brand);
		}
		
		
	}

}
