package sg.edu.iss.ims.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.iss.ims.category.Category;
import sg.edu.iss.ims.category.CategoryRepository;

@SpringBootTest
public class CategoryRepositoryTest {
	@Autowired
	CategoryRepository crepo;
	
	@Test
	void findByName() {
		String[] categories = {"Tyres", "Brake System", "Engine", "Filters"};
		for (String cate: categories) {
			Category c = crepo.findByName(cate);
			System.out.println(c);
		}
		
	}

}
