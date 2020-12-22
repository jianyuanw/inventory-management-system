package sg.edu.iss.ims.repo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.iss.ims.category.Category;
import sg.edu.iss.ims.category.CategoryRepository;
import sg.edu.iss.ims.category.Subcategory;
import sg.edu.iss.ims.category.SubcategoryRepository;

@SpringBootTest
public class SubcategoryRepositoryTest {
	@Autowired
	SubcategoryRepository subrepo;
	
	@Autowired
	CategoryRepository crepo;
	
	@Test
	void findSubcategoryByName() {
		String[] subCategories = {"Winter Tyres, Summer Tyres", 
					"Brake discs, Brake pads, Brake hose, Brake shoes", 
					"Head gasket, Engine mount, Thermostat, Ignition coil", 
					"Oil filter, Air filter, Pollen filter, Fuel filter"};
		for(String sub: subCategories) {
			Subcategory s = subrepo.findSubcategoryByName(sub);
			System.out.println(s);
		}
	}
	
	@Test
	void findSubcategoriesByCategory() {
		String[] categories = {"Tyres", "Brake System", "Engine", "Filters"};
		for(String cate: categories) {
			Category c = crepo.findByName(cate);
			List<Subcategory> sclist = subrepo.findSubcategoriesByCategory(c);
			sclist.stream().forEach(System.out::println);
		}	
	}
}
